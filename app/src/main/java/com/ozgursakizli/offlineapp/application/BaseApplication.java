package com.ozgursakizli.offlineapp.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.ozgursakizli.offlineapp.service.base.ServiceManager;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class BaseApplication extends Application {

    private static BaseApplication singleton;

    public BaseApplication() {
    }

    public static BaseApplication getSingleton() {
        if (singleton == null) {
            singleton = new BaseApplication();
        }
        return singleton;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        ServiceManager.with(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
