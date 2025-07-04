package com.example.taskflow;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.Task;
import com.example.taskflow.model.complexTypes.TaskPriorityEnum;
import com.example.taskflow.model.complexTypes.TaskStatusEnum;
import com.example.taskflow.utils.FormUtils;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RegisterTaskActivity extends AppCompatActivity {
    Spinner spinnerTaskPriority;
    EditText editTextTaskTitleInput;
    EditText editTextTaskDescriptionInput;
    EditText editTextTaskDeliveryDateInput;
    Button buttonContinue;
    private List<String> spinnerOptions;

    private Task taskToEdit = null;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            int fixedPadding = (int) getResources().getDisplayMetrics().density * 24; // 24dp em pixels
            v.setPadding(
                    systemBars.left + fixedPadding,
                    systemBars.top + fixedPadding,
                    systemBars.right + fixedPadding,
                    systemBars.bottom + fixedPadding
            );
            return insets;
        });
        db = AppDatabase.getInstance(this);

        spinnerTaskPriority = findViewById(R.id.spinnerTaskPriority);
        buttonContinue = findViewById(R.id.buttonContinue);
        editTextTaskTitleInput = findViewById(R.id.editTextTaskTitleInput);
        editTextTaskDescriptionInput = findViewById(R.id.editTextTaskDescriptionInput);
        editTextTaskDeliveryDateInput = findViewById(R.id.editTextTaskDeliveryDateInput);

        spinnerOptions = new ArrayList<>();
        for (TaskPriorityEnum priority : TaskPriorityEnum.values()) {
            spinnerOptions.add(priority.getDescription());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.item_spinner,
                spinnerOptions
        );
        adapter.setDropDownViewResource(R.layout.item_spinner_dropdown);
        spinnerTaskPriority.setAdapter(adapter);
        spinnerTaskPriority.setBackgroundResource(R.drawable.bg_spinner);


        editTextTaskDeliveryDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterTaskActivity.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {
                            String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                            editTextTaskDeliveryDateInput.setText(date);
                        },
                        year, month, day
                );
                datePickerDialog.show();
            }
        });

        int taskId = getIntent().getIntExtra("TASK_ID", -1);
        if (taskId != -1) {
            new Thread(() -> {
                Task task = db.getInstance(this).taskDao().getTaskById(taskId);
                runOnUiThread(() -> {
                    if (task != null) {
                        taskToEdit = task;
                        fillFormWithTask(task);
                    }
                });
            }).start();
        }

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFormValid = validateForm();
                if (isFormValid) {
                    Timestamp timestamp;
                    try {
                        String dateString = editTextTaskDeliveryDateInput.getText().toString();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date parsedDate = dateFormat.parse(dateString);
                        timestamp = new Timestamp(parsedDate.getTime());
                    } catch (Exception e) {
                        timestamp = new Timestamp(System.currentTimeMillis());
                    }

                    Task task;
                    if (taskToEdit != null) {
                        taskToEdit.setTitle(editTextTaskTitleInput.getText().toString());
                        taskToEdit.setDescription(editTextTaskDescriptionInput.getText().toString());
                        taskToEdit.setDeadline(timestamp);
                        taskToEdit.setPriority(TaskPriorityEnum.values()[(int)spinnerTaskPriority.getSelectedItemId()]);
                        task = taskToEdit;
                    } else {
                        task = new Task(
                                editTextTaskTitleInput.getText().toString(),
                                editTextTaskDescriptionInput.getText().toString(),
                                timestamp,
                                TaskPriorityEnum.values()[(int)spinnerTaskPriority.getSelectedItemId()],
                                TaskStatusEnum.PENDING
                        );
                    }

                    Intent intent = new Intent(RegisterTaskActivity.this, AddTaskMembersActivity.class);
                    intent.putExtra("TASK", task);
                    startActivity(intent);
                }
            }
        });
    }

    private void fillFormWithTask(Task task) {
        editTextTaskTitleInput.setText(task.getTitle());
        editTextTaskDescriptionInput.setText(task.getDescription());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        editTextTaskDeliveryDateInput.setText(sdf.format(task.getDeadline()));
        spinnerTaskPriority.setSelection(task.getPriority().ordinal());
    }

    private boolean validateForm() {
        boolean isValid = true;

        TextInputLayout layoutTaskTitle = findViewById(R.id.layoutTaskTitle);
        TextInputLayout layoutTaskDescription = findViewById(R.id.layoutTaskDescription);
        TextInputLayout layoutTaskDeliveryDate = findViewById(R.id.layoutTaskDeliveryDate);

        String taskTitle = editTextTaskTitleInput.getText().toString();
        String taskDescription = editTextTaskDescriptionInput.getText().toString();
        String taskDeliveryDate = editTextTaskDeliveryDateInput.getText().toString();

        if (taskTitle.isEmpty()) {
            layoutTaskTitle.setError("Campo obrigatório");
            isValid = false;
        } else {
            layoutTaskTitle.setError(null);
        }

        if (taskDescription.isEmpty()) {
            layoutTaskDescription.setError("Campo obrigatório");
            isValid = false;
        } else {
            layoutTaskDescription.setError(null);
        }

        if (taskDeliveryDate.isEmpty()) {
            layoutTaskDeliveryDate.setError("Campo obrigatório");
            isValid = false;
        } else {
            layoutTaskDeliveryDate.setError(null);
        }

        return isValid;
    }

}