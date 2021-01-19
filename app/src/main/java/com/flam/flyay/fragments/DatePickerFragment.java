package com.flam.flyay.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private Calendar c;
    private int year;
    private int month;
    private int day;
    private StringBuilder str;

    public DatePickerFragment() {}

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        DatePickerDialog dialogDatePicker =  new DatePickerDialog(getActivity(), this, year, month, day); //android.R.style.Theme_Holo_Dialog
        dialogDatePicker.getDatePicker().setSpinnersShown(true);
        //dialogDatePicker.getDatePicker().setCalendarViewShown(false);
        return dialogDatePicker;
    }


    public void onDateSet(DatePicker view, int sYear, int sMonth, int sDay) {
        year = sYear;
        month = sMonth;
        day = sDay;
        str = new StringBuilder().append(day).append("/").append(month + 1).append("/").append(year);
        createParamsEventsFragment();
    }

    private Bundle createParamsEventsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("selectedDate", str.toString());
        Log.d(".DatePickerFragment", str.toString());
        return bundle;
    }
}
