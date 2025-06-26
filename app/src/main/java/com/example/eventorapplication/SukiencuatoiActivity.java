package com.example.eventorapplication;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivitySukiencuatoiBinding;

public class SukiencuatoiActivity extends BaseActivity<ActivitySukiencuatoiBinding> {



    @Override
    protected ActivitySukiencuatoiBinding inflateBinding() {
        return binding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        //        Tránh che màn hình

        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che nút

            // Đẩy TextView xuống dưới status bar
            View txtTitle = findViewById(R.id.header);
            if(txtTitle != null) {
                txtTitle.setPadding(
                        txtTitle.getPaddingLeft(),
                        systemBars.top,
                        txtTitle.getPaddingRight(),
                        txtTitle.getPaddingBottom()
                );
            }

            return insets;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }



}