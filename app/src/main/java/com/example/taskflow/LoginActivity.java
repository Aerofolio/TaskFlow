package com.example.taskflow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.User;
import com.example.taskflow.utils.FormUtils;
import com.example.taskflow.utils.PrefsUtils;

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
                boolean isFormValid = validateForm();
                if (isFormValid){
                    String userEmail = editTextEmailInput.getText().toString();
                    String userPassword = editTextPasswordInput.getText().toString();

                    new Thread(() -> {
                        User userFromDatabase = db.userDao().getUserByEmail(userEmail);
                        if (userFromDatabase != null && userFromDatabase.getPassword().equals(userPassword)) {
                            SharedPreferences prefs = getSharedPreferences(PrefsUtils.APP_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putInt(PrefsUtils.USER_ID, userFromDatabase.id);
                            editor.putString(PrefsUtils.USER_NAME, userFromDatabase.getName());
                            editor.putString(PrefsUtils.USER_COMPANY_CODE, userFromDatabase.getCompanyCode());
                            editor.apply();

                            Intent homeIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(homeIntent);
                        } else {
                            runOnUiThread(() -> 
                                Toast.makeText(LoginActivity.this, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
                            );
                        }
                    }).start();
                }
            }
        });
    }

    private boolean validateForm() {
        boolean isValid = true;
        String userEmail = editTextEmailInput.getText().toString();
        String userPassword = editTextPasswordInput.getText().toString();

        if (userEmail.isEmpty()){
            isValid = false;
            FormUtils.markInvalid(LoginActivity.this, editTextEmailInput, findViewById(R.id.textViewEmailLabel));
        }

        if (userPassword.isEmpty()){
            isValid = false;
            FormUtils.markInvalid(LoginActivity.this, editTextPasswordInput, findViewById(R.id.textViewPasswordLabel));
        }

        return isValid;
    }
}