package com.flam.flyay.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;
import com.flam.flyay.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class EventDetailsAdapter extends RecyclerView.Adapter<EventDetailsAdapter.ViewHolder> {

    private final List<String> keyList;
    private final Map<String, Object> valueList;

    public EventDetailsAdapter(List<String> keyList, Map<String, Object> valueList) {
        this.keyList = keyList;
        this.valueList = valueList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout propertyContainer;

        public TextView propertyName;
        public TextView propertyValue;

        public ViewHolder(View itemView) {
            super(itemView);

            propertyContainer = itemView.findViewById(R.id.property_container);
            propertyName = itemView.findViewById(R.id.property_name_event_details);
            propertyValue = itemView.findViewById(R.id.property_value_event_details);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(".EventDetailsAdapter",keyList.toString());
        final View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_details_adapter_layout, parent, false);

        return new ViewHolder(contactView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String propertyName = Utils.buildPropertyName(keyList.get(position), ':');
        holder.propertyName.setText(propertyName);

        if(valueList.get(keyList.get(position)) == null ||
                Objects.requireNonNull(valueList.get(keyList.get(position))).toString().length() == 0) {
            holder.propertyContainer.setVisibility(View.GONE);
            holder.propertyContainer.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        } else {
            holder.propertyValue.setText(Utils.capitalize(Objects.requireNonNull(valueList.get(keyList.get(position))).toString()));
        }

    }

    @Override
    public int getItemCount() {
        return keyList.size();
    }

    @Override
    public boolean onFailedToRecycleView(@NonNull ViewHolder holder) {
        return super.onFailedToRecycleView(holder);
    }
}
