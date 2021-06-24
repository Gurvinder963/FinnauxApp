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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Adapter.LeadAdapter;
import com.finnauxapp.Adapter.MyApplicationAdapter;
import com.finnauxapp.ApiRequest.DashBoardDataRequest;
import com.finnauxapp.ApiRequest.LeadRequest;
import com.finnauxapp.ApiResponse.ApplicationListResponse;
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

public class SendBackBranchApplicationActivity extends AppCompatActivity {

    private SessionManager session;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;
    public String TAG ="Send Back Branch Applications";
    private TextView tvNoData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_myapplication_list);
        tvNoData = findViewById(R.id.tvNoData);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("File Send Back to branch");

        ImageView ivBack = findViewById(R.id.ivBack);
        ImageView ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SendBackBranchApplicationActivity.this,TabActivity.class);
                startActivity(intent);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        session=new SessionManager(SendBackBranchApplicationActivity.this);

        recycler_view = findViewById(R.id.recyclerView);
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



        progress = new ProgressDialog(SendBackBranchApplicationActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            DashBoardDataRequest model=new DashBoardDataRequest();
            model.setLoginUserId(session.getUserId());
            // model.setInqStatus("Assigned");


            Call<List<ApplicationListResponse>> call = api.getApplicationsSendBackBranchApp(model);

            call.enqueue(new Callback<List<ApplicationListResponse>>() {
                @Override
                public void onResponse(Call<List<ApplicationListResponse>> call, Response<List<ApplicationListResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<ApplicationListResponse> list=response.body();


                    if(list!=null && list.size()>0) {
                        tvNoData.setVisibility(View.GONE);
                        MyApplicationAdapter adpter = new MyApplicationAdapter(SendBackBranchApplicationActivity.this, list,TAG);
                        recycler_view.setAdapter(adpter);
                    }
                    else{
                        tvNoData.setVisibility(View.VISIBLE);
                    }



                }

                @Override
                public void onFailure(Call<List<ApplicationListResponse>> call, Throwable t) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }
}

