package com.example.taskflow;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.taskflow.database.AppDatabase;
import com.example.taskflow.model.User;
import com.example.taskflow.utils.FormUtils;

public class RegisterUserActivity extends AppCompatActivity {
    Button buttonRegister;
    TextView editTextFullNameInput;
    TextView editTextPasswordInput;
    TextView editTextRepeatPasswordInput;
    TextView editTextEmailInput;
    TextView editTextCompanyCodeInput;
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
        editTextCompanyCodeInput = findViewById(R.id.editTextCompanyCodeInput);

        db = AppDatabase.getInstance(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFormValid = validateForm();
                if (isFormValid) {
                    String userFullName = editTextFullNameInput.getText().toString();
                    String userPassword = editTextPasswordInput.getText().toString();
                    String userEmail = editTextEmailInput.getText().toString();
                    String companyCode = editTextCompanyCodeInput.getText().toString();

                    new Thread(() -> {
                            User newUser = new User(userFullName, userPassword, userEmail, companyCode);
                            db.userDao().addUser(newUser);
                            finish();
                    }).start();
                }
            }
        });
    }

    private boolean validateForm(){
        boolean isValid = true;
        String userFullName = editTextFullNameInput.getText().toString();
        String userPassword = editTextPasswordInput.getText().toString();
        String userRepeatPassword = editTextRepeatPasswordInput.getText().toString();
        String userEmail = editTextEmailInput.getText().toString();
        String companyCode = editTextCompanyCodeInput.getText().toString();

        if (userFullName.isEmpty()){
            isValid = false;
            FormUtils.markInvalid(RegisterUserActivity.this, editTextFullNameInput, findViewById(R.id.textViewFullNameLabel));
        }

        if (userPassword.isEmpty()){
            isValid = false;
            FormUtils.markInvalid(RegisterUserActivity.this, editTextPasswordInput, findViewById(R.id.textViewPasswordLabel));
        }

        if (userRepeatPassword.isEmpty()){
            isValid = false;
            FormUtils.markInvalid(RegisterUserActivity.this, editTextRepeatPasswordInput, findViewById(R.id.textViewRepeatPasswordLabel));
        }

        if (userEmail.isEmpty()){
            isValid = false;
            FormUtils.markInvalid(RegisterUserActivity.this, editTextEmailInput, findViewById(R.id.textViewEmailLabel));
        }

        if (companyCode.isEmpty()){
            isValid = false;
            FormUtils.markInvalid(RegisterUserActivity.this, editTextCompanyCodeInput, findViewById(R.id.textViewCompanyCodeLabel));
        }

        if (!userPassword.isEmpty() && !userRepeatPassword.isEmpty() && !userPassword.equals(userRepeatPassword)){
            isValid = false;
            FormUtils.markInvalid(RegisterUserActivity.this, editTextPasswordInput, findViewById(R.id.textViewPasswordLabel), ContextCompat.getString(RegisterUserActivity.this, R.string.text_passwords_equals));
            FormUtils.markInvalid(RegisterUserActivity.this, editTextRepeatPasswordInput, findViewById(R.id.textViewRepeatPasswordLabel), ContextCompat.getString(RegisterUserActivity.this, R.string.text_passwords_equals));
            editTextPasswordInput.setText("");
            editTextRepeatPasswordInput.setText("");
        }

        return isValid;
    }
}