package com.flam.flyay.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;
import com.flam.flyay.adapter.EventAdapter;
import com.flam.flyay.adapter.UserAdapter;
import com.flam.flyay.model.Event;
import com.flam.flyay.model.User;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.services.UserService;
import com.flam.flyay.util.ConverterFromJsonToModel;
import com.flam.flyay.util.MockServerUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class ProfileFragment extends Fragment {
    private UserService service;
    private ConverterFromJsonToModel converterFromJsonToModel;
    private User user;
    private OnUserListener onUserListener;

    public interface OnUserListener {
        void onUserSelected(User u);
    }


    public ProfileFragment(){
        // Required empty public constructor
    }
/*
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            onUserListener = (OnUserListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException("OnUserListener interface must be implemented");
        }
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(".ProfileFragment", "create view...");

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final RecyclerView listRecyclerView = view.findViewById(R.id.user_details_recycler);
        listRecyclerView.setNestedScrollingEnabled(false);
        Bundle arguments = getArguments();

        this.service = new UserService(this.getContext());
        this.converterFromJsonToModel = new ConverterFromJsonToModel();
        this.user = new User();

        String userId = "1";
        JSONObject params = getParams(userId);

        service.getUserById(params, new ServerCallback() {
            @Override
            public void onSuccess(Object result) {
                user = (User) result;
                Log.d(".ProfileFragment", user.toString());

                /*UserAdapter userAdapter = new UserAdapter(user, onUserListener);
                listRecyclerView.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();*/
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
