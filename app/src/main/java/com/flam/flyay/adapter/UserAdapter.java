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
import com.flam.flyay.fragments.EventsListFragment;
import com.flam.flyay.fragments.ProfileFragment;
import com.flam.flyay.model.Event;
import com.flam.flyay.model.User;
import com.flam.flyay.util.Utils;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private User user;
    private ProfileFragment.OnUserListener onUserListener;

    public UserAdapter(User user, ProfileFragment.OnUserListener onUserListener) {
        this.user = user;
        this.onUserListener = onUserListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView firstName;
        public TextView lastName;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            firstName = (TextView) itemView.findViewById(R.id.user_first_name);
            lastName = (TextView) itemView.findViewById(R.id.user_last_name);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        Log.d(".UserAdapter",user.toString());
        final View contactView = inflater.inflate(R.layout.profile_adapter_layout, parent, false);

        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Get the data model based on position

        // Set item views based on your views and data model
        holder.firstName.setText(user.getFirst_name());
        holder.lastName.setText(user.getLast_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserListener.onUserSelected(user);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

}
