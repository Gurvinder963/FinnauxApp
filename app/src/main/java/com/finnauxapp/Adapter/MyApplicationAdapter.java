package com.finnauxapp.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.finnauxapp.Activities.CustomerExistingLoanActivity;
import com.finnauxapp.Activities.LeadDetailActivity;
import com.finnauxapp.Activities.MyApplicationDetailActivity;
import com.finnauxapp.Activities.RequiredDocListActivity;
import com.finnauxapp.Activities.SendBackBranchApplicationActivity;
import com.finnauxapp.ApiResponse.ApplicationListResponse;
import com.finnauxapp.ApiResponse.DashboardDataResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.Util.SpinnerModel;
import com.finnauxapp.Util.WebUtility;
import com.finnauxapp.webservice.Api;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class MyApplicationAdapter extends RecyclerView.Adapter {
    private List<ApplicationListResponse> homeList;
    private Context context;
    private String TAG;




    public MyApplicationAdapter(Context context, List<ApplicationListResponse> list,String TAG) {
        this.context = context;

        this.homeList = list;
        this.TAG = TAG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_my_app_item, parent, false);
        return new HomeHolder(v);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        final HomeHolder holder = (HomeHolder) viewHolder;
        ApplicationListResponse response=homeList.get(position);

        if(response.getCustomer()!=null && !response.getCustomer().isEmpty()){
            holder.tvName.setText(response.getCustomer());
        }
        else{
            holder.tvName.setText("N/A");
        }

        holder.tvInquiryBranch.setText(response.getApplication_No());
        holder.tvMobile.setText(response.getBranch());
        holder.tvDescription.setText(response.getProduct());
        holder.tvAmount.setText(response.getLoanAmount()+" , ROI : "+response.getIR()+"% , EMI : "+response.getEMI());


        try {
            String[] dateArray = response.getCreateOn().split("\\s+");

            holder.tvDate.setText(dateArray[0]+" "+dateArray[1]+" "+dateArray[2]);
            holder.tvTime.setText(dateArray[dateArray.length-1]+"");

        }
        catch (Exception e){
            e.printStackTrace();
        }


     //   holder.tvInquiryBranch.setVisibility(View.GONE);

        //   holder.tvCount.setText(response.getValue()+"");


        if(position % 2==0){
           holder.viewDivider.setBackgroundColor(context.getResources().getColor(R.color.color_orange));
        }
        else{
            holder.viewDivider.setBackgroundColor(context.getResources().getColor(R.color.color_blue));
        }
       String profilePic = response.getProfilePic();
        if(profilePic!=null) {
           // String url = Api.BASE_URL + "uploadDoc/wwwroot/Document/Employee/ProfilePic/" + profilePic;
            SessionManager session = new SessionManager(context);
         String url =   session+ "uploadDoc/wwwroot/Document/Customer/" + response.getCustomerId() + "/" + profilePic;

            Glide.with(context)
                    .load(url)
                    .apply(RequestOptions.placeholderOf(R.drawable.user_icon).error(R.drawable.user_icon))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    holder.ivProfile.setImageResource(R.drawable.user_icon);
                                }
                            }, 700);


                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            return false;
                        }
                    })
                    .into(holder.ivProfile);
        }


        if(TAG.equalsIgnoreCase("Send Back Branch Applications")){
            holder.tvReasonForSendBack.setVisibility(View.VISIBLE);
        }
        holder.tvReasonForSendBack.setTag(position);
        holder.tvReasonForSendBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos=(int)v.getTag();

                AlertDialog.Builder dialogBuilder;
                final AlertDialog alertDialog;

                dialogBuilder  = new AlertDialog.Builder(context);
                SendBackBranchApplicationActivity activity =(SendBackBranchApplicationActivity) context;
                final View dialog = activity.getLayoutInflater().inflate(R.layout.dialog_sendback_reason, null);

                dialogBuilder.setView(dialog);
                alertDialog = dialogBuilder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                final TextView tvCreditUser = dialog.findViewById(R.id.tvCreditUser);
                final TextView tvRemark = dialog.findViewById(R.id.tvRemark);
                tvCreditUser.setText(homeList.get(pos).getCreditUser());
                tvRemark.setText(homeList.get(pos).getCreditUserRemark());

                alertDialog.show();
            }
        });

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
        //public ImageView ivProfile;
        public TextView tvInquiryBranch;
        public TextView tvAmount;
        public TextView tvReasonForSendBack;
        public View viewDivider;
        public ImageView ivProfile;


        public HomeHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvMobile = (TextView) itemView.findViewById(R.id.tvMobile);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
           // ivProfile =  itemView.findViewById(R.id.ivProfile);
            tvInquiryBranch =  itemView.findViewById(R.id.tvInquiryBranch);
            tvAmount =  itemView.findViewById(R.id.tvAmount);
            tvReasonForSendBack =  itemView.findViewById(R.id.tvReasonForSendBack);
            ivProfile =  itemView.findViewById(R.id.ivProfile);

            viewDivider =  itemView.findViewById(R.id.viewDivider);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(TAG.equalsIgnoreCase("Pending DOC")){
                        Intent intent = new Intent(context, RequiredDocListActivity.class);
                        intent.putExtra("applicationId", homeList.get(getAdapterPosition()).getApplicationId());
                        intent.putExtra("appNo", homeList.get(getAdapterPosition()).getApplication_No());
                        intent.putExtra("TAG", TAG);
                        context.startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(context, MyApplicationDetailActivity.class);
                       // intent.putExtra("data", homeList.get(getAdapterPosition()));
                        intent.putExtra("applicationId", homeList.get(getAdapterPosition()).getApplicationId());
                        intent.putExtra("appNo", homeList.get(getAdapterPosition()).getApplication_No());
                        intent.putExtra("from","list");
                        intent.putExtra("TAG", TAG);
                        context.startActivity(intent);
                    }
                }
            });

        }


    }

}
