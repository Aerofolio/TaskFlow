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

import com.example.taskflow.model.Task;
import com.example.taskflow.model.complexTypes.TaskPriorityEnum;
import com.example.taskflow.utils.FormUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisterTaskActivity extends AppCompatActivity {
    Spinner spinnerTaskPriority;
    EditText editTextTaskTitleInput;
    EditText editTextTaskDescriptionInput;
    EditText editTextTaskDeliveryDateInput;
    Button buttonContinue;
    private List<String> spinnerOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_task);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
                spinnerOptions);
        spinnerTaskPriority.setAdapter(adapter);

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
                            // Format the date as you like
                            String date = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear);
                            editTextTaskDeliveryDateInput.setText(date);
                        },
                        year, month, day
                );
                datePickerDialog.show();
            }
        });

        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFormValid = validateForm();
                if (isFormValid) {
                    Task createdTask = new Task(
                        editTextTaskTitleInput.getText().toString(),
                        editTextTaskDescriptionInput.getText().toString(),
                        editTextTaskDeliveryDateInput.getText().toString(),
                        TaskPriorityEnum.values()[(int)spinnerTaskPriority.getSelectedItemId()]);

                    Intent addTaskMembersIntent = new Intent(RegisterTaskActivity.this, AddTaskMembersActivity.class);
                    addTaskMembersIntent.putExtra("createdTask", createdTask);
                    startActivity(addTaskMembersIntent);
                }
            }
        });
    }

    private boolean validateForm() {
        boolean isValid = true;
        String taskTitle = editTextTaskTitleInput.getText().toString();
        String taskDescription = editTextTaskDescriptionInput.getText().toString();
        String taskDeliveryDate = editTextTaskDeliveryDateInput.getText().toString();

        if (taskTitle.isEmpty()){
            isValid = false;
            FormUtils.markInvalid(RegisterTaskActivity.this, editTextTaskTitleInput, findViewById(R.id.textViewTaskTitleLabel));
        }

        if (taskDescription.isEmpty()){
            isValid = false;
            FormUtils.markInvalid(RegisterTaskActivity.this, editTextTaskDescriptionInput, findViewById(R.id.textViewTaskDescriptionLabel));
        }

        //TODO: Validar formato da data
        if (taskDeliveryDate.isEmpty()){
            isValid = false;
            FormUtils.markInvalid(RegisterTaskActivity.this, editTextTaskDeliveryDateInput, findViewById(R.id.textViewTaskDeliveryDateLabel));
        }

        return isValid;
    }
}