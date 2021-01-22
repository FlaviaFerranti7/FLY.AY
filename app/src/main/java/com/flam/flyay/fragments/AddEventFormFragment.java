package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.AddEventActivity;
import com.flam.flyay.R;


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
        addFragment(new AddEventPickersFragment());
        addFragment(new AddEventPeriodicCheckboxFragment());
        addFragment(new AddEventButtonFragment());

        return view;
    }

    public void addFragment(Fragment fragment){

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(linearLayout.getId(), fragment, fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

}