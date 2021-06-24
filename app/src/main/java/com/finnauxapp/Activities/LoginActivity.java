package com.finnauxapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.finnauxapp.ApiRequest.LoginRequest;
import com.finnauxapp.ApiResponse.LoginResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edUserName;
    private EditText edPassword;
    private Button btn_login;
    private SessionManager session;
    private TextView tvAppName;
    private ImageView ivImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         session=new SessionManager(LoginActivity.this);
        edUserName=findViewById(R.id.edUserName);
        edPassword= findViewById(R.id.edPassword);
        btn_login= findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        tvAppName= findViewById(R.id.tvAppName);
        ivImage= findViewById(R.id.ivImage);

        tvAppName.setText(session.getClientName());
        Glide.with(this)
                .load(session.getClientLogo())
                .apply(RequestOptions.placeholderOf(R.drawable.user_icon).error(R.drawable.user_icon))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ivImage.setImageResource(R.drawable.user_icon);
                            }
                        }, 700);


                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
                .into(ivImage);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_login:

                if(edUserName.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please enter username",Toast.LENGTH_LONG).show();
                }
                else if(edPassword.getText().toString().trim().isEmpty()){
                    Toast.makeText(LoginActivity.this,"Please enter password",Toast.LENGTH_LONG).show();
                }
                else{

                callWebServiceREST(edUserName.getText().toString(), edPassword.getText().toString());
            }
                break;
        }



    }
    public void callWebServiceREST(String muserName, String mpassword) {
        final ProgressDialog progress;

        final String userName=muserName;
        String password=mpassword;

        progress = new ProgressDialog(LoginActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

    try {
        Retrofit retrofit= ApiClient.getClient(getApplicationContext());
        //creating the api interface
        Api api = retrofit.create(Api.class);

        LoginRequest model=new LoginRequest();
        model.setUserLoginID(userName);
        model.setUserPassword(password);

        Call<LoginResponse> call = api.getLoginDetails(model);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (progress != null && progress.isShowing()) {
                    progress.dismiss();
                }

                LoginResponse loginResponse = response.body();
                if(loginResponse!=null){

               if(loginResponse.getEmpId()!=0){

                   session.saveuserId(loginResponse.getEmpId());
                   session.saveToken(loginResponse.getToken());
                   session.saveName(loginResponse.getFirstName());
                   session.saveEmail(loginResponse.getEmail());
                   session.saveRole(loginResponse.getRoleName());
                   session.saveImage(loginResponse.getProfilePic());
                   Intent intent=new Intent(LoginActivity.this,TabActivity.class);
                   startActivity(intent);
                   finish();

               }
               else{
                   Toast.makeText(LoginActivity.this,"Invalid username or password!",Toast.LENGTH_LONG).show();
               }

                }




            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
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
