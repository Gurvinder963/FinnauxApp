package com.finnauxapp.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;

public class RegisterIncomeActivity extends AppCompatActivity {

    private SessionManager session;
    private CustomerDetailResponse customerDetailObject;
    private int applicationId;
    private TextView tvAppNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session=new SessionManager(RegisterIncomeActivity.this);
      
        customerDetailObject = (CustomerDetailResponse) getIntent().getSerializableExtra("customerDetailObject");
        applicationId = getIntent().getIntExtra("applicationId", 0);
       
        tvAppNo = findViewById(R.id.tvAppNo);
        tvAppNo.setText("Application : " + getIntent().getStringExtra("appNo"));
    }
}
