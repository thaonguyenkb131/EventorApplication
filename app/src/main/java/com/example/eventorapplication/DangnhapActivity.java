package com.example.eventorapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityDangnhapBinding;

public class DangnhapActivity extends AppCompatActivity {

    ActivityDangnhapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangnhapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangnhapActivity.this, DangkyActivity.class);
                startActivity(intent);
            }
        });

        binding.Googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Mở dialog chọn tài khoản Google
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_chontaikhoan, null);

                // Tạo Dialog
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(dialogView);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.BOTTOM);

                // Xử lý nút đăng nhập
                Button btnConfirm = dialogView.findViewById(R.id.btnConfirmLogin);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xử lý logic đăng nhập tại đây
                        Toast.makeText(v.getContext(), "Đã chọn tài khoản và đăng nhập", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DangnhapActivity.this, TrangchuActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangnhapActivity.this, TrangchuActivity.class);
                startActivity(intent);
            }
        });

        binding.txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangnhapActivity.this, QuenmatkhauActivity.class);
                startActivity(intent);
            }
        });
    }
}