package com.example.pwp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check credentials
                if (username.getText().toString().equals("user") && password.getText().toString().equals("1234")) {
                    Toast.makeText(MainActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                    // Assuming you are in the LoginActivity.java
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); // This will close the login screen so the user cannot return to it by pressing back

                } else {
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
