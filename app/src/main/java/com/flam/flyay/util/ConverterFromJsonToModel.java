package com.flam.flyay.util;

import com.flam.flyay.model.Event;
import com.flam.flyay.model.InputField;

import com.flam.flyay.model.ToDoItems;
import com.flam.flyay.model.User;
import com.flam.flyay.model.ToDo;
import com.flam.flyay.model.subevent.FestivityEvent;
import com.flam.flyay.model.subevent.FinancesEvent;
import com.flam.flyay.model.subevent.FreeTimeEvent;
import com.flam.flyay.model.subevent.StudyEvent;
import com.flam.flyay.model.subevent.WellnessEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class ConverterFromJsonToModel {

    private Gson gson;

    public static Event converterFromJsonToEvent(JSONObject jsonToConvert) throws JSONException {
        Event event = null;
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();

        switch (jsonToConvert.getString("category")) {
            case "FESTIVITY":
                event = gson.fromJson(jsonToConvert.toString(), FestivityEvent.class);
                break;
            case "STUDY":
                event = gson.fromJson(jsonToConvert.toString(), StudyEvent.class);
                break;
            case "WELLNESS":
                event = gson.fromJson(jsonToConvert.toString(), WellnessEvent.class);
                break;
            case "FINANCES":
                event = gson.fromJson(jsonToConvert.toString(), FinancesEvent.class);
                break;
            case "FREE_TIME":
                event = gson.fromJson(jsonToConvert.toString(), FreeTimeEvent.class);
                break;
        }

        return event;
    }

    public static InputField converterFromJsonToInputFiled(JSONObject jsonToConvert) {
        return new GsonBuilder().create().fromJson(jsonToConvert.toString(), InputField.class);
    }

    public static ToDo converterFromJsonToList(JSONObject jsonToConvert) {
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();
        return gson.fromJson(jsonToConvert.toString(), ToDo.class);
    }

    public static User converterFromJsonToUser(JSONObject jsonToConvert) {
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();
        return gson.fromJson(jsonToConvert.toString(), User.class);
    }

    public static ToDoItems converterFromJsonToListItems(JSONObject jsonToConvert) {
        Gson gson = new GsonBuilder()
                .setDateFormat("dd/MM/yyyy").create();
        return gson.fromJson(jsonToConvert.toString(), ToDoItems.class);
    }
}
