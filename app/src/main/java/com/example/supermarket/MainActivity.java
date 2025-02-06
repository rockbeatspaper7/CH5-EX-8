package com.example.supermarket;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    protected SupermarketInfo currentSupermarket = new SupermarketInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

            //test
        });
        initRateButton();
        initTextChangedEvents();
    }
    protected void initRateButton() {
        Button rateButton = findViewById(R.id.rateIntentButton);
        rateButton.setOnClickListener(b -> {
            Intent intent = new Intent(MainActivity.this, RateActivity.class);

            intent.putExtra("marketName", currentSupermarket.getMarketName());
            intent.putExtra("address", currentSupermarket.getAddress());

            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }

    private void initTextChangedEvents() {
        final EditText editMarket = findViewById(R.id.marketNameEdit);
        editMarket.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentSupermarket.setMarketName(editMarket.getText().toString());
            }
        });

        final EditText editAddress = findViewById(R.id.addressEdit);
        editMarket.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentSupermarket.setAddress(editAddress.getText().toString());
            }
        });
    }

}