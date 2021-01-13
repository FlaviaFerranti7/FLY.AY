package com.flam.flyay.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.flam.flyay.R;
import com.flam.flyay.adapter.UserAdapter;
import com.flam.flyay.model.User;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.services.UserService;
import com.flam.flyay.util.ConverterFromJsonToModel;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {
    private UserService service;
    private ConverterFromJsonToModel converterFromJsonToModel;
    private List<User> users;

    public ProfileFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        final RecyclerView listRecyclerView = view.findViewById(R.id.profile_recycler);
        listRecyclerView.setNestedScrollingEnabled(false);

        this.service = new UserService(this.getContext());
        this.converterFromJsonToModel = new ConverterFromJsonToModel();
        this.users = new ArrayList<>();

        String userId = "1";
        JSONObject params = getParams(userId);

        service.getUserById(params, new ServerCallback() {
            @Override
            public void onSuccess(Object result) {
                users = (List<User>) result;
               // Log.d(".ProfileFragment", users.toString());
                UserAdapter userAdapter = new UserAdapter(users);
                listRecyclerView.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();

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
