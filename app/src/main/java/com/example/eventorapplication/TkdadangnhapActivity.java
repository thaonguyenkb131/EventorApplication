package com.example.eventorapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

        // Footer tách biệt với thanh điều hướng (giống trang chủ, thông báo)
        View rootView = findViewById(R.id.main); // Layout gốc có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            int bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom;
            v.setPadding(0, 0, 0, 0); // Không thêm padding cho rootView

            // Đẩy header xuống dưới status bar nếu có
            View txtTitle = findViewById(R.id.header);
            if (txtTitle != null) {
                txtTitle.setPadding(
                    txtTitle.getPaddingLeft(),
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    txtTitle.getPaddingRight(),
                    txtTitle.getPaddingBottom()
                );
            }

            // Footer tách biệt với thanh điều hướng
            View footer = findViewById(R.id.footerLayout);
            if (footer != null) {
                footer.setPadding(
                    footer.getPaddingLeft(),
                    footer.getPaddingTop(),
                    footer.getPaddingRight(),
                    bottomInset
                );
            }

            return insets;
        });

        // Lấy dữ liệu đã lưu trong SharedPreferences
        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userEmail = preferences.getString("userEmail", null);
        String userName = preferences.getString("userName", null);
        String userLastname = preferences.getString("userLastname", null);

        // Nếu chưa đăng nhập (không có email hoặc tên), chuyển về màn hình chưa đăng nhập
        if (userEmail == null || userName == null) {
            Intent intent = new Intent(TkdadangnhapActivity.this, TkchuadangnhapActivity.class);
            startActivity(intent);
            finish(); // đóng activity hiện tại
            return;
        }

        // Hiển thị dữ liệu bằng View Binding
        String fullName = userLastname + " " + userName;
        binding.txtEmail.setText(userEmail);
        binding.txtName.setText(fullName);

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

        binding.goitaikhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, GoitaikhoanActivity.class);
                startActivity(intent);
            }
        });

        binding.cauhoithuonggap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, CauhoithuonggapActivity.class);
                startActivity(intent);
            }
        });

        binding.chinhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TkdadangnhapActivity.this, ChinhsachActivity.class);
                startActivity(intent);
            }
        });

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa thông tin đăng nhập đã lưu
                SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear(); // hoặc chỉ xóa từng key: remove("userEmail"), remove("userName"), ...
                editor.apply();

                // Chuyển về trang chưa đăng nhập hoặc trang chủ
                Intent intent = new Intent(TkdadangnhapActivity.this, TrangchuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Xóa stack để không quay lại bằng nút back
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected String getActiveFooterId() {
        return "taikhoan"; // Footer này sẽ được highlight
    }
}