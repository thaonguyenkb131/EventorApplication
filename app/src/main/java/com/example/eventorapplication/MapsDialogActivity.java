package com.example.eventorapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adapters.MapAdapter;
import com.example.eventorapplication.databinding.ActivityMapsDialogBinding;
import com.example.models.MapItem;

import java.util.ArrayList;

public class MapsDialogActivity extends AppCompatActivity {
    ActivityMapsDialogBinding binding;
    ArrayList<MapItem> items;
    MapAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadData();

        adapter = new MapAdapter(this, items);
        binding.listRecentPlaces.setAdapter(adapter);
    }

    private void loadData() {
        items = new ArrayList<>();
        items.add(new MapItem("Trường Đại học Kinh tế Luật, ĐHQG TPHCM", "669 Quốc lộ 1A, phường Linh Xuân, TP Thủ Đức"));
        items.add(new MapItem("Emerald Center", "Trung tâm Emerald, Đường N4, Centre Place, Quận 1"));
        items.add(new MapItem("Vạn Phúc City - Trung tâm Mới", "Quốc lộ 13, khu phố 6, Hiệp Bình Phước, Thành phố Thủ Đức"));
        items.add(new MapItem("Ký túc xá khu B - ĐHQG TPHCM", "Mạc Đỉnh Chi, khu phố Tân Hòa, Dĩ An, Bình Dương"));
    }
}
