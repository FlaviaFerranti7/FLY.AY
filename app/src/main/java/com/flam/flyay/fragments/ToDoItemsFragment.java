package com.flam.flyay.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;


import com.flam.flyay.R;
import com.flam.flyay.ToDoActivity;
import com.flam.flyay.adapter.ToDoAdapter;
import com.flam.flyay.adapter.ToDoItemsAdapter;
import com.flam.flyay.model.ToDo;
import com.flam.flyay.model.ToDoItems;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.services.ToDoService;
import com.flam.flyay.util.ItemMoveCallback;
import com.flam.flyay.util.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.flam.flyay.R.id.etNewItem;

public class ToDoItemsFragment extends Fragment {

    private ToDoService service;
    private ArrayList<String> items;
    private List<ToDoItems> listItems;
    private ToDoItemsAdapter itemsAdapter;

    private TextInputLayout listTitleLayout;
    private TextInputEditText listTitle;
    private TextInputLayout addItemLayout;
    private TextInputEditText addedItem;

    public ToDoItemsFragment(){}

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
        final ListView lvItems = view.findViewById(R.id.lvItems);
        final Button button = view.findViewById(R.id.btnAddItem);

        this.service = new ToDoService(this.getContext());
        this.listTitleLayout = (TextInputLayout) view.findViewById(R.id.list_title_layout);
        this.listTitle = (TextInputEditText) view.findViewById(R.id.list_title);
        this.addItemLayout = (TextInputLayout) view.findViewById(R.id.etNewItemLayout);
        this.addedItem = (TextInputEditText) view.findViewById(R.id.etNewItem);

        Bundle arguments = getArguments();

        ToDo toDo = null;

        assert arguments != null;
        if(arguments.get("toDo") instanceof ToDo)
            toDo = (ToDo) arguments.get("toDo");
        else return null;

        ((ToDoActivity) getActivity()).getSupportActionBar().setTitle(" ");
        ((ToDoActivity) getActivity()).getSupportActionBar().setIcon(null);
        ((ToDoActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listTitle.setText(toDo.getTitle());

        String listId = String.valueOf(toDo.getId());
        JSONObject params = getParams(listId);


        try {
            service.getListItems(params, new ServerCallback() {
                @Override
                public void onSuccess(Object result) {
                    listItems = (List<ToDoItems>) result;
                    items = new ArrayList<String>();
                    for(ToDoItems i: listItems){
                        items.add(i.getTitle());
                    }
                    Log.d(".ToDoItemsFragment", "items: " + items);
                    //instantiate custom adapter
                    itemsAdapter = new ToDoItemsAdapter(listItems, items, getContext());

                    //handle listview and assign adapter
                    ListView lView = (ListView) view.findViewById(R.id.lvItems);
                    lView.setAdapter(itemsAdapter);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }



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

    private JSONObject getParams(String listId) {
        JSONObject params = new JSONObject();
        try {
            params.put("ListId", listId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }

}
