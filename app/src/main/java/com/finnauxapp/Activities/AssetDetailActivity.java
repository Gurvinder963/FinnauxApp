package com.finnauxapp.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Adapter.AssetDetailAdapter;
import com.finnauxapp.Adapter.CustomerExistingLoanAdapter;
import com.finnauxapp.Adapter.QuestionAnswerAdapter;
import com.finnauxapp.ApiRequest.ApplicationDetailRequest;
import com.finnauxapp.ApiRequest.CustomerDetailRequest;
import com.finnauxapp.ApiRequest.CustomerIncomeBaseRequest;
import com.finnauxapp.ApiRequest.CustomerRegMainRequest;
import com.finnauxapp.ApiRequest.SaveAnswerBaseModel;
import com.finnauxapp.ApiRequest.SaveAnswerChildModel;
import com.finnauxapp.ApiRequest.SaveAssetBaseModel;
import com.finnauxapp.ApiRequest.SaveAssetChileModel;
import com.finnauxapp.ApiRequest.SaveIncomeDetailRequest;
import com.finnauxapp.ApiRequest.UploadApplicationDocRequest;
import com.finnauxapp.ApiRequest.UploadCustomerDocRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.AssetListResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.ExistingLoanResponse;
import com.finnauxapp.ApiResponse.QuestionAnswerResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.Base64;
import com.finnauxapp.Util.CompressImage;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.pickit.PickiT;
import com.finnauxapp.pickit.PickiTCallbacks;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AssetDetailActivity extends AppCompatActivity implements PickiTCallbacks {
    private SessionManager session;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;
    int uploadedCount=0;
    private CustomerDetailResponse customerDetailObject;
    private int applicationId;
    private AssetDetailAdapter adpter;
    private Uri selectedImageUri;
    private ProgressDialog progressDialog1;
    final int RESULT_LOAD_IMAGE = 1;
    final int CAMERA_REQUEST = 2;
    private PickiT pickiT;
    private EditText mEditText;
    private AssetListResponse mResponse;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);
        pickiT = new PickiT(AssetDetailActivity.this, AssetDetailActivity.this, AssetDetailActivity.this);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Asset Detail");

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
                Intent intent = new Intent(AssetDetailActivity.this, TabActivity.class);
                startActivity(intent);
            }
        });


        session = new SessionManager(AssetDetailActivity.this);
        customerDetailObject = (CustomerDetailResponse) getIntent().getSerializableExtra("customerDetailObject");

        applicationId = getIntent().getIntExtra("applicationId", 0);
        TextView tvCustomerName = findViewById(R.id.tvCustomerName);

       tvCustomerName.setText("Loan Product : "+getIntent().getStringExtra("product"));
        TextView tvAppNo = findViewById(R.id.tvAppNo);
        tvAppNo.setText("Application No : " + getIntent().getStringExtra("appNo"));

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
                uploadedCount=0;
                if (adpter != null && adpter.getList().size() > 0) {


                    for(int i=0;i<adpter.getList().size();i++){

                        if(adpter.getList().get(i).isMandatory() && (adpter.getList().get(i).getAnswer()==null || adpter.getList().get(i).getAnswer().trim().isEmpty())){
                            WebUtility.showOkDialog(AssetDetailActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please fill mandatory fields. ");
                            return;
                        }

                    }

                    saveWebServiceREST();
                }
            }
        });

        callWebServiceREST();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void callWebServiceREST() {
        final ProgressDialog progress;


        progress = new ProgressDialog(AssetDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            ApplicationDetailRequest model = new ApplicationDetailRequest();
            // model.setCustomerId(customerDetailObject.getCustomerId());
            model.setApplicationId(String.valueOf(applicationId));


            Call<List<AssetListResponse>> call = api.getLOS_GetAssetDetailAppp(model);

            call.enqueue(new Callback<List<AssetListResponse>>() {
                @Override
                public void onResponse(Call<List<AssetListResponse>> call, Response<List<AssetListResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<AssetListResponse> list = response.body();

                    if(list!=null) {
                        adpter = new AssetDetailAdapter(AssetDetailActivity.this, list);
                        recycler_view.setAdapter(adpter);
                    }
                }

                @Override
                public void onFailure(Call<List<AssetListResponse>> call, Throwable t) {
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

    public void saveWebServiceREST() {
        final ProgressDialog progress;


        progress = new ProgressDialog(AssetDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            //   SpinnerModel spinnerModel=(SpinnerModel)sp_loan_type.getSelectedItem();


            List<AssetListResponse> list = adpter.getList();
            ArrayList<SaveAssetChileModel> FIAnswer = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {

                SaveAssetChileModel saveAnswerChildModel = new SaveAssetChileModel();
                saveAnswerChildModel.setLoginUserId(session.getUserId());
                saveAnswerChildModel.setApplicationId(applicationId);

                if(list.get(i).getQuestionType().equalsIgnoreCase("Upload")){
                    String path=list.get(i).getAnswer();

                    if (path != null && !path.isEmpty()) {
                        path = path.substring(path.lastIndexOf("/") + 1);
                        saveAnswerChildModel.setAnswer(path);

                    }
                }
                else{
                    saveAnswerChildModel.setAnswer(list.get(i).getAnswer());
                }


                saveAnswerChildModel.setQuestionId(list.get(i).getAssetId());
                FIAnswer.add(saveAnswerChildModel);

            }

            SaveAssetBaseModel saveAnswerBaseModel = new SaveAssetBaseModel();
            saveAnswerBaseModel.setAssetDetails(FIAnswer);


            String res = new Gson().toJson(saveAnswerBaseModel);
            Log.d("my_json", res);


            CustomerRegMainRequest customerRegMainRequest = new CustomerRegMainRequest();
            customerRegMainRequest.setJson(res);

            Call<List<AcceptRejectResponse>> call = api.saveAssetDetailsApp(customerRegMainRequest);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        String msg = loginResponse.get(0).getMSG();
                        Toast.makeText(AssetDetailActivity.this, msg, Toast.LENGTH_LONG).show();
                        //  finish();


                        int docCount = 0;
                        List<AssetListResponse> list = adpter.getList();


                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getQuestionType().equalsIgnoreCase("Upload")) {
                                String docFile = list.get(i).getAnswer();
                                if (docFile!=null && !docFile.isEmpty()) {
                                    docCount = docCount + 1;
                                }
                            }
                        }

                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getQuestionType().equalsIgnoreCase("Upload")) {

                                String img_upload_data = "";
                                String docFile = list.get(i).getAnswer();

                                if (docFile!=null && !docFile.isEmpty()) {

                                    File file = new File(docFile);
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
                                        e.printStackTrace();
                                    }
                                    String path=docFile;

                                    if (path != null && !path.isEmpty()) {
                                        path = path.substring(path.lastIndexOf("/") + 1);


                                    }
                                    saveDocumentFromApi(getIntent().getStringExtra("appNo"), path, img_upload_data,docCount);
                                }

                            }
                        }


                        if (docCount == 0) {
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

    private void pickFile() {
        if (ActivityCompat.checkSelfPermission(AssetDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AssetDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(AssetDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 22);
        }


    }


    public void pickCamera() {
        if (ActivityCompat.checkSelfPermission(AssetDetailActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AssetDetailActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AssetDetailActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(AssetDetailActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,}, 23);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Data", requestCode + "  " + resultCode);
        switch (requestCode) {


            case RESULT_LOAD_IMAGE:

                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data.getData();

                    progressDialog1 = new ProgressDialog(AssetDetailActivity.this);
                    progressDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    progressDialog1.setMessage("Getting file path... \n(This may take some time depending upon size of File)");
                    progressDialog1.setCancelable(false);
                    progressDialog1.show();

                    pickiT.getPath(selectedImageUri, Build.VERSION.SDK_INT);


                }

                break;
            case CAMERA_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    // selectedImageUri = data.getData();


                    progressDialog1 = new ProgressDialog(AssetDetailActivity.this);
                    progressDialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    progressDialog1.setMessage("Getting file path... \n(This may take some time depending upon size of File)");
                    progressDialog1.setCancelable(false);
                    progressDialog1.show();

                    pickiT.getPath(selectedImageUri, Build.VERSION.SDK_INT);
                }
                break;
            default:
                break;
        }
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

          //  File filenew = new File(path);
           // int file_size = Integer.parseInt(String.valueOf(filenew.length() / 1024));
            if (path != null && !path.isEmpty()) {

                mEditText.setText(path);
                mResponse.setAnswer(path);
            }

            // isFilePicked = true;

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

    public void showChoosenDialog(EditText editText, AssetListResponse response) {
        mEditText = editText;
        mResponse=response;

        final Dialog dialog1 = new Dialog(AssetDetailActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_choose_content);


        Button btnCamera = dialog1.findViewById(R.id.btnCamera);
        Button btnGallery = dialog1.findViewById(R.id.btnGallery);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();

                pickCamera();
            }
        });
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();

                pickFile();
            }
        });

        dialog1.show();
        Window window = dialog1.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void saveDocumentFromApi(String appNo, String kyc_docFile, String img_upload_data, final int docCount) {

        final ProgressDialog progress;


        progress = new ProgressDialog(AssetDetailActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            UploadApplicationDocRequest model = new UploadApplicationDocRequest();
            model.setApplicationNo(appNo);
            model.setDocData(img_upload_data);
            model.setDocName(kyc_docFile);

            Call<Boolean> call = api.uploadApplicationDOC(model);

            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    Boolean loginResponse = response.body();
                    if (loginResponse != null) {
                        uploadedCount=uploadedCount+1;

                        //callWebServiceREST();
                    if(uploadedCount==docCount) {
                        Toast.makeText(AssetDetailActivity.this, "Document save successfully", Toast.LENGTH_LONG).show();
                        finish();
                    }

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
}
