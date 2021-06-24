package com.finnauxapp.Adapter;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Activities.AssetDetailActivity;
import com.finnauxapp.Activities.CustomerKYCDocListActivity;
import com.finnauxapp.Activities.CustomerRegistrationActivity;
import com.finnauxapp.Activities.FamilyMemberListActivity;
import com.finnauxapp.Activities.RequiredDocListActivity;
import com.finnauxapp.ApiResponse.AssetListResponse;
import com.finnauxapp.ApiResponse.QuestionAnswerResponse;
import com.finnauxapp.ApiResponse.RequiredDOCListResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.DateOnClick;
import com.finnauxapp.Util.DatePickerFragment;
import com.finnauxapp.Util.DatePickerFragment2;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.pickit.PickiTCallbacks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RequiredDocAdapter extends RecyclerView.Adapter implements DateOnClick<String> {
    private List<RequiredDOCListResponse> homeList;
    private Context context;
    private AssetListResponse mResponse;
    String appNo;


    public RequiredDocAdapter(Context context, List<RequiredDOCListResponse> list,String appNo) {
        this.context = context;

        this.homeList = list;
        this.appNo=appNo;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_required_doc_list, parent, false);
        return new HomeHolder(v);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        final HomeHolder holder = (HomeHolder) viewHolder;
        final RequiredDOCListResponse response=homeList.get(position);

        holder.tvAnswer.setTag(position);

        holder.btn_browse.setTag(position);
      holder.tvCustomerName.setTag(position);
      holder.tvDocName.setTag(position);
      holder.edDocNo.setTag(position);

      holder.tvCustomerName.setText(response.getCustomer());

        if(response.getCustomer().equalsIgnoreCase("Application")){
            holder.tvTagName.setText("Application No :" +appNo);
        }
      else{

            holder.tvTagName.setText("Customer name :");

        }

      holder.tvDocName.setText(response.getDocName());

            holder.tvAnswer.setFocusableInTouchMode(false);

        holder.tvAnswer.setText(response.getDocFileName());
        holder.edDocNo.setText(response.getDocNo());


        if(response.getDocName().equalsIgnoreCase("Aadhaar Card")){
            holder.edDocNo.setInputType(InputType.TYPE_CLASS_PHONE);
        }
        else{
            holder.edDocNo.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        }

      /*  holder.edDocNo.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                int pos=(int)v.getTag();
                if (event.getAction() == KeyEvent.ACTION_UP) {
                    if(holder.edDocNo.getText().length()>0){
                        homeList.get(pos).setDocNo(holder.edDocNo.getText().toString());
                    }
                    else{
                        homeList.get(pos).setDocNo("");
                    }
                }
                return false;
            }
        });
*/

        holder.edDocNo.addTextChangedListener(new TextWatcher() {

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
                int pos=(int)holder.edDocNo.getTag();
                if(s.length()==0){

                    homeList.get(pos).setDocNo("");
                }
                else{
                    homeList.get(pos).setDocNo(s.toString());

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String s1=s.toString();
                if(!s1.equals(s1.toUpperCase()))
                {
                    s1=s1.toUpperCase();
                    holder.edDocNo.setText(s1);
                    holder.edDocNo.setSelection(holder.edDocNo.length()); //fix reverse texting
                }
               // SpinnerModel spinnerModel=(SpinnerModel)sp_doc_type.getSelectedItem();



                if (!isInputCorrect(s, TOTAL_SYMBOLS, DIVIDER_MODULO, DIVIDER) && response.getDocName().equalsIgnoreCase("aadhaar card")) {
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

            holder.btn_browse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   int pos=(int)v.getTag();
                    showChoosen(holder.tvAnswer,homeList.get(pos));
                }
            });


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

        public TextView tvCustomerName;
        public TextView tvTagName;
        public TextView tvDocName;
        public EditText tvAnswer;
        public EditText edDocNo;

        public Button btn_browse;

        public View viewDivider;


        public HomeHolder(View itemView) {
            super(itemView);
            tvCustomerName = (TextView) itemView.findViewById(R.id.tvCustomerName);
            tvTagName = (TextView) itemView.findViewById(R.id.tvTagName);
            tvDocName = (TextView) itemView.findViewById(R.id.tvDocName);
            tvAnswer = (EditText) itemView.findViewById(R.id.tvAnswer);
            edDocNo = (EditText) itemView.findViewById(R.id.edDocNo);

            btn_browse = itemView.findViewById(R.id.btn_browse);

            viewDivider =  itemView.findViewById(R.id.viewDivider);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }


    }

    public List<RequiredDOCListResponse> getList(){

        return homeList;
    }
    public void showChoosen(EditText tvAnswer, RequiredDOCListResponse response){
        RequiredDocListActivity activity=(RequiredDocListActivity)context;
        activity.showChoosenDialog(tvAnswer,response);
    }
}