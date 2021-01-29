package com.flam.flyay.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.flam.flyay.R;
import com.flam.flyay.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeFieldFragment extends Fragment {

    private Button btnStartTime;
    private String startTime;
    private Button btnEndTime;
    private String endTime;


    private LinearLayout linearLayout;


    public TimeFieldFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.time_field_fragment, container, false);

        linearLayout = view.findViewById(R.id.time_field_fragment);

        addTimePickersDialog();
        addCheckBox();

        return view;
    }


    public void addIcon(LinearLayout layout, Integer obj, Integer marginTop){
        ImageView image = new ImageView(this.getContext());
        image.setImageResource(obj);
        LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        imageParams.setMargins(Utils.convertDpToPixel(0), Utils.convertDpToPixel(marginTop), 0, 0);
        image.setBackgroundColor(Color.TRANSPARENT);
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
        textView.setLayoutParams(textParams);
        textView.setTextSize(16);

        layout.addView(textView);
    }

    public void addIconAndTextView(LinearLayout layout, Integer obj, String text) {

        addIcon(layout, obj, 16);
        addTextView(layout, text, 32, -22);

    }


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
        btnStartTime.setTextSize(16);
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnparams.setMargins(Utils.convertDpToPixel(88), Utils.convertDpToPixel(-34), 0, 0);
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

            try {
                Date timeStart = new SimpleDateFormat("hh:mm a").parse(startTime);
                Integer h = timeStart.getHours() + 1;
                endTime =  ((h > 12) ? h % 12 : h) + ":" + (minute < 10 ? ("0" + minute) : minute) + " " + ((h >= 12) ? "PM" : "AM");
                btnEndTime.setText(endTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }


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

        addTextView(layout, "End:", 32, 4);

        btnEndTime = new Button(this.getContext());
        btnEndTime.setText(time);
        btnEndTime.setTextSize(16);
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnparams.setMargins(Utils.convertDpToPixel(88), Utils.convertDpToPixel(-34), 0, 0);
        btnEndTime.setLayoutParams(btnparams);
        btnEndTime.setBackgroundColor(Color.TRANSPARENT);
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
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onTimeSet(final TimePicker view, final int hour, final int minute) {

            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY,hour);
            c.set(Calendar.MINUTE, minute);

            endTime =  ((hour > 12) ? hour % 12 : hour) + ":" + (minute < 10 ? ("0" + minute) : minute) + " " + ((hour >= 12) ? "PM" : "AM");
            btnEndTime.setText(endTime);

            if(!Utils.isEmptyOrBlank(startTime) && !Utils.isEmptyOrBlank(endTime)) {
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
                            public void onClick(DialogInterface dialog, int id) {
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

    public void addCheckBox() {

        LinearLayout checkBoxLayout = new LinearLayout(this.getContext());
        checkBoxLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(checkBoxLayout);

        final CheckBox checkBox = new CheckBox(this.getContext());
        checkBox.setText("All day");
        checkBox.setTextSize(16);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(Utils.convertDpToPixel(24), 0, 0, 0);
        checkBox.setLayoutParams(params);
        checkBoxLayout.addView(checkBox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnStartTime.setEnabled(false);
                    btnEndTime.setEnabled(false);
                } else {
                    btnStartTime.setEnabled(true);
                    btnEndTime.setEnabled(true);
                }
            }
        });

    }

}
