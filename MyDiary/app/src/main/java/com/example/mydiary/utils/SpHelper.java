package com.example.mydiary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Yuzhe You on 2021/10/25.
 * No.1159774
 */

public class SpHelper {

    private static final String SP_NAME = "sp_name";
    private static SpHelper mSpHelper;
    private Context mAppContext;
    private SharedPreferences mSharedPreferences;
    private String info;

    private SpHelper(Context context){
        mAppContext = context.getApplicationContext();
    }

    //Get an instance of sphelper
    public static SpHelper getInstance(Context context){
        if(mSpHelper == null){
            synchronized (SpHelper.class){
                if(mSpHelper == null){
                    mSpHelper = new SpHelper(context);
                }
            }
        }
        return mSpHelper;
    }

    @SuppressLint("WrongConstant")
    private SharedPreferences getSharePreferences(){
        if(mSharedPreferences == null){
            mSharedPreferences = mAppContext.getSharedPreferences(SP_NAME, Context.MODE_APPEND);
        }
        return mSharedPreferences;
    }

    public void setInfo(String info){
        this.info = info;
        getSharePreferences().edit().putString("info", info).apply();
    }

    public String getInfo(){
        if(info.equals("") || info.length() == 0){
            info = getSharePreferences().getString("info", "");
        }
        return info;
    }
}

