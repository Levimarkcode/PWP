package com.example.pwp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity {

    private TextView balanceTextView; // TextView for displaying current balance
    private DatabaseReference databaseReference; // Firebase reference

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Access UI elements
        CardView cardMiscellaneous = findViewById(R.id.card_miscellaneous);
        CardView cardFood = findViewById(R.id.card_food);
        CardView cardRent = findViewById(R.id.card_rent);
        CardView cardCar = findViewById(R.id.card_car);
        ImageView incomeIcon = findViewById(R.id.income);
        balanceTextView = findViewById(R.id.balanceTextView); // Balance TextView

        // Initialize Firebase reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Initialize expenses to 0.00 if not already set
        initializeExpenses();

        // Set click listeners for category cards
        cardMiscellaneous.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, MiscellaneousInputActivity.class);
            startActivity(intent);
        });

        cardFood.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, FoodInputActivity.class);
            startActivity(intent);
        });

        cardRent.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, RentInputActivity.class);
            startActivity(intent);
        });

        cardCar.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CarInputActivity.class);
            startActivity(intent);
        });

        incomeIcon.setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, IncomeInputActivity.class);
            startActivity(intent);
        });

        // Calculate and display the current balance
        calculateAndDisplayBalance();
    }

    /**
     * Method to initialize all expense categories to 0.00 if they don't exist in Firebase.
     */
    private void initializeExpenses() {
        DatabaseReference expensesRef = databaseReference.child("Expenditures");

        // Define default expense categories
        String[] expenseCategories = {"Miscellaneous", "Food", "Rent", "Car"};

        for (String category : expenseCategories) {
            expensesRef.child(category).child("amount").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        // Set the initial amount to 0.00 if it doesn't exist
                        dataSnapshot.getRef().setValue("0.00");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Toast.makeText(HomeActivity.this, "Failed to initialize expenses.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    /**
     * Method to calculate and display the current balance.
     */
    private void calculateAndDisplayBalance() {
        // Initialize income and expenditure amounts
        final double[] totalIncome = {0};  // Using an array for passing the value
        final double[] totalExpenditures = {0};  // Using an array for passing the value

        // Fetch total income
        databaseReference.child("Incomes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot incomeSnapshot) {
                for (DataSnapshot income : incomeSnapshot.getChildren()) {
                    String amountStr = income.child("amount").getValue(String.class);
                    if (amountStr != null) {
                        try {
                            // Parse the amount as a double and add to total income
                            totalIncome[0] += Double.parseDouble(amountStr);
                        } catch (NumberFormatException e) {
                            Toast.makeText(HomeActivity.this, "Invalid income amount format", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                // After income is retrieved, fetch total expenses
                fetchTotalExpenses(totalIncome[0], totalExpenditures);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Failed to fetch income.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Method to fetch total expenses and calculate the balance.
     */
    private void fetchTotalExpenses(final double totalIncome, final double[] totalExpenditures) {
        databaseReference.child("Expenditures").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot expenditureSnapshot) {
                for (DataSnapshot category : expenditureSnapshot.getChildren()) {
                    String amountStr = category.child("amount").getValue(String.class);
                    if (amountStr != null) {
                        try {
                            // Parse the amount as a double and add to total expenditures
                            totalExpenditures[0] += Double.parseDouble(amountStr);
                        } catch (NumberFormatException e) {
                            Toast.makeText(HomeActivity.this, "Invalid expenditure amount format", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                // After both income and expenses are fetched, calculate the balance
                double currentBalance = totalIncome - totalExpenditures[0];

                // Display the balance
                balanceTextView.setText("Current Balance: $" + currentBalance);

                // Show a Toast message for balance update
                Toast.makeText(HomeActivity.this, "Balance updated.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(HomeActivity.this, "Failed to fetch expenses.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
