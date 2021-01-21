package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.AddEventActivity;
import com.flam.flyay.R;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AddEventFormFragment extends Fragment {

    private String btnString;
    private Boolean clicked;

    private Button btnDate;
    private String selectedDate;

    private Button btnStartTime;
    private String startTime;
    private Button btnEndTime;
    private String endTime;


    private LinearLayout linearLayout;

    private List<String> categoryList;

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

        categoryList = Arrays.asList(CategoryEnum.FREE_TIME.name, CategoryEnum.STUDY.name, CategoryEnum.WELLNESS.name,
                CategoryEnum.FESTIVITY.name, CategoryEnum.FINANCES.name);

        linearLayout = view.findViewById(R.id.add_event_form);

        addLineSeparator();
        addIconAndEditText(R.drawable.ic_title,"Title");
        addTextViewAndButtons(R.drawable.ic_category, "Which category?", categoryList);
        addDatePickerDialog();
        addTimePickersDialog();
        addCheckBox("All day");
        addCheckBox("Periodic event");
        addIconAndEditText(R.drawable.ic_position,"Where?");
        addIconAndEditText(R.drawable.ic_notes,"Note");
        addEventButton();
        return view;
    }

    public void addIcon(LinearLayout layout, Integer obj, Integer marginLeft, Integer marginTop){
        ImageView image = new ImageView(this.getContext());
        image.setImageResource(obj);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        imageParams.setMargins(Utils.convertDpToPixel(marginLeft), Utils.convertDpToPixel(marginTop), 0, 0);
        image.setBackgroundColor(Color.WHITE );
        image.setLayoutParams(imageParams);

        layout.addView(image);
    }

    public void addTextView(LinearLayout layout, String text, Integer marginLeft, Integer marginTop) {

        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textParams.setMargins(Utils.convertDpToPixel(marginLeft), Utils.convertDpToPixel(marginTop), 0, 0);
        textView.setTextColor(Color.BLACK);
        textView.setLayoutParams(textParams);

        layout.addView(textView);
    }

    public void addEditText(LinearLayout layout, String hint, Integer marginLeft, Integer marginTop) {

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

    public void addIconAndTextView(LinearLayout layout, Integer obj, String text) {

        addIcon(layout, obj, 16, 16);
        addTextView(layout, text, 56, -19);

        addLineSeparator();

    }

    public void addIconAndEditText(Integer obj, String hint) {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        addIcon(layout, obj, 16, 16);
        addEditText(layout, hint, 48, -32);

        addLineSeparator();

    }

    public void horizontalScrollView(LinearLayout mainLayout, LinearLayout scrollableLayout) {

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this.getContext());
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        horizontalScrollView.setLayoutParams(scrollParams);
        horizontalScrollView.addView(scrollableLayout);

        mainLayout.addView(horizontalScrollView);
    }

    public void addButtons(LinearLayout layout, List<String> categoryList) {

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
            btn.setBackgroundColor(Color.TRANSPARENT);
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

    public void addTextViewAndButtons(Integer obj, String text, final List<String> categoryList) {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        addIconAndTextView(layout, obj, text);
        addButtons(layout, categoryList);

        addLineSeparator();
    }

    public void addDatePickerDialog() {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        Calendar calendar = Calendar.getInstance();
        final int bYear = calendar.get(Calendar.YEAR);
        final int bMonth = calendar.get(Calendar.MONTH);
        final int bDay = calendar.get(Calendar.DAY_OF_MONTH);

        addIcon(layout, R.drawable.ic_calendar, 16, 16);
        addTextView(layout, "When?", 56, -19);

        btnDate = new Button(this.getContext());
        btnDate.setText(new StringBuilder().append(bDay).append("/").append(bMonth + 1).append("/").append(bYear));
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnparams.setMargins(Utils.convertDpToPixel(104), Utils.convertDpToPixel(-33), 0, 0);
        btnDate.setLayoutParams(btnparams);
        btnDate.setBackgroundColor(Color.TRANSPARENT);
        layout.addView(btnDate);

        btnDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DatePickerFragment dialogFragment = new DatePickerFragment().newInstance();
                dialogFragment.setCallBack(onDate);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });

        addLineSeparator();

    }

    private DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(final DatePicker view, final int year, final int month, final int dayOfMonth) {

            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            selectedDate = (new StringBuilder().append(dayOfMonth).append("/").append(month + 1).append("/").append(year)).toString();
            btnDate.setText(selectedDate);
        }
    };

    public void addStartTimePickerDialog(LinearLayout layout) {

        layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String time = ((hour > 12) ? hour % 12 : hour) + ":" + (minute < 10 ? ("0" + minute) : minute) + " " + ((hour >= 12) ? "PM" : "AM");

        addTextView(layout, "Start:", 32, 16);

        btnStartTime = new Button(this.getContext());
        btnStartTime.setText(time);
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnparams.setMargins(Utils.convertDpToPixel(72), Utils.convertDpToPixel(-33), 0, 0);
        btnStartTime.setLayoutParams(btnparams);
        btnStartTime.setBackgroundColor(Color.TRANSPARENT);
        layout.addView(btnStartTime);

        btnStartTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TimePickerFragment dialogFragment = new TimePickerFragment().newInstance();
                dialogFragment.setCallBack(onTimeStart);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });
    }

    private TimePickerDialog.OnTimeSetListener onTimeStart = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(final TimePicker view, final int hour, final int minute) {

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY,hour);
            c.set(Calendar.MINUTE, minute);

            startTime =  ((hour > 12) ? hour % 12 : hour) + ":" + (minute < 10 ? ("0" + minute) : minute) + " " + ((hour >= 12) ? "PM" : "AM");
            btnStartTime.setText(startTime);
        }
    };

    public void addEndTimePickerDialog(LinearLayout layout) {

        layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String time = (((hour+1) > 12) ? (hour+1) % 12 : (hour+1)) + ":" + (minute < 10 ? ("0" + minute) : minute) + " " + (((hour+1) >= 12) ? "PM" : "AM");

        addTextView(layout, "End:", 32, 16);

        btnEndTime = new Button(this.getContext());
        btnEndTime.setText(time);
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnparams.setMargins(Utils.convertDpToPixel(72), Utils.convertDpToPixel(-33), 0, 0);
        btnEndTime.setLayoutParams(btnparams);
        btnEndTime.setBackgroundColor(000000);
        layout.addView(btnEndTime);

        btnEndTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TimePickerFragment dialogFragment = new TimePickerFragment().newInstance();
                dialogFragment.setCallBack(onTimeEnd);
                dialogFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });
    }

    private TimePickerDialog.OnTimeSetListener onTimeEnd = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(final TimePicker view, final int hour, final int minute) {

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY,hour);
            c.set(Calendar.MINUTE, minute);

            endTime =  ((hour > 12) ? hour % 12 : hour) + ":" + (minute < 10 ? ("0" + minute) : minute) + " " + ((hour >= 12) ? "PM" : "AM");
            btnEndTime.setText(endTime);

            try {
                Date timeS = new SimpleDateFormat("hh:mm a").parse(startTime);
                Date timeE = new SimpleDateFormat("hh:mm a").parse(endTime);
                Log.d(".AddEventFormFragment", timeS.toString());
                Log.d(".AddEventFormFragment", timeE.toString());
                if (timeE.getTime() < timeS.getTime()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Check your schedules!\nThe end is expected before the start of your event");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            } catch (ParseException parseException) {
                parseException.printStackTrace();
            }
        }
    };

    public void addTimePickersDialog() {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        LinearLayout internalLayout = new LinearLayout(this.getContext());
        internalLayout.setOrientation(LinearLayout.HORIZONTAL);

        addStartTimePickerDialog(internalLayout);
        addEndTimePickerDialog(internalLayout);

        addIconAndTextView(layout, R.drawable.ic_clock, "At what time?");
        layout.addView(internalLayout);

    }


    public void addCheckBox(String text) {

        LinearLayout checkBoxLayout = new LinearLayout(this.getContext());
        checkBoxLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(checkBoxLayout);

        final CheckBox checkBox = new CheckBox(this.getContext());
        checkBox.setText(text);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(16), Utils.convertDpToPixel(16), 0);
        checkBox.setLayoutParams(params);
        checkBoxLayout.addView(checkBox);
        if(checkBox.getText().equals("Periodic event")){
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    AddEventPeriodicFragment fragment = new AddEventPeriodicFragment();
                    if (checkBox.isChecked()) {
                        addFragment(fragment);
                    } else {
                        fragment = (AddEventPeriodicFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                        removeFragment(fragment);
                    }
                }
            });
        }

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
        btnparams.setMargins(Utils.convertDpToPixel(128), Utils.convertDpToPixel(16), 0, 0);
        btn.setLayoutParams(btnparams);
        buttonLayout.addView(btn);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //add event
            }
        });
    }

    public void addLineSeparator() {
        LinearLayout lineLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
        params.setMargins(0, Utils.convertDpToPixel(3), 0, Utils.convertDpToPixel(3));
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