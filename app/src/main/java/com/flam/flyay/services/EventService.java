package com.flam.flyay.services;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class EventService<T> {
    private T object;

    public EventService(T object) {
        this.object = object;
    }

    public void getEventsByDay(String url, JSONObject params, final ServerCallback callback) {
        final Context context;
        if(object instanceof Activity) {
            context = (Activity) object;
        } else {
            context = ((View) object).getContext();
        }
        Log.d(".EventService", "Starting getEventsByDay");
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

}
