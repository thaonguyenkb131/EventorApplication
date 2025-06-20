package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityTrangSuKienDaDangBinding;

public class trang_su_kien_da_dang extends AppCompatActivity {

    ActivityTrangSuKienDaDangBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrangSuKienDaDangBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();

    }

    private void addEvents() {
        binding.btnSavedEvents.setOnClickListener(v -> {
            Intent intent = new Intent(trang_su_kien_da_dang.this, TrangVeCuaToi.class);
            startActivity(intent);
        });

        binding.btnPurchasedTickets.setOnClickListener(v -> {
            Intent intent = new Intent(trang_su_kien_da_dang.this, trang_ve_da_mua.class);
            startActivity(intent);
        });

    }
}