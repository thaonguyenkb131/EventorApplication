package com.example.eventorapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivitySukiencuatoiBinding;
import com.example.eventorapplication.utils.DataManager;

public class SukiencuatoiActivity extends BaseActivity<ActivitySukiencuatoiBinding> {

    private long lastClickTime = 0;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300; // 300ms

    @Override
    protected ActivitySukiencuatoiBinding inflateBinding() {
        return binding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // Kiểm tra đăng nhập
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        if (userId == null) {
            binding.txtDangNhap.setVisibility(View.VISIBLE);
            binding.groupLoggedInContent.setVisibility(View.GONE); // Ẩn toàn bộ nội dung chính
            binding.txtDangNhap.setPaintFlags(binding.txtDangNhap.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            binding.txtDangNhap.setOnClickListener(v -> {
                Intent intent = new Intent(this, DangnhapActivity.class);
                startActivity(intent);
            });
            return;
        } else {
            binding.txtDangNhap.setVisibility(View.GONE);
            binding.groupLoggedInContent.setVisibility(View.VISIBLE); // Hiện nội dung chính nếu đã đăng nhập
        }

        // Load mặc định fragment đầu tiên
        loadFragment(new SukiendaluuFragment());
        updateButtonStates(0);

        // Xử lý click từng nút với double-click để reload
        binding.btnSkdaluu.setOnClickListener(v -> {
            long clickTime = System.currentTimeMillis();
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                // Double click - reload data
                dataManager.removeData(DataManager.KEY_SAVED_EVENTS);
                loadFragmentWithDelay(new SukiendaluuFragment(), 0);
            } else {
                // Single click - load fragment normally
                loadFragmentWithDelay(new SukiendaluuFragment(), 0);
            }
            lastClickTime = clickTime;
        });

        binding.btnVedamua.setOnClickListener(v -> {
            long clickTime = System.currentTimeMillis();
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                // Double click - reload data
                dataManager.removeData(DataManager.KEY_MY_TICKETS);
                loadFragmentWithDelay(new VedamuaFragment(), 1);
            } else {
                // Single click - load fragment normally
                loadFragmentWithDelay(new VedamuaFragment(), 1);
            }
            lastClickTime = clickTime;
        });

        binding.btnSkdadang.setOnClickListener(v -> {
            long clickTime = System.currentTimeMillis();
            if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                // Double click - reload data
                dataManager.removeData(DataManager.KEY_POSTED_EVENTS);
                loadFragmentWithDelay(new SukiendadangFragment(), 2);
            } else {
                // Single click - load fragment normally
                loadFragmentWithDelay(new SukiendadangFragment(), 2);
            }
            lastClickTime = clickTime;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
    
    private void loadFragmentWithDelay(Fragment fragment, int buttonIndex) {
        // Load fragment trước
        loadFragment(fragment);
        
        // Delay một chút để fragment load xong rồi mới cập nhật button state
        new android.os.Handler().postDelayed(() -> {
            updateButtonStates(buttonIndex);
        }, 100); // Delay 100ms
    }

    private void updateButtonStates(int selected) {
        // 0: btnSkdaluu, 1: btnVedamua, 2: btnSkdadang
        binding.btnSkdaluu.setAlpha(selected == 0 ? 1f : 0.5f);
        binding.btnVedamua.setAlpha(selected == 1 ? 1f : 0.5f);
        binding.btnSkdadang.setAlpha(selected == 2 ? 1f : 0.5f);
    }

    @Override
    protected String getActiveFooterId() {
        return "Sukiencuatoi";
    }

    @Override
    protected void scrollToTopIfNeeded(String footerId) {
        if ("Sukiencuatoi".equals(footerId)) {
            // Nếu có fragment, gọi scrollToTop cho fragment nếu cần
            // Nếu không, scroll HorizontalScrollView
            android.widget.HorizontalScrollView hsv = findViewById(R.id.horizontalScrollView);
            if (hsv != null) hsv.smoothScrollTo(0, 0);
        }
    }
}