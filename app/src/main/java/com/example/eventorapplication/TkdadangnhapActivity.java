package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTkdadangnhapBinding;

public class TkdadangnhapActivity extends BaseActivity<ActivityTkdadangnhapBinding> {

    @Override
    protected ActivityTkdadangnhapBinding inflateBinding() {
        return ActivityTkdadangnhapBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Footer tách biệt với thanh điều hướng (giống trang chủ, thông báo)
        View rootView = findViewById(R.id.main); // Layout gốc có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            int bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
            v.setPadding(0, 0, 0, 0); // Không thêm padding cho rootView

            // Đẩy header xuống dưới status bar nếu có
            View txtTitle = findViewById(R.id.header);
            if (txtTitle != null) {
                txtTitle.setPadding(
                    txtTitle.getPaddingLeft(),
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    txtTitle.getPaddingRight(),
                    txtTitle.getPaddingBottom()
                );
            }

            // Footer tách biệt với thanh điều hướng
            View footer = findViewById(R.id.footerLayout);
            if (footer != null) {
                footer.setPadding(
                    footer.getPaddingLeft(),
                    footer.getPaddingTop(),
                    footer.getPaddingRight(),
                    bottomInset
                );
            }

            return insets;
        });

        binding.caidattk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, CaidattaikhoanActivity.class);
                startActivity(intent);
            }
        });

        binding.trangcanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, TrangcanhanActivity.class);
                startActivity(intent);
            }
        });

        binding.bonuspoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, DiemthuongActivity.class);
                startActivity(intent);
            }
        });

        binding.thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, TaikhoanthanhtoanActivity.class);
                startActivity(intent);
            }
        });

        binding.chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, ChatbotActivity.class);
                startActivity(intent);
            }
        });
    }
}