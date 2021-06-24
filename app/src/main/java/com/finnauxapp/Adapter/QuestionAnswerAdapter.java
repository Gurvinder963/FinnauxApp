package com.finnauxapp.Adapter;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Activities.FamilyMemberListActivity;
import com.finnauxapp.ApiResponse.QuestionAnswerResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SpinnerModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerAdapter extends RecyclerView.Adapter {
    private List<QuestionAnswerResponse> homeList;
    private Context context;
    boolean isHindi;




    public QuestionAnswerAdapter(Context context, List<QuestionAnswerResponse> list,boolean isHindi) {
        this.context = context;
        this.isHindi=isHindi;
        this.homeList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_question_answer, parent, false);
        return new HomeHolder(v);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        final HomeHolder holder = (HomeHolder) viewHolder;
        final QuestionAnswerResponse response=homeList.get(position);

        holder.tvQuestion.setTag(position);
        holder.tvAnswer.setTag(position);
        holder.sp_Answer_type.setTag(position);

        if(isHindi){
            holder.tvQuestion.setText(position+1+".> Question : "+response.getQuestion_Hindi());
        }
        else{
            holder.tvQuestion.setText(position+1+".> Question : "+response.getQuestion());
        }



        if(response.getQuestionType().equalsIgnoreCase("Text")){
            holder.tvAnswer.setText(response.getFIAnswer());
            holder.tvAnswer.setVisibility(View.VISIBLE);
            holder.sp_Answer_type.setVisibility(View.GONE);
            holder.tvAnswer.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    int pos=(int)holder.tvAnswer.getTag();
                    homeList.get(pos).setFIAnswer(s.toString());


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
        else  if(response.getQuestionType().equalsIgnoreCase("Selection")){
           holder.tvAnswer.setVisibility(View.GONE);
            holder.sp_Answer_type.setVisibility(View.VISIBLE);
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

            String answer=response.getFIAnswer();
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

                     int pos=(int)holder.sp_Answer_type.getTag();
                     homeList.get(pos).setFIAnswer(model23.getName());
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


    public class HomeHolder extends RecyclerView.ViewHolder {

        public TextView tvQuestion;
        public EditText tvAnswer;
        public Spinner sp_Answer_type;

        public View viewDivider;


        public HomeHolder(View itemView) {
            super(itemView);
            tvQuestion = (TextView) itemView.findViewById(R.id.tvQuestion);
            tvAnswer = (EditText) itemView.findViewById(R.id.tvAnswer);
            sp_Answer_type = (Spinner) itemView.findViewById(R.id.sp_Answer_type);

            viewDivider =  itemView.findViewById(R.id.viewDivider);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }


    }

    public List<QuestionAnswerResponse> getList(){

        return homeList;
    }

    public void setQuestionLang(boolean isHindi){
        this.isHindi=isHindi;
    }

}



