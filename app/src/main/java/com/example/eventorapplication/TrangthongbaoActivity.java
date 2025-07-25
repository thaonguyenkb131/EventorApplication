package com.example.eventorapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.TrangthongbaoAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTrangthongbaoBinding;
import com.example.eventorapplication.databinding.ItemLvthongbaoBinding;
import com.example.models.TrangthongbaoItem;

import java.util.ArrayList;

public class TrangthongbaoActivity extends BaseActivity<ActivityTrangthongbaoBinding> {
    ArrayList<TrangthongbaoItem> tbao;
    TrangthongbaoAdapter adapter;
    private boolean isNotificationOn = false;

    @Override
    protected ActivityTrangthongbaoBinding inflateBinding() {
        return ActivityTrangthongbaoBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Đồng nhất tránh che footer
        View rootView = findViewById(R.id.main); // Layout gốc có id="main"
        if (rootView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che footer

                // Đẩy header xuống dưới status bar nếu có
                View txtTitle = findViewById(R.id.header);
                if (txtTitle != null) {
                    txtTitle.setPadding(
                            txtTitle.getPaddingLeft(),
                            systemBars.top,
                            txtTitle.getPaddingRight(),
                            txtTitle.getPaddingBottom()
                    );
                }

                return insets;
            });
        }

        // Kiểm tra trạng thái đăng nhập
        // Kiểm tra đăng nhập
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        if (userId == null) {
            binding.txtDangNhap.setVisibility(View.VISIBLE);
            binding.layoutThongBao.setVisibility(View.GONE); // Ẩn toàn bộ nội dung chính
            binding.txtDangNhap.setPaintFlags(binding.txtDangNhap.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            binding.txtDangNhap.setOnClickListener(v -> {
                Intent intent = new Intent(this, DangnhapActivity.class);
                startActivity(intent);
            });
            return;
        } else {
            binding.txtDangNhap.setVisibility(View.GONE);
            binding.layoutThongBao.setVisibility(View.VISIBLE); // Hiện nội dung chính nếu đã đăng nhập
        }

        binding.btnTatThongBao.setOnClickListener(v -> {
            isNotificationOn = !isNotificationOn;

            if (isNotificationOn) {
                binding.btnTatThongBao.setText("Tắt thông báo");
                binding.btnTatThongBao.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#006183")));
                binding.btnTatThongBao.setTextColor(Color.WHITE);
            } else {
                binding.btnTatThongBao.setText("Bật thông báo");
                binding.btnTatThongBao.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                binding.btnTatThongBao.setTextColor(Color.BLACK);
            }
        });

        loadData();

        binding.lvThongbao.setOnItemClickListener((parent, view, position, id) -> {
            ItemLvthongbaoBinding itemBinding = (ItemLvthongbaoBinding) view.getTag();

            TextView txtTieude = itemBinding.txtTieude;
            TextView txtNoidung = itemBinding.txtNoidung;

            boolean isCollapsed = txtNoidung.getMaxLines() == 3 && txtTieude.getMaxLines() == 1;

            if (isCollapsed) {
                txtTieude.setMaxLines(Integer.MAX_VALUE);
                txtNoidung.setMaxLines(Integer.MAX_VALUE);
            } else {
                txtTieude.setMaxLines(1);
                txtNoidung.setMaxLines(3);
            }
        });
    }

    private void loadData() {
        tbao = new ArrayList<>();
        tbao.add(new TrangthongbaoItem(R.drawable.ttb1,"Lễ hội âm nhạc, sáng tạo Tràng An - Ninh Bình","10:45 A.M","Thảo Nguyên ơi! Thời gian diễn ra sự kiện Lễ hội âm nhạc, sáng tạo Tràng An - Ninh Bình FORESTIVAL sắp đến rồi, bạn tranh thủ tham dự nhé!"));

        tbao.add(new TrangthongbaoItem(R.drawable.ttb1,"Madame Show - Những đường chim bay","10:30 A.M","Sự kiện đã lưu rồi thì ngại gì không tham gia hả Thảo Nguyên ơi? Cùng chia sẻ đến bạn bè và người thân tham gia sự kiện ngay nhé!"));

        tbao.add(new TrangthongbaoItem(R.drawable.ttb2,"1900 Future Hits #75 - Jun Phạm","10:15 A.M","Hãy cùng những người bạn thân yêu của bạn đến tham gia ngay sự kiện 1900 Future Hits ngay nào Thảo Nguyên ơi!"));

        tbao.add(new TrangthongbaoItem(R.drawable.ttb3,"[BẾN THÀNH] Đêm nhạc Bạch Công Khanh - Chu Thúy Quỳnh","09:59 A.M","Thảo Nguyên ơi! Thời gian diễn ra sự kiện [BẾN THÀNH] Đêm nhạc Bạch Công Khanh - Chu Thúy Quỳnh sắp đến rồi, bạn tranh thủ tham dự nhé!"));

        tbao.add(new TrangthongbaoItem(R.drawable.ttb4,"Lễ hội âm nhạc, sáng tạo Tràng An - Ninh Bình","09:56 A.M","Thảo Nguyên ơi! Liệu bạn có phải đang quan tâm đến sự kiện Lễ hội âm nhạc, sáng tạo Tràng An - Ninh Bình không? Hãy cho chúng tớ biết với nhé!"));

        tbao.add(new TrangthongbaoItem(R.drawable.ttb5,"Hành Tinh Nâu - Sân khấu Quốc Thảo","09:40 A.M","Thảo Nguyên ơi! Liệu bạn có phải đang quan tâm đến sự kiện Hành Tinh Nâu - Sân khấu Quốc Thảo không? Hãy cho chúng tớ biết với nhé!"));

        adapter = new TrangthongbaoAdapter(this, tbao);
        binding.lvThongbao.setAdapter(adapter);
    }

    @Override
    protected String getActiveFooterId() {
        return "thongbao"; 
    }

    @Override
    protected void scrollToTopIfNeeded(String footerId) {
        if ("thongbao".equals(footerId)) {
            android.widget.ListView lv = findViewById(R.id.lvThongbao);
            if (lv != null && lv.getCount() > 0) lv.setSelection(0);
        }
    }
}