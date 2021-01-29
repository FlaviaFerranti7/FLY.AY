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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.R;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.TouchInterceptor;
import com.flam.flyay.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CategoriesFieldFragment extends Fragment {

    private LinearLayout linearLayout;
    private View fragmentContainer;
    private List<String> categoryList;

    public CategoriesFieldFragment() {
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.categories_field_fragment, container, false);

        categoryList = Arrays.asList(CategoryEnum.FREE_TIME.name, CategoryEnum.STUDY.name, CategoryEnum.WELLNESS.name,
                CategoryEnum.FESTIVITY.name, CategoryEnum.FINANCES.name);

        linearLayout = view.findViewById(R.id.add_event_title_categories_fragment);
        linearLayout.setOnTouchListener(new TouchInterceptor(getActivity()));
        linearLayout.setId(2);

        addLineSeparator();
        addTextViewAndButtons(R.drawable.ic_category, "Which category?", categoryList);

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
        image.setBackgroundColor(Color.TRANSPARENT);
        image.setLayoutParams(imageParams);

        layout.addView(image);
    }

    public void addTextView(LinearLayout layout, String text, Integer marginLeft, Integer marginTop) {

        TextView textView = new TextView(this.getContext());
        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textParams.setMargins(Utils.convertDpToPixel(marginLeft), Utils.convertDpToPixel(marginTop), 0, 0);
        textView.setLayoutParams(textParams);

        layout.addView(textView);
    }

    public void addIconAndTextView(LinearLayout layout, Integer obj, String text) {

        addIcon(layout, obj, 16, 16);
        addTextView(layout, text, 56, -19);

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

        final List<Button> buttons = new ArrayList<>();

        for (final Object i : categoryList) {
            final Button btn = new Button(this.getContext());
            btn.setText(String.valueOf(i));
            buttons.add(btn);
            LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            btnparams.setMargins(Utils.convertDpToPixel(8), Utils.convertDpToPixel(8), 0, 0);
            btn.setLayoutParams(btnparams);
            btn.setBackgroundColor(Color.TRANSPARENT);
            btn.setClickable(true);
            buttonsLayout.addView(btn);


            btn.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                public void onClick(View view) {
                    String categoryName = String.valueOf(i);

                    Fragment subcategoryFragment = getActivity().getSupportFragmentManager()
                            .findFragmentByTag(SubCategoriesFieldFragment.class.getName());

                    if(subcategoryFragment == null)
                        subcategoryFragment = new SubCategoriesFieldFragment();

                    Drawable background = btn.getBackground();
                    int color = Color.TRANSPARENT;

                    if(background instanceof ColorDrawable)
                        color = ((ColorDrawable) background).getColor();

                    clearOtherButtonBackground(buttons, btn);
                    switch(categoryName) {
                        case "FINANCES":
                            openOrCloseSubcategory(color, btn, subcategoryFragment, CategoryEnum.FINANCES);
                            break;
                        case "WELLNESS":
                            openOrCloseSubcategory(color, btn, subcategoryFragment, CategoryEnum.WELLNESS);
                            break;
                        case "FESTIVITY":
                            openOrCloseSubcategory(color, btn, subcategoryFragment, CategoryEnum.FESTIVITY);
                            break;
                        case "STUDY":
                            openOrCloseSubcategory(color, btn, subcategoryFragment, CategoryEnum.STUDY);
                            break;
                        case "FREE_TIME":
                            openOrCloseSubcategory(color, btn, subcategoryFragment, CategoryEnum.FREE_TIME);
                            break;
                    }
                }
            });
        }
    }



    public void addTextViewAndButtons(Integer obj, String text, final List<String> categoryList) {

        LinearLayout layout = new LinearLayout(this.getContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(layout);

        //addIconAndTextView(layout, obj, text);
        addTextView(layout, text, 5, 5);
        addButtons(layout, categoryList);

    }

    private void openOrCloseSubcategory(int color, Button btn, Fragment fragment, CategoryEnum categoryEnum) {
        if(color == Color.TRANSPARENT) {
            btn.setBackgroundColor(Color.parseColor(categoryEnum.color));
            addSubcategoryFragment(createParamsEventsFragment(categoryEnum));
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            btn.setBackgroundColor(Color.TRANSPARENT);
            removeSubcategoryFragment();
        }
    }

    private void clearOtherButtonBackground(List<Button> buttons, Button excluding) {
        for(Button btn: buttons){
            if(btn != excluding)
                btn.setBackgroundColor(Color.TRANSPARENT);
        }
    }


    public void addLineSeparator() {
        LinearLayout lineLayout = new LinearLayout(this.getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2);
        params.setMargins(0, Utils.convertDpToPixel(3), 0, Utils.convertDpToPixel(3));
        lineLayout.setLayoutParams(params);
        linearLayout.addView(lineLayout);
    }

    private void addSubcategoryFragment(Bundle params) {
        Fragment fragment = new SubCategoriesFieldFragment();
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if(params != null)
            fragment.setArguments(params);
        transaction.replace(linearLayout.getId(), fragment, fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void removeSubcategoryFragment() {
        Fragment subcategoryFragment = getActivity().getSupportFragmentManager()
                .findFragmentByTag(SubCategoriesFieldFragment.class.getName());

        if(subcategoryFragment != null) {
            Log.d(".CategoriesField", "remove current fragment " + subcategoryFragment);
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.remove(subcategoryFragment);
            transaction.commit();

            FragmentManager fm = getFragmentManager();
            AddEventFormFragment dynamicFormFragment = (AddEventFormFragment)fm.findFragmentById(R.id.fragment_container);
            dynamicFormFragment.clearDynamicForm();
        }
    }

    private Bundle createParamsEventsFragment(CategoryEnum categoryEnum) {
        Bundle bundle = new Bundle();
        bundle.putString("btnString", categoryEnum.name);
        return bundle;
    }

}