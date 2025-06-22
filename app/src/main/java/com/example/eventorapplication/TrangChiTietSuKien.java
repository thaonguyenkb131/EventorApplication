package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

        //        Tránh che màn hình

        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che nút

            // Đẩy TextView xuống dưới status bar
            View txtTitle = findViewById(R.id.header_layout);
            txtTitle.setPadding(
                    txtTitle.getPaddingLeft(),
                    systemBars.top,
                    txtTitle.getPaddingRight(),
                    txtTitle.getPaddingBottom()
            );

            return insets;
        });

    }
}