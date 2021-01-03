package com.flam.flyay.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flam.flyay.R;
import com.flam.flyay.adapter.EventAdapter;
import com.flam.flyay.adapter.EventDetailsAdapter;
import com.flam.flyay.model.Event;

import org.json.JSONObject;

import java.util.ArrayList;

public class EventDetailsFragment extends Fragment {

    public EventDetailsFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println("EventDetailsFragment: " + container);
        final View view = inflater.inflate(R.layout.fragment_event_details, container, false);
        final RecyclerView listRecyclerView = view.findViewById(R.id.events_info_recycler);
        Bundle arguments = getArguments();

        Event event = null;

        assert arguments != null;
        if(arguments.get("event") instanceof Event)
            event = (Event) arguments.get("event");
        else return null;


        Log.d(".EventDetailsFragment", "event received: " + event.toString());
        System.out.println(event.getValueEvent());
        EventDetailsAdapter eventDetailsAdapter = new EventDetailsAdapter(event.getKeySetSorted(), event.getValueEvent());
        eventDetailsAdapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        listRecyclerView.setLayoutManager(layoutManager);
        listRecyclerView.setAdapter(eventDetailsAdapter);
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}