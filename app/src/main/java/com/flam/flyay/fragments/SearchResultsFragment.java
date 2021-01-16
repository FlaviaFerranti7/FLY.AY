package com.flam.flyay.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;
import com.flam.flyay.SearchActivity;
import com.flam.flyay.adapter.EventFilteredAdapter;
import com.flam.flyay.model.Event;
import com.flam.flyay.model.subevent.FreeTimeEvent;
import com.flam.flyay.model.subevent.StudyEvent;
import com.flam.flyay.model.subevent.WellnessEvent;
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

    private String searchName;
    private String searchPlace;
    private String checkedCategory;

    private SearchResultsFragment.OnEventsListListener onEventsListListener;

    public interface OnEventsListListener {
        void onEventSelected(Event e);
    }

    public SearchResultsFragment(){}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            onEventsListListener = (SearchResultsFragment.OnEventsListListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException("OnEventsListListener interface must be implemented");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.search_results_fragment, container, false);
        final RecyclerView listRecyclerView = view.findViewById(R.id.events_recycler);
        listRecyclerView.setNestedScrollingEnabled(false);

        ((SearchActivity) getActivity()).getSupportActionBar().setTitle("Results");
        ((SearchActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((SearchActivity) getActivity()).getSupportActionBar().setIcon(null);

        this.service = new EventService(this.getContext());
        this.converterFromJsonToModel = new ConverterFromJsonToModel();
        this.events = new ArrayList<>();
        this.eventsFiltered = new ArrayList<>();

        Bundle arguments = getArguments();

        searchName = arguments.getString("searchParamsName");
        searchPlace = arguments.getString("searchParamsPlace");
        checkedCategory = arguments.getString("checkedCategory");

        JSONObject params = getParams(searchName, searchPlace, checkedCategory);

        Log.d(".SearchResultsFragment", "parameters: " + searchName + searchPlace + checkedCategory);

        service.getEventsByFilter(params, new ServerCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(Object result) {
                events = (List<Event>) result;

                for (Event e : events) {
                    if (!Utils.isEmptyOrBlank(searchName) && e.getTitle().toLowerCase().contains(searchName)) {
                        Log.d(".SearchResultsFragment", e.toString());
                        Log.d(".SearchResultsFragment",  " eventsFiltered: " + eventsFiltered.toString());
                        if (!(eventsFiltered.contains(e))) {
                            eventsFiltered.add(e);
                        }
                    }
                    if (checkedCategory.contains(e.getCategory())){
                        Log.d(".SearchResultsFragment", e.toString());
                        Log.d(".SearchResultsFragment",  " eventsFilteredPerCategories: " + eventsFiltered.toString());
                        if (!(eventsFiltered.contains(e))) {
                            eventsFiltered.add(e);
                        }
                    }
                    if(e.getClass() == FreeTimeEvent.class && !Utils.isEmptyOrBlank(searchPlace) && ((FreeTimeEvent) e).getPlace().toLowerCase().contains(searchPlace)){
                        Log.d(".SearchResultsFragment", e.toString());
                        Log.d(".SearchResultsFragment",  " eventsFiltered: " + eventsFiltered.toString());
                        if (!(eventsFiltered.contains(e))) {
                            eventsFiltered.add(e);
                        }
                    }
                    else if(e.getClass() == WellnessEvent.class && !Utils.isEmptyOrBlank(searchPlace) && ((WellnessEvent) e).getPlace().toLowerCase().contains(searchPlace)){
                        Log.d(".SearchResultsFragment", e.toString());
                        Log.d(".SearchResultsFragment",  " eventsFiltered: " + eventsFiltered.toString());
                        if (!(eventsFiltered.contains(e))) {
                            eventsFiltered.add(e);
                        }
                    }
                    else if(e.getClass() == StudyEvent.class && !Utils.isEmptyOrBlank(searchPlace) && ((StudyEvent) e).getPlace().toLowerCase().contains(searchPlace)){
                        Log.d(".SearchResultsFragment", e.toString());
                        Log.d(".SearchResultsFragment",  " eventsFiltered: " + eventsFiltered.toString());
                        if (!(eventsFiltered.contains(e))) {
                            eventsFiltered.add(e);
                        }
                    }
                }

                Log.d(".SearchResultsFragment",  " eventsFiltered: " + eventsFiltered.toString());
                EventFilteredAdapter eventFilteredAdapter = new EventFilteredAdapter(eventsFiltered, onEventsListListener);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                listRecyclerView.setAdapter(eventFilteredAdapter);
                listRecyclerView.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listRecyclerView.getContext(),
                        layoutManager.getOrientation());
                listRecyclerView.addItemDecoration(dividerItemDecoration);
                eventFilteredAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private JSONObject getParams(String sName, String sPlace, String checkCat) {
        JSONObject params = new JSONObject();
        try {
            params.put("searchParamsName", sName);
            params.put("searchParamsPlace", sPlace);
            params.put("checkedCategory", checkCat);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
