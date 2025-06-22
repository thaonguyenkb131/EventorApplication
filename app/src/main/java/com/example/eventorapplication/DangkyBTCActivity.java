package com.example.eventorapplication;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityDangkyBtcactivityBinding;

public class DangkyBTCActivity extends BaseActivity<ActivityDangkyBtcactivityBinding> {

    private PopupWindow popupTaianh;
    private PopupWindow popupMaps;

    @Override
    protected ActivityDangkyBtcactivityBinding inflateBinding() {
        return ActivityDangkyBtcactivityBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.txtTaigiayto.setOnClickListener(view -> showPopupTaianh(view));

        binding.edtSearch.setFocusable(false);
        binding.edtSearch.setClickable(true);
        binding.edtSearch.setShowSoftInputOnFocus(false);

        binding.edtSearch.setOnClickListener(view -> showPopupMap(view));
        binding.btnChon.setOnClickListener(view -> showPopupThanhToan());

    }

    private void showPopupTaianh(View anchorView) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.activity_popup_taianhlen, null);

        popupTaianh = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupTaianh.setOutsideTouchable(true);
        popupTaianh.setFocusable(true);

        popupTaianh.showAsDropDown(anchorView, 0, 16);
    }

    private void showPopupMap(View anchorView) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.activity_maps_dialog, null);

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
    private void showPopupThanhToan() {
        View popupView = LayoutInflater.from(this).inflate(R.layout.activity_popup_thanhtoan, null);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(popupView);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);

        dialog.show();
    }

}