package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventorapplication.databinding.ActivityDangkyBinding;

public class DangkyActivity extends AppCompatActivity {

    ActivityDangkyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangkyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangkyActivity.this, DangnhapActivity.class);
                startActivity(intent);
            }
        });

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangkyActivity.this, TrangchuActivity.class);
                startActivity(intent);
            }
        });

    }
}