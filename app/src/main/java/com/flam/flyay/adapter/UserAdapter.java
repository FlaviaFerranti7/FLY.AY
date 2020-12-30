package com.flam.flyay.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.flam.flyay.R;
import com.flam.flyay.model.User;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>  {
    private List<User> users;

    public UserAdapter(List<User> users){
        this.users = users;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        TextView firstName;
        TextView lastName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.user_username);
            firstName = itemView.findViewById(R.id.user_firstName);
            lastName = itemView.findViewById(R.id.user_lastName);

        }
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.profile_adapter_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        final User user = users.get(position);
        Log.d(".Profile", user.toString());
        holder.username.setText(user.getUsername());
        holder.firstName.setText(user.getFirst_name());
        holder.lastName.setText(user.getLast_name());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
