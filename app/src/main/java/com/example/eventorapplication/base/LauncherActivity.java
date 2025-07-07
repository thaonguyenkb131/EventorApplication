package com.example.eventorapplication.base;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventorapplication.OnboardingActivity;
import com.example.eventorapplication.TrangchuActivity;

public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences preferences = getSharedPreferences("onboarding", MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);

        if (isFirstTime) {
            // Lần đầu mở app → chuyển đến Onboarding
            Intent intent = new Intent(this, OnboardingActivity.class);
            startActivity(intent);
        } else {
            // Đã từng mở app → chuyển đến Trang chủ
            Intent intent = new Intent(this, TrangchuActivity.class);
            startActivity(intent);
        }

        finish(); // Kết thúc LauncherActivity
    }
}
