package com.flam.flyay.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flam.flyay.MainActivity;
import com.flam.flyay.R;
import com.flam.flyay.SearchActivity;
import com.flam.flyay.adapter.EventAdapter;
import com.flam.flyay.model.Event;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.util.ConverterFromJsonToModel;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

import java.util.List;


public class HomeFragment extends Fragment {
    private EventService service;
    private ConverterFromJsonToModel converterFromJsonToModel;
    private List<Event> events;
    private OnEventsListListener onEventsListListener;

    public interface OnEventsListListener {
        void onEventSelected(Event e);
    }

    public HomeFragment() {}

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
        final View view = inflater.inflate(R.layout.home_fragment, container, false);
        final RecyclerView listRecyclerView = view.findViewById(R.id.events_recycler);
        listRecyclerView.setNestedScrollingEnabled(false);
        Bundle arguments = getArguments();

        this.service = new EventService(this.getContext());
        this.converterFromJsonToModel = new ConverterFromJsonToModel();
        this.events = new ArrayList<>();
        String currentDate = arguments.getString("currentDate");

        ((MainActivity) getActivity()).getSupportActionBar().setTitle("      " + currentDate);
        ((MainActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.ic_home_page);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        JSONObject params = getParams(currentDate);
        Log.d(".EventsListFragment", "parameters: [currentDate = '" + currentDate + "']");

        service.getEventsByDay(params, new ServerCallback() {
            @Override
            public void onSuccess(Object result) {
                events = (List<Event>) result;
                Log.d(".EventsListFragment", events.toString());

                EventAdapter eventAdapter = new EventAdapter(events, onEventsListListener);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                listRecyclerView.setAdapter(eventAdapter);
                listRecyclerView.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listRecyclerView.getContext(),
                        layoutManager.getOrientation());
                listRecyclerView.addItemDecoration(dividerItemDecoration);
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