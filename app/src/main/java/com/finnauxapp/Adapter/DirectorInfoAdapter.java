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

import com.finnauxapp.Activities.CustomerKYCDocListActivity;
import com.finnauxapp.Activities.FamilyMemberListActivity;
import com.finnauxapp.Activities.FirmPartnerSaveActivity;
import com.finnauxapp.Activities.LeadDetailActivity;
import com.finnauxapp.Activities.MyApplicationDetailActivity;
import com.finnauxapp.ApiResponse.ApplicationListResponse;
import com.finnauxapp.ApiResponse.CustomerMemberListResponse;
import com.finnauxapp.ApiResponse.DashboardDataResponse;
import com.finnauxapp.ApiResponse.FirmResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DirectorInfoAdapter extends RecyclerView.Adapter {
    private List<FirmResponse> homeList;
    private Context context;




    public DirectorInfoAdapter(Context context, List<FirmResponse> list) {
        this.context = context;

        this.homeList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_customer_family_member, parent, false);
        return new HomeHolder(v);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        HomeHolder holder = (HomeHolder) viewHolder;
        FirmResponse response=homeList.get(position);




        holder.tvName.setText("Partner name : "+response.getPartner_Name());
        holder.tvAge.setText("Age : "+response.getPartner_Age());
        holder.tvPhone.setText("Phone : "+response.getPartner_PhoneNo());
        holder.tvJob.setText("Designation : "+response.getPartner_Designation());
        holder.tvIncome.setText("Share : "+response.getPartner_SharePer()+"%");






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
                FirmPartnerSaveActivity activity=((FirmPartnerSaveActivity)context);
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
        public TextView tvAge;
        public TextView tvPhone;
        public TextView tvJob;
        public TextView tvIncome;

        public View viewDivider;
        public ImageView ivEdit;

        public HomeHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvAge = (TextView) itemView.findViewById(R.id.tvAge);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            tvJob = (TextView) itemView.findViewById(R.id.tvJob);
            tvIncome = (TextView) itemView.findViewById(R.id.tvIncome);
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

