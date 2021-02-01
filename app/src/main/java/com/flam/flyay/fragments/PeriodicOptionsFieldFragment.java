package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.R;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PeriodicOptionsFieldFragment extends Fragment {

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;

    private List<String> periodicEventList1;
    private List<String> periodicEventList2;


    private List<String> buttonsValue;

    private boolean clickedW;
    private boolean clickedC;

    public PeriodicOptionsFieldFragment() {}

    @SuppressLint({"LongLogTag", "ResourceType"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.periodic_options_field_fragment, container, false);

        buttonsValue = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");

        linearLayout1 = view.findViewById(R.id.periodic_options_field_fragment_1);
        linearLayout2 = view.findViewById(R.id.periodic_options_field_fragment_2);

        clickedW = false;
        clickedC = false;

        periodicEventList1 = Arrays.asList("every day", "every week");
        addCheckBox(linearLayout1, periodicEventList1);

        periodicEventList2 = Arrays.asList("every month", "every year", "customized");
        addCheckBox(linearLayout2, periodicEventList2);



        return view;
    }

    public void addCheckBox(LinearLayout layout, final List<String> list) {

        LinearLayout checkBoxLayout = new LinearLayout(this.getContext());
        checkBoxLayout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(checkBoxLayout);

        for (final Object i : list) {
            final CheckBox checkBox = new CheckBox(this.getContext());
            checkBox.setText(String.valueOf(i));
            checkBox.setTextSize(16);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(Utils.convertDpToPixel(32), Utils.convertDpToPixel(16), Utils.convertDpToPixel(16), 0);
            checkBox.setLayoutParams(params);
            checkBoxLayout.addView(checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    ButtonsFieldFragment fragmentD = new ButtonsFieldFragment();
                    CalendarFieldFragment fragmentC = new CalendarFieldFragment();

                    if (checkBox.isChecked() && String.valueOf(i).equals("every week") && !clickedW) {
                        addFragment(linearLayout1, fragmentD, createParamsEventsFragment());
                        clickedW = true;
                    } else {
                        if (String.valueOf(i).equals("every week") && clickedW){
                            fragmentD = (ButtonsFieldFragment) getActivity().getSupportFragmentManager().findFragmentById(linearLayout1.getId());
                            removeFragment(fragmentD);
                            clickedW = false;
                        }
                    }

                    if (checkBox.isChecked() && String.valueOf(i).equals("customized") && !clickedC) {
                        addFragment(linearLayout2, fragmentC, null);
                        clickedC = true;

                    } else {
                        if (String.valueOf(i).equals("customized") && clickedC){
                            fragmentC = (CalendarFieldFragment) getActivity().getSupportFragmentManager().findFragmentById(linearLayout2.getId());
                            removeFragment(fragmentC);
                            clickedC = false;
                        }
                    }
                }
            });
        }
    }

    private void addFragment(LinearLayout layout, Fragment fragment, Bundle params) {

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if(params != null)
            fragment.setArguments(params);
        transaction.replace(layout.getId(), fragment, fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void removeFragment(Fragment fragment) {

        if(fragment != null) {
            Log.d(".OptionField", "remove current fragment " + fragment);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

    private Bundle createParamsEventsFragment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", new ArrayList(buttonsValue));
        Log.d(".OptionsField", buttonsValue.toString());
        return bundle;
    }

}

