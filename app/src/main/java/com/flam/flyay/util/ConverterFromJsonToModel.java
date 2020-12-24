package com.flam.flyay.util;

import com.flam.flyay.model.Event;
import com.flam.flyay.model.EventFinances;
import com.flam.flyay.model.EventWellness;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class ConverterFromJsonToModel {

    public static Event converterFromJsonToEvent(JSONObject jsonToConvert) throws JSONException {
        Event event = new Event();
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();
        switch (jsonToConvert.getString("category")) {
            case "FESTIVITY":
                break;
            case "WELLNESS":
                event = gson.fromJson(jsonToConvert.toString(), EventWellness.class);
                break;
            case "STUDY":
                break;
            case "FINANCES":
                event = gson.fromJson(jsonToConvert.toString(), EventFinances.class);
                break;
            case "FREE_TIME":
                break;
        }

        return event;
    }
}
