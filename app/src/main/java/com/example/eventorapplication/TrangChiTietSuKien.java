package com.example.eventorapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTrangChiTietSuKienBinding;

public class TrangChiTietSuKien extends BaseActivity<ActivityTrangChiTietSuKienBinding> {

    ActivityTrangChiTietSuKienBinding binding;

    private boolean isSaved = false;

    @Override
    protected ActivityTrangChiTietSuKienBinding inflateBinding() {
        return ActivityTrangChiTietSuKienBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //        Tránh che màn hình
        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che nút

            // Đẩy TextView xuống dưới status bar
            View txtTitle = findViewById(R.id.header_layout);
            txtTitle.setPadding(
                    txtTitle.getPaddingLeft(),
                    systemBars.top,
                    txtTitle.getPaddingRight(),
                    txtTitle.getPaddingBottom()
            );

            return insets;
        });

        binding.btnContact.setOnClickListener(v -> {
            Intent intent = new Intent(TrangChiTietSuKien.this, Tinnhan.class);
            startActivity(intent);
        });

        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(TrangChiTietSuKien.this);
                dialog.setContentView(R.layout.activity_popup_chia_se_su_kien);

                dialog.show();
            }
        });

        binding.btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(TrangChiTietSuKien.this);
                dialog.setContentView(R.layout.activity_popup_so_luong_ve);

                dialog.show();

                Button btnXacnhan = dialog.findViewById(R.id.btnXacnhan);
                btnXacnhan.setOnClickListener(view -> {
                    Intent intent = new Intent(TrangChiTietSuKien.this, TrangThanhToan.class);
                    startActivity(intent);
                    dialog.dismiss();
                });
            }
        });

        // Sự kiện đổi hình nền khi nhấn "Lưu sự kiện"
        LinearLayout btnSave = findViewById(R.id.btnSave);
        LinearLayout saveIcon = (LinearLayout) btnSave.getChildAt(0); // layout nền chứa icon

        btnSave.setOnClickListener(v -> {
            if (!isSaved) {
                saveIcon.setBackgroundResource(R.drawable.save2); // đổi sang ảnh đã lưu
                isSaved = true;
            } else {
                saveIcon.setBackgroundResource(R.drawable.save); // trở lại ảnh gốc
                isSaved = false;
            }
        });
    }
}