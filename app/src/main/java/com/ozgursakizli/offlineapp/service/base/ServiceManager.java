package com.ozgursakizli.offlineapp.service.base;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.Volley;
import com.ozgursakizli.offlineapp.components.ActivityManager;
import com.ozgursakizli.offlineapp.BuildConfig;
import com.ozgursakizli.offlineapp.R;
import com.ozgursakizli.offlineapp.service.enums.RequestSuffix;
import com.ozgursakizli.offlineapp.utils.Util;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class ServiceManager {

    private static ServiceManager instance;
    private Context context;
    private RequestQueue requestQueue;

    private ServiceManager(Context context) {
        this.context = context;
    }

    /**
     * @param context application context
     * @return api
     */
    public static ServiceManager with(Context context) {
        if (instance == null) {
            synchronized (ServiceManager.class) {
                if (instance == null) {
                    instance = new ServiceManager(context);
                }
            }
        }
        return instance;
    }

    public static ServiceManager singleton() {
        if (instance == null) {
            throw new IllegalStateException("Must initialize ServiceManager before using singleton");
        } else {
            return instance;
        }
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        if (BuildConfig.DEBUG) {
            VolleyLog.d("Adding request to queue: %s", request.getUrl());
        }

        if (Util.isNetworkActive()) {
            getRequestQueue().add(request);
        } else {
            ActivityManager.getInstance().getCurrentActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, context.getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public String getCategoryRequestUrl(RequestSuffix requestSuffix, String keyword) {
        String url = ServiceConstants.baseUrl;

        switch (requestSuffix) {
            case PRODUCT_LIST:
                url += ServiceConstants.productList;
                break;
            case PRODUCT_DETAIL:
                url += keyword + ServiceConstants.productDetail;
                break;
        }

        return url;
    }

}

