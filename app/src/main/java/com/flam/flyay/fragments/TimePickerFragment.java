package com.flam.flyay.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {

    TimePickerDialog.OnTimeSetListener onTimeSet;

    private Calendar c;
    private int hour;
    private int minute;
    private StringBuilder str;

    public static TimePickerFragment newInstance(){
        TimePickerFragment frag = new TimePickerFragment();
        return frag;
    }

    public TimePickerFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable final Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable final Bundle savedInstanceState) {
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog dialogTimePicker =  new TimePickerDialog(getActivity(), onTimeSet, hour, minute, DateFormat.is24HourFormat(getActivity()));

        return dialogTimePicker;
    }

    public void setCallBack(TimePickerDialog.OnTimeSetListener onTime) {
        onTimeSet = onTime;
    }
}












