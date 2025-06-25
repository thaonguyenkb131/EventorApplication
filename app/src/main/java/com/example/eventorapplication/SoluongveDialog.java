package com.example.eventorapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventorapplication.databinding.DialogSoluongveBinding;

public class SoluongveDialog extends AppCompatActivity {

    DialogSoluongveBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogSoluongveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}