package com.example.pwp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Access CardViews
        CardView cardMiscellaneous = findViewById(R.id.card_miscellaneous);
        CardView cardFood = findViewById(R.id.card_food);
        CardView cardRent = findViewById(R.id.card_rent);
        CardView cardCar = findViewById(R.id.card_car);
        ImageView incomeIcon = findViewById(R.id.income);

        // Set click listeners
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
    }
}