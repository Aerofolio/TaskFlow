package com.example.taskflow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.R;
import com.example.taskflow.model.User;

import java.util.List;

public class AddUserAdapter extends RecyclerView.Adapter<AddUserAdapter.AddUserViewHolder> {

    private List<User> userList;

    public static class AddUserViewHolder extends RecyclerView.ViewHolder {
        public TextView userInitial, userName;
        public CheckBox addedUser;

        public AddUserViewHolder(View itemView) {
            super(itemView);
            userInitial = itemView.findViewById(R.id.user_initial);
            userName = itemView.findViewById(R.id.user_name);
            addedUser = itemView.findViewById(R.id.add_user);
        }
    }

    public AddUserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public AddUserAdapter.AddUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_add_user, parent, false);
        return new AddUserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AddUserViewHolder holder, int position) {
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