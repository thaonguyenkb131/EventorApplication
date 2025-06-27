package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityCauhoithuonggapBinding;

public class CauhoithuonggapActivity extends AppCompatActivity {

    ActivityCauhoithuonggapBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCauhoithuonggapBinding.inflate(getLayoutInflater());
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

        txtHeaderTitle.setText("Câu hỏi thường gặp");
        btnBack.setOnClickListener(v -> finish());

        // Chatbot
        binding.chatbot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CauhoithuonggapActivity.this, ChatbotActivity.class);
                startActivity(intent);
            }
        });
    }
}