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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flam.flyay.AddEventActivity;
import com.flam.flyay.R;
import com.flam.flyay.SearchActivity;
import com.flam.flyay.model.InputField;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.util.TouchInterceptor;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;


public class AddEventFormFragment extends Fragment {
    private EventService service;
    List<InputField> object = null;
    LinearLayout linearLayout;
    LinearLayout dynamicForm;



    private List<String> weekDays;
    private List<String> projectWith;
    private List<String> studyBy;
    private List<String> recapPer;

    private List<String> buttonsValue;



    public AddEventFormFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AddEventActivity) getActivity()).getSupportActionBar().setTitle("      Add event");
        ((AddEventActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.ic_add_event);
        ((AddEventActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        System.out.println(".AddEventFormFragment: " + container);
        final View view = inflater.inflate(R.layout.add_event_form_fragment, container, false);
        this.service = new EventService(getActivity());
        linearLayout = view.findViewById(R.id.touchInterceptorFormFragment);
        dynamicForm = view.findViewById(R.id.dynamic_form);
        dynamicForm.setId(View.generateViewId());
        linearLayout.setOnTouchListener(new TouchInterceptor(getActivity()));

        addFragment(new CategoriesFieldFragment(), null, R.id.category_fragment);


        weekDays = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
        projectWith = Arrays.asList("by myself", "with friend");
        studyBy = Arrays.asList("Topics", "Pages");
        recapPer = Arrays.asList("Main", "Sub");

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

                clearDynamicForm();

                for(int i = 0; i < object.size(); i ++) {
                    InputField input = object.get(i);
                    switch (input.getFieldType()) {
                        case "TEXT":
                        case "EMAIL":
                            Log.d(".AddEventForm", "input text received");
                            TextInputLayout textInputLayout=new TextInputLayout(getActivity());
                            textInputLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT));

                            TextInputEditText textInputEditText = new TextInputEditText(getContext());

                            LinearLayout.LayoutParams editTextParams = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                            textInputEditText.setLayoutParams(editTextParams);

                            textInputEditText.setTextSize(16);
                            textInputLayout.setHint(input.getLabelName());
                            textInputLayout.setFocusable(true);
                            textInputLayout.setFocusableInTouchMode(true);
                            textInputLayout.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);

                            textInputLayout.addView(textInputEditText);
                            dynamicForm.addView(textInputLayout, i);

                            break;
                        case "DATE":
                            Log.d(".AddEventForm", "data picker received");
                            RelativeLayout childLayout = new RelativeLayout(getContext());
                            LinearLayout.LayoutParams paramsLayout = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            childLayout.setLayoutParams(paramsLayout);

                            dynamicForm.addView(childLayout, i);
                            childLayout.setId(View.generateViewId());
                            addFragment(new DateFieldFragment(), childLayout.getId());
                            break;
                        case "TIME":
                            Log.d(".AddEventForm", "time picker received");
                            childLayout = new RelativeLayout(getContext());
                            paramsLayout = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            childLayout.setLayoutParams(paramsLayout);

                            dynamicForm.addView(childLayout, i);
                            childLayout.setId(View.generateViewId());
                            addFragment(new TimeFieldFragment(), childLayout.getId());
                            break;
                        case "CHECKBOX":
                            Log.d(".AddEventForm", "checkbox received");
                            childLayout = new RelativeLayout(getContext());
                            paramsLayout = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            childLayout.setLayoutParams(paramsLayout);

                            dynamicForm.addView(childLayout, i);
                            childLayout.setId(View.generateViewId());
                            addFragment(new CheckboxFieldFragment(), childLayout.getId());
                            break;
                        case "BUTTON":
                            Log.d(".AddEventForm", "button received");
                            childLayout = new RelativeLayout(getContext());
                            paramsLayout = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            childLayout.setLayoutParams(paramsLayout);

                            dynamicForm.addView(childLayout, i);
                            childLayout.setId(View.generateViewId());
                            ButtonsFieldFragment fragmentB = new ButtonsFieldFragment();
                            buttonsValue = recapPer;
                            fragmentB.setArguments(createParamsEventsFragment());
                            addFragment(fragmentB, childLayout.getId());
                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void addFragmentDynamically(Fragment fragment, int index) {
        RelativeLayout childLayout = new RelativeLayout(getContext());
        LinearLayout.LayoutParams paramsLayout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        childLayout.setLayoutParams(paramsLayout);

        dynamicForm.addView(childLayout, index);
        childLayout.setId(View.generateViewId());
        addFragment(fragment, childLayout.getId());
    }

    public void addFragment(Fragment fragment, int id){
        Log.d(".AddEventForm", "fragment to add: " + fragment);
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(id, fragment, fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void clearDynamicForm() {
        dynamicForm.removeAllViews();
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

    private Bundle createParamsEventsFragment() {
        Bundle bundle = new Bundle();
        bundle.putString("buttonsValue", buttonsValue.toString());
        Log.d(".AddEventForm", buttonsValue.toString());
        return bundle;
    }


}