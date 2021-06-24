package com.finnauxapp.Activities;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Adapter.CustomerExistingLoanAdapter;
import com.finnauxapp.Adapter.SpinnerAdapter;
import com.finnauxapp.ApiRequest.CustomerDetailRequest;
import com.finnauxapp.ApiRequest.ExistingLoanSaveRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.ExistingLoanResponse;
import com.finnauxapp.ApiResponse.ProductTypeResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomerExistingLoanActivity extends AppCompatActivity {

    private SessionManager session;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;

    private CustomerDetailResponse customerDetailObject;
    private int applicationId;
    private TextView tvAppNo;
    ArrayList<SpinnerModel> arrListProduct=new ArrayList<>();
    private ArrayList<SpinnerModel> arrListCustomerType=new ArrayList<>();
    private ArrayList<SpinnerModel> arrListLoanStatus=new ArrayList<>();
    private int celId=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_kyc_document);

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Customer Existing Loan");

        SpinnerModel model1=new SpinnerModel();
        model1.setId("1");
        model1.setName("Running");
        arrListLoanStatus.add(model1);
        SpinnerModel model2=new SpinnerModel();
        model2.setId("2");
        model2.setName("Completed");
        arrListLoanStatus.add(model2);
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
                Intent intent =new Intent(CustomerExistingLoanActivity.this,TabActivity.class);
                startActivity(intent);
            }
        });


        session=new SessionManager(CustomerExistingLoanActivity.this);
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
        getProductList();
    }
    @Override
    protected void onResume() {
        super.onResume();
        callWebServiceREST();
    }
    public void callWebServiceREST() {
        final ProgressDialog progress;



        progress = new ProgressDialog(CustomerExistingLoanActivity.this);
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


            Call<List<ExistingLoanResponse>> call = api.getCustomerExistingLoanApp(model);

            call.enqueue(new Callback<List<ExistingLoanResponse>>() {
                @Override
                public void onResponse(Call<List<ExistingLoanResponse>> call, Response<List<ExistingLoanResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<ExistingLoanResponse> list=response.body();

                    if(list!=null) {

                        CustomerExistingLoanAdapter adpter = new CustomerExistingLoanAdapter(CustomerExistingLoanActivity.this, list);
                        recycler_view.setAdapter(adpter);
                    }
                }

                @Override
                public void onFailure(Call<List<ExistingLoanResponse>> call, Throwable t) {
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

    public void addEditMember(final ExistingLoanResponse item) {
        AlertDialog.Builder dialogBuilder;
        final AlertDialog alertDialog;

        dialogBuilder  = new AlertDialog.Builder(CustomerExistingLoanActivity.this);
        final View dialog = getLayoutInflater().inflate(R.layout.dialog_add_exisiting_loan, null);

        dialogBuilder.setView(dialog);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

   /*     final Dialog dialog = new Dialog(CustomerExistingLoanActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_exisiting_loan);*/
        final Spinner sp_loan_type = dialog.findViewById(R.id.sp_loan_type);
        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerExistingLoanActivity.this, arrListProduct);
        sp_loan_type.setAdapter(enqiryAdapter);

        final Spinner spLoanStatus = dialog.findViewById(R.id.spLoanStatus);
        SpinnerAdapter enqiryAdapter1 = new SpinnerAdapter(CustomerExistingLoanActivity.this, arrListLoanStatus);
        spLoanStatus.setAdapter(enqiryAdapter1);


        final EditText edLoanTakenFrom = dialog.findViewById(R.id.edLoanTakenFrom);
        final EditText edLoanAmount = dialog.findViewById(R.id.edLoanAmount);
        final EditText edLoanTakenYr = dialog.findViewById(R.id.edLoanTakenYr);
        edLoanTakenYr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBirthDayPicker(edLoanTakenYr);
            }
        });
        final EditText edLoanDuration = dialog.findViewById(R.id.edLoanDuration);
        final EditText edOutStandingAmount = dialog.findViewById(R.id.edOutStandingAmount);
        final EditText edMonthlyEMI = dialog.findViewById(R.id.edMonthlyEMI);


        Button btn_save = dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SpinnerModel md = (SpinnerModel) sp_loan_type.getSelectedItem();

                if(md.getName().equalsIgnoreCase("Select Loan Product") || edLoanTakenFrom.getText().toString().trim().isEmpty() || edLoanAmount.getText().toString().trim().isEmpty() || edLoanTakenYr.getText().toString().trim().isEmpty() || edLoanDuration.getText().toString().trim().isEmpty() || edOutStandingAmount.getText().toString().trim().isEmpty() || edMonthlyEMI.getText().toString().trim().isEmpty())
                {
                    WebUtility.showOkDialog(CustomerExistingLoanActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "All Fields are mandatory!");
                }
                else{


                    alertDialog.dismiss();
                   SpinnerModel sp= (SpinnerModel)spLoanStatus.getSelectedItem();

                saveWebServiceREST(md.getName(), sp.getName(), edLoanTakenFrom.getText().toString(), edLoanAmount.getText().toString(), edLoanTakenYr.getText().toString(), edLoanDuration.getText().toString(), edOutStandingAmount.getText().toString(), edMonthlyEMI.getText().toString());
                } }
        });

        if (item != null) {
            celId = item.getExistingLoanId();
            for(int i=0;i<arrListLoanStatus.size();i++){
                if(arrListLoanStatus.get(i).getName().equalsIgnoreCase(item.getLoanStatus())){
                    spLoanStatus.setSelection(i);
                    break;
                }
            }

            edLoanTakenFrom.setText(item.getLoanTakenFrom());
            edLoanAmount.setText(String.format("%s", item.getLoanAmount()));
            edLoanTakenYr.setText(String.format("%s", item.getLoanTakenOn()));
            edLoanDuration.setText(String.format("%d", item.getLoanDuration_Month()));
            edMonthlyEMI.setText(String.format("%s", item.getMonthlyEMI()));
            edOutStandingAmount.setText(String.format("%s", item.getCurrentOutStandingAmount()));
           for (int i = 0; i < arrListProduct.size(); i++) {
                if (item.getLoanType().equalsIgnoreCase(arrListProduct.get(i).getName())) {
                    sp_loan_type.setSelection(i);
                    break;
                }
            }

        } else {
            celId = 0;
        }


        alertDialog.show();
      //  Window window = dialog.getWindow();
        //window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    private void showBirthDayPicker(final EditText edReferenceFrom) {
        int year = Calendar.getInstance().get(Calendar.YEAR);

        final Dialog birthDayDialog = new Dialog(this);
        birthDayDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        birthDayDialog.setContentView(R.layout.dialog_birthday);

        Button okBtn = birthDayDialog.findViewById(R.id.btn_ok);
        Button cancelBtn = birthDayDialog.findViewById(R.id.btn_cancel);

        final NumberPicker np = birthDayDialog.findViewById(R.id.birthdayPicker);
        np.setMinValue(year - 100);
        np.setMaxValue(year);
        np.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        // setDivider(np, android.R.color.white);
        np.setWrapSelectorWheel(false);
        np.setValue(year);
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {

            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edReferenceFrom.setText(String.valueOf(np.getValue()));
                birthDayDialog.dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                birthDayDialog.dismiss();
            }
        });

        birthDayDialog.show();
    }
    public void saveWebServiceREST(String loanType, String loanStatus, String takenFrom, String amount, String takenYear, String duration, String outstandingAmount, String monthlyEmi) {
        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerExistingLoanActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            //   SpinnerModel spinnerModel=(SpinnerModel)sp_loan_type.getSelectedItem();
            ExistingLoanSaveRequest model = new ExistingLoanSaveRequest();
           model.setCELId(celId);
            model.setApplicationId(applicationId);
            model.setCustomerId(customerDetailObject.getCustomerId());
            model.setLoginUserId(session.getUserId());
            model.setLoanType(loanType);
            model.setLoanStatus(loanStatus);
            model.setLoanAmount(Float.parseFloat(amount));
            model.setLoanDuration(Integer.parseInt(duration));
            model.setOutstandingAmount(Float.parseFloat(outstandingAmount));
            model.setMonthlyEMI(Float.parseFloat(monthlyEmi));
            model.setLoanFirmName(takenFrom);
            model.setLoanTakeYear(takenYear);


            Call<List<AcceptRejectResponse>> call = api.saveCustomerExistingLoanApp(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        String msg = loginResponse.get(0).getMSG();
                        Toast.makeText(CustomerExistingLoanActivity.this, msg, Toast.LENGTH_LONG).show();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getProductList() {
        final ProgressDialog progress;



        progress = new ProgressDialog(CustomerExistingLoanActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);



            Call<ArrayList<ProductTypeResponse>> call = api.getLoadProductType();

            call.enqueue(new Callback<ArrayList<ProductTypeResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<ProductTypeResponse>> call, Response<ArrayList<ProductTypeResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    ArrayList<ProductTypeResponse> list = response.body();
                    if(list!=null){
                        SpinnerModel itempe = new SpinnerModel();
                        itempe.setId("-1");
                        itempe.setName("Select Loan Product");
                        arrListProduct.add(itempe);

                        for(int i=0;i<list.size();i++){
                            SpinnerModel itemp = new SpinnerModel();
                            itemp.setId(String.valueOf(list.get(i).getProductId()));
                            itemp.setName(list.get(i).getProduct_Name());
                            arrListProduct.add(itemp);
                        }






                    }




                }

                @Override
                public void onFailure(Call<ArrayList<ProductTypeResponse>> call, Throwable t) {
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

