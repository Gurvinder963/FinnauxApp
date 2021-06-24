package com.finnauxapp.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Adapter.CustomerExistingLoanAdapter;
import com.finnauxapp.Adapter.QuestionAnswerAdapter;
import com.finnauxapp.ApiRequest.CustomerDetailRequest;
import com.finnauxapp.ApiRequest.CustomerIncomeBaseRequest;
import com.finnauxapp.ApiRequest.CustomerRegMainRequest;
import com.finnauxapp.ApiRequest.SaveAnswerBaseModel;
import com.finnauxapp.ApiRequest.SaveAnswerChildModel;
import com.finnauxapp.ApiRequest.SaveIncomeDetailRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.ExistingLoanResponse;
import com.finnauxapp.ApiResponse.QuestionAnswerResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class QuestionAnswerActivity extends AppCompatActivity {
    private SessionManager session;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;

    private CustomerDetailResponse customerDetailObject;
    private int applicationId;
    private QuestionAnswerAdapter adpter;
    boolean isHindi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        TextView tvTitle = findViewById(R.id.tvTitle);
       Switch simpleSwitch = findViewById(R.id.simpleSwitch);
       simpleSwitch.setVisibility(View.VISIBLE);
        simpleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    isHindi=true;
                }
                else{
                    isHindi=false;
                }
                adpter.setQuestionLang(isHindi);
                adpter.notifyDataSetChanged();
            }
        });
        tvTitle.setText("Customer FI Questions");

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
                Intent intent =new Intent(QuestionAnswerActivity.this,TabActivity.class);
                startActivity(intent);
            }
        });


        session=new SessionManager(QuestionAnswerActivity.this);
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

        Button btn_Submit = findViewById(R.id.btn_Submit);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* CustomerIncomeBaseRequest customerIncomeBaseRequest = new CustomerIncomeBaseRequest();
                customerIncomeBaseRequest.setCustomerIncome(model);

                String res = new Gson().toJson(customerIncomeBaseRequest);
                Log.d("my_json", res);


                CustomerRegMainRequest customerRegMainRequest = new CustomerRegMainRequest();
                customerRegMainRequest.setJson(res);*/

               if(adpter!=null && adpter.getList().size()>0) {
                   saveWebServiceREST();
               }
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



        progress = new ProgressDialog(QuestionAnswerActivity.this);
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


            Call<List<QuestionAnswerResponse>> call = api.getLOS_GetFIQuestionsApp(model);

            call.enqueue(new Callback<List<QuestionAnswerResponse>>() {
                @Override
                public void onResponse(Call<List<QuestionAnswerResponse>> call, Response<List<QuestionAnswerResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<QuestionAnswerResponse> list=response.body();

                   if(list!=null) {


                       adpter = new QuestionAnswerAdapter(QuestionAnswerActivity.this, list,isHindi);
                       recycler_view.setAdapter(adpter);
                   }
                }

                @Override
                public void onFailure(Call<List<QuestionAnswerResponse>> call, Throwable t) {
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

    public void saveWebServiceREST() {
        final ProgressDialog progress;


        progress = new ProgressDialog(QuestionAnswerActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            //   SpinnerModel spinnerModel=(SpinnerModel)sp_loan_type.getSelectedItem();


            List<QuestionAnswerResponse> list = adpter.getList();
            ArrayList<SaveAnswerChildModel> FIAnswer=new ArrayList<>();
          for(int i=0;i<list.size();i++){

              SaveAnswerChildModel saveAnswerChildModel=new SaveAnswerChildModel();
              saveAnswerChildModel.setFI_LoginUserId(session.getUserId());
              saveAnswerChildModel.setFI_ApplicationId(applicationId);
              saveAnswerChildModel.setFI_CustomerId(customerDetailObject.getCustomerId());
              saveAnswerChildModel.setFI_Answer(list.get(i).getFIAnswer());
              saveAnswerChildModel.setFI_QuestionId(list.get(i).getQueId());
              FIAnswer.add(saveAnswerChildModel);

          }

            SaveAnswerBaseModel saveAnswerBaseModel=new SaveAnswerBaseModel();
            saveAnswerBaseModel.setFIAnswer(FIAnswer);






            String res = new Gson().toJson(saveAnswerBaseModel);
            Log.d("my_json", res);


            CustomerRegMainRequest customerRegMainRequest = new CustomerRegMainRequest();
            customerRegMainRequest.setJson(res);

            Call<List<AcceptRejectResponse>> call = api.saveCustomerFIAnswerApp(customerRegMainRequest);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        String msg = loginResponse.get(0).getMSG();
                        Toast.makeText(QuestionAnswerActivity.this, msg, Toast.LENGTH_LONG).show();
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
}
