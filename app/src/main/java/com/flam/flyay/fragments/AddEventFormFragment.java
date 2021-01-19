package com.flam.flyay.fragments;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.AddEventActivity;
import com.flam.flyay.R;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.SubCategoryEnum;
import com.flam.flyay.util.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AddEventFormFragment extends Fragment {

    private String selectedDate;

    private RelativeLayout relativeLayout;

    private List<String> categoryList;
    private List<String> wellnessSubCategoryList;
    private List<String> financesSubCategoryList;
    private List<String> freeTimeSubCategoryList;
    private List<String> studySubCategoryList;
    private List<String> festivitySubCategoryList;

    private List<String> overRangeList;

    public AddEventFormFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_event_form_fragment, container, false);

        ((AddEventActivity) getActivity()).getSupportActionBar().setTitle("      Add event");
        ((AddEventActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.ic_add_event);
        ((AddEventActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        Bundle arguments = getArguments();
        if (arguments != null) {
            selectedDate = arguments.getString("selectedDate");
            Log.d(".AddEventFormFragment", selectedDate);
        }

        categoryList = Arrays.asList(CategoryEnum.STUDY.name, CategoryEnum.FREE_TIME.name, CategoryEnum.WELLNESS.name,
                CategoryEnum.FESTIVITY.name, CategoryEnum.FINANCES.name);

        wellnessSubCategoryList = Arrays.asList(SubCategoryEnum.BODY_CARE.name, SubCategoryEnum.MEDICINES.name, SubCategoryEnum.MED_APPOINTMENT.name);

        financesSubCategoryList = Arrays.asList(SubCategoryEnum.REVENUE.name, SubCategoryEnum.OUTFLOW.name);

        freeTimeSubCategoryList = Arrays.asList(SubCategoryEnum.FRIENDS.name, SubCategoryEnum.FAMILY.name, SubCategoryEnum.HOBBY.name,
                SubCategoryEnum.TRAVELS.name, SubCategoryEnum.FILMS_TV_SERIES.name, SubCategoryEnum.THEATRE.name, SubCategoryEnum.MUSIC.name,
                SubCategoryEnum.SPORTIVE_EVENTS.name, SubCategoryEnum.SPORT.name, SubCategoryEnum.OTHER.name);

        studySubCategoryList = Arrays.asList(SubCategoryEnum.EXAM.name, SubCategoryEnum.STUDY_TIME.name, SubCategoryEnum.LESSONS.name,
                SubCategoryEnum.TEACHERS_OFFICE_HOURS.name, SubCategoryEnum.STUDY_GROUP.name, SubCategoryEnum.INTERSHIP.name);

        festivitySubCategoryList = Arrays.asList(SubCategoryEnum.BIRTHDAY.name, SubCategoryEnum.LONG_WEEKEND.name, SubCategoryEnum.HOLIDAYS.name);

        overRangeList = Arrays.asList("Morning", "Afternoon", "Evening");


        relativeLayout = view.findViewById(R.id.add_event_form);

        addTextViewAndEditText("Title: ", "Insert here", 16);
        addTextViewAndButtons("Which category?", 64, categoryList);
        addDatePickerDialog();
        addTimePickersDialog();
        addCheckBox("Periodic event", 288);
        addTextViewAndEditText("Where?", "Insert here", 352);
        addTextViewAndEditText("Note:", "Insert here", 400);
        addButton();

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
        editTextparams.setMargins(Utils.convertDpToPixel(marginLeft), Utils.convertDpToPixel(marginTop),0, 0);
        editText.setLayoutParams(editTextparams);
        editText.setTextSize(16);

        layout.addView(editText);
    }

    public void addTextViewAndEditText(String text, String hint, Integer marginTop) {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        relativeLayout.addView(layout);

        addTextView(layout, text, 16, marginTop);
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
            btnparams.setMargins(Utils.convertDpToPixel(8), Utils.convertDpToPixel(8), 0, 0);
            btn.setLayoutParams(btnparams);
            btn.setBackgroundColor(000000);
            buttonsLayout.addView(btn);

            if (i.toString() == CategoryEnum.WELLNESS.name) {
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        addTextViewAndButtons("Which subcategory?", 144, wellnessSubCategoryList);

                    }
                });
            }
            if (i.toString() == CategoryEnum.FINANCES.name) {
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        addTextViewAndButtons("Which subcategory?", 144, financesSubCategoryList);
                    }
                });
            }
            if (i.toString() == CategoryEnum.FREE_TIME.name) {
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        addTextViewAndButtons("Which subcategory?", 144, freeTimeSubCategoryList);
                    }
                });
            }
            if (i.toString() == CategoryEnum.STUDY.name) {
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        addTextViewAndButtons("Which subcategory?", 144, studySubCategoryList);
                    }
                });
            }
            if (i.toString() == CategoryEnum.FESTIVITY.name) {
                btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        addTextViewAndButtons("Subcategory", 144, festivitySubCategoryList);
                    }
                });
            }
        }
    }

    private void addTextViewAndButtons(String text, Integer marginTop, final List<String> categoryList) {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        relativeLayout.addView(layout);

        addTextView(layout, text, 16, marginTop);
        addButtons(layout, categoryList);

        addLineSeparator();
    }

    public void addDatePickerDialog() {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        relativeLayout.addView(layout);

        Calendar cal = Calendar.getInstance();
        final int bYear = cal.get(Calendar.YEAR);
        final int bMonth = cal.get(Calendar.MONTH);
        final int bDay = cal.get(Calendar.DAY_OF_MONTH);

        addTextView(layout, "When?", 16, 176);

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
        relativeLayout.addView(layout);

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
        relativeLayout.addView(layout);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        addTextView(layout, "At what time?", 16, 224);
        addTimePickerDialog(layout, new StringBuilder().append(hour).append(":").append(minutes), "Starting time:", 24, 256);
        addTimePickerDialog(layout, new StringBuilder().append(hour + 1).append(":").append(minutes), "Ending time:", 184, 256);

        addLineSeparator();
    }

    private void addCheckBox(String text, Integer marginTop) {

        LinearLayout checkBoxLayout = new LinearLayout(this.getContext());
        checkBoxLayout.setOrientation(LinearLayout.VERTICAL);
        relativeLayout.addView(checkBoxLayout);

        CheckBox checkBox = new CheckBox(this.getContext());
        checkBox.setText(text);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(marginTop), Utils.convertDpToPixel(16), 0);
        checkBox.setLayoutParams(params);
        checkBoxLayout.addView(checkBox);

        addLineSeparator();
    }

    public void addButton(){

        LinearLayout buttonLayout = new LinearLayout(this.getContext());
        buttonLayout.setOrientation(LinearLayout.VERTICAL);
        relativeLayout.addView(buttonLayout);

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
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
                //btn.setText(selectedDate);
            }
        });
    }

    private void addLineSeparator() {
        LinearLayout lineLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
        params.setMargins(0, Utils.convertDpToPixel(10), 0, Utils.convertDpToPixel(10));
        lineLayout.setLayoutParams(params);
        relativeLayout.addView(lineLayout);
    }

}