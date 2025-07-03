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
import com.example.taskflow.model.TaskWithUsers;
import com.example.taskflow.model.User;
import com.example.taskflow.model.complexTypes.TaskStatusEnum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    private TextView completedAtText;
    private View cardCompletedAt;
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
        cardCompletedAt = view.findViewById(R.id.cardCompletedAt);
        completedAtText = view.findViewById(R.id.descriptionCompletedAt);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        loadTaskFromDatabase();

        return view;
    }

    private void loadTaskFromDatabase() {
        new Thread(() -> {
            TaskWithUsers taskWithUsers = db.taskUserDao().getTaskWithUsers(taskId);

            if (taskWithUsers != null) {
                Task task = taskWithUsers.task;
                List<User> users = taskWithUsers.users;

                requireActivity().runOnUiThread(() -> {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

                    titleText.setText(task.getTitle());
                    descriptionText.setText(task.getDescription());
                    deadlineText.setText(sdf.format(task.getDeadline()));
                    priorityText.setText(task.getPriority().getDescription());
                   
                    statusText.setText(task.getStatus().getDescription());

                    if (task.getStatus() == TaskStatusEnum.COMPLETED && task.getCompletedAt() != null) {
                        cardCompletedAt.setVisibility(View.VISIBLE);
                        completedAtText.setText(sdf.format(task.getCompletedAt()));
                    } else {
                        cardCompletedAt.setVisibility(View.GONE);
                    }
                    
                    userAdapter = new UserAdapter(users);
                    recyclerView.setAdapter(userAdapter);
                });
            }
        }).start();
    }
}
