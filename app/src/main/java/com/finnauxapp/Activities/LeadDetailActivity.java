package com.finnauxapp.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finnauxapp.Adapter.AssetDetailAdapter;
import com.finnauxapp.ApiRequest.GenerateApplicationRequest;
import com.finnauxapp.ApiRequest.RejectRequest;
import com.finnauxapp.ApiRequest.SaveHoleRequest;
import com.finnauxapp.ApiRequest.SaveRevertRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.AssetListResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.ApiResponse.LoginResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.DateOnClick;
import com.finnauxapp.Util.DatePickerFragment;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LeadDetailActivity extends AppCompatActivity implements DateOnClick<String> {
    private EditText tvName;
    private EditText tvMobile;
    private EditText tvAddress;
    private EditText tvEmail;
    private EditText tvAmount;
    private EditText tvPurpose;
    //private Dialog dialog;
    private LeadResponse leadResponse;
    private SessionManager session;
    private EditText tvInquiryNo;
    private EditText tvBranch;
    private EditText tvSource;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_lead_detail);

       leadResponse = (LeadResponse) getIntent().getSerializableExtra("data");
        session=new SessionManager(LeadDetailActivity.this);
        ImageView ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LeadDetailActivity.this,TabActivity.class);
                startActivity(intent);
            }
        });
        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Lead Detail");
        tvName= findViewById(R.id.tvName);
        tvSource= findViewById(R.id.tvSource);
        tvInquiryNo= findViewById(R.id.tvInquiryNo);
        tvBranch= findViewById(R.id.tvBranch);
        tvMobile= findViewById(R.id.tvMobile);
        tvAddress= findViewById(R.id.tvAddress);
        tvEmail= findViewById(R.id.tvEmail);
        tvAmount= findViewById(R.id.tvAmount);
        tvPurpose= findViewById(R.id.tvPurpose);

        tvName.setText(leadResponse.getCustomerName());
        tvMobile.setText(leadResponse.getContactNumber());
        tvAddress.setText(leadResponse.getCustomerAddress());
        tvSource.setText(leadResponse.getSource());
        if(leadResponse.getEmail()!=null && !leadResponse.getEmail().isEmpty()){
        tvEmail.setText(leadResponse.getEmail());
        }
        tvAmount.setText(leadResponse.getLoanAmount());
        tvPurpose.setText(leadResponse.getPurpose());

        tvInquiryNo.setText(leadResponse.getInquiryNo());
        tvBranch.setText(leadResponse.getBranch());

        Button btn_Proceed = findViewById(R.id.btn_Proceed);
        Button btn_Hold = findViewById(R.id.btn_Hold);
        Button btn_Revert = findViewById(R.id.btn_Revert);

        btn_Hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder;
                final AlertDialog alertDialog;

                dialogBuilder  = new AlertDialog.Builder(LeadDetailActivity.this);
                final View dialog = getLayoutInflater().inflate(R.layout.dialog_hold_lead, null);

                dialogBuilder.setView(dialog);
                alertDialog = dialogBuilder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




                final EditText edDate=dialog.findViewById(R.id.edDate);
                edDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showDatePickerDialog(edDate);
                    }
                });
                final EditText edReason=dialog.findViewById(R.id.edReason);
                Button btnOk=dialog.findViewById(R.id.btnOk);
                Button btnCancel=dialog.findViewById(R.id.btnCancel);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(edDate.getText().toString().trim().isEmpty()){

                            WebUtility.showOkDialog(LeadDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter follow up date!");
                        }

                        else if(edReason.getText().toString().trim().isEmpty()){

                            WebUtility.showOkDialog(LeadDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter reason!");
                        }
                        else{
                            alertDialog.dismiss();
                            callWebServiceHoldREST(edReason.getText().toString(),edDate.getText().toString());
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        btn_Revert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder;
                final AlertDialog alertDialog;

                dialogBuilder  = new AlertDialog.Builder(LeadDetailActivity.this);
                final View dialog = getLayoutInflater().inflate(R.layout.dialog_revert_lead, null);

                dialogBuilder.setView(dialog);
                alertDialog = dialogBuilder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




                final EditText edReason=dialog.findViewById(R.id.edReason);
                Button btnOk=dialog.findViewById(R.id.btnOk);
                Button btnCancel=dialog.findViewById(R.id.btnCancel);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(edReason.getText().toString().trim().isEmpty()){

                            WebUtility.showOkDialog(LeadDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter reason!");
                        }
                        else{
                            alertDialog.dismiss();
                            callWebServiceRevertREST(edReason.getText().toString());
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });


        btn_Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(LeadDetailActivity.this,GenerateApplicationNewActivity.class);
                intent.putExtra("applicationId", 0);
                intent.putExtra("data",leadResponse);
                startActivity(intent);
            }
        });
        Button btn_Reject = findViewById(R.id.btn_Reject);
        btn_Reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder;
                final AlertDialog alertDialog;

                dialogBuilder  = new AlertDialog.Builder(LeadDetailActivity.this);
                final View dialog = getLayoutInflater().inflate(R.layout.dialog_reject_lead, null);

                dialogBuilder.setView(dialog);
                alertDialog = dialogBuilder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




               final EditText edReason=dialog.findViewById(R.id.edReason);
               Button btnOk=dialog.findViewById(R.id.btnOk);
               Button btnCancel=dialog.findViewById(R.id.btnCancel);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(edReason.getText().toString().trim().isEmpty()){

                            WebUtility.showOkDialog(LeadDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter reason!");
                        }
                        else{
                            alertDialog.dismiss();
                        callWebServiceREST(edReason.getText().toString());
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
             //   Window window = dialog.getWindow();
               // window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

    }

    public void callWebServiceREST(String reason) {
        final ProgressDialog progress;



        progress = new ProgressDialog(LeadDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            RejectRequest model=new RejectRequest();
            model.setInquiryId(leadResponse.getInquiryId());
            model.setLoginUserId(session.getUserId());
            model.setReason(reason);


            Call<List<AcceptRejectResponse>> call = api.getRejectLead(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if(loginResponse!=null){
                   String msg= loginResponse.get(0).getMSG();
                    Toast.makeText(LeadDetailActivity.this,msg,Toast.LENGTH_LONG).show();
                    finish();

                    }




                }

                @Override
                public void onFailure(Call<List<AcceptRejectResponse>> call, Throwable t) {
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
    public void callWebServiceRevertREST(String reason) {
        final ProgressDialog progress;



        progress = new ProgressDialog(LeadDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            SaveRevertRequest model=new SaveRevertRequest();
            model.setInquiryId(leadResponse.getInquiryId());
            model.setLoginUserId(session.getUserId());
            model.setRevertReason(reason);



            Call<List<AcceptRejectResponse>> call = api.saveRevertLead(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if(loginResponse!=null){
                        String msg= loginResponse.get(0).getMSG();
                        Toast.makeText(LeadDetailActivity.this,msg,Toast.LENGTH_LONG).show();
                        finish();

                    }




                }

                @Override
                public void onFailure(Call<List<AcceptRejectResponse>> call, Throwable t) {
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

    public void callWebServiceHoldREST(String reason, String s) {
        final ProgressDialog progress;



        progress = new ProgressDialog(LeadDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            SaveHoleRequest model=new SaveHoleRequest();
            model.setInquiryId(leadResponse.getInquiryId());
            model.setLoginUserId(session.getUserId());
            model.setHoldReason(reason);
            model.setNextFollowUp(s);


            Call<List<AcceptRejectResponse>> call = api.saveHoldLead(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if(loginResponse!=null){
                        String msg= loginResponse.get(0).getMSG();
                        Toast.makeText(LeadDetailActivity.this,msg,Toast.LENGTH_LONG).show();
                        finish();

                    }




                }

                @Override
                public void onFailure(Call<List<AcceptRejectResponse>> call, Throwable t) {
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
    private void showDatePickerDialog(EditText tvAnswer) {

        int year = 0, month = 0, day = 0;

        //  if (tvDob.getText().toString().equals("")) {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        //  } else {
        //     String[] date = tvDob.getText().toString().split("-");
        //    year = Integer.parseInt(date[2]);
        //    month = Integer.parseInt(date[1]) - 1;
        //    day = Integer.parseInt(date[0]);
        // }
        DatePickerFragment pickerFragment = new DatePickerFragment(false, LeadDetailActivity.this);
        pickerFragment.setContext(LeadDetailActivity.this);
        pickerFragment.setEdittext(tvAnswer);

        DatePickerDialog datePickerDialog = new DatePickerDialog(LeadDetailActivity.this, pickerFragment, year, month, day);
        datePickerDialog.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
        //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    @Override
    public void DateOnClicked(String date) {

    }
}
