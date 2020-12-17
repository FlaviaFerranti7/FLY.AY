package com.flam.flyay.ui.list;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flam.flyay.R;
import com.flam.flyay.ui.addevent.AddEventViewModel;

public class ListFragment extends Fragment {

    private ListViewModel listViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        listViewModel =
                new ViewModelProvider(this).get(ListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list, container, false);
        final TextView textView = root.findViewById(R.id.title_list);
        listViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

}