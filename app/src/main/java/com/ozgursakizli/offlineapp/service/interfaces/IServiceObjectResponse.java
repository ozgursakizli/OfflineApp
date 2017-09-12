package com.ozgursakizli.offlineapp.service.interfaces;

import org.json.JSONObject;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public interface IServiceObjectResponse {

    void onSuccess(JSONObject jsonObject);

    void onError(String error);

}
