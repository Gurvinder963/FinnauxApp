package com.finnauxapp.Activities;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.finnauxapp.Adapter.HomeAdapter;
import com.finnauxapp.ApiRequest.CustomerIncomeBaseRequest;
import com.finnauxapp.ApiRequest.CustomerRegMainRequest;
import com.finnauxapp.ApiRequest.DashBoardDataRequest;
import com.finnauxapp.ApiRequest.DeviceBaseModel;
import com.finnauxapp.ApiRequest.DeviceInfoChildModel;
import com.finnauxapp.ApiRequest.LoginRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.DashboardDataResponse;
import com.finnauxapp.ApiResponse.LoginResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {
    private SessionManager session;
    private GridLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;
    private ImageView btn_add_enquiry;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        String fcmToken = FirebaseInstanceId.getInstance().getToken();

        session = new SessionManager(HomeActivity.this);
        recycler_view = findViewById(R.id.recyclerView);
        //  btn_add_enquiry = findViewById(R.id.btn_add_enquiry);
        linearLayoutManager = new GridLayoutManager(this, 3);
        recycler_view.setLayoutManager(linearLayoutManager);
        recycler_view.getLayoutManager().setAutoMeasureEnabled(true);
        recycler_view.setNestedScrollingEnabled(false);
        recycler_view.setHasFixedSize(false);
      /*  btn_add_enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,RegisterNewInquiryActivity.class);
                startActivity(intent);
            }
        });*/

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mSwipeRefreshLayout.setRefreshing(false);


                callWebServiceREST();
            }
        });

        pickFile();
        saveDeviceInfoWebServiceREST(fcmToken);
    }

    @Override
    protected void onResume() {
        super.onResume();
        callWebServiceREST();
    }

    private void pickFile() {
        if (ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 22);
        }


    }

    public void callWebServiceREST() {
        final ProgressDialog progress;


        progress = new ProgressDialog(HomeActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            DashBoardDataRequest model = new DashBoardDataRequest();
            model.setLoginUserId(session.getUserId());


            Call<List<DashboardDataResponse>> call = api.getDashBoardDetails(model);

            call.enqueue(new Callback<List<DashboardDataResponse>>() {
                @Override
                public void onResponse(Call<List<DashboardDataResponse>> call, Response<List<DashboardDataResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }


                        List list = response.body();
                    if(list!=null) {

                        StringBuilder sb=new StringBuilder();
                        for(int i=0;i<list.size();i++){
                            DashboardDataResponse res= (DashboardDataResponse) list.get(i);
                            sb.append(res.getProcessId());
                            sb.append(",");

                        }

                        SessionManager sessionManager=new SessionManager(HomeActivity.this);
                        sessionManager.saveProcessIds(sb.toString());
                        Intent intent = new Intent("process_event");

                        LocalBroadcastManager.getInstance(HomeActivity.this).sendBroadcast(intent);

                        HomeAdapter adpter = new HomeAdapter(HomeActivity.this, list);
                        recycler_view.setAdapter(adpter);
                    }
                }

                @Override
                public void onFailure(Call<List<DashboardDataResponse>> call, Throwable t) {
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


    public void saveDeviceInfoWebServiceREST(String fcmToken) {
        String gmailId = "";
        String imeiNumber = "";
        try {
            Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
            Account[] accounts = AccountManager.get(HomeActivity.this).getAccounts();
            for (Account account : accounts) {
                if (emailPattern.matcher(account.name).matches()) {
                    gmailId = account.name;

                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        try {
            TelephonyManager mngr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            imeiNumber = mngr.getDeviceId();
        } catch (Exception e) {

        }


        try {
            String deviceId = Settings.Secure.getString(this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);

            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;

            String PhoneModel = android.os.Build.MODEL;
            String manufacture = android.os.Build.MANUFACTURER;

            String osVersion = android.os.Build.VERSION.RELEASE;

            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            DeviceInfoChildModel model = new DeviceInfoChildModel();
            model.setEmpId(session.getUserId());
            model.setMobileId(fcmToken);
            model.setAppVersion(1);
            model.setDeviceIMEI(imeiNumber);
            model.setDeviceMailAddress(gmailId);
            model.setDeviceName(manufacture);
            model.setModelName(PhoneModel);
            model.setDeviceId(deviceId);
            model.setDeviceVersion(osVersion);

            DeviceBaseModel customerIncomeBaseRequest = new DeviceBaseModel();
            customerIncomeBaseRequest.setMobileDevice(model);

            String res = new Gson().toJson(customerIncomeBaseRequest);
            Log.d("my_json", res);


            CustomerRegMainRequest customerRegMainRequest = new CustomerRegMainRequest();
            customerRegMainRequest.setJson(res);

            Call<List<AcceptRejectResponse>> call = api.saveMobileInfoApp(customerRegMainRequest);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {

                    List list = response.body();


                }

                @Override
                public void onFailure(Call<List<AcceptRejectResponse>> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
