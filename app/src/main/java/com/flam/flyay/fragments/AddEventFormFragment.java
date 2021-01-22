package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.AddEventActivity;
import com.flam.flyay.R;
import com.flam.flyay.util.Utils;


public class AddEventFormFragment extends Fragment {

    private LinearLayout linearLayout;

    public AddEventFormFragment() {
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_event_form_fragment, container, false);

        ((AddEventActivity) getActivity()).getSupportActionBar().setTitle("      Add event");
        ((AddEventActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.ic_add_event);
        ((AddEventActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        linearLayout = view.findViewById(R.id.add_event_form);
        linearLayout.setId(1);

        addFragment(new AddEventTitleCategoriesFragment());
        addFragment(new AddEventSubCategoryFragment());
        addFragment(new AddEventPickersFragment());

        return view;
    }


    public void addCheckBox(String text) {

        LinearLayout checkBoxLayout = new LinearLayout(this.getContext());
        checkBoxLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(checkBoxLayout);

        final CheckBox checkBox = new CheckBox(this.getContext());
        checkBox.setText(text);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(Utils.convertDpToPixel(16), Utils.convertDpToPixel(16), Utils.convertDpToPixel(16), 0);
        checkBox.setLayoutParams(params);
        checkBoxLayout.addView(checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AddEventPeriodicFragment fragment = new AddEventPeriodicFragment();
                if (checkBox.isChecked()) {
                    addFragment(fragment);
                } else {
                    fragment = (AddEventPeriodicFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.fragment_container);
                    removeFragment(fragment);
                }
            }
        });

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
        //bundle.putString("btnString", btnString);
        return bundle;
    }

}