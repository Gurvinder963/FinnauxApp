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

import com.finnauxapp.Activities.FIMyApplicationActivity;
import com.finnauxapp.Activities.LeadActivity;
import com.finnauxapp.Activities.MyApplicationActivity;
import com.finnauxapp.Activities.PendingDOCActivity;
import com.finnauxapp.Activities.SendBackBranchApplicationActivity;
import com.finnauxapp.ApiResponse.DashboardDataResponse;
import com.finnauxapp.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter {
    private List<DashboardDataResponse> homeList;
    private Context context;




    public HomeAdapter(Context context, List list) {
        this.context = context;

        this.homeList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_dashboard_item, parent, false);
        return new HomeHolder(v);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        HomeHolder holder = (HomeHolder) viewHolder;
    DashboardDataResponse response=homeList.get(position);
    holder.tvName.setText(response.getKey());
    holder.tvCount.setText(response.getValue()+"");

/*    if(response.getKey().equalsIgnoreCase("file processing")){
        holder.ivImage.setImageResource(R.drawable.file_process);
    }
    else if(response.getKey().equalsIgnoreCase("My Leads")){
            holder.ivImage.setImageResource(R.drawable.leads_ic1);
        }

    else if(response.getKey().equalsIgnoreCase("pending doc")){
        holder.ivImage.setImageResource(R.drawable.pending_doc_ic);
    }*/



    }


    @Override
    public int getItemCount() {
        return homeList.size();
    }


    public class HomeHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvCount;
        public ImageView ivImage;


        public HomeHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvCount = (TextView) itemView.findViewById(R.id.tvCount);
      //      ivImage =  itemView.findViewById(R.id.ivImage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(homeList.get(getAdapterPosition()).getKey().equalsIgnoreCase("My Leads")){

                        Intent intent=new Intent(context, LeadActivity.class);
                        intent.putExtra("TAG","New Inquiry");
                        context.startActivity(intent);

                    }
                    else if(homeList.get(getAdapterPosition()).getKey().equalsIgnoreCase("My Applications")){

                        Intent intent=new Intent(context, MyApplicationActivity.class);

                        context.startActivity(intent);

                    }

                    else if(homeList.get(getAdapterPosition()).getKey().equalsIgnoreCase("My FI Applications")){

                        Intent intent=new Intent(context, FIMyApplicationActivity.class);

                        context.startActivity(intent);

                    }

                    else if(homeList.get(getAdapterPosition()).getKey().equalsIgnoreCase("File SendBack To Branch")){

                        Intent intent=new Intent(context, SendBackBranchApplicationActivity.class);

                        context.startActivity(intent);

                    }
                    else if(homeList.get(getAdapterPosition()).getKey().equalsIgnoreCase("Pending Doc")){

                        Intent intent=new Intent(context, PendingDOCActivity.class);

                        context.startActivity(intent);

                    }
                }
            });

        }


    }

}
