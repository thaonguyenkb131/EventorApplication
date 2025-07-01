package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTkchuadangnhapBinding;

public class TkchuadangnhapActivity extends BaseActivity<ActivityTkchuadangnhapBinding> {


    @Override
    protected ActivityTkchuadangnhapBinding inflateBinding() {
        return binding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tránh che màn hình

        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che nút

            // Đẩy TextView xuống dưới status bar
            View txtTitle = findViewById(R.id.imgCover);
            if (txtTitle != null) {
                txtTitle.setPadding(
                        txtTitle.getPaddingLeft(),
                        txtTitle.getPaddingTop(),
                        txtTitle.getPaddingRight(),
                        txtTitle.getPaddingBottom()
                );
            }

            return insets;
        });

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