package com.flam.flyay.services;

import android.content.Context;
import android.util.Log;

import com.flam.flyay.model.ToDo;
import com.flam.flyay.util.ConverterFromJsonToModel;
import com.flam.flyay.util.MockServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ToDoService {

    private Context context;
    private AppRequest appRequest;

    public ToDoService(Context context) {
        this.context = context;
        this.appRequest = new AppRequest();
    }

    public void getUserLists(JSONObject params, final ServerCallback callback) {
        Log.d(".ToDoService", "GET -UserLists");
        appRequest.jsonObjectGETRequest(context, MockServerUrl.LIST.url, params, new ServerCallback() {
            @Override
            public void onSuccess(Object result) {
                getToDoList((JSONObject) result, callback);
            }
        });
    }

    public void getToDoList(JSONObject requestResult, final ServerCallback callback) {
        List<ToDo> toDoList = new ArrayList<>();
        JSONArray containerResponse = new JSONArray();
        try {
            containerResponse = requestResult.getJSONArray("return");

            for(int i = 0; i < containerResponse.length(); i ++) {
                JSONObject currentJSONObject = containerResponse.getJSONObject(i);
                ToDo toDo = ConverterFromJsonToModel.converterFromJsonToList(currentJSONObject);
                Log.d(".TodoListFragment", toDo.toString());

                toDoList.add(toDo);
            }
            callback.onSuccess(toDoList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}

