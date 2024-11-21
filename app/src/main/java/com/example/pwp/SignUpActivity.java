package com.example.pwp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    protected Button signUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        // Initialize views
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        signUpButton = findViewById(R.id.signUpButton);

        // Set click listener for the Sign-Up button
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (validateInputs(email, password)) {
                    // TODO: Implement sign-up logic (e.g., Firebase Authentication or local storage)

                    // For now, show a success message and redirect to the login page
                    Toast.makeText(SignUpActivity.this, "Sign-Up Successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, MainActivity.class); // Navigate to login page
                    startActivity(intent);
                    finish(); // Close the sign-up activity
                }
            }
        });
    }

    // Validate email and password inputs
    private boolean validateInputs(String email, String password) {
        if (TextUtils.isEmpty(email)) {
            emailEditText.setError("Email is required");
            return false;
        }
        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Password is required");
            return false;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            return false;
        }
        return true;
    }
}
