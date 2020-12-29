package com.flam.flyay.util;

import android.util.Log;

import com.flam.flyay.model.Event;
import com.flam.flyay.model.StatusResponse;
import com.flam.flyay.services.ServerCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {

    public static String getTimeToString(Event e) {
        if(e.getStartingTime() != null && e.getEndTime() != null) {
            return getTimeToString(e.getStartingTime(), e.getEndTime());
        }

        return "all day";
    }

    public static String getTimeToString(double startingTimeInput, double endTimeInput) {
        String startingTime = Double.toString(startingTimeInput);
        String endTime = Double.toString(endTimeInput);
        String startingTimeInt = startingTime.substring(0,2);
        String startingTimeDecimal = startingTime.substring(3);

        if(startingTimeDecimal.length() == 1)
            startingTimeDecimal += "0";

        String endTimeInt = endTime.substring(0,2);
        String endTimeDecimal = endTime.substring(3);
        if(endTimeDecimal.length() == 1)
            endTimeDecimal += "0";

        return startingTimeInt + ":" + startingTimeDecimal + " - " + endTimeInt + ":" + endTimeDecimal;
    }

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
