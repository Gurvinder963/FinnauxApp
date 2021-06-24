package com.finnauxapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.finnauxapp.Adapter.SpinnerAdapter;
import com.finnauxapp.ApiRequest.BranchRequest;
import com.finnauxapp.ApiRequest.CustomerRegMainRequest;
import com.finnauxapp.ApiRequest.DistrictRequest;
import com.finnauxapp.ApiRequest.GenerateApplicationRequest;
import com.finnauxapp.ApiRequest.PartnerRequest;
import com.finnauxapp.ApiRequest.SaveEnquiryRequest;
import com.finnauxapp.ApiRequest.SaveInquiryBaseRequest;
import com.finnauxapp.ApiRequest.TahsilRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.DistrictResponse;
import com.finnauxapp.ApiResponse.EmployeeBranchResponse;
import com.finnauxapp.ApiResponse.LeadSouseResponse;
import com.finnauxapp.ApiResponse.PartnerResponse;
import com.finnauxapp.ApiResponse.ProductTypeResponse;
import com.finnauxapp.ApiResponse.StateResponse;
import com.finnauxapp.ApiResponse.TahsilResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLDisplay;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterNewInquiryActivity extends AppCompatActivity {


    private Spinner sp_loan_type;
    private EditText edName;
    private EditText edAddress;
    private EditText edContact;
    private EditText edLoanAmount;
    private EditText edPupose;
    private Button btn_Submit;
    ArrayList<SpinnerModel> arrListProduct = new ArrayList<>();
    ArrayList<SpinnerModel> arrListReference = new ArrayList<>();
    ArrayList<SpinnerModel> arrListBranch = new ArrayList<>();
    private EditText edEmail;
    private ImageView ivHome;
    private Spinner sp_lead_refrence;
    ArrayList<SpinnerModel> arrListState = new ArrayList<>();
    ArrayList<SpinnerModel> arrListDistrict = new ArrayList<>();
    ArrayList<SpinnerModel> arrListTahsil = new ArrayList<>();
    private Spinner sp_state;
    private Spinner sp_district;

    private Spinner sp_Tehsil;
    private LinearLayout llLeadReference;
    private EditText edReference;
    private CheckBox cbSelfAssign;
    private EditText edPincode;
    private SessionManager session;
    private LinearLayout llBranch;
    private Spinner sp_Branch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_new_inquiry);

        sp_loan_type = findViewById(R.id.sp_loan_type);
        sp_lead_refrence = findViewById(R.id.sp_lead_refrence);
        llLeadReference = findViewById(R.id.llLeadReference);
        cbSelfAssign = findViewById(R.id.cbSelfAssign);
        llBranch = findViewById(R.id.llBranch);
        sp_Branch = findViewById(R.id.sp_Branch);
        ivHome = findViewById(R.id.ivHome);
        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        edAddress = findViewById(R.id.edAddress);
        edContact = findViewById(R.id.edContact);
        edLoanAmount = findViewById(R.id.edLoanAmount);
        edReference = findViewById(R.id.edReference);
        edPupose = findViewById(R.id.edPupose);
        edPincode = findViewById(R.id.edPincode);
        session = new SessionManager(this);
        String processIds=session.getProcessIds();
        if(processIds.contains("3")){
            cbSelfAssign.setVisibility(View.VISIBLE);

        }

        cbSelfAssign.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                  llBranch.setVisibility(View.VISIBLE);
                }
                else{
                    llBranch.setVisibility(View.GONE);
                }
            }
        });

        btn_Submit = findViewById(R.id.btn_Submit);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpinnerModel spinnerModel = (SpinnerModel) sp_loan_type.getSelectedItem();
                SpinnerModel spinnerModel1 = (SpinnerModel) sp_state.getSelectedItem();
                SpinnerModel spinnerModel2 = (SpinnerModel) sp_district.getSelectedItem();
                SpinnerModel spinnerModel3 = (SpinnerModel) sp_Tehsil.getSelectedItem();

                SpinnerModel spinnerModelLoan = (SpinnerModel) sp_loan_type.getSelectedItem();

                String ref="";
                if (spinnerModelLoan.getName().equalsIgnoreCase("DSA") || spinnerModelLoan.getName().equalsIgnoreCase("Dealer") || spinnerModelLoan.getName().equalsIgnoreCase("Agent")) {
                    SpinnerModel spLead = (SpinnerModel) sp_lead_refrence.getSelectedItem();
                    ref = spLead.getId();
                } else if (spinnerModelLoan.getName().equalsIgnoreCase("Reference")) {
                    ref = edReference.getText().toString();
                }


                if (spinnerModel.getName().equalsIgnoreCase("Select Lead Source")) {

                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please Select Lead Source!");
                }

                else if (ref.equalsIgnoreCase("-1") && (spinnerModelLoan.getName().equalsIgnoreCase("DSA") || spinnerModelLoan.getName().equalsIgnoreCase("Dealer") || spinnerModelLoan.getName().equalsIgnoreCase("Agent"))) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please Select "+spinnerModelLoan.getName());
                }
                else if (ref.isEmpty() && spinnerModelLoan.getName().equalsIgnoreCase("Reference")) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter reference");
                }

                else if (edName.getText().toString().trim().isEmpty()) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter name!");
                } else if (edAddress.getText().toString().trim().isEmpty()) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter address!");
                }
                else if (spinnerModel1.getName().equalsIgnoreCase("--Select State--")) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select state!");
                }
                else if (spinnerModel2.getName().equalsIgnoreCase("--Select District--")) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select district!");
                }
                else if (spinnerModel3.getName().equalsIgnoreCase("--Select Tehsil--")) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select tehsil!");
                }
                else if (edPincode.getText().toString().trim().isEmpty()) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter pincode!");
                }
                else if (edPincode.getText().toString().trim().length() < 6) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Pincode not valid! ");
                }


                else if (edContact.getText().toString().trim().isEmpty()) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter contact!");
                }
                else if (edContact.getText().toString().trim().length() < 10) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Contact no. not valid! ");
                }
                else if(!edEmail.getText().toString().trim().isEmpty() && !WebUtility.validEmail(edEmail.getText().toString())){
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Email not valid!");
                }

                else if (edLoanAmount.getText().toString().trim().isEmpty()) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter loan amount!");
                }

                else if (Integer.parseInt(edLoanAmount.getText().toString().trim())<1) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Loan amount should be greater than 0!");
                }


                else if (edPupose.getText().toString().trim().isEmpty()) {
                    WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter purpose!");
                } else {

                    SpinnerModel md
                            =(SpinnerModel)sp_Branch.getSelectedItem();

                    if(cbSelfAssign.isChecked() && md.getId().equalsIgnoreCase("0")){
                        WebUtility.showOkDialog(RegisterNewInquiryActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select branch!");
                    }
                    else {

                        callWebServiceREST();
                    }
                }
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sp_state = findViewById(R.id.sp_state);
        sp_district = findViewById(R.id.sp_district);
        sp_Tehsil = findViewById(R.id.sp_Tehsil);
        getStateList();
        getProductList();
        getBranchList();
    }

    public void getProductList() {
       /* final ProgressDialog progress;



        progress = new ProgressDialog(RegisterNewInquiryActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();*/

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);


            Call<List<LeadSouseResponse>> call = api.getLeadSourse();

            call.enqueue(new Callback<List<LeadSouseResponse>>() {
                @Override
                public void onResponse(Call<List<LeadSouseResponse>> call, Response<List<LeadSouseResponse>> response) {
                  /*  if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/

                    List<LeadSouseResponse> list = response.body();
                    if (list != null) {
                        SpinnerModel itemp1 = new SpinnerModel();
                        itemp1.setId("0");
                        itemp1.setName("Select Lead Source");
                        arrListProduct.add(itemp1);
                        for (int i = 0; i < list.size(); i++) {
                            SpinnerModel itemp = new SpinnerModel();
                            itemp.setId(String.valueOf(i));
                            itemp.setName(list.get(i).getLead_Source());
                            arrListProduct.add(itemp);
                        }


                        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(RegisterNewInquiryActivity.this, arrListProduct);
                        sp_loan_type.setAdapter(enqiryAdapter);

                        sp_loan_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                SpinnerModel spinnerModel = (SpinnerModel) sp_loan_type.getSelectedItem();
                                arrListReference.clear();
                                if (spinnerModel.getName().equalsIgnoreCase("DSA") || spinnerModel.getName().equalsIgnoreCase("Dealer") || spinnerModel.getName().equalsIgnoreCase("Agent")) {
                                    llLeadReference.setVisibility(View.VISIBLE);
                                    sp_lead_refrence.setVisibility(View.VISIBLE);
                                    edReference.setVisibility(View.GONE);

                                    callWebServiceDSAREST(spinnerModel.getName());
                                } else if (spinnerModel.getName().equalsIgnoreCase("Reference")) {
                                    llLeadReference.setVisibility(View.VISIBLE);
                                    sp_lead_refrence.setVisibility(View.GONE);
                                    edReference.setVisibility(View.VISIBLE);
                                } else {
                                    llLeadReference.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });


                    }


                }

                @Override
                public void onFailure(Call<List<LeadSouseResponse>> call, Throwable t) {
                  /*  if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getBranchList() {
       /* final ProgressDialog progress;



        progress = new ProgressDialog(RegisterNewInquiryActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();*/

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            SessionManager sessionManager=new SessionManager(RegisterNewInquiryActivity.this);
            BranchRequest request =new BranchRequest();
            request.setEmployeeId(sessionManager.getUserId());
            Call<List<EmployeeBranchResponse>> call = api.getEmployeeBranch(request);

            call.enqueue(new Callback<List<EmployeeBranchResponse>>() {
                @Override
                public void onResponse(Call<List<EmployeeBranchResponse>> call, Response<List<EmployeeBranchResponse>> response) {
                  /*  if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/

                    List<EmployeeBranchResponse> list = response.body();
                    if (list != null) {
                        SpinnerModel itemp1 = new SpinnerModel();
                        itemp1.setId("0");
                        itemp1.setName("--Select Branch--");
                        arrListBranch.add(itemp1);
                        for (int i = 0; i < list.size(); i++) {
                            SpinnerModel itemp = new SpinnerModel();
                            itemp.setId(String.valueOf(list.get(i).getBranchId()));
                            itemp.setName(list.get(i).getBranch_Name());
                            arrListBranch.add(itemp);
                        }


                        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(RegisterNewInquiryActivity.this, arrListBranch);
                        sp_Branch.setAdapter(enqiryAdapter);




                    }


                }

                @Override
                public void onFailure(Call<List<EmployeeBranchResponse>> call, Throwable t) {
                  /*  if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void callWebServiceREST() {

        String ref;

        SpinnerModel spinnerModelLoan = (SpinnerModel) sp_loan_type.getSelectedItem();

        if (spinnerModelLoan.getName().equalsIgnoreCase("DSA") || spinnerModelLoan.getName().equalsIgnoreCase("Dealer") || spinnerModelLoan.getName().equalsIgnoreCase("Agent")) {
            SpinnerModel spLead = (SpinnerModel) sp_lead_refrence.getSelectedItem();
            ref = spLead.getId();
        } else if (spinnerModelLoan.getName().equalsIgnoreCase("Reference")) {
            ref = edReference.getText().toString();
        } else {
            ref = "";
        }


        int selfAssign = 0;
        if (cbSelfAssign.isChecked()) {
            selfAssign = 1;
        }

        SpinnerModel spinnerModel2 = (SpinnerModel) sp_state.getSelectedItem();
        SpinnerModel spinnerModel3 = (SpinnerModel) sp_district.getSelectedItem();
        SpinnerModel spinnerModel4 = (SpinnerModel) sp_Tehsil.getSelectedItem();


        final ProgressDialog progress;


        progress = new ProgressDialog(RegisterNewInquiryActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            SpinnerModel spinnerModel = (SpinnerModel) sp_loan_type.getSelectedItem();
            SaveEnquiryRequest model = new SaveEnquiryRequest();
            model.setLoginUserId(session.getUserId());
            model.setSource(spinnerModel.getName());
            model.setCustomerName(edName.getText().toString());
            model.setCustomerAddress(edAddress.getText().toString());
            model.setCustomerNumber(edContact.getText().toString());
            model.setCustomerEmail(edEmail.getText().toString());
            model.setLoanAmount(Integer.parseInt(edLoanAmount.getText().toString()));
            model.setPurpose(edPupose.getText().toString());
            model.setCustomerPincode(edPincode.getText().toString());
            model.setCustomerState(spinnerModel2.getName());
            model.setCustomerDistrict(spinnerModel3.getName());
            model.setCustomerTehsil(spinnerModel4.getName());
            model.setInquirySelfAssigned(selfAssign);
            if(cbSelfAssign.isChecked()){
                SpinnerModel md=(SpinnerModel) sp_Branch.getSelectedItem();
                model.setBranchId(Integer.parseInt(md.getId()));
            }

            model.setLeadReference(ref);

            SaveInquiryBaseRequest request = new SaveInquiryBaseRequest();
            request.setInquiry(model);

            String res = new Gson().toJson(request);
            Log.d("my_json", res);


            CustomerRegMainRequest customerRegMainRequest = new CustomerRegMainRequest();
            customerRegMainRequest.setJson(res);

            Call<List<AcceptRejectResponse>> call = api.saveNewEnquiry(customerRegMainRequest);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    try {
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        String msg = loginResponse.get(0).getMSG();
                        Toast.makeText(RegisterNewInquiryActivity.this, msg, Toast.LENGTH_LONG).show();

                        sp_loan_type.setSelection(0);


                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Write whatever to want to do after delay specified (1 sec)
                                edName.setText("");
                                edEmail.setText("");
                                edAddress.setText("");
                                edContact.setText("");
                                edLoanAmount.setText("");
                                edPupose.setText("");
                                edPincode.setText("");
                                arrListDistrict.clear();
                                arrListTahsil.clear();
                                TabActivity parentActivity;
                                parentActivity = (TabActivity) RegisterNewInquiryActivity.this.getParent();
                                parentActivity.onTabChange();
                            }
                        }, 1000);
                        //  finish();

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

    public void callWebServiceDSAREST(final String type) {
       /* final ProgressDialog progress;



        progress = new ProgressDialog(RegisterNewInquiryActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();*/

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            PartnerRequest request = new PartnerRequest();
            request.setPartnerType(type);
            Call<List<PartnerResponse>> call = api.getPartnerForDropdown(request);

            call.enqueue(new Callback<List<PartnerResponse>>() {
                @Override
                public void onResponse(Call<List<PartnerResponse>> call, Response<List<PartnerResponse>> response) {
                  /*  if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/

                    List<PartnerResponse> list = response.body();
                    if (list != null) {





                            SpinnerModel itemp11= new SpinnerModel();
                        itemp11.setId("-1");
                        itemp11.setName("--select " +type+"--");
                        arrListReference.add(itemp11);

                        for (int i = 0; i < list.size(); i++) {
                            SpinnerModel itemp = new SpinnerModel();
                            itemp.setId(String.valueOf(list.get(i).getPartnerId()));
                            itemp.setName(list.get(i).getPartner_Name());
                            arrListReference.add(itemp);
                        }


                        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(RegisterNewInquiryActivity.this, arrListReference);
                        sp_lead_refrence.setAdapter(enqiryAdapter);


                    }


                }

                @Override
                public void onFailure(Call<List<PartnerResponse>> call, Throwable t) {
                  /*  if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDistrictList(String stateId) {
       /* final ProgressDialog progress;



        progress = new ProgressDialog(CustomerAddressDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();*/

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);


            DistrictRequest request = new DistrictRequest();
            request.setStateID(Integer.parseInt(stateId));

            Call<List<DistrictResponse>> call = api.getDistricts(request);

            call.enqueue(new Callback<List<DistrictResponse>>() {
                @Override
                public void onResponse(Call<List<DistrictResponse>> call, Response<List<DistrictResponse>> response) {
                    /*if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/

                    List<DistrictResponse> list = response.body();
                    if (list != null) {


                        for (int i = 0; i < list.size(); i++) {
                            SpinnerModel itemp = new SpinnerModel();
                            itemp.setId(String.valueOf(list.get(i).getDistrictId()));
                            itemp.setName(list.get(i).getDistrict_Name());


                            arrListDistrict.add(itemp);
                        }


                        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(RegisterNewInquiryActivity.this, arrListDistrict);
                        sp_district.setAdapter(enqiryAdapter);


                        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                SpinnerModel spinnerModel = (SpinnerModel) sp_district.getSelectedItem();
                                String stateId = spinnerModel.getId();
                                arrListTahsil.clear();
                                SpinnerModel itemp1 = new SpinnerModel();
                                itemp1.setId("-1");
                                itemp1.setName("--Select Tehsil--");
                                arrListTahsil.add(itemp1);
                                SpinnerAdapter enqiryAdapter = new SpinnerAdapter(RegisterNewInquiryActivity.this, arrListTahsil);
                                sp_Tehsil.setAdapter(enqiryAdapter);
                                getTahsilList(stateId);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });


                    }


                }

                @Override
                public void onFailure(Call<List<DistrictResponse>> call, Throwable t) {
                  /*  if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getStateList() {
      /*  final ProgressDialog progress;



        progress = new ProgressDialog(CustomerAddressDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();*/

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);


            Call<List<StateResponse>> call = api.getState();

            call.enqueue(new Callback<List<StateResponse>>() {
                @Override
                public void onResponse(Call<List<StateResponse>> call, Response<List<StateResponse>> response) {
                   /* if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
*/
                    List<StateResponse> list = response.body();
                    if (list != null) {
                        SpinnerModel itemp1 = new SpinnerModel();
                        itemp1.setId("-1");
                        itemp1.setName("--Select State--");
                        arrListState.add(itemp1);

                        int index = 0;

                        for (int i = 0; i < list.size(); i++) {
                            SpinnerModel itemp = new SpinnerModel();
                            itemp.setId(String.valueOf(list.get(i).getStateId()));
                            itemp.setName(list.get(i).getState_Name());
                            if (list.get(i).getState_Name().equalsIgnoreCase("Rajasthan")) {
                                index = i + 1;
                            }

                            arrListState.add(itemp);
                        }


                        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(RegisterNewInquiryActivity.this, arrListState);
                        sp_state.setAdapter(enqiryAdapter);
                        sp_state.setSelection(index);

                        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                SpinnerModel spinnerModel = (SpinnerModel) sp_state.getSelectedItem();
                                String stateId = spinnerModel.getId();
                                arrListDistrict.clear();
                                SpinnerModel itemp1 = new SpinnerModel();
                                itemp1.setId("-1");
                                itemp1.setName("--Select District--");
                                arrListDistrict.add(itemp1);
                                SpinnerAdapter enqiryAdapter1 = new SpinnerAdapter(RegisterNewInquiryActivity.this, arrListDistrict);
                                sp_district.setAdapter(enqiryAdapter1);

                                arrListTahsil.clear();

                                SpinnerModel itemp11 = new SpinnerModel();
                                itemp11.setId("-1");
                                itemp11.setName("--Select Tehsil--");
                                arrListTahsil.add(itemp11);
                                SpinnerAdapter enqiryAdapter = new SpinnerAdapter(RegisterNewInquiryActivity.this, arrListTahsil);
                                sp_Tehsil.setAdapter(enqiryAdapter);
                                getDistrictList(stateId);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });


                    }


                }

                @Override
                public void onFailure(Call<List<StateResponse>> call, Throwable t) {
                    /*if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getTahsilList(String id) {
   /*     final ProgressDialog progress;



        progress = new ProgressDialog(CustomerAddressDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();*/

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);


            TahsilRequest request = new TahsilRequest();
            request.setDistrictId(Integer.parseInt(id));

            Call<List<TahsilResponse>> call = api.getTahsil(request);

            call.enqueue(new Callback<List<TahsilResponse>>() {
                @Override
                public void onResponse(Call<List<TahsilResponse>> call, Response<List<TahsilResponse>> response) {
                 /*   if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/

                    List<TahsilResponse> list = response.body();
                    if (list != null) {
                      /*  SpinnerModel itemp1 = new SpinnerModel();
                        itemp1.setId("-1");
                        itemp1.setName("--Select Tehsil--");
                        arrListTahsil.add(itemp1);*/

                        for (int i = 0; i < list.size(); i++) {
                            SpinnerModel itemp = new SpinnerModel();
                            itemp.setId(String.valueOf(list.get(i).getTehsilId()));
                            itemp.setName(list.get(i).getTehsil_Name());


                            arrListTahsil.add(itemp);
                        }


                        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(RegisterNewInquiryActivity.this, arrListTahsil);
                        sp_Tehsil.setAdapter(enqiryAdapter);


                        sp_Tehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                SpinnerModel spinnerModel = (SpinnerModel) sp_Tehsil.getSelectedItem();
                                String stateId = spinnerModel.getId();


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                        // if(!isLaunch) {
                        //    isLaunch=true;

                        //     }

                    }


                }

                @Override
                public void onFailure(Call<List<TahsilResponse>> call, Throwable t) {
                 /*   if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        TabActivity parentActivity;
        parentActivity = (TabActivity) RegisterNewInquiryActivity.this.getParent();
        parentActivity.onTabChange();
    }
}
