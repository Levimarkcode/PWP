package com.example.pwp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pwp.HomeActivity;
import com.example.pwp.R;
import com.example.pwp.SignUpActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button loginButton;
    private TextView signupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the views
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        loginButton = findViewById(R.id.loginButton);
        signupText = findViewById(R.id.signup);

        // Set click listener for login button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (validateInputs(email, password)) {
                    // TODO: Implement authentication logic (e.g., Firebase, local DB)
                    // If login is successful, navigate to HomePageActivity
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // Close the login activity
                } else {
                    Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set click listener for sign-up text
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to SignUpActivity
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    // Validate email and password inputs
    private boolean validateInputs(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            return false;
        }
        return true;
    }
}
