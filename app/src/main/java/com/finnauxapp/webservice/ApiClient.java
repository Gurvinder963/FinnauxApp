package com.finnauxapp.webservice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.finnauxapp.Activities.LoginActivity;
import com.finnauxapp.Activities.TabActivity;
import com.finnauxapp.Util.SessionManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit retrofit = null;
    public static SessionManager session;

    public static Retrofit getClient(final Context applicationContext) {
        session=new SessionManager(applicationContext);
        if (retrofit==null && session.getClientUrl()==null) {
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging)
                    .connectTimeout(300, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS).build();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer "+session.getToken()).build();
                    Response response =  chain.proceed(request);
                    if (response.code() == 401) {

                        new Handler(Looper.getMainLooper()).post(new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                Toast.makeText(applicationContext, "Session has been expired , need to login again!!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        SharedPreferences sharedPrefs = applicationContext.getSharedPreferences(SessionManager.PREF_NAME,0);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.clear();
                        editor.commit();

                        Intent i = new Intent(applicationContext, LoginActivity.class);
// set the new task and clear flags
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        applicationContext.startActivity(i);

                    }

                        return response;

                }
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(Api.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();

        }
        else if(retrofit==null && session.getClientUrl()!=null){
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(logging)
                    .connectTimeout(300, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS).build();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer "+session.getToken()).build();
                    Response response =  chain.proceed(request);
                    if (response.code() == 401) {

                        new Handler(Looper.getMainLooper()).post(new Runnable()
                        {

                            @Override
                            public void run()
                            {
                                Toast.makeText(applicationContext, "Session has been expired , need to login again!!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        SharedPreferences sharedPrefs = applicationContext.getSharedPreferences(SessionManager.PREF_NAME,0);
                        SharedPreferences.Editor editor = sharedPrefs.edit();
                        editor.clear();
                        editor.commit();

                        Intent i = new Intent(applicationContext, LoginActivity.class);
// set the new task and clear flags
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        applicationContext.startActivity(i);

                    }

                    return response;

                }
            });

            retrofit = new Retrofit.Builder()
                    .baseUrl(session.getClientUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;
    }
}
