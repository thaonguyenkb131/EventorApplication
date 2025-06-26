package com.example.eventorapplication;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.eventorapplication.databinding.DialogBolocBinding;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class BolocDialog extends DialogFragment {
    private DialogBolocBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DialogBolocBinding.inflate(inflater, container, false);
        setCancelable(true);

        // Cho phép chọn nhiều chip cùng lúc cho danh mục
        binding.grpDanhmuc.setSingleSelection(false);

        // Gán listener cho từng chip danh mục để đổi màu khi chọn/bỏ chọn
        for (int i = 0; i < binding.grpDanhmuc.getChildCount(); i++) {
            View child = binding.grpDanhmuc.getChildAt(i);
            if (child instanceof Chip) {
                Chip chip = (Chip) child;
                chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        chip.setTextColor(Color.parseColor("#006183"));
                        chip.setTypeface(null, Typeface.BOLD);
                    } else {
                        chip.setTextColor(Color.BLACK);
                        chip.setTypeface(null, Typeface.NORMAL);
                    }
                });
                // Áp dụng style ban đầu nếu chip đã được chọn
                if (chip.isChecked()) {
                    chip.setTextColor(Color.parseColor("#006183"));
                    chip.setTypeface(null, Typeface.BOLD);
                } else {
                    chip.setTextColor(Color.BLACK);
                    chip.setTypeface(null, Typeface.NORMAL);
                }
            }
        }

        // Xử lý style cho CheckBox địa điểm
        binding.checkboxTPHCM.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.checkboxTPHCM.setTextColor(Color.parseColor("#006183"));
                binding.checkboxTPHCM.setTypeface(null, Typeface.BOLD);
            } else {
                binding.checkboxTPHCM.setTextColor(Color.BLACK);
                binding.checkboxTPHCM.setTypeface(null, Typeface.NORMAL);
            }
        });
        binding.checkboxHaNoi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.checkboxHaNoi.setTextColor(Color.parseColor("#006183"));
                binding.checkboxHaNoi.setTypeface(null, Typeface.BOLD);
            } else {
                binding.checkboxHaNoi.setTextColor(Color.BLACK);
                binding.checkboxHaNoi.setTypeface(null, Typeface.NORMAL);
            }
        });
        binding.checkboxDaLat.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.checkboxDaLat.setTextColor(Color.parseColor("#006183"));
                binding.checkboxDaLat.setTypeface(null, Typeface.BOLD);
            } else {
                binding.checkboxDaLat.setTextColor(Color.BLACK);
                binding.checkboxDaLat.setTypeface(null, Typeface.NORMAL);
            }
        });
        binding.checkboxKhac.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.checkboxKhac.setTextColor(Color.parseColor("#006183"));
                binding.checkboxKhac.setTypeface(null, Typeface.BOLD);
            } else {
                binding.checkboxKhac.setTextColor(Color.BLACK);
                binding.checkboxKhac.setTypeface(null, Typeface.NORMAL);
            }
        });

        // Áp dụng style ban đầu cho các CheckBox
        if (binding.checkboxTPHCM.isChecked()) {
            binding.checkboxTPHCM.setTextColor(Color.parseColor("#006183"));
            binding.checkboxTPHCM.setTypeface(null, Typeface.BOLD);
        }
        if (binding.checkboxHaNoi.isChecked()) {
            binding.checkboxHaNoi.setTextColor(Color.parseColor("#006183"));
            binding.checkboxHaNoi.setTypeface(null, Typeface.BOLD);
        }
        if (binding.checkboxDaLat.isChecked()) {
            binding.checkboxDaLat.setTextColor(Color.parseColor("#006183"));
            binding.checkboxDaLat.setTypeface(null, Typeface.BOLD);
        }
        if (binding.checkboxKhac.isChecked()) {
            binding.checkboxKhac.setTextColor(Color.parseColor("#006183"));
            binding.checkboxKhac.setTypeface(null, Typeface.BOLD);
        }

        // Xử lý nút Reset
        binding.btnReset.setOnClickListener(v -> {
            // Reset các chip danh mục
            for (int i = 0; i < binding.grpDanhmuc.getChildCount(); i++) {
                View child = binding.grpDanhmuc.getChildAt(i);
                if (child instanceof Chip) {
                    Chip chip = (Chip) child;
                    chip.setChecked(false);
                    chip.setTextColor(Color.BLACK);
                    chip.setTypeface(null, Typeface.NORMAL);
                }
            }
            // Reset các CheckBox địa điểm
            binding.checkboxTPHCM.setChecked(false);
            binding.checkboxHaNoi.setChecked(false);
            binding.checkboxDaLat.setChecked(false);
            binding.checkboxKhac.setChecked(false);

            // Reset Switch miễn phí
            binding.swMienphi.setChecked(false);
        });

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().setCanceledOnTouchOutside(true);
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    600,
                    getResources().getDisplayMetrics()
            );

            getDialog().getWindow().setLayout(width, height);

            // getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}