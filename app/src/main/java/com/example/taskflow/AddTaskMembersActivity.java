package com.example.taskflow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
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
import com.example.taskflow.business.HistoryBusiness;
import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.TaskUserCrossRef;
import com.example.taskflow.model.User;
import com.example.taskflow.utils.PrefsUtils;

import java.util.List;

public class AddTaskMembersActivity extends AppCompatActivity {
    Button buttonSaveTask;
    RecyclerView recyclerViewAddTaskMembers;
    private AddUserAdapter addUserAdapter;
    private AppDatabase db;

    private Task currentTask;

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

        currentTask = (Task) getIntent().getSerializableExtra("TASK");

        recyclerViewAddTaskMembers.setLayoutManager(new LinearLayoutManager(this));
        setUserList();

        buttonSaveTask.setOnClickListener(v -> {
            List<User> selectedUsers = addUserAdapter.getSelectedUsers();

            if (selectedUsers == null || selectedUsers.isEmpty()) {
                Toast.makeText(AddTaskMembersActivity.this, "Selecione pelo menos um membro!", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                if (currentTask == null) {
                    runOnUiThread(() -> Toast.makeText(AddTaskMembersActivity.this, "Erro: tarefa inválida", Toast.LENGTH_SHORT).show());
                    return;
                }

                if (currentTask.getId() > 0) {
                    db.taskDao().updateTask(currentTask);

                    db.taskUserDao().deleteByTaskId(currentTask.getId());

                    for (User user : selectedUsers) {
                        db.taskUserDao().insertTaskUserCrossRef(new TaskUserCrossRef(currentTask.getId(), user.getId()));
                    }

                    new HistoryBusiness(this).registerHistory(currentTask.getId(), "Tarefa editada.");

                } else {
                    long taskId = db.taskDao().addTask(currentTask);
                    currentTask.setId((int) taskId);

                    for (User user : selectedUsers) {
                        db.taskUserDao().insertTaskUserCrossRef(new TaskUserCrossRef(currentTask.getId(), user.getId()));
                    }

                    new HistoryBusiness(this).registerHistory(currentTask.getId(), "Tarefa criada.");
                }

                runOnUiThread(() -> {
                    Toast.makeText(AddTaskMembersActivity.this, "Tarefa salva com sucesso!", Toast.LENGTH_SHORT).show();
                    Intent homeIntent = new Intent(AddTaskMembersActivity.this, HomeActivity.class);
                    startActivity(homeIntent);
                    finish();
                });

            }).start();
        });
    }

    private void setUserList() {
        new Thread(() -> {
            SharedPreferences prefs = getSharedPreferences(PrefsUtils.APP_PREFS, MODE_PRIVATE);
            String loggedUserCompanyCode = prefs.getString(PrefsUtils.USER_COMPANY_CODE, "");

            List<User> sameCompanyUsers = db.userDao().getUsersByCompanyCode(loggedUserCompanyCode);

            runOnUiThread(() -> {
                addUserAdapter = new AddUserAdapter(sameCompanyUsers);

                // Se estiver editando, já marca os usuários selecionados
                if (currentTask != null && currentTask.getId() > 0) {
                    new Thread(() -> {
                        List<User> taskUsers = db.taskUserDao().getUsersByTaskId(currentTask.getId());
                        runOnUiThread(() -> addUserAdapter.setSelectedUsers(taskUsers));
                    }).start();
                }

                recyclerViewAddTaskMembers.setAdapter(addUserAdapter);
            });
        }).start();
    }
}
