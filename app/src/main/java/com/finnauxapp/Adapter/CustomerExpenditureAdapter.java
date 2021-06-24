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

import com.finnauxapp.Activities.CustomerExpenditureListActivity;
import com.finnauxapp.Activities.FamilyMemberListActivity;
import com.finnauxapp.Activities.LeadDetailActivity;
import com.finnauxapp.Activities.MyApplicationDetailActivity;
import com.finnauxapp.ApiResponse.ApplicationListResponse;
import com.finnauxapp.ApiResponse.CustomerExpenditureResponse;
import com.finnauxapp.ApiResponse.DashboardDataResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CustomerExpenditureAdapter extends RecyclerView.Adapter {
    private List<CustomerExpenditureResponse> homeList;
    private Context context;




    public CustomerExpenditureAdapter(Context context, List<CustomerExpenditureResponse> list) {
        this.context = context;

        this.homeList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_customer_expenditure, parent, false);
        return new HomeHolder(v);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        HomeHolder holder = (HomeHolder) viewHolder;
        CustomerExpenditureResponse response=homeList.get(position);



        holder.tvName.setText("Head : "+response.getExpenditureType());
        holder.tvAmount.setText("Amount : Rs. "+response.getTotalExpenditureAmount());
        holder.tvDuration.setText("Expense Frequency : "+response.getRemark());






        //   holder.tvCount.setText(response.getValue()+"");


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
                CustomerExpenditureListActivity activity=((CustomerExpenditureListActivity)context);
                activity.addEditMember(homeList.get(pos));
            }
        });

    }


    @Override
    public int getItemCount() {
        return homeList.size();
    }


    public class HomeHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvAmount;
        public TextView tvDuration;
        public ImageView ivEdit;

        public View viewDivider;


        public HomeHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAmount = (TextView) itemView.findViewById(R.id.tvAmount);
            tvDuration = (TextView) itemView.findViewById(R.id.tvDuration);
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

