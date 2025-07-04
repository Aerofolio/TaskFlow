package com.example.taskflow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
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
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Task> allTasks = new ArrayList<>();
    private TaskAdapter taskAdapter;
    private EditText searchEditText;
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

        searchEditText = findViewById(R.id.editTextText2);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterTasks(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_logout) {
                SharedPreferences.Editor editor = getSharedPreferences(PrefsUtils.APP_PREFS, MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();

                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            }
            return false;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));

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
            SharedPreferences prefs = getSharedPreferences(PrefsUtils.APP_PREFS, MODE_PRIVATE);
            int loggedUserId = prefs.getInt(PrefsUtils.USER_ID, 0);

            List<Task> taskList = db.taskDao().getTasksAssignedToUser(loggedUserId);

            allTasks.clear();
            allTasks.addAll(taskList);

            runOnUiThread(() -> {
                taskAdapter = new TaskAdapter(allTasks);
                recyclerView.setAdapter(taskAdapter);
            });
        }).start();
    }


    private void filterTasks(String query) {
        List<Task> filteredList = new ArrayList<>();
        for (Task task : allTasks) {
            if (task.getTitle().toLowerCase().contains(query.toLowerCase()) || task.getDescription().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(task);
            }
        }

        runOnUiThread(() -> {
            taskAdapter.setTasks(filteredList);
            taskAdapter.notifyDataSetChanged();
        });
    }

}