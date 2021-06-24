package com.finnauxapp.Activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.finnauxapp.Adapter.SpinnerAdapter;
import com.finnauxapp.ApiRequest.ApplicationCustomerModel;
import com.finnauxapp.ApiRequest.CustomerAddressBean;
import com.finnauxapp.ApiRequest.CustomerAddressSaveRequest;
import com.finnauxapp.ApiRequest.CustomerRegMainRequest;
import com.finnauxapp.ApiRequest.CustomerRegistrationRequest;
import com.finnauxapp.ApiRequest.DistrictRequest;
import com.finnauxapp.ApiRequest.GetCustomerAddressRequest;
import com.finnauxapp.ApiRequest.TahsilRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.CustomerAddressResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.DistrictResponse;
import com.finnauxapp.ApiResponse.LeadSouseResponse;
import com.finnauxapp.ApiResponse.StateResponse;
import com.finnauxapp.ApiResponse.TahsilResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.android.gms.common.api.Status;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
public class CustomerAddressDetailActivity extends AppCompatActivity implements
        LocationListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    ArrayList<SpinnerModel> arrListState=new ArrayList<>();
    ArrayList<SpinnerModel> arrListDistrict=new ArrayList<>();
    ArrayList<SpinnerModel> arrListTahsil=new ArrayList<>();
    private Spinner sp_state;
    private Spinner sp_district;

    private Spinner sp_Tehsil;
    private Spinner sp_address_type;
    ArrayList<SpinnerModel> arrListAddressType=new ArrayList<>();
    private Button btnSave;
    private int addressId=0;
    private EditText edAddress;
    private EditText edAddressLandmark;
    private EditText edPincode;
    private Spinner spYearsLiving;
   // private EditText edOwnRent;
    private EditText edNearrestBranch;
    private CheckBox cbGeoCode;
    private CustomerDetailResponse customerDetailObject;
    private int applicationId;
    private TextView tvCustomerName;
    private static final String ARG_PARAM1 = "param1";
    private static final int REQUEST_CHECK_SETTINGS = 21213;
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    // FragmentDetectLocationBinding locationDetectBinding;
    private LocationRequest mLocationRequest;
    
    String latLong;
    private TextView tvLocation;
    private ArrayList<SpinnerModel> arrListCustomerType=new ArrayList<>();
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    private static final long INTERVAL = 1000 * 10;
    private static final long FASTEST_INTERVAL = 1000 * 5;
    private RadioGroup rbAddressType;
    private String addressType="Own";
    private RadioButton rbOwn;
    private RadioButton rbRent;
    boolean isLaunch=false;

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_address_detail);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Customer Address Detail");
        ImageView ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(CustomerAddressDetailActivity.this,TabActivity.class);
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

        mGoogleApiClient = new GoogleApiClient.Builder(CustomerAddressDetailActivity.this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(CustomerAddressDetailActivity.this)
                .addOnConnectionFailedListener(CustomerAddressDetailActivity.this)
                .build();

        customerDetailObject= (CustomerDetailResponse) getIntent().getSerializableExtra("customerDetailObject");
        applicationId=getIntent().getIntExtra("applicationId",0);



        edAddress=findViewById(R.id.edAddress);
        tvLocation=findViewById(R.id.tvLocation);
        tvCustomerName=findViewById(R.id.tvCustomerName);
        tvCustomerName.setText("Customer Name : "+customerDetailObject.getCustomer_FirstName()+" " +customerDetailObject.getCustomer_LastName()+" ("+customerDetailObject.getCustomerType()+")");
        TextView tvAppNo = findViewById(R.id.tvAppNo);
        tvAppNo.setText("Application No : "+getIntent().getStringExtra("appNo"));

        edAddressLandmark=findViewById(R.id.edAddressLandmark);
        edPincode=findViewById(R.id.edPincode);
        spYearsLiving=findViewById(R.id.spYearsLiving);
       // edOwnRent=findViewById(R.id.edOwnRent);
        setYears();
        rbAddressType= findViewById(R.id.rbAddressType);

        rbOwn= findViewById(R.id.rbOwn);
        rbRent= findViewById(R.id.rbRent);

        rbAddressType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("checked_id",checkedId+"");


                switch (checkedId) {

                    case R.id.rbOwn:

                        addressType="Own";
                        break;

                    case R.id.rbRent:

                        addressType="Rent";
                        break;


                }
            }

        });

        edNearrestBranch=findViewById(R.id.edNearrestBranch);

        sp_address_type=findViewById(R.id.sp_address_type);

        cbGeoCode= findViewById(R.id.cbGeoCode);
        cbGeoCode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkForPermissions();
                }
                else{
                    latLong="";
                    tvLocation.setText("");
                }
            }
        });
        btnSave=findViewById(R.id.btnSave);

        if(customerDetailObject.isFirm()){
            SpinnerModel model1 = new SpinnerModel();
            model1.setId("1");
            model1.setName("Corporate");
            arrListAddressType.add(model1);
            SpinnerModel model2 = new SpinnerModel();
            model2.setId("2");
            model2.setName("Register");
            arrListAddressType.add(model2);
        }
        else {
            SpinnerModel model1 = new SpinnerModel();
            model1.setId("1");
            model1.setName("Present");
            arrListAddressType.add(model1);
            SpinnerModel model2 = new SpinnerModel();
            model2.setId("2");
            model2.setName("Permanent");
            arrListAddressType.add(model2);
            SpinnerModel model3 = new SpinnerModel();
            model3.setId("3");
            model3.setName("Work");
            arrListAddressType.add(model3);
        }
        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerAddressDetailActivity.this, arrListAddressType);
        sp_address_type.setAdapter(enqiryAdapter);
        sp_state=findViewById(R.id.sp_state);
        sp_district=findViewById(R.id.sp_district);
        sp_Tehsil=findViewById(R.id.sp_Tehsil);
        getStateList();



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SpinnerModel model= (SpinnerModel) spYearsLiving.getSelectedItem();
                SpinnerModel model1= (SpinnerModel) sp_state.getSelectedItem();
                SpinnerModel model11= (SpinnerModel) sp_district.getSelectedItem();
                SpinnerModel model111= (SpinnerModel) sp_Tehsil.getSelectedItem();

                if(model1.getId().equalsIgnoreCase("-1")){
                    WebUtility.showOkDialog(CustomerAddressDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select state!");
                }
                else if(model11.getId().equalsIgnoreCase("-1")){
                    WebUtility.showOkDialog(CustomerAddressDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select district!");
                }
                else if(model111.getId().equalsIgnoreCase("-1")){
                    WebUtility.showOkDialog(CustomerAddressDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select tehsil!");
                }
                else if(edAddress.getText().toString().trim().isEmpty()){
                    WebUtility.showOkDialog(CustomerAddressDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter address!");
                }
                else if(edAddressLandmark.getText().toString().trim().isEmpty()){
                    WebUtility.showOkDialog(CustomerAddressDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter address landmark!");
                }
                else if(edPincode.getText().toString().trim().isEmpty()){
                    WebUtility.showOkDialog(CustomerAddressDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter pincode!");
                }
                else if (edPincode.getText().toString().trim().length() < 6) {
                    WebUtility.showOkDialog(CustomerAddressDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Pincode not valid! ");
                }


                else if(model.getId().equalsIgnoreCase("-1")){
                    WebUtility.showOkDialog(CustomerAddressDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select years of living!");
                }

                else if(edNearrestBranch.getText().toString().trim().isEmpty()){
                    WebUtility.showOkDialog(CustomerAddressDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter nearest branch!");
                }

               else {


                callWebServiceUpdate();

                }
            }
        });

        sp_address_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                getCustomerAddress();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void setYears(){

        SpinnerModel model1=new SpinnerModel();
        model1.setId("-1");
        model1.setName("How many years living on this address");
        arrListCustomerType.add(model1);

        if(!customerDetailObject.isFirm()) {
            SpinnerModel model12 = new SpinnerModel();
            model12.setId("0");
            model12.setName("By-Birth");
            arrListCustomerType.add(model12);
        }
        for(int i=1;i<51;i++){
            SpinnerModel model11=new SpinnerModel();
            model11.setId(String.valueOf(i));
            model11.setName(String.valueOf(i));
            arrListCustomerType.add(model11);
        }

        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerAddressDetailActivity.this, arrListCustomerType);
        spYearsLiving.setAdapter(enqiryAdapter);
    }

    private void callWebServiceUpdate() {

        final ProgressDialog progress;



        progress = new ProgressDialog(CustomerAddressDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {

            SpinnerModel addressTypeModel= (SpinnerModel) sp_address_type.getSelectedItem();

            SpinnerModel districtModel= (SpinnerModel) sp_district.getSelectedItem();
            SpinnerModel tehsilModel= (SpinnerModel) sp_Tehsil.getSelectedItem();

            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            CustomerAddressSaveRequest customerRegistrationRequest=new CustomerAddressSaveRequest();
            CustomerAddressBean model=new CustomerAddressBean();

            model.setAddressId(addressId);
            model.setAddressType(addressTypeModel.getName());
            model.setAddress(edAddress.getText().toString());
            model.setCustomerId(customerDetailObject.getCustomerId());
            model.setPinCode(edPincode.getText().toString());
            model.setAddressRentBuy(addressType);
            model.setDistrictId(Integer.parseInt(districtModel.getId()));
            model.setLandMark(edAddressLandmark.getText().toString());
            model.setNearstBranchDistance_KM(Float.parseFloat(edNearrestBranch.getText().toString()));
            model.setTehsilId(Integer.parseInt(tehsilModel.getId()));

            model.setLatLong(latLong);
            SpinnerModel spinnerModel= (SpinnerModel) spYearsLiving.getSelectedItem();
           model.setTotalYearsOnAddress(Integer.parseInt(spinnerModel.getId()));



            customerRegistrationRequest.setAddress(model);



            String res=new Gson().toJson(customerRegistrationRequest);
            Log.d("my_json",res);



            CustomerRegMainRequest customerRegMainRequest=new CustomerRegMainRequest();
            customerRegMainRequest.setJson(res);


            Call<List<AcceptRejectResponse>> call = api.saveCustomerAddress(customerRegMainRequest);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if(loginResponse!=null){
                        String msg= loginResponse.get(0).getMSG();
                        Toast.makeText(CustomerAddressDetailActivity.this,msg,Toast.LENGTH_LONG).show();
                        finish();

                        //  uploadProfilePic(arrayListImageData,arrayList,String.valueOf(loginResponse.get(0).getCODE()));





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
    public void getStateList() {
      /*  final ProgressDialog progress;



        progress = new ProgressDialog(CustomerAddressDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();*/

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
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
                    if(list!=null){
                        SpinnerModel itemp1 = new SpinnerModel();
                        itemp1.setId("-1");
                        itemp1.setName("--Select State--");
                        arrListState.add(itemp1);

                        int index=0;

                        for(int i=0;i<list.size();i++){
                            SpinnerModel itemp = new SpinnerModel();
                            itemp.setId(String.valueOf(list.get(i).getStateId()));
                            itemp.setName(list.get(i).getState_Name());
                            if(list.get(i).getState_Name().equalsIgnoreCase("Rajasthan")){
                                index=i+1;
                            }

                            arrListState.add(itemp);
                        }




                        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerAddressDetailActivity.this, arrListState);
                        sp_state.setAdapter(enqiryAdapter);
                        sp_state.setSelection(index);

                        sp_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                SpinnerModel  spinnerModel= (SpinnerModel) sp_state.getSelectedItem();
                                String stateId = spinnerModel.getId();
                              arrListDistrict.clear();
                                 SpinnerModel itemp1 = new SpinnerModel();
                        itemp1.setId("-1");
                        itemp1.setName("--Select District--");
                        arrListDistrict.add(itemp1);
                                SpinnerAdapter enqiryAdapter1 = new SpinnerAdapter(CustomerAddressDetailActivity.this, arrListDistrict);
                                sp_district.setAdapter(enqiryAdapter1);

                                arrListTahsil.clear();

                                   SpinnerModel itemp11 = new SpinnerModel();
                        itemp11.setId("-1");
                        itemp11.setName("--Select Tehsil--");
                        arrListTahsil.add(itemp11);
                                SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerAddressDetailActivity.this, arrListTahsil);
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
        }

        catch (Exception e){
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
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);


            DistrictRequest request=new DistrictRequest();
            request.setStateID(Integer.parseInt(stateId));

            Call<List<DistrictResponse>> call = api.getDistricts(request);

            call.enqueue(new Callback<List<DistrictResponse>>() {
                @Override
                public void onResponse(Call<List<DistrictResponse>> call, Response<List<DistrictResponse>> response) {
                    /*if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/

                    List<DistrictResponse> list = response.body();
                    if(list!=null){




                        for(int i=0;i<list.size();i++){
                            SpinnerModel itemp = new SpinnerModel();
                            itemp.setId(String.valueOf(list.get(i).getDistrictId()));
                            itemp.setName(list.get(i).getDistrict_Name());


                            arrListDistrict.add(itemp);
                        }




                        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerAddressDetailActivity.this, arrListDistrict);
                        sp_district.setAdapter(enqiryAdapter);


                        sp_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                SpinnerModel  spinnerModel= (SpinnerModel) sp_district.getSelectedItem();
                                String stateId = spinnerModel.getId();
                                arrListTahsil.clear();
                                   SpinnerModel itemp1 = new SpinnerModel();
                        itemp1.setId("-1");
                        itemp1.setName("--Select Tehsil--");
                        arrListTahsil.add(itemp1);
                                SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerAddressDetailActivity.this, arrListTahsil);
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
        }

        catch (Exception e){
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
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);


            TahsilRequest request=new TahsilRequest();
            request.setDistrictId(Integer.parseInt(id));

            Call<List<TahsilResponse>> call = api.getTahsil(request);

            call.enqueue(new Callback<List<TahsilResponse>>() {
                @Override
                public void onResponse(Call<List<TahsilResponse>> call, Response<List<TahsilResponse>> response) {
                 /*   if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/

                    List<TahsilResponse> list = response.body();
                    if(list!=null){
                      /*  SpinnerModel itemp1 = new SpinnerModel();
                        itemp1.setId("-1");
                        itemp1.setName("--Select Tehsil--");
                        arrListTahsil.add(itemp1);*/

                        for(int i=0;i<list.size();i++){
                            SpinnerModel itemp = new SpinnerModel();
                            itemp.setId(String.valueOf(list.get(i).getTehsilId()));
                            itemp.setName(list.get(i).getTehsil_Name());


                            arrListTahsil.add(itemp);
                        }




                        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerAddressDetailActivity.this, arrListTahsil);
                        sp_Tehsil.setAdapter(enqiryAdapter);


                        sp_Tehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                SpinnerModel  spinnerModel= (SpinnerModel) sp_Tehsil.getSelectedItem();
                                String stateId = spinnerModel.getId();


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });

                     // if(!isLaunch) {
                      //    isLaunch=true;
                            getCustomerAddress();
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
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void getCustomerAddress() {

        final ProgressDialog progress;



        progress = new ProgressDialog(CustomerAddressDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);


            GetCustomerAddressRequest request=new GetCustomerAddressRequest();
            request.setCustomerId(customerDetailObject.getCustomerId());
            request.setApplicationId(applicationId);
            SpinnerModel addressTypeModel= (SpinnerModel) sp_address_type.getSelectedItem();
            request.setAddressType(addressTypeModel.getName());

            Call<CustomerAddressResponse> call = api.getCustomerAddress(request);

            call.enqueue(new Callback<CustomerAddressResponse>() {
                @Override
                public void onResponse(Call<CustomerAddressResponse> call, Response<CustomerAddressResponse> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    CustomerAddressResponse customerAddressResponse = response.body();
                    if(customerAddressResponse!=null){

                        if(customerAddressResponse.getItem2()!=null){

                            if(customerAddressResponse.getItem2().size()>0){
                                addressId=customerAddressResponse.getItem2().get(0).getAddressId();

                                edAddress.setText(customerAddressResponse.getItem2().get(0).getAddress());
                                edAddressLandmark.setText(customerAddressResponse.getItem2().get(0).getLandMark());
                                edPincode.setText(customerAddressResponse.getItem2().get(0).getPinCode());
                                edNearrestBranch.setText(String.format("%d", customerAddressResponse.getItem2().get(0).getNearstBranchDistance_KM()));

                                String latLong=customerAddressResponse.getItem2().get(0).getLatLong();
                              if(latLong!=null && !latLong.isEmpty()){
                                  cbGeoCode.setChecked(true);
                                  tvLocation.setText(latLong);
                              }


                              //  edYrsLiving.setText(customerAddressResponse.getItem2().get(0).getTotalYearsOnAddress());
                              //  edOwnRent.setText(customerAddressResponse.getItem2().get(0).getAddressRentBuy());
                                if(customerAddressResponse.getItem2().get(0).getAddressRentBuy().equalsIgnoreCase("Own")){
                                    rbOwn.setChecked(true);
                                    addressType="Own";
                                }
                                else{
                                    rbRent.setChecked(true);
                                    addressType="Rent";
                                }

                               for(int i=0;i<arrListCustomerType.size();i++){

                                    if(arrListCustomerType.get(i).getId().equalsIgnoreCase(customerAddressResponse.getItem2().get(0).getTotalYearsOnAddress())){
                                        spYearsLiving.setSelection(i);
                                        break;
                                    }

                                }

                                for(int i=0;i<arrListState.size();i++){

                                    if(Integer.parseInt(arrListState.get(i).getId())==customerAddressResponse.getItem2().get(0).getStateId()){
                                        sp_state.setSelection(i);
                                        break;
                                    }

                                }
                                for(int i=0;i<arrListDistrict.size();i++){

                                    if(Integer.parseInt(arrListDistrict.get(i).getId())==customerAddressResponse.getItem2().get(0).getDistrictId()){
                                        sp_district.setSelection(i);
                                        break;
                                    }

                                }

                                for(int i=0;i<arrListTahsil.size();i++){

                                    if(Integer.parseInt(arrListTahsil.get(i).getId())==customerAddressResponse.getItem2().get(0).getTehsilId()){
                                        sp_Tehsil.setSelection(i);
                                        break;
                                    }

                                }
                            }

                            else{
                                addressId=0;
                                edAddress.setText("");
                                edAddressLandmark.setText("");
                                edPincode.setText("");
                                edNearrestBranch.setText("");
                             //   edYrsLiving.setText("");
                             //   edOwnRent.setText("");

                            }
                        }


                    }




                }

                @Override
                public void onFailure(Call<CustomerAddressResponse> call, Throwable t) {
                 /*   if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void checkForPermissions() {
        if (ActivityCompat.checkSelfPermission(CustomerAddressDetailActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            createLocationRequest();
            checkLocationSettings();
        } else {
            ActivityCompat.requestPermissions( CustomerAddressDetailActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 29);
        }
    }
/*    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:

                        mGoogleApiClient.connect();
                        break;
                    case Activity.RESULT_CANCELED:
                        // Toast.makeText(getActivity(), "You must turn on the location to be able to search the location automatically.", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                break;
        }
    }

    private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest).setAlwaysShow(false);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(Objects.requireNonNull(CustomerAddressDetailActivity.this)).checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                Log.d("djhfj","djfhdj");
                try {


                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    // All location settings are satisfied. The client can initialize location requests here.
                  //  getLocation();

                    mGoogleApiClient.connect();
                }
                catch (ApiException exception){

                    switch (exception.getStatusCode()) {

                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            // Cast to a resolvable exception.
                            try {
                                ResolvableApiException resolvable = (ResolvableApiException) exception;
                                CustomerAddressDetailActivity.this.startIntentSenderForResult(resolvable.getResolution().getIntentSender(), REQUEST_CHECK_SETTINGS, null, 0, 0, 0, null);
                                // resolvable.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException | ClassCastException e) {
                                Log.d("djhfj","djfhdj");
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            Log.d("djhfj","djfhdj");
                            break;
                    }
                    Log.d("djhfj","djfhdj");
                }
            }
        });


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 29:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createLocationRequest();
                    checkLocationSettings();
                } else {

                }
                break;
          
        }
    }
    @Override
    public void onConnected(Bundle bundle) {
       // Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        @SuppressLint("MissingPermission") PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
       // Log.d(TAG, "Location update started ..............: ");
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
       // Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public void onLocationChanged(Location location) {
      //  Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
      //  mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
     //   updateUI();
        latLong=location.getLatitude()+","+location.getLongitude();
        tvLocation.setText("("+location.getLatitude()+","+location.getLongitude()+")");
    }

    @Override
    public void onStart() {
        super.onStart();
       // Log.d(TAG, "onStart fired ..............");


    }

    @Override
    public void onStop() {
        super.onStop();
     //   Log.d(TAG, "onStop fired ..............");
        if(mGoogleApiClient!=null){
            if (mGoogleApiClient.isConnected()) {
        mGoogleApiClient.disconnect();
            }
        }
      //  Log.d(TAG, "isConnected ...............: " + mGoogleApiClient.isConnected());
    }
    @Override
    protected void onPause() {
        super.onPause();  if(mGoogleApiClient!=null) {
            if (mGoogleApiClient.isConnected()) {
        stopLocationUpdates();}}
    }
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
      //  Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mGoogleApiClient!=null) {
            if (mGoogleApiClient.isConnected()) {
                startLocationUpdates();
                //  Log.d(TAG, "Location update resumed .....................");
            }
        }
    }
}
