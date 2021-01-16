package com.flam.flyay.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.flam.flyay.R;
import com.flam.flyay.ToDoActivity;
import com.flam.flyay.model.ToDo;

public class ToDoItemsFragment extends Fragment {

    public ToDoItemsFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_to_do_items, container, false);

        Bundle arguments = getArguments();

        ToDo toDo = null;

        assert arguments != null;
        if(arguments.get("toDo") instanceof ToDo)
            toDo = (ToDo) arguments.get("toDo");
        else return null;

        ((ToDoActivity) getActivity()).getSupportActionBar().setTitle(toDo.getTitle());
        ((ToDoActivity) getActivity()).getSupportActionBar().setIcon(null);
        ((ToDoActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Log.d(".ToDoItemsFragment", "event received: " + toDo.toString());

        return view;
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
