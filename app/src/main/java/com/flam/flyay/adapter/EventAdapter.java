package com.flam.flyay.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flam.flyay.R;
import com.flam.flyay.model.Event;
import com.flam.flyay.util.Utils;

import java.util.List;

public class EventAdapter extends ArrayAdapter<Event> {
    public EventAdapter(@NonNull Context context, int resource, @NonNull List<Event> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.event_list_adapter_layout, null);
        TextView nome = (TextView)convertView.findViewById(R.id.event_time);
        TextView numero = (TextView)convertView.findViewById(R.id.event_title);
        Event event = getItem(position);
        nome.setText(Utils.getTimeToString(event));
        numero.setText(event.getTitle());

        return convertView;
    }
}
