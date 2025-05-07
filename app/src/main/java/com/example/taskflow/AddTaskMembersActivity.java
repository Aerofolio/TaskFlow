package com.example.taskflow;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.adapters.UserAdapter;
import com.example.taskflow.model.User;

import java.util.ArrayList;
import java.util.List;

public class AddTaskMembersActivity extends AppCompatActivity {
    Button buttonSaveTask;
    RecyclerView recyclerViewAddedTaskMembers;
    private List<User> userList;
    private UserAdapter userAdapter;

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

        buttonSaveTask = findViewById(R.id.buttonSaveTask);
        recyclerViewAddedTaskMembers = findViewById(R.id.recyclerViewAddedTaskMembers);

        buttonSaveTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(AddTaskMembersActivity.this, HomeActivity.class);
                startActivity(homeIntent);
            }
        });

        recyclerViewAddedTaskMembers.setLayoutManager(new LinearLayoutManager(AddTaskMembersActivity.this));

        userList = new ArrayList<>();
        userList.add(new User("José"));
        userList.add(new User("Maria"));
        userList.add(new User("Lucas"));
        userList.add(new User("Ana"));

        userAdapter = new UserAdapter(userList);
        recyclerViewAddedTaskMembers.setAdapter(userAdapter);
    }
}