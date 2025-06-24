package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityHeaderChiTietSuKienBinding;

public class HeaderChiTietSuKien extends AppCompatActivity {

    ActivityHeaderChiTietSuKienBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHeaderChiTietSuKienBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Sự kiện click vào LinearLayout có id="iconSearch"
        binding.iconSearch.setOnClickListener(v -> {
            Intent intent = new Intent(HeaderChiTietSuKien.this, Timkiem.class);
            startActivity(intent);
        });

        // Sự kiện click vào nút chat có id = "iconChat"
        binding.iconChat.setOnClickListener(v -> {
            Intent intent = new Intent(HeaderChiTietSuKien.this, Tinnhan.class);
            startActivity(intent);
        });

        // Tránh che màn hình
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, systemBars.top, 0, systemBars.bottom);
            return insets;
        });
    }
}