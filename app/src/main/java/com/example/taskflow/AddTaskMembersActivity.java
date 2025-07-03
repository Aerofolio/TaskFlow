package com.example.taskflow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.adapters.AddUserAdapter;
import com.example.taskflow.adapters.UserAdapter;
import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.HistoryItem;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.TaskUserCrossRef;
import com.example.taskflow.model.User;
import com.example.taskflow.utils.PrefsUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTaskMembersActivity extends AppCompatActivity {
    Button buttonSaveTask;
    RecyclerView recyclerViewAddTaskMembers;
    private AddUserAdapter addUserAdapter;
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task_members);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = AppDatabase.getInstance(this);

        buttonSaveTask = findViewById(R.id.buttonSaveTask);
        recyclerViewAddTaskMembers = findViewById(R.id.recyclerViewAddTaskMembers);

        buttonSaveTask.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
            @Override
            public void onClick(View v) {
                Task createdTask = getIntent().getSerializableExtra("createdTask", Task.class);

                new Thread(() -> {
                    long taskId = db.taskDao().addTask(createdTask);
                    createdTask.id = (int) taskId;

                    List<User> selectedUsers = addUserAdapter.getSelectedUsers();

                    if (selectedUsers == null || selectedUsers.isEmpty()) {
                        runOnUiThread(() -> {
                            Toast.makeText(AddTaskMembersActivity.this, "Selecione pelo menos um membro!", Toast.LENGTH_SHORT).show();
                        });
                        return;
                    }

                    for (User user : selectedUsers) {
                        db.taskUserDao().insertTaskUserCrossRef(new TaskUserCrossRef(createdTask.id, user.getId()));
                    }

                    createHistory(taskId);

                    Intent homeIntent = new Intent(AddTaskMembersActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                }).start();
            }
        });


        recyclerViewAddTaskMembers.setLayoutManager(new LinearLayoutManager(AddTaskMembersActivity.this));
        setUserList();
    }

    private void createHistory(long taskId) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        SharedPreferences prefs = getSharedPreferences(PrefsUtils.APP_PREFS, MODE_PRIVATE);
        int userId = prefs.getInt(PrefsUtils.USER_ID, -1);

        HistoryItem item = new HistoryItem(taskId, userId, "Tarefa criada", timestamp);

        new Thread(() -> db.historyItemDao().insert(item)).start();
    }
    private void setUserList() {
        new Thread(() -> {
            SharedPreferences prefs = getSharedPreferences(PrefsUtils.APP_PREFS, MODE_PRIVATE);
            String loggedUserCompanyCode = prefs.getString(PrefsUtils.USER_COMPANY_CODE, "");

            List<User> sameCompanyUsers = db.userDao().getUsersByCompanyCode(loggedUserCompanyCode);
            addUserAdapter = new AddUserAdapter(sameCompanyUsers);
            recyclerViewAddTaskMembers.setAdapter(addUserAdapter);
        }).start();
    }
}