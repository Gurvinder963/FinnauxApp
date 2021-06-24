package com.finnauxapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.finnauxapp.Activities.LeadDetailActivity;
import com.finnauxapp.ApiResponse.DashboardDataResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.ApiResponse.SchemeResponse;
import com.finnauxapp.R;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SchemeAdapter extends RecyclerView.Adapter {
    private List<SchemeResponse> homeList;
    private Context context;




    public SchemeAdapter(Context context, List<SchemeResponse> list) {
        this.context = context;

        this.homeList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_scheme_item, parent, false);
        return new HomeHolder(v);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        HomeHolder holder = (HomeHolder) viewHolder;
        SchemeResponse response=homeList.get(position);
        holder.tvSchemeProduct.setText("Product : "+response.getProduct());
        holder.tvSchemeName.setText("Name : "+response.getScheme());
        holder.tvSchemeAmount.setText("Amount : "+response.getAmount());
        holder.tvSchemePeriod.setText("Period : "+response.getPeriod());
        holder.tvSchemeState.setText("State : "+response.getSchemeState());
        holder.tvSchemeAdvance.setText("Ad EMI : "+String.valueOf(response.getAdvanceEMI()));

        holder.tvSchemeROI.setText("ROI : "+response.getROI()+"%");

      if(homeList.get(position).isSelected()){
         holder.cbScheme.setChecked(true);
      }
      else{
          holder.cbScheme.setChecked(false);
      }

        holder.cbScheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int k=0; k<homeList.size(); k++) {
                    if(k==position) {
                        homeList.get(k).setSelected(true);
                    } else {
                        homeList.get(k).setSelected(false);
                    }
                }
                notifyDataSetChanged();

            }
        });





    }


    @Override
    public int getItemCount() {
        return homeList.size();
    }


    public class HomeHolder extends RecyclerView.ViewHolder {

        public TextView tvSchemeProduct;
        public TextView tvSchemeName;
        public TextView tvSchemeAmount;
        public TextView tvSchemePeriod;
        public TextView tvSchemeAdvance;
        public TextView tvSchemeState;
        public TextView tvSchemeROI;
        public CheckBox cbScheme;



        public HomeHolder(View itemView) {
            super(itemView);
            tvSchemeProduct = (TextView) itemView.findViewById(R.id.tvSchemeProduct);
            tvSchemeName = (TextView) itemView.findViewById(R.id.tvSchemeName);
            tvSchemeROI = (TextView) itemView.findViewById(R.id.tvSchemeROI);
            tvSchemeAmount = (TextView) itemView.findViewById(R.id.tvSchemeAmount);
            tvSchemePeriod = (TextView) itemView.findViewById(R.id.tvSchemePeriod);
            tvSchemeAdvance = (TextView) itemView.findViewById(R.id.tvSchemeAdvance);
            tvSchemeState = (TextView) itemView.findViewById(R.id.tvSchemeState);
            cbScheme = itemView.findViewById(R.id.cbScheme);

          //  viewDivider =  itemView.findViewById(R.id.viewDivider);



        }


    }
    public List<SchemeResponse> getList(){
        return homeList;
    }

}

