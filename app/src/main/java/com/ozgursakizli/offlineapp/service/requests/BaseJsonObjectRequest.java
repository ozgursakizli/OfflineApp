package com.ozgursakizli.offlineapp.service.requests;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ozgursakizli.offlineapp.service.base.ServiceManager;
import com.ozgursakizli.offlineapp.service.enums.RequestSuffix;
import com.ozgursakizli.offlineapp.service.interfaces.IServiceObjectResponse;

import org.json.JSONObject;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class BaseJsonObjectRequest {

    private String url;
    private IServiceObjectResponse serviceListener;

    private BaseJsonObjectRequest(BaseJsonObjectRequest.Builder builder) {
        this.url = builder.url;
        this.serviceListener = builder.serviceListener;
        doRequest();
    }

    private void doRequest() {
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, this.url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                serviceListener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    if (error.networkResponse != null) {
                        String responseBody = new String(error.networkResponse.data, "UTF-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        serviceListener.onError(jsonObject.get("error").toString());
                    } else {
                        serviceListener.onError("Failed");
                    }
                } catch (Exception ex) {
                    serviceListener.onError("Failed");
                    ex.printStackTrace();
                }
            }
        });

        objectRequest.setRetryPolicy(new DefaultRetryPolicy(1000 * 10, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ServiceManager.singleton().addToRequestQueue(objectRequest);
    }

    public static class Builder {

        private String url;
        private IServiceObjectResponse serviceListener;

        public Builder() {

        }

        /**
         * @param requestSuffix request suffix
         * @param keyword       keyword
         * @return url
         */
        public BaseJsonObjectRequest.Builder url(RequestSuffix requestSuffix, String keyword) {
            this.url = ServiceManager.singleton().getCategoryRequestUrl(requestSuffix, keyword);
            return this;
        }

        /**
         * @param serviceListener callback
         * @return serviceListener
         */
        public BaseJsonObjectRequest.Builder serviceListener(IServiceObjectResponse serviceListener) {
            this.serviceListener = serviceListener;
            return this;
        }

        public BaseJsonObjectRequest build() {
            return new BaseJsonObjectRequest(this);
        }

    }

}
