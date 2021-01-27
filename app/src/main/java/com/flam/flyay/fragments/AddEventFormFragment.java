package com.flam.flyay.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;
import com.flam.flyay.adapter.DynamicFormAdapter;
import com.flam.flyay.adapter.EventAdapter;
import com.flam.flyay.model.Event;
import com.flam.flyay.model.InputField;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.util.TouchInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class AddEventFormFragment extends Fragment {
    private EventService service;
    List<InputField> object = null;
    RecyclerView listRecyclerView;

    public AddEventFormFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        System.out.println(".AddEventFormFragment: " + container);
        final View view = inflater.inflate(R.layout.add_event_form_fragment, container, false);
        listRecyclerView = view.findViewById(R.id.dynamic_form);
        this.service = new EventService(getActivity());
        LinearLayout linearLayout = view.findViewById(R.id.touchInterceptorFormFragment);
        linearLayout.setOnTouchListener(new TouchInterceptor(getActivity()));

        addFragment(new CategoriesFieldFragment(), null, R.id.category_fragment);

        return view;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflator) {
        menu.clear();
        inflator.inflate(R.menu.actions_menu, menu);
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflator);
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

    public void addFragment(Fragment fragment, Bundle params, int id) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if(params != null)
            fragment.setArguments(params);
        transaction.replace(id, fragment, fragment.getClass().getName());
        Log.d("String fragment: ", fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void activeDynamicForm(String subcategoryName) {
        JSONObject params = getParams(subcategoryName);

        service.getInputFieldBySubcategory(params, new ServerCallback() {
            @Override
            public void onSuccess(Object result) {
                object = (List<InputField>) result;
                Log.d(".AddEventFromFragment", object.toString());

                DynamicFormAdapter dynamicFormAdapter = new DynamicFormAdapter(object);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                listRecyclerView.setAdapter(dynamicFormAdapter);
                listRecyclerView.setLayoutManager(layoutManager);
                if(listRecyclerView.getVisibility() == View.GONE)
                    listRecyclerView.setVisibility(View.VISIBLE);
                dynamicFormAdapter.notifyDataSetChanged();
            }
        });
    }

    public void hideDynamicForm() {
        listRecyclerView.setVisibility(View.GONE);
    }

    private JSONObject getParams(String subcategory) {
        JSONObject params = new JSONObject();
        try {
            params.put("subcategory", subcategory);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return params;
    }



}