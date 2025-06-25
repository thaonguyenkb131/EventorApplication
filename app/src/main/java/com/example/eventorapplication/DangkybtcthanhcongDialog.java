package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.DialogDangkybtcthanhcongBinding;

public class DangkybtcthanhcongDialog extends BaseActivity<DialogDangkybtcthanhcongBinding> {

    @Override
    protected DialogDangkybtcthanhcongBinding inflateBinding() {
        return DialogDangkybtcthanhcongBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.btnTaosukien.setOnClickListener(v -> {
            Intent intent = new Intent(this, TaosukienActivity.class);
            startActivity(intent);
        });


    }
}