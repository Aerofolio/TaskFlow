package com.example.taskflow;

import android.content.Intent;
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

import java.io.Console;

public class LoginActivity extends AppCompatActivity {
    TextView textViewRegisterLink;
    Button buttonLogin;
    TextView editTextEmailInput;
    TextView editTextPasswordInput;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textViewRegisterLink = findViewById(R.id.textViewRegisterLink);
        buttonLogin = findViewById(R.id.buttonLogin);
        editTextEmailInput = findViewById(R.id.editTextEmailInput);
        editTextPasswordInput = findViewById(R.id.editTextPasswordInput);

        db = AppDatabase.getInstance(this);

        textViewRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(registerIntent);
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = editTextEmailInput.getText().toString();
                String userPassword = editTextPasswordInput.getText().toString();

                new Thread(() -> {
                    User userFromDatabase = db.userDao().getUserByEmail(userEmail);
                    if (userFromDatabase != null && userFromDatabase.password.equals(userPassword)) {
                        Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(homeIntent);
                    }
                }).start();
            }
        });
    }
}