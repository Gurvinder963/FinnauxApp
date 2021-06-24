package com.finnauxapp.Util;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    public static final String KEY_USER_ID = "userid";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_USERNAME = "name";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ROLE = "role";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_PROCESS_IDS = "process_ids";

    public static final String KEY_CLIENT_URL = "client_url";
    public static final String KEY_CLIENT_CODE = "client_code";
    public static final String KEY_CLIENT_NAME = "client_name";
    public static final String KEY_CLIENT_LOGO = "client_logo";

    SharedPreferences.Editor editor;
    SharedPreferences pref;
    int PRIVATE_MODE = 0;
    Context _context;
  public static final String PREF_NAME = "FinnauxPref";
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);

        editor = pref.edit();

    }
    public void saveuserId(int id) {
        // Storing name in pref
        editor.putInt(KEY_USER_ID, id);
        // commit changes
        editor.commit();

    }
    public int getUserId() {
        return pref.getInt(KEY_USER_ID, 0);
    }

    public void saveToken(String id) {
        // Storing name in pref
        editor.putString(KEY_TOKEN, id);
        // commit changes
        editor.commit();

    }

    public void saveName(String id) {
        // Storing name in pref
        editor.putString(KEY_USERNAME, id);
        // commit changes
        editor.commit();

    }
    public void saveEmail(String id) {
        // Storing name in pref
        editor.putString(KEY_EMAIL, id);
        // commit changes
        editor.commit();

    }
    public void saveRole(String id) {
        // Storing name in pref
        editor.putString(KEY_ROLE, id);
        // commit changes
        editor.commit();

    }
    public void saveImage(String id) {
        // Storing name in pref
        editor.putString(KEY_IMAGE, id);
        // commit changes
        editor.commit();

    }
    public void saveProcessIds(String id) {
        // Storing name in pref
        editor.putString(KEY_PROCESS_IDS, id);
        // commit changes
        editor.commit();

    }
    public void saveClientInfo(String url,String code,String name,String image) {
        // Storing name in pref
        editor.putString(KEY_CLIENT_URL, url);
        editor.putString(KEY_CLIENT_CODE, code);
        editor.putString(KEY_CLIENT_NAME, name);
        editor.putString(KEY_CLIENT_LOGO, image);
        // commit changes
        editor.commit();

    }

    public void saveClientURL(String url) {
        // Storing name in pref
        editor.putString(KEY_CLIENT_URL, url);

        // commit changes
        editor.commit();

    }



    public String getClientUrl()
    {
        return pref.getString(KEY_CLIENT_URL,null);
    }
    public String getClientCode()
    {
        return pref.getString(KEY_CLIENT_CODE,"");
    }
    public String getClientName()
    {
        return pref.getString(KEY_CLIENT_NAME,"");
    }
    public String getClientLogo()
    {
        return pref.getString(KEY_CLIENT_LOGO,"");
    }

    public String getToken()
    {
        return pref.getString(KEY_TOKEN,"");
    }
    public String getName()
    {
        return pref.getString(KEY_USERNAME,"");
    }
    public String getEmail()
    {
        return pref.getString(KEY_EMAIL,"");
    }
    public String getRole()
    {
        return pref.getString(KEY_ROLE,"");
    }
    public String getImage()
    {
        return pref.getString(KEY_IMAGE,"");
    }
    public String getProcessIds()
    {
        return pref.getString(KEY_PROCESS_IDS,"");
    }
}
