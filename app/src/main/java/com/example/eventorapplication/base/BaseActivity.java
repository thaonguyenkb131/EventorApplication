package com.example.eventorapplication.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.example.eventorapplication.MainActivity;
import com.example.eventorapplication.Taosukien;
import com.example.eventorapplication.TrangthongbaoActivity;
import com.example.eventorapplication.R;
import com.example.eventorapplication.trang_ve_da_mua;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB binding;

    protected abstract VB inflateBinding();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflateBinding();
        setContentView(binding.getRoot());
        setupFooterNavigation();
    }

    private void setupFooterNavigation() {
        View homepage = binding.getRoot().findViewById(R.id.Homepage);
        View taosukien = binding.getRoot().findViewById(R.id.taosukien);
        View thongbao = binding.getRoot().findViewById(R.id.thongbao);
        View sukiencuatoi = binding.getRoot().findViewById(R.id.Sukiencuatoi);

        if (homepage != null) {
            homepage.setOnClickListener(v -> {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            });
        }

        if (taosukien != null) {
            taosukien.setOnClickListener(v -> {
                startActivity(new Intent(this, Taosukien.class));
                finish();
            });
        }

        if (thongbao != null) {
            thongbao.setOnClickListener(v -> {
                startActivity(new Intent(this, TrangthongbaoActivity.class));
                finish();
            });
        }

        if (sukiencuatoi != null) {
            sukiencuatoi.setOnClickListener(v -> {
                startActivity(new Intent(this, trang_ve_da_mua.class));
                finish();
            });
        }


    }
}
