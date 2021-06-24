package com.finnauxapp.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Adapter.CustomerKYCDocAdapter;
import com.finnauxapp.Adapter.SpinnerAdapter;
import com.finnauxapp.ApiRequest.CustomerKYCDocRequest;
import com.finnauxapp.ApiRequest.DeleteCustomerRequest;
import com.finnauxapp.ApiRequest.DeleteKYCRequest;
import com.finnauxapp.ApiRequest.DocMasterRequest;
import com.finnauxapp.ApiRequest.KYCDocSaveRequest;
import com.finnauxapp.ApiRequest.UploadCustomerDocRequest;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.CustomerDetailResponse;
import com.finnauxapp.ApiResponse.DocTypeResponse;
import com.finnauxapp.ApiResponse.KYCDocListResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.Base64;
import com.finnauxapp.Util.CompressImage;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.Util.VerhoeffAlgorithm;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.pickit.PickiT;
import com.finnauxapp.pickit.PickiTCallbacks;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CustomerKYCDocListActivity extends AppCompatActivity implements PickiTCallbacks {
    String img_upload_data = "";
    private SessionManager session;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recycler_view;
    private CustomerDetailResponse customerDetailObject;
    private int applicationId;
    private TextView tvAppNo;
    //  private Dialog dialog;
    final int RESULT_LOAD_IMAGE = 1;
    final int CAMERA_REQUEST = 2;
    private PickiT pickiT;
    private Uri selectedImageUri;
    private ProgressDialog progressDialog1;
    private EditText edFile;
    boolean isFilePicked = false;
    ArrayList<SpinnerModel> arrListDocument = new ArrayList<>();
    String newName = "";
    private boolean isSpinnerTouched = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_kyc_document);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Customer KYC Document");
        ImageView ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerKYCDocListActivity.this, TabActivity.class);
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
        pickiT = new PickiT(CustomerKYCDocListActivity.this, CustomerKYCDocListActivity.this, CustomerKYCDocListActivity.this);
        session = new SessionManager(CustomerKYCDocListActivity.this);
        customerDetailObject = (CustomerDetailResponse) getIntent().getSerializableExtra("customerDetailObject");
        applicationId = getIntent().getIntExtra("applicationId", 0);

        TextView tvCustomerName = findViewById(R.id.tvCustomerName);
        tvCustomerName.setText("Customer Name : " + customerDetailObject.getCustomer_FirstName() + " " + customerDetailObject.getCustomer_LastName() + " (" + customerDetailObject.getCustomerType() + ")");
        TextView tvAppNo = findViewById(R.id.tvAppNo);
        tvAppNo.setText("Application No : " + getIntent().getStringExtra("appNo"));

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
                addEditDocument(null);
            }
        });


        callWebServiceREST();
        getDocTypeFromApi();
    }

    public void addEditDocument(final KYCDocListResponse.Item2Bean item) {
        isSpinnerTouched=false;
        isFilePicked = false;
        img_upload_data = "";
        AlertDialog.Builder dialogBuilder;
        final AlertDialog alertDialog;

        dialogBuilder = new AlertDialog.Builder(CustomerKYCDocListActivity.this);
        final View dialog = getLayoutInflater().inflate(R.layout.dialog_add_new_kyc_doc, null);

        dialogBuilder.setView(dialog);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

     /*   dialog = new Dialog(CustomerKYCDocListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_new_kyc_doc);
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));*/
        //   int width = (int)(getResources().getDisplayMetrics().widthPixels*0.90);
        // int height = (int)(getResources().getDisplayMetrics().heightPixels*0.90);

        //  dialog.getWindow().setLayout(width, height);
        final Spinner sp_doc_type = dialog.findViewById(R.id.sp_doc_type);
        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(CustomerKYCDocListActivity.this, arrListDocument);
        sp_doc_type.setAdapter(enqiryAdapter);
        final EditText edDocNo = dialog.findViewById(R.id.edDocNo);
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
                String s1=s.toString();
                if(!s1.equals(s1.toUpperCase()))
                {
                    s1=s1.toUpperCase();
                    edDocNo.setText(s1);
                    edDocNo.setSelection(edDocNo.length()); //fix reverse texting
                }
                SpinnerModel spinnerModel=(SpinnerModel)sp_doc_type.getSelectedItem();



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
        edFile = dialog.findViewById(R.id.edFile);
        sp_doc_type.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isSpinnerTouched = true;
                return false;
            }
        });
        sp_doc_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               if(isSpinnerTouched) {
                   SpinnerModel spinnerModel = (SpinnerModel) sp_doc_type.getSelectedItem();
                   edDocNo.setText("");
                   edFile.setText("");
                   if (spinnerModel.getName().equalsIgnoreCase("aadhaar card")) {
                       edDocNo.setInputType(InputType.TYPE_CLASS_PHONE);
                   } else {
                       edDocNo.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
                   }
               }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button btn_Upload = dialog.findViewById(R.id.btn_Upload);
        Button btnBrowse = dialog.findViewById(R.id.btnBrowse);

        if (item != null) {
            sp_doc_type.setEnabled(false);
            edDocNo.setText(item.getKYC_DocNumber());
            edFile.setText(item.getKYC_DocFile());

            for (int i = 0; i < arrListDocument.size(); i++) {

                if (item.getKYC_DocId()==Integer.parseInt(arrListDocument.get(i).getId())) {
                    sp_doc_type.setSelection(i);
                    break;
                }
            }

        }

        btn_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpinnerModel model = (SpinnerModel) sp_doc_type.getSelectedItem();


                if (model.getName().equalsIgnoreCase("Select Document Type") || edDocNo.getText().toString().trim().isEmpty() || edFile.getText().toString().trim().isEmpty()) {
                    WebUtility.showOkDialog(CustomerKYCDocListActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "All Fields are mandatory!");
                } else if (model.getName().equalsIgnoreCase("Aadhaar Card") && !isValidAadharNumber(edDocNo.getText().toString().trim())) {

                    WebUtility.showOkDialog(CustomerKYCDocListActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Aadhaar card not valid! ");


                } else if (model.getName().equalsIgnoreCase("Pan Card")) {

                    String Pan = edDocNo.getText().toString().trim();
                    Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");

                    Matcher matcher = pattern.matcher(Pan);

                    if (matcher.matches()) {
                        alertDialog.dismiss();
                        saveKYVDocREST(edDocNo.getText().toString(), edFile.getText().toString(), model.getId(), model.getName(), item);
                    } else {
                        WebUtility.showOkDialog(CustomerKYCDocListActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Pan card not valid! ");

                    }
                } else {

                    alertDialog.dismiss();
                    saveKYVDocREST(edDocNo.getText().toString(), edFile.getText().toString(), model.getId(), model.getName(), item);
                }

            }
        });

        btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(CustomerKYCDocListActivity.this);
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
        });


        alertDialog.show();
        //Window window = dialog.getWindow();
        //window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    public boolean isValidAadharNumber(String str)
    {
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

    public void callWebServiceREST() {
        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerKYCDocListActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            CustomerKYCDocRequest model = new CustomerKYCDocRequest();
            model.setCustomerId(customerDetailObject.getCustomerId());
            model.setApplicationId(applicationId);


            Call<KYCDocListResponse> call = api.getCustomerKYCDoc(model);

            call.enqueue(new Callback<KYCDocListResponse>() {
                @Override
                public void onResponse(Call<KYCDocListResponse> call, Response<KYCDocListResponse> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    KYCDocListResponse item = response.body();

                    if (item != null) {
                        int cId = item.getItem1().get(0).getCustomerId();
                        CustomerKYCDocAdapter adpter = new CustomerKYCDocAdapter(CustomerKYCDocListActivity.this, item.getItem2(), cId);
                        recycler_view.setAdapter(adpter);
                    }
                }

                @Override
                public void onFailure(Call<KYCDocListResponse> call, Throwable t) {
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


    public void saveKYVDocREST(String docNo, String docFile, String id, String name, KYCDocListResponse.Item2Bean item) {

        if (isFilePicked) {
            String file1 = docFile;
            String fileName = null;
            if (file1 != null && !file1.isEmpty()) {
                fileName = file1.substring(file1.lastIndexOf("/") + 1);

            }


            String extension = fileName.substring(fileName.lastIndexOf("."));

            String name1 = name.replaceAll("\\s+", "");
            newName = name1 + "_" + gen() + extension;


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

        } else {
            newName = item.getKYC_DocFile();
        }

        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerKYCDocListActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            KYCDocSaveRequest model = new KYCDocSaveRequest();
            model.setCustomerId(customerDetailObject.getCustomerId());
            model.setKYC_DocNumber(docNo);
            model.setLoginUserId(session.getUserId());
            model.setKYC_DocFile(newName);
            model.setKYC_DocId(Integer.parseInt(id));


            Call<List<AcceptRejectResponse>> call = api.saveCustomerKYCDoc(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        String msg = loginResponse.get(0).getMSG();
                        //   Toast.makeText(CustomerKYCDocListActivity.this, msg, Toast.LENGTH_LONG).show();

                        if (isFilePicked) {
                            saveDocumentFromApi(String.valueOf(customerDetailObject.getCustomerId()), newName, img_upload_data);
                        } else {
                            Toast.makeText(CustomerKYCDocListActivity.this, "Document save successfully", Toast.LENGTH_LONG).show();
                            callWebServiceREST();
                        }
                    }

                    //  callWebServiceREST();

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

    public int gen() {
        Random r = new Random(System.currentTimeMillis());
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    private void pickFile() {
        if (ActivityCompat.checkSelfPermission(CustomerKYCDocListActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CustomerKYCDocListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            ActivityCompat.requestPermissions(CustomerKYCDocListActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 22);
        }


    }


    public void pickCamera() {
        if (ActivityCompat.checkSelfPermission(CustomerKYCDocListActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CustomerKYCDocListActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CustomerKYCDocListActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(CustomerKYCDocListActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,}, 23);
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

                    progressDialog1 = new ProgressDialog(CustomerKYCDocListActivity.this);
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


                    progressDialog1 = new ProgressDialog(CustomerKYCDocListActivity.this);
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

            edFile.setText(path);
            isFilePicked = true;

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

    private void getDocTypeFromApi() {


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


                    }


                }

                @Override
                public void onFailure(Call<List<DocTypeResponse>> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveDocumentFromApi(String customerId, String kyc_docFile, String img_upload_data) {

        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerKYCDocListActivity.this);
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

                        Toast.makeText(CustomerKYCDocListActivity.this, "Document save successfully", Toast.LENGTH_LONG).show();
                        callWebServiceREST();
                        //  finish();

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

    public void DeleteKYCApi(int customerId) {

        final ProgressDialog progress;


        progress = new ProgressDialog(CustomerKYCDocListActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            DeleteKYCRequest model = new DeleteKYCRequest();
            model.setCustomerId(customerDetailObject.getCustomerId());
            model.setLoginUserId(session.getUserId());
            model.setDocumentId(customerId);


            Call<List<AcceptRejectResponse>> call = api.getDeleteCustomerDocument(model);

            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null) {
                        String msg = loginResponse.get(0).getMSG();
                        Toast.makeText(CustomerKYCDocListActivity.this, msg, Toast.LENGTH_LONG).show();
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
