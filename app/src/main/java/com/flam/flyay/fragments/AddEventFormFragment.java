package com.flam.flyay.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;
import com.flam.flyay.services.EventService;

import org.json.JSONException;
import org.json.JSONObject;


public class AddEventFormFragment extends Fragment {


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
        final RecyclerView listRecyclerView = view.findViewById(R.id.dynamic_form);
        final EventService service = new EventService(view.getContext());

        addFragment(new CategoriesFieldFragment(), null, R.id.category_fragment);

        return view;
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

}