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

import com.finnauxapp.Activities.LeadDetailActivity;
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

public class LeadAdapter extends RecyclerView.Adapter {
    private List<LeadResponse> homeList;
    private Context context;
    String from;



    public LeadAdapter(Context context, List<LeadResponse> list, String from) {
        this.context = context;
        this.from=from;
        this.homeList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_lead_item, parent, false);
        return new HomeHolder(v);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        HomeHolder holder = (HomeHolder) viewHolder;
        LeadResponse response=homeList.get(position);
        holder.tvName.setText(response.getCustomerName());
        holder.tvInquiryBranch.setText(response.getInquiryNo()+" , "+response.getBranch());
        holder.tvMobile.setText(response.getContactNumber());
        holder.tvDescription.setText(response.getCustomerAddress());

      try {
          String[] dateArray = response.getCreateOn().split("\\s+");

          holder.tvDate.setText(dateArray[0]+" "+dateArray[1]+" "+dateArray[2]);
          holder.tvTime.setText(dateArray[dateArray.length-1]+"");

      }
      catch (Exception e){
          e.printStackTrace();
      }

     //   holder.tvCount.setText(response.getValue()+"");

        if(from.equalsIgnoreCase("Hold Inquiry")){
            holder.tvHoldOn.setVisibility(View.VISIBLE);
            holder.tvHoldReason.setVisibility(View.VISIBLE);

            holder.tvHoldOn.setText("Hold on : "+response.getHoldOn());
            holder.tvHoldReason.setText("Hold Reason : "+response.getHoldReason());
        }
        else{
            holder.tvHoldOn.setVisibility(View.GONE);
            holder.tvHoldReason.setVisibility(View.GONE);
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

        public TextView tvName;
        public TextView tvMobile;
        public TextView tvDescription;
        public TextView tvDate;
        public TextView tvTime;
        public TextView tvInquiryBranch;
        public TextView tvHoldOn;
        public TextView tvHoldReason;
        public ImageView ivProfile;
        public View viewDivider;

        public HomeHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvMobile = (TextView) itemView.findViewById(R.id.tvMobile);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            ivProfile =  itemView.findViewById(R.id.ivProfile);
            tvInquiryBranch =  itemView.findViewById(R.id.tvInquiryBranch);
            tvHoldOn =  itemView.findViewById(R.id.tvHoldOn);
            tvHoldReason =  itemView.findViewById(R.id.tvHoldReason);
            viewDivider =  itemView.findViewById(R.id.viewDivider);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Intent intent =new Intent(context, LeadDetailActivity.class);
                    intent.putExtra("data",homeList.get(getAdapterPosition()));
                    context.startActivity(intent);

                }
            });

        }


    }

}

