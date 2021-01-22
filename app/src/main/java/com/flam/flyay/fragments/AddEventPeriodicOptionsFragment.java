package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
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
import com.flam.flyay.util.Utils;

import java.util.Arrays;
import java.util.List;

public class AddEventPeriodicOptionsFragment extends Fragment {

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private List<String> periodicEventList1;
    private List<String> periodicEventList2;
    private boolean clicked;

    public AddEventPeriodicOptionsFragment() {}

    @SuppressLint({"LongLogTag", "ResourceType"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_event_periodic_options_fragment, container, false);

        linearLayout1 = view.findViewById(R.id.add_event_periodic_options_fragment_1);
        linearLayout1.setId(4);

        linearLayout2 = view.findViewById(R.id.add_event_periodic_options_fragment_2);
        linearLayout2.setId(5);

        clicked = false;

        periodicEventList1 = Arrays.asList("every day", "every week");
        addCheckBox(linearLayout1, periodicEventList1);

        periodicEventList2 = Arrays.asList("every month", "every year", "customized");
        addCheckBox(linearLayout2, periodicEventList2);

        return view;
    }

    public void addCheckBox(LinearLayout layout, List<String> list) {

        LinearLayout checkBoxLayout = new LinearLayout(this.getContext());
        checkBoxLayout.setOrientation(LinearLayout.VERTICAL);
        layout.addView(checkBoxLayout);

        for (final Object i : list) {
            final CheckBox checkBox = new CheckBox(this.getContext());
            checkBox.setText(String.valueOf(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(Utils.convertDpToPixel(32), Utils.convertDpToPixel(16), Utils.convertDpToPixel(16), 0);
            checkBox.setLayoutParams(params);
            checkBoxLayout.addView(checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    AddEventPeriodicWeekOptionsFragment fragment = new AddEventPeriodicWeekOptionsFragment();
                    if (checkBox.isChecked() && String.valueOf(i).equals("every week") && !clicked) {
                        addFragment(fragment);
                        clicked = true;

                    } else {
                        if (String.valueOf(i).equals("every week") && clicked){
                            fragment = (AddEventPeriodicWeekOptionsFragment) getActivity().getSupportFragmentManager().findFragmentById(linearLayout1.getId());
                            removeFragment(fragment);
                            clicked = false;
                        }
                    }
                }
            });
        }
    }

    public void addFragment(Fragment fragment){

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(linearLayout1.getId(), fragment, fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void removeFragment(Fragment fragment){

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

}
