package com.example.eventorapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.eventorapplication.databinding.ActivityChitietvedamuaBinding;
import com.example.models.Thesukien;

public class ChitietvedamuaActivity extends AppCompatActivity {

    ActivityChitietvedamuaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChitietvedamuaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        txtHeaderTitle.setText("Vé đã mua"); // Luôn giữ tiêu đề này, không đổi theo sự kiện

        Thesukien thesukien = (Thesukien) getIntent().getSerializableExtra("thesukien");
        if (thesukien != null) {
            // Không set lại txtHeaderTitle ở đây nữa
            binding.txtEventTitle.setText(thesukien.getTitle());
            binding.txtTime.setText(thesukien.getDetailTime());
            binding.location.setText(thesukien.getDetailAddress());
            Glide.with(this).load(thesukien.getThumbnail()).into(binding.imgPoster);
        }
        btnBack.setOnClickListener(v -> finish());
    }
}