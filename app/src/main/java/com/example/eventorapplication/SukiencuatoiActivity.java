package com.example.eventorapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.eventorapplication.databinding.ActivitySukiencuatoiBinding;

public class SukiencuatoiActivity extends AppCompatActivity {

    private ActivitySukiencuatoiBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySukiencuatoiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Load mặc định fragment đầu tiên
        loadFragment(new SukiendaluuFragment());

        // Xử lý click từng nút
        binding.btnSkdaluu.setOnClickListener(v -> {
            loadFragment(new SukiendaluuFragment());
        });

        binding.btnVedamua.setOnClickListener(v -> {
            loadFragment(new VedamuaFragment());
        });

        binding.btnSkdadang.setOnClickListener(v -> {
            loadFragment(new SukiendadangFragment());
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}