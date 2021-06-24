package com.finnauxapp.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.finnauxapp.Adapter.LeadAdapter;
import com.finnauxapp.Adapter.SpinnerAdapter;
import com.finnauxapp.ApiRequest.ApplicationCustomerModel;
import com.finnauxapp.ApiRequest.CustomerDuplicationRequest;
import com.finnauxapp.ApiRequest.CustomerRegMainRequest;
import com.finnauxapp.ApiRequest.CustomerRegistrationRequest;
import com.finnauxapp.ApiRequest.DashBoardDataRequest;
import com.finnauxapp.ApiRequest.DocMasterRequest;
import com.finnauxapp.ApiRequest.FirmSaveBaseModel;
import com.finnauxapp.ApiRequest.FirmSaveChildModel;
import com.finnauxapp.ApiRequest.GenerateApplicationRequest;
import com.finnauxapp.ApiRequest.GenerateOTPRequest;
import com.finnauxapp.ApiRequest.LeadRequest;
import com.finnauxapp.ApiRequest.SaveOTPVerifyRequest;
import com.finnauxapp.ApiRequest.SendMessageRequest;
import com.finnauxapp.ApiRequest.UploadCustomerDocRequest;
import com.finnauxapp.ApiRequest.UploadPicRequest;
import com.finnauxapp.ApiRequest.kycDocModel;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.DocTypeResponse;
import com.finnauxapp.ApiResponse.DuplicationCheckResponse;
import com.finnauxapp.ApiResponse.GenerateOTPResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.Base64;
import com.finnauxapp.Util.CompressImage;
import com.finnauxapp.Util.DateOnClick;
import com.finnauxapp.Util.DatePickerFragment2;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.Util.VerhoeffAlgorithm;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.pickit.PickiT;
import com.finnauxapp.pickit.PickiTCallbacks;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;
import com.google.gson.Gson;
import com.theartofdev.edmodo.cropper.CropImage;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomerRegistrationActivity extends AppCompatActivity implements PickiTCallbacks, DateOnClick<String> {
    final int RESULT_LOAD_IMAGE = 1;
    final int CAMERA_REQUEST = 2;
    private EditText edCustomerType;
    private EditText edFirstName;
    private EditText edFirmName;
    private EditText edPrimaryContactNoFirm;
    private EditText edAlternateNoFirm;
    private EditText edEmailFirm;
    private EditText edFirmDateOfIncorporation;
    private EditText edFirmNoOfPartner;
    private EditText edFirmNoOfEmployee;
    private EditText edFirmNatureOfBusiness;
    private EditText edLastName;
    private EditText edSpouseFatherName;
    private RadioGroup rbGender;
    private EditText tvDob;
    private EditText edPrimaryContactNo;
    private EditText edAlternateNo;
    private EditText edWhatsUpno;
    private EditText edEmail;
    private Button btnSave;
    private SessionManager session;
    private Button btnBrowse;

    private Uri selectedImageUri;
    private ProgressDialog progressDialog1;
    private PickiT pickiT;

    private ImageView ivAddView;
    private DatePickerDialog datePickerDialog;
    private LinearLayout llKYCInfo;
    int index = 1;
    int clickedIndex = 1;
    private Button btnNext;
    private LinearLayout llDemoInfo;
    private LinearLayout llDocs;
    private String gender = "M";
    int applicationId;
    ArrayList<SpinnerModel> arrListDocument = new ArrayList<>();
    private Spinner sp_customer_type;
    ArrayList<SpinnerModel> arrListCustomerType = new ArrayList<>();
    ArrayList<SpinnerModel> arrListCustomerRel = new ArrayList<>();
    ArrayList<SpinnerModel> arrListMaritalStatus = new ArrayList<>();
    ArrayList<SpinnerModel> arrListReligion = new ArrayList<>();
    ArrayList<SpinnerModel> arrListCast = new ArrayList<>();
    ArrayList<SpinnerModel> arrListType = new ArrayList<>();
    ArrayList<SpinnerModel> arrListFirmType = new ArrayList<>();
    private Button btnDemoInfo;
    private Button btnKYCInfo;
    int pageClicked = 1;
    private TextView tvAppNo;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private int CustomerId = 0;
    private CircleImageView ivProfile;
    boolean isProfileClicked = false;
    String profilePicPath = "";
    CustomerDetailResponse customerDetailObject;
    private LinearLayout llTabs;
    private String from;
    private boolean lockAspectRatio = false, setBitmapMaxWidthHeight = false;
    private int ASPECT_RATIO_X = 16, ASPECT_RATIO_Y = 9, bitmapMaxWidth = 1000, bitmapMaxHeight = 1000;
    private int IMAGE_COMPRESSION = 80;
    private String TAG;
    private String product;
    private String loanAmount;
    private LinearLayout llRelationWithHirer;
    private LinearLayout llIndividual;
    private LinearLayout llNonIndividual;
    private LinearLayout llDirectorInfo;
    private Spinner sp_customer_relation;
    private Spinner sp_marital_status;
    private Spinner sp_religion;
    private Spinner sp_cast;
    private Spinner sp_type;
    private Spinner sp_firm_type;
    private Button btn_get_otp;
    private ImageView ivVerify;
    private TextView tvVerifiedText;
    private int firmId=0;
    private Button btn_get_otp_firm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_customer_registration);

        ImageView ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Customer Registration");


        SpinnerModel modelFirmType0 = new SpinnerModel();
        modelFirmType0.setId("0");
        modelFirmType0.setName("--Select--");
        arrListFirmType.add(modelFirmType0);

        SpinnerModel modelFirmType7 = new SpinnerModel();
        modelFirmType7.setId("7");
        modelFirmType7.setName("Firm");
        arrListFirmType.add(modelFirmType7);

        SpinnerModel modelFirmType1 = new SpinnerModel();
        modelFirmType1.setId("1");
        modelFirmType1.setName("Company-LTD(P)");
        arrListFirmType.add(modelFirmType1);
        SpinnerModel modelFirmType2 = new SpinnerModel();
        modelFirmType2.setId("2");
        modelFirmType2.setName("Company-LLP");
        arrListFirmType.add(modelFirmType2);
        SpinnerModel modelFirmType3 = new SpinnerModel();
        modelFirmType3.setId("3");
        modelFirmType3.setName("Company-LTD");
        arrListFirmType.add(modelFirmType3);
        SpinnerModel modelFirmType4 = new SpinnerModel();
        modelFirmType4.setId("4");
        modelFirmType4.setName("Trust");
        arrListFirmType.add(modelFirmType4);
        SpinnerModel modelFirmType5 = new SpinnerModel();
        modelFirmType5.setId("5");
        modelFirmType5.setName("HUF");
        arrListFirmType.add(modelFirmType5);
        SpinnerModel modelFirmType6 = new SpinnerModel();
        modelFirmType6.setId("6");
        modelFirmType6.setName("Institute");
        arrListFirmType.add(modelFirmType6);



        SpinnerModel modelType1 = new SpinnerModel();
        modelType1.setId("1");
        modelType1.setName("Individual");
        arrListType.add(modelType1);
        SpinnerModel modelType2 = new SpinnerModel();
        modelType2.setId("2");
        modelType2.setName("Non-Individual");
        arrListType.add(modelType2);


        SpinnerModel modelms1 = new SpinnerModel();
        modelms1.setId("1");
        modelms1.setName("Married");
        arrListMaritalStatus.add(modelms1);
        SpinnerModel modelms2 = new SpinnerModel();
        modelms2.setId("2");
        modelms2.setName("Un-Married");
        arrListMaritalStatus.add(modelms2);

        SpinnerModel modelrl1 = new SpinnerModel();
        modelrl1.setId("1");
        modelrl1.setName("Hindus");
        arrListReligion.add(modelrl1);
        SpinnerModel modelrl2 = new SpinnerModel();
        modelrl2.setId("2");
        modelrl2.setName("Muslims");
        arrListReligion.add(modelrl2);
        SpinnerModel modelrl3 = new SpinnerModel();
        modelrl3.setId("3");
        modelrl3.setName("Christians");
        arrListReligion.add(modelrl3);
        SpinnerModel modelrl4 = new SpinnerModel();
        modelrl4.setId("4");
        modelrl4.setName("Sikhs");
        arrListReligion.add(modelrl4);
        SpinnerModel modelrl5 = new SpinnerModel();
        modelrl5.setId("5");
        modelrl5.setName("Buddhists");
        arrListReligion.add(modelrl5);
        SpinnerModel modelrl6 = new SpinnerModel();
        modelrl6.setId("6");
        modelrl6.setName("Jains");
        arrListReligion.add(modelrl6);
        SpinnerModel modelrl7 = new SpinnerModel();
        modelrl7.setId("7");
        modelrl7.setName("Others");
        arrListReligion.add(modelrl7);

        SpinnerModel modelcc1 = new SpinnerModel();
        modelcc1.setId("1");
        modelcc1.setName("General");
        arrListCast.add(modelcc1);
        SpinnerModel modelcc2 = new SpinnerModel();
        modelcc2.setId("2");
        modelcc2.setName("OBC");
        arrListCast.add(modelcc2);
        SpinnerModel modelcc3 = new SpinnerModel();
        modelcc3.setId("3");
        modelcc3.setName("SC");
        arrListCast.add(modelcc3);
        SpinnerModel modelcc4 = new SpinnerModel();
        modelcc4.setId("4");
        modelcc4.setName("ST");
        arrListCast.add(modelcc4);
        SpinnerModel modelcc5 = new SpinnerModel();
        modelcc5.setId("5");
        modelcc5.setName("Others");
        arrListCast.add(modelcc5);


        SpinnerModel model1 = new SpinnerModel();
        model1.setId("H");
        model1.setName("Hirer");
        arrListCustomerType.add(model1);
        SpinnerModel model2 = new SpinnerModel();
        model2.setId("C");
        model2.setName("Co-Borrower");
        arrListCustomerType.add(model2);
        SpinnerModel model3 = new SpinnerModel();
        model3.setId("G");
        model3.setName("Guarantor");
        arrListCustomerType.add(model3);


        SpinnerModel cr1 = new SpinnerModel();
        cr1.setId("1");
        cr1.setName("Father");
        arrListCustomerRel.add(cr1);
        SpinnerModel cr2 = new SpinnerModel();
        cr2.setId("2");
        cr2.setName("Mother");
        arrListCustomerRel.add(cr2);
        SpinnerModel cr3 = new SpinnerModel();
        cr3.setId("3");
        cr3.setName("Spouse");
        arrListCustomerRel.add(cr3);
        SpinnerModel cr4 = new SpinnerModel();
        cr4.setId("4");
        cr4.setName("Son");
        arrListCustomerRel.add(cr4);
        SpinnerModel cr5 = new SpinnerModel();
        cr5.setId("5");
        cr5.setName("Daughter");
        arrListCustomerRel.add(cr5);
        SpinnerModel cr6 = new SpinnerModel();
        cr6.setId("6");
        cr6.setName("Brother");
        arrListCustomerRel.add(cr6);
        SpinnerModel cr7 = new SpinnerModel();
        cr7.setId("7");
        cr7.setName("Father-In-Law");
        arrListCustomerRel.add(cr7);
        SpinnerModel cr8 = new SpinnerModel();
        cr8.setId("8");
        cr8.setName("Mother-In-Law");
        arrListCustomerRel.add(cr8);
        SpinnerModel cr9 = new SpinnerModel();
        cr9.setId("9");
        cr9.setName("Brother-In-Law");
        arrListCustomerRel.add(cr9);
        SpinnerModel cr10 = new SpinnerModel();
        cr10.setId("10");
        cr10.setName("Friend");
        arrListCustomerRel.add(cr10);
        SpinnerModel cr11 = new SpinnerModel();
        cr11.setId("11");
        cr11.setName("Other");
        arrListCustomerRel.add(cr11);
        SpinnerModel cr12 = new SpinnerModel();
        cr12.setId("12");
        cr12.setName("None");
        arrListCustomerRel.add(cr12);

        ivProfile = findViewById(R.id.ivProfile);

        applicationId = getIntent().getIntExtra("applicationId", 0);
        // CustomerId=getIntent().getIntExtra("CustomerId",0);
        from = getIntent().getStringExtra("from");
        TAG = getIntent().getStringExtra("TAG");
        product = getIntent().getStringExtra("product");

        loanAmount = getIntent().getStringExtra("loanAmount");

        if (from.equalsIgnoreCase("detail")) {
            tvTitle.setText("Customer Detail");
        }

        customerDetailObject = (CustomerDetailResponse) getIntent().getSerializableExtra("customerDetailObject");

        pickiT = new PickiT(CustomerRegistrationActivity.this, CustomerRegistrationActivity.this, CustomerRegistrationActivity.this);
        session = new SessionManager(CustomerRegistrationActivity.this);

        sp_type = findViewById(R.id.sp_type);
        sp_firm_type = findViewById(R.id.sp_firm_type);
        llRelationWithHirer = findViewById(R.id.llRelationWithHirer);
        llIndividual = findViewById(R.id.llIndividual);
        llNonIndividual = findViewById(R.id.llNonIndividual);
        llDirectorInfo = findViewById(R.id.llDirectorInfo);
        sp_customer_relation = findViewById(R.id.sp_customer_relation);


        SpinnerAdapter enqiryAdapterFirmType = new SpinnerAdapter(CustomerRegistrationActivity.this, arrListFirmType);
        sp_firm_type.setAdapter(enqiryAdapterFirmType);

        SpinnerAdapter enqiryAdapterType = new SpinnerAdapter(CustomerRegistrationActivity.this, arrListType);
        sp_type.setAdapter(enqiryAdapterType);
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerModel spinnerModel = (SpinnerModel) sp_type.getSelectedItem();
                if (spinnerModel.getName().equalsIgnoreCase("Individual")) {
                    llIndividual.setVisibility(View.VISIBLE);
                    llNonIndividual.setVisibility(View.GONE);
                } else {
                    llIndividual.setVisibility(View.GONE);
                    llNonIndividual.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinnerAdapter enqiryAdapter1 = new SpinnerAdapter(CustomerRegistrationActivity.this, arrListCustomerRel);
        sp_customer_relation.setAdapter(enqiryAdapter1);

        ivVerify = findViewById(R.id.ivVerify);
        tvVerifiedText = findViewById(R.id.tvVerifiedText);

        btn_get_otp = findViewById(R.id.btn_get_otp);
        btn_get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edPrimaryContactNo.getText().toString().trim().isEmpty()) {
                    WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Primary contact!");
                } else if (!edPrimaryContactNo.getText().toString().trim().isEmpty() && edPrimaryContactNo.getText().toString().trim().length() < 10) {
                    WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Primary contact no. not valid! ");
                } else {
                    callWebServiceGETOTPREST(edPrimaryContactNo.getText().toString());
                }
            }
        });


        btn_get_otp_firm = findViewById(R.id.btn_get_otp_firm);
        btn_get_otp_firm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edPrimaryContactNoFirm.getText().toString().trim().isEmpty()) {
                    WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Primary contact!");
                } else if (!edPrimaryContactNoFirm.getText().toString().trim().isEmpty() && edPrimaryContactNoFirm.getText().toString().trim().length() < 10) {
                    WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Primary contact no. not valid! ");
                } else {
                    callWebServiceGETOTPREST(edPrimaryContactNoFirm.getText().toString());
                }
            }
        });

        btnDemoInfo = findViewById(R.id.btnDemoInfo);
        btnKYCInfo = findViewById(R.id.btnKYCInfo);
        btnDemoInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageClicked = 1;

                llTabs.setBackgroundResource(R.drawable.active_left);
                llDemoInfo.setVisibility(View.VISIBLE);
                llKYCInfo.setVisibility(View.GONE);
            }
        });
        btnKYCInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pageClicked = 2;

                llTabs.setBackgroundResource(R.drawable.active_right);
                llDemoInfo.setVisibility(View.GONE);
                llKYCInfo.setVisibility(View.VISIBLE);
            }
        });

        sp_customer_type = findViewById(R.id.sp_customer_type);
        sp_marital_status = findViewById(R.id.sp_marital_status);
        sp_religion = findViewById(R.id.sp_religion);
        sp_cast = findViewById(R.id.sp_cast);


        SpinnerAdapter msAdapter = new SpinnerAdapter(CustomerRegistrationActivity.this, arrListMaritalStatus);
        sp_marital_status.setAdapter(msAdapter);

        SpinnerAdapter rlAdapter = new SpinnerAdapter(CustomerRegistrationActivity.this, arrListReligion);
        sp_religion.setAdapter(rlAdapter);

        SpinnerAdapter ctAdapter = new SpinnerAdapter(CustomerRegistrationActivity.this, arrListCast);
        sp_cast.setAdapter(ctAdapter);

        TextView tvCustomerName = findViewById(R.id.tvCustomerName);
        if (customerDetailObject != null) {
            tvCustomerName.setText("Customer Name : " + customerDetailObject.getCustomer_FirstName() + " " + customerDetailObject.getCustomer_LastName() + " (" + customerDetailObject.getCustomerType() + ")");
            tvCustomerName.setVisibility(View.VISIBLE);
        }


        TextView tvAppNo = findViewById(R.id.tvAppNo);
        tvAppNo.setText("Application No : " + getIntent().getStringExtra("appNo"));

        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerRegistrationActivity.this, arrListCustomerType);
        sp_customer_type.setAdapter(enqiryAdapter);

        sp_customer_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerModel spinnerModel = (SpinnerModel) sp_customer_type.getSelectedItem();
                if (spinnerModel.getName().equalsIgnoreCase("Hirer")) {

                    llRelationWithHirer.setVisibility(View.GONE);
                } else {
                    llRelationWithHirer.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edFirmName = findViewById(R.id.edFirmName);
        edPrimaryContactNoFirm = findViewById(R.id.edPrimaryContactNoFirm);
        edAlternateNoFirm = findViewById(R.id.edAlternateNoFirm);
        edEmailFirm = findViewById(R.id.edEmailFirm);
        edFirmDateOfIncorporation = findViewById(R.id.edFirmDateOfIncorporation);

        edFirmNoOfPartner = findViewById(R.id.edFirmNoOfPartner);
        edFirmNoOfEmployee = findViewById(R.id.edFirmNoOfEmployee);
        edFirmNatureOfBusiness = findViewById(R.id.edFirmNatureOfBusiness);
        edFirmDateOfIncorporation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(edFirmDateOfIncorporation);
            }
        });

        edFirstName = findViewById(R.id.edFirstName);
        llTabs = findViewById(R.id.llTabs);
        edLastName = findViewById(R.id.edLastName);
        edSpouseFatherName = findViewById(R.id.edSpouseFatherName);
        rbGender = findViewById(R.id.rbGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        rbGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("checked_id", checkedId + "");


                switch (checkedId) {

                    case R.id.rbMale:

                        gender = "M";
                        break;

                    case R.id.rbFemale:

                        gender = "F";
                        break;


                }
            }

        });
        ImageView ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerRegistrationActivity.this, TabActivity.class);
                startActivity(intent);
            }
        });
        tvDob = findViewById(R.id.tvDob);
        //  edFile= findViewById(R.id.edFile);
        edPrimaryContactNo = findViewById(R.id.edPrimaryContactNo);
        edPrimaryContactNo.setOnFocusChangeListener(onFoucusChangeListener);
        edAlternateNo = findViewById(R.id.edAlternateNo);
        edWhatsUpno = findViewById(R.id.edWhatsUpno);
        edEmail = findViewById(R.id.edEmail);
        btnNext = findViewById(R.id.btnNext);
        // btnBrowse= findViewById(R.id.btnBrowse);
        ivAddView = findViewById(R.id.ivAddView);
        llKYCInfo = findViewById(R.id.llKYCInfo);
        llDemoInfo = findViewById(R.id.llDemoInfo);
        llDocs = findViewById(R.id.llDocs);
        btnSave = findViewById(R.id.btnSave);

        Button btn_Cancel = findViewById(R.id.btn_Cancel);
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(tvDob);
            }
        });

        ivAddView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = index + 1;
                addDocRow();
            }
        });
        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dialogBuilder;
                final AlertDialog alertDialog;

                dialogBuilder = new AlertDialog.Builder(CustomerRegistrationActivity.this);
                final View dialog = getLayoutInflater().inflate(R.layout.dialog_choose_content, null);

                dialogBuilder.setView(dialog);
                alertDialog = dialogBuilder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                Button btnCamera = dialog.findViewById(R.id.btnCamera);
                Button btnGallery = dialog.findViewById(R.id.btnGallery);
                btnCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        isProfileClicked = true;
                        pickCamera();
                    }
                });
                btnGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        isProfileClicked = true;
                        pickFile();
                    }
                });

                alertDialog.show();
                //   Window window = dialog.getWindow();
                // window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from.equalsIgnoreCase("detail")) {
                    SpinnerModel spType = (SpinnerModel) sp_type.getSelectedItem();
                    if (spType.getName().equalsIgnoreCase("Individual")) {
                        if (edFirstName.getText().toString().trim().isEmpty()) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter first name!");
                        } else if (edSpouseFatherName.getText().toString().trim().isEmpty()) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Father/Spouse name!");
                        } else if (edPrimaryContactNo.getText().toString().trim().isEmpty()) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Primary contact!");
                        } else if (tvDob.getText().toString().trim().equalsIgnoreCase("--Pick--")) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter DOB!");
                        } else if (!edPrimaryContactNo.getText().toString().trim().isEmpty() && edPrimaryContactNo.getText().toString().trim().length() < 10) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Primary contact no. not valid! ");
                        } else if (!edAlternateNo.getText().toString().trim().isEmpty() && edAlternateNo.getText().toString().trim().length() < 10) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Alternate no. not valid! ");
                        } else if (!edWhatsUpno.getText().toString().trim().isEmpty() && edWhatsUpno.getText().toString().trim().length() < 10) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "WhatsApp no. not valid! ");
                        } else if (!edEmail.getText().toString().trim().isEmpty() && !WebUtility.validEmail(edEmail.getText().toString())) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Email not valid!");
                        } else {

                            callWebServiceUpdate();
                        }
                    }
                    else{
                        SpinnerModel spModel=(SpinnerModel) sp_firm_type.getSelectedItem();
                        String firmTypeName=spModel.getName();

                        if(firmTypeName.equalsIgnoreCase("--Select--")){
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select firm Type!");
                        }
                        else if (edFirmName.getText().toString().trim().isEmpty()) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter firm name!");
                        } else if (edPrimaryContactNoFirm.getText().toString().trim().isEmpty()) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Primary contact of firm!");
                        } /*else if (edEmailFirm.getText().toString().trim().isEmpty()) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter email!");
                        }*/ else if (edFirmDateOfIncorporation.getText().toString().trim().isEmpty()) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select date of incorporation!");
                        } else if (!edPrimaryContactNoFirm.getText().toString().trim().isEmpty() && edPrimaryContactNoFirm.getText().toString().trim().length() < 10) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Primary contact no. not valid! ");
                        } else if (!edAlternateNoFirm.getText().toString().trim().isEmpty() && edAlternateNoFirm.getText().toString().trim().length() < 10) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Alternate no. not valid! ");
                        } else if (edFirmNoOfEmployee.getText().toString().trim().isEmpty()) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter no of employee!");
                        } else if (edFirmNoOfPartner.getText().toString().trim().isEmpty()) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter no of partner!");
                        } else if (edFirmNatureOfBusiness.getText().toString().trim().isEmpty()) {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter nature of business!");
                        }
                        else  {
                            callWebServiceFirmREST(null, null);
                        }
                    }

                } else {


                  /*  if(profilePicPath.isEmpty() || edFirstName.getText().toString().trim().isEmpty()  || edSpouseFatherName.getText().toString().trim().isEmpty() || edPrimaryContactNo.getText().toString().trim().isEmpty() || tvDob.getText().toString().equalsIgnoreCase("--Pick--")){
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "All Fields are mandatory!");
                    }
*/
                    //                  else {
                    pageClicked = 2;

                    llTabs.setBackgroundResource(R.drawable.active_right);
                    llDemoInfo.setVisibility(View.GONE);
                    llKYCInfo.setVisibility(View.VISIBLE);
                    //                }
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int docCount = 0;
                for (int i = 0; i < llDocs.getChildCount(); i++) {

                    LinearLayout ll = (LinearLayout) llDocs.getChildAt(i);
                    Spinner sp_doc_type = ll.getChildAt(1).findViewById(R.id.sp_doc_type);
                    EditText edDocNo1 = ll.getChildAt(2).findViewById(R.id.edDocNo);
                    EditText edFile = ll.getChildAt(3).findViewById(R.id.edFile);
                    SpinnerModel item = (SpinnerModel) sp_doc_type.getSelectedItem();
                    if (!item.getName().equalsIgnoreCase("Select Document Type") && !edDocNo1.getText().toString().trim().isEmpty() && !edFile.getText().toString().trim().isEmpty()) {
                        docCount = docCount + 1;
                    }

                }

                SpinnerModel spType = (SpinnerModel) sp_type.getSelectedItem();
                if (spType.getName().equalsIgnoreCase("Individual")) {

                    if (profilePicPath.isEmpty()) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please choose profile picture!");
                    } else if (edFirstName.getText().toString().trim().isEmpty()) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter first name!");
                    } else if (edSpouseFatherName.getText().toString().trim().isEmpty()) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Father/Spouse name!");
                    } else if (edPrimaryContactNo.getText().toString().trim().isEmpty()) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Primary contact!");
                    } else if (tvDob.getText().toString().trim().equalsIgnoreCase("--Pick--")) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter DOB!");
                    } else if (!edPrimaryContactNo.getText().toString().trim().isEmpty() && edPrimaryContactNo.getText().toString().trim().length() < 10) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Primary contact no. not valid! ");
                    } else if (!edAlternateNo.getText().toString().trim().isEmpty() && edAlternateNo.getText().toString().trim().length() < 10) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Alternate no. not valid! ");
                    } else if (!edWhatsUpno.getText().toString().trim().isEmpty() && edWhatsUpno.getText().toString().trim().length() < 10) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "WhatsApp no. not valid! ");
                    } else if (!edEmail.getText().toString().trim().isEmpty() && !WebUtility.validEmail(edEmail.getText().toString())) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Email not valid!");
                    } else if (docCount < 1) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "At least one document are mandatory!");
                    } else {

                        getkycDocData();

                    }
                } else {
                    SpinnerModel spModel=(SpinnerModel) sp_firm_type.getSelectedItem();
                    String firmTypeName=spModel.getName();
                    if(firmTypeName.equalsIgnoreCase("--Select--")){
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select firm Type!");
                    }
                    else if (edFirmName.getText().toString().trim().isEmpty()) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter firm name!");
                    } else if (edPrimaryContactNoFirm.getText().toString().trim().isEmpty()) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter Primary contact of firm!");
                    }/* else if (edEmailFirm.getText().toString().trim().isEmpty()) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter email!");
                    }*/ else if (edFirmDateOfIncorporation.getText().toString().trim().isEmpty()) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select date of incorporation!");
                    } else if (!edPrimaryContactNoFirm.getText().toString().trim().isEmpty() && edPrimaryContactNoFirm.getText().toString().trim().length() < 10) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Primary contact no. not valid! ");
                    } else if (!edAlternateNoFirm.getText().toString().trim().isEmpty() && edAlternateNoFirm.getText().toString().trim().length() < 10) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Alternate no. not valid! ");
                    } else if (edFirmNoOfEmployee.getText().toString().trim().isEmpty()) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter no of employee!");
                    } else if (edFirmNoOfPartner.getText().toString().trim().isEmpty()) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter no of partner!");
                    } else if (edFirmNatureOfBusiness.getText().toString().trim().isEmpty()) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter nature of business!");
                    }
                    else if (docCount < 1) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "At least one document are mandatory!");
                    }
                    else {
                        getkycDocData();
                    }
                }


            }
        });

        if (customerDetailObject != null) {
            CustomerId = customerDetailObject.getCustomerId();
            btnNext.setText("Update");
            llTabs.setVisibility(View.GONE);


            if(customerDetailObject.isFirm()){
                firmId=customerDetailObject.getCustomerId();
                llNonIndividual.setVisibility(View.VISIBLE);
                llIndividual.setVisibility(View.GONE);
                sp_type.setSelection(1);
              edFirmName.setText(customerDetailObject.getCustomer_FirstName());
                edFirmNoOfEmployee.setText(String.valueOf(customerDetailObject.getNoofEmployee()));
                edFirmNoOfPartner.setText(String.valueOf(customerDetailObject.getNoOfPartner()));
                edFirmNatureOfBusiness.setText(customerDetailObject.getNature_Of_Business());
                edEmailFirm.setText(customerDetailObject.getCustomer_Email());
                edPrimaryContactNoFirm.setText(customerDetailObject.getCustomer_PhoneNo());
                edAlternateNoFirm.setText(customerDetailObject.getCustomer_PhoneNo1());
                edFirmDateOfIncorporation.setText(customerDetailObject.getDate_Of_Incorruptions());
                String rel = customerDetailObject.getFirmType();
                for (int z = 0; z < arrListFirmType.size(); z++) {
                    if (arrListFirmType.get(z).getName().equalsIgnoreCase(rel)) {
                        sp_firm_type.setSelection(z);
                        break;
                    }

                }


            }

            edFirstName.setText(customerDetailObject.getCustomer_FirstName());
            edLastName.setText(customerDetailObject.getCustomer_LastName());
            edSpouseFatherName.setText(customerDetailObject.getCustomer_FatherName());
            edPrimaryContactNo.setText(customerDetailObject.getCustomer_PhoneNo());

            String rel = customerDetailObject.getCustomer_Relation();
            for (int z = 0; z < arrListCustomerRel.size(); z++) {
                if (arrListCustomerRel.get(z).getName().equalsIgnoreCase(rel)) {
                    sp_customer_relation.setSelection(z);
                    break;
                }

            }

            String ms = customerDetailObject.getCustomer_MaritalStatus();
            for (int z = 0; z < arrListMaritalStatus.size(); z++) {
                if (arrListMaritalStatus.get(z).getName().equalsIgnoreCase(ms)) {
                    sp_marital_status.setSelection(z);
                    break;
                }

            }
            String rl = customerDetailObject.getCustomer_Religion();
            for (int z = 0; z < arrListReligion.size(); z++) {
                if (arrListReligion.get(z).getName().equalsIgnoreCase(rl)) {
                    sp_religion.setSelection(z);
                    break;
                }

            }
            String ct = customerDetailObject.getCustomer_Cast();
            for (int z = 0; z < arrListCast.size(); z++) {
                if (arrListCast.get(z).getName().equalsIgnoreCase(ct)) {
                    sp_cast.setSelection(z);
                    break;
                }

            }

            if (customerDetailObject.getCustomerType().equalsIgnoreCase("H")) {
                sp_customer_type.setSelection(0);

            } else if (customerDetailObject.getCustomerType().equalsIgnoreCase("C")) {
                sp_customer_type.setSelection(1);

            } else if (customerDetailObject.getCustomerType().equalsIgnoreCase("G")) {
                sp_customer_type.setSelection(2);

            }
            Date newDate;
            String date = "";
            try {
                String strCurrentDate = customerDetailObject.getCustomer_DOB();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss a");
                newDate = format.parse(strCurrentDate);

                format = new SimpleDateFormat("dd/MM/yyyy");
                date = format.format(newDate);


            } catch (ParseException e) {
                e.printStackTrace();
            }
            tvDob.setText(date);
            edEmail.setText(customerDetailObject.getCustomer_Email());
            edWhatsUpno.setText(customerDetailObject.getCustomer_WhatsAppNo());
            edAlternateNo.setText(customerDetailObject.getCustomer_PhoneNo1());

            if (customerDetailObject.getCustomer_Gender().equalsIgnoreCase("M")) {
                rbMale.setChecked(true);
                gender = "M";
            } else {
                rbFemale.setChecked(true);
                gender = "F";
            }

            String profilePic = customerDetailObject.getCustomer_ProfilePic();

            if (profilePic != null && !profilePic.isEmpty()) {
                //   String url = Api.BASE_URL + "uploadDoc/wwwroot/Document/Employee/ProfilePic/" + profilePic;

                String url = session.getClientUrl() + "uploadDoc/wwwroot/Document/Customer/" + CustomerId + "/" + profilePic;
                Glide.with(CustomerRegistrationActivity.this)
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
        }

        //  addDirectorRow();
        getDocTypeFromApi();
        //  getCustomerTypeFromApi();

    }

    public void getkycDocData() {

        ArrayList<kycDocModel> arrayList = new ArrayList<>();
        ArrayList<String> arrayListImageData = new ArrayList<>();
        for (int i = 0; i < llDocs.getChildCount(); i++) {

            LinearLayout ll = (LinearLayout) llDocs.getChildAt(i);

            if ((int) ll.getTag() == 0) {

                Spinner sp_doc_type = ll.getChildAt(1).findViewById(R.id.sp_doc_type);
                EditText edDocNo = ll.getChildAt(2).findViewById(R.id.edDocNo);
                EditText edFile = ll.getChildAt(3).findViewById(R.id.edFile);
                SpinnerModel spinnerModelDOC = (SpinnerModel) sp_doc_type.getSelectedItem();
                if (spinnerModelDOC.getName().equalsIgnoreCase("Select Document Type")) {
                    WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select document type! ");
                    return;

                } else if (spinnerModelDOC.getName().equalsIgnoreCase("Aadhaar Card")) {
                    String aadharNumber = edDocNo.getText().toString().trim();
                    boolean res = isValidAadharNumber(aadharNumber);

                    if (!res) {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Aadhaar card not valid! ");
                        return;
                    }

                } else if (spinnerModelDOC.getName().equalsIgnoreCase("Pan Card")) {

                    String Pan = edDocNo.getText().toString().trim();
                    Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

                    Matcher matcher = pattern.matcher(Pan);

                    if (matcher.matches()) {

                    } else {
                        WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Pan card not valid! ");
                        return;
                    }
                }


                if (!edFile.getText().toString().isEmpty()) {


                    String file1 = edFile.getText().toString();
                    String fileName = null;
                    if (file1 != null && !file1.isEmpty()) {
                        fileName = file1.substring(file1.lastIndexOf("/") + 1);

                    }
                    kycDocModel model = new kycDocModel();
                    SpinnerModel sps = (SpinnerModel) sp_doc_type.getSelectedItem();
                    model.setKYC_DocId(Integer.parseInt(sps.getId()));
                    model.setKYC_DocNumber(edDocNo.getText().toString().trim());

                    SpinnerModel spinnerModel = (SpinnerModel) sp_doc_type.getSelectedItem();
                    spinnerModel.getName();

                    String extension = fileName.substring(fileName.lastIndexOf("."));

                    String name = spinnerModel.getName().replaceAll("\\s+", "");
                    model.setKYC_DocFile(name + "_" + gen() + extension);

                    arrayList.add(model);


                    String img_upload_data = "";
                    File file = new File(edFile.getText().toString());
                    InputStream is = null;
                    try {
                        is = new FileInputStream(file);

                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] b = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = is.read(b)) != -1) {
                            bos.write(b, 0, bytesRead);
                        }
                        b = null;
                        byte[] bytes = bos.toByteArray();
                        img_upload_data = Base64.encode(bytes);

                        arrayListImageData.add(img_upload_data);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            }
        }
        SpinnerModel spType = (SpinnerModel) sp_type.getSelectedItem();
        if (spType.getName().equalsIgnoreCase("Individual")) {
            callWebServiceREST(arrayList, arrayListImageData);
        }
        else{
            callWebServiceFirmREST(arrayList, arrayListImageData);
        }

    }

    public boolean isValidAadharNumber(String str) {
        // Regex to check valid Aadhar number.
        String regex
                = "^[2-9]{1}[0-9]{3}\\s[0-9]{4}\\s[0-9]{4}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the string is empty
        // return false
        if (str == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given string
        // and regular expression.
        Matcher m = p.matcher(str);

        // Return if the string
        // matched the ReGex
        return m.matches();
    }

    public boolean validateAadharNumber(String aadharNumber) {
        Pattern aadharPattern = Pattern.compile("\\d{12}");
        boolean isValidAadhar = aadharPattern.matcher(aadharNumber).matches();
        if (isValidAadhar) {
            isValidAadhar = VerhoeffAlgorithm.validateVerhoeff(aadharNumber);
        }
        return isValidAadhar;
    }

    private void callWebServiceUpdate() {

        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerRegistrationActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest();
            ApplicationCustomerModel model = new ApplicationCustomerModel();

            model.setCustomerId(CustomerId);
            model.setCustomer_FirstName(edFirstName.getText().toString());
            model.setCustomer_LastName(edLastName.getText().toString());
            model.setCustomer_FatherName(edSpouseFatherName.getText().toString());
            model.setCustomer_Email(edEmail.getText().toString());
            model.setCustomer_DOB(tvDob.getText().toString());
            model.setCustomer_Gender(gender);
            model.setCustomer_PhoneNo(edPrimaryContactNo.getText().toString());
            model.setCustomer_PhoneNo1(edAlternateNo.getText().toString());
            model.setCustomer_WhatsAppNo(edWhatsUpno.getText().toString());
            //   model.setCustomer_WhatsAppNo(edWhatsUpno.getText().toString());
            model.setLoginUserId(session.getUserId());
            model.setApplicationId(applicationId);
            model.setCustomer_PhoneNo2("");
            SpinnerModel smMS = (SpinnerModel) sp_marital_status.getSelectedItem();
            model.setCustomer_MaritalStatus(smMS.getName());

            SpinnerModel smRL = (SpinnerModel) sp_religion.getSelectedItem();
            model.setCustomer_Religion(smRL.getName());

            SpinnerModel smCT = (SpinnerModel) sp_cast.getSelectedItem();
            model.setCustomer_Cast(smCT.getName());
            String fileName = "";
            if (profilePicPath != null && !profilePicPath.isEmpty()) {
                fileName = profilePicPath.substring(profilePicPath.lastIndexOf("/") + 1);

            } else {
                fileName = customerDetailObject.getCustomer_ProfilePic();
            }

            model.setCustomer_ProfilePic(fileName);
            model.setCustomer_PhoneNo_IsVerified(1);

            //  customerRegistrationRequest.setCustomerKYCDoc(arrayList);
            customerRegistrationRequest.setApplicationCustomer(model);

            SpinnerModel spinnerModel = (SpinnerModel) sp_customer_type.getSelectedItem();
            model.setCustomer_Type(spinnerModel.getId());

            String res = new Gson().toJson(customerRegistrationRequest);
            Log.d("my_json", res);

            //res.replaceAll("\\","");
            //    model.setJson(res);

            CustomerRegMainRequest customerRegMainRequest = new CustomerRegMainRequest();
            customerRegMainRequest.setJson(res);


            Call<List<AcceptRejectResponse>> call = api.updateCustomerDetails(customerRegMainRequest);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        String msg = loginResponse.get(0).getMSG();
                        Toast.makeText(CustomerRegistrationActivity.this, msg, Toast.LENGTH_LONG).show();
                        if (profilePicPath != null && !profilePicPath.isEmpty()) {
                            updateProfilePic(loginResponse.get(0).getCODE());
                            //  uploadProfilePic(arrayListImageData,arrayList,String.valueOf(loginResponse.get(0).getCODE()));
                        } else {
                            Intent intent = new Intent("listRefresh");
                            // You can also include some extra data.
                            intent.putExtra("message", "This is my message!");
                            LocalBroadcastManager.getInstance(CustomerRegistrationActivity.this).sendBroadcast(intent);
                            finish();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateProfilePic(int code) {

        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerRegistrationActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            File file = new File(profilePicPath);
            String fileName = null;
            if (profilePicPath != null && !profilePicPath.isEmpty()) {
                fileName = profilePicPath.substring(profilePicPath.lastIndexOf("/") + 1);

            }


            String img_upload_data = "";

            InputStream is = null;
            try {
                is = new FileInputStream(file);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                b = null;
                byte[] bytes = bos.toByteArray();
                img_upload_data = Base64.encode(bytes);
            } catch (Exception e) {

            }

            UploadCustomerDocRequest model = new UploadCustomerDocRequest();
            model.setDocName(fileName);
            model.setDocData(img_upload_data);
            model.setCustomerID(String.valueOf(customerDetailObject.getCustomerId()));


            Call<Boolean> call = api.uploadCustomerDOC(model);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    Boolean loginResponse = response.body();
                    if (loginResponse != null) {
                        Intent intent = new Intent("listRefresh");
                        // You can also include some extra data.
                        intent.putExtra("message", "This is my message!");
                        LocalBroadcastManager.getInstance(CustomerRegistrationActivity.this).sendBroadcast(intent);
                        finish();
                    }


                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
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

    public View.OnFocusChangeListener onFoucusChangeListener = new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus && edPrimaryContactNo.getText().length() > 0 && !from.equalsIgnoreCase("detail")) {
                getCustomerDuplicationCheckFromApi("PhoneNo", edPrimaryContactNo.getText().toString());
            }
        }
    };


    public int gen() {
        Random r = new Random(System.currentTimeMillis());
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    private void getCustomerDuplicationCheckFromApi(String type, String value) {

       /* final ProgressDialog progress;



        progress = new ProgressDialog(CustomerRegistrationActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();*/

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            CustomerDuplicationRequest model = new CustomerDuplicationRequest();
            model.setType(type);
            model.setValue(value);

            Call<DuplicationCheckResponse> call = api.checkCustomerDuplication(model);

            call.enqueue(new Callback<DuplicationCheckResponse>() {
                @Override
                public void onResponse(Call<DuplicationCheckResponse> call, Response<DuplicationCheckResponse> response) {
                  /*  if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/

                    final DuplicationCheckResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        if (loginResponse.getItem1() != null && loginResponse.getItem1().size() > 0) {

                            AlertDialog.Builder dialogBuilder;
                            final AlertDialog alertDialog;

                            dialogBuilder = new AlertDialog.Builder(CustomerRegistrationActivity.this);
                            final View dialog = getLayoutInflater().inflate(R.layout.dialog_exist_customer_detail, null);

                            dialogBuilder.setView(dialog);
                            alertDialog = dialogBuilder.create();
                            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                         /*   final Dialog dialog = new Dialog(CustomerRegistrationActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog_exist_customer_detail);*/
                            DuplicationCheckResponse.Item1Bean item1 = loginResponse.getItem1().get(0);
                            final TextView tvFirstName = dialog.findViewById(R.id.tvFirstName);
                            tvFirstName.setText(item1.getCustomer_FirstName());


                            final CircleImageView circularImageView = dialog.findViewById(R.id.ivProfile);
                            String profilePic = item1.getCustomer_ProfilePic();

                            if (profilePic != null && !profilePic.isEmpty()) {
                                //   String url = Api.BASE_URL + "uploadDoc/wwwroot/Document/Employee/ProfilePic/" + profilePic;

                                String url = session.getClientUrl()+ "uploadDoc/wwwroot/Document/Customer/" + item1.getCustomerId() + "/" + profilePic;
                                Glide.with(CustomerRegistrationActivity.this)
                                        .load(url)
                                        .apply(RequestOptions.placeholderOf(R.drawable.user_icon).error(R.drawable.user_icon))
                                        .listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        circularImageView.setImageResource(R.drawable.user_icon);
                                                    }
                                                }, 700);


                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                                                return false;
                                            }
                                        })
                                        .into(circularImageView);
                            }

                            final TextView tvLastName = dialog.findViewById(R.id.tvLastName);
                            tvLastName.setText(item1.getCustomer_LastName());
                            final TextView tvFatherName = dialog.findViewById(R.id.tvFatherName);
                            tvFatherName.setText(item1.getCustomer_FatherName());
                            final TextView tvGender = dialog.findViewById(R.id.tvGender);
                            tvGender.setText(item1.getCustomer_Gender());
                            final TextView tvDob1 = dialog.findViewById(R.id.tvDob);
                            tvDob1.setText(item1.getCustomer_DOB());

                            final TextView tvPrimaryNumber = dialog.findViewById(R.id.tvPrimaryNumber);
                            tvPrimaryNumber.setText(item1.getCustomer_PhoneNo());
                            final TextView tvAlternateNumber = dialog.findViewById(R.id.tvAlternateNumber);
                            tvAlternateNumber.setText(item1.getCustomer_PhoneNo1());
                            final TextView tvWhastupNumber = dialog.findViewById(R.id.tvWhastupNumber);
                            tvWhastupNumber.setText(item1.getCustomer_WhatsAppNo());
                            final TextView tvEmail = dialog.findViewById(R.id.tvEmail);
                            tvEmail.setText(item1.getCustomer_Email());
                            Button btn_yes = dialog.findViewById(R.id.btn_yes);
                            Button btn_no = dialog.findViewById(R.id.btn_no);
                            btn_yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    DuplicationCheckResponse.Item1Bean item1 = loginResponse.getItem1().get(0);
                                    String profilePic = item1.getCustomer_ProfilePic();
                                    if (profilePic != null) {
                                        profilePicPath = profilePic;
                                        String url = session.getClientUrl() + "uploadDoc/wwwroot/Document/Customer/" + item1.getCustomerId() + "/" + profilePic;

                                        Glide.with(CustomerRegistrationActivity.this)
                                                .load(url)

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


                                /*    if(item1.getCustomerType().equalsIgnoreCase("H"))
                                    {
                                        sp_customer_type.setSelection(0);

                                    } else if(item1.getCustomerType().equalsIgnoreCase("C"))
                                    {
                                        sp_customer_type.setSelection(1);

                                    }
                                    else if(item1.getCustomerType().equalsIgnoreCase("G"))
                                    {
                                        sp_customer_type.setSelection(2);

                                    }*/


                                    CustomerId = item1.getCustomerId();
                                    edFirstName.setText(item1.getCustomer_FirstName());
                                    edLastName.setText(item1.getCustomer_LastName());
                                    edSpouseFatherName.setText(item1.getCustomer_FatherName());
                                    tvDob.setText(item1.getCustomer_DOB());
                                    edEmail.setText(item1.getCustomer_Email());
                                    edPrimaryContactNo.setText(item1.getCustomer_PhoneNo());
                                    edWhatsUpno.setText(item1.getCustomer_WhatsAppNo());
                                    edAlternateNo.setText(item1.getCustomer_PhoneNo1());

                                    if (item1.getCustomer_Gender().equalsIgnoreCase("M")) {
                                        rbMale.setChecked(true);
                                        gender = "M";
                                    } else {
                                        rbFemale.setChecked(true);
                                        gender = "F";
                                    }


                                    llDocs.removeAllViews();

                                    for (int i = 0; i < loginResponse.getItem2().size(); i++) {

                                        DuplicationCheckResponse.Item2Bean item = loginResponse.getItem2().get(i);

                                        if (i == 0) {
                                            index = 1;
                                        } else {
                                            index = index + 1;
                                        }
                                        addItem(item);

                                    }
                                    alertDialog.dismiss();
                                }
                            });
                            btn_no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    alertDialog.dismiss();
                                }
                            });

                            alertDialog.show();
                            // Window window = dialog.getWindow();
                            //window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);


                        }


                    }


                }

                @Override
                public void onFailure(Call<DuplicationCheckResponse> call, Throwable t) {
                   /* if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDocTypeFromApi() {

        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerRegistrationActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            DocMasterRequest model = new DocMasterRequest();
            model.setDocCategory("KYC");

            Call<List<DocTypeResponse>> call = api.getDocumentMaster(model);

            call.enqueue(new Callback<List<DocTypeResponse>>() {
                @Override
                public void onResponse(Call<List<DocTypeResponse>> call, Response<List<DocTypeResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<DocTypeResponse> list = response.body();
                    if (list != null) {


                        SpinnerModel itempe = new SpinnerModel();
                        itempe.setId("-1");
                        itempe.setName("Select Document Type");
                        arrListDocument.add(itempe);

                        for (int i = 0; i < list.size(); i++) {
                            SpinnerModel itemp = new SpinnerModel();
                            itemp.setId(String.valueOf(list.get(i).getDocId()));
                            itemp.setName(list.get(i).getDocumnet());
                            arrListDocument.add(itemp);
                        }


                        addDocRow();


                    }


                }

                @Override
                public void onFailure(Call<List<DocTypeResponse>> call, Throwable t) {
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

    public void addDirectorRow() {
        final LinearLayout _itemRow = (LinearLayout) getLayoutInflater().inflate(R.layout.view_director_info, llDirectorInfo, false);
        _itemRow.setTag(0);
        llDirectorInfo.addView(_itemRow);
    }

    public void addDocRow() {
        final LinearLayout _itemRow = (LinearLayout) getLayoutInflater().inflate(R.layout.row_customer_doc, llDocs, false);
        _itemRow.setTag(0);


        final Spinner sp_doc_type = _itemRow.findViewById(R.id.sp_doc_type);
        sp_doc_type.setTag(index);
        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerRegistrationActivity.this, arrListDocument);
        sp_doc_type.setAdapter(enqiryAdapter);

        EditText edFile = _itemRow.findViewById(R.id.edFile);
        final EditText edDocNo = _itemRow.findViewById(R.id.edDocNo);
        edDocNo.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        final ImageView ivClose = _itemRow.findViewById(R.id.ivClose);
        edFile.setTag(index);
        edDocNo.setTag(index);
        ivClose.setTag(index);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int indexItem = (int) v.getTag();
                llDocs.removeView(_itemRow);
            }
        });

        edDocNo.addTextChangedListener(new TextWatcher() {

            private static final int TOTAL_SYMBOLS = 14; // size of pattern 0000-0000-0000-0000
            private static final int TOTAL_DIGITS = 12; // max numbers of digits in pattern: 0000 x 4
            private static final int DIVIDER_MODULO = 5; // means divider position is every 5th symbol beginning with 1
            private static final int DIVIDER_POSITION = DIVIDER_MODULO - 1; // means divider position is every 4th symbol beginning with 0
            private static final char DIVIDER = ' ';

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // noop
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // noop
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                if (!s1.equals(s1.toUpperCase())) {
                    s1 = s1.toUpperCase();
                    edDocNo.setText(s1);
                    edDocNo.setSelection(edDocNo.length()); //fix reverse texting
                }
                SpinnerModel spinnerModel = (SpinnerModel) sp_doc_type.getSelectedItem();


                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER) && spinnerModel.getName().equalsIgnoreCase("aadhaar card")) {
                    s.replace(0, s.length(), buildCorrectString(getDigitArray(s, TOTAL_DIGITS), DIVIDER_POSITION, DIVIDER));
                }
            }

            private boolean isInputCorrect(Editable s, int totalSymbols, int dividerModulo, char divider) {
                boolean isCorrect = s.length() <= totalSymbols; // check size of entered string
                for (int i = 0; i < s.length(); i++) { // check that every element is right
                    if (i > 0 && (i + 1) % dividerModulo == 0) {
                        isCorrect &= divider == s.charAt(i);
                    } else {
                        isCorrect &= Character.isDigit(s.charAt(i));
                    }
                }
                return isCorrect;
            }

            private String buildCorrectString(char[] digits, int dividerPosition, char divider) {
                final StringBuilder formatted = new StringBuilder();

                for (int i = 0; i < digits.length; i++) {
                    if (digits[i] != 0) {
                        formatted.append(digits[i]);
                        if ((i > 0) && (i < (digits.length - 1)) && (((i + 1) % dividerPosition) == 0)) {
                            formatted.append(divider);
                        }
                    }
                }

                return formatted.toString();
            }

            private char[] getDigitArray(final Editable s, final int size) {
                char[] digits = new char[size];
                int index = 0;
                for (int i = 0; i < s.length() && index < size; i++) {
                    char current = s.charAt(i);
                    if (Character.isDigit(current)) {
                        digits[index] = current;
                        index++;
                    }
                }
                return digits;
            }
        });


        sp_doc_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerModel spinnerModel = (SpinnerModel) sp_doc_type.getSelectedItem();
                edDocNo.setText("");
                if (spinnerModel.getName().equalsIgnoreCase("aadhaar card")) {
                    edDocNo.setInputType(InputType.TYPE_CLASS_PHONE);
                } else {
                    edDocNo.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edDocNo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if (edDocNo.getText().length() > 4) {
                        SpinnerModel model = (SpinnerModel) sp_doc_type.getSelectedItem();
                        getCustomerDuplicationCheckFromApi(model.getName(), edDocNo.getText().toString());
                    }
                }
                return false;
            }
        });
/*
        edDocNo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && edDocNo.getText().length()>0){
                    getCustomerDuplicationCheckFromApi("KYC",edDocNo.getText().toString());
                }
            }
        });*/
        final Button btnBrowse = _itemRow.findViewById(R.id.btnBrowse);
        btnBrowse.setTag(index);
        llDocs.addView(_itemRow);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedIndex = (int) btnBrowse.getTag();
                isProfileClicked = false;
                final Dialog dialog = new Dialog(CustomerRegistrationActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_choose_content);


                Button btnCamera = dialog.findViewById(R.id.btnCamera);
                Button btnGallery = dialog.findViewById(R.id.btnGallery);
                btnCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        pickCamera();
                    }
                });
                btnGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        pickFile();
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    private void showDatePickerDialog(EditText edittext) {
        int year = 0, month = 0, day = 0;

        //  if (tvDob.getText().toString().equals("")) {
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        //  } else {
        //     String[] date = tvDob.getText().toString().split("-");
        //    year = Integer.parseInt(date[2]);
        //    month = Integer.parseInt(date[1]) - 1;
        //    day = Integer.parseInt(date[0]);
        // }
        DatePickerFragment2 pickerFragment = new DatePickerFragment2(false, CustomerRegistrationActivity.this);
        pickerFragment.setContext(CustomerRegistrationActivity.this);
        pickerFragment.setEdittext(edittext);
        datePickerDialog = new DatePickerDialog(CustomerRegistrationActivity.this, pickerFragment, year, month, day);
        datePickerDialog.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);

        if (edittext.getId() == R.id.edFirmDateOfIncorporation) {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        } else {
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 568024668000l);
        }

        datePickerDialog.show();
    }

    @Override
    public void DateOnClicked(String date) {

    }

    private void pickFile() {
        if (ActivityCompat.checkSelfPermission(CustomerRegistrationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CustomerRegistrationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(CustomerRegistrationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 22);
        }


    }


    public void pickCamera() {
        if (ActivityCompat.checkSelfPermission(CustomerRegistrationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CustomerRegistrationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CustomerRegistrationActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(CustomerRegistrationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,}, 23);
        }

    }

    public void openGallery() {


        String[] mimeTypes =
                {"application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "text/plain",
                        "application/pdf",
                        "image/*"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "image/*|application/pdf|text/plain|application/vnd.openxmlformats-officedocument.wordprocessingml.document|application/msword");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0, mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent, "ChooseFile"), RESULT_LOAD_IMAGE);
    }

    public void callWebServiceREST(final ArrayList<kycDocModel> arrayList, final ArrayList<String> arrayListImageData) {
        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerRegistrationActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            CustomerRegistrationRequest customerRegistrationRequest = new CustomerRegistrationRequest();
            ApplicationCustomerModel model = new ApplicationCustomerModel();

            model.setCustomerId(CustomerId);
            model.setCustomer_FirstName(edFirstName.getText().toString());
            model.setCustomer_LastName(edLastName.getText().toString());
            model.setCustomer_FatherName(edSpouseFatherName.getText().toString());
            model.setCustomer_Email(edEmail.getText().toString());
            model.setCustomer_DOB(tvDob.getText().toString());
            model.setCustomer_Gender(gender);
            model.setCustomer_PhoneNo(edPrimaryContactNo.getText().toString());
            model.setCustomer_PhoneNo1(edAlternateNo.getText().toString());
            model.setCustomer_WhatsAppNo(edWhatsUpno.getText().toString());

            SpinnerModel smMS = (SpinnerModel) sp_marital_status.getSelectedItem();
            model.setCustomer_MaritalStatus(smMS.getName());

            SpinnerModel smRL = (SpinnerModel) sp_religion.getSelectedItem();
            model.setCustomer_Religion(smRL.getName());

            SpinnerModel smCT = (SpinnerModel) sp_cast.getSelectedItem();
            model.setCustomer_Cast(smCT.getName());

            //   model.setCustomer_WhatsAppNo(edWhatsUpno.getText().toString());
            SpinnerModel spm = (SpinnerModel) sp_customer_type.getSelectedItem();
            if (spm.getName().equalsIgnoreCase("Hirer")) {
                model.setCustomer_Relation("Own");
            } else {
                SpinnerModel spm1 = (SpinnerModel) sp_customer_relation.getSelectedItem();
                model.setCustomer_Relation(spm1.getName());
            }


            model.setLoginUserId(session.getUserId());
            model.setApplicationId(applicationId);
            model.setCustomer_PhoneNo2("");
            File file = new File(profilePicPath);
            String fileName = null;
            if (profilePicPath != null && !profilePicPath.isEmpty()) {
                fileName = profilePicPath.substring(profilePicPath.lastIndexOf("/") + 1);

            }

            model.setCustomer_ProfilePic(fileName);
            model.setCustomer_PhoneNo_IsVerified(1);

            customerRegistrationRequest.setCustomerKYCDoc(arrayList);
            customerRegistrationRequest.setApplicationCustomer(model);

            SpinnerModel spinnerModel = (SpinnerModel) sp_customer_type.getSelectedItem();
            model.setCustomer_Type(spinnerModel.getId());

            String res = new Gson().toJson(customerRegistrationRequest);
            Log.d("my_json", res);

            //res.replaceAll("\\","");
            //    model.setJson(res);

            CustomerRegMainRequest customerRegMainRequest = new CustomerRegMainRequest();
            customerRegMainRequest.setJson(res);


            Call<List<AcceptRejectResponse>> call = api.saveAppCustomer(customerRegMainRequest);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        String msg = loginResponse.get(0).getMSG();
                        Toast.makeText(CustomerRegistrationActivity.this, msg, Toast.LENGTH_LONG).show();


                        uploadProfilePic(arrayListImageData, arrayList, String.valueOf(loginResponse.get(0).getCODE()));


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


    public void callWebServiceFirmREST(final ArrayList<kycDocModel> arrayList, final ArrayList<String> arrayListImageData) {
        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerRegistrationActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            FirmSaveBaseModel customerRegistrationRequest = new FirmSaveBaseModel();
            FirmSaveChildModel model = new FirmSaveChildModel();
            SpinnerModel spinnerModelFirmType = (SpinnerModel) sp_firm_type.getSelectedItem();
            SpinnerModel spinnerModel = (SpinnerModel) sp_customer_type.getSelectedItem();
            model.setFirmId(firmId);
            model.setFirm_Type(spinnerModelFirmType.getName());
            model.setFirm_Name(edFirmName.getText().toString());
            model.setFirm_PhoneNo(edPrimaryContactNoFirm.getText().toString());
            model.setFirm_PhoneNo1(edAlternateNoFirm.getText().toString());
            model.setFirm_Email(edEmailFirm.getText().toString());
            model.setFirm_Date_Of_Incorruptions(edFirmDateOfIncorporation.getText().toString());
            model.setFirm_CustomerType(spinnerModel.getId());

            model.setFirm_No_Of_Employee(Integer.parseInt(edFirmNoOfEmployee.getText().toString()));
            model.setFirm_No_Of_Partner(Integer.parseInt(edFirmNoOfPartner.getText().toString()));
            model.setFirm_GrossValue(0);
            model.setFirm_PhoneNoIsVerified(1);
            model.setFirm_Nature_Of_Business(edFirmNatureOfBusiness.getText().toString());
            model.setFirm_HirerRelation("Own");
            model.setApplicationId(applicationId);
            model.setLoginUserId(session.getUserId());



            customerRegistrationRequest.setCustomerKYCDoc(arrayList);
            customerRegistrationRequest.setFirm(model);


            String res = new Gson().toJson(customerRegistrationRequest);
            Log.d("my_json", res);

            //res.replaceAll("\\","");
            //    model.setJson(res);

            CustomerRegMainRequest customerRegMainRequest = new CustomerRegMainRequest();
            customerRegMainRequest.setJson(res);


            Call<List<AcceptRejectResponse>> call = api.saveAppFirm(customerRegMainRequest);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        String msg = loginResponse.get(0).getMSG();
                        Toast.makeText(CustomerRegistrationActivity.this, msg, Toast.LENGTH_LONG).show();


                      //  uploadProfilePic(arrayListImageData, arrayList, String.valueOf(loginResponse.get(0).getCODE()));
                        final String s=String.valueOf(loginResponse.get(0).getCODE());

                        if (arrayListImageData!=null && arrayListImageData.size() > 0) {

                            for (int i = 0; i < arrayListImageData.size(); i++) {

                                String customerId = s;
                                saveDocumentFromApi(customerId, arrayList.get(i).getKYC_DocFile(), arrayListImageData.get(i));
                            }
                        } else {

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Write whatever to want to do after delay specified (1 sec)
                                    Intent intent = new Intent(CustomerRegistrationActivity.this, CustomerDetailActivity.class);
                                    intent.putExtra("applicationId", applicationId);
                                    intent.putExtra("appNo", getIntent().getStringExtra("appNo"));
                                    intent.putExtra("product", product);
                                    intent.putExtra("loanAmount", loanAmount + "");
                                    intent.putExtra("CustomerId", Integer.parseInt(s));
                                    //     intent.putExtra("profilePic",response.getProfilePic());
                                    intent.putExtra("TAG", TAG);
                                    CustomerRegistrationActivity.this.startActivity(intent);
                                    finish();
                                }
                            }, 1000);
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void uploadProfilePic(final ArrayList<String> arrayListImageData, final ArrayList<kycDocModel> arrayList, final String s) {

        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerRegistrationActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            File file = new File(profilePicPath);
            String fileName = null;
            if (profilePicPath != null && !profilePicPath.isEmpty()) {
                fileName = profilePicPath.substring(profilePicPath.lastIndexOf("/") + 1);

            }


            String img_upload_data = "";

            InputStream is = null;
            try {
                is = new FileInputStream(file);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] b = new byte[1024];
                int bytesRead;
                while ((bytesRead = is.read(b)) != -1) {
                    bos.write(b, 0, bytesRead);
                }
                b = null;
                byte[] bytes = bos.toByteArray();
                img_upload_data = Base64.encode(bytes);
            } catch (Exception e) {

            }

            UploadCustomerDocRequest model = new UploadCustomerDocRequest();
            model.setDocName(fileName);
            model.setDocData(img_upload_data);
            model.setCustomerID(s);


            Call<Boolean> call = api.uploadCustomerDOC(model);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    Boolean loginResponse = response.body();
                    if (loginResponse != null && loginResponse) {

                        if (arrayListImageData.size() > 0) {

                            for (int i = 0; i < arrayListImageData.size(); i++) {

                                String customerId = s;
                                saveDocumentFromApi(customerId, arrayList.get(i).getKYC_DocFile(), arrayListImageData.get(i));
                            }
                        } else {

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Write whatever to want to do after delay specified (1 sec)
                                    Intent intent = new Intent(CustomerRegistrationActivity.this, CustomerDetailActivity.class);
                                    intent.putExtra("applicationId", applicationId);
                                    intent.putExtra("appNo", getIntent().getStringExtra("appNo"));
                                    intent.putExtra("product", product);
                                    intent.putExtra("loanAmount", loanAmount + "");
                                    intent.putExtra("CustomerId", Integer.parseInt(s));
                                    //     intent.putExtra("profilePic",response.getProfilePic());
                                    intent.putExtra("TAG", TAG);
                                    CustomerRegistrationActivity.this.startActivity(intent);
                                    finish();
                                }
                            }, 1000);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Some error in saving profile pic!", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Data", requestCode + "  " + resultCode);
        switch (requestCode) {


            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE: {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    progressDialog1 = new ProgressDialog(CustomerRegistrationActivity.this);
                    progressDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    progressDialog1.setMessage("Getting file path... \n(This may take some time depending upon size of File)");
                    progressDialog1.setCancelable(false);
                    progressDialog1.show();
                    pickiT.getPath(resultUri, Build.VERSION.SDK_INT);

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
            }
            break;


            case RESULT_LOAD_IMAGE:

                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data.getData();


                    if (isProfileClicked) {

                        cropImage(selectedImageUri);
                    } else {
                        progressDialog1 = new ProgressDialog(CustomerRegistrationActivity.this);
                        progressDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        progressDialog1.setMessage("Getting file path... \n(This may take some time depending upon size of File)");
                        progressDialog1.setCancelable(false);
                        progressDialog1.show();

                        pickiT.getPath(selectedImageUri, Build.VERSION.SDK_INT);
                    }

                }

                break;
            case CAMERA_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    // selectedImageUri = data.getData();

                    if (isProfileClicked) {
                        cropImage(selectedImageUri);
                    } else {
                        progressDialog1 = new ProgressDialog(CustomerRegistrationActivity.this);
                        progressDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        progressDialog1.setMessage("Getting file path... \n(This may take some time depending upon size of File)");
                        progressDialog1.setCancelable(false);
                        progressDialog1.show();

                        pickiT.getPath(selectedImageUri, Build.VERSION.SDK_INT);
                    }
                }
                break;
            case UCrop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    handleUCropResult(data);
                } else {
                    setResultCancelled();
                }
                break;
            case UCrop.RESULT_ERROR:
                final Throwable cropError = UCrop.getError(data);
                // Log.e(TAG, "Crop error: " + cropError);
                setResultCancelled();
                break;
            default:
                break;
        }
    }

    private void handleUCropResult(Intent data) {
        if (data == null) {
            setResultCancelled();
            return;
        }
        final Uri resultUri = UCrop.getOutput(data);
        progressDialog1 = new ProgressDialog(CustomerRegistrationActivity.this);
        progressDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog1.setMessage("Getting file path... \n(This may take some time depending upon size of File)");
        progressDialog1.setCancelable(false);
        progressDialog1.show();

        pickiT.getPath(resultUri, Build.VERSION.SDK_INT);
    }

    private void setResultCancelled() {
        Intent intent = new Intent();
        setResult(Activity.RESULT_CANCELED, intent);
        // finish();
    }

    private void cropImage(Uri sourceUri) {
        CropImage.activity(sourceUri)
                .start(this);
    }

    private static String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

    @Override
    public void PickiTonUriReturned() {

    }

    @Override
    public void PickiTonStartListener() {

    }

    @Override
    public void PickiTonProgressUpdate(int progress) {

    }

    @Override
    public void PickiTonCompleteListener(String path, boolean wasDriveFile, boolean wasUnknownProvider, boolean wasSuccessful, String Reason) {
        progressDialog1.dismiss();
        if (path != null) {

            CompressImage.compress(path);
            File filenew = new File(path);
            int file_size = Integer.parseInt(String.valueOf(filenew.length() / 1024));
          /*  if (file_size > 5120) {
                selectedImageUri=null;
                //  CommonUtil.errorAleart(getActivity(), BuildConfig.AppName, "Max. file size is 150 mb allowed only.");
            }
            else {*/


            if (isProfileClicked) {
                profilePicPath = path;
                //   final Bitmap myBitmap = BitmapFactory.decodeFile(filenew.getAbsolutePath());

                //    ivProfile.setImageBitmap(myBitmap);

                Glide.with(CustomerRegistrationActivity.this)
                        .load(profilePicPath)
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


            } else {

                for (int i = 0; i < llDocs.getChildCount(); i++) {

                    LinearLayout ll = (LinearLayout) llDocs.getChildAt(i);

                    EditText edFile = ll.getChildAt(3).findViewById(R.id.edFile);

                    if ((int) edFile.getTag() == clickedIndex) {
                        edFile.setText(path);

                    }
                }
            }

            //    }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 22:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {

                }
                break;
            case 23:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {

                }
                break;
        }
    }

    private void saveDocumentFromApi(final String customerId, String kyc_docFile, String img_upload_data) {

        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerRegistrationActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            UploadCustomerDocRequest model = new UploadCustomerDocRequest();
            model.setCustomerID(customerId);
            model.setDocData(img_upload_data);
            model.setDocName(kyc_docFile);

            Call<Boolean> call = api.uploadCustomerDOC(model);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    Boolean loginResponse = response.body();
                    if (loginResponse != null) {

                        Toast.makeText(CustomerRegistrationActivity.this, "Document save successfully", Toast.LENGTH_LONG).show();

                      /*  Intent intent = new Intent("listRefresh");
                        // You can also include some extra data.
                        intent.putExtra("message", "This is my message!");
                        LocalBroadcastManager.getInstance(CustomerRegistrationActivity.this).sendBroadcast(intent);
                        finish();*/
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Write whatever to want to do after delay specified (1 sec)
                                Intent intent = new Intent(CustomerRegistrationActivity.this, CustomerDetailActivity.class);
                                intent.putExtra("applicationId", applicationId);
                                intent.putExtra("appNo", getIntent().getStringExtra("appNo"));
                                intent.putExtra("product", product);
                                intent.putExtra("loanAmount", loanAmount + "");
                                intent.putExtra("CustomerId", Integer.parseInt(customerId));
                                //     intent.putExtra("profilePic",response.getProfilePic());
                                intent.putExtra("TAG", TAG);
                                CustomerRegistrationActivity.this.startActivity(intent);
                                finish();
                            }
                        }, 1000);


                    }


                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
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

    @Override
    public void onBackPressed() {
        if (pageClicked == 1) {
            super.onBackPressed();
        } else {
            pageClicked = 1;
          /*  btnDemoInfo.setBackgroundResource(R.color.colordemoinfo);
            btnKYCInfo.setBackgroundResource(R.color.colorkycinfo);
            btnDemoInfo.setTextColor(Color.WHITE);
            btnKYCInfo.setTextColor(Color.BLACK);*/
            llTabs.setBackgroundResource(R.drawable.active_left);
            llDemoInfo.setVisibility(View.VISIBLE);
            llKYCInfo.setVisibility(View.GONE);
        }
    }

    public void addItem(DuplicationCheckResponse.Item2Bean item) {
        LinearLayout _itemRow = (LinearLayout) getLayoutInflater().inflate(R.layout.row_customer_doc, llDocs, false);
        _itemRow.setTag(1);

        EditText edDocNo = _itemRow.findViewById(R.id.edDocNo);
        edDocNo.setText(item.getKYC_DocNumber());
        Spinner sp_doc_type = _itemRow.findViewById(R.id.sp_doc_type);
        sp_doc_type.setTag(index);
        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerRegistrationActivity.this, arrListDocument);
        sp_doc_type.setAdapter(enqiryAdapter);

        for (int i = 0; i < arrListDocument.size(); i++) {

            int id = Integer.parseInt(arrListDocument.get(i).getId());
            if (id == item.getKYC_DocId()) {

                sp_doc_type.setSelection(i);
                break;

            }
        }

        EditText edFile = _itemRow.findViewById(R.id.edFile);
        edFile.setTag(index);
        edFile.setText(item.getKYC_DocFile());
        final Button btnBrowse = _itemRow.findViewById(R.id.btnBrowse);
        btnBrowse.setTag(index);
        llDocs.addView(_itemRow);
        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedIndex = (int) btnBrowse.getTag();
                isProfileClicked = false;
                final Dialog dialog = new Dialog(CustomerRegistrationActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_choose_content);


                Button btnCamera = dialog.findViewById(R.id.btnCamera);
                Button btnGallery = dialog.findViewById(R.id.btnGallery);
                btnCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        pickCamera();
                    }
                });
                btnGallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        pickFile();
                    }
                });

                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    public void openCamera() {

        String fileName = "temp.jpg";
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, fileName);
        selectedImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, selectedImageUri);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        values.clear();
    }

    public void callWebServiceGETOTPREST(String phoneNo) {
        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerRegistrationActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            Call<List<GenerateOTPResponse>> call = null;


            GenerateOTPRequest request = new GenerateOTPRequest();
            request.setLoginUserId(session.getUserId());
            request.setPhoneNo(phoneNo);
            request.setCustomerId(0);
            call = api.generateOTP(request);


            call.enqueue(new Callback<List<GenerateOTPResponse>>() {
                @Override
                public void onResponse(Call<List<GenerateOTPResponse>> call, Response<List<GenerateOTPResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    final List<GenerateOTPResponse> list = response.body();
                    if (list != null) {
                        sendSMSAPI(list.get(0).getOTPCode());
                        AlertDialog.Builder dialogBuilder;
                        final AlertDialog alertDialog;

                        dialogBuilder = new AlertDialog.Builder(CustomerRegistrationActivity.this);
                        final View dialog = getLayoutInflater().inflate(R.layout.dialog_enter_otp, null);

                        dialogBuilder.setView(dialog);
                        alertDialog = dialogBuilder.create();
                        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                        final EditText edOTP = dialog.findViewById(R.id.edOTP);
                        Button btnOk = dialog.findViewById(R.id.btnOk);
                        TextView btnResend = dialog.findViewById(R.id.btnResend);
                        btnResend.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sendSMSAPI(list.get(0).getOTPCode());
                            }
                        });


                        Button btnCancel = dialog.findViewById(R.id.btnCancel);
                        btnOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (edOTP.getText().toString().trim().isEmpty()) {


                                    WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter OTP!");
                                } else {
                                    alertDialog.dismiss();

                                    callWebServiceVERIFY(edOTP.getText().toString());
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
                    }


                }

                @Override
                public void onFailure(Call<List<GenerateOTPResponse>> call, Throwable t) {
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

    public void callWebServiceVERIFY(String s) {
        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerRegistrationActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            Call<List<AcceptRejectResponse>> call = null;


            SaveOTPVerifyRequest request = new SaveOTPVerifyRequest();
            request.setOTPCode(Integer.parseInt(s));
            request.setPhoneNo(edPrimaryContactNo.getText().toString());

            call = api.saveOTPVerification(request);


            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<AcceptRejectResponse> list = response.body();
                    if (list != null) {

                        if (list.get(0).getCODE() == 0) {
                            btn_get_otp.setVisibility(View.GONE);
                            ivVerify.setVisibility(View.VISIBLE);
                            tvVerifiedText.setVisibility(View.VISIBLE);
                        } else {
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "OTP not verified!");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendSMSAPI(int s) {
      /*  final ProgressDialog progress;


        progress = new ProgressDialog(CustomerRegistrationActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();*/

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            Call<Integer> call = null;


            SendMessageRequest request = new SendMessageRequest();
            request.setMobileNo(edPrimaryContactNo.getText().toString());
            request.setSentMessage(s + " is your OTP to validate your Mobile No");

            call = api.sendSMS(request);


            call.enqueue(new Callback<Integer>() {
                @Override
                public void onResponse(Call<Integer> call, Response<Integer> response) {
                  /*  if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/
                  /*  List<AcceptRejectResponse> list = response.body();
                    if(list!=null){

                        if(list.get(0).getCODE()==0){
                            btn_get_otp.setVisibility(View.GONE);
                            ivVerify.setVisibility(View.VISIBLE);
                            tvVerifiedText.setVisibility(View.VISIBLE);
                        }
                        else{
                            WebUtility.showOkDialog(CustomerRegistrationActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "OTP not verified!");
                        }

                    }*/


                }

                @Override
                public void onFailure(Call<Integer> call, Throwable t) {
                   /* if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
