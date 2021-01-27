package com.flam.flyay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.flam.flyay.R;
import com.flam.flyay.model.ToDoItems;

import java.util.ArrayList;
import java.util.List;

public class ToDoItemsAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private List<ToDoItems> todo_items;

    public ToDoItemsAdapter(List<ToDoItems> todo_items, ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
        this.todo_items = todo_items;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.to_do_item_adapter, null);
        }

        //Handle TextView and display string from your list
        TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.delete_btn);
        deleteBtn.setBackgroundColor(Color.TRANSPARENT);
        CheckBox cb = (CheckBox) view.findViewById(R.id.list_item_checkbox);

        if(position < todo_items.size()) {
            if(todo_items.get(position).isChecked())
                cb.setChecked(true);
        }
        else cb.setChecked(false);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //do something
                list.remove(position); //or some other task
                notifyDataSetChanged();
            }
        });

        return view;
    }

    public void add(String itemText) {
        list.add(itemText);
        notifyDataSetChanged();
    }
}
