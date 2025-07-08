package com.example.eventorapplication;

import android.os.Bundle;
import android.content.Intent;
import android.app.Activity;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adapters.MapAdapter;
import com.example.eventorapplication.databinding.DialogMapBinding;
import com.example.models.MapItem;

import java.util.ArrayList;

public class MapDialog extends AppCompatActivity {
    DialogMapBinding binding;
    ArrayList<MapItem> items;
    MapAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogMapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadData();

        adapter = new MapAdapter(this, items);
        binding.listRecentPlaces.setAdapter(adapter);

        // Handle item click to return address
        binding.listRecentPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                MapItem selected = items.get(position);
                Intent result = new Intent();
                result.putExtra("selected_address", selected.getTxtDiachi());
                setResult(Activity.RESULT_OK, result);
                finish();
            }
        });

        // Handle back button click to return
        binding.ivBack.setOnClickListener(v -> finish());
    }

    private void loadData() {
        items = new ArrayList<>();
        items.add(new MapItem("Trường Đại học Kinh tế Luật, ĐHQG TPHCM", "669 Quốc lộ 1A, phường Linh Xuân, TP Thủ Đức"));
        items.add(new MapItem("Emerald Center", "Trung tâm Emerald, Đường N4, Centre Place, Quận 1"));
        items.add(new MapItem("Vạn Phúc City - Trung tâm Mới", "Quốc lộ 13, khu phố 6, Hiệp Bình Phước, Thành phố Thủ Đức"));
        items.add(new MapItem("Ký túc xá khu B - ĐHQG TPHCM", "Mạc Đỉnh Chi, khu phố Tân Hòa, Dĩ An, Bình Dương"));
    }
}
