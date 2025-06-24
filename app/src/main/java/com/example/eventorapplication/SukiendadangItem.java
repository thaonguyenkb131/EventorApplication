package com.example.eventorapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivitySukiendadangItemBinding;

public class SukiendadangItem extends AppCompatActivity {

    ActivitySukiendadangItemBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySukiendadangItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}