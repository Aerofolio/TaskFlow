package com.example.taskflow;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.User;

public class RegisterUserActivity extends AppCompatActivity {
    Button buttonRegister;
    TextView editTextFullNameInput;
    TextView editTextPasswordInput;
    TextView editTextRepeatPasswordInput;
    TextView editTextEmailInput;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonRegister = findViewById(R.id.buttonRegister);
        editTextFullNameInput = findViewById(R.id.editTextFullNameInput);
        editTextPasswordInput = findViewById(R.id.editTextPasswordInput);
        editTextRepeatPasswordInput = findViewById(R.id.editTextRepeatPasswordInput);
        editTextEmailInput = findViewById(R.id.editTextEmailInput);

        db = AppDatabase.getInstance(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userFullName = editTextFullNameInput.getText().toString();
                String userPassword = editTextPasswordInput.getText().toString();
                String userRepeatPassword = editTextRepeatPasswordInput.getText().toString();
                String userEmail = editTextEmailInput.getText().toString();

                if (!userFullName.isEmpty()
                    && !userPassword.isEmpty()
                    && !userRepeatPassword.isEmpty()
                    && !userEmail.isEmpty()
                    && userPassword.equals(userRepeatPassword)) {

                    new Thread(() -> {
                            User newUser = new User(userFullName);
                            newUser.password = userPassword;
                            newUser.email = userEmail;
                            db.userDao().addUser(newUser);
                            finish();
                    }).start();
                }
            }
        });
    }
}