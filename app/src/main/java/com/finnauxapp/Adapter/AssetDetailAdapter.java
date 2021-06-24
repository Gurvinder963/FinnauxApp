package com.finnauxapp.Adapter;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Activities.AssetDetailActivity;
import com.finnauxapp.Activities.CustomerKYCDocListActivity;
import com.finnauxapp.Activities.CustomerRegistrationActivity;
import com.finnauxapp.Activities.FamilyMemberListActivity;
import com.finnauxapp.ApiResponse.AssetListResponse;
import com.finnauxapp.ApiResponse.QuestionAnswerResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.DateOnClick;
import com.finnauxapp.Util.DatePickerFragment;
import com.finnauxapp.Util.DatePickerFragment2;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.pickit.PickiTCallbacks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AssetDetailAdapter extends RecyclerView.Adapter implements DateOnClick<String> {
    private List<AssetListResponse> homeList;
    private Context context;
    private AssetListResponse mResponse;


    public AssetDetailAdapter(Context context, List<AssetListResponse> list) {
        this.context = context;

        this.homeList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_asset_detail, parent, false);
        return new HomeHolder(v);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        final HomeHolder holder = (HomeHolder) viewHolder;
        final AssetListResponse response=homeList.get(position);


        holder.tvQuestion.setTag(position);
        holder.tvAnswer.setTag(position);
        holder.sp_Answer_type.setTag(position);
        holder.btn_browse.setTag(position);
        holder.rbAns.setTag(position);
        holder.rbYes.setTag(position);
        holder.rbNo.setTag(position);
        holder.ivAsterick.setTag(position);

        holder.tvQuestion.setText(response.getQuestion());

        if(response.isMandatory()){
            holder.ivAsterick.setVisibility(View.VISIBLE);
        }
        else {
            holder.ivAsterick.setVisibility(View.GONE);
        }

        if(response.getQuestionType().equalsIgnoreCase("Text")){
            holder.btn_browse.setVisibility(View.GONE);
            holder.tvAnswer.setText(response.getAnswer());
            holder.tvAnswer.setVisibility(View.VISIBLE);
            holder.sp_Answer_type.setVisibility(View.GONE);
            holder.tvAnswer.setFocusableInTouchMode(true);
            holder.rbAns.setVisibility(View.GONE);
            holder.tvAnswer.setHint("Give Answer");
            holder.tvAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
            holder.tvAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
  }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                   // response.setAnswer(s.toString());
                    int pos=(int)holder.tvAnswer.getTag();
                    homeList.get(pos).setAnswer(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
     }
            });

        }

        else if(response.getQuestionType().equalsIgnoreCase("Integer")){
            holder.btn_browse.setVisibility(View.GONE);
            holder.tvAnswer.setText(response.getAnswer());
            holder.tvAnswer.setVisibility(View.VISIBLE);
            holder.rbAns.setVisibility(View.GONE);
            holder.tvAnswer.setFocusableInTouchMode(true);
           holder.tvAnswer.setMinLines(1);
            holder.tvAnswer.setMaxLines(1);
            holder.tvAnswer.setLines(1);
            holder.tvAnswer.setHint("Give Answer");
          holder.tvAnswer.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            holder.sp_Answer_type.setVisibility(View.GONE);
            holder.tvAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int pos=(int)holder.tvAnswer.getTag();
                    homeList.get(pos).setAnswer(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });

        }
        else if(response.getQuestionType().equalsIgnoreCase("Boolean")){
            holder.btn_browse.setVisibility(View.GONE);
            holder.tvAnswer.setVisibility(View.GONE);
            holder.sp_Answer_type.setVisibility(View.GONE);
            holder.rbAns.setVisibility(View.VISIBLE);
            String ans=response.getAnswer();
            if(ans!=null) {
                if (ans.equalsIgnoreCase("Yes")) {
                    holder.rbYes.setChecked(true);
                } else if (ans.equalsIgnoreCase("No")) {
                    holder.rbNo.setChecked(true);
                }
            }
            holder.rbAns.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Log.d("checked_id",checkedId+"");


                    switch (checkedId) {

                        case R.id.rbYes:

                            response.setAnswer("Yes");
                            break;

                        case R.id.rbNo:

                            response.setAnswer("No");
                            break;


                    }
                }

            });
        }


        else if(response.getQuestionType().equalsIgnoreCase("Calendar")){
            holder.btn_browse.setVisibility(View.GONE);
            holder.tvAnswer.setText(response.getAnswer());
            holder.tvAnswer.setVisibility(View.VISIBLE);
            holder.tvAnswer.setFocusableInTouchMode(false);
            holder.rbAns.setVisibility(View.GONE);
            holder.tvAnswer.setHint("--Choose Date--");
            holder.tvAnswer.setMinLines(1);
            holder.tvAnswer.setMaxLines(1);
            holder.tvAnswer.setLines(1);
            holder.tvAnswer.setInputType(InputType.TYPE_CLASS_NUMBER);
            holder.tvAnswer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog(holder.tvAnswer,response);
                }
            });


            holder.sp_Answer_type.setVisibility(View.GONE);


        }
        else if(response.getQuestionType().equalsIgnoreCase("Upload")){
            holder.btn_browse.setVisibility(View.VISIBLE);
            holder.tvAnswer.setText(response.getAnswer());
            holder.tvAnswer.setVisibility(View.VISIBLE);
            holder.tvAnswer.setFocusableInTouchMode(false);
            holder.rbAns.setVisibility(View.GONE);
            holder.tvAnswer.setHint("no file chosen");
            holder.tvAnswer.setMinLines(1);
            holder.tvAnswer.setMaxLines(1);
            holder.tvAnswer.setLines(1);
            holder.tvAnswer.setInputType(InputType.TYPE_CLASS_TEXT);
            holder.btn_browse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int pos=(int)holder.tvAnswer.getTag();
                    showChoosen(holder.tvAnswer,homeList.get(pos));
                }
            });


            holder.sp_Answer_type.setVisibility(View.GONE);


        }

        else  if(response.getQuestionType().equalsIgnoreCase("Select")){
            holder.btn_browse.setVisibility(View.GONE);
            holder.tvAnswer.setVisibility(View.GONE);
            holder.sp_Answer_type.setVisibility(View.VISIBLE);
            holder.rbAns.setVisibility(View.GONE);
            ArrayList<SpinnerModel> list=new ArrayList<>();
            String[] arr = response.getQuestionOptions().split("@@");

            SpinnerModel model1=new SpinnerModel();
            model1.setId(String.valueOf(-1));
            model1.setName("--Select Answer--");
            list.add(model1);

            for(int i=0;i<arr.length;i++){

                SpinnerModel model=new SpinnerModel();
                model.setId(String.valueOf(i));
                model.setName(arr[i]);
                list.add(model);
            }


            SpinnerAdapter enqiryAdapter = new SpinnerAdapter(context, list);
            holder.sp_Answer_type.setAdapter(enqiryAdapter);

            String answer=response.getAnswer();
            if(answer!=null && !answer.isEmpty()){

                for(int i=0;i<list.size();i++){

                    if(answer.equalsIgnoreCase(list.get(i).getName())){
                        holder.sp_Answer_type.setSelection(i);
                        break;

                    }
                }
            }
            holder.sp_Answer_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    SpinnerModel model23= (SpinnerModel) holder.sp_Answer_type.getSelectedItem();
                    if(!model23.getId().equalsIgnoreCase("-1")) {
                        response.setAnswer(model23.getName());
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }



        if(position % 2==0){
            holder.viewDivider.setBackgroundColor(context.getResources().getColor(R.color.color_orange));
        }
        else{
            holder.viewDivider.setBackgroundColor(context.getResources().getColor(R.color.color_blue));
        }

    }


    @Override
    public int getItemCount() {
        return homeList.size();
    }

    @Override
    public void DateOnClicked(String date) {
        mResponse.setAnswer(date);
    }


    public class HomeHolder extends RecyclerView.ViewHolder {

        public TextView tvQuestion;
        public EditText tvAnswer;
        public Spinner sp_Answer_type;
        public RadioGroup rbAns;
        public RadioButton rbYes;
        public RadioButton rbNo;
        public Button btn_browse;
        public ImageView ivAsterick;

        public View viewDivider;


        public HomeHolder(View itemView) {
            super(itemView);
            tvQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
            tvAnswer = (EditText) itemView.findViewById(R.id.tvAnswer);
            sp_Answer_type = (Spinner) itemView.findViewById(R.id.sp_Answer_type);
            rbAns = itemView.findViewById(R.id.rbAns);
            rbYes = itemView.findViewById(R.id.rbYes);
            rbNo = itemView.findViewById(R.id.rbNo);
            btn_browse = itemView.findViewById(R.id.btn_browse);
            ivAsterick = itemView.findViewById(R.id.ivAsterick);

            viewDivider =  itemView.findViewById(R.id.viewDivider);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }


    }
    private void showDatePickerDialog(EditText tvAnswer, AssetListResponse response) {
        mResponse=response;
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
        DatePickerFragment pickerFragment = new DatePickerFragment(false, AssetDetailAdapter.this);
        pickerFragment.setContext(context);
        pickerFragment.setEdittext(tvAnswer);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, pickerFragment, year, month, day);
        datePickerDialog.getDatePicker().setDescendantFocusability(DatePicker.FOCUS_BLOCK_DESCENDANTS);
      //  datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }
    public List<AssetListResponse> getList(){

        return homeList;
    }
public void showChoosen(EditText tvAnswer, AssetListResponse response){
    AssetDetailActivity activity=(AssetDetailActivity)context;
    activity.showChoosenDialog(tvAnswer,response);
}
}