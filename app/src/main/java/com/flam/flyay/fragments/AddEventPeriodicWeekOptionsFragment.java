package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.flam.flyay.R;
import com.flam.flyay.util.Utils;

import java.util.Arrays;
import java.util.List;

public class AddEventPeriodicWeekOptionsFragment extends Fragment {

    private LinearLayout linearLayout;
    private List<String> weekDays;

    public AddEventPeriodicWeekOptionsFragment() {}

    @SuppressLint({"LongLogTag", "ResourceType"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_event_periodic_week_options_fragment, container, false);

        linearLayout = view.findViewById(R.id.add_event_periodic_week_options_fragment);

        weekDays = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
        addButtons(linearLayout, weekDays);

        return view;
    }


    public void horizontalScrollView(LinearLayout mainLayout, LinearLayout scrollableLayout) {

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this.getContext());
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        scrollParams.setMargins(Utils.convertDpToPixel(32), 0, 0, 0);
        horizontalScrollView.setLayoutParams(scrollParams);
        horizontalScrollView.addView(scrollableLayout);

        mainLayout.addView(horizontalScrollView);
    }

    public void addButtons(LinearLayout layout, List<String> list) {

        final LinearLayout buttonsLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams buttonsParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setLayoutParams(buttonsParams);

        horizontalScrollView(layout, buttonsLayout);

        for (final Object i : list) {
            Button btn = new Button(this.getContext());
            btn.setText(String.valueOf(i));
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                    Utils.convertDpToPixel(60),
                    Utils.convertDpToPixel(48)
            );
            btnparams.setMargins(0, 0, 0, 0);
            btn.setLayoutParams(btnparams);
            //btn.setBackgroundColor(Color.TRANSPARENT);
            buttonsLayout.addView(btn);
        }
    }


}
