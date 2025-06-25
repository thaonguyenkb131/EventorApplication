package com.example.eventorapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventorapplication.databinding.ActivityFooterBinding;

public class FooterActivity extends AppCompatActivity {

    ActivityFooterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFooterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}