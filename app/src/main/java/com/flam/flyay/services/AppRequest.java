package com.flam.flyay.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class AppRequest{

    public static void jsonObjectGETRequest(final Context context, String url, JSONObject params, final ServerCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, params,
                new Response.Listener<JSONObject>() {
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
                        Toast.makeText(context.getApplicationContext(),"Error server connection",Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }

    public static void jsonObjectPOSTRequest(final Context context, String url, JSONObject params, final ServerCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
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
                        Toast.makeText(context.getApplicationContext(),"Error server connection",Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }

    public static void jsonObjectDELETERequest(final Context context, String url, JSONObject params, final ServerCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, params,
                new Response.Listener<JSONObject>() {
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
                        Toast.makeText(context.getApplicationContext(),"Error server connection",Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }

    public static void jsonObjectPUTRequest(final Context context, String url, JSONObject params, final ServerCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, url, params,
                new Response.Listener<JSONObject>() {
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
                        Toast.makeText(context.getApplicationContext(),"Error server connection",Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }
}
