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
import com.finnauxapp.Adapter.SpinnerAdapter;
import com.finnauxapp.ApiRequest.CustomerMemberRequest;
import com.finnauxapp.ApiRequest.CustomerRegMainRequest;
import com.finnauxapp.ApiRequest.SaveCustomerMemberBaseRequest;
import com.finnauxapp.ApiRequest.SaveCustomerMemberRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.CustomerMemberListResponse;
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

public class FamilyMemberListActivity extends AppCompatActivity {

    private SessionManager session;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;
    private CustomerDetailResponse customerDetailObject;
    private int applicationId;
    private TextView tvAppNo;
    private ArrayList<SpinnerModel> arrListCustomerType=new ArrayList<>();
    String gender="M";
    private int memberId=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_kyc_document);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Customer Family Member");
        ImageView ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(FamilyMemberListActivity.this,TabActivity.class);
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
        session=new SessionManager(FamilyMemberListActivity.this);
        customerDetailObject = (CustomerDetailResponse) getIntent().getSerializableExtra("customerDetailObject");
        applicationId = getIntent().getIntExtra("applicationId", 0);



        SpinnerModel model1=new SpinnerModel();
        model1.setId("1");
        model1.setName("Father");
        arrListCustomerType.add(model1);
        SpinnerModel model2=new SpinnerModel();
        model2.setId("2");
        model2.setName("Mother");
        arrListCustomerType.add(model2);
        SpinnerModel model3=new SpinnerModel();
        model3.setId("3");
        model3.setName("Spouse");
        arrListCustomerType.add(model3);
        SpinnerModel model4=new SpinnerModel();
        model4.setId("4");
        model4.setName("Brother");
        arrListCustomerType.add(model4);
        SpinnerModel model5=new SpinnerModel();
        model5.setId("5");
        model5.setName("Sister");
        arrListCustomerType.add(model5);
        SpinnerModel model6=new SpinnerModel();
        model6.setId("6");
        model6.setName("Son");
        arrListCustomerType.add(model6);
        SpinnerModel model7=new SpinnerModel();
        model7.setId("7");
        model7.setName("Daughter");
        arrListCustomerType.add(model7);



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



        progress = new ProgressDialog(FamilyMemberListActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            CustomerMemberRequest model=new CustomerMemberRequest();
            model.setCustomerId(customerDetailObject.getCustomerId());
            // model.setInqStatus("Assigned");


            Call<List<CustomerMemberListResponse>> call = api.getCustomerMemberApp(model);

            call.enqueue(new Callback<List<CustomerMemberListResponse>>() {
                @Override
                public void onResponse(Call<List<CustomerMemberListResponse>> call, Response<List<CustomerMemberListResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<CustomerMemberListResponse> list=response.body();

                   if(list!=null) {

                       CustomerFamilyAdapter adpter = new CustomerFamilyAdapter(FamilyMemberListActivity.this, list);
                       recycler_view.setAdapter(adpter);
                   }
                }

                @Override
                public void onFailure(Call<List<CustomerMemberListResponse>> call, Throwable t) {
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

    public void addEditMember(final CustomerMemberListResponse item) {

        AlertDialog.Builder dialogBuilder;
        final AlertDialog alertDialog;

        dialogBuilder  = new AlertDialog.Builder(FamilyMemberListActivity.this);
        final View dialog = getLayoutInflater().inflate(R.layout.dialog_add_family_member, null);

        dialogBuilder.setView(dialog);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
      /*  final Dialog dialog = new Dialog(FamilyMemberListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_family_member);*/
        final Spinner sp_Relation = dialog.findViewById(R.id.sp_Relation);
        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(FamilyMemberListActivity.this, arrListCustomerType);
        sp_Relation.setAdapter(enqiryAdapter);
        final EditText edMemberName = dialog.findViewById(R.id.edMemberName);
        final EditText edAge = dialog.findViewById(R.id.edAge);
        final EditText edOccupation = dialog.findViewById(R.id.edOccupation);
        final EditText edMontlhyEarn = dialog.findViewById(R.id.edMontlhyEarn);
        final EditText edContactNo = dialog.findViewById(R.id.edContactNo);
        RadioGroup rbGender = dialog.findViewById(R.id.rbGender);
       RadioButton rbMale=dialog.findViewById(R.id.rbMale);
        RadioButton rbFemale= dialog.findViewById(R.id.rbFemale);

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

                if(edMemberName.getText().toString().trim().isEmpty() || edAge.getText().toString().trim().isEmpty() || edOccupation.getText().toString().trim().isEmpty() || edMontlhyEarn.getText().toString().trim().isEmpty() )
                {
                    WebUtility.showOkDialog(FamilyMemberListActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "All Fields are mandatory!");
                }

              else {
                    alertDialog.dismiss();
                    SpinnerModel md = (SpinnerModel) sp_Relation.getSelectedItem();


                  saveWebServiceREST(gender,md.getName(),edMemberName.getText().toString(),edAge.getText().toString(),edOccupation.getText().toString(),edMontlhyEarn.getText().toString(),edContactNo.getText().toString());
            }}
        });

        if (item != null) {
            memberId=item.getMemberId();
            edMemberName.setText(item.getMemberName());
            String genderAge = item.getMemberGenderAge();
            String[] arr = genderAge.split("\\s+");
            if(arr[0].equalsIgnoreCase("M")){
                rbMale.setChecked(true);
            }
            else{
                rbFemale.setChecked(true);
            }

            edAge.setText(arr[1]);
            edOccupation.setText(item.getMemberOccupationType());
          edMontlhyEarn.setText(String.format("%d", item.getMemberHowMuchEarn()));
          edContactNo.setText(item.getMemberContactNumber());

            for (int i = 0; i < arrListCustomerType.size(); i++) {
                if (item.getMemberReletion().equalsIgnoreCase(arrListCustomerType.get(i).getName())) {
                    sp_Relation.setSelection(i);
                    break;
                }
            }

        }
        else{
            memberId=0;
        }





        alertDialog.show();
      //  Window window = dialog.getWindow();
        //window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void saveWebServiceREST(String gender, String rel, String s1, String s2, String s3, String s4, String contact) {
        final ProgressDialog progress;



        progress = new ProgressDialog(FamilyMemberListActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
     //   SpinnerModel spinnerModel=(SpinnerModel)sp_loan_type.getSelectedItem();
            SaveCustomerMemberRequest model=new SaveCustomerMemberRequest();
           model.setMemberId(memberId);
            model.setMemberRelation(rel);
            model.setCustomerId(customerDetailObject.getCustomerId());
            model.setLoginUserId(session.getUserId());
            model.setMemberAge(Integer.parseInt(s2));
            model.setMemberName(s1);
            model.setMemberGender(gender);
            model.setMemberAddress("");
            model.setMemberOccupation(s3);
            model.setMemberContactNumber(contact);
            model.setMemberWorkAddress("");
            model.setMember_HowMuchEarn(Integer.parseInt(s4));

            SaveCustomerMemberBaseRequest request =new SaveCustomerMemberBaseRequest();
            request.setCustomerMember(model);

          String res=new Gson().toJson(request);
            //Log.d("my_json",res);



            CustomerRegMainRequest customerRegMainRequest=new CustomerRegMainRequest();
          customerRegMainRequest.setJson(res);

            Call<List<AcceptRejectResponse>> call = api.saveCustomerMemberApp(customerRegMainRequest);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if(loginResponse!=null){
                        String msg= loginResponse.get(0).getMSG();
                        Toast.makeText(FamilyMemberListActivity.this,msg,Toast.LENGTH_LONG).show();
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
