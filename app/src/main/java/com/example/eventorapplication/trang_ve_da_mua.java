package com.example.eventorapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.adapters.VeDaMuaAdapter;
import com.example.eventorapplication.R;
import com.example.models.VeDaMua;
import com.example.eventorapplication.databinding.ActivityTrangVeDaMuaBinding;

import java.util.ArrayList;

public class trang_ve_da_mua extends AppCompatActivity {

    ActivityTrangVeDaMuaBinding binding;
    ArrayList<VeDaMua> listEvent;
    VeDaMuaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTrangVeDaMuaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadData();
        addEvents();
    }

    private void loadData() {
        listEvent = new ArrayList<>();

        listEvent.add(new VeDaMua(R.drawable.vdmhappy, "Noo’s Chill Night’s Concert", "Từ 750.000 VND", "Hà Nội", "15/06/2025"));
        listEvent.add(new VeDaMua(R.drawable.vdmquan, "Lễ hội Thể thao Giải trí hàng đầu tại Việt Nam - GAMA", "Từ 600.000 VND", "Vũng Tàu", "30/06/2025"));
        listEvent.add(new VeDaMua(R.drawable.vdmforest, "Nhạc kịch “Sấm Vang Dòng Như Nguyệt”", "Từ 500.000 VND", "Hà Tĩnh", "21/06/2025"));
        listEvent.add(new VeDaMua(R.drawable.vdmhtnau, "HBAshow: Lê Hiếu - Bạch Công Khanh \"Bài Tình Ca Cho Em\"", "Từ 300.000 VND", "TP HCM", "27/06/2025"));
        listEvent.add(new VeDaMua(R.drawable.vdmamthuc, "Lễ hội ẩm thực Ấn Độ - Benaras Heritage Royal Indi", "Từ 750.000 VND", "Bình Dương", "10/07/2025"));
        listEvent.add(new VeDaMua(R.drawable.vdmmotnha, "The East - Live Concert “Hạ” Thành Phố Huế", "Từ 1.000.000 VND", "Hải Phòng", "2/07/2025"));
        listEvent.add(new VeDaMua(R.drawable.vdmthanhxuan, "Piano : Tiên nữ, giấc mơ và điệu vũ - David Greilsammer", "Từ 350.000 VND", "Bến Tre", "06/07/2025"));
        listEvent.add(new VeDaMua(R.drawable.vdmtiama, "Madame Show - Những Đường Chim Bay", "Từ 250.000 VND", "Hà Tĩnh", "30/06/2025"));
        listEvent.add(new VeDaMua(R.drawable.vdmbcn, "Lễ hội âm nhạc, sáng tạo Tràng An - Ninh Bình \"FORESTIVAL\"", "Từ 900.000 VND", "Ninh Bình", "28/06/2025"));
        listEvent.add(new VeDaMua(R.drawable.vdmvmh, "Future With AI - AI và tương lai doanh nghiệp", "Từ 350.000 VND", "Hà Nội", "24/06/2025"));

        adapter = new VeDaMuaAdapter(this, listEvent);
        binding.gvVdm.setAdapter(adapter);
    }

    private void addEvents() {
        binding.gvVdm.setOnItemClickListener((parent, view, position, id) -> {
            VeDaMua sp = listEvent.get(position);

            Dialog dialog = new Dialog(trang_ve_da_mua.this);
            dialog.setCancelable(false);

            ImageView imv = dialog.findViewById(R.id.imvThumb);
            TextView name = dialog.findViewById(R.id.tvTitle);
            TextView price = dialog.findViewById(R.id.tvPrice);
            TextView location = dialog.findViewById(R.id.tvLocation);
            TextView date = dialog.findViewById(R.id.tvDate);

            // Giả lập ảnh thumbnail nếu có (nếu không có có thể dùng ảnh mặc định)
            imv.setImageResource(R.drawable.eventimage);
            name.setText(sp.getTitle());
            price.setText(sp.getPrice());
            location.setText(sp.getLocation());
            date.setText(sp.getDate());

        });
    }
}
