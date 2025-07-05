package com.example.eventorapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityCaidattaikhoanBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CaidattaikhoanActivity extends AppCompatActivity {

    ActivityCaidattaikhoanBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCaidattaikhoanBinding.inflate(getLayoutInflater());
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

        txtHeaderTitle.setText("Cài đặt tài khoản");
        btnBack.setOnClickListener(v -> finish());

        // Thiết lập sự kiện click cho các mục trong cài đặt tài khoản
        binding.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogXoaTaiKhoan();
            }
        });
    }

    private void showDialogXoaTaiKhoan() {
        // Tạo dialog
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_xoataikhoan, null);
        builder.setView(dialogView);
        android.app.AlertDialog dialog = builder.create();

        // Bắt sự kiện nút Yes và No
        View btnYes = dialogView.findViewById(R.id.btn_yes);
        View btnNo = dialogView.findViewById(R.id.btn_no);

        btnYes.setOnClickListener(v -> {
            SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String userId = prefs.getString("userId", null);

            if (userId == null) {
                Toast.makeText(this, "Không tìm thấy người dùng đang đăng nhập", Toast.LENGTH_SHORT).show();
                return;
            }

            DatabaseReference userRef = FirebaseDatabase.getInstance()
                    .getReference("accounts")
                    .child(userId);

            userRef.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    dialog.dismiss();
                    showDialogThanhCong(); // hiển thị dialog thành công 3s và chuyển về màn hình đăng nhập
                } else {
                    Toast.makeText(this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                }
            });
            dialog.dismiss();
        });

        btnNo.setOnClickListener(v -> dialog.dismiss());

        // Hiển thị dialog
        dialog.show();
    }

    private void showDialogThanhCong() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_xoataikhoanthanhcong, null);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(dialogView);
        android.app.AlertDialog successDialog = builder.create();
        successDialog.show();

        new android.os.Handler().postDelayed(() -> {
            successDialog.dismiss();

            // Xóa thông tin đăng nhập
            SharedPreferences.Editor editor = getSharedPreferences("user_prefs", MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();

            // Quay về màn hình đăng nhập
            Intent intent = new Intent(this, DangnhapActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }, 3000);
    }

}