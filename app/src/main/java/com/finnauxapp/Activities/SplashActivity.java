package com.finnauxapp.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finnauxapp.Adapter.LeadAdapter;
import com.finnauxapp.ApiRequest.ClientCodeRequest;
import com.finnauxapp.ApiRequest.DashBoardDataRequest;
import com.finnauxapp.ApiRequest.LeadRequest;
import com.finnauxapp.ApiRequest.RejectRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.ClientCodeResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SplashActivity extends AppCompatActivity {

    private SessionManager session;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        session = new SessionManager(SplashActivity.this);

        if (session.getClientUrl() == null) {


            AlertDialog.Builder dialogBuilder;
            final AlertDialog alertDialog;

            dialogBuilder = new AlertDialog.Builder(SplashActivity.this);
            final View dialog = getLayoutInflater().inflate(R.layout.dialog_client_info, null);


            final EditText edCode = dialog.findViewById(R.id.edCode);
            Button btnOk = dialog.findViewById(R.id.btnOk);
            Button btnCancel = dialog.findViewById(R.id.btnCancel);

            dialogBuilder.setView(dialog);
            alertDialog = dialogBuilder.create();
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (edCode.getText().toString().trim().equalsIgnoreCase("")) {

                        WebUtility.showOkDialog(SplashActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter client code!");
                    } else {

                        callWebServiceREST(alertDialog,edCode.getText().toString().trim());
                    }

                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
        } else {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Write whatever to want to do after delay specified (1 sec)
                    if (session.getName().isEmpty()) {

                        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(SplashActivity.this, TabActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }, 2000);
        }
    }

    public void callWebServiceREST(final AlertDialog alertDialog, String code) {
        final ProgressDialog progress;


        progress = new ProgressDialog(SplashActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            ClientCodeRequest model=new ClientCodeRequest();
            model.setCode(code);


            Call<List<ClientCodeResponse>> call = api.getClientAPIDetails(model);

            call.enqueue(new Callback<List<ClientCodeResponse>>() {
                @Override
                public void onResponse(Call<List<ClientCodeResponse>> call, Response<List<ClientCodeResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<ClientCodeResponse> list = response.body();

                    if(list!=null){
                        if(list.size()>0) {
                            alertDialog.dismiss();
                            ApiClient.retrofit=null;
                          //  session.saveClientURL(list.get(0).getClient_API_URL()+"/");
                            session.saveClientInfo(list.get(0).getClient_API_URL()+"/", String.valueOf(list.get(0).getClient_ID()),list.get(0).getClient_Name(),list.get(0).getClient_Logo_Image());
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Write whatever to want to do after delay specified (1 sec)

                                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();

                                }
                            }, 1000);
                        }
                        else{
                            WebUtility.showOkDialog(SplashActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Invalid input!");
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<ClientCodeResponse>> call, Throwable t) {
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
