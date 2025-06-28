package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.SukienxuhuongAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivitySukienxuhuongBinding;
import com.example.models.SukienxuhuongItem;

import java.util.ArrayList;


public class SukienxuhuongActivity extends BaseActivity<ActivitySukienxuhuongBinding> {

    private ArrayList<SukienxuhuongItem> dsSuKien;
    private SukienxuhuongAdapter adapter;

    @Override
    protected ActivitySukienxuhuongBinding inflateBinding() {
        return ActivitySukienxuhuongBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupListView();

        //        Tránh che màn hình

        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che nút

            // Đẩy TextView xuống dưới status bar
            View txtTitle = findViewById(R.id.header);
            txtTitle.setPadding(
                    txtTitle.getPaddingLeft(),
                    systemBars.top,
                    txtTitle.getPaddingRight(),
                    txtTitle.getPaddingBottom()
            );

            return insets;
        });

        // Thiết lập tiêu đề và nút quay lại
        TextView txtHeaderTitle = findViewById(R.id.txtTitle);
        ImageView btnBack = findViewById(R.id.btnBack);

        txtHeaderTitle.setText("Sự kiện xu hướng");
        btnBack.setOnClickListener(v -> finish());

        binding.lvSukien.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy sự kiện được click
            SukienxuhuongItem selectedItem = dsSuKien.get(position);

            // Tạo Intent sang trang Chi tiết sự kiện
            Intent intent = new Intent(SukienxuhuongActivity.this, ChitietsukienActivity.class);

            startActivity(intent);
        });
    }


    private void setupListView() {
        dsSuKien = new ArrayList<>();
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxh1, "SuperFest - Concert Mùa Hè Rực Sáng", "Số vé đã bán: 15,256 vé", "Từ 800.000 VND", "Quảng Ninh","TOP 1"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxh2, "[River Flows In You] Đêm Nhạc Xương Rồng", "Số vé đã bán: 14,891 vé", "Từ 600.000 VND", "Hà Nội","TOP 2"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxh3, "The East - Live Concert Hạ Thành Phố Huế", "Số vé đã bán: 14,812 vé", "Từ 900.000 VND", "Huế","TOP 3"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxh4, "Future With AI - AI và tương lai doanh nghiệp", "Số vé đã bán: 12,619 vé", "Từ 200.000 VND", "TP. Hồ Chí Minh","TOP 4"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxh5, "Nhà Hát Kịch IDECAF: Tấm Cám Đại Chiến","Số vé đã bán: 11,224 vé", "Từ 200.000 VND", "TP. Hồ Chí Minh", "TOP 5"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxh6, "Sân Khấu Thiên Đăng: Ngũ Qúy Tương Phùng","Số vé đã bán: 10,982 vé", "Từ 300.000 VND", "TP. Hồ Chí Minh","TOP 6"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxh7, "YES|Dancenter Annual Show 2025","Số vé đã bán: 10,120 vé", "Từ 350.000 VND", "TP. Hồ Chí Minh", "TOP 7"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxh8, "Nhà hát thanh niên Hài kịch: Lạc lối ở BangKok","Số vé đã bán: 10,112 vé", "Từ 300.000 VND", "TP. Hồ Chí Minh", "TOP 8"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxh9, "BOF Debut Showcase","Số vé đã bán: 11,224 vé", "Từ 500.000 VND", "TP. Hồ Chí Minh", "TOP 9"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxh10, "Lululola Show Hương Tràm - Một Nửa Sự Thật","Số vé đã bán: 9,989 vé", "Từ 550.000 VND", "Đà Lạt", "TOP 10"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxhpage11, "Sân Khấu Xóm Kịch: Vở Kẻ Lạc Hồn","Số vé đã bán: 9,809 vé", "Từ 250.000 VND", "TP. Hồ Chí Minh", "TOP 11"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxhpage12, "Địa Đạo Củ Chi: Trăng Chiến Khu","Số vé đã bán: 8,985 vé", "Từ 400.000 VND", "TP. Hồ Chí Minh", "TOP 12"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxhpage13, "Nhà Hát Kihj IDECAF: Tía Ơi Má Dìa!","Số vé đã bán: 8,765 vé", "Từ 270.000 VND", "TP. Hồ Chí Minh","TOP 13"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxhpage14, "1900 Future Hits #63|Christmas Edition","Số vé đã bán: 7,985 vé", "Từ 350.000 VND", "Hà Nội","TOP 14"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxhpage15, "Hãy Để Anh Đi - Quốc Thiên & Bùi Công Nam","Số vé đã bán: 6,235","Từ 800.000 VND", "TP. Hồ Chí Minh","TOP 15"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxhpage16, "[Bến Thành] Đêm nhạc Văn Mai Hương","Số vé đã bán: 6,023 vé", "Từ 1.000.000 VND", "TP. Hồ Chí Minh","TOP 16"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxhpage17, "[Mộng Mơ] Khóa Ly Biệt - Anh Tú & Hà Nhi","Số vé đã bán: 5,825 vé", "Từ 1.000.000 VND", "Hà Nội","TOP 17"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxhpage18, "Concert Ký Ức Mãi Xanh","Số vé đã bán: 4,453 vé", "Từ 500.000 VND", "Hà Nội", "TOP 18"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxhpage19, "Trịnh Cuối - Giang Trang | Kỷ niệm 86 năm ngày sinh Nhạc sĩ Trịnh Công Sơn","Số vé đã bán: 4,245 vé", "Từ 500.000 VND", "Hà Nội", "TOP 19"));
        dsSuKien.add(new SukienxuhuongItem(R.drawable.skxhpage20, "Piano: Tiên nữ, giấc mơ và điệu vũ","Số vé đã bán: 3,265 vé", "Từ 300.000 VND", "Hà Nội","TOP 20"));

        adapter = new SukienxuhuongAdapter(this, dsSuKien);
        binding.lvSukien.setAdapter(adapter);
    }

}
