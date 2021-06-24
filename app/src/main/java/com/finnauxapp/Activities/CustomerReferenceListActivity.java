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

import com.finnauxapp.Adapter.CustomerReferenceAdapter;
import com.finnauxapp.Adapter.SpinnerAdapter;
import com.finnauxapp.ApiRequest.CustomerDetailRequest;
import com.finnauxapp.ApiRequest.ReferenceSaveRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.ReferenceResponse;
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

public class CustomerReferenceListActivity extends AppCompatActivity {

    private SessionManager session;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;
    private ArrayList<SpinnerModel> arrListCustomerType = new ArrayList<>();
    private int refId = 0;
    private CustomerDetailResponse customerDetailObject;
    private int applicationId;
    private TextView tvAppNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_kyc_document);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Customer Reference");
        ImageView ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CustomerReferenceListActivity.this,TabActivity.class);
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
        session = new SessionManager(CustomerReferenceListActivity.this);
        customerDetailObject = (CustomerDetailResponse) getIntent().getSerializableExtra("customerDetailObject");

        applicationId = getIntent().getIntExtra("applicationId", 0);
        TextView tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerName.setText("Customer Name : "+customerDetailObject.getCustomer_FirstName()+" " +customerDetailObject.getCustomer_LastName()+" ("+customerDetailObject.getCustomerType()+")");
        TextView tvAppNo = findViewById(R.id.tvAppNo);
        tvAppNo.setText("Application No : "+getIntent().getStringExtra("appNo"));

        SpinnerModel model1 = new SpinnerModel();
        model1.setId("1");
        model1.setName("Neighbour");
        arrListCustomerType.add(model1);
        SpinnerModel model2 = new SpinnerModel();
        model2.setId("2");
        model2.setName("Office");
        arrListCustomerType.add(model2);
        SpinnerModel model3 = new SpinnerModel();
        model3.setId("3");
        model3.setName("Friends");
        arrListCustomerType.add(model3);
        SpinnerModel model4 = new SpinnerModel();
        model4.setId("4");
        model4.setName("Colleague");
        arrListCustomerType.add(model4);
        SpinnerModel model5 = new SpinnerModel();
        model5.setId("5");
        model5.setName("Other");
        arrListCustomerType.add(model5);
        session = new SessionManager(CustomerReferenceListActivity.this);

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


        progress = new ProgressDialog(CustomerReferenceListActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);


            CustomerDetailRequest model = new CustomerDetailRequest();
            model.setCustomerId(customerDetailObject.getCustomerId());
            model.setApplicationId(applicationId);


            Call<List<ReferenceResponse>> call = api.getCustomerReferenceApp(model);

            call.enqueue(new Callback<List<ReferenceResponse>>() {
                @Override
                public void onResponse(Call<List<ReferenceResponse>> call, Response<List<ReferenceResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<ReferenceResponse> list = response.body();

                    if(list!=null) {

                        CustomerReferenceAdapter adpter = new CustomerReferenceAdapter(CustomerReferenceListActivity.this, list);
                        recycler_view.setAdapter(adpter);
                    }
                }

                @Override
                public void onFailure(Call<List<ReferenceResponse>> call, Throwable t) {
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

    public void addEditMember(final ReferenceResponse item) {
        AlertDialog.Builder dialogBuilder;
        final AlertDialog alertDialog;

        dialogBuilder  = new AlertDialog.Builder(CustomerReferenceListActivity.this);
        final View dialog = getLayoutInflater().inflate(R.layout.dialog_add_customer_reference, null);

        dialogBuilder.setView(dialog);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

      /*  final Dialog dialog = new Dialog(CustomerReferenceListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_customer_reference);*/
        final Spinner sp_Relation = dialog.findViewById(R.id.sp_Relation);
        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerReferenceListActivity.this, arrListCustomerType);
        sp_Relation.setAdapter(enqiryAdapter);
        final EditText edPersonName = dialog.findViewById(R.id.edPersonName);
        final EditText edAddress = dialog.findViewById(R.id.edAddress);
        final EditText edContactNumber = dialog.findViewById(R.id.edContactNumber);
        final EditText edReferenceFrom = dialog.findViewById(R.id.edReferenceFrom);
        final EditText edComment = dialog.findViewById(R.id.edComment);
        edReferenceFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBirthDayPicker(edReferenceFrom);
            }
        });

        Button btn_save = dialog.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(edPersonName.getText().toString().trim().isEmpty() || edAddress.getText().toString().trim().isEmpty() || edContactNumber.getText().toString().trim().isEmpty() || edReferenceFrom.getText().toString().trim().isEmpty() || edComment.getText().toString().trim().isEmpty())
                {
                    WebUtility.showOkDialog(CustomerReferenceListActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "All Fields are mandatory!");
                }
                else if (edContactNumber.getText().toString().trim().length() < 10) {
                    WebUtility.showOkDialog(CustomerReferenceListActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Contact no. not valid! ");
                }
                else if (edReferenceFrom.getText().toString().trim().length() < 4) {
                    WebUtility.showOkDialog(CustomerReferenceListActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Reference from (Yr) not valid! ");
                }

                else {
                    alertDialog.dismiss();
                    SpinnerModel md = (SpinnerModel) sp_Relation.getSelectedItem();

                    saveWebServiceREST(md.getName(), edPersonName.getText().toString(), edAddress.getText().toString(), edContactNumber.getText().toString(), edReferenceFrom.getText().toString(), edComment.getText().toString());
                }
                }
        });

        if (item != null) {
            refId = item.getRefId();
            edPersonName.setText(item.getPersonName());
            edAddress.setText(item.getPersonAddress());
            edContactNumber.setText(item.getContactNo());
            edReferenceFrom.setText(String.format("%d", item.getPersonKnowYear()));
            edComment.setText(item.getPersonComments());

            for (int i = 0; i < arrListCustomerType.size(); i++) {
                if (item.getReferenceType().equalsIgnoreCase(arrListCustomerType.get(i).getName())) {
                    sp_Relation.setSelection(i);
                    break;
                }
            }

        } else {
            refId = 0;
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
    public void saveWebServiceREST(String ref, String nm, String add, String cont, String reffrom, String coment) {
        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerReferenceListActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            //   SpinnerModel spinnerModel=(SpinnerModel)sp_loan_type.getSelectedItem();
            ReferenceSaveRequest model = new ReferenceSaveRequest();
            model.setRefId(refId);
            model.setApplicationId(applicationId);
            model.setCustomerId(customerDetailObject.getCustomerId());
            model.setLoginUserId(session.getUserId());
            model.setPersonName(nm);
            model.setPersonAddress(add);
            model.setReferenceType(ref);
            model.setContactNo(cont);
            model.setPersonKnowYear(Integer.parseInt(reffrom));
            model.setPersonComments(coment);


            Call<List<AcceptRejectResponse>> call = api.saveCustomerReferenceApp(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        String msg = loginResponse.get(0).getMSG();
                        Toast.makeText(CustomerReferenceListActivity.this, msg, Toast.LENGTH_LONG).show();
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
}
