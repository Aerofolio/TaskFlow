package com.example.taskflow;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskflow.adapters.CalendarDayAdapter;
import com.example.taskflow.adapters.TaskAdapter;
import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.complexTypes.TaskPriorityEnum;
import com.example.taskflow.utils.PrefsUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private RecyclerView recyclerTasks;
    private RecyclerView recyclerViewCalendarDays;
    private TaskAdapter taskAdapter;
    private CalendarDayAdapter calendarDayAdapter;
    private List<Task> taskList;
    private List<Date> calendarDaysList;
    private Date selectedDate;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calendar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.container), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        selectedDate = new Date();
        db = AppDatabase.getInstance(this);

        recyclerTasks = findViewById(R.id.recyclerTasks);
        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<Task>();
        setTaskList();
        taskAdapter = new TaskAdapter(taskList);
        recyclerTasks.setAdapter(taskAdapter);

        recyclerViewCalendarDays = findViewById(R.id.recyclerViewCalendarDays);
        recyclerViewCalendarDays.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        );
        setCalendarDates();
        calendarDayAdapter = new CalendarDayAdapter(calendarDaysList, selectedDate);
        recyclerViewCalendarDays.setAdapter(calendarDayAdapter);
        recyclerViewCalendarDays.addItemDecoration(new RecyclerView.ItemDecoration() {
            private final int space = 24; // espaço em pixels

            @Override
            public void getItemOffsets(@NonNull android.graphics.Rect outRect, @NonNull View view, RecyclerView parent, @NonNull RecyclerView.State state) {
                int position = parent.getChildAdapterPosition(view);
                if (position == 0) {
                    outRect.left = space;
                }
                outRect.right = space;
            }
        });

        calendarDayAdapter.setOnDayClickListener(date -> {
            selectedDate = date;
            calendarDaysList = getCenteredDates(selectedDate);
            calendarDayAdapter = new CalendarDayAdapter(calendarDaysList, selectedDate);
            calendarDayAdapter.setOnDayClickListener(this::onDayClicked); // reatribua o listener
            recyclerViewCalendarDays.setAdapter(calendarDayAdapter);
        });
    }

    private void setTaskList() {
        new Thread(() -> {
            SharedPreferences prefs = getSharedPreferences(PrefsUtils.APP_PREFS, MODE_PRIVATE);
            int loggedUserId = prefs.getInt(PrefsUtils.USER_ID, 0);

            List<Task> dayTasks = db.taskDao().getTasksAssignedToUserOnDate(loggedUserId, getDateWithoutTime(selectedDate));

            taskList.clear();
            taskList.addAll(dayTasks);

            runOnUiThread(() -> {
                taskAdapter = new TaskAdapter(taskList);
                recyclerTasks.setAdapter(taskAdapter);
            });
        }).start();
    }

    private Timestamp getDateWithoutTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new Timestamp(cal.getTime().getTime());
    }

    private void setCalendarDates() {
        calendarDaysList = getCenteredDates(selectedDate);
    }

    private List<Date> getCenteredDates(Date centerDate) {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(centerDate);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        for (int i = 0; i < 5; i++) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dates;
    }

    private void onDayClicked(Date date) {
        selectedDate = date;
        calendarDaysList = getCenteredDates(selectedDate);
        calendarDayAdapter = new CalendarDayAdapter(calendarDaysList, selectedDate);
        calendarDayAdapter.setOnDayClickListener(this::onDayClicked);
        recyclerViewCalendarDays.setAdapter(calendarDayAdapter);
        setTaskList();
    }
}