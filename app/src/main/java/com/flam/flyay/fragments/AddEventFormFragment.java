 package com.flam.flyay.fragments;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.util.Util;
import com.flam.flyay.AddEventActivity;
import com.flam.flyay.MainActivity;
import com.flam.flyay.R;
import com.flam.flyay.model.Event;
import com.flam.flyay.model.InputField;
import com.flam.flyay.model.TeacherInfo;
import com.flam.flyay.model.subevent.StudyEvent;
import com.flam.flyay.services.EventService;
import com.flam.flyay.services.ServerCallback;
import com.flam.flyay.util.CategoryEnum;
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

    TextInputEditText title;
    TextInputEditText note;
    Button save;
    Button cancel;


    Map<Integer, List<View>> viewWithParentId;
    Event eventEditable;
    Map<String, Object> eventEditableValues;

    public AddEventFormFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

        title = view.findViewById(R.id.dynamic_form_title_value);
        note = view.findViewById(R.id.note_value);
        eventEditableValues = new HashMap<>();

        Bundle arguments = getArguments();
        if(arguments != null) {
            eventEditable = (Event) arguments.getSerializable("eventEditable");
            if(eventEditable != null) {
                ((AddEventActivity) getActivity()).getSupportActionBar().setTitle("      Edit " + eventEditable.getTitle() + " event");
                eventEditableValues = eventEditable.getValueEvent();
                if(eventEditableValues.get("teacher") != null) {
                    TeacherInfo teacherInfo = (TeacherInfo) eventEditableValues.get("teacher");
                    eventEditableValues.put("teacherName", teacherInfo.getName());
                    eventEditableValues.put("teacherEmail", teacherInfo.getEmail());
                    eventEditableValues.put("teacherOfficeDay", teacherInfo.getOfficeDay());
                    eventEditableValues.put("teacherReceiptTime", Utils.getTimeToString(teacherInfo.getReceiptStartingTime(), teacherInfo.getReceiptEndingTime()));
                    eventEditableValues.put("teacherReceiptRoom", teacherInfo.getReceiptRoom());
                }
            }
        }


        Log.d(".AddEventForm", "event editable: " + eventEditable);

        save = view.findViewById(R.id.button_save);
        cancel = view.findViewById(R.id.button_cancel);

        dynamicForm.setId(View.generateViewId());
        linearLayout.setOnTouchListener(new TouchInterceptor(getActivity()));

        object = new ArrayList<>();
        objectView = new HashMap<>();
        viewWithParentId = new HashMap<>();
        mapInputField = new HashMap<>();

        CategoriesFieldFragment categoriesFieldFragment = new CategoriesFieldFragment();
        Bundle bundle = new Bundle();
        if(eventEditable != null) {
            bundle.putString("initialCategory", eventEditable.getCategory());
            bundle.putString("initialSubcategory", eventEditable.getSubcategory());
        } else {
            bundle = null;
        }

        addFragment(categoriesFieldFragment, bundle, R.id.category_fragment);


        save.setEnabled(false);

        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(".AddEventForm", "click on save button");

                List<String> errors = new ArrayList<>();

                if(title.getText() == null || title.getText().toString().length() == 0)
                    errors.add("- TITLE cannot be blank");

                for(InputField input : object) {
                    if(objectView.get(input).getVisibility() == View.VISIBLE && input.isMandatory() && input.getValue() == null) {
                        errors.add("- " + input.getName().toUpperCase() + " cannot be blank");
                    }
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

                if(errors.size() > 0) {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(getContext());
                    alertbox.setTitle("Error validation field");

                    String errorMessage = "Field errors:";
                    for(int i = 0; i < errors.size(); i ++) {
                        errorMessage += "\n\t" + errors.get(i);
                    }

                    alertbox.setMessage(errorMessage);
                    alertbox.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alertbox.setIcon(R.drawable.ic_warning);
                    alertbox.show();
                }
                else {
                    AlertDialog.Builder alertbox = new AlertDialog.Builder(getContext());
                    alertbox.setTitle("Save changes");
                    alertbox.setMessage("Do you want to save new event?");

                    alertbox.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    alertbox.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    alertbox.setIcon(R.drawable.ic_saving);
                    alertbox.show();
                }

                Log.d(".AddEventForm", errors.toString());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(".AddEventForm", "click on cancel button");
                AlertDialog.Builder alertbox = new AlertDialog.Builder(getContext());
                alertbox.setTitle("Delete event");
                alertbox.setMessage("Do you want to delete the new event?");

                alertbox.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getContext(), AddEventActivity.class);
                        startActivity(intent);
                    }
                });
                alertbox.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                alertbox.setIcon(R.drawable.ic_delete);
                alertbox.show();
            }
        });

        if(eventEditable != null) {
            /* set layout variables */
            title.setText(eventEditable.getTitle());
            if(eventEditable.getNote() != null)
                note.setText(eventEditable.getNote());

            /* Open dynamic form */
            switch (eventEditable.getCategory()) {
                case "FREE_TIME":
                    activeDynamicForm(eventEditable.getSubcategory(), CategoryEnum.FREE_TIME.name, Color.parseColor(CategoryEnum.FREE_TIME.color));
                    break;
                case "FINANCES":
                    activeDynamicForm(eventEditable.getSubcategory(), CategoryEnum.FINANCES.name, Color.parseColor(CategoryEnum.FINANCES.color));
                    break;
                case "WELLNESS":
                    activeDynamicForm(eventEditable.getSubcategory(), CategoryEnum.WELLNESS.name, Color.parseColor(CategoryEnum.WELLNESS.color));
                    break;
                case "STUDY":
                    activeDynamicForm(eventEditable.getSubcategory(), CategoryEnum.STUDY.name, Color.parseColor(CategoryEnum.STUDY.color));
                    break;
                case "FESTIVITY":
                    activeDynamicForm(eventEditable.getSubcategory(), CategoryEnum.FESTIVITY.name, Color.parseColor(CategoryEnum.FESTIVITY.color));
                    break;
            }
        }

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
    public void activeDynamicForm(String subcategoryName, String category, final int colorCategory) {
        final JSONObject params = getParams(subcategoryName);

        if(eventEditable != null && !eventEditable.getCategory().equalsIgnoreCase(category)) {
            eventEditable = null;
            eventEditableValues = new HashMap<>();
        }

        service.getInputFieldBySubcategory(params, category.toLowerCase(), new ServerCallback() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onSuccess(Object result) {
                object = (List<InputField>) result;
                Log.d(".AddEventFromFragment", object.toString());

                clearDynamicForm();
                createDynamicForm(object, colorCategory);

                if(eventEditable instanceof StudyEvent && ((StudyEvent) eventEditable).getStudyPlan() != null) {
                    Log.d(".TESTTT", "Ho un evento studio con study plane");
                    Utils.listVISIBLE(viewWithParentId.get(45));
                    Switch tmp = (Switch) ((LinearLayout) dynamicForm.findViewWithTag("studyPlanFlag")).getChildAt(1);
                    Log.d(".TESTTTT", tmp.toString());

                    tmp.setChecked(true);
                }
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

    private Bundle createParamsTime(boolean flag, String key, String label, String initialValue) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("allDayFlag", flag);
        bundle.putString("key", key);
        bundle.putString("label", label);
        bundle.putString("initialValue", initialValue);
        return bundle;
    }

    private Bundle createParamsButtonList(String title, String key, String typeOfList, int colorCategory, List<String> initialValues) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("key", key);
        bundle.putString("typeOfList", typeOfList);
        bundle.putInt("color", colorCategory);
        bundle.putStringArrayList("initialValues", initialValues != null ? new ArrayList<String>(initialValues): null);

        return bundle;
    }

    private Bundle createParamsDate(String title, String key, String initialDate) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("key", key);
        bundle.putString("initialDate", initialDate);
        return bundle;
    }

    private Bundle createParamsCheckbox(String title, String key, String typeCheckbox, List<String> initialValues) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("key", key);
        bundle.putString("typeCheckbox", typeCheckbox);
        bundle.putStringArrayList("initialValues", initialValues != null ? new ArrayList<String>(initialValues): null);
        return bundle;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

                    if(eventEditableValues.get(input.getName()) != null) {
                        numberInput.setText(eventEditableValues.get(input.getName()).toString());
                        input.setValue(eventEditableValues.get(input.getName()));
                    }

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

                    if(eventEditableValues.get(input.getName()) != null) {
                        textInputEditText.setText(eventEditableValues.get(input.getName()).toString());
                        input.setValue(eventEditableValues.get(input.getName()));
                    }

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
                    if(eventEditable != null && input.getName().equalsIgnoreCase("date")) {
                        dateFieldFragment.setArguments(createParamsDate(input.getLabelName(), input.getName(),
                                Utils.convertionFromDateToString(eventEditable.getDate())));
                        input.setValue(eventEditable.getDate());
                    }
                    else if(eventEditable != null && ((StudyEvent)eventEditable).getStudyPlan()!=null && input.getName().equalsIgnoreCase("endingStudy")) {
                        dateFieldFragment.setArguments(createParamsDate(input.getLabelName(), input.getName(),
                                Utils.convertionFromDateToString(((StudyEvent) eventEditable).getStudyPlan().getEndStudy())));
                        input.setValue(eventEditable.getDate());
                    }
                    else
                        dateFieldFragment.setArguments(createParamsDate(input.getLabelName(), input.getName(), null));
                    addFragment(dateFieldFragment, childLayout.getId());
                    break;
                case "TIME":
                    Log.d(".AddEventForm", "time picker received");
                    childLayout = new RelativeLayout(getContext());
                    paramsLayout = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    childLayout.setLayoutParams(paramsLayout);
                    TimeFieldFragment f = new TimeFieldFragment();
                    if(eventEditableValues.get("time") != null) {
                        f.setArguments(createParamsTime(true, input.getName(), input.getLabelName(), eventEditableValues.get("time").toString()));
                        input.setValue(eventEditableValues.get("time"));
                    }

                    else
                        f.setArguments(createParamsTime(true, input.getName(), input.getLabelName(), null));

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

                    if(eventEditableValues.get("teacherReceiptTime") != null) {
                        f.setArguments(createParamsTime(false, input.getName(), input.getLabelName(), eventEditableValues.get("teacherReceiptTime").toString()));
                        input.setValue(eventEditableValues.get("time"));
                    }
                    else
                        f.setArguments(createParamsTime(false, input.getName(), input.getLabelName(), null));


                    childLayout.setTag(input.getName());
                    addElementIntoListMap(viewWithParentId, childLayout, input.getFieldParentId());

                    objectView.put(input, childLayout);
                    dynamicForm.addView(childLayout, input.getFieldOrderId());
                    childLayout.setId(View.generateViewId());
                    addFragment(f, childLayout.getId());
                    break;
                case "PERIODIC":
                    Log.d(".AddEventForm", "periodic field received");
                    childLayout = new RelativeLayout(getContext());
                    paramsLayout = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    childLayout.setLayoutParams(paramsLayout);

                    childLayout.setTag(input.getName());
                    addElementIntoListMap(viewWithParentId, childLayout, input.getFieldParentId());

                    CheckboxFieldFragment checkboxFieldFragment = new CheckboxFieldFragment();

                    Bundle bundle = new Bundle();
                    bundle.putInt("color", colorCategory);

                    checkboxFieldFragment.setArguments(bundle);

                    objectView.put(input, childLayout);
                    dynamicForm.addView(childLayout, input.getFieldOrderId());
                    childLayout.setId(View.generateViewId());
                    addFragment(checkboxFieldFragment, childLayout.getId());

                    break;
                case "CHECKBOX":
                    Log.d(".AddEventForm", "checkbox received");
                    childLayout = new RelativeLayout(getContext());
                    paramsLayout = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    childLayout.setLayoutParams(paramsLayout);
                    OptionsFieldFragment optionsFieldFragment = new OptionsFieldFragment();

                    StudyEvent studyEvent = null;
                    if(eventEditable != null) {
                        studyEvent = (StudyEvent) eventEditable;
                        input.setValue("hasValue");
                    }

                    switch (input.getName()) {
                        case "examDifficulty":
                            if(studyEvent != null && studyEvent.getStudyPlan()!=null) {
                                optionsFieldFragment.setArguments(createParamsCheckbox(input.getLabelName(), input.getName(), "EXAM_DIFFICULTY", Arrays.asList(studyEvent.getStudyPlan().getExamDifficulty())));
                            }
                            else {
                                optionsFieldFragment.setArguments(createParamsCheckbox(input.getLabelName(), input.getName(), "EXAM_DIFFICULTY", null));
                            }

                            break;
                        case "overRange":
                            if(studyEvent != null && studyEvent.getStudyPlan()!=null ) {
                                optionsFieldFragment.setArguments(createParamsCheckbox(input.getLabelName(), input.getName(), "OVER_RANGE", studyEvent.getStudyPlan().getOverRange()));
                            }
                            else{
                                optionsFieldFragment.setArguments(createParamsCheckbox(input.getLabelName(), input.getName(), "OVER_RANGE", null));
                            }

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
                    if(eventEditable != null && ((StudyEvent)eventEditable).getStudyPlan()!=null && input.getName().equalsIgnoreCase("studyingDays")) {
                        fragmentB.setArguments(createParamsButtonList(input.getLabelName(), input.getName(), "weekDays", colorCategory, ((StudyEvent)eventEditable).getStudyPlan().getStudyingDays()));
                        input.setValue("hasValue");
                    }
                    else if(eventEditable != null && ((StudyEvent)eventEditable).getTeacherInfo()!=null && input.getName().equalsIgnoreCase("officeDay")) {
                        fragmentB.setArguments(createParamsButtonList(input.getLabelName(), input.getName(), "weekDays", colorCategory, ((StudyEvent)eventEditable).getTeacherInfo().getOfficeDay()));
                        input.setValue("hasValue");
                    }
                    else {
                        fragmentB.setArguments(createParamsButtonList(input.getLabelName(), input.getName(), "weekDays", colorCategory, null));
                    }
                    addFragment(fragmentB, childLayout.getId());
                    break;
                case "TOPICSPAGES":
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
                    fragmentB = new ButtonsFieldFragment();
                    fragmentB.setArguments(createParamsButtonList(input.getLabelName(), input.getName(), "studyBy", colorCategory, null));
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
                    break;
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
        for(View view : viewWithParentId.get(53)) {
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