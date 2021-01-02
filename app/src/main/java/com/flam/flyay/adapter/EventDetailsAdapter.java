package com.flam.flyay.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;

import java.util.ArrayList;
import java.util.List;

public class EventDetailsAdapter extends RecyclerView.Adapter<EventDetailsAdapter.ViewHolder> {

    private List<String> keyList;
    private List<Object> valueList;

    public EventDetailsAdapter(List<String> keyList, List<Object> valueList) {
        this.keyList = keyList;
        this.valueList = valueList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView propertyName;
        public TextView propertyValue;

        public ViewHolder(View itemView) {
            super(itemView);

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
        holder.propertyName.setText(keyList.get(position));
        holder.propertyValue.setText(valueList.get(position) != null ? valueList.get(position).toString(): "");
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
