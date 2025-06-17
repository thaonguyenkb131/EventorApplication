package com.example.eventorapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.KetquatimkiemAdapter;
import com.example.eventorapplication.databinding.ActivityKetquatimkiemBinding;
import com.example.models.KetquatimkiemItem;

import java.util.ArrayList;

public class Ketquatimkiem extends AppCompatActivity {

    private ActivityKetquatimkiemBinding binding;
    private KetquatimkiemAdapter adapter;
    private ArrayList<KetquatimkiemItem> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityKetquatimkiemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addControls();
        loadData();
    }

    private void addControls() {
        dataList = new ArrayList<>();
        adapter = new KetquatimkiemAdapter(this, dataList);
        binding.gvKetquatimkiem.setAdapter(adapter);
    }

    private void loadData() {
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk1, "The East - Live Concert Hạ Thành Phố Huế", "Từ 800.000 VND", "Ninh Bình", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk2, "Lễ hội ẩm thực Ấn Độ - Benaras Heritage Royal Indian Food", "Từ 800.000 VND", "Ninh Bình", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk3, "Madame Show - Những đường chim bay", "Từ 700.000 VND", "Lâm Đồng", "10/07/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk4, "Lễ hội âm nhạc - Sáng tạo Tràng An, Ninh Bình - FORESTIVAL Show", "Từ 270.000 VND", "TP. Hồ Chí Minh", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk5, "Đêm nhạc xương rồng - Một Nhà. Thung lũng hoa Hồ Tây", "Từ 300.000 VND", "Ninh Bình", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk6, "Lễ hội âm nhạc - Sáng tạo Tràng An, Ninh Bình - FORESTIVAL Show", "Từ 270.000 VND", "TP. Hồ Chí Minh", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk7, "Lễ hội Thể thao giải trí hàng đầu tại Việt Nam - GAMA ESPORT GAME", "Từ 700.000 VND", "Lâm Đồng", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk8, "Nhạc kịch Sấm Vang Dòng Như Nguyệt", "Từ 270.000 VND", "Tp. Hồ Chí Minh", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk9, "Đêm nhạc xương rồng - Một Nhà. Thung lũng hoa Hồ Tây", "Từ 300.000 VND", "Ninh Bình", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk10, "Lễ hội âm nhạc - Sáng tạo Tràng An, Ninh Bình - FORESTIVAL Show", "Từ 800.000 VND", "TP. Hồ Chí Minh", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk11, "Lễ hội Thể thao giải trí hàng đầu tại Việt Nam - GAMA ESPORT GAME", "Từ 700.000 VND", "Lâm Đồng", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk12, "Nhà hát kịch IDECAP", "Từ 270.000 VND", "Tp. Hồ Chí Minh", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk13, "Đêm nhạc xương rồng - Một Nhà. Thung lũng hoa Hồ Tây", "Từ 300.000 VND", "Ninh Bình", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk14, "Lễ hội âm nhạc - Sáng tạo Tràng An, Ninh Bình - FORESTIVAL Show", "Từ 800.000 VND", "TP. Hồ Chí Minh", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk15, "Lễ hội Thể thao giải trí hàng đầu tại Việt Nam - GAMA ESPORT GAME", "Từ 700.000 VND", "Lâm Đồng", "15/06/2025"));
        dataList.add(new KetquatimkiemItem(R.drawable.kqtk16, "Nhà hát kịch IDECAF: Tấm Cám đại chiến", "Từ 270.000 VND", "Tp. Hồ Chí Minh", "15/06/2025"));

        adapter.notifyDataSetChanged();
    }
}