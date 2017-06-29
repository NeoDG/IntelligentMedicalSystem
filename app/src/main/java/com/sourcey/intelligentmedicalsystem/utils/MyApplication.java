package com.sourcey.intelligentmedicalsystem.utils;

import android.app.Application;
import android.content.Context;

/**
 * Created by 七忋 on 2017/5/17.
 */

public class MyApplication extends Application {
    private static int userId;

    private static Boolean loginFlag=false;

    private static Context context;
    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
    }
    public static Context getContext(){
        return context;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        MyApplication.userId = userId;
    }

    public static Boolean getLoginFlag() {
        return loginFlag;
    }

    public static void setLoginFlag(Boolean loginFlag) {
        MyApplication.loginFlag = loginFlag;
    }
}
