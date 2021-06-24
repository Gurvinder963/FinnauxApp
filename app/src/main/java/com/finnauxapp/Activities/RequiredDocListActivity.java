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
import com.finnauxapp.Adapter.RequiredDocAdapter;
import com.finnauxapp.ApiRequest.ApplicationDetailRequest;
import com.finnauxapp.ApiRequest.ApplicationDocSaveRequest;
import com.finnauxapp.ApiRequest.KYCDocSaveRequest;
import com.finnauxapp.ApiRequest.UploadApplicationDocRequest;
import com.finnauxapp.ApiRequest.UploadCustomerDocRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.AssetListResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.KYCDocListResponse;
import com.finnauxapp.ApiResponse.RequiredDOCListResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.Base64;
import com.finnauxapp.Util.CompressImage;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.pickit.PickiT;
import com.finnauxapp.pickit.PickiTCallbacks;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RequiredDocListActivity extends AppCompatActivity implements PickiTCallbacks {
    private SessionManager session;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;
    float uploadedCount = 0;
    float uploadedCountFiles = 0;
    //  private CustomerDetailResponse customerDetailObject;
    private int applicationId;
    private Uri selectedImageUri;
    private ProgressDialog progressDialog1;
    final int RESULT_LOAD_IMAGE = 1;
    final int CAMERA_REQUEST = 2;
    private PickiT pickiT;
    private EditText mEditText;
    private RequiredDOCListResponse mResponse;
    private RequiredDocAdapter adpter;
    ProgressDialog progressSimple;
    ProgressDialog progressFiles;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_required_doc_list);

        pickiT = new PickiT(RequiredDocListActivity.this, RequiredDocListActivity.this, RequiredDocListActivity.this);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Pending DOC");

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
                Intent intent = new Intent(RequiredDocListActivity.this, TabActivity.class);
                startActivity(intent);
            }
        });


        session = new SessionManager(RequiredDocListActivity.this);
        //  customerDetailObject = (CustomerDetailResponse) getIntent().getSerializableExtra("customerDetailObject");

        applicationId = getIntent().getIntExtra("applicationId", 0);
        TextView tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerName.setVisibility(View.GONE);
        //  tvCustomerName.setText("Customer Name : "+customerDetailObject.getCustomer_FirstName()+" " +customerDetailObject.getCustomer_LastName()+" ("+customerDetailObject.getCustomerType()+")");
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
                uploadedCount = 0;
                float docCount = 0;


                if (adpter != null && adpter.getList().size() > 0) {

                    for (int i = 0; i < adpter.getList().size(); i++) {
                        if (adpter.getList().get(i).getDocFileName() != null) {
                            if (!adpter.getList().get(i).getDocFileName().isEmpty() && !adpter.getList().get(i).getDocNo().isEmpty()) {
                                docCount = docCount + 1;
                            }
                        }
                    }


                    for (int i = 0; i < adpter.getList().size(); i++) {
                        if (adpter.getList().get(i).getDocFileName() != null) {
                            if (!adpter.getList().get(i).getDocFileName().isEmpty() && !adpter.getList().get(i).getDocNo().isEmpty()) {

                                String newName = null;
                                String img_upload_data = "";

                                File file = new File(adpter.getList().get(i).getDocFileName());
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

                                adpter.getList().get(i).setBaseImageString(img_upload_data);

                                String fileName = adpter.getList().get(i).getDocFileName().substring(adpter.getList().get(i).getDocFileName().lastIndexOf("/") + 1);

                                String extension = fileName.substring(fileName.lastIndexOf("."));

                                String name1 = adpter.getList().get(i).getDocName().replaceAll("\\s+", "");
                                newName = name1 + "_" + gen() + extension;

                                adpter.getList().get(i).setDocFileName(newName);

                                saveKYVDocREST(adpter.getList().get(i).getApplicationId(),adpter.getList().get(i).getCustomerId(), adpter.getList().get(i).getDocNo(), adpter.getList().get(i).getDocFileName(), adpter.getList().get(i).getDocumentId(), adpter.getList().get(i).getDocName(), docCount);

                            }
                        }

                    }
                }


            }
        });

        callWebServiceREST();
    }

    public void callWebServiceREST() {
        final ProgressDialog progress;


        progress = new ProgressDialog(RequiredDocListActivity.this);
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


            Call<List<RequiredDOCListResponse>> call = api.getApplicationRequiredDocListApp(model);

            call.enqueue(new Callback<List<RequiredDOCListResponse>>() {
                @Override
                public void onResponse(Call<List<RequiredDOCListResponse>> call, Response<List<RequiredDOCListResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<RequiredDOCListResponse> list = response.body();

                    if(list!=null) {
                        adpter = new RequiredDocAdapter(RequiredDocListActivity.this, list,getIntent().getStringExtra("appNo"));
                        recycler_view.setAdapter(adpter);
                    }
                }

                @Override
                public void onFailure(Call<List<RequiredDOCListResponse>> call, Throwable t) {
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
        if (ActivityCompat.checkSelfPermission(RequiredDocListActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RequiredDocListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(RequiredDocListActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 22);
        }


    }


    public void pickCamera() {
        if (ActivityCompat.checkSelfPermission(RequiredDocListActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RequiredDocListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(RequiredDocListActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(RequiredDocListActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,}, 23);
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

                    progressDialog1 = new ProgressDialog(RequiredDocListActivity.this);
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


                    progressDialog1 = new ProgressDialog(RequiredDocListActivity.this);
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

           File filenew = new File(path);
           int file_size = Integer.parseInt(String.valueOf(filenew.length() / 1024));

           if(file_size>5120)
           {
               WebUtility.showOkDialog(RequiredDocListActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Max. file size is 5 mb allowed only.");
           }
            else if (path != null && !path.isEmpty()) {

                mEditText.setText(path);
                mResponse.setDocFileName(path);
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

    public void showChoosenDialog(EditText editText, RequiredDOCListResponse response) {
        mEditText = editText;
        mResponse = response;

        final Dialog dialog1 = new Dialog(RequiredDocListActivity.this);
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

    public void saveKYVDocREST(int appId,int cId, String docNo, String docFile, int id, String name, final float docCount) {


        if (uploadedCount == 0) {


            progressSimple = new ProgressDialog(RequiredDocListActivity.this);
            progressSimple.setMessage("Saving Data ,Please wait...");
            progressSimple.setCancelable(false);
            progressSimple.setIndeterminate(false);
            progressSimple.setMax(100);
            progressSimple.show();
        }
        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            Call<List<AcceptRejectResponse>> call = null;
            if(cId!=0) {
                KYCDocSaveRequest model = new KYCDocSaveRequest();
                model.setCustomerId(cId);
                model.setKYC_DocNumber(docNo);
                model.setLoginUserId(session.getUserId());
                model.setKYC_DocFile(docFile);
                model.setKYC_DocId(id);

                call = api.saveCustomerKYCDoc(model);
            }
            else{
                ApplicationDocSaveRequest applicationDocSaveRequest=new ApplicationDocSaveRequest();
                applicationDocSaveRequest.setApplicationId(appId);
                applicationDocSaveRequest.setDocFileName(docFile);
                applicationDocSaveRequest.setDocId(id);
                applicationDocSaveRequest.setDocTitle(docNo);
                applicationDocSaveRequest.setLoginUserId(session.getUserId());

                call=api.saveApplicationDoc(applicationDocSaveRequest);
            }
            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        String msg = loginResponse.get(0).getMSG();
                        //   Toast.makeText(CustomerKYCDocListActivity.this, msg, Toast.LENGTH_LONG).show();

                        uploadedCount = uploadedCount + 1;




                      runOnUiThread (new Runnable() {

                            @Override
                            public void run () {
                                float count = (uploadedCount / docCount) * 100;
                                progressSimple.setProgress((int) count);
                                progressSimple.setMessage("Saving data, Please wait...   " + uploadedCount + "/" + docCount + " data saved");


                            }
                        });


                        if (docCount == uploadedCount) {
                            if (progressSimple != null && progressSimple.isShowing()) {
                                progressSimple.dismiss();
                            }
                            uploadedCountFiles = 0;
                            float docCountFiles = docCount;


                            for (int i = 0; i < adpter.getList().size(); i++) {
                                if (adpter.getList().get(i).getDocFileName() != null) {
                                    if (!adpter.getList().get(i).getDocFileName().isEmpty() && !adpter.getList().get(i).getDocNo().isEmpty()) {
                                        String newName = adpter.getList().get(i).getDocFileName();
                                        String img_upload_data = adpter.getList().get(i).getBaseImageString();

                                        saveDocumentFromApi(String.valueOf(adpter.getList().get(i).getCustomerId()), newName, img_upload_data, docCountFiles);

                                    }
                                }

                            }
                        }


                    }


                }


                @Override
                public void onFailure(Call<List<AcceptRejectResponse>> call, Throwable t) {
                    if (progressSimple != null && progressSimple.isShowing()) {
                        progressSimple.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int gen() {
        Random r = new Random(System.currentTimeMillis());
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    private void saveDocumentFromApi(String customerId, String kyc_docFile, String img_upload_data, final float docCountFiles) {

        if (uploadedCountFiles == 0) {


            progressFiles = new ProgressDialog(RequiredDocListActivity.this);
            progressFiles.setMessage("Saving files, Please wait...");
            progressFiles.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressFiles.setCancelable(false);
            progressFiles.setIndeterminate(false);
            progressFiles.setMax(100);
            progressFiles.show();

        }
        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            Call<Boolean> call = null;
            if(!customerId.equalsIgnoreCase("-1")) {

                UploadCustomerDocRequest model = new UploadCustomerDocRequest();
                model.setCustomerID(customerId);
                model.setDocData(img_upload_data);
                model.setDocName(kyc_docFile);

                 call = api.uploadCustomerDOC(model);
            }
            else{
                UploadApplicationDocRequest model = new UploadApplicationDocRequest();
                model.setApplicationNo(getIntent().getStringExtra("appNo"));
                model.setDocData(img_upload_data);
                model.setDocName(kyc_docFile);

                call = api.uploadApplicationDOC(model);
            }
            call.enqueue(new Callback<Boolean>() {
                @Override
                public void onResponse(Call<Boolean> call, Response<Boolean> response) {


                    Boolean loginResponse = response.body();
                    if (loginResponse != null) {

                        uploadedCountFiles = uploadedCountFiles + 1;




                       runOnUiThread (new Runnable() {

                            @Override
                            public void run () {
                                float count = (uploadedCountFiles / docCountFiles) * 100;
                                progressFiles.setProgress((int) count);
                                progressFiles.setMessage("Saving files, Please wait...   " + uploadedCountFiles + "/" + docCountFiles + " files uploaded");


                            }
                        });

                        if (uploadedCountFiles == docCountFiles) {

                            if (progressFiles != null && progressFiles.isShowing()) {
                                progressFiles.dismiss();
                            }
                            Toast.makeText(RequiredDocListActivity.this, "Document save successfully", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        //  Toast.makeText(RequiredDocListActivity.this, "Document save successfully", Toast.LENGTH_LONG).show();


                    }


                }

                @Override
                public void onFailure(Call<Boolean> call, Throwable t) {
                    if (progressFiles != null && progressFiles.isShowing()) {
                        progressFiles.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
