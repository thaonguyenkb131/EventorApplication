package com.example.eventorapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTaosukienBinding;

import java.util.Calendar;

public class TaosukienActivity extends BaseActivity<ActivityTaosukienBinding> {

    private PopupWindow popupTaianh;
    private PopupWindow popupMaps;

    @Override
    protected ActivityTaosukienBinding inflateBinding() {
        return ActivityTaosukienBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Đặt màu thanh điều hướng là trắng, icon tối (giống trang chủ, thông báo)
        Window window = getWindow();
        window.setNavigationBarColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final View decor = window.getDecorView();
            int flags = decor.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            decor.setSystemUiVisibility(flags);
        }

        // Đồng nhất tránh che footer, footer tách biệt với thanh điều hướng
        View rootView = findViewById(R.id.main); // Layout gốc có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, 0); // Không thêm padding cho rootView

            // Đẩy header xuống dưới status bar nếu có
            View txtTitle = findViewById(R.id.Header);
            if (txtTitle != null) {
                txtTitle.setPadding(
                        txtTitle.getPaddingLeft(),
                        systemBars.top,
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
                    systemBars.bottom
                );
            }

            return insets;
        });

        // Kiểm tra trạng thái đăng nhập
        // Kiểm tra đăng nhập
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        if (userId == null) {
            binding.txtDangNhap.setVisibility(View.VISIBLE);
            binding.scrollView.setVisibility(View.GONE); // Ẩn toàn bộ nội dung chính
            binding.txtDangNhap.setPaintFlags(binding.txtDangNhap.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            binding.txtDangNhap.setOnClickListener(v -> {
                Intent intent = new Intent(this, DangnhapActivity.class);
                startActivity(intent);
            });
            return;
        } else {
            binding.txtDangNhap.setVisibility(View.GONE);
            binding.scrollView.setVisibility(View.VISIBLE); // Hiện nội dung chính nếu đã đăng nhập
        }

        binding.edtSearch.setFocusable(false);
        binding.edtSearch.setClickable(true);
        binding.edtSearch.setShowSoftInputOnFocus(false);

        binding.edtSearch.setOnClickListener(view -> showPopupMap(view));

        binding.btnDangkyBTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaosukienActivity.this, DangkybtcActivity.class);
                startActivity(intent);
            }
        });

        binding.btnDoiAnh.setOnClickListener(view -> showPopupTaianh(view));

        binding.btnDangSukien.setOnClickListener(view -> showPopupTaoskthanhcong());

        binding.rbOnline.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.edtOffline.setVisibility(View.GONE);
                binding.edtOnline.setVisibility(View.VISIBLE);
            }
        });

        binding.rbOffline.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.edtOffline.setVisibility(View.VISIBLE);
                binding.edtOnline.setVisibility(View.GONE);
            }
        });

        binding.edtStart.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        String selectedDate = dayOfMonth + "/" + (month+1) + "/" + year;
                        binding.edtStart.setText(selectedDate);
                    },
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
    }

    private void showPopupTaoskthanhcong() {
        View popupView = LayoutInflater.from(this).inflate(R.layout.dialog_taosukienthanhcong, null);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(popupView);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); // full width
            int padding = (int) getResources().getDisplayMetrics().density * 24; // ~24dp
            window.getDecorView().setPadding(padding, padding, padding, padding); // cách lề đều
            window.setGravity(Gravity.CENTER);
        }
        dialog.getWindow().setGravity(Gravity.CENTER);

        // Bắt sự kiện click nút Tạo sự kiện
        Button btnXemsukien = popupView.findViewById(R.id.btnXemsukien);
        btnXemsukien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang TaosukienActivity
                Intent intent = new Intent(TaosukienActivity.this, ChitietsukienActivity.class);
                startActivity(intent);
                dialog.dismiss(); // đóng dialog
            }
        });

        dialog.show();
    }

    private void showPopupMap(View anchorView) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.dialog_map, null);

        popupMaps = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupMaps.setOutsideTouchable(true);
        popupMaps.setFocusable(true);

        popupMaps.showAtLocation(binding.getRoot(), android.view.Gravity.CENTER, 0, 0);
    }

    private void showPopupTaianh(View anchorView) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.dialog_taianhlen, null);

        popupTaianh = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupTaianh.setOutsideTouchable(true);
        popupTaianh.setFocusable(true);
        popupTaianh.setBackgroundDrawable(getDrawable(android.R.color.transparent));

        popupTaianh.showAsDropDown(anchorView, 0, 16);
    }

    @Override
    protected String getActiveFooterId() {
        return "taosukien";
    }
}
