package com.ozgursakizli.offlineapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ozgursakizli.offlineapp.application.BaseApplication;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class Util {

    public static boolean isNetworkActive() {
        ConnectivityManager connectivityManager = (ConnectivityManager) BaseApplication.getSingleton().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}
