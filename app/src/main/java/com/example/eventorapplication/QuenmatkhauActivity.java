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

import com.example.eventorapplication.databinding.ActivityQuenmatkhauBinding;

public class QuenmatkhauActivity extends AppCompatActivity {

    ActivityQuenmatkhauBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuenmatkhauBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        Click lấy mã hiện dialog otp
        binding.btnGetCode.setOnClickListener(v -> {
            // Hiển thị dialog nhập mã OTP
            View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_otp, null);

            // Tạo Dialog
            Dialog dialog = new Dialog(v.getContext());
            dialog.setContentView(dialogView);
            dialog.setCancelable(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setGravity(Gravity.CENTER);

            // Xử lý nút
            Button btnConfirm = dialogView.findViewById(R.id.btnConfirmOtp);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(QuenmatkhauActivity.this, CailaimatkhauActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });

            dialog.show();

        });

    }
}