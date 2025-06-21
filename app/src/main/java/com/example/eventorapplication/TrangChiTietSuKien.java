package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityTrangChiTietSuKienBinding;

public class TrangChiTietSuKien extends AppCompatActivity {

    ActivityTrangChiTietSuKienBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrangChiTietSuKienBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}