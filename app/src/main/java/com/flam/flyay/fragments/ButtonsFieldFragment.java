package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.flam.flyay.R;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ButtonsFieldFragment extends Fragment {

    private LinearLayout linearLayout;

    private String typeOfList;
    private String keyToSetValue;
    private int colorCategory;

    private List<Button> activeButtons;
    private List<String> initialValues;
    private List<Button> buttons;


    public ButtonsFieldFragment() {}

    @SuppressLint({"LongLogTag", "ResourceType"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.buttons_field_fragment, container, false);

        linearLayout = view.findViewById(R.id.buttons_field_fragment);
        TextView title = view.findViewById(R.id.button_group_title);

        Bundle arguments = getArguments();

        String titleParam = arguments.getString("title");
        typeOfList = arguments.getString("typeOfList");
        colorCategory = arguments.getInt("color");
        keyToSetValue = arguments.getString("key");
        initialValues = (List<String>) arguments.getStringArrayList("initialValues");

        if(initialValues != null)
            Log.d(".ButtonField", "initialValues: " + initialValues);

        activeButtons = new ArrayList<>();

        title.setText(titleParam);

        List<String> list = new ArrayList<>();
        switch (typeOfList) {
            case "weekDays":
                list = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
                break;
            case "projectWith":
                list = Arrays.asList("by myself", "with friend");
                break;
            case "studyBy":
                list = Arrays.asList("Topics", "Pages");
                break;
            case "recapPer":
                list = Arrays.asList("Main", "Sub");
                break;
        }
        Log.d(".ButtonGroup", "list received: " + list.toString());
        addButtons(list);

        return view;
    }

    public void horizontalScrollView(LinearLayout scrollableLayout) {

        HorizontalScrollView horizontalScrollView = new HorizontalScrollView(this.getContext());
        LinearLayout.LayoutParams scrollParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );

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

        buttons = new ArrayList<>();

        for (final Object i : list) {
            final Button btn = new Button(this.getContext());
            btn.setText(String.valueOf(i));
            buttons.add(btn);
            btn.setTextSize(16);
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            );

            if(list.size() == 2){
                Log.d(".ButtonsField", String.valueOf(list.size()));
                btnparams.setMargins(Utils.convertDpToPixel(50), 0,0,0);
            }

            btn.setLayoutParams(btnparams);
            btn.setBackgroundColor(Color.TRANSPARENT);
            buttonsLayout.addView(btn);

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    Drawable background = btn.getBackground();
                    int color = Color.TRANSPARENT;

                    FragmentManager fm = getFragmentManager();
                    AddEventFormFragment dynamicFormFragment = (AddEventFormFragment)fm.findFragmentById(R.id.fragment_container);

                    if(background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();

                    if(!typeOfList.equalsIgnoreCase("weekDays")) {
                        clearOtherButtonBackground(buttons, btn);
                    }

                    if(color == Color.TRANSPARENT) {
                        btn.setBackgroundColor(colorCategory);
                        linearLayout.setVisibility(View.VISIBLE);

                        activeButtons.add(btn);
                        Log.d(".ButtonsField", "ADD OPERATION; activeButtons => " + activeButtons.toString());

                        if(!typeOfList.equalsIgnoreCase("weekDays")) {
                            dynamicFormFragment.setValueInputField(keyToSetValue, btn.getText().toString());
                        } else {
                            List<String> newValue = new ArrayList<>();
                            for(Button button : activeButtons)
                                newValue.add(button.getText().toString());
                            Log.d(".ButtonsField", "weekDays value: " + activeButtons.toString());
                            dynamicFormFragment.setValueInputField(keyToSetValue, newValue);
                        }

                        if(btn.getText().toString().equalsIgnoreCase("Pages")) {
                            dynamicFormFragment.toggleTopicPagesForm("P",true);
                            dynamicFormFragment.toggleTopicPagesForm("T",false);
                        }
                        else if(btn.getText().toString().equalsIgnoreCase("Topics")) {
                            dynamicFormFragment.toggleTopicPagesForm("T",true);
                            dynamicFormFragment.toggleTopicPagesForm("P",false);
                        }

                    } else {
                        btn.setBackgroundColor(Color.TRANSPARENT);
                        activeButtons.remove(btn);
                        Log.d(".ButtonsField", "DELETE OPERATION; activeButtons => " + activeButtons.toString());

                        if(!typeOfList.equalsIgnoreCase("weekDays")) {
                            dynamicFormFragment.setValueInputField(keyToSetValue, null);
                        } else {
                            List<String> newValue = new ArrayList<>();
                            for(Button button : activeButtons)
                                newValue.add(button.getText().toString());
                            Log.d(".ButtonsField", "weekDays value: " + activeButtons.toString());
                            dynamicFormFragment.setValueInputField(keyToSetValue, newValue);
                        }

                        if(btn.getText().toString().equalsIgnoreCase("Pages"))
                            dynamicFormFragment.toggleTopicPagesForm("P",false);
                        else if(btn.getText().toString().equalsIgnoreCase("Topics"))
                            dynamicFormFragment.toggleTopicPagesForm("T",false);
                    }
                }

            });

            if(initialValues != null) {
                for(String initialValue: initialValues) {
                    if(initialValue.equalsIgnoreCase(btn.getText().toString())) {
                        btn.setBackgroundColor(colorCategory);
                        activeButtons.add(btn);
                    }
                }
            }
        }
    }

    private void clearOtherButtonBackground(List<Button> buttons, Button excluding) {
        for(Button btn: buttons){
            if(btn != excluding)
                btn.setBackgroundColor(Color.TRANSPARENT);
        }
    }


}
