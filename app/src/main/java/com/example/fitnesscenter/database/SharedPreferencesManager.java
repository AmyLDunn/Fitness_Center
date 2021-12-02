package com.example.fitnesscenter.database;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private SharedPreferences SP;

    public SharedPreferencesManager(Context context){
        SP = context.getApplicationContext().getSharedPreferences("Preferences", 0);
    }

    public void setUserType(int type){
        SP.edit().putInt("ACCOUNT_TYPE", type).commit();
    }

    public int getUserType(){
        return SP.getInt("ACCOUNT_TYPE", 0);
    }

    public void setUsername(String name){
        SP.edit().putString("ACCOUNT_NAME", name).commit();
    }

    public String getUsername(){
        return SP.getString("ACCOUNT_NAME", "Bob");
    }

}
