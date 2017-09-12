package com.ozgursakizli.offlineapp.components;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ozgursakizli.offlineapp.interfaces.OnConnectivityChangedListener;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class ConnectivityController extends BroadcastReceiver {

    private Context context;
    private static OnConnectivityChangedListener onConnectivityChangedListener;

    public void setOnConnectivityChangedListener(OnConnectivityChangedListener listener) {
        onConnectivityChangedListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        if (intent != null) {
            switch (intent.getAction()) {
                case "android.net.wifi.WIFI_STATE_CHANGED":
                    actionWifiStateChanged();
                    break;
            }
        }
    }

    protected void actionWifiStateChanged() {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnected();

            if (onConnectivityChangedListener != null && isConnected) {
                onConnectivityChangedListener.onConnected();
            }
        }
    }

}