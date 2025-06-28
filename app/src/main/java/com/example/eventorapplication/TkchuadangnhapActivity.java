package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityTkchuadangnhapBinding;

public class TkchuadangnhapActivity extends AppCompatActivity {

    ActivityTkchuadangnhapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTkchuadangnhapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.cauhoithuonggap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkchuadangnhapActivity.this, CauhoithuonggapActivity.class);
                startActivity(intent);
            }
        });

        binding.chinhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkchuadangnhapActivity.this, ChinhsachActivity.class);
                startActivity(intent);
            }
        });

        binding.txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkchuadangnhapActivity.this, DangkyActivity.class);
                startActivity(intent);
            }
        });

        binding.txtDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkchuadangnhapActivity.this, DangnhapActivity.class);
                startActivity(intent);
            }
        });

    }
}