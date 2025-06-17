package com.example.eventorapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adapters.TimkiemgvAdapter;
import com.example.adapters.TimkiemlvAdapter;
import com.example.eventorapplication.databinding.ActivityTimkiemBinding;
import com.example.models.TimkiemgvItem;
import com.example.models.TimkiemlvItem;

import java.util.ArrayList;

public class Timkiem extends AppCompatActivity {
    ActivityTimkiemBinding binding;

    private ArrayList<TimkiemlvItem> data;
    private TimkiemlvAdapter adapter;

    private ArrayList<TimkiemgvItem> phvbList;
    private TimkiemgvAdapter gvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTimkiemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvents();
    }

    private void addEvents() {
        setupListView();
        setupGridView();
        addFilterEvent();
    }
    //Xử lý sự kiện listview kết quả tìm kiếm
    private void setupListView() {
        data = new ArrayList<>();
        data.add(new TimkiemlvItem("jun phạm", R.drawable.icclock, R.drawable.ictrash));
        data.add(new TimkiemlvItem("workshop", R.drawable.icclock, R.drawable.ictrash));
        data.add(new TimkiemlvItem("làm gốm", R.drawable.icclock, R.drawable.ictrash));
        data.add(new TimkiemlvItem("vẽ", R.drawable.icgrowth, null));
        data.add(new TimkiemlvItem("nhạc kịch", R.drawable.icgrowth, null));
        data.add(new TimkiemlvItem("múa rối", R.drawable.icgrowth, null));

        adapter = new TimkiemlvAdapter(this, data);
        binding.lvTimkiem.setAdapter(adapter);
    }
//Xử lý sự kiện gridview phù hợp với bạn

    private void setupGridView() {
        phvbList = new ArrayList<>();
        phvbList.add(new TimkiemgvItem(R.drawable.phvb1, "Sân khấu Thiên Đăng", "Từ 300.000 VND", "TP.Hồ Chí Minh", "06/06/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb2, "Lễ hội âm nhạc, sáng tạo Tràng An, Ninh Bình - FORESTIVAL 2025", "Từ 700.000 VND", "Ninh Bình", "07/07/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb3, "Madame Show - Những đường chim bay", "Từ 700.000 VND", "Đà Nẵng", "10/07/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb4, "Sân khấu nhạc kịch - Cái gì vui vẻ th mình ưu tiên", "Từ 300.000 VND", "TP.Hồ Chí Minh", "15/06/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb5, "BOYF DEBUT SHOWCASE", "Từ 300.000 VND", "TP.Hồ Chí Minh", "15/06/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb6, "Lễ hội âm nhạc SPERFEST - Lễ hội ánh sáng", "Từ 800.000 VND", "TP.Hồ Chí Minh", "01/07/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb7, "Sân khấu nhạc kịch - Hành tinh nâu", "Từ 150.000 VND", "Tp. Hồ Chí Minh", "07/07/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb8, "Lululola Show - Hương Tràm", "Từ 250.000 VND", "Đà Lạt", "16/07/2025"));

        gvAdapter = new TimkiemgvAdapter(this, phvbList);
        binding.gvPhuhopvoiban.setAdapter(gvAdapter);
    }
    //Xử lý filter bộ lọc
    private void addFilterEvent() {
        binding.imgFilter.setOnClickListener(v -> {
            BolocDialog dialog = new BolocDialog();
            dialog.show(getSupportFragmentManager(), "BolocDialog");
        });
    }
}
