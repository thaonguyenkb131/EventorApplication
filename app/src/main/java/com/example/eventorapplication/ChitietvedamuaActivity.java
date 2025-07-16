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

        View rootView = findViewById(R.id.main); 
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);

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

        txtHeaderTitle.setText("Vé đã mua"); 

        Thesukien thesukien = (Thesukien) getIntent().getSerializableExtra("thesukien");
        if (thesukien != null) {
            binding.txtEventTitle.setText(thesukien.getTitle());
            binding.txtTime.setText(thesukien.getDetailTime());
            binding.location.setText(thesukien.getLocation());
            Glide.with(this).load(thesukien.getThumbnail()).into(binding.imgPoster);

            // Thêm sự kiện click cho btnView
            binding.btnView.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(this, ChitietsukienActivity.class);
                com.google.gson.Gson gson = new com.google.gson.Gson();
                intent.putExtra("event_json", gson.toJson(thesukien));
                startActivity(intent);
            });
        }
        btnBack.setOnClickListener(v -> finish());
    }
}