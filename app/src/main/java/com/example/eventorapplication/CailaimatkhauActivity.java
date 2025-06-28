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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventorapplication.databinding.ActivityCailaimatkhauBinding;

public class CailaimatkhauActivity extends AppCompatActivity {

    ActivityCailaimatkhauBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCailaimatkhauBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị dialog nhập mã OTP
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_doimkthanhcong, null);

                // Tạo Dialog
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(dialogView);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.CENTER);

                // Xử lý nút
                Button btnConfirm = dialogView.findViewById(R.id.btnGoHome);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CailaimatkhauActivity.this, TrangchuActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

    }
}