package com.finnauxapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Adapter.HomeAdapter;
import com.finnauxapp.Adapter.LeadAdapter;
import com.finnauxapp.ApiRequest.DashBoardDataRequest;
import com.finnauxapp.ApiRequest.LeadRequest;
import com.finnauxapp.ApiResponse.DashboardDataResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LeadActivity extends AppCompatActivity {

    private SessionManager session;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;
    private TextView tvNoData;
    private String from;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_lead_list);
        session = new SessionManager(LeadActivity.this);

        recycler_view = findViewById(R.id.recyclerView);
        tvNoData = findViewById(R.id.tvNoData);

        from = getIntent().getStringExtra("TAG");

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeadActivity.this, TabActivity.class);
                startActivity(intent);
            }
        });
        TextView tvTitle = findViewById(R.id.tvTitle);
        if (from.equalsIgnoreCase("Hold Inquiry")) {
            tvTitle.setText("Hold Inquiries");
        } else {
            tvTitle.setText("My Leads");
        }

        linearLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.getLayoutManager().setAutoMeasureEnabled(true);
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.setHasFixedSize(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        callWebServiceREST();
    }

    public void callWebServiceREST() {
        final ProgressDialog progress;


        progress = new ProgressDialog(LeadActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            Call<List<LeadResponse>> call = null;

            if (from.equalsIgnoreCase("New Inquiry")) {
                LeadRequest model = new LeadRequest();
                model.setLoginUserId(session.getUserId());
                model.setInqStatus("Assigned");

                call = api.getLeadList(model);
            }

            else{

                DashBoardDataRequest request =new DashBoardDataRequest();
                request.setLoginUserId(session.getUserId());
                call = api.getHoldLead(request);

            }

            call.enqueue(new Callback<List<LeadResponse>>() {
                @Override
                public void onResponse(Call<List<LeadResponse>> call, Response<List<LeadResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<LeadResponse> list = response.body();


                    if (list != null && list.size() > 0) {
                        tvNoData.setVisibility(View.GONE);
                        LeadAdapter adpter = new LeadAdapter(LeadActivity.this, list, from);
                        recycler_view.setAdapter(adpter);
                    } else {
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<List<LeadResponse>> call, Throwable t) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
