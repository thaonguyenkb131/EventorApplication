package com.example.eventorapplication;

import android.graphics.Color;
import android.os.Bundle;

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
                //Bật
                binding.swMienphi.getThumbDrawable().setTint(Color.parseColor("#006183"));
                binding.swMienphi.getTrackDrawable().setTint(Color.parseColor("#006183"));
            } else {
                // Tắt
                binding.swMienphi.getThumbDrawable().setTint(Color.GRAY);
                binding.swMienphi.getTrackDrawable().setTint(Color.LTGRAY);
            }
        });
    }
}
