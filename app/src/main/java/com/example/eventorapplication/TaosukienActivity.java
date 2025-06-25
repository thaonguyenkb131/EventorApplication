package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTaosukienBinding;

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

        //        Tránh che màn hình

        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che nút

            // Đẩy TextView xuống dưới status bar
            View txtTitle = findViewById(R.id.Header);
            txtTitle.setPadding(
                    txtTitle.getPaddingLeft(),
                    systemBars.top,
                    txtTitle.getPaddingRight(),
                    txtTitle.getPaddingBottom()
            );

            return insets;
        });

        binding.edtSearch.setFocusable(false);
        binding.edtSearch.setClickable(true);
        binding.edtSearch.setShowSoftInputOnFocus(false);

        binding.edtSearch.setOnClickListener(view -> showPopupMap(view));

        binding.btnDangkyBTC.setOnClickListener(view ->
                startActivity(new Intent(this, DangkybtcActivity.class)));

        binding.btnDoiAnh.setOnClickListener(view -> showPopupTaianh(view));

        binding.btnDangSukien.setOnClickListener(view -> {
            TaosukienthanhcongDialog dialog = new TaosukienthanhcongDialog();
            dialog.show(getSupportFragmentManager(), "popup_taosk");
        });
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
}
