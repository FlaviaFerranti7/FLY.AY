package com.flam.flyay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flam.flyay.R;
import com.flam.flyay.model.ToDo;
import com.flam.flyay.util.ItemMoveCallback;
import com.google.android.material.card.MaterialCardView;

import java.util.Collections;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> implements ItemMoveCallback.ItemTouchHelperContract{

    private List<ToDo> toDoList;

    public ToDoAdapter(List<ToDo> toDoList){
        this.toDoList = toDoList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MaterialCardView card;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.todo_title);
            card = itemView.findViewById(R.id.card);

        }
    }

    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.to_do_list_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ToDoAdapter.ViewHolder holder, int position) {
        final ToDo toDo = toDoList.get(position);

        holder.textView.setText(toDo.getTitle());

        setCheckedList(holder.card, toDo);
    }

    private void setCheckedList (MaterialCardView card, ToDo toDo) {
        if(toDo.isChecked())
            card.setChecked(true);
        else
            card.setChecked(false);
    }

    @Override
    public void onRowMoved(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(toDoList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(toDoList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onRowSelected(ViewHolder viewHolder) {
        viewHolder.card.setCardBackgroundColor(Color.GRAY);

    }

    @Override
    public void onRowClear(ViewHolder viewHolder) {
        viewHolder.card.setCardBackgroundColor(Color.WHITE);

    }


    @Override
    public int getItemCount() {
        return toDoList.size();
    }
}
