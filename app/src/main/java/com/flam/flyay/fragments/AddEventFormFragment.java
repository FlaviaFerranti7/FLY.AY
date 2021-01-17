package com.flam.flyay.fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flam.flyay.AddEventActivity;
import com.flam.flyay.R;
import com.flam.flyay.model.Event;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class AddEventFormFragment extends Fragment {

    RelativeLayout relativeLayout;
    List<String> categoryList;
    List<String> overRangeList;

    public AddEventFormFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_event_form_fragment, container, false);

        ((AddEventActivity) getActivity()).getSupportActionBar().setTitle("      Add event");
        ((AddEventActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.ic_add_event);
        ((AddEventActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        categoryList = new ArrayList();
        categoryList.add(CategoryEnum.STUDY.name);
        categoryList.add(CategoryEnum.FREE_TIME.name);
        categoryList.add(CategoryEnum.WELLNESS.name);
        categoryList.add(CategoryEnum.FESTIVITY.name);
        categoryList.add(CategoryEnum.FINANCES.name);

        overRangeList = new ArrayList();
        overRangeList.add("Morning");
        overRangeList.add("Afternoon");
        overRangeList.add("Evening");

        relativeLayout = view.findViewById(R.id.add_event_form);

        addTextViewAndEditText("Title: ", "Insert here");
        addToggleButtons("Which category?", categoryList);
        addCheckBoxes("Over range", overRangeList);

        return view;
    }

    private void addTextViewAndEditText(String text, String hint) {

        LinearLayout textLayout = new LinearLayout(this.getContext());
        textLayout.setOrientation(LinearLayout.HORIZONTAL);
        relativeLayout.addView(textLayout);

        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        LinearLayout.LayoutParams textViewparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textViewparams.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(16),0, 0);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(textViewparams);

        EditText editText = new EditText(this.getContext());
        editText.setHint(hint);
        LinearLayout.LayoutParams editTextparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        editTextparams.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(16), Utils.convertDpToPixel(16),0);
        editText.setLayoutParams(editTextparams);

        textLayout.addView(textView);
        textLayout.addView(editText);

        addLineSeperator();
    }

    private void addToggleButtons(String text, List<String> categoryList) {

        LinearLayout textAndToggleLayout = new LinearLayout(this.getContext());
        textAndToggleLayout.setOrientation(LinearLayout.VERTICAL);
        relativeLayout.addView(textAndToggleLayout);

        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        textParams.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(64),0, 0);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(textParams);

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this.getContext());
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        horizontalScrollView.setLayoutParams(scrollParams);

        LinearLayout toggleButtonsLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams toggleButtonsParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        toggleButtonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        toggleButtonsLayout.setLayoutParams(toggleButtonsParams);

        horizontalScrollView.addView(toggleButtonsLayout);

        textAndToggleLayout.addView(textView);
        textAndToggleLayout.addView(horizontalScrollView);

        for (Object i : categoryList) {
            ToggleButton toggleButton = new ToggleButton(this.getContext());
            toggleButton.setText(String.valueOf(i));
            LinearLayout.LayoutParams toggleButtonparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            toggleButtonparams.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(8),0, 0);
            toggleButton.setLayoutParams(toggleButtonparams);
            toggleButtonsLayout.addView(toggleButton);
        }
        addLineSeperator();
    }

    private void addCheckBoxes(String text, List<String> overRangeList) {

        LinearLayout textAndCheckBoxes = new LinearLayout(this.getContext());
        textAndCheckBoxes.setOrientation(LinearLayout.VERTICAL);
        relativeLayout.addView(textAndCheckBoxes);

        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        textParams.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(144),0, 0);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(textParams);

        LinearLayout checkBoxLayout = new LinearLayout(this.getContext());
        checkBoxLayout.setOrientation(LinearLayout.VERTICAL);

        textAndCheckBoxes.addView(textView);
        textAndCheckBoxes.addView(checkBoxLayout);

        for (Object i : overRangeList) {
            CheckBox checkBox = new CheckBox(this.getContext());
            checkBox.setText(String.valueOf(i));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(4), Utils.convertDpToPixel(16),0);
            checkBox.setLayoutParams(params);
            checkBoxLayout.addView(checkBox);
        }

        addLineSeperator();
    }

    private void addLineSeperator() {
        LinearLayout lineLayout = new LinearLayout(this.getContext());
        lineLayout.setBackgroundColor(Color.GRAY);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                2);
        params.setMargins(0, Utils.convertDpToPixel(10), 0, Utils.convertDpToPixel(10));
        lineLayout.setLayoutParams(params);
        relativeLayout.addView(lineLayout);
    }

}
