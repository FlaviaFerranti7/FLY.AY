package com.flam.flyay.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;
import com.flam.flyay.adapter.ToDoAdapter;
import com.flam.flyay.model.Event;
import com.flam.flyay.model.ToDo;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.services.ToDoService;
import com.flam.flyay.util.ConverterFromJsonToModel;
import com.flam.flyay.util.ItemMoveCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ToDoListFragment extends Fragment {
    private ToDoService service;
    private ConverterFromJsonToModel converterFromJsonToModel;
    private List<ToDo> toDoList;
    private OnToDoListListener onToDoListListener;

    public interface OnToDoListListener {
        void onToDoListSelected(ToDo toDo);
    }

    public ToDoListFragment(){
        //empty constructor
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            onToDoListListener = (OnToDoListListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException("OnToDoListListener interface must be implemented");
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_do_list, container, false);

        final RecyclerView listRecyclerView = view.findViewById(R.id.to_do_list_recycler);
        listRecyclerView.setLayoutManager(new LinearLayoutManager((getActivity().getApplicationContext())));
        listRecyclerView.setNestedScrollingEnabled(false);


        this.service = new ToDoService(this.getContext());
        this.converterFromJsonToModel = new ConverterFromJsonToModel();
        this.toDoList = new ArrayList<>();

        String userId = "1";

        JSONObject params = getParams(userId);
        Log.d(".ToDoListFragment", "parameters: [userId = '" + userId + "']");

        service.getUserLists(params,  new ServerCallback() {
            @Override
            public void onSuccess(Object result) {
                toDoList = (List<ToDo>) result;
                Log.d(".TodoListFragment", toDoList.toString());
                ToDoAdapter toDoAdapter = new ToDoAdapter(toDoList, onToDoListListener, getContext());
                ItemTouchHelper.Callback callback =
                        new ItemMoveCallback(toDoAdapter);
                ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
                touchHelper.attachToRecyclerView(listRecyclerView);
                listRecyclerView.setAdapter(toDoAdapter);
                toDoAdapter.notifyDataSetChanged();

            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private JSONObject getParams(String userId) {
        JSONObject params = new JSONObject();
        try {
            params.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }
}
