package com.flam.flyay.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.util.Util;
import com.flam.flyay.AddEventActivity;
import com.flam.flyay.R;
import com.flam.flyay.SearchActivity;
import com.flam.flyay.model.InputField;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.util.TouchInterceptor;
import com.flam.flyay.util.Utils;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


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
    public void activeDynamicForm(String subcategoryName, final int colorCategory) {
        final JSONObject params = getParams(subcategoryName);

        service.getInputFieldBySubcategory(params, new ServerCallback() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onSuccess(Object result) {
                object = (List<InputField>) result;
                Log.d(".AddEventFromFragment", object.toString());

                clearDynamicForm();
                createDynamicForm(object, colorCategory);
            }
        });
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

    private Bundle createParamsTime(boolean flag, String label) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("allDayFlag", flag);
        bundle.putString("label", label);
        return bundle;
    }

    private Bundle createParamsButtonList(String title, List<String> nameButton, int colorCategory) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putSerializable("list", new ArrayList(nameButton));
        bundle.putInt("color", colorCategory);

        return bundle;
    }

    private Bundle createParamsDate(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        return bundle;
    }

    private Bundle createParamsCheckbox(String title, String typeCheckbox) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("typeCheckbox", typeCheckbox);
        return bundle;
    }

    private boolean visibleField(List<InputField> list, int parentId) {
        Log.d(".AddEventForm", "parent id received: " + parentId);
        for(InputField input : list) {
            if(input.getId() == parentId) {
                Object value = input.getValue();
                if(value instanceof Boolean) {
                    Log.d(".AddEventForm", value.toString());
                    return (Boolean) value;
                }
            }
        }

        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createDynamicForm(final List<InputField> object, final int colorCategory) {
        for(int i = 0; i < object.size(); i ++) {
            final InputField input = object.get(i);
            boolean parent = true;
            if(input.getFieldParentId() != null) {
                Log.d(".AddEventForm", "input with parent id: " + input);
                parent = visibleField(object, input.getFieldParentId());
            }

            Log.d(".AddEventForm", input + "=======>" + parent);

            if(parent) {
                switch (input.getFieldType()) {
                    case "NUMBER":
                        Log.d(".AddEventForm", "switch received");
                        LinearLayout cLayout = new LinearLayout(getContext());
                        LinearLayout.LayoutParams  paramsLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                        paramsLayout.setMargins(Utils.convertDpToPixel(5), Utils.convertDpToPixel(20), 0, 0);

                        cLayout.setLayoutParams(paramsLayout);
                        cLayout.setOrientation(LinearLayout.HORIZONTAL);

                        TextView textViewNumber = new TextView(getContext());
                        textViewNumber.setTextSize(16);
                        textViewNumber.setText(input.getLabelName());

                        EditText numberInput = new EditText(getContext());
                        numberInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                        numberInput.setTextSize(16);

                        cLayout.addView(textViewNumber);
                        cLayout.addView(numberInput);

                        dynamicForm.addView(cLayout, input.getFieldOrderId());
                        break;
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

                        dynamicForm.addView(textInputLayout, input.getFieldOrderId());

                        break;
                    case "DATE":
                        Log.d(".AddEventForm", "data picker received");
                        RelativeLayout childLayout = new RelativeLayout(getContext());
                        paramsLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        childLayout.setLayoutParams(paramsLayout);

                        dynamicForm.addView(childLayout, input.getFieldOrderId());
                        childLayout.setId(View.generateViewId());
                        DateFieldFragment dateFieldFragment = new DateFieldFragment();
                        dateFieldFragment.setArguments(createParamsDate(input.getLabelName()));
                        addFragment(dateFieldFragment, childLayout.getId());
                        break;
                    case "TIME":
                        Log.d(".AddEventForm", "time picker received");
                        childLayout = new RelativeLayout(getContext());
                        paramsLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        childLayout.setLayoutParams(paramsLayout);
                        TimeFieldFragment f = new TimeFieldFragment();
                        f.setArguments(createParamsTime(true, input.getLabelName()));
                        dynamicForm.addView(childLayout, input.getFieldOrderId());
                        childLayout.setId(View.generateViewId());
                        addFragment(f, childLayout.getId());
                        break;
                    case "TIMEWITHOUTALLDAY":
                        Log.d(".AddEventForm", "time picker received");
                        childLayout = new RelativeLayout(getContext());
                        paramsLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        childLayout.setLayoutParams(paramsLayout);
                        f = new TimeFieldFragment();
                        f.setArguments(createParamsTime(false, input.getLabelName()));
                        dynamicForm.addView(childLayout, input.getFieldOrderId());
                        childLayout.setId(View.generateViewId());
                        addFragment(f, childLayout.getId());
                        break;
                    case "CHECKBOX":
                        Log.d(".AddEventForm", "checkbox received");
                        childLayout = new RelativeLayout(getContext());
                        paramsLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        childLayout.setLayoutParams(paramsLayout);
                        OptionsFieldFragment optionsFieldFragment = new OptionsFieldFragment();

                        switch (input.getName()) {
                            case "examDifficulty":
                                optionsFieldFragment.setArguments(createParamsCheckbox(input.getLabelName(), "EXAM_DIFFICULTY"));
                                break;
                            case "overRange":
                                optionsFieldFragment.setArguments(createParamsCheckbox(input.getLabelName(), "OVER_RANGE"));
                                break;
                            default:
                                break;
                        }

                        dynamicForm.addView(childLayout, input.getFieldOrderId());
                        childLayout.setId(View.generateViewId());
                        addFragment(optionsFieldFragment, childLayout.getId());
                        break;
                    case "OFFICEDAY":
                        Log.d(".AddEventForm", "button received");
                        Log.d(".AddEventForm", "OFFICEDAY: " + input);
                        childLayout = new RelativeLayout(getContext());
                        paramsLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        paramsLayout.setMargins(Utils.convertDpToPixel(5), Utils.convertDpToPixel(20), 0, 0);
                        childLayout.setLayoutParams(paramsLayout);

                        dynamicForm.addView(childLayout, input.getFieldOrderId());
                        childLayout.setId(View.generateViewId());
                        ButtonsFieldFragment fragmentB = new ButtonsFieldFragment();
                        fragmentB.setArguments(createParamsButtonList(input.getLabelName(), weekDays, colorCategory));
                        addFragment(fragmentB, childLayout.getId());
                        break;
                    case "TOPICSPAGES":
                        Log.d(".AddEventForm", "button received");
                        Log.d(".AddEventForm", "OFFICEDAY: " + input);
                        childLayout = new RelativeLayout(getContext());
                        paramsLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        paramsLayout.setMargins(Utils.convertDpToPixel(5), Utils.convertDpToPixel(20), 0, 0);
                        childLayout.setLayoutParams(paramsLayout);

                        dynamicForm.addView(childLayout, input.getFieldOrderId());
                        childLayout.setId(View.generateViewId());
                        fragmentB = new ButtonsFieldFragment();
                        fragmentB.setArguments(createParamsButtonList(input.getLabelName(), studyBy, colorCategory));
                        addFragment(fragmentB, childLayout.getId());
                        break;
                    case "SWITCH":
                        Log.d(".AddEventForm", "switch received");
                        cLayout = new LinearLayout(getContext());
                        paramsLayout = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                        paramsLayout.setMargins(Utils.convertDpToPixel(5), Utils.convertDpToPixel(20), 0, 0);

                        cLayout.setLayoutParams(paramsLayout);
                        cLayout.setOrientation(LinearLayout.HORIZONTAL);


                        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                1.0f
                        );

                        TextView textViewSwitch = new TextView(getContext());
                        textViewSwitch.setTextSize(16);
                        textViewSwitch.setText(input.getLabelName());
                        textViewSwitch.setLayoutParams(param);

                        final Switch switchOption = new Switch(getContext());
                        if(input.getValue() != null) {
                            switchOption.setChecked((Boolean) input.getValue());
                        }

                        cLayout.addView(textViewSwitch);
                        cLayout.addView(switchOption);

                        dynamicForm.addView(cLayout, input.getFieldOrderId());

                        if(input.getName().equalsIgnoreCase("studyPlanFlag")) {
                            Log.d(".AddEventForm", "This is a study plan switch");
                            switchOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    Log.d("QUANTE VOLTE ENTRO?", "FGSJAHGKDFLSJG");
                                    input.setValue(isChecked);
                                    clearDynamicForm();
                                    createDynamicForm(object, colorCategory);
                                }
                            });
                        }
                    default:
                        break;
                }
            }
        }
    }


}