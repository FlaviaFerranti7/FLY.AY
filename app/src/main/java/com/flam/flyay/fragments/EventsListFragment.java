package com.flam.flyay.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.flam.flyay.R;
import com.flam.flyay.model.Event;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.services.UserService;
import com.flam.flyay.util.ConverterFromJsonToModel;
import com.flam.flyay.util.MockServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class EventsListFragment extends Fragment {
    private EventService service;
    private ConverterFromJsonToModel converterFromJsonToModel;

    public EventsListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_events_list, container, false);

        this.service = new EventService(view.getContext());
        converterFromJsonToModel = new ConverterFromJsonToModel();
        final LinearLayout eventsList = view.findViewById(R.id.eventsList);

        JSONObject params = new JSONObject();
        try {
            params.put("currentDay", "30/01/2021");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        service.getEventsByDay(MockServerUrl.EVENT_DAY.url, params, new ServerCallback() {
            @Override
            public void onSuccess(JSONObject result) {
                JSONArray containerResponse = new JSONArray();
                try {
                    containerResponse = result.getJSONArray("return");

                    for(int i = 0; i < containerResponse.length(); i ++) {
                        JSONObject currentJSONObject = containerResponse.getJSONObject(i);
                        Button btn = new Button(view.getContext());
                        Event event = converterFromJsonToModel.converterFromJsonToEvent(currentJSONObject);
                        Log.d(".MainActivity", event.toString());


                        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
                        btn.setText(event.getTitle());
                        eventsList.addView(btn);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }

}