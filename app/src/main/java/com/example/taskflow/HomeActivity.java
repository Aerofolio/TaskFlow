package com.example.taskflow;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.adapters.AddUserAdapter;
import com.example.taskflow.adapters.TaskAdapter;
import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.User;
import com.example.taskflow.model.complexTypes.TaskPriorityEnum;
import com.example.taskflow.utils.PrefsUtils;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
//    private TaskAdapter taskAdapter;
//    private List<Task> taskList;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private TextView welcomeText;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        drawerLayout = findViewById(R.id.drawerLayout);
        db = AppDatabase.getInstance(this);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

//        taskList = new ArrayList<Task>();
//        taskList.add(new Task("Tarefa 1", "Descrição da tarefa 1", "2025-05-05", TaskPriorityEnum.HIGH));
//        taskList.add(new Task("Tarefa 2", "Descrição da tarefa 2", "2025-06-06", TaskPriorityEnum.HIGH));
//        taskList.add(new Task("Tarefa 3", "Descrição da tarefa 3", "2025-07-07", TaskPriorityEnum.HIGH));
//
//        taskAdapter = new TaskAdapter(taskList);
//        recyclerView.setAdapter(taskAdapter);

        welcomeText = findViewById(R.id.textView3);
        SharedPreferences prefs = getSharedPreferences(PrefsUtils.APP_PREFS, MODE_PRIVATE);
        String loggedUserName = prefs.getString(PrefsUtils.USER_NAME, "");
        welcomeText.setText("Olá " + loggedUserName);

        setTaskList();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setTaskList() {
        new Thread(() -> {
            //TODO: Pegar somente tasks do usuário logado
            //TODO: Ordernar por prioridade
            //TODO: Filtrar apenas tasks não concluídas

            List<Task> taskList = db.taskDao().getTasks();

            TaskAdapter taskAdapter = new TaskAdapter(taskList);
            recyclerView.setAdapter(taskAdapter);
        }).start();
    }

}