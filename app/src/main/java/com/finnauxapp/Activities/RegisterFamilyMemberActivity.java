package com.finnauxapp.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Adapter.MyApplicationAdapter;
import com.finnauxapp.ApiRequest.CustomerRegMainRequest;
import com.finnauxapp.ApiRequest.DashBoardDataRequest;
import com.finnauxapp.ApiRequest.SaveEnquiryRequest;
import com.finnauxapp.ApiRequest.SaveInquiryBaseRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.ApplicationListResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterFamilyMemberActivity extends AppCompatActivity {


    private SessionManager session;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_member_list);
        session=new SessionManager(RegisterFamilyMemberActivity.this);

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



        progress = new ProgressDialog(RegisterFamilyMemberActivity.this);
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


            Call<List<ApplicationListResponse>> call = api.getApplicationList(model);

            call.enqueue(new Callback<List<ApplicationListResponse>>() {
                @Override
                public void onResponse(Call<List<ApplicationListResponse>> call, Response<List<ApplicationListResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<ApplicationListResponse> list=response.body();




                //    MyApplicationAdapter adpter = new MyApplicationAdapter(RegisterFamilyMemberActivity.this, list);
                 //   recycler_view.setAdapter(adpter);

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
