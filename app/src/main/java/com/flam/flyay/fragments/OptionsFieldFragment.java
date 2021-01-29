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
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.R;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OptionsFieldFragment extends Fragment {

    private LinearLayout linearLayout;

    private List<String> overRange;
    private List<String> examDifficulty;

    private TextView title;

    private String typeCheckbox;

    public OptionsFieldFragment() {}

    @SuppressLint({"LongLogTag", "ResourceType"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.options_field_fragment, container, false);

        linearLayout = view.findViewById(R.id.options_field_fragment);
        title = view.findViewById(R.id.checkbox_group_title);

        Bundle arguments = getArguments();
        typeCheckbox = arguments.getString("typeCheckbox");

        String titleParam = arguments.getString("title");
        title.setText(titleParam);

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

                }
            });
        }
    }

}
