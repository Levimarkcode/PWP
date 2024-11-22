package com.example.pwp;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CarInputActivity extends AppCompatActivity {

    private EditText amountEditText, noteEditText;
    private Button submitButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_input);

        // Initialize views using matching IDs
        amountEditText = findViewById(R.id.amount);
        noteEditText = findViewById(R.id.note);
        submitButton = findViewById(R.id.car_btn);

        // Initialize Firebase Database Reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Car");

        // Set click listener for the submit button
        submitButton.setOnClickListener(view -> {
            String amount = amountEditText.getText().toString().trim();
            String note = noteEditText.getText().toString().trim();

            if (TextUtils.isEmpty(amount)) {
                Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                return;
            }

            saveIncomeToFirebase(amount, note);
        });
    }

    private void saveIncomeToFirebase(String amount, String note) {
        String key = databaseReference.push().getKey();

        Map<String, Object> incomeData = new HashMap<>();
        incomeData.put("amount", amount);
        incomeData.put("note", note);
        incomeData.put("timestamp", System.currentTimeMillis());

        if (key != null) {
            databaseReference.child(key).setValue(incomeData)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Expense added successfully!", Toast.LENGTH_SHORT).show();
                        // Close the current activity and return to the previous one
                        finish();  // This will return to the previous screen (or refresh the previous activity)
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Failed to add expense: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }
}