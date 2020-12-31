package com.flam.flyay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;
import com.flam.flyay.fragments.EventsListFragment;
import com.flam.flyay.model.Event;
import com.flam.flyay.util.CategoryEnum;
import com.flam.flyay.util.Utils;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> events;
    private EventsListFragment.OnEventsListListener onEventsListListener;

    public EventAdapter(List<Event> events, EventsListFragment.OnEventsListListener onEventsListListener) {
        this.events = events;
        this.onEventsListListener = onEventsListListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView eventTime;
        public TextView leftBorder;
        public TextView eventTitle;
        public TextView eventPosition;



        public ImageView iconPosition;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            eventTime = (TextView) itemView.findViewById(R.id.event_time);
            leftBorder = (TextView) itemView.findViewById(R.id.left_border);
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
            eventPosition = (TextView) itemView.findViewById(R.id.event_position);

            iconPosition = (ImageView) itemView.findViewById(R.id.icon_position_events_layout);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        Log.d(".EventAdapter",events.toString());
        final View contactView = inflater.inflate(R.layout.event_list_adapter_layout, parent, false);

        return new ViewHolder(contactView);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Get the data model based on position
        final Event event = events.get(position);

        // Set item views based on your views and data model
        holder.eventTime.setText(Utils.getTimeToString(event));
        holder.eventTitle.setText(event.getTitle());

        String place = (String) event.getValueEvent().get("place");

        if(place != null) {
            holder.eventPosition.setText(place);
            holder.eventPosition.setVisibility(View.VISIBLE);
            holder.iconPosition.setVisibility(View.VISIBLE);
        }

        LayerDrawable shape = (LayerDrawable) holder.leftBorder.getBackground().mutate();
        setColorShape(shape, event);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEventsListListener.onEventSelected(event);
            }
        });

    }

    private void setColorShape (LayerDrawable shape, Event event) {
        if(event.getCategory().equals(CategoryEnum.WELLNESS.name))
            shape.setColorFilter(Color.parseColor(CategoryEnum.WELLNESS.color), PorterDuff.Mode.SRC_IN);
        else if(event.getCategory().equals(CategoryEnum.FINANCES.name))
            shape.setColorFilter(Color.parseColor(CategoryEnum.FINANCES.color), PorterDuff.Mode.SRC_IN);
        else if(event.getCategory().equals(CategoryEnum.STUDY.name))
            shape.setColorFilter(Color.parseColor(CategoryEnum.STUDY.color), PorterDuff.Mode.SRC_IN);
        else if(event.getCategory().equals(CategoryEnum.FREE_TIME.name))
            shape.setColorFilter(Color.parseColor(CategoryEnum.FREE_TIME.color), PorterDuff.Mode.SRC_IN);
        else
            shape.setColorFilter(Color.parseColor(CategoryEnum.FESTIVITY.color), PorterDuff.Mode.SRC_IN);
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
