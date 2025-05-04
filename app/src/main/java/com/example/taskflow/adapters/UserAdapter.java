package com.example.taskflow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.R;
import com.example.taskflow.model.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView userInitial, userName;

        public UserViewHolder(View itemView) {
            super(itemView);
            userInitial = itemView.findViewById(R.id.user_initial);
            userName = itemView.findViewById(R.id.user_name);
        }
    }

    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_user, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = userList.get(position);
        String name = user.getName();
        holder.userName.setText(name);
        holder.userInitial.setText(String.valueOf(name.charAt(0)).toUpperCase());
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}