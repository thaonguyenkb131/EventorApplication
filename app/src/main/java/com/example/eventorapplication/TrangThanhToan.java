package com.example.eventorapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityTrangThanhToanBinding;

public class TrangThanhToan extends AppCompatActivity {

    ActivityTrangThanhToanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrangThanhToanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(TrangThanhToan.this);
                dialog.setContentView(R.layout.activity_popup_thanh_toan_thanh_cong);

                dialog.show();
            }
        });
    }
}