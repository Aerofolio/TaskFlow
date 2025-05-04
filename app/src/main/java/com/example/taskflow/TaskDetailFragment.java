package com.example.taskflow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.adapters.UserAdapter;
import com.example.taskflow.model.User;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_detail_fragment, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userList = new ArrayList<>();
        userList.add(new User("José"));
        userList.add(new User("Maria"));
        userList.add(new User("Lucas"));
        userList.add(new User("Ana"));

        userAdapter = new UserAdapter(userList);
        recyclerView.setAdapter(userAdapter);

        return view;
    }
}
