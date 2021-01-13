package com.flam.flyay.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;
import com.flam.flyay.fragments.EventsListFragment;
import com.flam.flyay.model.Event;
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
        public TextView eventTitle;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            eventTime = (TextView) itemView.findViewById(R.id.event_time);
            eventTitle = (TextView) itemView.findViewById(R.id.event_title);
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Get the data model based on position
        final Event event = events.get(position);

        // Set item views based on your views and data model
        holder.eventTime.setText(Utils.getTimeToString(event));
        holder.eventTitle.setText(event.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEventsListListener.onEventSelected(event);
            }
        });
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
