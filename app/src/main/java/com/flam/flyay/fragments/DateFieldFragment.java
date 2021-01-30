package com.flam.flyay.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.flam.flyay.R;
import com.flam.flyay.model.InputField;
import com.flam.flyay.util.Utils;

import java.util.Calendar;

public class DateFieldFragment extends Fragment {

    private Button btnDate;
    private String selectedDate;

    private LinearLayout linearLayout;

    private String title;


    public DateFieldFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.date_field_fragment, container, false);

        linearLayout = view.findViewById(R.id.date_field_fragment);

        Bundle params = getArguments();

        title = params.getString("title");

        addDatePickerDialog();

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addDatePickerDialog() {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        Calendar calendar = Calendar.getInstance();
        final int bYear = calendar.get(Calendar.YEAR);
        final int bMonth = calendar.get(Calendar.MONTH);
        final int bDay = calendar.get(Calendar.DAY_OF_MONTH);

        addIcon(layout, R.drawable.ic_calendar, 16);
        addTextView(layout, title, 32, -22);

        btnDate = new Button(this.getContext());
        btnDate.setText(Utils.convertionFromDateToString(calendar.getTime()));
        btnDate.setTextSize(16);
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        int marginLeft = 0;
        if(title.length()>10)
            marginLeft = 191;
        else marginLeft = 91;

        btnparams.setMargins(Utils.convertDpToPixel(marginLeft), Utils.convertDpToPixel(-34), 0, 0);
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

    }

    private DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onDateSet(final DatePicker view, final int year, final int month, final int dayOfMonth) {

            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            selectedDate = Utils.convertionFromDateToString(c.getTime());
            btnDate.setText(selectedDate);
        }
    };

}
