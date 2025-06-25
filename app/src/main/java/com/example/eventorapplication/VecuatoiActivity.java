package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventorapplication.databinding.ActivityVecuatoiBinding;

public class VecuatoiActivity extends AppCompatActivity {

    ActivityVecuatoiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVecuatoiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnPurchasedTickets.setOnClickListener(v -> {
            Intent intent = new Intent(VecuatoiActivity.this, VedamuaActivity.class);
            startActivity(intent);
        });

        binding.btnPostedEvents.setOnClickListener(v -> {
            Intent intent = new Intent(VecuatoiActivity.this, SukiendadangActivity.class);
            startActivity(intent);
        });
    }
}