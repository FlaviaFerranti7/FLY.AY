package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.flam.flyay.R;
import com.flam.flyay.ToDoActivity;
import com.flam.flyay.adapter.ToDoItemsAdapter;
import com.flam.flyay.model.ToDoItems;
import com.flam.flyay.services.ToDoService;
import com.flam.flyay.util.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class ToDoNewFragment extends Fragment {

    private List<ToDoItems> listItems;
    private ToDoItemsAdapter itemsAdapter;

    private TextInputLayout listTitleLayout;
    private TextInputEditText listTitle;
    private TextInputLayout addItemLayout;
    private TextInputEditText addedItem;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_to_do_items, container, false);
        final Button button = view.findViewById(R.id.btnAddItem);

        this.listTitleLayout = (TextInputLayout) view.findViewById(R.id.list_title_layout);
        this.listTitle = (TextInputEditText) view.findViewById(R.id.list_title);
        this.addItemLayout = (TextInputLayout) view.findViewById(R.id.etNewItemLayout);
        this.addedItem = (TextInputEditText) view.findViewById(R.id.etNewItem);

        ((ToDoActivity) getActivity()).getSupportActionBar().setTitle(" ");
        ((ToDoActivity) getActivity()).getSupportActionBar().setIcon(null);
        ((ToDoActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listItems = new ArrayList<>();
        itemsAdapter = new ToDoItemsAdapter(listItems, getContext());

        ListView lView = (ListView) view.findViewById(R.id.lvItems);
        lView.setAdapter(itemsAdapter);

        button.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NewApi")
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                String itemText = addedItem.getText().toString();
                if(!Utils.isEmptyOrBlank(itemText)) {
                    itemsAdapter.add(itemText);
                    addedItem.setText("");
                }
            }
        });

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
            case R.id.delete:
                new AlertDialog.Builder(getContext())
                        .setTitle("Delete items")
                        .setMessage("Are you sure you want to delete the items in the list?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                itemsAdapter.deleteAll();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            case R.id.check_all:
                new AlertDialog.Builder(getContext())
                        .setTitle("Change item status")
                        .setMessage("Are you sure you want to change the status of all items?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                itemsAdapter.un_check();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            case R.id.alph_sort:
                new AlertDialog.Builder(getContext())
                        .setTitle("Change items order")
                        .setMessage("Are you sure you want to change items order into alphabetical?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                itemsAdapter.sort();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflator) {
        menu.clear();
        inflator.inflate(R.menu.actions_menu, menu);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
            if(menu.getItem(i).getItemId() == R.id.action_options_list)
                menu.getItem(i).setVisible(true);
        }
        super.onCreateOptionsMenu(menu, inflator);
    }

}
