package com.flam.flyay.adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;
import com.flam.flyay.fragments.AddEventSubCategoryFragment;
import com.flam.flyay.fragments.CategoriesFieldFragment;
import com.flam.flyay.model.InputField;
import com.flam.flyay.util.CategoryEnum;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class DynamicFormAdapter extends RecyclerView.Adapter<DynamicFormAdapter.ViewHolder>{
    private List<InputField> inputField;
    private Context context;

    public DynamicFormAdapter(List<InputField> inputField) {
        this.inputField = inputField;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextInputLayout textInputLayout;
        private final TextInputEditText textInputEditText;

        private final View fragmentContainer;


        public ViewHolder(View itemView) {
            super(itemView);

            textInputLayout = itemView.findViewById(R.id.dynamic_form_text_input_container);
            textInputEditText = itemView.findViewById(R.id.dynamic_form_text_input_value);
            fragmentContainer = itemView.findViewById(R.id.dynamic_form_fragment_container);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        Log.d(".EventAdapter",inputField.toString());
        final View contactView = inflater.inflate(R.layout.dynamic_form_adapter_layout, parent, false);

        return new DynamicFormAdapter.ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InputField field = inputField.get(position);

        switch(field.getFieldType()) {
            case "STRING":
                holder.textInputLayout.setVisibility(View.VISIBLE);
                holder.textInputEditText.setVisibility(View.VISIBLE);
                holder.textInputEditText.setHint(field.getLabelName() != null ? field.getLabelName() : "");

                break;
            case "CATEGORY":
                holder.fragmentContainer.setVisibility(View.VISIBLE);
                addFragment(new CategoriesFieldFragment(), null);

                break;
            case "SUBCATEGORY":
                holder.fragmentContainer.setVisibility(View.VISIBLE);
                Bundle bundle = new Bundle();
                bundle.putString("btnString", CategoryEnum.STUDY.name);
                addFragment(new AddEventSubCategoryFragment(), null);
                break;
            default:
                break;
        }
    }

    public void addFragment(Fragment fragment, Bundle params) {
        FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        if(params != null)
            fragment.setArguments(params);
        transaction.replace(R.id.dynamic_form_fragment_container, fragment, fragment.getClass().getName());
        Log.d("String fragment: ", fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public int getItemCount() {
        return inputField.size();
    }
}
