package com.flam.flyay.util;

import com.flam.flyay.model.Event;
import com.flam.flyay.model.EventFinances;
import com.flam.flyay.model.EventWellness;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class ConverterFromJsonToModel {
    private Gson gson;

    public ConverterFromJsonToModel() {
        this.gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();
    }

    public Event converterFromJsonToEvent(JSONObject jsonToConvert) throws JSONException {
        Event event = new Event();
        switch (jsonToConvert.getString("category")) {
            case "FESTIVITY":
                break;
            case "WELLNESS":
                event = this.gson.fromJson(jsonToConvert.toString(), EventWellness.class);
                break;
            case "STUDY":
                break;
            case "FINANCES":
                event = this.gson.fromJson(jsonToConvert.toString(), EventFinances.class);
                break;
            case "FREE_TIME":
                break;
        }

        return event;
    }
}
