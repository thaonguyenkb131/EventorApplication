package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.SuKienDaDangAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivitySukiendadangBinding;
import com.example.models.SukiendadangItem;

import java.util.ArrayList;

public class SukiendadangActivity extends BaseActivity<ActivitySukiendadangBinding> {
    ListView lvSkdd;
    ArrayList<SukiendadangItem> dsSuKien;
    SuKienDaDangAdapter adapter;

    @Override
    protected ActivitySukiendadangBinding inflateBinding() {
        return ActivitySukiendadangBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Tránh che màn hình
        View rootView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);

            View txtTitle = findViewById(R.id.header_layout);
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

        // Gán sự kiện cho các nút
        binding.btnPurchasedTickets.setOnClickListener(v -> {
            Intent intent = new Intent(this, VedamuaActivity.class);
            startActivity(intent);
        });

        binding.btnSavedEvents.setOnClickListener(v -> {
            Intent intent = new Intent(this, VedamuaActivity.class);
            intent.putExtra("clicked_button", "saved");
            startActivity(intent);
        });

        // Khởi tạo danh sách dữ liệu
        dsSuKien = new ArrayList<>();
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh1, "SuperFest - Concert Mùa Hè Rực Sáng", "Số vé đã bán: 10,982 vé", "", "Quảng Ninh"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh2, "[River Flows In You] Đêm Nhạc Xương Rồng", "Số vé đã bán: 8,422 vé", "", "Hà Nội"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh3, "Lễ Hội Ẩm Thực Việt - Vị Quê Hương", "Đã bán: 15,000 vé", "", "Hà Nội"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh4, "The “Traditional Water Puppet Show” - Múa rối nước", "Đã bán: 14,500 vé", "", "TP.HCM"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh5, "Hội Chợ Sách & Văn Hóa Đọc", "Đã bán: 14,500 vé", "", "TP.HCM"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh6, "Đại Nhạc Hội Mùa Đông - Winter Beat", "Số vé đã bán: 12,314 vé", "Từ 750.000 VND", "Hà Nội"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh7, "Hội Thảo Công Nghệ - TechNext 2025", "Số vé đã bán: 6,832 vé", "Từ 300.000 VND", "TP. Hồ Chí Minh"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh8, "Festival Ánh Sáng Hội An", "Số vé đã bán: 9,105 vé", "Từ 500.000 VND", "Quảng Nam"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh9, "Hội Chợ Sách & Văn Hóa Đọc", "Số vé đã bán: 5,247 vé", "Miễn phí vào cửa", "Đà Nẵng"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh10, "Lễ Hội Ẩm Thực Việt - Vị Quê Hương", "Số vé đã bán: 7,980 vé", "Từ 100.000 VND", "Cần Thơ"));

        // Gắn adapter vào ListView qua binding
        adapter = new SuKienDaDangAdapter(this, dsSuKien);
        binding.lvSkdd.setAdapter(adapter);
    }
}
