package com.finnauxapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Activities.CustomerExistingLoanActivity;
import com.finnauxapp.Activities.CustomerExpenditureListActivity;
import com.finnauxapp.Activities.LeadDetailActivity;
import com.finnauxapp.Activities.MyApplicationDetailActivity;
import com.finnauxapp.ApiResponse.ApplicationListResponse;
import com.finnauxapp.ApiResponse.DashboardDataResponse;
import com.finnauxapp.ApiResponse.ExistingLoanResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CustomerExistingLoanAdapter extends RecyclerView.Adapter {
    private List<ExistingLoanResponse> homeList;
    private Context context;




    public CustomerExistingLoanAdapter(Context context, List<ExistingLoanResponse> list) {
        this.context = context;

        this.homeList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_existing_loans, parent, false);
        return new HomeHolder(v);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        HomeHolder holder = (HomeHolder) viewHolder;
        ExistingLoanResponse response=homeList.get(position);






         holder.tvLoanName.setText(response.getLoanType());
         holder.tvYear.setText("Year : "+response.getLoanTakenFrom()+", Status :"+response.getLoanStatus().trim());
         holder.tvDuration.setText("Duration : "+response.getLoanDuration_Month()+"yr, Amount : "+response.getLoanAmount());
        holder.tvOutStanding.setText("OutStanding : "+response.getCurrentOutStandingAmount()+", EMI : "+response.getMonthlyEMI());
        holder.tvBank.setText("Bank : "+response.getLoanTakenFrom());


        if(position % 2==0){
            holder.viewDivider.setBackgroundColor(context.getResources().getColor(R.color.color_orange));
        }
        else{
            holder.viewDivider.setBackgroundColor(context.getResources().getColor(R.color.color_blue));
        }
        holder.ivEdit.setTag(position);
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();
                CustomerExistingLoanActivity activity=((CustomerExistingLoanActivity)context);
                activity.addEditMember(homeList.get(pos));
            }
        });

    }


    @Override
    public int getItemCount() {
        return homeList.size();
    }


    public class HomeHolder extends RecyclerView.ViewHolder {

        public TextView tvLoanName;
        public TextView tvYear;
        public TextView tvDuration;
        public TextView tvOutStanding;
        public TextView tvBank;

        public View viewDivider;
        public ImageView ivEdit;


        public HomeHolder(View itemView) {
            super(itemView);
            tvLoanName = (TextView) itemView.findViewById(R.id.tvLoanName);
            tvYear = (TextView) itemView.findViewById(R.id.tvYear);
            tvDuration = (TextView) itemView.findViewById(R.id.tvDuration);
            tvOutStanding = (TextView) itemView.findViewById(R.id.tvOutStanding);
            tvBank = (TextView) itemView.findViewById(R.id.tvBank);

            ivEdit =  itemView.findViewById(R.id.ivEdit);
            viewDivider =  itemView.findViewById(R.id.viewDivider);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }


    }

}


