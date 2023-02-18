package com.sahilssoft.p1_sportsocial.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefClass {
    Context context;
    static SharedPrefClass _this;
    static SharedPreferences sharedPreferences;
    static SharedPreferences.Editor editor;

    public SharedPrefClass(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("Data",Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPrefClass getInstance(Context context){
        if (_this == null){
            _this = new SharedPrefClass(context);
        }
        return _this;
    }

    public void SaveData(String key, String jsonStr){
        editor.putString(key,jsonStr);
        editor.apply();
    }

    public String getData(String key){
        return sharedPreferences.getString(key,"");
    }

    public void clearPref(){
        editor.clear();
        editor.commit();
    }

}
