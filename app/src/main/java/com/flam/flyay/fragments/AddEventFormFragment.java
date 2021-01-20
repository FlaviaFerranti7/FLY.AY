package com.flam.flyay.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.AddEventActivity;
import com.flam.flyay.R;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class AddEventFormFragment extends Fragment {

    private String selectedDate;
    private String btnString;
    private Boolean clicked;

    private LinearLayout linearLayout;

    private List<String> categoryList;
    private List<String> overRangeList;

    public AddEventFormFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_event_form_fragment, container, false);

        clicked = false;

        ((AddEventActivity) getActivity()).getSupportActionBar().setTitle("      Add event");
        ((AddEventActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.ic_add_event);
        ((AddEventActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            selectedDate = arguments.getString("selectedDate");
            Log.d(".AddEventFormFragment", selectedDate);
        }

        categoryList = Arrays.asList(CategoryEnum.FREE_TIME.name, CategoryEnum.STUDY.name, CategoryEnum.WELLNESS.name,
                CategoryEnum.FESTIVITY.name, CategoryEnum.FINANCES.name);

        overRangeList = Arrays.asList("Morning", "Afternoon", "Evening");


        linearLayout = view.findViewById(R.id.add_event_form);

        addTextViewAndEditText("Title: ", "Insert here");
        addTextViewAndButtons("Which category?", categoryList);
        addDatePickerDialog();
        addTimePickersDialog();
        addCheckBox("Periodic event");
        addTextViewAndEditText("Where?", "Insert here");
        addTextViewAndEditText("Note:", "Insert here");
        addEventButton();

        return view;
    }

    private void addTextView(LinearLayout layout, String text, Integer marginLeft, Integer marginTop) {

        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        textParams.setMargins(Utils.convertDpToPixel(marginLeft), Utils.convertDpToPixel(marginTop), 0, 0);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(textParams);

        layout.addView(textView);
    }

    private void addEditText(LinearLayout layout, String hint, Integer marginLeft, Integer marginTop) {

        EditText editText = new EditText(this.getContext());
        editText.setHint(hint);
        LinearLayout.LayoutParams editTextparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        editTextparams.setMargins(Utils.convertDpToPixel(marginLeft), Utils.convertDpToPixel(marginTop),Utils.convertDpToPixel(32), 0);
        editText.setLayoutParams(editTextparams);
        editText.setTextSize(16);

        layout.addView(editText);
    }

    public void addTextViewAndEditText(String text, String hint) {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        addTextView(layout, text, 16, 16);
        addEditText(layout, hint, 64, -32);

        addLineSeparator();

    }

    private void horizontalScrollView(LinearLayout mainLayout, LinearLayout scrollableLayout) {

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this.getContext());
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        horizontalScrollView.setLayoutParams(scrollParams);
        horizontalScrollView.addView(scrollableLayout);

        mainLayout.addView(horizontalScrollView);
    }

    private void addButtons(LinearLayout layout, List<String> categoryList) {

        final LinearLayout buttonsLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams buttonsParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setLayoutParams(buttonsParams);

        horizontalScrollView(layout, buttonsLayout);

        for (final Object i : categoryList) {
            Button btn = new Button(this.getContext());
            btn.setText(String.valueOf(i));
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            btnparams.setMargins(Utils.convertDpToPixel(8), Utils.convertDpToPixel(8), 0, 0);
            btn.setLayoutParams(btnparams);
            buttonsLayout.addView(btn);

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    AddEventSubCategoryFragment fragment = new AddEventSubCategoryFragment();
                    btnString = String.valueOf(i);
                    if (!clicked){
                        addFragment(fragment);
                        clicked = true;
                    } else {
                        fragment = (AddEventSubCategoryFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                        removeFragment(fragment);
                        clicked = false;
                    }
                }
            });
        }
    }

    private void addTextViewAndButtons(String text, final List<String> categoryList) {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        addTextView(layout, text, 16, 16);
        addButtons(layout, categoryList);

        addLineSeparator();
    }

    public void addDatePickerDialog() {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        Calendar cal = Calendar.getInstance();
        final int bYear = cal.get(Calendar.YEAR);
        final int bMonth = cal.get(Calendar.MONTH);
        final int bDay = cal.get(Calendar.DAY_OF_MONTH);

        addTextView(layout, "When?", 16, 16);

        Button btn = new Button(this.getContext());
        btn.setText(new StringBuilder().append(bDay).append("/").append(bMonth + 1).append("/").append(bYear));
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnparams.setMargins(Utils.convertDpToPixel(64), Utils.convertDpToPixel(-33), 0, 0);
        btn.setLayoutParams(btnparams);
        btn.setBackgroundColor(000000);
        layout.addView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                //btn.setText(selectedDate);
            }
        });

        addLineSeparator();

    }

    public void addTimePickerDialog(LinearLayout layout, StringBuilder time, String text, Integer marginLeft, Integer marginTop) {

        layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        addTextView(layout, text, marginLeft, marginTop);

        Button btn = new Button(this.getContext());
        btn.setText(time);
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnparams.setMargins(Utils.convertDpToPixel(marginLeft + 72), Utils.convertDpToPixel(-33), 0, 0);
        btn.setLayoutParams(btnparams);
        btn.setBackgroundColor(000000);
        layout.addView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });
    }

    public void addTimePickersDialog() {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.addView(layout);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        addTextView(layout, "At what time?", 16, 16);
        addTimePickerDialog(layout, new StringBuilder().append(hour).append(":").append(minutes), "Starting time:", 24, 16);
        addTimePickerDialog(layout, new StringBuilder().append(hour + 1).append(":").append(minutes), "Ending time:", 24, 16);

        addLineSeparator();
    }

    private void addCheckBox(String text) {

        LinearLayout checkBoxLayout = new LinearLayout(this.getContext());
        checkBoxLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(checkBoxLayout);

        CheckBox checkBox = new CheckBox(this.getContext());
        checkBox.setText(text);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(16), Utils.convertDpToPixel(16), 0);
        checkBox.setLayoutParams(params);
        checkBoxLayout.addView(checkBox);

        addLineSeparator();
    }

    public void addEventButton(){

        LinearLayout buttonLayout = new LinearLayout(this.getContext());
        buttonLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(buttonLayout);

        Button btn = new Button(this.getContext());
        btn.setText("Add event");
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnparams.setMargins(Utils.convertDpToPixel(128), Utils.convertDpToPixel(448), 0, 0);
        btn.setLayoutParams(btnparams);
        buttonLayout.addView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //
            }
        });
    }

    private void addLineSeparator() {
        LinearLayout lineLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
        params.setMargins(0, Utils.convertDpToPixel(10), 0, Utils.convertDpToPixel(10));
        lineLayout.setLayoutParams(params);
        linearLayout.addView(lineLayout);
    }

    public void addFragment(Fragment fragment){

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragment.setArguments(createParamsEventsFragment());
        transaction.add(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void removeFragment(Fragment fragment){

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(fragment);
        transaction.commit();
    }

    private Bundle createParamsEventsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("btnString", btnString);
        return bundle;
    }

}