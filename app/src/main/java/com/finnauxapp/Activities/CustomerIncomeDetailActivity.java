package com.finnauxapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finnauxapp.Adapter.CustomerExistingLoanAdapter;
import com.finnauxapp.Adapter.SpinnerAdapter;
import com.finnauxapp.ApiRequest.CustomerDetailRequest;
import com.finnauxapp.ApiRequest.CustomerIncomeBaseRequest;
import com.finnauxapp.ApiRequest.CustomerRegMainRequest;
import com.finnauxapp.ApiRequest.ExpenditureSaveRequest;
import com.finnauxapp.ApiRequest.SaveIncomeDetailRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.ExistingLoanResponse;
import com.finnauxapp.ApiResponse.IncomeDetailResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomerIncomeDetailActivity extends AppCompatActivity {
    private Spinner sp_job_type;
    private EditText edCompanyFirmName;
    private EditText edAddress;
    private EditText edPhoneNo;
    private EditText edMonthlyIncome;
    private EditText edJobFrom;
    private EditText edPriviousCompany;
    private EditText edNoOfYearPreviousCompany;
    private EditText edReasonToLeave;
    private ArrayList<SpinnerModel> arrListCustomerType = new ArrayList<>();
    private CustomerDetailResponse customerDetailObject;
    private int applicationId;
    private TextView tvAppNo;
    private SessionManager session;
    private Button btnSave;
    private EditText edNoOfPartner;
    private EditText edCustomerShareInFirm;
    private EditText edTotalTurnover;
    private EditText edGstRegNoOfFirm;
    private EditText edFirmType;
    private EditText edTotalStaff;
    private EditText edDirectorName;
    private EditText edDirectorContact;
    private EditText edDesignation;
    private TextInputLayout tilNoOfPartner;
    private TextInputLayout tilCustomerShareInFirm;
    private TextInputLayout tilTotalTurnover;
    private TextInputLayout tilGstRegNoOfFirm;
    private TextInputLayout tilFirmType;
    private TextInputLayout tilMonthlyIncome;
    private TextInputLayout tilJobFrom;
    private TextInputLayout tilPriviousCompany;
    private TextInputLayout tilNoOfYearPreviousCompany;
    private TextInputLayout tilReasonToLeave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detail);




        ImageView ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CustomerIncomeDetailActivity.this,TabActivity.class);
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
        tvTitle.setText("Customer Income Detail");

        SpinnerModel model1 = new SpinnerModel();
        model1.setId("1");
        model1.setName("Salaried");
        arrListCustomerType.add(model1);
        SpinnerModel model2 = new SpinnerModel();
        model2.setId("2");
        model2.setName("Business");
        arrListCustomerType.add(model2);
        sp_job_type = findViewById(R.id.sp_job_type);
        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerIncomeDetailActivity.this, arrListCustomerType);
        sp_job_type.setAdapter(enqiryAdapter);
        edCompanyFirmName = findViewById(R.id.edCompanyFirmName);
        edAddress = findViewById(R.id.edAddress);
        edPhoneNo = findViewById(R.id.edPhoneNo);
        edMonthlyIncome = findViewById(R.id.edMonthlyIncome);
        edJobFrom = findViewById(R.id.edJobFrom);
        edPriviousCompany = findViewById(R.id.edPriviousCompany);
        edNoOfYearPreviousCompany = findViewById(R.id.edNoOfYearPreviousCompany);
        edReasonToLeave = findViewById(R.id.edReasonToLeave);

        tilMonthlyIncome = findViewById(R.id.tilMonthlyIncome);
        tilJobFrom = findViewById(R.id.tilJobFrom);
        tilPriviousCompany = findViewById(R.id.tilPriviousCompany);
        tilNoOfYearPreviousCompany = findViewById(R.id.tilNoOfYearPreviousCompany);
        tilReasonToLeave = findViewById(R.id.tilReasonToLeave);


        edNoOfPartner = findViewById(R.id.edNoOfPartner);
        edCustomerShareInFirm = findViewById(R.id.edCustomerShareInFirm);
        edTotalTurnover = findViewById(R.id.edTotalTurnover);
        edGstRegNoOfFirm = findViewById(R.id.edGstRegNoOfFirm);
        edFirmType = findViewById(R.id.edFirmType);

        tilNoOfPartner = findViewById(R.id.tilNoOfPartner);
        tilCustomerShareInFirm = findViewById(R.id.tilCustomerShareInFirm);
        tilTotalTurnover = findViewById(R.id.tilTotalTurnover);
        tilGstRegNoOfFirm = findViewById(R.id.tilGstRegNoOfFirm);
        tilFirmType = findViewById(R.id.tilFirmType);



        edTotalStaff = findViewById(R.id.edTotalStaff);
        edDirectorName = findViewById(R.id.edDirectorName);
        edDirectorContact = findViewById(R.id.edDirectorContact);
        edDesignation = findViewById(R.id.edDesignation);


        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerModel model = (SpinnerModel) sp_job_type.getSelectedItem();

               if(model.getName().equalsIgnoreCase("Salaried")){
                   if (edCompanyFirmName.getText().toString().trim().isEmpty()) {
                       WebUtility.showOkDialog(CustomerIncomeDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Company/firm name!");
                   }
                  else if (edAddress.getText().toString().trim().isEmpty()) {
                       WebUtility.showOkDialog(CustomerIncomeDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Address!");
                   }
                   else if (edPhoneNo.getText().toString().trim().isEmpty()) {
                       WebUtility.showOkDialog(CustomerIncomeDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Phone No.!");
                   }
                   else if (edPhoneNo.getText().toString().trim().length() < 10) {
                       WebUtility.showOkDialog(CustomerIncomeDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Phone no. not valid! ");
                   }
                   else if (edMonthlyIncome.getText().toString().trim().isEmpty()) {
                       WebUtility.showOkDialog(CustomerIncomeDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter monthly income!");
                   }
                   else if (edDesignation.getText().toString().trim().isEmpty()) {
                       WebUtility.showOkDialog(CustomerIncomeDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Designation!");
                   }
                   else{
                   saveWebServiceREST(model.getName(), edCompanyFirmName.getText().toString(), edAddress.getText().toString(), edPhoneNo.getText().toString(), edMonthlyIncome.getText().toString(), edJobFrom.getText().toString(), edPriviousCompany.getText().toString(), edNoOfYearPreviousCompany.getText().toString(), edReasonToLeave.getText().toString());
               }
               }
               else{
                   if (edCompanyFirmName.getText().toString().trim().isEmpty()) {
                       WebUtility.showOkDialog(CustomerIncomeDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Company/firm name!");
                   }
                   else if (edAddress.getText().toString().trim().isEmpty()) {
                       WebUtility.showOkDialog(CustomerIncomeDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Address!");
                   }
                   else if (edPhoneNo.getText().toString().trim().isEmpty()) {
                       WebUtility.showOkDialog(CustomerIncomeDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Phone No.!");
                   }
                   else if (edPhoneNo.getText().toString().trim().length() < 10) {
                       WebUtility.showOkDialog(CustomerIncomeDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Phone no. not valid! ");
                   }
                   else if (edMonthlyIncome.getText().toString().trim().isEmpty()) {
                       WebUtility.showOkDialog(CustomerIncomeDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter monthly income!");
                   }
                   else if (edTotalTurnover.getText().toString().trim().isEmpty()) {
                       WebUtility.showOkDialog(CustomerIncomeDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter total turnover!");
                   }

                   else{
                       saveWebServiceREST(model.getName(), edCompanyFirmName.getText().toString(), edAddress.getText().toString(), edPhoneNo.getText().toString(), edMonthlyIncome.getText().toString(), edJobFrom.getText().toString(), edPriviousCompany.getText().toString(), edNoOfYearPreviousCompany.getText().toString(), edReasonToLeave.getText().toString());
                   }
               }


            }
        });


        session = new SessionManager(CustomerIncomeDetailActivity.this);
        customerDetailObject = (CustomerDetailResponse) getIntent().getSerializableExtra("customerDetailObject");

        applicationId = getIntent().getIntExtra("applicationId", 0);
        TextView tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerName.setText("Customer Name : "+customerDetailObject.getCustomer_FirstName()+" " +customerDetailObject.getCustomer_LastName()+" ("+customerDetailObject.getCustomerType()+")");
        TextView tvAppNo = findViewById(R.id.tvAppNo);
        tvAppNo.setText("Application No : "+getIntent().getStringExtra("appNo"));

        sp_job_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               SpinnerModel model= (SpinnerModel) sp_job_type.getSelectedItem();

               if(model.getName().equalsIgnoreCase("Business")){
                   tilNoOfPartner.setVisibility(View.VISIBLE);
                   tilCustomerShareInFirm.setVisibility(View.VISIBLE);
                   tilTotalTurnover.setVisibility(View.VISIBLE);
                   tilTotalTurnover.setVisibility(View.VISIBLE);
                   tilGstRegNoOfFirm.setVisibility(View.VISIBLE);
                   tilFirmType.setVisibility(View.VISIBLE);


                   tilJobFrom.setVisibility(View.GONE);
                   tilPriviousCompany.setVisibility(View.GONE);
                   tilNoOfYearPreviousCompany.setVisibility(View.GONE);
                   tilReasonToLeave.setVisibility(View.GONE);
               }
               else{
                   tilNoOfPartner.setVisibility(View.GONE);
                   tilCustomerShareInFirm.setVisibility(View.GONE);
                   tilTotalTurnover.setVisibility(View.GONE);
                   tilTotalTurnover.setVisibility(View.GONE);
                   tilGstRegNoOfFirm.setVisibility(View.GONE);
                   tilFirmType.setVisibility(View.GONE);


                   tilMonthlyIncome.setVisibility(View.VISIBLE);
                   tilJobFrom.setVisibility(View.VISIBLE);
                   tilPriviousCompany.setVisibility(View.VISIBLE);
                   tilNoOfYearPreviousCompany.setVisibility(View.VISIBLE);
                   tilReasonToLeave.setVisibility(View.VISIBLE);
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        callWebServiceREST();
    }

    public void saveWebServiceREST(String jobType, String coName, String address, String phone, String mothlyIncome, String jobFrom, String previousCompany, String NoofYearPCo, String reasonLeave) {
        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerIncomeDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            //   SpinnerModel spinnerModel=(SpinnerModel)sp_loan_type.getSelectedItem();
            SaveIncomeDetailRequest model = new SaveIncomeDetailRequest();
            // model.setExpenditueId(expenditureId);
            model.setApplicationId(applicationId);
            model.setCustomerId(customerDetailObject.getCustomerId());
            model.setLoginUserId(session.getUserId());
            model.setOccupationType(jobType);
            model.setCompanyName(coName);
            model.setCompanyAddress(address);
            model.setCompanyContactNo(phone);
            model.setMonthlyIncome(mothlyIncome);
            if(!jobFrom.isEmpty()) {
                model.setJoinedDate(jobFrom + "-01" + "-01");
            }
            model.setPreviousCompanyName(previousCompany);
            if(!NoofYearPCo.isEmpty()) {
                model.setPreviousCompanyTotalYear(Integer.parseInt(NoofYearPCo));
            }

            model.setReasonToLeavePrevious(reasonLeave);
            if (!edTotalStaff.getText().toString().isEmpty()) {
                model.setTotalStaff(Integer.parseInt(edTotalStaff.getText().toString()));
            }
            model.setDesignation(edDesignation.getText().toString());
            model.setDirectorName(edDirectorName.getText().toString());
            model.setDirectorContactNo(edDirectorContact.getText().toString());

            if(!edNoOfPartner.getText().toString().isEmpty()) {
                model.setNoOfPartner(Integer.parseInt(edNoOfPartner.getText().toString()));
            }
            model.setCustomerSharePercentage(edCustomerShareInFirm.getText().toString());
          model.setTotalTurnOver(edTotalTurnover.getText().toString());
          model.setFirmRegNo(edGstRegNoOfFirm.getText().toString());
          model.setCompanyType(edFirmType.getText().toString());

            CustomerIncomeBaseRequest customerIncomeBaseRequest = new CustomerIncomeBaseRequest();
            customerIncomeBaseRequest.setCustomerIncome(model);

            String res = new Gson().toJson(customerIncomeBaseRequest);
            Log.d("my_json", res);


            CustomerRegMainRequest customerRegMainRequest = new CustomerRegMainRequest();
            customerRegMainRequest.setJson(res);

            Call<List<AcceptRejectResponse>> call = api.saveCustomerIncomeDetailsApp(customerRegMainRequest);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        String msg = loginResponse.get(0).getMSG();
                        Toast.makeText(CustomerIncomeDetailActivity.this, msg, Toast.LENGTH_LONG).show();
                        finish();
                        //  callWebServiceREST();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callWebServiceREST() {
        final ProgressDialog progress;



        progress = new ProgressDialog(CustomerIncomeDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            CustomerDetailRequest model = new CustomerDetailRequest();
            model.setCustomerId(customerDetailObject.getCustomerId());
            model.setApplicationId(applicationId);


            Call<List<IncomeDetailResponse>> call = api.getCustomerIncomeDetailsApp(model);

            call.enqueue(new Callback<List<IncomeDetailResponse>>() {
                @Override
                public void onResponse(Call<List<IncomeDetailResponse>> call, Response<List<IncomeDetailResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<IncomeDetailResponse> list=response.body();

                    if(list!=null && list.size()>0){

                        String occupationType = list.get(0).getOccupationType();
                        for(int i=0;i<arrListCustomerType.size();i++){
                            if(occupationType.equalsIgnoreCase(arrListCustomerType.get(i).getName())){
                                sp_job_type.setSelection(i);
                                break;
                            }
                        }
                       edCompanyFirmName.setText(list.get(0).getCompanyName());
                        edAddress.setText(list.get(0).getCompanyAddress());
                        edPhoneNo.setText(list.get(0).getComapnyConatctNo());
                        String s=list.get(0).getJoinedDate();

                        if(s!=null && !s.isEmpty()) {
                            String[] arr = s.split("\\s+");
                            String[] bb = arr[0].split("/");
                            edJobFrom.setText(bb[2]);
                        }
                        edMonthlyIncome.setText(String.format("%d", list.get(0).getMonthlyIncome()));

                        edTotalStaff.setText(String.format("%d", list.get(0).getTotalStaff()));
                        edDirectorName.setText(list.get(0).getDirectorName());
                        edDirectorContact.setText(list.get(0).getDirectorConatctNo());
                        edDesignation.setText(list.get(0).getDesignation());
                        edPriviousCompany.setText(list.get(0).getPerviousCompanyName());
                        edNoOfYearPreviousCompany.setText(list.get(0).getPerviousCompanyTotalYear()+"");
                        edReasonToLeave.setText(list.get(0).getResoanToLeavePervious());

                        edGstRegNoOfFirm.setText(list.get(0).getFirmRegNo());
                        edTotalTurnover.setText(list.get(0).getTotalTrunOver());
                        edCustomerShareInFirm.setText(list.get(0).getCustomerSharePercentage());
                        edNoOfPartner.setText(String.format("%d", list.get(0).getNoofPartner()));

                        edFirmType.setText(list.get(0).getCompanyType());

                    }



                }

                @Override
                public void onFailure(Call<List<IncomeDetailResponse>> call, Throwable t) {
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
