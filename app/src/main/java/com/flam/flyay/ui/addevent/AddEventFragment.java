package com.flam.flyay.ui.addevent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.flam.flyay.R;

public class AddEventFragment extends Fragment {

    private AddEventViewModel addEventViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addEventViewModel =
                new ViewModelProvider(this).get(AddEventViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_event, container, false);
        final TextView textView = root.findViewById(R.id.title_addevent);
        addEventViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}