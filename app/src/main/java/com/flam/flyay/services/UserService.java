package com.flam.flyay.services;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flam.flyay.MainActivity;
import com.flam.flyay.util.MockServerUrl;

import org.json.JSONException;
import org.json.JSONObject;

public class UserService {
    private Activity activity;

    public UserService(Activity activity) {
        this.activity = activity;
    }

    public void signin(String url, JSONObject params) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject containerResponse = new JSONObject();
                        String status = "";
                        try {
                            containerResponse = response.getJSONObject("return");
                            status = containerResponse.getString("status");

                            Log.d("response: ", status);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        if(status.equalsIgnoreCase("OK")) {
                            activity.startActivity(new Intent(activity.getApplicationContext(), MainActivity.class));
                        }
                        else {
                            try {
                                Toast.makeText(activity.getApplicationContext(),
                                        containerResponse.getString("message"),
                                        Toast.LENGTH_SHORT)
                                        .show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error.Response", error.toString());
                        Toast.makeText(activity.getApplicationContext(),"Error server connection",Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(postRequest);
    }
}
