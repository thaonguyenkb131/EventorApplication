package com.example.eventorapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityThemptttBinding;

public class ThemptttActivity extends AppCompatActivity {

    ActivityThemptttBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemptttBinding.inflate(getLayoutInflater());
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

        txtHeaderTitle.setText("Thêm PTTT");
        btnBack.setOnClickListener(v -> finish());

        GridLayout gvTheqt = findViewById(R.id.gvTheqt);
        GridLayout gvThend = findViewById(R.id.gvThend);

// Bắt sự kiện cho từng item trong gvTheqt
        for (int i = 0; i < gvTheqt.getChildCount(); i++) {
            View item = gvTheqt.getChildAt(i);
            item.setOnClickListener(v -> showDialog());
        }

// Bắt sự kiện cho từng item trong gvThend
        for (int i = 0; i < gvThend.getChildCount(); i++) {
            View item = gvThend.getChildAt(i);
            item.setOnClickListener(v -> showDialog());
        }
    }

    private void showDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_thongtinthanhtoan, null);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .create();
        dialog.show();

        Button btnConfirm = dialogView.findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(v -> {
            dialog.dismiss(); // Ẩn dialog hiện tại (thông tin thanh toán)

            // Hiện dialog xác nhận
            View confirmView = getLayoutInflater().inflate(R.layout.dialog_xacnhanttthanhtoan, null);
            AlertDialog confirmDialog = new AlertDialog.Builder(this)
                    .setView(confirmView)
                    .setCancelable(false)
                    .create();
            confirmDialog.show();

            LinearLayout btnYes = confirmView.findViewById(R.id.btn_yes);
            LinearLayout btnNo = confirmView.findViewById(R.id.btn_no);

            btnYes.setOnClickListener(view -> {
                confirmDialog.dismiss();
                showDialog(); // Gọi lại dialog thông tin thanh toán
            });

            btnNo.setOnClickListener(view -> {
                confirmDialog.dismiss();
                showSavedDialog(); // Hiển thị thông báo "Thông tin thanh toán đã được lưu!"
            });
        });
    }

    private void showSavedDialog() {
        View savedView = getLayoutInflater().inflate(R.layout.dialog_xacnhanluutttt, null);

        AlertDialog savedDialog = new AlertDialog.Builder(this)
                .setView(savedView)
                .setCancelable(false) // Không cho người dùng bấm ngoài để tắt
                .create();

        savedDialog.show();

        new android.os.Handler().postDelayed(savedDialog::dismiss, 2000);
    }
}