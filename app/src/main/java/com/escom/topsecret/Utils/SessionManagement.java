package com.escom.topsecret.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SessionManagement, stores the session data in SharedPreferences.
 */

public class SessionManagement {
    private static final String NAME = "SESSION";
    private static final String USER= "USER";

    private final SharedPreferences sharedPreferences;

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public void setUserData(String userData){
        sharedPreferences.edit().putString(USER, userData).apply();
    }

    public String getUserData(){
        return sharedPreferences.getString(USER, "");
    }
}
