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
import com.finnauxapp.Activities.CustomerReferenceListActivity;
import com.finnauxapp.Activities.LeadDetailActivity;
import com.finnauxapp.Activities.MyApplicationDetailActivity;
import com.finnauxapp.ApiResponse.ApplicationListResponse;
import com.finnauxapp.ApiResponse.DashboardDataResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.ApiResponse.ReferenceResponse;
import com.finnauxapp.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CustomerReferenceAdapter extends RecyclerView.Adapter {
    private List<ReferenceResponse> homeList;
    private Context context;




    public CustomerReferenceAdapter(Context context, List<ReferenceResponse> list) {
        this.context = context;

        this.homeList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_customer_reference, parent, false);
        return new HomeHolder(v);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        HomeHolder holder = (HomeHolder) viewHolder;
        ReferenceResponse response=homeList.get(position);




        holder.tvName.setText(response.getPersonName());
        holder.tvRefType.setText("Ref Type : "+response.getReferenceType());
        holder.tvPhone.setText("Phone : "+response.getContactNo());
        holder.tvAddress.setText("Address : "+response.getPersonAddress());
        holder.tvKnownFrom.setText("Know from : "+response.getPersonKnowYear());
        holder.tvComment.setText("Comment : "+response.getPersonComments());





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
                CustomerReferenceListActivity activity=((CustomerReferenceListActivity)context);
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
        public TextView tvRefType;
        public TextView tvPhone;
        public TextView tvAddress;
        public TextView tvKnownFrom;
        public TextView tvComment;
        public ImageView ivEdit;
        public View viewDivider;


        public HomeHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvRefType = (TextView) itemView.findViewById(R.id.tvRefType);
            tvAddress = (TextView) itemView.findViewById(R.id.tvAddress);
            tvPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            tvKnownFrom = (TextView) itemView.findViewById(R.id.tvKnownFrom);
            tvComment = (TextView) itemView.findViewById(R.id.tvComment);
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


