package com.bsyun.aaai.utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;


public class AppUtils {

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    private AppUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void init(@NonNull final Context context) {
        AppUtils.sApplication = (Application) context.getApplicationContext();
    }

    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }

}
