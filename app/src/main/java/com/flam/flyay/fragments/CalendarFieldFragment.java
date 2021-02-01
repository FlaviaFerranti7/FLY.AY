package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.flam.flyay.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class CalendarFieldFragment extends Fragment {

    private LinearLayout linearLayout;
    private String selectedDate;
    private SimpleDateFormat df;

    public CalendarFieldFragment() {
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.checkbox_field_fragment, container, false);

        linearLayout = view.findViewById(R.id.checkbox_field_fragment);

        addCalendar();

        return view;
    }


    private void addCalendar() {
        CalendarView calendarView = new CalendarView(this.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        calendarView.setLayoutParams(layoutParams);
        linearLayout.addView(calendarView);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, final int year, final int month, final int dayOfMonth) {

                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                df = new SimpleDateFormat("dd/MM/yyyy");
                selectedDate = df.format(c.getTime());
                Log.d(".CalendarFieldFragment", selectedDate);

            }
        });
    }


}