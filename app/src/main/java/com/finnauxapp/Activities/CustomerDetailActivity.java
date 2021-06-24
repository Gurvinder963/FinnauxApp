package com.finnauxapp.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.finnauxapp.Adapter.MyApplicationDetailAdapter;
import com.finnauxapp.ApiRequest.ApplicationDetailRequest;
import com.finnauxapp.ApiRequest.CustomerDetailRequest;
import com.finnauxapp.ApiResponse.ApplicationDetailResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomerDetailActivity extends AppCompatActivity {
    private LinearLayout llCustomerDetail;
    private LinearLayout llCustomerAddress;
    private LinearLayout llCustomerKYCDOC;
    private TextView tvName;
    private TextView tvAge;
    private TextView tvLoanType;
    private TextView tvAmount;
    int CustomerId;
    private int applicationId;
    private String product;
    private String loanAmount;
    private CustomerDetailResponse customerDetailObject;
    private String appNo;
    private LinearLayout llCustomerFamilyMembe;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
          finish();
        }
    };
    private LinearLayout llCustomerExpenditure;
    private LinearLayout llCustomerReference;
    private LinearLayout llCustomerExistingLoan;
    private LinearLayout llCustomerIncomeDetail;
    private ImageView ivProfile;
    private LinearLayout llCustomerFIQuestions;
    private String profilePic;
    private LinearLayout llRelation;
    private TextView tvRelation;
    private LinearLayout llFirmDirector;
    private boolean Is_FI_Allow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("listRefresh"));
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("My Customer");
        ImageView ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CustomerDetailActivity.this,TabActivity.class);
                startActivity(intent);
            }
        });
        ImageView ivBack = findViewById(R.id.ivBack);
      //  Button btn_asset_detail = findViewById(R.id.btn_asset_detail);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvName=findViewById(R.id.tvName);
        tvAge= findViewById(R.id.tvAge);
        tvLoanType= findViewById(R.id.tvLoanType);
        tvAmount= findViewById(R.id.tvAmount);
        llCustomerKYCDOC=findViewById(R.id.llCustomerKYCDOC);
        llCustomerFamilyMembe=findViewById(R.id.llCustomerFamilyMembe);
        llRelation=findViewById(R.id.llRelation);
        tvRelation=findViewById(R.id.tvRelation);
        ivProfile=findViewById(R.id.ivProfile);

        CustomerId = getIntent().getIntExtra("CustomerId", 0);
        Is_FI_Allow = getIntent().getBooleanExtra("Is_FI_Allow", false);
        String TAG = getIntent().getStringExtra("TAG");
     /*   btn_asset_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CustomerDetailActivity.this,AssetDetailActivity.class);
                intent.putExtra("customerDetailObject",customerDetailObject);
                intent.putExtra("applicationId",applicationId);
                intent.putExtra("appNo",appNo);
                intent.putExtra("from","detail");
                startActivity(intent);
            }
        });*/

       // final String profilePic = getIntent().getStringExtra("profilePic");
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder;
                final AlertDialog alertDialog;

                dialogBuilder  = new AlertDialog.Builder(CustomerDetailActivity.this);
                final View dialog = LayoutInflater.from(CustomerDetailActivity.this).inflate(R.layout.dialog_view_kyc_doc, null);

                dialogBuilder.setView(dialog);
                alertDialog = dialogBuilder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                final ImageView ivPhoto = dialog.findViewById(R.id.ivPhoto);
                final ImageView ivClose = dialog.findViewById(R.id.ivClose);
                final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                String fileName=profilePic;

                if(profilePic!=null) {
                   // String url = Api.BASE_URL + "uploadDoc/wwwroot/Document/Employee/ProfilePic/" + profilePic;

                    SessionManager session = new SessionManager(CustomerDetailActivity.this);
                    String url =  session.getClientUrl()+ "uploadDoc/wwwroot/Document/Customer/" + CustomerId + "/" + profilePic;
                    Glide.with(CustomerDetailActivity.this)
                            .load(url)

                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBar.setVisibility(View.GONE);
                                            ivPhoto.setImageResource(R.drawable.user_icon);
                                        }
                                    }, 700);


                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(ivPhoto);
                }
                alertDialog.show();
            }
        });


        applicationId = getIntent().getIntExtra("applicationId", 0);
        product = getIntent().getStringExtra("product");
        appNo = getIntent().getStringExtra("appNo");
        loanAmount = getIntent().getStringExtra("loanAmount");

      
        llCustomerDetail=findViewById(R.id.llCustomerDetail);
        llCustomerAddress= findViewById(R.id.llCustomerAddress);
        llCustomerExpenditure= findViewById(R.id.llCustomerExpenditure);
        llCustomerReference= findViewById(R.id.llCustomerReference);
        llCustomerExistingLoan= findViewById(R.id.llCustomerExistingLoan);
        llCustomerIncomeDetail= findViewById(R.id.llCustomerIncomeDetail);
        llCustomerFIQuestions= findViewById(R.id.llCustomerFIQuestions);
        llFirmDirector= findViewById(R.id.llFirmDirector);


      if(TAG.equalsIgnoreCase("My FI Applications") && Is_FI_Allow){
          llCustomerIncomeDetail.setVisibility(View.VISIBLE);
          llCustomerExistingLoan.setVisibility(View.VISIBLE);
          llCustomerReference.setVisibility(View.VISIBLE);
          llCustomerExpenditure.setVisibility(View.VISIBLE);
          llCustomerFamilyMembe.setVisibility(View.VISIBLE);
          llCustomerFIQuestions.setVisibility(View.VISIBLE);

      }



        llCustomerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetailActivity.this,CustomerRegistrationActivity.class);

                intent.putExtra("customerDetailObject",customerDetailObject);
                intent.putExtra("applicationId",applicationId);
                intent.putExtra("appNo",appNo);
                intent.putExtra("from","detail");
                startActivity(intent);
            }
        });

        llCustomerAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetailActivity.this,CustomerAddressDetailActivity.class);
              intent.putExtra("customerDetailObject",customerDetailObject);
              intent.putExtra("applicationId",applicationId);
              intent.putExtra("appNo",appNo);
              intent.putExtra("from","detail");


                startActivity(intent);
            }
        });

        llCustomerKYCDOC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetailActivity.this,CustomerKYCDocListActivity.class);
                intent.putExtra("customerDetailObject",customerDetailObject);
                intent.putExtra("applicationId",applicationId);
                intent.putExtra("appNo",appNo);
                intent.putExtra("from","detail");
                startActivity(intent);
            }
        });

        llCustomerFamilyMembe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetailActivity.this,FamilyMemberListActivity.class);
                intent.putExtra("customerDetailObject",customerDetailObject);
                intent.putExtra("applicationId",applicationId);
                intent.putExtra("appNo",appNo);
                intent.putExtra("from","detail");
                startActivity(intent);
            }
        });

        llCustomerExpenditure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetailActivity.this,CustomerExpenditureListActivity.class);
                intent.putExtra("customerDetailObject",customerDetailObject);
                intent.putExtra("applicationId",applicationId);
                intent.putExtra("appNo",appNo);
                intent.putExtra("from","detail");
                startActivity(intent);
            }
        });
        llCustomerReference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetailActivity.this,CustomerReferenceListActivity.class);
                intent.putExtra("customerDetailObject",customerDetailObject);
                intent.putExtra("applicationId",applicationId);
                intent.putExtra("appNo",appNo);
                intent.putExtra("from","detail");
                startActivity(intent);
            }
        });
        llCustomerExistingLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetailActivity.this,CustomerExistingLoanActivity.class);
                intent.putExtra("customerDetailObject",customerDetailObject);
                intent.putExtra("applicationId",applicationId);
                intent.putExtra("appNo",appNo);
                intent.putExtra("from","detail");
                startActivity(intent);
            }
        });

        llCustomerIncomeDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetailActivity.this,CustomerIncomeDetailActivity.class);
                intent.putExtra("customerDetailObject",customerDetailObject);
                intent.putExtra("applicationId",applicationId);
                intent.putExtra("appNo",appNo);
                intent.putExtra("from","detail");
                startActivity(intent);
            }
        });

        llCustomerFIQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetailActivity.this,QuestionAnswerActivity.class);
                intent.putExtra("customerDetailObject",customerDetailObject);
                intent.putExtra("applicationId",applicationId);
                intent.putExtra("appNo",appNo);
                intent.putExtra("from","detail");
                startActivity(intent);
            }
        });
        llFirmDirector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CustomerDetailActivity.this,FirmPartnerSaveActivity.class);
                intent.putExtra("customerDetailObject",customerDetailObject);
                intent.putExtra("applicationId",applicationId);
                intent.putExtra("appNo",appNo);
                intent.putExtra("from","detail");
                startActivity(intent);
            }
        });
        callWebServiceREST();

    }

    public void callWebServiceREST() {
        final ProgressDialog progress;



        progress = new ProgressDialog(CustomerDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            CustomerDetailRequest model=new CustomerDetailRequest();
            model.setApplicationId(applicationId);
           model.setCustomerId(CustomerId);
            // model.setInqStatus("Assigned");


            Call<List<CustomerDetailResponse>> call = api.getCustomerDetail(model);

            call.enqueue(new Callback<List<CustomerDetailResponse>>() {
                @Override
                public void onResponse(Call<List<CustomerDetailResponse>> call, Response<List<CustomerDetailResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<CustomerDetailResponse> responseCustomerDetail  =response.body();

                    if(responseCustomerDetail!=null && responseCustomerDetail.size()>0){

                        customerDetailObject=responseCustomerDetail.get(0);

                         if(customerDetailObject.isFirm()){
                             llFirmDirector.setVisibility(View.VISIBLE);
                         }
                        tvName.setText(responseCustomerDetail.get(0).getCustomer_FirstName()+" "+responseCustomerDetail.get(0).getCustomer_LastName()+" ("+responseCustomerDetail.get(0).getCustomerType()+")");


                        if(!customerDetailObject.getCustomerType().equalsIgnoreCase("H") && customerDetailObject.getCustomer_Relation()!=null){
                          llRelation.setVisibility(View.VISIBLE);
                            tvRelation.setText(customerDetailObject.getCustomer_Relation());
                        }

                        profilePic=customerDetailObject.getCustomer_ProfilePic();

                        if(profilePic!=null) {
                            // String url = Api.BASE_URL + "uploadDoc/wwwroot/Document/Employee/ProfilePic/" + profilePic;
                            SessionManager session = new SessionManager(CustomerDetailActivity.this);

                            String url =    session.getClientUrl() + "uploadDoc/wwwroot/Document/Customer/" + CustomerId + "/" + profilePic;

                            Glide.with(CustomerDetailActivity.this)
                                    .load(url)
                                    .apply(RequestOptions.placeholderOf(R.drawable.user_icon).error(R.drawable.user_icon))
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    ivProfile.setImageResource(R.drawable.user_icon);
                                                }
                                            }, 700);


                                            return false;
                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                            return false;
                                        }
                                    })
                                    .into(ivProfile);
                        }

                        if(customerDetailObject.isFirm()){
                            llFirmDirector.setVisibility(View.VISIBLE);
                            tvAge.setText("N/A");
                        }
                        else {
                            Date newDate = null;
                            try {
                                String strCurrentDate = responseCustomerDetail.get(0).getCustomer_DOB();
                                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
                                newDate = format.parse(strCurrentDate);

                                format = new SimpleDateFormat("dd/MM/yyyy");
                                String date = format.format(newDate);

                                String[] arr = date.split("/");
                                String age = getAge(Integer.parseInt(arr[2]), Integer.parseInt(arr[1]), Integer.parseInt(arr[0]));
                                tvAge.setText(responseCustomerDetail.get(0).getCustomer_Gender() + " , " + age + "Yr");

                            } catch (Exception e) {
                                e.printStackTrace();
                                tvAge.setText("N/A");
                            }
                        }
                       tvLoanType.setText(product);
                        tvAmount.setText(loanAmount);





                    }

                }

                @Override
                public void onFailure(Call<List<CustomerDetailResponse>> call, Throwable t) {
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
    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }
    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
}
