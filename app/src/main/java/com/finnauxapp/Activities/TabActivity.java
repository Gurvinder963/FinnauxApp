package com.finnauxapp.Activities;

import android.annotation.SuppressLint;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.finnauxapp.Interfaces.InterfaceInquirySave;
import com.finnauxapp.R;
import com.finnauxapp.Util.SessionManager;
import com.finnauxapp.webservice.Api;
import com.finnauxapp.webservice.ApiClient;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class TabActivity extends android.app.TabActivity {

    private TabHost tabHost;
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String processIds=session.getProcessIds();
        if(processIds.contains("3")){
            llHoldInquires.setVisibility(View.VISIBLE);
            llCompeledApp.setVisibility(View.VISIBLE);
        }
        if(processIds.contains("6")){
            llCompeledFIApp.setVisibility(View.VISIBLE);
        }

        }
    };
    private SessionManager session;
    private LinearLayout llHoldInquires;
    private LinearLayout llCompeledApp;
    private LinearLayout llCompeledFIApp;

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("process_event"));
         session = new SessionManager(this);
         tabHost = (TabHost) findViewById(android.R.id.tabhost); // initiate TabHost
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        TextView tvTitle =header.findViewById(R.id.tvTitle);
        tvTitle.setText(session.getName());
       TextView tvEmail= header.findViewById(R.id.tvEmail);
       tvEmail.setText(session.getEmail());
       TextView tvRole= header.findViewById(R.id.tvRole);
        tvRole.setText(session.getRole());
        final ImageView ivImage= findViewById(R.id.ivImage);
        Glide.with(this)
                .load(session.getClientLogo())
                .apply(RequestOptions.placeholderOf(R.drawable.user_icon).error(R.drawable.user_icon))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ivImage.setImageResource(R.drawable.user_icon);
                            }
                        }, 700);


                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                        return false;
                    }
                })
                .into(ivImage);

        TextView tvLogout= header.findViewById(R.id.tvLogout);
        llHoldInquires= header.findViewById(R.id.llHoldInquires);
         llCompeledApp= header.findViewById(R.id.llCompeledApp);
         llCompeledFIApp= header.findViewById(R.id.llCompeledFIApp);
        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPrefs = TabActivity.this.getSharedPreferences(SessionManager.PREF_NAME,0);
                SharedPreferences.Editor editor = sharedPrefs.edit();
              //  editor.clear();

                editor.remove(SessionManager.KEY_USER_ID).commit();
                editor.remove(SessionManager.KEY_TOKEN).commit();
                editor.remove(SessionManager.KEY_USERNAME).commit();
                editor.remove(SessionManager.KEY_EMAIL).commit();
                editor.remove(SessionManager.KEY_ROLE).commit();
                editor.remove(SessionManager.KEY_IMAGE).commit();
                editor.remove(SessionManager.KEY_PROCESS_IDS).commit();

                ApiClient.retrofit=null;
                Intent i = new Intent(TabActivity.this, SplashActivity.class);
// set the new task and clear flags
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });


        TextView tvHoldInquiries= header.findViewById(R.id.tvHoldInquiries);
        tvHoldInquiries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                Intent i = new Intent(TabActivity.this, LeadActivity.class);
                i.putExtra("TAG","Hold Inquiry");
                startActivity(i);
            }
        });



        TextView tvCompletedApp= header.findViewById(R.id.tvCompletedApp);
        tvCompletedApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                Intent i = new Intent(TabActivity.this, CompletedApplicationActivity.class);
                i.putExtra("TAG","Completed Applications");
                startActivity(i);
            }
        });

        TextView tvCompletedFIApp= header.findViewById(R.id.tvCompletedFIApp);
        tvCompletedFIApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                Intent i = new Intent(TabActivity.this, CompletedApplicationActivity.class);
                i.putExtra("TAG","Completed FI Applications");
                startActivity(i);
            }
        });

        final CircleImageView ivProfile= header.findViewById(R.id.ivProfile);
        tvRole.setText(session.getRole());
        String profilePic=session.getImage();
        if(profilePic!=null && !profilePic.isEmpty()) {
              String url = session.getClientUrl() + "uploadDoc/wwwroot/Document/Employee/ProfilePic/" + profilePic;

          //  String url =    Api.BASE_URL + "uploadDoc/wwwroot/Document/Customer/" + CustomerId + "/" + profilePic;
            Glide.with(TabActivity.this)
                    .load(url)
                    .apply(RequestOptions.placeholderOf(R.drawable.user_icon).error(R.drawable.user_icon))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    ivProfile.setImageResource(R.drawable.user_icon);
                                }
                            }, 700);


                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {

                            return false;
                        }
                    })
                    .into(ivProfile);
        }

        ImageView ivMenu = findViewById(R.id.ivMenu);
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.START);
            }
        });
      /*  LocalActivityManager mLocalActivityManager = new LocalActivityManager(this, false);
        tabHost.setup(mLocalActivityManager);*/
        TabHost.TabSpec spec; // Reusable TabSpec for each tab
        Intent intent; // Reusable Intent for each tab

        spec = tabHost.newTabSpec("home"); // Create a new TabSpec using tab host
        spec.setIndicator("", getResources().getDrawable(R.drawable.tab_selector)); // set the “HOME” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, HomeActivity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);
        //getTabWidget().getChildAt(0).setBackground(null);
        // Do the same for the other tabs

        spec = tabHost.newTabSpec("Contact"); // Create a new TabSpec using tab host
        spec.setIndicator("", getResources().getDrawable(R.drawable.tab_selector_inquiry)); // set the “CONTACT” as an indicator
        // Create an Intent to launch an Activity for the tab (to be reused)
        intent = new Intent(this, RegisterNewInquiryActivity.class);
        spec.setContent(intent);
        tabHost.addTab(spec);
       // getTabWidget().getChildAt(1).setBackground(null);

        //set tab which one you want to open first time 0 or 1 or 2
        tabHost.setCurrentTab(0);
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                // display the name of the tab whenever a tab is changed
            //    Toast.makeText(getApplicationContext(), tabId, Toast.LENGTH_SHORT).show();
            }
        });


    }



    public void onTabChange() {
        tabHost.setCurrentTab(0);
    }
}
