package com.flam.flyay.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    DatePickerDialog.OnDateSetListener onDateSet;

    private Calendar c;
    private int year;
    private int month;
    private int day;

    public static DatePickerFragment newInstance(){
        DatePickerFragment frag = new DatePickerFragment();
        return frag;
    }

    public DatePickerFragment() {}

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
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialogDatePicker =  new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog, onDateSet, year, month, day);
        dialogDatePicker.getDatePicker().setSpinnersShown(true);
        dialogDatePicker.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return dialogDatePicker;
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener onDate) {
        onDateSet = onDate;
    }

}
