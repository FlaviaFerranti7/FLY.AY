package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.flam.flyay.R;
import com.flam.flyay.util.Utils;


public class AddEventButtonFragment extends Fragment {

    private LinearLayout linearLayout;

    public AddEventButtonFragment() {
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.add_event_button_fragment, container, false);

        linearLayout = view.findViewById(R.id.add_event_button_fragment);
        linearLayout.setId(6);

        addEventButton();

        return view;
    }

    public void addEventButton(){
        LinearLayout buttonLayout = new LinearLayout(this.getContext());
        buttonLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(buttonLayout);
        Button btn = new Button(this.getContext());
        btn.setText("Add event");
        LinearLayout.LayoutParams btnparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        btnparams.setMargins(Utils.convertDpToPixel(128), Utils.convertDpToPixel(16), 0, 0);
        btn.setLayoutParams(btnparams);
        buttonLayout.addView(btn);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //add event
            }
        });
    }

}