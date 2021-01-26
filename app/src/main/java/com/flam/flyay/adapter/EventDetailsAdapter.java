package com.flam.flyay.adapter;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;
import com.flam.flyay.model.TeacherInfo;
import com.flam.flyay.util.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
        public ImageView propertyIcon;
        public ImageView accordionIcon;
        public RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);

            propertyContainer = itemView.findViewById(R.id.property_container);
            propertyName = itemView.findViewById(R.id.property_name_event_details);
            propertyValue = itemView.findViewById(R.id.property_value_event_details);
            propertyIcon = itemView.findViewById(R.id.icon_of_event_details_option);
            recyclerView = itemView.findViewById(R.id.accordion_recycler);
            accordionIcon = itemView.findViewById(R.id.accordion);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        String propertyName = Utils.buildPropertyName(keyList.get(position), ':');
        int layoutType = 0;
        holder.propertyName.setText(propertyName);

        switch (keyList.get(position).startsWith("day") ?
                keyList.get(position).substring(0, keyList.get(position).length()-1) : keyList.get(position)) {
            case "date":
                holder.propertyIcon.setImageResource(R.drawable.ic_calendar);
                break;
            case "place":
                holder.propertyIcon.setImageResource(R.drawable.ic_position);
                break;
            case "day":
                holder.propertyIcon.setImageResource(R.drawable.ic_study_book);
                break;
            case "time":
                holder.propertyIcon.setImageResource(R.drawable.ic_clock);
                break;
            case "teacher":
                TeacherInfo info = (TeacherInfo) valueList.get(keyList.get(position));
                Log.d(".EventDetailsAdapter", info.toString());

                HashMap<String, Object> items = new HashMap<>();

                items.put("name", info.getName());
                items.put("email", info.getEmail());
                items.put("receipt date", info.getReceiptDate());
                items.put("receipt room", info.getReceiptRoom());

                EventDetailsAdapter adapter = new EventDetailsAdapter(Arrays.asList(items.keySet().toArray(new String[0])), items);
                holder.recyclerView.setAdapter(adapter);
                propertyName = propertyName.substring(0, propertyName.length() - 1) + " Info";
                holder.propertyName.setText(propertyName);
                layoutType = 2;
                break;
            case "studyingDays":
                List<String> days = (List<String>) valueList.get(keyList.get(position));
                List<String> keySetSorted = new ArrayList<>();
                items = new HashMap<>();
                for(int i = 0; i < days.size(); i ++) {
                    items.put("day" + i, days.get(i));
                    keySetSorted.add("day" + i);
                }

                adapter = new EventDetailsAdapter(keySetSorted, items);
                holder.recyclerView.setAdapter(adapter);
                holder.propertyName.setText(propertyName.substring(0, propertyName.length() - 1));
                layoutType = 2;
                break;
            default:
                layoutType = 1;
                break;
        }

        if(layoutType == 0) {
            holder.propertyName.setVisibility(View.GONE);
        } else if(layoutType == 1) {
            holder.propertyIcon.setVisibility(View.GONE);
        } else {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            holder.propertyName.setLayoutParams(param);
            holder.accordionIcon.setVisibility(View.VISIBLE);
            holder.propertyIcon.setVisibility(View.GONE);
            holder.propertyValue.setVisibility(View.GONE);

            holder.accordionIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.recyclerView.getVisibility() == View.GONE) {
                        holder.recyclerView.setVisibility(View.VISIBLE);
                        holder.accordionIcon.setImageResource(R.drawable.ic_arrow_up);
                    }
                    else {
                        holder.recyclerView.setVisibility(View.GONE);
                        holder.accordionIcon.setImageResource(R.drawable.ic_arrow_down);
                    }
                }
            });
        }

        if(valueList.get(keyList.get(position)) == null ||
                Objects.requireNonNull(valueList.get(keyList.get(position))).toString().length() == 0) {
            holder.propertyContainer.setVisibility(View.GONE);
            holder.propertyContainer.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
        } else {
            holder.propertyValue.setText(
                    Utils.replaceSpecialChar(Utils.capitalize(Objects.requireNonNull(valueList.get(keyList.get(position))).toString())));
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
