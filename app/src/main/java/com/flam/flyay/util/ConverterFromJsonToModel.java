package com.flam.flyay.util;

import com.flam.flyay.model.Event;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class ConverterFromJsonToModel {

    public static Event converterFromJsonToEvent(JSONObject jsonToConvert) throws JSONException {
        Event event = new Event();
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();

        return gson.fromJson(jsonToConvert.toString(), Event.class);
    }
}
