package com.finnauxapp.Activities;



import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Adapter.SchemeAdapter;
import com.finnauxapp.Adapter.SpinnerAdapter;
import com.finnauxapp.ApiRequest.ApplicationDetailRequest;
import com.finnauxapp.ApiRequest.ApplicationGenerateBaseModel;
import com.finnauxapp.ApiRequest.ApplicationGenerateUpdateModel;
import com.finnauxapp.ApiRequest.CalculateIRRRequest;
import com.finnauxapp.ApiRequest.CustomerRegMainRequest;
import com.finnauxapp.ApiRequest.GenerateApplicationRequest;
import com.finnauxapp.ApiRequest.SchemeRequest;
import com.finnauxapp.ApiRequest.StepEMIModel;
import com.finnauxapp.ApiResponse.AcceptRejectResponse;
import com.finnauxapp.ApiResponse.CalculateIRRResponse;
import com.finnauxapp.ApiResponse.FinancialDetailResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.ApiResponse.ProductTypeResponse;
import com.finnauxapp.ApiResponse.SchemeResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.DateOnClick;
import com.finnauxapp.Util.DatePickerFragment3;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class GenerateApplicationNewActivity extends AppCompatActivity implements DateOnClick<String> {

    private TextView tvInquiryNo;
    private TextView tvLoanProduct;
  //  private TextView edLoanAmount;
   // private EditText edDuration;
    private LeadResponse leadResponse;
    private SessionManager session;
    private Spinner sp_loan_type;
    private Spinner sp_Select_Scheme;
    private Spinner sp_emi_Frequency;
    ArrayList<SpinnerModel> arrListProduct = new ArrayList<>();
    private TextView tvBranch;
   // private EditText edEMIAmount;
   // private EditText edIRRate;
    private EditText edAssetsCost;
    private EditText edLoanPurpose;
    private EditText edNetFinanceAmount;
    private EditText edNoOfInstallments;
    private EditText edTenureMonth;
    private EditText edInstallmentInAdvance;
    private RadioGroup rbIrrInputType;
    private RadioButton rbROI;
    private RadioButton rbFlatEMI;
    private RadioButton rbStepEMI;
    private String roiType = "ROI";
    private EditText edManagementFee;
    private EditText edROIInput;
    private EditText edPreferableEMIDate;
    private ArrayList<SpinnerModel> arrListEMIType=new ArrayList<>();
    private TextView tvDisbursement_Amt;
    private TextView tvInterest_Amount;
    private TextView tvAgreement_Value;
    private TextView tvEMIAmount;
    private TextView tvLTV;
    private TextView tvROI;
    private TextView tvCaseIRR;
    private TextView tvDisbursementIRR;
    private TextView tvMargin;
    private LinearLayout llStepEMI;
    private Button btn_add;
    private Button btn_Get_Scheme;
    int indexStepEMI=1;
    private TextView tvTotalAmountStepEMI;
    private TextView tvTotalStepEMI;
   // private LinearLayout llSchemes;
    private LinearLayout llTotalStepView;
    private LinearLayout llCalculate_EMI_IRR;
    private LinearLayout llStepHeader;
    private TextInputLayout tvInputLayoutROI;
    private View view33;
    private View view44;
    private DatePickerDialog datePickerDialog;
    int schemeId=-1;
    private TextView tvSchemeName;
    private List<SchemeResponse> schemeList;
    private SchemeResponse selectedSchemeItem=null;
    private Button btn_Minus;
    private LinearLayout llPlusMinus;
    private int appId;
    private int branchId;
    private ImageView ivSchemeClose;
    final Calendar myCalendar = Calendar.getInstance();
    int lastToEMI=-1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_application_generate_new);


        SpinnerModel model1=new SpinnerModel();
        model1.setId("1");
        model1.setName("Monthly");
        arrListEMIType.add(model1);
        SpinnerModel model2=new SpinnerModel();
        model2.setId("2");
        model2.setName("ByMonthly");
        arrListEMIType.add(model2);
        SpinnerModel model3=new SpinnerModel();
        model3.setId("3");
        model3.setName("Quarterly");
        arrListEMIType.add(model3);
        SpinnerModel model4=new SpinnerModel();
        model4.setId("4");
        model4.setName("HalfYearly");
        arrListEMIType.add(model4);
        SpinnerModel model5=new SpinnerModel();
        model5.setId("5");
        model5.setName("Yearly");
        arrListEMIType.add(model5);
        SpinnerModel model6=new SpinnerModel();
        model6.setId("6");
        model6.setName("Bullet");
        arrListEMIType.add(model6);


        ImageView ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GenerateApplicationNewActivity.this, TabActivity.class);
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
        leadResponse = (LeadResponse) getIntent().getSerializableExtra("data");
        session = new SessionManager(GenerateApplicationNewActivity.this);
        TextView tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText("Generate Application No.");

        tvInquiryNo = findViewById(R.id.tvInquiryNo);
        llStepEMI = findViewById(R.id.llStepEMI);
        ivSchemeClose = findViewById(R.id.ivSchemeClose);
        tvBranch = findViewById(R.id.tvBranch);
        tvLoanProduct = findViewById(R.id.tvLoanProduct);
        btn_Get_Scheme = findViewById(R.id.btn_Get_Scheme);
        tvSchemeName = findViewById(R.id.tvSchemeName);
        llTotalStepView = findViewById(R.id.llTotalStepView);
        view33 = findViewById(R.id.view33);
        view44 = findViewById(R.id.view44);
        llCalculate_EMI_IRR = findViewById(R.id.llCalculate_EMI_IRR);
        llStepHeader = findViewById(R.id.llStepHeader);
       // edLoanAmount = findViewById(R.id.edLoanAmount);
        edAssetsCost = findViewById(R.id.edAssetsCost);
        edLoanPurpose = findViewById(R.id.edLoanPurpose);
        llPlusMinus = findViewById(R.id.llPlusMinus);
        btn_add = findViewById(R.id.btn_add);
        btn_Minus = findViewById(R.id.btn_Minus);
        btn_Minus.setVisibility(View.GONE);
        edNetFinanceAmount = findViewById(R.id.edNetFinanceAmount);
        edNoOfInstallments = findViewById(R.id.edNoOfInstallments);
        edTenureMonth = findViewById(R.id.edTenureMonth);
        edInstallmentInAdvance = findViewById(R.id.edInstallmentInAdvance);
        edManagementFee = findViewById(R.id.edManagementFee);
        edROIInput = findViewById(R.id.edROIInput);
        tvInputLayoutROI = findViewById(R.id.tvInputLayoutROI);
        edPreferableEMIDate = findViewById(R.id.edPreferableEMIDate);
        edPreferableEMIDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(GenerateApplicationNewActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        tvDisbursement_Amt = findViewById(R.id.tvDisbursement_Amt);
        tvInterest_Amount = findViewById(R.id.tvInterest_Amount);
        tvAgreement_Value = findViewById(R.id.tvAgreement_Value);
        tvTotalAmountStepEMI = findViewById(R.id.tvTotalAmountStepEMI);
        tvTotalStepEMI = findViewById(R.id.tvTotalStepEMI);
        tvEMIAmount = findViewById(R.id.tvEMIAmount);
        tvLTV = findViewById(R.id.tvLTV);
        tvROI = findViewById(R.id.tvROI);
        tvCaseIRR = findViewById(R.id.tvCaseIRR);
        tvDisbursementIRR = findViewById(R.id.tvDisbursementIRR);
        tvMargin = findViewById(R.id.tvMargin);
        sp_loan_type = findViewById(R.id.sp_loan_type);
       // sp_Select_Scheme = findViewById(R.id.sp_Select_Scheme);
        sp_emi_Frequency = findViewById(R.id.sp_emi_Frequency);
        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(GenerateApplicationNewActivity.this, arrListEMIType);
        sp_emi_Frequency.setAdapter(enqiryAdapter);
        rbIrrInputType = findViewById(R.id.rbIrrInputType);
        rbROI = findViewById(R.id.rbROI);
        rbFlatEMI = findViewById(R.id.rbFlatEMI);
        rbStepEMI = findViewById(R.id.rbStepEMI);

        rbROI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbROI.setChecked(true);
                rbFlatEMI.setChecked(false);
                rbStepEMI.setChecked(false);
                emptyData();
                indexStepEMI=1;
                llStepEMI.setVisibility(View.GONE);
                llCalculate_EMI_IRR.setVisibility(View.GONE);
                edROIInput.setVisibility(View.VISIBLE);
                tvInputLayoutROI.setVisibility(View.VISIBLE);
                llPlusMinus.setVisibility(View.GONE);
                llTotalStepView.setVisibility(View.GONE);
                llStepHeader.setVisibility(View.GONE);
                view33.setVisibility(View.GONE);
                view44.setVisibility(View.GONE);
                roiType = "ROI";
                tvInputLayoutROI.setHint("Flat Rate");

            }
        });
        rbFlatEMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbROI.setChecked(false);
                rbFlatEMI.setChecked(true);
                rbStepEMI.setChecked(false);
                emptyData();
                indexStepEMI=1;
                edROIInput.setVisibility(View.VISIBLE);
                tvInputLayoutROI.setVisibility(View.VISIBLE);
                llCalculate_EMI_IRR.setVisibility(View.GONE);
                llStepEMI.setVisibility(View.GONE);
                llPlusMinus.setVisibility(View.GONE);
                llTotalStepView.setVisibility(View.GONE);
                llStepHeader.setVisibility(View.GONE);
                view33.setVisibility(View.GONE);
                view44.setVisibility(View.GONE);
                tvInputLayoutROI.setHint("EMI Amount");
                roiType = "FLAT_EMI";

            }
        });
        rbStepEMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbROI.setChecked(false);
                rbFlatEMI.setChecked(false);
                rbStepEMI.setChecked(true);
                emptyData();
                indexStepEMI=1;
                llStepEMI.setVisibility(View.VISIBLE);
                llPlusMinus.setVisibility(View.VISIBLE);
                llTotalStepView.setVisibility(View.VISIBLE);
                llCalculate_EMI_IRR.setVisibility(View.GONE);
                llStepHeader.setVisibility(View.VISIBLE);
                view33.setVisibility(View.VISIBLE);
                view44.setVisibility(View.VISIBLE);
                llStepEMI.removeAllViews();
                edROIInput.setVisibility(View.GONE);
                tvInputLayoutROI.setVisibility(View.GONE);
                addStepEMIRow(null,0,0);
                roiType = "STEP_EMI";

            }
        });



      /*  rbIrrInputType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("checked_id", checkedId + "");


                switch (checkedId) {

                    case R.id.rbROI:
                        emptyData();
                        indexStepEMI=1;
                        llStepEMI.setVisibility(View.GONE);
                        llCalculate_EMI_IRR.setVisibility(View.GONE);
                        edROIInput.setVisibility(View.VISIBLE);
                        tvInputLayoutROI.setVisibility(View.VISIBLE);
                        llPlusMinus.setVisibility(View.GONE);
                        llTotalStepView.setVisibility(View.GONE);
                        llStepHeader.setVisibility(View.GONE);
                        view33.setVisibility(View.GONE);
                        view44.setVisibility(View.GONE);
                        roiType = "ROI";
                        tvInputLayoutROI.setHint("Flat Rate");
                        break;

                    case R.id.rbFlatEMI:
                        emptyData();
                        indexStepEMI=1;
                        edROIInput.setVisibility(View.VISIBLE);
                        tvInputLayoutROI.setVisibility(View.VISIBLE);
                        llCalculate_EMI_IRR.setVisibility(View.GONE);
                        llStepEMI.setVisibility(View.GONE);
                        llPlusMinus.setVisibility(View.GONE);
                        llTotalStepView.setVisibility(View.GONE);
                        llStepHeader.setVisibility(View.GONE);
                        view33.setVisibility(View.GONE);
                        view44.setVisibility(View.GONE);
                        tvInputLayoutROI.setHint("EMI Amount");
                        roiType = "FLAT_EMI";
                        break;
                    case R.id.rbStepEMI:
                        emptyData();
                        indexStepEMI=1;
                        llStepEMI.setVisibility(View.VISIBLE);
                        llPlusMinus.setVisibility(View.VISIBLE);
                        llTotalStepView.setVisibility(View.VISIBLE);
                        llCalculate_EMI_IRR.setVisibility(View.GONE);
                        llStepHeader.setVisibility(View.VISIBLE);
                        view33.setVisibility(View.VISIBLE);
                        view44.setVisibility(View.VISIBLE);
                        llStepEMI.removeAllViews();
                       edROIInput.setVisibility(View.GONE);
                        tvInputLayoutROI.setVisibility(View.GONE);
                        addStepEMIRow(null,0,0);
                        roiType = "STEP_EMI";
                        break;


                }
            }

        });*/
      ivSchemeClose.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              selectedSchemeItem=null;
              btn_Get_Scheme.setVisibility(View.VISIBLE);
              ivSchemeClose.setVisibility(View.GONE);
              tvSchemeName.setText("No Scheme Selected");
              for (int i = 0; i < schemeList.size(); i++) {
                  schemeList.get(i).setSelected(false);
              }

          }
      });
        btn_Get_Scheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SpinnerModel spinnerModel = (SpinnerModel) sp_loan_type.getSelectedItem();
                String productId=spinnerModel.getId();
                if (spinnerModel.getName().equalsIgnoreCase("Select Loan Product")) {
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select loan product!");
                }
               else {

                    AlertDialog.Builder dialogBuilder;
                    final AlertDialog alertDialog;

                    dialogBuilder = new AlertDialog.Builder(GenerateApplicationNewActivity.this);
                    final View dialog = getLayoutInflater().inflate(R.layout.dialog_add_scheme, null);

                    dialogBuilder.setView(dialog);
                    alertDialog = dialogBuilder.create();
                    alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                    alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                    final RecyclerView recyclerView = dialog.findViewById(R.id.recyclerView);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(GenerateApplicationNewActivity.this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.getLayoutManager().setAutoMeasureEnabled(true);
                    recyclerView.setNestedScrollingEnabled(false);
                    recyclerView.setHasFixedSize(false);
                    final SchemeAdapter adpter = new SchemeAdapter(GenerateApplicationNewActivity.this, schemeList);
                    recyclerView.setAdapter(adpter);
                    Button btnOk = dialog.findViewById(R.id.btnOk);
                    Button btnCancel = dialog.findViewById(R.id.btnCancel);
                    btnOk.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            List<SchemeResponse> scmList = adpter.getList();
                            for (int i = 0; i < scmList.size(); i++) {
                                if (scmList.get(i).isSelected()) {
                                    selectedSchemeItem = scmList.get(i);
                                    tvSchemeName.setText("Scheme : "+selectedSchemeItem.getScheme());
                                    btn_Get_Scheme.setVisibility(View.GONE);
                                    ivSchemeClose.setVisibility(View.VISIBLE);
                                    break;

                                }
                            }

                            alertDialog.dismiss();
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
        });
        sp_emi_Frequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String s=edNoOfInstallments.getText().toString();

                if(!s.toString().isEmpty()) {
                    SpinnerModel spm = (SpinnerModel) sp_emi_Frequency.getSelectedItem();
                    if (spm.getName().equalsIgnoreCase("Monthly") || spm.getName().equalsIgnoreCase("Bullet")) {
                        edTenureMonth.setText(s.toString());
                    } else if (spm.getName().equalsIgnoreCase("ByMonthly")) {
                        int value = Integer.parseInt(s.toString()) * 2;
                        edTenureMonth.setText(String.valueOf(value));
                    } else if (spm.getName().equalsIgnoreCase("Quarterly")) {
                        int value = Integer.parseInt(s.toString()) * 3;
                        edTenureMonth.setText(String.valueOf(value));
                    } else if (spm.getName().equalsIgnoreCase("HalfYearly")) {
                        int value = Integer.parseInt(s.toString()) * 6;
                        edTenureMonth.setText(String.valueOf(value));
                    } else if (spm.getName().equalsIgnoreCase("Yearly")) {
                        int value = Integer.parseInt(s.toString()) * 12;
                        edTenureMonth.setText(String.valueOf(value));
                    }
                }
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edNoOfInstallments.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().isEmpty()){
                    SpinnerModel spm=(SpinnerModel) sp_emi_Frequency.getSelectedItem();
                    if(spm.getName().equalsIgnoreCase("Monthly") || spm.getName().equalsIgnoreCase("Bullet")){
                     edTenureMonth.setText(s.toString());
                    }
                    else if(spm.getName().equalsIgnoreCase("ByMonthly")){
                        int value=Integer.parseInt(s.toString())*2;
                        edTenureMonth.setText(String.valueOf(value));
                    }
                    else if(spm.getName().equalsIgnoreCase("Quarterly")){
                        int value=Integer.parseInt(s.toString())*3;
                        edTenureMonth.setText(String.valueOf(value));
                    }
                    else if(spm.getName().equalsIgnoreCase("HalfYearly")){
                        int value=Integer.parseInt(s.toString())*6;
                        edTenureMonth.setText(String.valueOf(value));
                    }
                    else if(spm.getName().equalsIgnoreCase("Yearly")){
                        int value=Integer.parseInt(s.toString())*12;
                        edTenureMonth.setText(String.valueOf(value));
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!edNoOfInstallments.getText().toString().isEmpty()) {

                    if (llStepEMI.getChildCount() > 0) {
                        LinearLayout l1 = (LinearLayout) llStepEMI.getChildAt(llStepEMI.getChildCount() - 1);
                        LinearLayout l2 = (LinearLayout) l1.getChildAt(0);
                        TextView tvTotalAmount = l2.findViewById(R.id.tvTotalAmount);



                        int count=0;

                        for(int i=0;i<llStepEMI.getChildCount();i++){
                            LinearLayout l11 = (LinearLayout) llStepEMI.getChildAt(i);
                            LinearLayout l21 = (LinearLayout) l11.getChildAt(0);

                            TextView tvNoOFemi = l21.findViewById(R.id.tvNoOFemi);

                            count=Integer.parseInt(tvNoOFemi.getText().toString())+count;
                        }


                        if (tvTotalAmount.getText().toString().isEmpty()) {
                            WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please fill data!");
                        }
                        else if((count)>=(Integer.parseInt(edNoOfInstallments.getText().toString()))){
                            WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "total no of emi cann't greater than no of installments!");
                        }
                        else {
                            indexStepEMI = indexStepEMI + 1;
                            addStepEMIRow(null, 0, 0);
                            if (llStepEMI.getChildCount() > 1) {
                                btn_Minus.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
                else{
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter no of installment!");
                }

            }
        });
        btn_Minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indexStepEMI=indexStepEMI-1;

                float totalCount=0;
                int totalStepEMI=0;

                llStepEMI.removeViewAt(llStepEMI.getChildCount()-1);

                for(int i=0;i<llStepEMI.getChildCount();i++){
                    LinearLayout l1=(LinearLayout) llStepEMI.getChildAt(i);
                    LinearLayout l2= (LinearLayout) l1.getChildAt(0);

                    TextView mtvNoOFemi = l2.findViewById(R.id.tvNoOFemi);
                    totalStepEMI=totalStepEMI+Integer.parseInt(mtvNoOFemi.getText().toString());

                    TextView medEMIAmount = l2.findViewById(R.id.tvTotalAmount);
                    totalCount=totalCount+Float.parseFloat(medEMIAmount.getText().toString());

                }
                tvTotalStepEMI.setText(String.valueOf(totalStepEMI));
                tvTotalAmountStepEMI.setText(String.valueOf(totalCount));
                if(llStepEMI.getChildCount()==1){
                    btn_Minus.setVisibility(View.GONE);
                }
            }
        }); if(leadResponse!=null) {

                edLoanPurpose.setText(leadResponse.getPurpose());
                tvInquiryNo.setText("Inquiry No. : " + leadResponse.getInquiryNo());
                tvBranch.setText("Branch : " + leadResponse.getBranch());
        }
        else{

            branchId=getIntent().getIntExtra("branchId",0);
            tvBranch.setText("Branch : " +getIntent().getStringExtra("branchName"));
            edLoanPurpose.setText(getIntent().getStringExtra("purpose"));
            tvInquiryNo.setText("Inquiry No. : " +getIntent().getStringExtra("inquiryNo"));


        }

        Button btn_Calculate_emi_irr = findViewById(R.id.btn_Calculate_emi_irr);
        btn_Calculate_emi_irr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] netAmount = null;
                String[] roi = null;

                 if(selectedSchemeItem!=null){
                    netAmount = selectedSchemeItem.getAmount().split("-");
                     roi = selectedSchemeItem.getROI().split("-");

                 }

                SpinnerModel spinnerModel = (SpinnerModel) sp_loan_type.getSelectedItem();
                String productId=spinnerModel.getId();
                if (spinnerModel.getName().equalsIgnoreCase("Select Loan Product")) {
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select loan product!");
                }



                else if (!productId.equalsIgnoreCase("18") && (edAssetsCost.getText().toString().isEmpty() || Float.parseFloat(edAssetsCost.getText().toString()) < 1)) {
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter assets cost!");
                }
                else if (edNetFinanceAmount.getText().toString().isEmpty() || Float.parseFloat(edNetFinanceAmount.getText().toString()) < 1) {
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter net Finance amount!");
                }
                else if(roiType.equalsIgnoreCase("ROI") && (edROIInput.getText().toString().isEmpty() || Float.parseFloat(edROIInput.getText().toString())<1)){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Flat rate should be greater than 0.");
                }
                else if(roiType.equalsIgnoreCase("FLAT_EMI") && (edROIInput.getText().toString().isEmpty() || Float.parseFloat(edROIInput.getText().toString())<1)){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "EMI amount should be greater than 0.");
                }

                else if (edNoOfInstallments.getText().toString().isEmpty() || Float.parseFloat(edNoOfInstallments.getText().toString()) < 1) {
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "No of Installment should be greater than 0.");
                }

                else if(roiType.equalsIgnoreCase("STEP_EMI") && llStepEMI.getChildCount()<1){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please add step EMI first");
                }
                else if(roiType.equalsIgnoreCase("STEP_EMI") && (Integer.parseInt(tvTotalStepEMI.getText().toString())!=Integer.parseInt(edNoOfInstallments.getText().toString()))){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Total EMI Should Be Equal To No of Installment");
                }
                else if(roiType.equalsIgnoreCase("STEP_EMI") && (Float.parseFloat(tvTotalAmountStepEMI.getText().toString())<Float.parseFloat(edNetFinanceAmount.getText().toString()))){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Total EMI Amount should be  equal or Greater than Net Finance Amount.");
                }

                else if (edTenureMonth.getText().toString().isEmpty() || Float.parseFloat(edTenureMonth.getText().toString()) < 1) {
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Tenure should be greater than 0.");
                }

                else if(roiType.equalsIgnoreCase("FLAT_EMI") && Float.parseFloat(edROIInput.getText().toString())*Float.parseFloat(edNoOfInstallments.getText().toString())<Float.parseFloat(edNetFinanceAmount.getText().toString())){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "No of Installment multiply by EMI amount should be greater than or equal to Net Finance Amount.");
                }
                else if(selectedSchemeItem!=null && (Float.parseFloat(edNetFinanceAmount.getText().toString())>Double.parseDouble(netAmount[1]) || Float.parseFloat(edNetFinanceAmount.getText().toString())<Double.parseDouble(netAmount[0]))){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Net Finance Amount should be in between Scheme Amount.");
                }
                else if(selectedSchemeItem!=null && roiType.equalsIgnoreCase ("ROI") && (Float.parseFloat(edROIInput.getText().toString())>Double.parseDouble(roi[1]) || Float.parseFloat(edROIInput.getText().toString())<Double.parseDouble(roi[0]))){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Flat Rate should be in between Scheme ROI.");
                }
                else if(selectedSchemeItem!=null  && (Integer.parseInt(edInstallmentInAdvance.getText().toString())>selectedSchemeItem.getAdvanceEMI())){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Advance Installment can not be less than Scheme Advance EMI.");
                }
               else {
                    callWebServiceRESTCalculateIRR();
                }
            }
        });

        Button btn_Submit = findViewById(R.id.btn_Submit);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SpinnerModel spinnerModel = (SpinnerModel) sp_loan_type.getSelectedItem();
                String productId=spinnerModel.getId();
                if (spinnerModel.getName().equalsIgnoreCase("Select Loan Product")) {
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please select loan product!");
                }



                else if (!productId.equalsIgnoreCase("18") && (edAssetsCost.getText().toString().isEmpty() || Float.parseFloat(edAssetsCost.getText().toString()) < 1)) {
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter assets cost!");
                }
                else if (edNetFinanceAmount.getText().toString().isEmpty() || Float.parseFloat(edNetFinanceAmount.getText().toString()) < 1) {
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please enter net Finance amount!");
                }
                else if(roiType.equalsIgnoreCase("ROI") && (edROIInput.getText().toString().isEmpty() || Float.parseFloat(edROIInput.getText().toString())<1)){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Flat rate should be greater than 0.");
                }
                else if(roiType.equalsIgnoreCase("FLAT_EMI") && (edROIInput.getText().toString().isEmpty() || Float.parseFloat(edROIInput.getText().toString())<1)){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "EMI amount should be greater than 0.");
                }

                else if (edNoOfInstallments.getText().toString().isEmpty() || Float.parseFloat(edNoOfInstallments.getText().toString()) < 1) {
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "No of Installment should be greater than 0.");
                }

                else if(roiType.equalsIgnoreCase("STEP_EMI") && llStepEMI.getChildCount()<1){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Please add step EMI first");
                }
                else if(roiType.equalsIgnoreCase("STEP_EMI") && (Integer.parseInt(tvTotalStepEMI.getText().toString())!=Integer.parseInt(edNoOfInstallments.getText().toString()))){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Total EMI Should Be Equal To No of Installment");
                }
                else if(roiType.equalsIgnoreCase("STEP_EMI") && (Float.parseFloat(tvTotalAmountStepEMI.getText().toString())<Float.parseFloat(edNetFinanceAmount.getText().toString()))){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Total EMI Amount should be  equal or Greater than Net Finance Amount.");
                }

                else if (edTenureMonth.getText().toString().isEmpty() || Float.parseFloat(edTenureMonth.getText().toString()) < 1) {
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "Tenure should be greater than 0.");
                }

                else if(roiType.equalsIgnoreCase("FLAT_EMI") && Float.parseFloat(edROIInput.getText().toString())*Float.parseFloat(edNoOfInstallments.getText().toString())<Float.parseFloat(edNetFinanceAmount.getText().toString())){
                    WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "No of Installment multiply by EMI amount should be greater than or equal to Net Finance Amount.");
                }
                else{




                    callWebServiceREST();
                }

           /*
                    int duration = Integer.parseInt(edDuration.getText().toString().trim());
                    float emiAmount = Float.parseFloat(edEMIAmount.getText().toString().trim());
                    float loanAmount = Float.parseFloat(edLoanAmount.getText().toString());

                    float xxxAmount = loanAmount / duration;


                    DecimalFormat df = new DecimalFormat();
                    df.setMaximumFractionDigits(2);


                    if (emiAmount < xxxAmount) {
                        WebUtility.showOkDialog(GenerateApplicationNewActivity.this, getApplicationContext().getResources().getString(R.string.app_name), "EMI amount can not less than " + df.format(xxxAmount));
                    } else {

                        callWebServiceREST();
                    }*/

            }
        });

        Button btn_Cancel = findViewById(R.id.btn_Cancel);
        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        getProductList();



    }
    public void callWebServiceFinanceDetailsREST() {
      /*  final ProgressDialog progress;



        progress = new ProgressDialog(GenerateApplicationNewActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();*/

        try {
            Retrofit retrofit= ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            ApplicationDetailRequest model=new ApplicationDetailRequest();
            model.setApplicationId(String.valueOf(appId));




            Call<FinancialDetailResponse> call = api.getFinancialDetailsForUpdate(model);

            call.enqueue(new Callback<FinancialDetailResponse>() {
                @Override
                public void onResponse(Call<FinancialDetailResponse> call, Response<FinancialDetailResponse> response) {
                   /* if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/

                  FinancialDetailResponse financialDetailResponse = response.body();
                    if(financialDetailResponse!=null){


                        llCalculate_EMI_IRR.setVisibility(View.VISIBLE);
                        if(financialDetailResponse.getItem1()!=null && financialDetailResponse.getItem1().size()>0){

                            for(int i=0;i<arrListProduct.size();i++) {


                                if (financialDetailResponse.getItem1().get(0).getProductId()==Integer.parseInt(arrListProduct.get(i).getId())) {

                                    sp_loan_type.setSelection(i);
                                    sp_loan_type.setEnabled(false);
                                    break;

                                }
                            }

                            for(int i=0;i<arrListEMIType.size();i++) {


                                if (financialDetailResponse.getItem1().get(0).getEMI_Type().equalsIgnoreCase(arrListEMIType.get(i).getName())) {

                                    sp_emi_Frequency.setSelection(i);

                                    break;

                                }
                            }

                            edAssetsCost.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getAssetCost()));
                            edNetFinanceAmount.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getNetFinance()));
                            edNoOfInstallments.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getNo_Of_Instl()));
                            edTenureMonth.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getTenure()));
                            edInstallmentInAdvance.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getAdv_Instl()));
                            String[] arr = financialDetailResponse.getItem1().get(0).getFirstEMIDate().split("T");

                            edPreferableEMIDate.setText(arr[0]);
                            edManagementFee.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getManagementFee()));

                           tvLTV.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getLTV()));
                           tvAgreement_Value.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getAgreementValue()));
                           tvCaseIRR.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getCase_IRR()));
                           tvDisbursement_Amt.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getDisbursementAmt()));
                           tvDisbursementIRR.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getDisbursement_IRR()));
                           tvInterest_Amount.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getInterestAmt()));
                           tvEMIAmount.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getEMIAmount()));
                           tvROI.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getFlat_Rate()));
                           tvMargin.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getMargin()));

                            if(financialDetailResponse.getItem1().get(0).getIRR_CalculateBy().equalsIgnoreCase("ROI")){

                                roiType="ROI";
                                rbROI.setChecked(true);
                                edROIInput.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getFlat_Rate()));
                            }
                            else if(financialDetailResponse.getItem1().get(0).getIRR_CalculateBy().equalsIgnoreCase("STEP_EMI")){
                                roiType="STEP_EMI";
                                rbStepEMI.setChecked(true);
                                edROIInput.setVisibility(View.GONE);
                                tvInputLayoutROI.setVisibility(View.GONE);

                                llStepEMI.setVisibility(View.VISIBLE);
                                llPlusMinus.setVisibility(View.VISIBLE);
                                llTotalStepView.setVisibility(View.VISIBLE);

                                llStepHeader.setVisibility(View.VISIBLE);
                                view33.setVisibility(View.VISIBLE);
                                view44.setVisibility(View.VISIBLE);
                                llStepEMI.removeAllViews();

                                double totalCount=0;
                                int totalStepEMI=0;

                                for(int i=0;i<financialDetailResponse.getItem2().size();i++){
                                    totalStepEMI=totalStepEMI+financialDetailResponse.getItem2().get(i).getNoOfEMI();
                                    totalCount=totalCount+financialDetailResponse.getItem2().get(i).getTotalAmount();
                                    addStepEMIRow(financialDetailResponse.getItem2().get(i),totalStepEMI,totalCount);
                                }
                                llCalculate_EMI_IRR.setVisibility(View.VISIBLE);
                            }
                            else{
                                roiType="FLAT_EMI";
                                rbFlatEMI.setChecked(true);
                                edROIInput.setText(String.valueOf(financialDetailResponse.getItem1().get(0).getEMIAmount()));
                            }


                        }


                    }

                }

                @Override
                public void onFailure(Call<FinancialDetailResponse> call, Throwable t) {
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
    public void addStepEMIRow(FinancialDetailResponse.Item2Bean item,int mtotalStepEMI,double mtotalCount){
        final LinearLayout _itemRow = (LinearLayout) getLayoutInflater().inflate(R.layout.dialog_add_step_emi, llStepEMI, false);
        _itemRow.setTag(0);

        final EditText edEMIAmount=_itemRow.findViewById(R.id.edEMIAmount);
        final EditText edFromEMI=_itemRow.findViewById(R.id.edFromEMI);
        final EditText edToEMI=_itemRow.findViewById(R.id.edToEMI);
        final TextView tvTotalAmount=_itemRow.findViewById(R.id.tvTotalAmount);
        final TextView tvNoOFemi=_itemRow.findViewById(R.id.tvNoOFemi);
        final TextView tvSno=_itemRow.findViewById(R.id.tvSno);
         tvSno.setText(String.valueOf(indexStepEMI));

if(item==null && llStepEMI.getChildCount()>0){
    LinearLayout l1=(LinearLayout)  llStepEMI.getChildAt(llStepEMI.getChildCount()-1);
    LinearLayout l2= (LinearLayout) l1.getChildAt(0);
    EditText mtvNoOFemi = l2.findViewById(R.id.edToEMI);
    edFromEMI.setText(String.valueOf(Integer.parseInt(mtvNoOFemi.getText().toString())+1));
}






         if(item!=null){
             edFromEMI.setText(String.valueOf(item.getFromEMI()));
             edToEMI.setText(String.valueOf(item.getToEMI()));
             edEMIAmount.setText(String.valueOf(item.getEMIAmount()));
             tvNoOFemi.setText(String.valueOf(item.getNoOfEMI()));
             tvTotalAmount.setText(String.valueOf(item.getTotalAmount()));



             tvTotalStepEMI.setText(String.valueOf(mtotalStepEMI));
             tvTotalAmountStepEMI.setText(String.valueOf(mtotalCount));
         }


        edFromEMI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s!=null && !s.toString().isEmpty()){
                    if(s!=null && !s.toString().isEmpty()){

                        if(edToEMI.getText().toString()!=null && !edToEMI.getText().toString().isEmpty())
                        {
                            int fromEMI=Integer.parseInt(s.toString());
                            int toEMI=Integer.parseInt(edToEMI.getText().toString());
                            tvNoOFemi.setText(String.valueOf(toEMI-(fromEMI-1)));

                        }

                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edToEMI.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s!=null && !s.toString().isEmpty()){

                    if(edFromEMI.getText().toString()!=null && !edFromEMI.getText().toString().isEmpty())
                    {
                        int toEMI=Integer.parseInt(s.toString());
                        lastToEMI=toEMI;
                        int fromEMI=Integer.parseInt(edFromEMI.getText().toString());
                        tvNoOFemi.setText(String.valueOf(toEMI-(fromEMI-1)));

                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edEMIAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!edFromEMI.getText().toString().isEmpty() && !edToEMI.getText().toString().isEmpty()) {

                    if (edEMIAmount.getText().toString() == null || edEMIAmount.getText().toString().isEmpty()) {
                        edEMIAmount.setText("0");
                    }
                    float emiAmount = Float.parseFloat(edEMIAmount.getText().toString());
                    int noOfEMI = Integer.parseInt(tvNoOFemi.getText().toString());
                    tvTotalAmount.setText(String.valueOf(emiAmount * noOfEMI));


                    float totalCount = 0;
                    int totalStepEMI = 0;
                    for (int i = 0; i < llStepEMI.getChildCount(); i++) {
                        LinearLayout l1 = (LinearLayout) llStepEMI.getChildAt(i);
                        LinearLayout l2 = (LinearLayout) l1.getChildAt(0);

                        TextView mtvNoOFemi = l2.findViewById(R.id.tvNoOFemi);
                        totalStepEMI = totalStepEMI + Integer.parseInt(mtvNoOFemi.getText().toString());

                        TextView medEMIAmount = l2.findViewById(R.id.tvTotalAmount);
                        totalCount = totalCount + Float.parseFloat(medEMIAmount.getText().toString());

                    }
                    tvTotalStepEMI.setText(String.valueOf(totalStepEMI));
                    tvTotalAmountStepEMI.setText(String.valueOf(totalCount));

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        llStepEMI.addView(_itemRow);
    }

    public void getProductList() {
       /* final ProgressDialog progress;


        progress = new ProgressDialog(GenerateApplicationNewActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();*/

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);


            Call<ArrayList<ProductTypeResponse>> call = api.getLoadProductType();

            call.enqueue(new Callback<ArrayList<ProductTypeResponse>>() {
                @Override
                public void onResponse(Call<ArrayList<ProductTypeResponse>> call, Response<ArrayList<ProductTypeResponse>> response) {
                  /*  if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }*/

                    ArrayList<ProductTypeResponse> list = response.body();
                    if (list != null) {
                        SpinnerModel itempe = new SpinnerModel();
                        itempe.setId("-1");
                        itempe.setName("Select Loan Product");
                        arrListProduct.add(itempe);

                        for (int i = 0; i < list.size(); i++) {
                            SpinnerModel itemp = new SpinnerModel();
                            itemp.setId(String.valueOf(list.get(i).getProductId()));
                            itemp.setName(list.get(i).getProduct_Name());
                            arrListProduct.add(itemp);
                        }


                        SpinnerAdapter enqiryAdapter = new SpinnerAdapter(GenerateApplicationNewActivity.this, arrListProduct);
                        sp_loan_type.setAdapter(enqiryAdapter);

                        sp_loan_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                callWebServiceSchemeREST();

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        appId =  getIntent().getIntExtra("applicationId",0);
                        if(appId!=0){
                            callWebServiceFinanceDetailsREST();
                        }
                    }


                }

                @Override
                public void onFailure(Call<ArrayList<ProductTypeResponse>> call, Throwable t) {

                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void callWebServiceRESTCalculateIRR() {

       ArrayList<String> cashFlow=new ArrayList<>();

        final ProgressDialog progress;


        progress = new ProgressDialog(GenerateApplicationNewActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            CalculateIRRRequest model = new CalculateIRRRequest();
            model.setAsset_Cost(edAssetsCost.getText().toString());
            model.setNetFinance_Amt(edNetFinanceAmount.getText().toString());
            model.setNo_Of_Inst(edNoOfInstallments.getText().toString());
            model.setTenure(edTenureMonth.getText().toString());
            model.setAdv_Inst(edInstallmentInAdvance.getText().toString());
            model.setMgmtFee(edManagementFee.getText().toString());
            model.setPurpose(edLoanPurpose.getText().toString());
            model.setFirst_EMI_Date(edPreferableEMIDate.getText().toString()+"T11:44:03.000Z");
            SpinnerModel spinnerModel1 = (SpinnerModel) sp_loan_type.getSelectedItem();

            model.setProductId(spinnerModel1.getId());

            SpinnerModel spinnerModel= (SpinnerModel) sp_emi_Frequency.getSelectedItem();

            model.setEMI_Type(spinnerModel.getName());



            model.setSTEP_IRR(0);
            model.setIRR_CalculateBy(roiType);
            if(roiType.equalsIgnoreCase("ROI")){
                model.setFlat_Rate(edROIInput.getText().toString());
                model.setStep(false);
                model.setIRR_Type("Flat");
            }
            else if(roiType.equalsIgnoreCase("STEP_EMI")){
                model.setStep(true);
                model.setIRR_Type("Flat");
                model.setEMI_Amount(tvTotalAmountStepEMI.getText().toString());


                for(int i=0;i<llStepEMI.getChildCount();i++){
                    LinearLayout l1=(LinearLayout) llStepEMI.getChildAt(i);
                    LinearLayout l2= (LinearLayout) l1.getChildAt(0);

                    TextView mtvNoOFemi = l2.findViewById(R.id.tvNoOFemi);
                    int noOfemi=Integer.parseInt(mtvNoOFemi.getText().toString());

                    EditText edEMIAmount = l2.findViewById(R.id.edEMIAmount);
                    float emiAmount=Float.parseFloat(edEMIAmount.getText().toString());

                    for(int z=0;z<noOfemi;z++){
                        cashFlow.add(String.valueOf(emiAmount));
                    }


                }

                model.setCashflow(cashFlow);

            }
            else{
                model.setStep(false);
                model.setIRR_Type("Flat");
                model.setEMI_Amount(edROIInput.getText().toString());
            }


            Call<List<CalculateIRRResponse>> call = api.getCalculateEMIIRR(model);

            call.enqueue(new Callback<List<CalculateIRRResponse>>() {
                @Override
                public void onResponse(Call<List<CalculateIRRResponse>> call, Response<List<CalculateIRRResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    llCalculate_EMI_IRR.setVisibility(View.VISIBLE);
                    List<CalculateIRRResponse> calculateIRRResponse = response.body();
                    if (calculateIRRResponse != null && calculateIRRResponse.size()>0) {


                        tvDisbursement_Amt.setText( String.format("%.2f", calculateIRRResponse.get(0).getDisbursement_Amt()));
                        tvInterest_Amount.setText(String.format("%.2f",calculateIRRResponse.get(0).getInterest_Amt()));
                        tvEMIAmount.setText(String.format("%.2f",Math.ceil(calculateIRRResponse.get(0).getEMI_Amt())));
                        tvAgreement_Value.setText(String.format("%.2f",calculateIRRResponse.get(0).getAgreement_Value()));
                        tvLTV.setText(String.format("%.2f",calculateIRRResponse.get(0).getLTV()));
                        tvROI.setText(String.format("%.2f",calculateIRRResponse.get(0).getROI()));
                        tvCaseIRR.setText(String.format("%.2f",calculateIRRResponse.get(0).getCase_IRR()));
                        tvDisbursementIRR.setText(String.format("%.2f",calculateIRRResponse.get(0).getDisbursement_IRR()));
                        tvMargin.setText(String.format("%.2f",calculateIRRResponse.get(0).getMargin()));


                    }


                }

                @Override
                public void onFailure(Call<List<CalculateIRRResponse>> call, Throwable t) {
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

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edPreferableEMIDate.setText(sdf.format(myCalendar.getTime()));
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
        DatePickerFragment3 pickerFragment = new DatePickerFragment3(false, GenerateApplicationNewActivity.this);
        pickerFragment.setContext(GenerateApplicationNewActivity.this);
        pickerFragment.setEdittext(edittext);
        datePickerDialog = new DatePickerDialog(GenerateApplicationNewActivity.this, pickerFragment, year, month, day);
        datePickerDialog.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);


        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());


        datePickerDialog.show();
    }

    public void callWebServiceREST() {

        ArrayList<StepEMIModel> arrstepEMIModel=new ArrayList<>();
        final ProgressDialog progress;


        progress = new ProgressDialog(GenerateApplicationNewActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);

            GenerateApplicationRequest model = new GenerateApplicationRequest();
           if(leadResponse!=null) {
               model.setInquiryNo(leadResponse.getInquiryNo());
               model.setBranchId(leadResponse.getBranchId());
           }
           else{
               model.setInquiryNo(getIntent().getStringExtra("inquiryNo")
               );
               model.setBranchId(branchId);

           }
            model.setAsset_Cost(edAssetsCost.getText().toString());
            model.setNetFinance_Amt(edNetFinanceAmount.getText().toString());
            model.setNo_Of_Inst(edNoOfInstallments.getText().toString());
            model.setTenure(edTenureMonth.getText().toString());
            model.setAdv_Inst(edInstallmentInAdvance.getText().toString());
            model.setMgmtFee(edManagementFee.getText().toString());
            model.setFirst_EMI_Date(edPreferableEMIDate.getText().toString()+"T11:44:03.000Z");

            SpinnerModel spinnerModel= (SpinnerModel) sp_emi_Frequency.getSelectedItem();

            model.setEMI_Type(spinnerModel.getName());
            model.setIRR_CalculateBy(roiType);
            if(roiType.equalsIgnoreCase("ROI")){
                model.setIRR_Type("Flat");
                model.setFlat_Rate(edROIInput.getText().toString());
            }
            else if(roiType.equalsIgnoreCase("STEP_EMI")){

                model.setEMI_Amount(tvTotalAmountStepEMI.getText().toString());
                model.setIRR_Type("Flat");

                for(int i=0;i<llStepEMI.getChildCount();i++){
                    LinearLayout l1=(LinearLayout) llStepEMI.getChildAt(i);
                    LinearLayout l2= (LinearLayout) l1.getChildAt(0);

                    EditText edFromEMI = l2.findViewById(R.id.edFromEMI);
                    EditText edToEMI = l2.findViewById(R.id.edToEMI);

                    int toEMI=Integer.parseInt(edToEMI.getText().toString());
                    int fromEMI=Integer.parseInt(edFromEMI.getText().toString());


                    TextView mtvNoOFemi = l2.findViewById(R.id.tvNoOFemi);
                    int noOfemi=Integer.parseInt(mtvNoOFemi.getText().toString());

                    EditText edEMIAmount = l2.findViewById(R.id.edEMIAmount);
                    float emiAmount=Float.parseFloat(edEMIAmount.getText().toString());

                    float totalAmount=emiAmount*(toEMI-(fromEMI-1));

                    StepEMIModel stepEMIModel=new StepEMIModel();
                    stepEMIModel.setFromEMI(edFromEMI.getText().toString());
                    stepEMIModel.setToEMI(edToEMI.getText().toString());
                    stepEMIModel.setEMI_Amount(edEMIAmount.getText().toString());
                    stepEMIModel.setDisable(true);
                    stepEMIModel.setNoOfInstl(String.valueOf(toEMI-(fromEMI-1)));
                    stepEMIModel.setTotalAmount(String.valueOf(totalAmount));

                    arrstepEMIModel.add(stepEMIModel);


                }

                model.setStepIRR(arrstepEMIModel);

            }
            else{
                model.setIRR_Type("Flat");
                model.setEMI_Amount(edROIInput.getText().toString());
            }


            model.setLoanPurpose(edLoanPurpose.getText().toString());


            if(selectedSchemeItem!=null){
                model.setLoan_SchemeId(String.valueOf(selectedSchemeItem.getSchemeId()));
            }

            SpinnerModel spinnerModelP = (SpinnerModel) sp_loan_type.getSelectedItem();
            model.setProductId(spinnerModelP.getId());
            model.setLoginUserId(session.getUserId());

            model.setDisbursement_Amt(tvDisbursement_Amt.getText().toString());
            model.setInterest_Amt(tvInterest_Amount.getText().toString());
            model.setLTV(tvLTV.getText().toString());
            model.setEMI_Amt(tvEMIAmount.getText().toString());
            model.setCase_IRR(tvCaseIRR.getText().toString());
            model.setROI(tvROI.getText().toString());
            model.setMargin(tvMargin.getText().toString());
            model.setAgreement_Value(tvAgreement_Value.getText().toString());
            model.setDisbursement_IRR(tvDisbursementIRR.getText().toString());
            String res="";
            if(appId!=0){
                model.setApplicationId(String.valueOf(appId));
                ApplicationGenerateUpdateModel applicationGenerateUpdateModel=new ApplicationGenerateUpdateModel();
                applicationGenerateUpdateModel.setApplication(model);
                applicationGenerateUpdateModel.setStepIRR(arrstepEMIModel);
                 res = new Gson().toJson(applicationGenerateUpdateModel);
            }
            else{
                ApplicationGenerateBaseModel applicationGenerateBaseModel=new ApplicationGenerateBaseModel();
                applicationGenerateBaseModel.setApplication(model);

                 res = new Gson().toJson(applicationGenerateBaseModel);
            }







            Log.d("my_json", res);



            CustomerRegMainRequest customerRegMainRequest = new CustomerRegMainRequest();
            customerRegMainRequest.setJson(res);
            Call<List<AcceptRejectResponse>> call;
            if(appId!=0){
                 call = api.getUpdateApplicationFinancialDetails(customerRegMainRequest);
            }
            else{
                call = api.getGenerateLead(customerRegMainRequest);
            }


            call.enqueue(new Callback<List<AcceptRejectResponse>>() {
                @Override
                public void onResponse(Call<List<AcceptRejectResponse>> call, Response<List<AcceptRejectResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }

                    List<AcceptRejectResponse> loginResponse = response.body();
                    if (loginResponse != null ) {

                        if(loginResponse.get(0).getCODE()!=-1 && appId==0) {
                            String msg = loginResponse.get(0).getMSG();
                            Toast.makeText(GenerateApplicationNewActivity.this, msg, Toast.LENGTH_LONG).show();

                            String[] arr = msg.split("\\s+");

                            Intent intent = new Intent(GenerateApplicationNewActivity.this, MyApplicationDetailActivity.class);
                            intent.putExtra("applicationId", loginResponse.get(0).getCODE());
                            intent.putExtra("appNo", arr[3]);
                            intent.putExtra("from", "add");
                            intent.putExtra("TAG", "My Applications");
                            startActivity(intent);
                            finish();
                        }
                        else {
                            String msg = loginResponse.get(0).getMSG();
                            Toast.makeText(GenerateApplicationNewActivity.this, msg, Toast.LENGTH_LONG).show();

                            finish();

                        }


                        /*else{
                            String msg = loginResponse.get(0).getMSG();
                            Toast.makeText(GenerateApplicationNewActivity.this, msg, Toast.LENGTH_LONG).show();

                        }*/

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

    public void emptyData(){

       tvDisbursement_Amt.setText("");
     tvInterest_Amount.setText("");
       tvLTV.setText("");
      tvEMIAmount.setText("");
   tvCaseIRR.setText("");
        tvROI.setText("");
      tvMargin.setText("");
       tvAgreement_Value.setText("");
        tvDisbursementIRR.setText("");

    }


    public void callWebServiceSchemeREST() {
        final ProgressDialog progress;


        progress = new ProgressDialog(GenerateApplicationNewActivity.this);
        progress.setMessage("Please wait...");
        progress.setCancelable(false);
        progress.show();

        try {
            Retrofit retrofit = ApiClient.getClient(getApplicationContext());
            //creating the api interface
            Api api = retrofit.create(Api.class);
            Call<List<SchemeResponse>> call = null;
            SpinnerModel spinnerModelP = (SpinnerModel) sp_loan_type.getSelectedItem();
            SchemeRequest schemeRequest=new SchemeRequest();
            schemeRequest.setProductId(Integer.parseInt(spinnerModelP.getId()));
                call = api.getSchemeList(schemeRequest);



            call.enqueue(new Callback<List<SchemeResponse>>() {
                @Override
                public void onResponse(Call<List<SchemeResponse>> call, Response<List<SchemeResponse>> response) {
                    if (progress != null && progress.isShowing()) {
                        progress.dismiss();
                    }
                    schemeList = response.body();






                }

                @Override
                public void onFailure(Call<List<SchemeResponse>> call, Throwable t) {
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
    public void DateOnClicked(String date) {

    }
}
