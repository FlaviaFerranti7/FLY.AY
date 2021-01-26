package com.flam.flyay.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class CalendarFragment extends Fragment {

    private LinearLayout linearLayout;

    private String selectedDate;
    private Calendar c;
    private SimpleDateFormat df;

    private EventService service;
    private HomeFragment.OnEventsListListener onEventsListListener;
    private List<Event> events;

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

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        linearLayout = view.findViewById(R.id.fragment_calendar);

        final RecyclerView listRecyclerView = view.findViewById(R.id.events_recycler);
        listRecyclerView.setNestedScrollingEnabled(false);

        this.service = new EventService(this.getContext());
        this.events = new ArrayList<>();

        Bundle arguments = getArguments();
        selectedDate = arguments.getString("currentDate");

        ((MainActivity) getActivity()).getSupportActionBar().setIcon(null);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle(selectedDate);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addCalendar();

        JSONObject params = getParams(selectedDate);
        Log.d(".EventsListFragment", "parameters: [currentDate = '" + selectedDate + "']");

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


    public void addCalendar(){
        CalendarView calendarView = new CalendarView(this.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(90, 160, 80, 40);
        calendarView.setLayoutParams(layoutParams);

        linearLayout.addView(calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, final int year, final int month, final int dayOfMonth) {

                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                selectedDate = (new StringBuilder().append(dayOfMonth).append("/").append(month + 1).append("/").append(year)).toString();
                ((MainActivity) getActivity()).getSupportActionBar().setTitle(selectedDate);
            }
        });

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
