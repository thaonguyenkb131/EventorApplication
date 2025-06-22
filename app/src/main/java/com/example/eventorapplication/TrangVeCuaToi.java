package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityTrangVeCuaToiBinding;

public class TrangVeCuaToi extends AppCompatActivity {

    ActivityTrangVeCuaToiBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrangVeCuaToiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnPurchasedTickets.setOnClickListener(v -> {
            Intent intent = new Intent(TrangVeCuaToi.this, trang_ve_da_mua.class);
            startActivity(intent);
        });

        binding.btnPostedEvents.setOnClickListener(v -> {
            Intent intent = new Intent(TrangVeCuaToi.this, trang_su_kien_da_dang.class);
            startActivity(intent);
        });
    }
}