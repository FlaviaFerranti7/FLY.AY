package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.R;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import java.util.Arrays;
import java.util.List;


public class AddEventTitleCategoriesFragment extends Fragment {

    private String btnString;
    private Boolean clicked;

    private LinearLayout linearLayout;

    private List<String> categoryList;

    public AddEventTitleCategoriesFragment() {
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_event_title_categories_fragment, container, false);

        clicked = false;

        categoryList = Arrays.asList(CategoryEnum.FREE_TIME.name, CategoryEnum.STUDY.name, CategoryEnum.WELLNESS.name,
                CategoryEnum.FESTIVITY.name, CategoryEnum.FINANCES.name);

        linearLayout = view.findViewById(R.id.add_event_title_categories_fragment);
        linearLayout.setId(2);

        addLineSeparator();
        addIconAndEditText(R.drawable.ic_title,"Title");
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
                        fragment = (AddEventSubCategoryFragment) getActivity().getSupportFragmentManager().findFragmentById(linearLayout.getId());
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
        transaction.add(linearLayout.getId(), fragment, fragment.getClass().getName());
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