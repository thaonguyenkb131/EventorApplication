package com.example.eventorapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventorapplication.databinding.ActivityBolocBinding;

public class BolocActivity extends AppCompatActivity {

    private ActivityBolocBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBolocBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.swMienphi.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.swMienphi.getThumbDrawable().setTint(Color.parseColor("#006183"));
                binding.swMienphi.getTrackDrawable().setTint(Color.parseColor("#006183"));
            } else {
                binding.swMienphi.getThumbDrawable().setTint(Color.GRAY);
                binding.swMienphi.getTrackDrawable().setTint(Color.LTGRAY);
            }
        });

        // Đặt chiều cao 400dp
        float heightInPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 400, getResources().getDisplayMetrics());
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) heightInPx);

        // Cho phép bấm ra ngoài để tắt
        setFinishOnTouchOutside(true);

    }

}