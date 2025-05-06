package com.example.taskflow;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskflow.model.Task;

import java.util.ArrayList;
import java.util.List;

public class RegisterTaskActivity extends AppCompatActivity {
    Spinner spinnerTaskPriority;
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

        spinnerOptions = new ArrayList<>();
        spinnerOptions.add("Alta");
        spinnerOptions.add("Média");
        spinnerOptions.add("Baixa");

        try{

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    R.layout.item_spinner,
                    spinnerOptions);
            spinnerTaskPriority.setAdapter(adapter);
        } catch (Exception e) {
            e.toString();
        }
    }
}