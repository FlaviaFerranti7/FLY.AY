package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.R;
import com.flam.flyay.SearchActivity;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SearchFormFragment extends Fragment {

    private TextInputLayout eventNameLayout;
    private TextInputEditText eventNameTextField;
    private TextInputLayout eventPlaceLayout;
    private TextInputEditText eventPlaceTextField;

    private LinearLayout buttonsGroup;

    private Button searchButton;

    private String searchName;
    private String searchPlace;

    private Boolean clicked;

    private String checkedCategory;

    private List<String> categoryList;

    public SearchFormFragment() {}

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.search_form_fragment, container, false);

        ((SearchActivity) getActivity()).getSupportActionBar().setTitle("      Search");
        ((SearchActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.ic_search);
        ((SearchActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        clicked = false;

        this.eventNameLayout = (TextInputLayout) view.findViewById(R.id.whichEventLayout);
        this.eventNameTextField = (TextInputEditText) view.findViewById(R.id.whichEvent);

        this.eventPlaceLayout = (TextInputLayout) view.findViewById(R.id.whereEventLayout);
        this.eventPlaceTextField = (TextInputEditText) view.findViewById(R.id.whereEvent);

        this.buttonsGroup = view.findViewById(R.id.buttonCategoriesGroups);

        this.searchButton = view.findViewById(R.id.buttonSearch);

        categoryList = Arrays.asList(CategoryEnum.FREE_TIME.name, CategoryEnum.STUDY.name, CategoryEnum.WELLNESS.name,
                CategoryEnum.FESTIVITY.name, CategoryEnum.FINANCES.name);

        final List<Button> buttons = new ArrayList<>();

        for (final Object i : categoryList) {
            final Button btn = new Button(this.getContext());

            btn.setText(String.valueOf(i));
            buttons.add(btn);
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            btnparams.setMargins(Utils.convertDpToPixel(8), Utils.convertDpToPixel(8), 0, 0);
            btn.setLayoutParams(btnparams);
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setClickable(true);
            buttonsGroup.addView(btn);


            btn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                public void onClick(View view) {
                    String categoryName = String.valueOf(i);

                    Drawable background = btn.getBackground();
                    int color = Color.TRANSPARENT;

                    if(background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();

                    clearOtherButtonBackground(buttons, btn);
                    switch(categoryName) {
                        case "FINANCES":
                            selectCategory(color, btn, CategoryEnum.FINANCES);
                            break;
                        case "WELLNESS":
                            selectCategory(color, btn, CategoryEnum.WELLNESS);
                            break;
                        case "FESTIVITY":
                            selectCategory(color, btn, CategoryEnum.FESTIVITY);
                            break;
                        case "STUDY":
                            selectCategory(color, btn, CategoryEnum.STUDY);
                            break;
                        case "FREE_TIME":
                            selectCategory(color, btn, CategoryEnum.FREE_TIME);
                            break;
                    }
                }
            });
        }


        this.searchButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                searchName = eventNameTextField.getText().toString();
                searchPlace = eventPlaceTextField.getText().toString();

                Log.d(".SearchFormFragment", "parameters: " + searchName + searchPlace);
                substituteFragment(new SearchResultsFragment());

            }
        });

        return view;
    }

    private void selectCategory(int color, Button btn, CategoryEnum categoryEnum) {
        if(color == Color.TRANSPARENT) {
            btn.setBackgroundColor(Color.parseColor(categoryEnum.color));
            checkedCategory = categoryEnum.name;
        } else {
            btn.setBackgroundColor(Color.TRANSPARENT);
            checkedCategory = "";
        }
        Log.d(".SearchFormFragment", "selected category: " + checkedCategory);
    }

    private void clearOtherButtonBackground(List<Button> buttons, Button excluding) {
        for(Button btn: buttons){
            if(btn != excluding)
                btn.setBackgroundColor(Color.TRANSPARENT);
        }
    }


    public void substituteFragment(Fragment fragment){

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragment.setArguments(createParamsEventsFragment());
        transaction.replace(R.id.fragment_container, fragment, fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private Bundle createParamsEventsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("searchParamsName", searchName);
        bundle.putString("searchParamsPlace", searchPlace);
        bundle.putString("checkedCategory", checkedCategory);
        return bundle;
    }

}