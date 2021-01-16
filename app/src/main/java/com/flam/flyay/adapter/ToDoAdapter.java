package com.flam.flyay.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flam.flyay.R;
import com.flam.flyay.fragments.ToDoListFragment;
import com.flam.flyay.model.ToDo;
import com.flam.flyay.util.ItemMoveCallback;
import com.google.android.material.card.MaterialCardView;

import java.util.Collections;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> implements ItemMoveCallback.ItemTouchHelperContract{

    private List<ToDo> toDoList;
    private ToDoListFragment.OnToDoListListener onToDoListListener;
    private Context context;

    private int posImg;
    private Integer images[] = {R.drawable.img_default, R.drawable.img_food, R.drawable.img_gifts, R.drawable.img_travel, R.drawable.img_film, R.drawable.img_book};

    public ToDoAdapter(List<ToDo> toDoList, ToDoListFragment.OnToDoListListener onToDoListListener, Context context){
        this.toDoList = toDoList;
        this.onToDoListListener = onToDoListListener;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public MaterialCardView card;
        public ImageView image;
        public Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.todo_title);
            card = itemView.findViewById(R.id.card);
            image = itemView.findViewById(R.id.card_img);
            button = itemView.findViewById(R.id.action_button_1);
        }
    }

    @NonNull
    @Override
    public ToDoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.to_do_list_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ToDoAdapter.ViewHolder holder, int position) {
        final ToDo toDo = toDoList.get(position);

        holder.textView.setText(toDo.getTitle());
        Glide.with(context).load(getCardImage(toDo.getImage())).into(holder.image);

        for(int i=0; i<images.length; i++){
            if (getCardImage(toDo.getImage()) == images[i])
                toDo.setImagePos(i);
        }

        setCheckedList(holder.card, toDo);
        setImageRotateListener(holder, toDo);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onToDoListListener.onToDoListSelected(toDo);
            }
        });
    }

    public int getCardImage(String imageName) {
        int drawableResourceId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        return drawableResourceId;
    }

    private void setCheckedList (MaterialCardView card, ToDo toDo) {
        if(toDo.isChecked()) {
            card.setChecked(true);
            card.setCardBackgroundColor(Color.LTGRAY);
        }
        else
            card.setChecked(false);
    }

    private void setImageRotateListener(@NonNull final ToDoAdapter.ViewHolder holder, final ToDo todo){
        final Button rotatebutton = (Button) holder.button;
        rotatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                posImg = todo.getImagePos();
                posImg ++;
                if (posImg == 6){
                    posImg = 0;
                }
                Log.d(".Click", "imageposition '" + posImg + "']");
                Glide.with(context).load(images[posImg]).into(holder.image);
                todo.setImagePos(posImg);
            }
        });
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
