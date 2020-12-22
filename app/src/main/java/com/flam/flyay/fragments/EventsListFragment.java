package com.flam.flyay.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.flam.flyay.model.Event;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.util.ConverterFromJsonToModel;
import com.flam.flyay.util.MockServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

<<<<<<< HEAD
=======
import java.util.ArrayList;
>>>>>>> ccefb07... chore: integrated ListFragment
import java.util.List;


public class EventsListFragment extends ListFragment {
    private EventService service;
    private ConverterFromJsonToModel converterFromJsonToModel;
    private List<String> events;
    private OnEventsListListener onEventsListListener;

    public interface OnEventsListListener {
        void onEventSelected(int index);
    }

    public EventsListFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            onEventsListListener = (OnEventsListListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException("OnEventsListListener interface must be implemented");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(".EventsListFragment", "create view...");
        super.onCreate(savedInstanceState);

        this.service = new EventService(this.getContext());
        this.converterFromJsonToModel = new ConverterFromJsonToModel();
        this.events = new ArrayList<>();
        JSONObject params = getParams();

        service.getEventsByDay(params, new ServerCallback() {
            @Override
<<<<<<< HEAD
            public void onSuccess(Object result) {
                List<Event> events = (List<Event>) result;
                for(int i = 0; i < events.size(); i ++) {
                    Event event = events.get(i);
                    Log.d(".EventsListFragment", event.toString());

                    Button btn = new Button(view.getContext());
                    btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 200));
                    btn.setText(event.getTitle());
                    eventsList.addView(btn);
=======
            public void onSuccess(JSONObject result) {
                JSONArray containerResponse;
                try {
                    containerResponse = result.getJSONArray("return");

                    for(int i = 0; i < containerResponse.length(); i ++) {
                        Event event = converterFromJsonToModel.converterFromJsonToEvent(containerResponse.getJSONObject(i));
                        events.add(event.getTitle());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
>>>>>>> ccefb07... chore: integrated ListFragment
                }
                Log.d(".EventsListFragment",events.toString());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, events);
                setListAdapter(adapter);
            }
        });
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        onEventsListListener.onEventSelected(position);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private JSONObject getParams() {
        JSONObject params = new JSONObject();
        try {
            params.put("currentDay", "30/01/2021");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }
}