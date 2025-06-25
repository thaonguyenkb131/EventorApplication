package com.example.eventorapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventorapplication.databinding.ActivityThanhtoanBinding;

public class ThanhtoanActivity extends AppCompatActivity {

    ActivityThanhtoanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThanhtoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        binding.btnThanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ThanhtoanActivity.this);
                dialog.setContentView(R.layout.dialog_thanhtoanthanhcong);

                dialog.show();
            }
        });
    }
}