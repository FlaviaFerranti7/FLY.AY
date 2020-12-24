package com.flam.flyay.fragments;

import android.content.ClipData;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.flam.flyay.R;
import com.flam.flyay.adapter.EventAdapter;
import com.flam.flyay.model.Event;
import com.flam.flyay.model.EventWellness;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.util.ConverterFromJsonToModel;
import com.flam.flyay.util.MockServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import java.util.List;

import java.util.ArrayList;
import java.util.List;


public class EventsListFragment extends Fragment {
    private EventService service;
    private ConverterFromJsonToModel converterFromJsonToModel;
    private List<Event> events;
    private OnEventsListListener onEventsListListener;

    public interface OnEventsListListener {
        void onEventSelected(Event e);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        final ListView listView = view.findViewById(R.id.events_list);
        Bundle arguments = getArguments();

        this.service = new EventService(this.getContext());
        this.converterFromJsonToModel = new ConverterFromJsonToModel();
        this.events = new ArrayList<>();
        String currentDate = arguments.getString("currentDate");

        JSONObject params = getParams(currentDate);
        Log.d(".EventsListFragment", "parameters: [currentDate = '" + currentDate + "']");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onEventsListListener.onEventSelected(events.get(position));
            }
        });

        service.getEventsByDay(params, new ServerCallback() {
            @Override
            public void onSuccess(Object result) {
                events = (List<Event>) result;
                List<String> eventsTitle = new ArrayList<>();
                Log.d(".EventsListFragment", events.toString());

                EventAdapter eventAdapter = new EventAdapter(getActivity(), R.layout.event_list_adapter_layout, events);
                listView.setAdapter(eventAdapter);
                eventAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private JSONObject getParams(String currentDay) {
        JSONObject params = new JSONObject();
        try {
            params.put("currentDay", currentDay);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }
}