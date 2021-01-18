package com.flam.flyay.fragments;

import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.flam.flyay.AddEventActivity;
import com.flam.flyay.R;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.SubCategoryEnum;
import com.flam.flyay.util.Utils;

import java.util.ArrayList;
import java.util.List;


public class AddEventFormFragment extends Fragment {

    private RelativeLayout relativeLayout;
    private List<String> categoryList;
    private List<String> wellnessSubCategoryList;
    private List<String> financesSubCategoryList;
    private List<String> freeTimeSubCategoryList;
    private List<String> studySubCategoryList;
    private List<String> festivitySubCategoryList;

    private List<String> overRangeList;

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

        wellnessSubCategoryList = new ArrayList();
        wellnessSubCategoryList.add(SubCategoryEnum.BODY_CARE.name);
        wellnessSubCategoryList.add(SubCategoryEnum.MEDICINES.name);
        wellnessSubCategoryList.add(SubCategoryEnum.MED_APPOINTMENT.name);

        financesSubCategoryList = new ArrayList();
        financesSubCategoryList.add(SubCategoryEnum.REVENUE.name);
        financesSubCategoryList.add(SubCategoryEnum.OUTFLOW.name);

        freeTimeSubCategoryList = new ArrayList();
        freeTimeSubCategoryList.add(SubCategoryEnum.FRIENDS.name);
        freeTimeSubCategoryList.add(SubCategoryEnum.FAMILY.name);
        freeTimeSubCategoryList.add(SubCategoryEnum.HOBBY.name);
        freeTimeSubCategoryList.add(SubCategoryEnum.TRAVELS.name);
        freeTimeSubCategoryList.add(SubCategoryEnum.FILMS_TV_SERIES.name);
        freeTimeSubCategoryList.add(SubCategoryEnum.THEATRE.name);
        freeTimeSubCategoryList.add(SubCategoryEnum.MUSIC.name);
        freeTimeSubCategoryList.add(SubCategoryEnum.SPORTIVE_EVENTS.name);
        freeTimeSubCategoryList.add(SubCategoryEnum.SPORT.name);
        freeTimeSubCategoryList.add(SubCategoryEnum.OTHER.name);

        studySubCategoryList = new ArrayList();
        studySubCategoryList.add(SubCategoryEnum.EXAM.name);
        studySubCategoryList.add(SubCategoryEnum.STUDY_TIME.name);
        studySubCategoryList.add(SubCategoryEnum.LESSONS.name);
        studySubCategoryList.add(SubCategoryEnum.TEACHERS_OFFICE_HOURS.name);
        studySubCategoryList.add(SubCategoryEnum.STUDY_GROUP.name);
        studySubCategoryList.add(SubCategoryEnum.INTERSHIP.name);

        festivitySubCategoryList  = new ArrayList();
        festivitySubCategoryList.add(SubCategoryEnum.BIRTHDAY.name);
        festivitySubCategoryList.add(SubCategoryEnum.LONG_WEEKEND.name);
        festivitySubCategoryList.add(SubCategoryEnum.HOLIDAYS.name);


        overRangeList = new ArrayList();
        overRangeList.add("Morning");
        overRangeList.add("Afternoon");
        overRangeList.add("Evening");

        relativeLayout = view.findViewById(R.id.add_event_form);

        addTextViewAndEditText("Title: ", "Insert here");
        addTextViewAndButtons("Which category?", 64, categoryList);
        //addCheckBoxes("Over range", overRangeList);
        addDatePickerDialog();
        addTimePickerDialog();

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

        addLineSeparator();
    }

    private void addTextView(LinearLayout layout, String text, Integer marginTop) {

        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        textParams.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(marginTop),0, 0);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(textParams);

        layout.addView(textView);

    }

    private void horizontalScrollView(LinearLayout mainLayout, LinearLayout scrollableLayout){
        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this.getContext());
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        horizontalScrollView.setLayoutParams(scrollParams);
        horizontalScrollView.addView(scrollableLayout);
        mainLayout.addView(horizontalScrollView);

    }

    private void addButtons(final LinearLayout layout, List<String> categoryList) {

        final LinearLayout buttonsLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams buttonsParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setLayoutParams(buttonsParams);

        horizontalScrollView(layout, buttonsLayout);

        for (Object i : categoryList) {
            Button btn = new Button(this.getContext());
            btn.setText(String.valueOf(i));
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            btnparams.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(8),0, 0);
            btn.setLayoutParams(btnparams);
            buttonsLayout.addView(btn);

            if (i.toString() == CategoryEnum.WELLNESS.name) {
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        addTextViewAndButtons("Which subcategory?",144, wellnessSubCategoryList);

                    }
                });
            }
            if (i.toString() == CategoryEnum.FINANCES.name) {
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        addTextViewAndButtons("Which subcategory?",144, financesSubCategoryList);
                    }
                });
            }
            if (i.toString() == CategoryEnum.FREE_TIME.name) {
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        addTextViewAndButtons("Which subcategory?",144, freeTimeSubCategoryList);
                    }
                });
            }
            if (i.toString() == CategoryEnum.STUDY.name) {
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        addTextViewAndButtons("Which subcategory?",144, studySubCategoryList);
                    }
                });
            }
            if (i.toString() == CategoryEnum.FESTIVITY.name) {
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        addTextViewAndButtons("Subcategory",144, festivitySubCategoryList);
                    }
                });
            }
        }
        addLineSeparator();
    }


    private void addTextViewAndButtons(String text, Integer marginTop, final List<String> categoryList) {

        LinearLayout textAndButtons = new LinearLayout(this.getContext());
        textAndButtons.setOrientation(LinearLayout.VERTICAL);
        relativeLayout.addView(textAndButtons);

        addTextView(textAndButtons, text, marginTop);
        addButtons(textAndButtons, categoryList);


        addLineSeparator();
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

        addLineSeparator();
    }

    private void addLineSeparator() {
        LinearLayout lineLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,2);
        params.setMargins(0, Utils.convertDpToPixel(10), 0, Utils.convertDpToPixel(10));
        lineLayout.setLayoutParams(params);
        relativeLayout.addView(lineLayout);
    }

    public void addDatePickerDialog() {

        Button btn = new Button(this.getContext());
        btn.setText("Choose the date");
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnparams.setMargins(Utils.convertDpToPixel(160), Utils.convertDpToPixel(160),0, 0);
        btn.setLayoutParams(btnparams);
        relativeLayout.addView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

            }
        });
    }

    public void addTimePickerDialog() {

        Button btn = new Button(this.getContext());
        btn.setText("Choose the time");
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnparams.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(160),0, 0);
        btn.setLayoutParams(btnparams);
        relativeLayout.addView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");

            }
        });
    }



}
