package com.finnauxapp.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Adapter.MyApplicationAdapter;
import com.finnauxapp.Adapter.MyApplicationDetailAdapter;
import com.finnauxapp.Adapter.SpinnerAdapter;
import com.finnauxapp.ApiRequest.ApplicationDetailRequest;
import com.finnauxapp.ApiRequest.CompleteProcessAppRequest;
import com.finnauxapp.ApiRequest.DashBoardDataRequest;
import com.finnauxapp.ApiRequest.DeleteCustomerRequest;
import com.finnauxapp.ApiRequest.DeleteKYCRequest;
import com.finnauxapp.ApiRequest.LoginFeeRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.ApplicationDetailResponse;
import com.finnauxapp.ApiResponse.ApplicationItem1;
import com.finnauxapp.ApiResponse.ApplicationItem2;
import com.finnauxapp.ApiResponse.ApplicationListResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.BuildConfig;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyApplicationDetailActivity extends AppCompatActivity {

 //   private ApplicationListResponse applicationListResponse;
    private SessionManager session;
    private TextView tvName;
    private TextView tvInquiry;
    private TextView tvMobile;
    private TextView tvBranch;
    private TextView tvLoanType;
    private TextView tvAmount;
    private RecyclerView recycler_view;
    private LinearLayoutManager linearLayoutManager;
    private Button btn_Proceed;
    private RelativeLayout btn_add;
    ArrayList<SpinnerModel> arrListRecommendation=new ArrayList<>();
    private List<ApplicationItem2> item2;
    private List<ApplicationItem1> item1;
    private TextView tvLoginFee;
    private TextView tvLoginFeeTakenOn;
    private String TAG;
    private ArrayList<SpinnerModel> arrListCollectionType=new ArrayList<>();
    private Button btn_asset_detail;
    private String appNo;
    private int appId;
    private TextView tvPurpose;
    private TextView tvCollectionMode;
    private Button btn_financial_detail;
    private int branchId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




            setContentView(R.layout.screen_my_application_detail);

            ImageView ivBack = findViewById(R.id.ivBack);
            TextView tvTitle = findViewById(R.id.tvTitle);
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
                    Intent intent = new Intent(MyApplicationDetailActivity.this, TabActivity.class);
                    startActivity(intent);
                }
            });
            //  applicationListResponse = (ApplicationListResponse) getIntent().getSerializableExtra("data");

            appNo = getIntent().getStringExtra("appNo");
            appId = getIntent().getIntExtra("applicationId", 0);

            TAG = getIntent().getStringExtra("TAG");

            if(TAG.equalsIgnoreCase("Send Back Branch Applications")){
                tvTitle.setText("File Send Back to Branch");
            }
            else if(TAG.equalsIgnoreCase("My FI Applications")){
                tvTitle.setText("My FI Applications");
            }
            else if(TAG.equalsIgnoreCase("Completed Applications")){
            tvTitle.setText("Completed Applications");
            }
            else if(TAG.equalsIgnoreCase("Completed FI Applications")){
            tvTitle.setText("Completed FI Applications");
            }

            session = new SessionManager(MyApplicationDetailActivity.this);
            btn_asset_detail = findViewById(R.id.btn_asset_detail);
        btn_financial_detail = findViewById(R.id.btn_financial_detail);
        btn_financial_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyApplicationDetailActivity.this, GenerateApplicationNewActivity.class);
                intent.putExtra("applicationId", appId);
                intent.putExtra("branchId", branchId);
                intent.putExtra("branchName", tvBranch.getText().toString());
                intent.putExtra("purpose", tvPurpose.getText().toString());
                intent.putExtra("inquiryNo", tvInquiry.getText().toString());



                startActivity(intent);
            }
        });




            btn_asset_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyApplicationDetailActivity.this, AssetDetailActivity.class);
                    intent.putExtra("applicationId", appId);
                    intent.putExtra("appNo", appNo);
                    intent.putExtra("product", item1.get(0).getProduct());

                    startActivity(intent);
                }
            });

            SpinnerModel model0 = new SpinnerModel();
            model0.setId("0");
            model0.setName("--Select Recommendation--");
            arrListRecommendation.add(model0);
            SpinnerModel model1 = new SpinnerModel();
            model1.setId("1");
            model1.setName("Positive");
            arrListRecommendation.add(model1);
            SpinnerModel model2 = new SpinnerModel();
            model2.setId("2");
            model2.setName("Negative");
            arrListRecommendation.add(model2);
            SpinnerModel model3 = new SpinnerModel();
            model3.setId("3");
            model3.setName("Not Conducted");
            arrListRecommendation.add(model3);


            SpinnerModel model55 = new SpinnerModel();
            model55.setId("1");
            model55.setName("Cash");
            arrListCollectionType.add(model55);
            SpinnerModel model56 = new SpinnerModel();
            model56.setId("2");
            model56.setName("Bank");
            arrListCollectionType.add(model56);
            SpinnerModel model57 = new SpinnerModel();
            model57.setId("3");
            model57.setName("E-Gateway");
            arrListCollectionType.add(model57);

            recycler_view = findViewById(R.id.recyclerView);
            btn_Proceed = findViewById(R.id.btn_Proceed);
            tvLoginFee = findViewById(R.id.tvLoginFee);
            tvPurpose = findViewById(R.id.tvPurpose);
            tvCollectionMode = findViewById(R.id.tvCollectionMode);

            tvLoginFeeTakenOn = findViewById(R.id.tvLoginFeeTakenOn);
            btn_add = findViewById(R.id.btn_add);

            tvLoginFee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (tvLoginFee.getText().toString().equalsIgnoreCase("Login Fee : Pending")) {

                        double amount = item1.get(0).getLoginFeeAmount_Taken();
                        boolean isMandtory = item1.get(0).isLoginFee_IsMandtory();
                        if (amount < 1 && isMandtory) {

                            showLoginFeeDialog(item1.get(0).getLoginFeeAmount_ToBeTaken(), item1.get(0).getLoginFeeTax(),item1.get(0).getSGST(),item1.get(0).getCGST());
                        }
                    }
                }
            });
            if (TAG.equalsIgnoreCase("My FI Applications")) {
                btn_add.setVisibility(View.GONE);
            }
        else if(TAG.equalsIgnoreCase("Completed Applications")){
                btn_add.setVisibility(View.GONE);
                btn_Proceed.setVisibility(View.GONE);
        }
        else if(TAG.equalsIgnoreCase("Completed FI Applications")){
                btn_add.setVisibility(View.GONE);
                btn_Proceed.setVisibility(View.GONE);
        }
       /* else if(TAG.equalsIgnoreCase("Send Back Branch Applications")){
            btn_add.setVisibility(View.GONE);
        }*/
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MyApplicationDetailActivity.this, CustomerRegistrationActivity.class);
                    intent.putExtra("applicationId", appId);
                    intent.putExtra("appNo", appNo);
                    intent.putExtra("from", "add");
                    intent.putExtra("product",item1.get(0).getProduct());
                    intent.putExtra("loanAmount",item1.get(0).getLoanAmount()+"");
                    intent.putExtra("TAG",TAG);


                    startActivity(intent);
                }
            });


            btn_Proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  completeProcessWebServiceREST();
                    ValidateAppApi(appId);

                }
            });


            linearLayoutManager = new LinearLayoutManager(this);
            recycler_view.setLayoutManager(linearLayoutManager);
            recycler_view.getLayoutManager().setAutoMeasureEnabled(true);
            recycler_view.setNestedScrollingEnabled(false);
            recycler_view.setHasFixedSize(false);
            tvName = findViewById(R.id.tvName);
            tvInquiry = findViewById(R.id.tvInquiry);
            tvMobile = findViewById(R.id.tvMobile);
            tvBranch = findViewById(R.id.tvBranch);
            tvLoanType = findViewById(R.id.tvLoanType);
            tvAmount = findViewById(R.id.tvAmount);


    }

    @Override
    protected void onResume() {
        super.onResume();

            callWebServiceREST();

    }

    public void showLoginFeeDialog(final double loginFeeAmount_taken, final double tax, final double sgst, final double cgst){

        AlertDialog.Builder dialogBuilder;
        final AlertDialog alertDialog;

        dialogBuilder  = new AlertDialog.Builder(MyApplicationDetailActivity.this);
        final View dialog1 = getLayoutInflater().inflate(R.layout.dialog_login_fee, null);

        dialogBuilder.setView(dialog1);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);



        final EditText edLoginFee = dialog1.findViewById(R.id.edLoginFee);
        final EditText edRemark = dialog1.findViewById(R.id.edRemark);
        final TextView tvTaxAmount = dialog1.findViewById(R.id.tvTaxAmount);
        final TextView tvSGSTAmount = dialog1.findViewById(R.id.tvSGSTAmount);
        final TextView tvCGSTAmount = dialog1.findViewById(R.id.tvCGSTAmount);
        final TextView tvTotalFeeAmount = dialog1.findViewById(R.id.tvTotalFeeAmount);
        final Spinner sp_collection_mode = dialog1.findViewById(R.id.sp_collection_mode);
        final LinearLayout llReference = dialog1.findViewById(R.id.llReference);
        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(MyApplicationDetailActivity.this, arrListCollectionType);
        sp_collection_mode.setAdapter(enqiryAdapter);
        sp_collection_mode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerModel model= (SpinnerModel) sp_collection_mode.getSelectedItem();
                if(!model.getName().equalsIgnoreCase("Cash")){
                    llReference.setVisibility(View.VISIBLE);
                }
                else{
                    llReference.setVisibility(View.GONE);
                    edRemark.setText("");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edLoginFee.setText(String.valueOf(loginFeeAmount_taken));

        edLoginFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()>0){

                String var=s.toString();
                Double feeAmount=Double.parseDouble(var);
                final double calTax=(feeAmount*tax)/100;
                final double total=feeAmount+calTax;
                tvTaxAmount.setText(calTax +"  ("+tax+"%)");
                tvTotalFeeAmount.setText(String.valueOf(total));

                    final double sgstTax=(feeAmount*sgst)/100;
                    tvSGSTAmount.setText(sgstTax +"  ("+sgst+"%)");

                    final double cgstTax=(feeAmount*cgst)/100;
                    tvCGSTAmount.setText(cgstTax +"  ("+cgst+"%)");


                }
                else{
                    tvTaxAmount.setText("");
                    tvTotalFeeAmount.setText("");
                    tvSGSTAmount.setText("");
                    tvCGSTAmount.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        final double calTax=(loginFeeAmount_taken*tax)/100;
        final double total=loginFeeAmount_taken+calTax;
        tvTaxAmount.setText(calTax +"  ("+tax+"%)");
        tvTotalFeeAmount.setText(String.valueOf(total));

        final double sgstTax=(loginFeeAmount_taken*sgst)/100;
        tvSGSTAmount.setText(sgstTax +"  ("+sgst+"%)");

        final double cgstTax=(loginFeeAmount_taken*cgst)/100;
        tvCGSTAmount.setText(cgstTax +"  ("+cgst+"%)");

        Button btnOk = dialog1.findViewById(R.id.btnOk);
        Button btnCancel = dialog1.findViewById(R.id.btnCancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    alertDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                if(!edLoginFee.getText().toString().isEmpty() && Float.parseFloat(edLoginFee.getText().toString())>0)
                {

                    if(Double.parseDouble(edLoginFee.getText().toString())<loginFeeAmount_taken){
                        Toast.makeText(MyApplicationDetailActivity.this,"Login fee cannot be take less than the given amount !",Toast.LENGTH_LONG).show();
                    }
                    else{
                    alertDialog.dismiss();
                        String refNo=edRemark.getText().toString();
                        SpinnerModel model= (SpinnerModel) sp_collection_mode.getSelectedItem();
                        final double calTaxF=(Double.parseDouble(edLoginFee.getText().toString())*tax)/100;
                        final double totalF=Double.parseDouble(edLoginFee.getText().toString())+calTaxF;

                    submitLoginsWebServiceREST(edLoginFee.getText().toString(),model.getName(),tax,calTaxF,totalF,refNo);
                    }
                }
                else{
                    Toast.makeText(MyApplicationDetailActivity.this,"Not valid Amount!",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
       // Window window = dialog1.getWindow();
        //window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void submitLoginsWebServiceREST(String name,String cMode,double taxPersentage,double taxAmount,double amount,String refNo) {
        final ProgressDialog progress;



        progress = new ProgressDialog(MyApplicationDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            LoginFeeRequest model=new LoginFeeRequest();
            model.setApplicationId(appId);
            model.setLoginUserId(session.getUserId());
            model.setAmount((float)(amount));
            model.setCollectionMode(cMode);
            model.setTaxPercentage((float)taxPersentage);
            model.setTaxAmount((float)taxAmount);
            model.setRemark(refNo);



            Call<List<AcceptRejectResponse>> call = api.saveLoginFeeApp(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> applicationDetailResponse  =response.body();

                    if(applicationDetailResponse!=null){
                        String msg= applicationDetailResponse.get(0).getMSG();
                        Toast.makeText(MyApplicationDetailActivity.this,msg,Toast.LENGTH_LONG).show();
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


    public void completeProcessWebServiceREST(EditText edReason, String name) {
        final ProgressDialog progress;



        progress = new ProgressDialog(MyApplicationDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            CompleteProcessAppRequest model=new CompleteProcessAppRequest();
            model.setApplicationId(appId);
            model.setLoginUserId(session.getUserId());
         if(TAG.equalsIgnoreCase("Send Back Branch Applications")){
             model.setProcessId(21);
         }
         else if(TAG.equalsIgnoreCase("My FI Applications")){
             model.setProcessId(6);
         }
         else{
            model.setProcessId(3);
         }
            model.setRecommendation(name);
            model.setRemarks(edReason.getText().toString());


            Call<List<AcceptRejectResponse>> call = api.completeProcessAPP(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> applicationDetailResponse  =response.body();

                    if(applicationDetailResponse!=null){
                        String msg= applicationDetailResponse.get(0).getMSG();
                        Toast.makeText(MyApplicationDetailActivity.this,msg,Toast.LENGTH_LONG).show();
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



    public void callWebServiceREST() {
        final ProgressDialog progress;



        progress = new ProgressDialog(MyApplicationDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            ApplicationDetailRequest model=new ApplicationDetailRequest();
            model.setApplicationId(String.valueOf(appId));
            // model.setInqStatus("Assigned");


            Call<ApplicationDetailResponse> call = api.getApplicationDetail(model);

            call.enqueue(new Callback<ApplicationDetailResponse>() {
                @Override
                public void onResponse(Call<ApplicationDetailResponse> call, Response<ApplicationDetailResponse> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    ApplicationDetailResponse applicationDetailResponse  =response.body();

                    if(applicationDetailResponse!=null) {
                        if (applicationDetailResponse.getItem1().get(0).isIs_AssetDetail()) {
                            btn_asset_detail.setVisibility(View.VISIBLE);
                        }

                        tvName.setText(applicationDetailResponse.getItem1().get(0).getCustomer());
                        tvInquiry.setText(applicationDetailResponse.getItem1().get(0).getApplication_No());
                        tvMobile.setText(applicationDetailResponse.getItem1().get(0).getMobileNo());
                       branchId= applicationDetailResponse.getItem1().get(0).getBranchId();
                        tvBranch.setText(applicationDetailResponse.getItem1().get(0).getBranch());
                        tvLoanType.setText(applicationDetailResponse.getItem1().get(0).getProduct());
                        tvPurpose.setText(applicationDetailResponse.getItem1().get(0).getLoanPurpose());
                        tvAmount.setText(applicationDetailResponse.getItem1().get(0).getLoanAmount() + " , " + applicationDetailResponse.getItem1().get(0).getIR() + "%" + " , " + applicationDetailResponse.getItem1().get(0).getLoanDuration());

                        item2 = applicationDetailResponse.getItem2();
                        item1 = applicationDetailResponse.getItem1();

                        if (item1.get(0).getLoginFeeAmount_Taken() < 1) {
                            tvLoginFee.setText("Login Fee : Pending");
                            tvLoginFeeTakenOn.setVisibility(View.GONE);
                            tvCollectionMode.setVisibility(View.GONE);
                            tvLoginFee.setPaintFlags(tvLoginFee.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                        } else {
                            tvLoginFee.setText("Login Fee : " + item1.get(0).getLoginFeeAmount_Taken());
                            tvCollectionMode.setText("Collection Mode : " + item1.get(0).getCollectionMode());

                            Date newDate = null;
                            try {
                                String strCurrentDate = item1.get(0).getLoginFeeAmount_TakenOn();
                                String[] arr = strCurrentDate.split("T");
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                newDate = format.parse(arr[0]);

                                format = new SimpleDateFormat("dd MMM yyyy");
                                String date = format.format(newDate);
                                tvLoginFeeTakenOn.setText("Fee Collection On : " + date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            //  tvLoginFeeTakenOn.setText("Fee Collection On : "+item1.get(0).getLoginFeeAmount_TakenOn());
                            tvLoginFeeTakenOn.setVisibility(View.VISIBLE);
                            tvLoginFee.setVisibility(View.VISIBLE);
                        }

                        MyApplicationDetailAdapter adpter = new MyApplicationDetailAdapter(MyApplicationDetailActivity.this, item2, appId, appNo, item1.get(0).getLoanAmount(), item1.get(0).getProduct(), TAG);
                        recycler_view.setAdapter(adpter);
                    }

                }

                @Override
                public void onFailure(Call<ApplicationDetailResponse> call, Throwable t) {
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

    public void DeleteCustomerApi(int customerId){

        final ProgressDialog progress;



        progress = new ProgressDialog(MyApplicationDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            DeleteCustomerRequest model=new DeleteCustomerRequest();
            model.setApplicationId(appId);
            model.setLoginUserId(session.getUserId());
            model.setCustomerId(customerId);


            Call<List<AcceptRejectResponse>> call = api.getDeleteApplicationCustomer(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if(loginResponse!=null){
                        String msg= loginResponse.get(0).getMSG();
                        Toast.makeText(MyApplicationDetailActivity.this,msg,Toast.LENGTH_LONG).show();
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

    public void ValidateAppApi(int appId){

        final ProgressDialog progress;



        progress = new ProgressDialog(MyApplicationDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            ApplicationDetailRequest model=new ApplicationDetailRequest();
            model.setApplicationId(String.valueOf(appId));



            Call<List<AcceptRejectResponse>> call = api.getValidateSalesProcessApp(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if(loginResponse!=null){
                        String msg= loginResponse.get(0).getMSG();
                      //  Toast.makeText(CustomerKYCDocListActivity.this,msg,Toast.LENGTH_LONG).show();
                        if(loginResponse.get(0).getCODE()==0){
                            showRecommendDialog();

                        }
                        else{
                            Toast.makeText(MyApplicationDetailActivity.this,msg,Toast.LENGTH_LONG).show();
                        }

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
    public void showRecommendDialog(){

        double amount = item1.get(0).getLoginFeeAmount_Taken();
        boolean isMandtory = item1.get(0).isLoginFee_IsMandtory();

        boolean isHirer=false;
        if (item2 != null) {

            for(int z =0;z<item2.size();z++){
                if(item2.get(z).getCustomerType().equalsIgnoreCase("Hirer")){
                    isHirer=true;
                    break;
                }

            }

            if(item2.size()<1 || !isHirer){
                // Toast.makeText(MyApplicationDetailActivity.this,"Atleast one person added to be in application!",Toast.LENGTH_LONG).show();
                WebUtility.showOkDialog(MyApplicationDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "At least One Hirer to be added  here .Hirer missing on this application, please add.!");
            }
            else if(amount<1 && isMandtory){

                WebUtility.showOkDialog(MyApplicationDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please submit login fee first! ");
            }
            else {
                AlertDialog.Builder dialogBuilder;
                final AlertDialog alertDialog;

                dialogBuilder  = new AlertDialog.Builder(MyApplicationDetailActivity.this);
                final View dialog = getLayoutInflater().inflate(R.layout.dialog_complete_process_app, null);

                dialogBuilder.setView(dialog);
                alertDialog = dialogBuilder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));



                final Spinner sp_recommendation = dialog.findViewById(R.id.sp_recommendation);
                final TextView tv_login_fee = dialog.findViewById(R.id.tv_login_fee);
                Button btnOk = dialog.findViewById(R.id.btnOk);
               /*   if(item1.get(0).isLoginFee_IsMandtory()){
                      tv_login_fee.setVisibility(View.VISIBLE);
                      btnOk.setEnabled(false);
                  }*/

                 /*   tv_login_fee.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });*/

                SpinnerAdapter enqiryAdapter = new SpinnerAdapter(MyApplicationDetailActivity.this, arrListRecommendation);
                sp_recommendation.setAdapter(enqiryAdapter);

                final EditText edReason = dialog.findViewById(R.id.edReason);

                Button btnCancel = dialog.findViewById(R.id.btnCancel);
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        final SpinnerModel spinnerModel = (SpinnerModel) sp_recommendation.getSelectedItem();



                        if (spinnerModel.getName().equalsIgnoreCase("--Select Recommendation--")) {
                            // Toast.makeText(MyApplicationDetailActivity.this,"Please select Recommendation!",Toast.LENGTH_LONG).show();

                            WebUtility.showOkDialog(MyApplicationDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select Recommendation!");

                        } else if (edReason.getText().toString().trim().equalsIgnoreCase("")) {

                            WebUtility.showOkDialog(MyApplicationDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter remark!");
                        } else {
                            alertDialog.dismiss();
                            completeProcessWebServiceREST(edReason, spinnerModel.getName());
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
                //  Window window = dialog.getWindow();
                //window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
        else{

            WebUtility.showOkDialog(MyApplicationDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Atleast one person added to be in application!");

        }


    }
}
