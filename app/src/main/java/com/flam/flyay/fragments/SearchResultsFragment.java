package com.flam.flyay.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;
import com.flam.flyay.adapter.EventAdapter;
import com.flam.flyay.model.Event;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.util.ConverterFromJsonToModel;
import com.flam.flyay.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchResultsFragment extends Fragment {
    private EventService service;
    private ConverterFromJsonToModel converterFromJsonToModel;
    private List<Event> events;
    private List<Event> eventsFiltered;

    String searchName;
    String searchPlace;

    public SearchResultsFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.search_results_fragment, container, false);
        final RecyclerView listRecyclerView = view.findViewById(R.id.events_recycler);
        listRecyclerView.setNestedScrollingEnabled(false);

        Intent intent = getActivity().getIntent();


        searchName = intent.getStringExtra("searchParamsName");
        searchPlace = intent.getStringExtra("searchParamsPlace");

        this.service = new EventService(this.getContext());
        this.converterFromJsonToModel = new ConverterFromJsonToModel();
        this.events = new ArrayList<>();
        this.eventsFiltered = new ArrayList<>();

        JSONObject params = getParams(searchName, searchPlace);

        Log.d(".SearchResultsFragment", "parameters: " + searchName + searchPlace);

        service.getEventsByFilter(params, new ServerCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(Object result) {
                events = (List<Event>) result;

                for (Event e : events) {
                    if (!Utils.isEmptyOrBlank(searchName) && e.getTitle().toLowerCase().contains(searchName)) {
                        Log.d(".SearchResultsFragment", e.toString());
                        Log.d(".SearchResultsFragment",  " eventsFiltered: " + eventsFiltered.toString());
                        eventsFiltered.add(e);
                    }
                    if (!Utils.isEmptyOrBlank(searchPlace) && e.getTitle().toLowerCase().contains(searchPlace)) {
                        Log.d(".SearchResultsFragment", e.toString());
                        Log.d(".SearchResultsFragment",  " eventsFiltered: " + eventsFiltered.toString());
                        eventsFiltered.add(e);
                    }
                }

                Log.d(".SearchResultsFragment",  " eventsFiltered: " + eventsFiltered.toString());
                EventAdapter filteredEventAdapter = new EventAdapter(eventsFiltered, null);
                listRecyclerView.setAdapter(filteredEventAdapter);
                filteredEventAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private JSONObject getParams(String sName, String sPlace) {
        JSONObject params = new JSONObject();
        try {
            params.put("searchParamsName", sName);
            params.put("searchParamsPlace", sPlace);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }

}
