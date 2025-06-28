package com.example.eventorapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.SukiendadangAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTrangcanhanBinding;
import com.example.models.SukiendadangItem;

import java.util.ArrayList;
import java.util.List;

public class TrangcanhanActivity extends BaseActivity<ActivityTrangcanhanBinding> {


    @Override
    protected ActivityTrangcanhanBinding inflateBinding() {
        return ActivityTrangcanhanBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //        Tránh che màn hình

        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che nút

            // Đẩy TextView xuống dưới status bar
            View txtTitle = findViewById(R.id.header);
            if(txtTitle != null) {
                txtTitle.setPadding(
                        txtTitle.getPaddingLeft(),
                        systemBars.top,
                        txtTitle.getPaddingRight(),
                        txtTitle.getPaddingBottom()
                );
            }

            return insets;
        });

        ListView lvSkdtc = findViewById(R.id.lvSkdtc);

        ArrayList<SukiendadangItem> Skdtc = new ArrayList<>();
        Skdtc.add(new SukiendadangItem(R.drawable.skxh1, "SuperFest - Concert Mùa Hè Rực Sáng", "Số vé đã bán: 10,982 vé", "", "Quảng Ninh"));
        Skdtc.add(new SukiendadangItem(R.drawable.skxh2, "[River Flows In You] Đêm Nhạc Xương Rồng", "Số vé đã bán: 8,422 vé", "", "Hà Nội"));

        SukiendadangAdapter adapter = new SukiendadangAdapter(this, Skdtc);
        lvSkdtc.setAdapter(adapter);

        TextView txtHeaderTitle = findViewById(R.id.txtTitle);
        ImageView btnBack = findViewById(R.id.btnBack);

        txtHeaderTitle.setText("Trang cá nhân");
        btnBack.setOnClickListener(v -> finish());

    }
}