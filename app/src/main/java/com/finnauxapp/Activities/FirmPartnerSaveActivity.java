package com.finnauxapp.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Adapter.CustomerFamilyAdapter;
import com.finnauxapp.Adapter.DirectorInfoAdapter;
import com.finnauxapp.Adapter.SpinnerAdapter;
import com.finnauxapp.ApiRequest.CustomerMemberRequest;
import com.finnauxapp.ApiRequest.CustomerRegMainRequest;
import com.finnauxapp.ApiRequest.FirmRequestModel;
import com.finnauxapp.ApiRequest.SaveCustomerMemberBaseRequest;
import com.finnauxapp.ApiRequest.SaveCustomerMemberRequest;
import com.finnauxapp.ApiRequest.SavePartnerRequestModel;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.CustomerMemberListResponse;
import com.finnauxapp.ApiResponse.FirmResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class FirmPartnerSaveActivity extends AppCompatActivity {

    private SessionManager session;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;
    private CustomerDetailResponse customerDetailObject;
    private int applicationId;
    private TextView tvAppNo;
    private ArrayList<SpinnerModel> arrListCustomerType=new ArrayList<>();
    String gender="M";
    private int memberId=0;
    private int partnerId=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_kyc_document);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Firm Directors Info");
        ImageView ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(FirmPartnerSaveActivity.this,TabActivity.class);
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
        session=new SessionManager(FirmPartnerSaveActivity.this);
        customerDetailObject = (CustomerDetailResponse) getIntent().getSerializableExtra("customerDetailObject");
        applicationId = getIntent().getIntExtra("applicationId", 0);






        TextView tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerName.setText("Customer Name : "+customerDetailObject.getCustomer_FirstName()+" " +customerDetailObject.getCustomer_LastName()+" ("+customerDetailObject.getCustomerType()+")");
        TextView tvAppNo = findViewById(R.id.tvAppNo);
        tvAppNo.setText("Application No : "+getIntent().getStringExtra("appNo"));

        recycler_view = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.getLayoutManager().setAutoMeasureEnabled(true);
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.setHasFixedSize(false);

        Button btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEditMember(null);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
    callWebServiceREST();
    }
    public void callWebServiceREST() {
        final ProgressDialog progress;



        progress = new ProgressDialog(FirmPartnerSaveActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            FirmRequestModel model=new FirmRequestModel();
            model.setFirmId(customerDetailObject.getCustomerId());
            // model.setInqStatus("Assigned");


            Call<List<FirmResponse>> call = api.getFirmPartnerApp(model);

            call.enqueue(new Callback<List<FirmResponse>>() {
                @Override
                public void onResponse(Call<List<FirmResponse>> call, Response<List<FirmResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<FirmResponse> list=response.body();

                    if(list!=null) {

                        DirectorInfoAdapter adpter = new DirectorInfoAdapter(FirmPartnerSaveActivity.this, list);
                        recycler_view.setAdapter(adpter);
                    }
                }

                @Override
                public void onFailure(Call<List<FirmResponse>> call, Throwable t) {
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

    public void addEditMember(final FirmResponse item) {

        AlertDialog.Builder dialogBuilder;
        final AlertDialog alertDialog;

        dialogBuilder  = new AlertDialog.Builder(FirmPartnerSaveActivity.this);
        final View dialog = getLayoutInflater().inflate(R.layout.view_director_info, null);

        dialogBuilder.setView(dialog);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      /*  final Dialog dialog = new Dialog(FamilyMemberListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_family_member);*/

        final EditText edDirectorName = dialog.findViewById(R.id.edDirectorName);
        final EditText tvAgeDirector = dialog.findViewById(R.id.tvAgeDirector);
        final EditText edDesignationDirector = dialog.findViewById(R.id.edDesignationDirector);
        final EditText edDirectorNumber = dialog.findViewById(R.id.edDirectorNumber);
        final EditText edDirectorShare = dialog.findViewById(R.id.edDirectorShare);
        RadioGroup rbGender = dialog.findViewById(R.id.rbGenderDirector);
        RadioButton rbMale=dialog.findViewById(R.id.rbMaleDirector);
        RadioButton rbFemale= dialog.findViewById(R.id.rbFemaleDirector);

        rbGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("checked_id",checkedId+"");


                switch (checkedId) {

                    case R.id.rbMale:

                        gender="M";
                        break;

                    case R.id.rbFemale:

                        gender="F";
                        break;


                }
            }

        });

        Button btn_save = dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edDirectorName.getText().toString().trim().isEmpty() || tvAgeDirector.getText().toString().trim().isEmpty() || edDesignationDirector.getText().toString().trim().isEmpty() || edDirectorNumber.getText().toString().trim().isEmpty() || edDirectorShare.getText().toString().trim().isEmpty())
                {
                    WebUtility.showOkDialog(FirmPartnerSaveActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "All Fields are mandatory!");
                }

                else {
                    alertDialog.dismiss();
                  //  SpinnerModel md = (SpinnerModel) sp_Relation.getSelectedItem();


                    saveWebServiceREST(gender,edDirectorName.getText().toString(),tvAgeDirector.getText().toString(),edDesignationDirector.getText().toString(),edDirectorNumber.getText().toString(),edDirectorShare.getText().toString());
                }}
        });

        if (item != null) {
            partnerId=item.getID();
            edDirectorName.setText(item.getPartner_Name());
            String gender = item.getPartner_Gender();

            if(gender.equalsIgnoreCase("M")){
                rbMale.setChecked(true);
            }
            else{
                rbFemale.setChecked(true);
            }

            tvAgeDirector.setText(String.format("%d", item.getPartner_Age()));
            edDirectorNumber.setText(item.getPartner_PhoneNo());
            edDesignationDirector.setText( item.getPartner_Designation());
            edDirectorShare.setText(String.valueOf(item.getPartner_SharePer()));


        }
        else{
            partnerId=0;
        }





        alertDialog.show();
        //  Window window = dialog.getWindow();
        //window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void saveWebServiceREST(String gender, String s1, String s2, String s3, String s4, String s5) {
        final ProgressDialog progress;



        progress = new ProgressDialog(FirmPartnerSaveActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            //   SpinnerModel spinnerModel=(SpinnerModel)sp_loan_type.getSelectedItem();
            SavePartnerRequestModel model=new SavePartnerRequestModel();
            model.setApplicationId(applicationId);
            model.setFirmId(customerDetailObject.getCustomerId());
            model.setPartner_Name(s1);
            model.setPartner_Age(Integer.parseInt(s2));
            model.setPartner_Designation(s3);
            model.setPartner_PhoneNo(s4);
            model.setPartner_Gender(gender);
           model.setPartnerId(partnerId);
            model.setPartner_Share(Double.parseDouble(s5));



            Call<List<AcceptRejectResponse>> call = api.saveFirmPartnersAp(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if(loginResponse!=null){
                        String msg= loginResponse.get(0).getMSG();
                        Toast.makeText(FirmPartnerSaveActivity.this,msg,Toast.LENGTH_LONG).show();
                        //  finish();
                        callWebServiceREST();
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
}