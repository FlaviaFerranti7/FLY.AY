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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.R;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OptionsFieldFragment extends Fragment {

    private LinearLayout linearLayout;

    private List<String> overRange;
    private List<String> examDifficulty;

    private List<CheckBox> listCheckBox;

    private TextView title;

    private String typeCheckbox;
    private String keyToSetValue;

    public OptionsFieldFragment() {}

    @SuppressLint({"LongLogTag", "ResourceType"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.options_field_fragment, container, false);

        linearLayout = view.findViewById(R.id.options_field_fragment);
        title = view.findViewById(R.id.checkbox_group_title);

        listCheckBox = new ArrayList<>();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(Utils.convertDpToPixel(5), Utils.convertDpToPixel(20), 0, 0);

        Bundle arguments = getArguments();
        typeCheckbox = arguments.getString("typeCheckbox");
        keyToSetValue = arguments.getString("key");

        String titleParam = arguments.getString("title");
        title.setText(titleParam);
        title.setLayoutParams(params);

        overRange = Arrays.asList("morning", "afternoon", "evening");
        examDifficulty = Arrays.asList("easy", "medium", "difficult");

        switch (typeCheckbox) {
            case "OVER_RANGE":
                addCheckBox(overRange);
                break;
            case "EXAM_DIFFICULTY":
                addCheckBox(examDifficulty);
                break;
        }

        Log.d(".OptionsFieldFragment", typeCheckbox);





        return view;
    }

    public void addCheckBox(final List<String> list) {

        LinearLayout checkBoxLayout = new LinearLayout(this.getContext());
        checkBoxLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(checkBoxLayout);

        for (final Object i : list) {
            final CheckBox checkBox = new CheckBox(this.getContext());
            checkBox.setText(String.valueOf(i));
            listCheckBox.add(checkBox);
            checkBox.setTextSize(16);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(Utils.convertDpToPixel(25), Utils.convertDpToPixel(10), Utils.convertDpToPixel(10), 0);
            checkBox.setLayoutParams(params);
            checkBoxLayout.addView(checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    FragmentManager fm = getFragmentManager();
                    AddEventFormFragment dynamicFormFragment = (AddEventFormFragment)fm.findFragmentById(R.id.fragment_container);

                    for(CheckBox checkBoxItem : listCheckBox) {

                        if(isChecked) {
                            if(typeCheckbox.equalsIgnoreCase("EXAM_DIFFICULTY")) {
                                if(!checkBoxItem.getText().toString().equalsIgnoreCase(checkBox.getText().toString())) {
                                    checkBoxItem.setChecked(false);
                                }
                            }
                        }

                    }

                    if(isChecked && typeCheckbox.equalsIgnoreCase("EXAM_DIFFICULTY")) {
                        dynamicFormFragment.setValueInputField(keyToSetValue, checkBox.getText().toString());
                    }
                    else if(!isChecked && typeCheckbox.equalsIgnoreCase("EXAM_DIFFICULTY")) {
                        dynamicFormFragment.setValueInputField(keyToSetValue, null);
                    }
                    else {
                        List<String> newValue = new ArrayList<>();
                        for(CheckBox c : listCheckBox) {
                            if(c.isChecked())
                                newValue.add(c.getText().toString());
                        }
                        dynamicFormFragment.setValueInputField(keyToSetValue, newValue);
                    }
                }
            });
        }
    }

}
