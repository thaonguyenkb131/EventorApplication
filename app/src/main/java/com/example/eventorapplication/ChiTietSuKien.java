package com.example.eventorapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityChiTietSuKienBinding;

public class ChiTietSuKien extends AppCompatActivity {

    ActivityChiTietSuKienBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChiTietSuKienBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnContact.setOnClickListener(v -> {
            Intent intent = new Intent(ChiTietSuKien.this, Tinnhan.class);
            startActivity(intent);
        });

        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ChiTietSuKien.this);
                dialog.setContentView(R.layout.activity_popup_chia_se_su_kien);

                dialog.show();
            }
        });

        binding.btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ChiTietSuKien.this);
                dialog.setContentView(R.layout.activity_popup_so_luong_ve);

                dialog.show();

                Button btnXacnhan = dialog.findViewById(R.id.btnXacnhan);
                btnXacnhan.setOnClickListener(view -> {
                    Intent intent = new Intent(ChiTietSuKien.this, TrangThanhToan.class);
                    startActivity(intent);
                    dialog.dismiss();
                });
            }
        });

    }
}