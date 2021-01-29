package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ButtonsFieldFragment extends Fragment {

    private LinearLayout linearLayout;

    private List<String> buttonsValue;

    private List<String> weekDays;
    private List<String> projectWith;
    private List<String> studyBy;
    private List<String> recapPer;


    public ButtonsFieldFragment() {}

    @SuppressLint({"LongLogTag", "ResourceType"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.buttons_field_fragment, container, false);

        linearLayout = view.findViewById(R.id.buttons_field_fragment);

        weekDays = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
        projectWith = Arrays.asList("by myself", "with friend");
        studyBy = Arrays.asList("Topics", "Pages");
        recapPer = Arrays.asList("Main", "Sub");

        Bundle arguments = getArguments();
        buttonsValue = new ArrayList<String>(Arrays.asList(arguments.getString("buttonsValue")
                .replace("[","").replace("]","")
                .split(",")));
        Log.d(".ButtonsFieldFragment", buttonsValue.toString());


        addButtons(buttonsValue);

        return view;
    }


    public void horizontalScrollView(LinearLayout scrollableLayout) {

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this.getContext());
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        scrollParams.setMargins(Utils.convertDpToPixel(32), 0, 0, 0);
        horizontalScrollView.setLayoutParams(scrollParams);
        horizontalScrollView.addView(scrollableLayout);

        linearLayout.addView(horizontalScrollView);
    }

    public void addButtons(final List<String> list) {

        final LinearLayout buttonsLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams buttonsParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setLayoutParams(buttonsParams);
        
        horizontalScrollView(buttonsLayout);

        final List<Button> buttons = new ArrayList<>();

        for (final Object i : list) {
            final Button btn = new Button(this.getContext());
            btn.setText(String.valueOf(i));
            buttons.add(btn);
            btn.setTextSize(16);
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );
            btnparams.setMargins(0, 0, 0, 0);
            btn.setLayoutParams(btnparams);
            btn.setBackgroundColor(Color.TRANSPARENT);
            buttonsLayout.addView(btn);

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Drawable background = btn.getBackground();
                    int color = Color.TRANSPARENT;

                    if(background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();

                    clearOtherButtonBackground(buttons, btn);
                    if(color == Color.TRANSPARENT) {
                        btn.setBackgroundColor(Color.parseColor(CategoryEnum.STUDY.color));
                        linearLayout.setVisibility(View.VISIBLE);
                    } else {
                        btn.setBackgroundColor(Color.TRANSPARENT);
                    }
                }

            });
        }
    }

    private void clearOtherButtonBackground(List<Button> buttons, Button excluding) {
        for(Button btn: buttons){
            if(btn != excluding)
                btn.setBackgroundColor(Color.TRANSPARENT);
        }
    }


}
