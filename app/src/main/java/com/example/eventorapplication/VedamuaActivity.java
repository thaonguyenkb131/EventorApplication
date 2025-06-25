package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.VeDaMuaAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.models.VedamuaItem;
import com.example.eventorapplication.databinding.ActivityVedamuaBinding;

import java.util.ArrayList;

public class VedamuaActivity extends BaseActivity<ActivityVedamuaBinding> {
    ArrayList<VedamuaItem> listEvent;
    VeDaMuaAdapter adapter;

    @Override
    protected ActivityVedamuaBinding inflateBinding() {
        return ActivityVedamuaBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadData();
        addEvents();

        Intent intent = getIntent();
        String clickedButton = intent.getStringExtra("clicked_button");
        if ("saved".equals(clickedButton)) {
            highlightButton(binding.btnSavedEvents);
        } else if ("purchased".equals(clickedButton)) {
            highlightButton(binding.btnPurchasedTickets);
        } else if ("posted".equals(clickedButton)) {
            highlightButton(binding.btnPostedEvents);
        }

        // Tránh che màn hình
        View rootView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);

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

    private void highlightButton(View activeButton) {
        binding.btnSavedEvents.setAlpha(0.5f);
        binding.btnPurchasedTickets.setAlpha(0.5f);
        binding.btnPostedEvents.setAlpha(0.5f);
        activeButton.setAlpha(1.0f);
    }

    private void loadData() {
        listEvent = new ArrayList<>();

        listEvent.add(new VedamuaItem(R.drawable.vdmhappy, "Noo’s Chill Night’s Concert", "Từ 750.000 VND", "Hà Nội", "15/06/2025"));
        listEvent.add(new VedamuaItem(R.drawable.vdmquan, "Lễ hội Thể thao Giải trí hàng đầu tại Việt Nam - GAMA", "Từ 600.000 VND", "Vũng Tàu", "30/06/2025"));
        listEvent.add(new VedamuaItem(R.drawable.vdmforest, "Nhạc kịch “Sấm Vang Dòng Như Nguyệt”", "Từ 500.000 VND", "Hà Tĩnh", "21/06/2025"));
        listEvent.add(new VedamuaItem(R.drawable.vdmhtnau, "HBAshow: Lê Hiếu - Bạch Công Khanh \"Bài Tình Ca Cho Em\"", "Từ 300.000 VND", "TP HCM", "27/06/2025"));
        listEvent.add(new VedamuaItem(R.drawable.vdmamthuc, "Lễ hội ẩm thực Ấn Độ - Benaras Heritage Royal Indi", "Từ 750.000 VND", "Bình Dương", "10/07/2025"));
        listEvent.add(new VedamuaItem(R.drawable.vdmmotnha, "The East - Live Concert “Hạ” Thành Phố Huế", "Từ 1.000.000 VND", "Hải Phòng", "2/07/2025"));
        listEvent.add(new VedamuaItem(R.drawable.vdmthanhxuan, "Piano : Tiên nữ, giấc mơ và điệu vũ - David Greilsammer", "Từ 350.000 VND", "Bến Tre", "06/07/2025"));
        listEvent.add(new VedamuaItem(R.drawable.vdmtiama, "Madame Show - Những Đường Chim Bay", "Từ 250.000 VND", "Hà Tĩnh", "30/06/2025"));
        listEvent.add(new VedamuaItem(R.drawable.vdmbcn, "Lễ hội âm nhạc, sáng tạo Tràng An - Ninh Bình \"FORESTIVAL\"", "Từ 900.000 VND", "Ninh Bình", "28/06/2025"));
        listEvent.add(new VedamuaItem(R.drawable.vdmvmh, "Future With AI - AI và tương lai doanh nghiệp", "Từ 350.000 VND", "Hà Nội", "24/06/2025"));

        adapter = new VeDaMuaAdapter(this, listEvent);
        binding.gvVdm.setAdapter(adapter);
    }

    private void addEvents() {
        binding.gvVdm.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(VedamuaActivity.this, ChitietsukienActivity.class);
            startActivity(intent);
        });

        binding.btnSavedEvents.setOnClickListener(v -> highlightButton(binding.btnSavedEvents));
        binding.btnPurchasedTickets.setOnClickListener(v -> highlightButton(binding.btnPurchasedTickets));

        binding.btnPostedEvents.setOnClickListener(v -> {
            Intent intent = new Intent(VedamuaActivity.this, SukiendadangActivity.class);
            startActivity(intent);
        });
    }
}