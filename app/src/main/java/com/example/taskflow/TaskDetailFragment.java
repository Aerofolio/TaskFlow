package com.example.taskflow;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.adapters.UserAdapter;
import com.example.taskflow.business.HistoryBusiness;
import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.TaskWithUsers;
import com.example.taskflow.model.User;
import com.example.taskflow.model.complexTypes.TaskStatusEnum;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
    private LinearLayout buttonContainer;
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
        buttonContainer = view.findViewById(R.id.buttonContainer);

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

                    buttonContainer.removeAllViews();

                    if (task.getStatus() == TaskStatusEnum.PENDING || task.getStatus() == TaskStatusEnum.IN_PROGRESS) {
                        buttonContainer.setVisibility(View.VISIBLE);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1f
                        );
                        params.setMargins(8, 0, 8, 0);

                        Button btnEdit = new Button(requireContext());
                        btnEdit.setText("Editar");
                        btnEdit.setLayoutParams(params);
                        btnEdit.setTextColor(Color.WHITE);
                        btnEdit.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue));
                        btnEdit.setOnClickListener(v -> {
                        /*    Intent intent = new Intent(requireContext(), EditTaskActivity.class);
                            intent.putExtra("TASK_ID", task.getId());
                            startActivity(intent);
                        */});
                        buttonContainer.addView(btnEdit);

                        if (task.getStatus() == TaskStatusEnum.PENDING) {
                            Button btnStart = new Button(requireContext());
                            btnStart.setText("Iniciar Tarefa");
                            btnStart.setLayoutParams(params);
                            btnStart.setTextColor(Color.WHITE);
                            btnStart.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green));
                            btnStart.setOnClickListener(v -> {
                                startTask(task);
                            });
                            buttonContainer.addView(btnStart);
                        } else if (task.getStatus() == TaskStatusEnum.IN_PROGRESS) {
                            Button btnComplete = new Button(requireContext());
                            btnComplete.setText("Concluir");
                            btnComplete.setLayoutParams(params);
                            btnComplete.setTextColor(Color.WHITE);
                            btnComplete.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.yellow));
                            btnComplete.setOnClickListener(v -> {
                                completeTask(task);
                            });
                            buttonContainer.addView(btnComplete);
                        }
                    } else {
                        buttonContainer.setVisibility(View.GONE);
                    }

                    userAdapter = new UserAdapter(users);
                    recyclerView.setAdapter(userAdapter);
                });
            }
        }).start();
    }

    private void startTask(Task task) {
        new Thread(() -> {
            db.taskDao().updateStatus(task.getId(), TaskStatusEnum.IN_PROGRESS.getCode());
            new HistoryBusiness(requireContext()).registerHistory(taskId, "Tarefa iniciada.");
            requireActivity().runOnUiThread(this::loadTaskFromDatabase);
        }).start();
    }

    private void completeTask(Task task) {
        new Thread(() -> {
            db.taskDao().completeTask(task.getId(), TaskStatusEnum.COMPLETED.getCode(), new Timestamp(System.currentTimeMillis()));
            new HistoryBusiness(requireContext()).registerHistory(taskId, "Tarefa concluída.");
            requireActivity().runOnUiThread(this::loadTaskFromDatabase);
        }).start();
    }
}
