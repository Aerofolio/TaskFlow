package com.example.taskflow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
import com.example.taskflow.model.Task;
import com.example.taskflow.model.User;
import com.example.taskflow.utils.PrefsUtils;

import java.util.ArrayList;
import java.util.List;

public class AddTaskMembersActivity extends AppCompatActivity {
    Button buttonSaveTask;
    RecyclerView recyclerViewAddTaskMembers;
//    private List<User> userList;
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
                //TODO: Adicionar usuários selecionados, obrogar selecionar pelo menos 1

                Intent homeIntent = new Intent(AddTaskMembersActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        recyclerViewAddTaskMembers.setLayoutManager(new LinearLayoutManager(AddTaskMembersActivity.this));
        setUserList();
    }

    private void setUserList() {
        new Thread(() -> {
            SharedPreferences prefs = getSharedPreferences(PrefsUtils.APP_PREFS, MODE_PRIVATE);
            String loggedUserCompanyCode = prefs.getString(PrefsUtils.USER_COMPANY_CODE, "");
            int loggedUserId = prefs.getInt(PrefsUtils.USER_ID, 0);

            List<User> sameCompanyUsers = db.userDao().getUsersByCompanyCode(loggedUserCompanyCode, loggedUserId);


            addUserAdapter = new AddUserAdapter(sameCompanyUsers);
            recyclerViewAddTaskMembers.setAdapter(addUserAdapter);
        }).start();
    }
}