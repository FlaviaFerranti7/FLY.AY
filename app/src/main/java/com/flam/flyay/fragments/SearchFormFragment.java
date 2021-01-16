package com.flam.flyay.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import static com.flam.flyay.util.CategoryEnum.FESTIVITY;
import static com.flam.flyay.util.CategoryEnum.FINANCES;
import static com.flam.flyay.util.CategoryEnum.FREE_TIME;
import static com.flam.flyay.util.CategoryEnum.STUDY;
import static com.flam.flyay.util.CategoryEnum.WELLNESS;

public class SearchFormFragment extends Fragment {

    private TextInputLayout eventNameLayout;
    private TextInputEditText eventNameTextField;
    private TextInputLayout eventPlaceLayout;
    private TextInputEditText eventPlaceTextField;
    private Button searchButton;

    private MaterialButtonToggleGroup toggleCategories;

    private String searchName;
    private String searchPlace;

    private String checkedCategory;
    private List<String> checkedCategoryList;

    public SearchFormFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.search_form, container, false);

        this.checkedCategoryList = new ArrayList<>();

        this.eventNameLayout = (TextInputLayout) view.findViewById(R.id.whichEventLayout);
        this.eventNameTextField = (TextInputEditText) view.findViewById(R.id.whichEvent);

        this.eventPlaceLayout = (TextInputLayout) view.findViewById(R.id.whereEventLayout);
        this.eventPlaceTextField = (TextInputEditText) view.findViewById(R.id.whereEvent);

        this.toggleCategories = view.findViewById(R.id.toggleButtonCategories);

        this.searchButton = view.findViewById(R.id.buttonSearch);

        this.toggleCategories.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if(checkedId == R.id.festivityCategoryButton) {
                        checkedCategory = FESTIVITY.name;
                        checkedCategoryList.add(checkedCategory);
                        Log.d(".SearchFormFragment", "selected category: " + checkedCategory);
                        Log.d(".SearchFormFragment", "selected category: " + checkedCategoryList);
                    } else if(checkedId == R.id.financesCategoryButton) {
                        checkedCategory = FINANCES.name;
                        checkedCategoryList.add(checkedCategory);
                        Log.d(".SearchFormFragment", "selected category: " + checkedCategory);
                        Log.d(".SearchFormFragment", "selected category: " + checkedCategoryList);
                    } else if(checkedId == R.id.freeTimeCategoryButton) {
                        checkedCategory = FREE_TIME.name;
                        checkedCategoryList.add(checkedCategory);
                        Log.d(".SearchFormFragment", "selected category: " + checkedCategory);
                        Log.d(".SearchFormFragment", "selected category: " + checkedCategoryList);
                    } else if(checkedId == R.id.studyCategoryButton) {
                        checkedCategory = STUDY.name;
                        checkedCategoryList.add(checkedCategory);
                        Log.d(".SearchFormFragment", "selected category: " + checkedCategory);
                        Log.d(".SearchFormFragment", "selected category: " + checkedCategoryList);
                    } else if(checkedId == R.id.wellnessCategoryButton) {
                        checkedCategory = WELLNESS.name;
                        checkedCategoryList.add(checkedCategory);
                        Log.d(".SearchFormFragment", "selected category: " + checkedCategory);
                        Log.d(".SearchFormFragment", "selected category: " + checkedCategoryList);
                    }
                }
            }
        });

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