package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityPopupSoLuongVeBinding;

public class PopupSoLuongVe extends AppCompatActivity {

    ActivityPopupSoLuongVeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPopupSoLuongVeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}