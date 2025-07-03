package com.example.taskflow;

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
import com.example.taskflow.model.Task;
import com.example.taskflow.model.complexTypes.TaskPriorityEnum;

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

        recyclerTasks = findViewById(R.id.recyclerTasks);
        recyclerTasks.setLayoutManager(new LinearLayoutManager(this));

        taskList = new ArrayList<Task>();
//        taskList.add(new Task("Tarefa 1", "Descrição da tarefa 1", "2025-05-05", TaskPriorityEnum.MEDIUM));
//        taskList.add(new Task("Tarefa 2", "Descrição da tarefa 2", "2025-06-06", TaskPriorityEnum.MEDIUM));
//        taskList.add(new Task("Tarefa 3", "Descrição da tarefa 3", "2025-07-07", TaskPriorityEnum.MEDIUM));

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
    }

    private void setCalendarDates() {
        List<Date> dates = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        for (int i = 0; i < 5; i++) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        calendarDaysList = dates;
    }
}