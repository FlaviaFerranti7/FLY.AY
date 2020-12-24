package com.flam.flyay.util;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.flam.flyay.MainActivity;
import com.flam.flyay.model.StatusResponse;
import com.flam.flyay.services.ServerCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Utils {


    public static void getStatusResponse(JSONObject requestResult, final ServerCallback callback) {
        JSONObject containerResponse = new JSONObject();
        String status = "";

        StatusResponse response = new StatusResponse();

        try {
            containerResponse = requestResult.getJSONObject("return");
            status = containerResponse.getString("status");

            response.setStatus(status);
            if(status.equalsIgnoreCase("KO")) {
                response.setMessage(containerResponse.getString("message"));
            }


            Log.d("response: ", status);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        callback.onSuccess(response);
    }
}
