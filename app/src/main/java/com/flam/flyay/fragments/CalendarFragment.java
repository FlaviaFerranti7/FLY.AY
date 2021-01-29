package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.MainActivity;
import com.flam.flyay.R;
import com.flam.flyay.adapter.EventAdapter;
import com.flam.flyay.model.Event;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;

import com.flam.flyay.util.DayDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatDayFormatter;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.file.attribute.UserPrincipalLookupService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CalendarFragment extends Fragment {

    private LinearLayout linearLayoutCalendar;

    private String selectedDate;
    private SimpleDateFormat df;
    private String eventDate;

    private EventService service;
    private HomeFragment.OnEventsListListener onEventsListListener;
    private List<Event> events;
    private List<Event> eventsFiltered;
    private List<Date> listDays;
    private HashSet<CalendarDay> dates;
    
    private int color;
    private CalendarDay c;
    private String calendarDay;

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            onEventsListListener = (HomeFragment.OnEventsListListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException("OnEventsListListener interface must be implemented");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        final RecyclerView listRecyclerView = view.findViewById(R.id.events_recycler);
        listRecyclerView.setNestedScrollingEnabled(false);

        this.service = new EventService(this.getContext());
        this.events = new ArrayList<>();
        this.eventsFiltered = new ArrayList<>();

        linearLayoutCalendar = view.findViewById(R.id.fragment_calendar);

        Bundle arguments = getArguments();
        selectedDate = arguments.getString("currentDate");

        ((MainActivity) getActivity()).getSupportActionBar().setIcon(null);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(selectedDate);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        JSONObject params = getParams(selectedDate);

        c = CalendarDay.today();
        final MaterialCalendarView materialCalView = view.findViewById(R.id.calendarView);
        materialCalView.setDateSelected(c, true);

        int day = c.getDay();
        int month = c.getMonth();
        int year = c.getYear();

        if(day < 10 && month < 10) {
            calendarDay = "0" + day + "/0" + month + "/" + year;
        }
        else if(month < 10){
            calendarDay = day + "/0" + month +"/" + year;
        }
        else if(day < 10){
            calendarDay = "0"+ day + "/" + month +"/" + year;
        }
        else{
            calendarDay = day + "/" + month +"/" + year;
        }

        dates = new HashSet<>();
        CalendarDay newCal = CalendarDay.from(2021, 01,15);
        dates.add(newCal);
        color = Color.rgb(0,0, 255);

        DayDecorator dayDecorator = new DayDecorator(color, dates);
        materialCalView.addDecorator(dayDecorator);

        df = new SimpleDateFormat("dd/MM/yyyy");
        service.getEventsByDay(params, new ServerCallback() {
            @Override
            public void onSuccess(Object result) {
                events = (List<Event>) result;
                for (Event e : events) {
                    eventDate = df.format(e.getDate());
                    if(calendarDay.equals(eventDate)){
                        eventsFiltered.add(e);
                    }
                }
                EventAdapter eventAdapter = new EventAdapter(eventsFiltered, onEventsListListener);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                listRecyclerView.setAdapter(eventAdapter);
                listRecyclerView.setLayoutManager(layoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(listRecyclerView.getContext(),
                        layoutManager.getOrientation());
                listRecyclerView.addItemDecoration(dividerItemDecoration);
                eventAdapter.notifyDataSetChanged();
            }

        });


        materialCalView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int day = date.getDay();
                int month =  date.getMonth();
                int year =  date.getYear();
                if(day < 10 && month < 10) {
                    selectedDate = "0" + day + "/0" + month + "/" + year;
                }
                else if(month < 10){
                    selectedDate = day + "/0" + month +"/" + year;
                }
                else if(day < 10){
                    selectedDate = "0"+ day + "/" + month +"/" + year;
                }
                else{
                    selectedDate = day + "/" + month +"/" + year;
                }
                ((MainActivity) getActivity()).getSupportActionBar().setTitle(selectedDate);

                eventsFiltered.clear();

                for (Event e : events) {
                    eventDate = df.format(e.getDate());
                    if(selectedDate.equals(eventDate)){
                        eventsFiltered.add(e);
                    }
                }
                EventAdapter eventAdapter = new EventAdapter(eventsFiltered, onEventsListListener);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflator) {
        menu.clear();
        inflator.inflate(R.menu.actions_menu, menu);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflator);
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
