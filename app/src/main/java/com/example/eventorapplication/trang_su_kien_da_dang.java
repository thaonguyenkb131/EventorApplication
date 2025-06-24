package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adapters.SuKienDaDangAdapter;
import com.example.eventorapplication.R;
import com.example.models.SuKienDaDangItem;

import java.util.ArrayList;

public class trang_su_kien_da_dang extends AppCompatActivity {
    ListView lvSkdd;
    ArrayList<SuKienDaDangItem> dsSuKien;
    SuKienDaDangAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trang_su_kien_da_dang); // dùng đúng layout

        // Ánh xạ ListView
        lvSkdd = findViewById(R.id.lvSkdd);

        // Ánh xạ nút "Vé đã mua"
        Button btnPurchasedTickets = findViewById(R.id.btnPurchasedTickets);

        // Gán sự kiện chuyển trang
        btnPurchasedTickets.setOnClickListener(v -> {
            Intent intent = new Intent(trang_su_kien_da_dang.this, trang_ve_da_mua.class);
            startActivity(intent);
        });

        Button btnSavedEvents = findViewById(R.id.btnSavedEvents);

        btnSavedEvents.setOnClickListener(v -> {
            Intent intent = new Intent(trang_su_kien_da_dang.this, trang_ve_da_mua.class);
            intent.putExtra("clicked_button", "saved");
            startActivity(intent);
        });


        // Dữ liệu sự kiện
        dsSuKien = new ArrayList<>();
        dsSuKien.add(new SuKienDaDangItem(R.drawable.skxh1, "SuperFest - Concert Mùa Hè Rực Sáng", "Số vé đã bán: 10,982 vé", "", "Quảng Ninh"));
        dsSuKien.add(new SuKienDaDangItem(R.drawable.skxh2, "[River Flows In You] Đêm Nhạc Xương Rồng", "Số vé đã bán: 8,422 vé", "", "Hà Nội"));
        dsSuKien.add(new SuKienDaDangItem(R.drawable.skxh3, "Lễ Hội Ẩm Thực Việt - Vị Quê Hương", "Đã bán: 15,000 vé", "", "Hà Nội"));
        dsSuKien.add(new SuKienDaDangItem(R.drawable.skxh4, "The “Traditional Water Puppet Show” - Múa rối nước", "Đã bán: 14,500 vé", "", "TP.HCM"));
        dsSuKien.add(new SuKienDaDangItem(R.drawable.skxh5, "Hội Chợ Sách & Văn Hóa Đọc", "Đã bán: 14,500 vé", "", "TP.HCM"));
        dsSuKien.add(new SuKienDaDangItem(R.drawable.skxh6, "Đại Nhạc Hội Mùa Đông - Winter Beat", "Số vé đã bán: 12,314 vé", "Từ 750.000 VND", "Hà Nội"));
        dsSuKien.add(new SuKienDaDangItem(R.drawable.skxh7, "Hội Thảo Công Nghệ - TechNext 2025", "Số vé đã bán: 6,832 vé", "Từ 300.000 VND", "TP. Hồ Chí Minh"));
        dsSuKien.add(new SuKienDaDangItem(R.drawable.skxh8, "Festival Ánh Sáng Hội An", "Số vé đã bán: 9,105 vé", "Từ 500.000 VND", "Quảng Nam"));
        dsSuKien.add(new SuKienDaDangItem(R.drawable.skxh9, "Hội Chợ Sách & Văn Hóa Đọc", "Số vé đã bán: 5,247 vé", "Miễn phí vào cửa", "Đà Nẵng"));
        dsSuKien.add(new SuKienDaDangItem(R.drawable.skxh10, "Lễ Hội Ẩm Thực Việt - Vị Quê Hương", "Số vé đã bán: 7,980 vé", "Từ 100.000 VND", "Cần Thơ"));

        adapter = new SuKienDaDangAdapter(this, dsSuKien);
        lvSkdd.setAdapter(adapter);
    }
}
