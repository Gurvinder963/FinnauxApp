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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Adapter.CustomerExpenditureAdapter;
import com.finnauxapp.Adapter.SpinnerAdapter;
import com.finnauxapp.ApiRequest.CustomerDetailRequest;
import com.finnauxapp.ApiRequest.ExpenditureSaveRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.CustomerExpenditureResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomerExpenditureListActivity extends AppCompatActivity {

    private SessionManager session;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;
    public int expenditureId=0;
    private CustomerDetailResponse customerDetailObject;
    private int applicationId;
    private TextView tvAppNo;
    private ArrayList<SpinnerModel> arrListCustomerType=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_kyc_document);

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
                Intent intent =new Intent(CustomerExpenditureListActivity.this,TabActivity.class);
                startActivity(intent);
            }
        });
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Customer Expenditure");
        SpinnerModel model1=new SpinnerModel();
        model1.setId("1");
        model1.setName("Monthly");
        arrListCustomerType.add(model1);
        SpinnerModel model2=new SpinnerModel();
        model2.setId("2");
        model2.setName("Quarterly");
        arrListCustomerType.add(model2);
        SpinnerModel model3=new SpinnerModel();
        model3.setId("3");
        model3.setName("Half-Yearly");
        arrListCustomerType.add(model3);
        SpinnerModel model4=new SpinnerModel();
        model4.setId("4");
        model4.setName("Yearly");
        arrListCustomerType.add(model4);

        session=new SessionManager(CustomerExpenditureListActivity.this);
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

    public void addEditMember(final CustomerExpenditureResponse item) {
        AlertDialog.Builder dialogBuilder;
        final AlertDialog alertDialog;

        dialogBuilder  = new AlertDialog.Builder(CustomerExpenditureListActivity.this);
        final View dialog = getLayoutInflater().inflate(R.layout.dialog_add_expenditure, null);

        dialogBuilder.setView(dialog);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

       /* final Dialog dialog = new Dialog(CustomerExpenditureListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_expenditure);*/
        final Spinner sp_expense_frequency = dialog.findViewById(R.id.sp_expense_frequency);
      SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerExpenditureListActivity.this, arrListCustomerType);
      sp_expense_frequency.setAdapter(enqiryAdapter);
        final EditText edExpenseHead = dialog.findViewById(R.id.edExpenseHead);
        final EditText edExpenseAmount = dialog.findViewById(R.id.edExpenseAmount);


        Button btn_save = dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( edExpenseHead.getText().toString().trim().isEmpty() || edExpenseAmount.getText().toString().trim().isEmpty())
                {
                    WebUtility.showOkDialog(CustomerExpenditureListActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "All Fields are mandatory!");
                }
               else {
                    alertDialog.dismiss();
                      SpinnerModel md= (SpinnerModel) sp_expense_frequency.getSelectedItem();

                    saveWebServiceREST(edExpenseHead.getText().toString(), edExpenseAmount.getText().toString(),md.getName());
                }
               }
        });

        if (item != null) {
           expenditureId=item.getCustomerExpenditureId();
            edExpenseHead.setText(item.getExpenditureType());
            edExpenseAmount.setText(String.format("%d", item.getTotalExpenditureAmount()));



       //     edOccupation.setText(item.getMemberOccupationType());
       //     edMontlhyEarn.setText(String.format("%d", item.getMemberHowMuchEarn()));

           for (int i = 0; i < arrListCustomerType.size(); i++) {
                if (item.getRemark().equalsIgnoreCase(arrListCustomerType.get(i).getName())) {
                    sp_expense_frequency.setSelection(i);
                    break;
                }
            }

        }
        else{
            expenditureId=0;
        }




        alertDialog.show();
      //  Window window = dialog.getWindow();
        //window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    public void callWebServiceREST() {
        final ProgressDialog progress;



        progress = new ProgressDialog(CustomerExpenditureListActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            CustomerDetailRequest model=new CustomerDetailRequest();
            model.setCustomerId(customerDetailObject.getCustomerId());
            model.setApplicationId(applicationId);
            // model.setInqStatus("Assigned");


            Call<List<CustomerExpenditureResponse>> call = api.getCustomerExpenditureApp(model);

            call.enqueue(new Callback<List<CustomerExpenditureResponse>>() {
                @Override
                public void onResponse(Call<List<CustomerExpenditureResponse>> call, Response<List<CustomerExpenditureResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<CustomerExpenditureResponse> list=response.body();



                    if(list!=null) {
                        CustomerExpenditureAdapter adpter = new CustomerExpenditureAdapter(CustomerExpenditureListActivity.this, list);
                        recycler_view.setAdapter(adpter);
                    }
                }

                @Override
                public void onFailure(Call<List<CustomerExpenditureResponse>> call, Throwable t) {
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
    public void saveWebServiceREST(String r1, String r2, String name) {
        final ProgressDialog progress;



        progress = new ProgressDialog(CustomerExpenditureListActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            //   SpinnerModel spinnerModel=(SpinnerModel)sp_loan_type.getSelectedItem();
            ExpenditureSaveRequest model=new ExpenditureSaveRequest();
           model.setExpenditueId(expenditureId);
            model.setApplicationId(applicationId);
         model.setCustomerId(customerDetailObject.getCustomerId());
            model.setLoginUserId(session.getUserId());
            model.setExpenditureAmount(Integer.parseInt(r2));
            model.setExpenditureType(r1);
            model.setRemark(name);



            Call<List<AcceptRejectResponse>> call = api.saveCustomerExpenditureApp(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if(loginResponse!=null){
                        String msg= loginResponse.get(0).getMSG();
                        Toast.makeText(CustomerExpenditureListActivity.this,msg,Toast.LENGTH_LONG).show();
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
