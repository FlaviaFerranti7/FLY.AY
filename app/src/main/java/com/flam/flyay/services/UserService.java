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

    public void signin(String url, JSONObject params, final ServerCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(activity);

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response);
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
