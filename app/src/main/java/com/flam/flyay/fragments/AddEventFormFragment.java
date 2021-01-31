 package com.flam.flyay.fragments;

import android.content.Intent;
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
import android.widget.Button;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

 public class AddEventFormFragment extends Fragment {
    private EventService service;
    List<InputField> object;
    Map<String, InputField> mapInputField;
    Map<InputField, View> objectView;
    LinearLayout linearLayout;
    LinearLayout dynamicForm;

    Button save;
    Button cancel;


    Map<Integer, List<View>> viewWithParentId;


    private List<String> weekDays;
    private List<String> projectWith;
    private List<String> studyBy;
    private List<String> recapPer;



    public AddEventFormFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AddEventActivity) getActivity()).getSupportActionBar().setTitle("      Add event");
        ((AddEventActivity) getActivity()).getSupportActionBar().setIcon(R.drawable.ic_add_event);
        ((AddEventActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        System.out.println(".AddEventFormFragment: " + container);
        final View view = inflater.inflate(R.layout.add_event_form_fragment, container, false);
        this.service = new EventService(getActivity());
        linearLayout = view.findViewById(R.id.touchInterceptorFormFragment);
        dynamicForm = view.findViewById(R.id.dynamic_form);

        save = view.findViewById(R.id.button_save);
        cancel = view.findViewById(R.id.button_cancel);

        dynamicForm.setId(View.generateViewId());
        linearLayout.setOnTouchListener(new TouchInterceptor(getActivity()));

        object = new ArrayList<>();
        objectView = new HashMap<>();
        viewWithParentId = new HashMap<>();
        mapInputField = new HashMap<>();

        addFragment(new CategoriesFieldFragment(), null, R.id.category_fragment);


        weekDays = Arrays.asList("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat");
        projectWith = Arrays.asList("by myself", "with friend");
        studyBy = Arrays.asList("Topics", "Pages");
        recapPer = Arrays.asList("Main", "Sub");

        save.setEnabled(false);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(".AddEventForm", "click on save button");

                for(InputField input : object) {
                    if(objectView.get(input).getVisibility() == View.VISIBLE) {
                        switch (input.getFieldType()) {
                            case "TEXT":
                            case "EMAIL":
                                TextInputLayout t = (TextInputLayout) objectView.get(input);
                                Log.d(".SAVEAddEventForm", input.getName() + " " + t.getEditText().getText());

                                input.setValue(t.getEditText().getText());
                                break;
                            case "NUMBER":
                                EditText e = (EditText) ((LinearLayout) objectView.get(input)).getChildAt(1);
                                Log.d(".SAVEAddEventForm", input.getName() + " " + e.getText());

                                input.setValue(e.getText());
                                break;
                            case "DATE":
                            case "SWITCH":
                            case "OFFICEDAY":
                            case "TOPICSPAGES":
                            case "CHECKBOX":
                            case "TIME":
                            case "TIMEWITHOUTALLDAY":
                                Log.d(".SAVEAddEventForm", input.getName() + " " + input.getValue());
                                break;

                        }
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(".AddEventForm", "click on cancel button");
                Intent intent = new Intent(getContext(), AddEventActivity.class);
                startActivity(intent);
            }
        });

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

    private Bundle createParamsTime(boolean flag, String key, String label) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("allDayFlag", flag);
        bundle.putString("key", key);
        bundle.putString("label", label);
        return bundle;
    }

    private Bundle createParamsButtonList(String title, String key, String typeOfList, int colorCategory) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("key", key);
        bundle.putString("typeOfList", typeOfList);
        bundle.putInt("color", colorCategory);

        return bundle;
    }

    private Bundle createParamsDate(String title, String key) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("key", key);
        return bundle;
    }

    private Bundle createParamsCheckbox(String title, String key, String typeCheckbox) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("key", key);
        bundle.putString("typeCheckbox", typeCheckbox);
        return bundle;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createDynamicForm(final List<InputField> object, final int colorCategory) {
        save.setEnabled(true);

        for(int i = 0; i < object.size(); i ++) {
            final InputField input = object.get(i);
            viewWithParentId.put(input.getId(), new ArrayList<View>());
            mapInputField.put(input.getName(), input);

            switch (input.getFieldType()) {
                case "NUMBER":
                    Log.d(".AddEventForm", "switch received");
                    LinearLayout cLayout = new LinearLayout(getContext());
                    LinearLayout.LayoutParams  paramsLayout = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                    paramsLayout.setMargins(Utils.convertDpToPixel(5), Utils.convertDpToPixel(10), 0, 0);

                    cLayout.setLayoutParams(paramsLayout);
                    cLayout.setOrientation(LinearLayout.HORIZONTAL);

                    TextView textViewNumber = new TextView(getContext());
                    textViewNumber.setTextSize(16);
                    textViewNumber.setText(input.getLabelName());

                    EditText numberInput = new EditText(getContext());
                    numberInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                    numberInput.setTextSize(16);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(Utils.convertDpToPixel(10), 0, 0, 0);
                    numberInput.setLayoutParams(lp);

                    cLayout.addView(textViewNumber);
                    cLayout.addView(numberInput);

                    cLayout.setTag(input.getName());
                    addElementIntoListMap(viewWithParentId, cLayout, input.getFieldParentId());
                    objectView.put(input, cLayout);
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
                    if(input.getFieldType().equalsIgnoreCase("EMAIL"))
                        textInputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    else {
                        textInputEditText.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                    textInputEditText.setTextSize(16);
                    textInputLayout.setHint(input.getLabelName());
                    textInputLayout.setFocusable(true);
                    textInputLayout.setFocusableInTouchMode(true);
                    textInputLayout.setEndIconMode(TextInputLayout.END_ICON_CLEAR_TEXT);

                    textInputLayout.addView(textInputEditText);

                    textInputLayout.setTag(input.getName());
                    addElementIntoListMap(viewWithParentId, textInputLayout, input.getFieldParentId());
                    objectView.put(input, textInputLayout);
                    dynamicForm.addView(textInputLayout, input.getFieldOrderId());

                    break;
                case "DATE":
                    Log.d(".AddEventForm", "data picker received");
                    RelativeLayout childLayout = new RelativeLayout(getContext());
                    paramsLayout = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    childLayout.setLayoutParams(paramsLayout);

                    childLayout.setTag(input.getName());
                    addElementIntoListMap(viewWithParentId, childLayout, input.getFieldParentId());

                    objectView.put(input, childLayout);
                    dynamicForm.addView(childLayout, input.getFieldOrderId());
                    childLayout.setId(View.generateViewId());
                    DateFieldFragment dateFieldFragment = new DateFieldFragment();
                    dateFieldFragment.setArguments(createParamsDate(input.getLabelName(), input.getName()));
                    addFragment(dateFieldFragment, childLayout.getId());
                    break;
                case "TIME":
                    Log.d(".AddEventForm", "time picker received");
                    childLayout = new RelativeLayout(getContext());
                    paramsLayout = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    childLayout.setLayoutParams(paramsLayout);
                    TimeFieldFragment f = new TimeFieldFragment();
                    f.setArguments(createParamsTime(true, input.getName(), input.getLabelName()));

                    childLayout.setTag(input.getName());
                    addElementIntoListMap(viewWithParentId, childLayout, input.getFieldParentId());

                    objectView.put(input, childLayout);
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
                    f.setArguments(createParamsTime(false, input.getName(), input.getLabelName()));

                    childLayout.setTag(input.getName());
                    addElementIntoListMap(viewWithParentId, childLayout, input.getFieldParentId());

                    objectView.put(input, childLayout);
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
                            optionsFieldFragment.setArguments(createParamsCheckbox(input.getLabelName(), input.getName(), "EXAM_DIFFICULTY"));
                            break;
                        case "overRange":
                            optionsFieldFragment.setArguments(createParamsCheckbox(input.getLabelName(), input.getName(), "OVER_RANGE"));
                            break;
                        default:
                            break;
                    }

                    childLayout.setTag(input.getName());
                    addElementIntoListMap(viewWithParentId, childLayout, input.getFieldParentId());

                    objectView.put(input, childLayout);
                    dynamicForm.addView(childLayout, input.getFieldOrderId());
                    childLayout.setId(View.generateViewId());
                    addFragment(optionsFieldFragment, childLayout.getId());
                    break;
                case "OFFICEDAY":
                    Log.d(".AddEventForm", "button received");
                    childLayout = new RelativeLayout(getContext());
                    paramsLayout = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    paramsLayout.setMargins(Utils.convertDpToPixel(5), Utils.convertDpToPixel(20), 0, 0);
                    childLayout.setLayoutParams(paramsLayout);


                    childLayout.setTag(input.getName());
                    addElementIntoListMap(viewWithParentId, childLayout, input.getFieldParentId());

                    objectView.put(input, childLayout);
                    dynamicForm.addView(childLayout, input.getFieldOrderId());
                    childLayout.setId(View.generateViewId());
                    ButtonsFieldFragment fragmentB = new ButtonsFieldFragment();
                    fragmentB.setArguments(createParamsButtonList(input.getLabelName(), input.getName(), "weekDays", colorCategory));
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

                    childLayout.setTag(input.getName());
                    addElementIntoListMap(viewWithParentId, childLayout, input.getFieldParentId());

                    objectView.put(input, childLayout);
                    dynamicForm.addView(childLayout, input.getFieldOrderId());
                    childLayout.setId(View.generateViewId());
                    fragmentB = new ButtonsFieldFragment();
                    fragmentB.setArguments(createParamsButtonList(input.getLabelName(), input.getName(), "studyBy", colorCategory));
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


                    cLayout.setTag(input.getName());
                    addElementIntoListMap(viewWithParentId, cLayout, input.getFieldParentId());
                    objectView.put(input, cLayout);
                    dynamicForm.addView(cLayout, input.getFieldOrderId());

                    if(input.getName().equalsIgnoreCase("studyPlanFlag")) {
                        Log.d(".AddEventForm", "This is a study plan switch");
                        switchOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                Log.d(".AddEventForm", "study plan switch is checked");
                                input.setValue(isChecked);
                                if(input.getName().equalsIgnoreCase("studyPlanFlag")) {
                                    if (isChecked) {
                                        Utils.listVISIBLE(viewWithParentId.get(input.getId()));
                                    } else {
                                        Utils.listGONE(viewWithParentId.get(input.getId()));
                                        toggleTopicPagesForm("P", false);
                                        toggleTopicPagesForm("T", false);
                                    }
                                }
                            }
                        });
                    }
                default:
                    break;
            }

            input.setFieldParentId(null);
        }
    }

    private void addElementIntoListMap(Map<Integer, List<View>> map, View newItem, Integer key) {
        if(key != null) {
            newItem.setVisibility(View.GONE);
            List<View> list = map.get(key);
            list.add(newItem);
            map.put(key, list);
        }
    }

    public void toggleTopicPagesForm(String typeForm, boolean flag) {
        List<View> tmp = new ArrayList<>();
        for(View view : viewWithParentId.get(52)) {
            if(view.getTag().toString().endsWith(typeForm))
                tmp.add(view);
        }
        if(flag)
            Utils.listVISIBLE(tmp);
        else
            Utils.listGONE(tmp);
    }

    public void setValueInputField(String name, Object value) {
        mapInputField.get(name).setValue(value);
   }
}