package com.example.taskflow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.R;
import com.example.taskflow.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AddUserAdapter extends RecyclerView.Adapter<AddUserAdapter.AddUserViewHolder> {

    private List<User> userList;
    private final Set<Integer> selectedUserIds = new HashSet<>();

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
        holder.addedUser.setChecked(selectedUserIds.contains(user.getId()));
        holder.addedUser.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedUserIds.add(user.getId());
            } else {
                selectedUserIds.remove(user.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public List<User> getSelectedUsers() {
        List<User> selectedUsers = new ArrayList<>();
        for (User user : userList) {
            if (selectedUserIds.contains(user.getId())) {
                selectedUsers.add(user);
            }
        }
        return selectedUsers;
    }
}