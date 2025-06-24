package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityMainBinding;
import com.example.eventorapplication.databinding.MyAccountLoggedInBinding;

public class MyAccountLoggedIn extends BaseActivity<MyAccountLoggedInBinding> {

    @Override
    protected MyAccountLoggedInBinding inflateBinding() {
        return MyAccountLoggedInBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.caidattk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountLoggedIn.this, AccountInfor.class);
                startActivity(intent);
            }
        });

        binding.trangcanhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountLoggedIn.this, PersonalProfile.class);
                startActivity(intent);
            }
        });

        binding.bonuspoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountLoggedIn.this, BonusPoint.class);
                startActivity(intent);
            }
        });

        binding.thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountLoggedIn.this, Payment.class);
                startActivity(intent);
            }
        });

        binding.chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyAccountLoggedIn.this, ChatboxMessage.class);
                startActivity(intent);
            }
        });
    }
}