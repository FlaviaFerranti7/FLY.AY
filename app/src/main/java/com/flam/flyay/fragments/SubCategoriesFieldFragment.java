package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.flam.flyay.R;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.SubCategoryEnum;
import com.flam.flyay.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubCategoriesFieldFragment extends Fragment {

    private LinearLayout linearLayout;
    private String btnString;
    private String initialSubcategory;

    private List<String> freeTimeSubCategoryList;
    private List<String> studySubCategoryList;
    private List<String> wellnessSubCategoryList;
    private List<String> festivitySubCategoryList;
    private List<String> financesSubCategoryList;


    public SubCategoriesFieldFragment() {}

    @SuppressLint({"LongLogTag", "ResourceType"})
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.sub_categories_field_fragment, container, false);

        linearLayout = view.findViewById(R.id.sub_categories_field_fragment);

        Bundle arguments = getArguments();
        btnString = arguments.getString("btnString");
        initialSubcategory = arguments.getString("initialSubcategory");

        freeTimeSubCategoryList = Arrays.asList(SubCategoryEnum.FRIENDS.name, SubCategoryEnum.FAMILY.name, SubCategoryEnum.HOBBY.name,
                SubCategoryEnum.TRAVELS.name, SubCategoryEnum.FILMS_TV_SERIES.name, SubCategoryEnum.THEATRE.name, SubCategoryEnum.MUSIC.name,
                SubCategoryEnum.SPORTIVE_EVENTS.name, SubCategoryEnum.SPORT.name, SubCategoryEnum.OTHER.name);

        studySubCategoryList = Arrays.asList(SubCategoryEnum.EXAM.name, SubCategoryEnum.STUDY_TIME.name, SubCategoryEnum.LESSONS.name,
                SubCategoryEnum.TEACHERS_OFFICE_HOURS.name, SubCategoryEnum.STUDY_GROUP.name, SubCategoryEnum.INTERSHIP.name);

        wellnessSubCategoryList = Arrays.asList(SubCategoryEnum.BODY_CARE.name, SubCategoryEnum.MEDICINES.name, SubCategoryEnum.MED_APPOINTMENT.name);

        festivitySubCategoryList = Arrays.asList(SubCategoryEnum.BIRTHDAY.name, SubCategoryEnum.LONG_WEEKEND.name, SubCategoryEnum.HOLIDAYS.name);

        financesSubCategoryList = Arrays.asList(SubCategoryEnum.REVENUE.name, SubCategoryEnum.OUTFLOW.name);

        if (btnString == CategoryEnum.FREE_TIME.name) {
            addTextViewAndButtons("Which subcategory?", freeTimeSubCategoryList);
        }
        if (btnString == CategoryEnum.STUDY.name) {
            addTextViewAndButtons("Which subcategory?", studySubCategoryList);
        }
        if (btnString == CategoryEnum.WELLNESS.name) {
            addTextViewAndButtons("Which subcategory?", wellnessSubCategoryList);
        }
        if (btnString == CategoryEnum.FINANCES.name) {
            addTextViewAndButtons("Which subcategory?", financesSubCategoryList);
        }
        if (btnString == CategoryEnum.FESTIVITY.name) {
            addTextViewAndButtons("Which subcategory?", festivitySubCategoryList);
        }

        return view;
    }


    public void addTextView(LinearLayout layout, String text, Integer marginLeft, Integer marginTop) {

        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        textParams.setMargins(Utils.convertDpToPixel(marginLeft), Utils.convertDpToPixel(marginTop), 0, 0);
        textView.setLayoutParams(textParams);

        layout.addView(textView);
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

    public void addButtons(LinearLayout layout, List<String> subCategoryList) {

        final LinearLayout buttonsLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams buttonsParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        buttonsLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonsLayout.setLayoutParams(buttonsParams);

        horizontalScrollView(layout, buttonsLayout);

        final List<Button> buttons = new ArrayList<>();

        for (final Object i : subCategoryList) {
            final Button btn = new Button(this.getContext());
            final String subcategoryName = String.valueOf(i);
            btn.setText(String.valueOf(i));
            buttons.add(btn);
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            btnparams.setMargins(Utils.convertDpToPixel(8), Utils.convertDpToPixel(8), 0, 0);

            if(initialSubcategory != null && btn.getText().toString().equalsIgnoreCase(initialSubcategory)) {
                switch(btnString) {
                    case "FINANCES":
                        btn.setBackgroundColor(Color.parseColor(CategoryEnum.FINANCES.color));
                        break;
                    case "WELLNESS":
                        btn.setBackgroundColor(Color.parseColor(CategoryEnum.WELLNESS.color));
                        break;
                    case "FESTIVITY":
                        btn.setBackgroundColor(Color.parseColor(CategoryEnum.FESTIVITY.color));
                        break;
                    case "STUDY":
                        btn.setBackgroundColor(Color.parseColor(CategoryEnum.STUDY.color));
                        break;
                    case "FREE_TIME":
                        btn.setBackgroundColor(Color.parseColor(CategoryEnum.FREE_TIME.color));
                        break;
                }
            } else {
                btn.setBackgroundColor(Color.TRANSPARENT);
            }
            btn.setLayoutParams(btnparams);
            buttonsLayout.addView(btn);

            btn.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @SuppressLint("ResourceAsColor")
                public void onClick(View view) {

                    Drawable background = btn.getBackground();
                    int color = Color.TRANSPARENT;

                    if(background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();

                    clearOtherButtonBackground(buttons, btn);
                    switch(btnString) {
                        case "FINANCES":
                            changeStatusButton(color, btn, CategoryEnum.FINANCES, subcategoryName);
                            break;
                        case "WELLNESS":
                            changeStatusButton(color, btn, CategoryEnum.WELLNESS, subcategoryName);
                            break;
                        case "FESTIVITY":
                            changeStatusButton(color, btn, CategoryEnum.FESTIVITY, subcategoryName);
                            break;
                        case "STUDY":
                            changeStatusButton(color, btn, CategoryEnum.STUDY, subcategoryName);
                            break;
                        case "FREE_TIME":
                            changeStatusButton(color, btn, CategoryEnum.FREE_TIME, subcategoryName);
                            break;
                    }
                }
            });

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void changeStatusButton(int color, Button btn, CategoryEnum categoryEnum, String subcategoryName) {
        FragmentManager fm = getFragmentManager();
        AddEventFormFragment dynamicFormFragment = (AddEventFormFragment)fm.findFragmentById(R.id.fragment_container);

        if(color == Color.TRANSPARENT) {
            btn.setBackgroundColor(Color.parseColor(categoryEnum.color));
            dynamicFormFragment.activeDynamicForm(subcategoryName, Color.parseColor(categoryEnum.color));
        } else {
            btn.setBackgroundColor(Color.TRANSPARENT);
            dynamicFormFragment.clearDynamicForm();
        }

    }

    private void clearOtherButtonBackground(List<Button> buttons, Button excluding) {
        for(Button btn: buttons){
            if(btn != excluding)
                btn.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    public void addTextViewAndButtons(String text, final List<String> categoryList) {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        addTextView(layout, text, 5, 5);
        addButtons(layout, categoryList);

    }

}
