package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTkdadangnhapBinding;

public class TkdadangnhapActivity extends BaseActivity<ActivityTkdadangnhapBinding> {

    @Override
    protected ActivityTkdadangnhapBinding inflateBinding() {
        return ActivityTkdadangnhapBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.caidattk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, CaidattaikhoanActivity.class);
                startActivity(intent);
            }
        });

        binding.trangcanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, TrangcanhanActivity.class);
                startActivity(intent);
            }
        });

        binding.bonuspoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, DiemthuongActivity.class);
                startActivity(intent);
            }
        });

        binding.thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, TaikhoanthanhtoanActivity.class);
                startActivity(intent);
            }
        });

        binding.chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, ChatbotActivity.class);
                startActivity(intent);
            }
        });
    }
}