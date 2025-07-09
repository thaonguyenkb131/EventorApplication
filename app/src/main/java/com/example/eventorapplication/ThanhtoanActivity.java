package com.example.eventorapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityThanhtoanBinding;

public class ThanhtoanActivity extends AppCompatActivity {

    ActivityThanhtoanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThanhtoanBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Nhận dữ liệu tổng tiền từ intent
        double totalAmount = getIntent().getDoubleExtra("total_amount", 0);
        double finalTotal = getIntent().getDoubleExtra("final_total", 0);
        if (totalAmount > 0) {
            binding.tvTotalAmount.setText(String.format("%,.0f VND", totalAmount));
        }
        if (finalTotal > 0) {
            binding.tvFinalTotal.setText(String.format("%,.0f VND", finalTotal));
        }

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

        //        Tránh che màn hình

        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che nút

            // Đẩy TextView xuống dưới status bar
            View txtTitle = findViewById(R.id.header);
            if(txtTitle != null) {
                txtTitle.setPadding(
                        txtTitle.getPaddingLeft(),
                        systemBars.top,
                        txtTitle.getPaddingRight(),
                        txtTitle.getPaddingBottom()
                );
            }

            return insets;
        });

        // Thiết lập tiêu đề và nút quay lại
        TextView txtHeaderTitle = findViewById(R.id.txtTitle);
        ImageView btnBack = findViewById(R.id.btnBack);

        txtHeaderTitle.setText("Thanh toán");
        btnBack.setOnClickListener(v -> finish());
    }
}