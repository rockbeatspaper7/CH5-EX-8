package com.example.supermarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class RateActivity extends AppCompatActivity {
    SupermarketInfo currentSupermarket = new SupermarketInfo();
    private TextView displayAverage;
    private RatingBar r1, r2, r3, r4, r5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rate);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Intent intent = getIntent();
        if (intent != null) {
            currentSupermarket.setMarketName(intent.getStringExtra("marketName"));
            currentSupermarket.setAddress(intent.getStringExtra("address"));
        }

        initGoBackButton();
        initSaveButton();
        updateAverageRating();
        setRatingChangeListener();
    }

    protected void initGoBackButton() {
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(b -> {
            Intent intent = new Intent(RateActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }


    private void updateAverageRating() {
        displayAverage = findViewById(R.id.averageView);
        r1 = findViewById(R.id.ratingBar);
        r2 = findViewById(R.id.ratingBar2);
        r3 = findViewById(R.id.ratingBar3);
        r4 = findViewById(R.id.ratingBar4);
        r5 = findViewById(R.id.ratingBar5);

        float average = (r1.getRating() + r2.getRating() + r3.getRating() +
                r4.getRating() + r5.getRating()) / 5;

        displayAverage.setText(String.format("%.1f", average));
    }

    private void setRatingChangeListener() {
        RatingBar.OnRatingBarChangeListener listener = (ratingBar, rating, fromUser) -> updateAverageRating();

        r1.setOnRatingBarChangeListener(listener);
        r2.setOnRatingBarChangeListener(listener);
        r3.setOnRatingBarChangeListener(listener);
        r4.setOnRatingBarChangeListener(listener);
        r5.setOnRatingBarChangeListener(listener);
    }

    private void initSaveButton() {
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            boolean wasSuccessful = false;
            SupermarketDataSource ds = new SupermarketDataSource(RateActivity.this);
            try {
                ds.open();

                if (currentSupermarket.getId() == -1) {
                    wasSuccessful = ds.insertMarket(currentSupermarket);
                    if (wasSuccessful) {
                        int newId = ds.getLastID();
                        currentSupermarket.setId(newId);
                        Log.d("RateActivity", "Supermarket inserted successfully with ID: " + newId);
                    } else {
                        Log.e("RateActivity", "Failed to insert supermarket");
                    }
                } else {
                    wasSuccessful = ds.updateMarket(currentSupermarket);
                    if (wasSuccessful) {
                        Log.d("RateActivity", "Supermarket updated successfully with ID: " + currentSupermarket.getId());
                    } else {
                        Log.e("RateActivity", "Failed to update supermarket");
                    }
                }

                ds.close();
            } catch (Exception e) {
                Log.e("RateActivity", "Database operation failed", e);
            }

            if (!wasSuccessful) {
                Log.e("RateActivity", "Save operation was unsuccessful");
            }
        });
    }

}