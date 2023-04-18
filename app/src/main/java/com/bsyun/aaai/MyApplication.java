package com.bsyun.aaai;

import android.app.Application;

import com.bsyun.aaai.utils.AppUtils;
import com.bsyun.aaai.utils.UiUtils;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
        UiUtils.init(this);
    }

}

