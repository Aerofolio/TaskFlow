package com.example.taskflow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.adapters.UserAdapter;
import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.User;

import java.util.ArrayList;
import java.util.List;

public class TaskDetailFragment extends Fragment {
    private int taskId;
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    private AppDatabase db;

    private TextView titleText;
    private TextView descriptionText;
    private TextView deadlineText;
    private TextView priorityText;
    private TextView statusText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = AppDatabase.getInstance(requireContext());
        if (getArguments() != null) {
            taskId = getArguments().getInt("TASK_ID", -1);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.task_detail_fragment, container, false);

        titleText = view.findViewById(R.id.title);
        descriptionText = view.findViewById(R.id.description);
        deadlineText = view.findViewById(R.id.descriptionDate);
        priorityText = view.findViewById(R.id.descriptionPriority);
        statusText = view.findViewById(R.id.descriptionStatus);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadTaskFromDatabase();

        return view;
    }

    private void loadTaskFromDatabase() {
        new Thread(() -> {
            Task task = db.taskDao().getTaskById(taskId);

            if (task != null) {
                requireActivity().runOnUiThread(() -> {
                    titleText.setText(task.getTitle());
                    descriptionText.setText(task.getDescription());
                    deadlineText.setText(task.getDeadline());
                    priorityText.setText(task.getPriority().getDescription());

                    userList = new ArrayList<>();
//        userList.add(new User("José"));
//        userList.add(new User("Maria"));
//        userList.add(new User("Lucas"));
//        userList.add(new User("Ana"));

                    userAdapter = new UserAdapter(userList);
                    recyclerView.setAdapter(userAdapter);

                });
            }
        }).start();
    }
}
