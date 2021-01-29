package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.R;
import com.flam.flyay.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;


public class CheckboxFieldFragment extends Fragment {

    private LinearLayout linearLayout;

    private String periodicEvent;



    public CheckboxFieldFragment() {
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.checkbox_field_fragment, container, false);

        linearLayout = view.findViewById(R.id.checkbox_field_fragment);

        periodicEvent = getActivity().getString(R.string.periodic_event);

        addCheckBox(periodicEvent);

        return view;
    }

    public void addCheckBox(final String text) {

        LinearLayout checkBoxLayout = new LinearLayout(this.getContext());
        checkBoxLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(checkBoxLayout);

        final CheckBox checkBox = new CheckBox(this.getContext());
        checkBox.setText(text);
        checkBox.setTextSize(16);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, Utils.convertDpToPixel(16), Utils.convertDpToPixel(16), 0);
        checkBox.setLayoutParams(params);
        checkBoxLayout.addView(checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBox.isChecked()) {
                    addOptionsFragment();

                } else {
                    removeOptionsFragment();
                }

            }
        });

    }

    private void addOptionsFragment() {
        Fragment fragment = new PeriodicOptionsFieldFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

        transaction.replace(linearLayout.getId(), fragment, fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void removeOptionsFragment() {
        Fragment optionsFragment = getActivity().getSupportFragmentManager()
                .findFragmentByTag(PeriodicOptionsFieldFragment.class.getName());

        if(optionsFragment != null) {
            Log.d(".CheckboxField", "remove current fragment " + optionsFragment);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.remove(optionsFragment);
            transaction.commit();
        }
    }

}