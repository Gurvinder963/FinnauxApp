package com.finnauxapp.Adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.finnauxapp.Activities.CustomerDetailActivity;
import com.finnauxapp.Activities.CustomerRegistrationActivity;
import com.finnauxapp.Activities.LeadDetailActivity;
import com.finnauxapp.Activities.MyApplicationDetailActivity;
import com.finnauxapp.ApiResponse.ApplicationItem2;
import com.finnauxapp.ApiResponse.ApplicationListResponse;
import com.finnauxapp.ApiResponse.DashboardDataResponse;
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

public class MyApplicationDetailAdapter extends RecyclerView.Adapter {
    private final String TAG;
    private List<ApplicationItem2> homeList;
    private Context context;
    int appId;
    String appNo;double loanAmount;
    String product;

  //  ApplicationListResponse applicationListResponse;
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();

    public MyApplicationDetailAdapter(Context context, List<ApplicationItem2> list, int appId,String appNo,double loanAmount,String product,String TAG) {
        this.context = context;
        this.TAG=TAG;
        this.homeList = list;
        this.appId=appId;
        this.appNo=appNo;
        this.loanAmount=loanAmount;
        this.product=product;
        //this.applicationListResponse=applicationListResponse;
        binderHelper.setOpenOnlyOne(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_application_detail, parent, false);
        return new HomeHolder(v);


    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {


        final HomeHolder holder = (HomeHolder) viewHolder;
        final ApplicationItem2 response=homeList.get(position);

        binderHelper.bind(holder.swipeLayout, String.valueOf(response.getCustomerId()));
        holder.tvName.setText(response.getCustomer());
        holder.tvType.setText(response.getCustomerType());
        holder.tvDelete.setTag(position);
        holder.frontLayout.setTag(position);

        if(!response.getCustomerType().equalsIgnoreCase("Hirer") && response.getCustomerRelation()!=null){
            holder.tvReleation.setVisibility(View.VISIBLE);
            holder.tvReleation.setText("Relation with Hirer : " +response.getCustomerRelation());
        }
        else{
            holder.tvReleation.setVisibility(View.GONE);
        }


        if(TAG.equalsIgnoreCase("My FI Applications") || TAG.equalsIgnoreCase("Completed Applications") || TAG.equalsIgnoreCase("Completed FI Applications")){
            holder.tvDelete.setVisibility(View.GONE);
        }

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want to delete this customer?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                holder.swipeLayout.close(true);
                                MyApplicationDetailActivity activity=(MyApplicationDetailActivity)context;
                                activity.DeleteCustomerApi(response.getCustomerId());

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
        holder.frontLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, CustomerDetailActivity.class);
                intent.putExtra("applicationId",appId);
                intent.putExtra("appNo",appNo);
                intent.putExtra("product",product);
                intent.putExtra("loanAmount",loanAmount+"");
                intent.putExtra("CustomerId",response.getCustomerId());
                intent.putExtra("profilePic",response.getProfilePic());
                intent.putExtra("Is_FI_Allow",response.isIs_FI_Allow());
                intent.putExtra("TAG",TAG);
                context.startActivity(intent);

            }
        });

        if(position % 2==0){
            holder.viewDivider.setBackgroundColor(context.getResources().getColor(R.color.color_orange));
        }
        else{
            holder.viewDivider.setBackgroundColor(context.getResources().getColor(R.color.color_blue));
        }

        String profilePic = response.getProfilePic();
        if(profilePic!=null) {
          //  String url = Api.BASE_URL + "uploadDoc/wwwroot/Document/Employee/ProfilePic/" + profilePic;
            SessionManager session = new SessionManager(context);
            String url =   session.getClientUrl() + "uploadDoc/wwwroot/Document/Customer/" + response.getCustomerId() + "/" + profilePic;
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

        //   holder.tvCount.setText(response.getValue()+"");



    }


    @Override
    public int getItemCount() {
        return homeList.size();
    }


    public class HomeHolder extends RecyclerView.ViewHolder {
        private SwipeRevealLayout swipeLayout;
        public TextView tvName;
        public TextView tvReleation;
        public TextView tvType;
        public TextView tvDelete;
        public ImageView ivProfile;
        public View viewDivider;
        public LinearLayout frontLayout;


        public HomeHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvReleation = (TextView) itemView.findViewById(R.id.tvReleation);
            tvType = (TextView) itemView.findViewById(R.id.tvType);
            ivProfile =  itemView.findViewById(R.id.ivProfile);
            viewDivider =  itemView.findViewById(R.id.viewDivider);
            swipeLayout = (SwipeRevealLayout) itemView.findViewById(R.id.swipe_layout);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            frontLayout = itemView.findViewById(R.id.frontLayout);


        }


    }

}
