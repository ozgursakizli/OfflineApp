package com.ozgursakizli.offlineapp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.ozgursakizli.offlineapp.components.ActivityManager;
import com.ozgursakizli.offlineapp.components.ConnectivityController;
import com.ozgursakizli.offlineapp.interfaces.OnConnectivityChangedListener;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class BaseAppCompatActivity extends AppCompatActivity implements OnConnectivityChangedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().setCurrentActivity(this);
        new ConnectivityController().setOnConnectivityChangedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityManager.getInstance().setCurrentActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivityManager.getInstance().removeFromActivities(this);
        super.onDestroy();
    }

    public String getStringResource(int resource) {
        return getResources().getString(resource);
    }

    public void showToast(int resource) {
        Toast.makeText(this, getStringResource(resource), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnected() {
        Log.i("InternetStatus", "İnternet erişimi sağlandı");
    }

}
