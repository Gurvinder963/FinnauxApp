package com.finnauxapp.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.finnauxapp.Activities.CustomerExistingLoanActivity;
import com.finnauxapp.Activities.CustomerKYCDocListActivity;
import com.finnauxapp.Activities.CustomerRegistrationActivity;
import com.finnauxapp.Activities.LeadDetailActivity;
import com.finnauxapp.Activities.MyApplicationDetailActivity;
import com.finnauxapp.ApiResponse.ApplicationListResponse;
import com.finnauxapp.ApiResponse.DashboardDataResponse;
import com.finnauxapp.ApiResponse.KYCDocListResponse;
import com.finnauxapp.ApiResponse.LeadResponse;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.webservice.Api;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class CustomerKYCDocAdapter extends RecyclerView.Adapter {
    private List<KYCDocListResponse.Item2Bean> homeList;
    private Context context;
    int cID;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();


    public CustomerKYCDocAdapter(Context context, List<KYCDocListResponse.Item2Bean> list, int cID) {
        this.context = context;
        this.cID = cID;
        this.homeList = list;
        binderHelper.setOpenOnlyOne(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_kyc_doc_list, parent, false);


        return new HomeHolder(v);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        final HomeHolder holder = (HomeHolder) viewHolder;
        final KYCDocListResponse.Item2Bean response = homeList.get(position);
        binderHelper.bind(holder.swipeLayout, String.valueOf(response.getKYC_DocId()));
        holder.tvDelete.setTag(position);
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pos = (int) v.getTag();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this KYC?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                holder.swipeLayout.close(true);
                                CustomerKYCDocListActivity activity = (CustomerKYCDocListActivity) context;
                                activity.DeleteKYCApi(homeList.get(pos).getKYC_DocId());

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                                holder.swipeLayout.close(true);
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        String docfile = response.getKYC_DocFile();
      //  String[] arr = docfile.split("_");

        holder.tvName.setText(docfile);

        holder.tvNo.setText(response.getKYC_DocNumber());
        //  holder.tvDescription.setText(response.getProduct()+" , "+response.getLoanAmount());
        holder.ivEdit.setTag(position);
        holder.frontLayout.setTag(position);

       if(response.getKYC_DocId()==6){
          holder.ivProfile.setBackgroundResource(R.drawable.ic_aadhar_card);
       }
       else  if(response.getKYC_DocId()==1){
           holder.ivProfile.setBackgroundResource(R.drawable.ic_pan_card);
       }
       else  if(response.getKYC_DocId()==4){
           holder.ivProfile.setBackgroundResource(R.drawable.ic_voter_id);
       }
       else  if(response.getKYC_DocId()==5){
           holder.ivProfile.setBackgroundResource(R.drawable.ic_dl);
       }
       else  if(response.getKYC_DocId()==3){
           holder.ivProfile.setBackgroundResource(R.drawable.ic_pp);
       }
       else  if(response.getKYC_DocId()==2){
           holder.ivProfile.setBackgroundResource(R.drawable.ic_e_bill);
       }
       else  if(response.getKYC_DocId()==21){
           holder.ivProfile.setBackgroundResource(R.drawable.ic_rashan);
       }
       else  if(response.getKYC_DocId()==7){
           holder.ivProfile.setBackgroundResource(R.drawable.ic_other);
       }
       else {
           holder.ivProfile.setBackgroundResource(R.drawable.ic_other);
       }
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                CustomerKYCDocListActivity activity = ((CustomerKYCDocListActivity) context);
                activity.addEditDocument(homeList.get(pos));
            }
        });
        holder.frontLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                AlertDialog.Builder dialogBuilder;
                final AlertDialog alertDialog;

                dialogBuilder = new AlertDialog.Builder(context);
                final View dialog = LayoutInflater.from(context).inflate(R.layout.dialog_view_kyc_doc, null);

                dialogBuilder.setView(dialog);
                alertDialog = dialogBuilder.create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                final ImageView ivPhoto = dialog.findViewById(R.id.ivPhoto);
                final ImageView ivClose = dialog.findViewById(R.id.ivClose);
                final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress);
                ivClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                String fileName = homeList.get(pos).getKYC_DocFile();

                if (fileName != null) {
                    SessionManager session = new SessionManager(context);
                    String url = session.getClientUrl() + "uploadDoc/wwwroot/Document/Customer/" + cID + "/" + fileName;


                    Glide.with(context)
                            .load(url)

                            .listener(new RequestListener<Drawable>() {
                                @Override
                                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBar.setVisibility(View.GONE);
                                            ivPhoto.setImageResource(R.drawable.user_icon);
                                        }
                                    }, 700);


                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                            .into(ivPhoto);
                }
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
        public TextView tvNo;
        public TextView tvDelete;

        public ImageView ivProfile;
        public ImageView ivEdit;

        public View viewDivider;
        SwipeRevealLayout swipeLayout;
        RelativeLayout frontLayout;


        public HomeHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvNo = (TextView) itemView.findViewById(R.id.tvNo);

            ivProfile = itemView.findViewById(R.id.ivProfile);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            viewDivider = itemView.findViewById(R.id.viewDivider);
            frontLayout = itemView.findViewById(R.id.frontLayout);



        }


    }

}
