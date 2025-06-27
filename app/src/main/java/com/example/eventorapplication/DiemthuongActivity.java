package com.example.eventorapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.DiemthuongAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityDiemthuongBinding;
import com.example.models.DiemthuongItem;

import java.util.ArrayList;
import java.util.List;

public class DiemthuongActivity extends BaseActivity<ActivityDiemthuongBinding> {

    @Override
    protected ActivityDiemthuongBinding inflateBinding() {
        return ActivityDiemthuongBinding.inflate(getLayoutInflater());
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
        // Khởi tạo danh sách điểm thưởng
        List<DiemthuongItem> items = new ArrayList<>();
        items.add(new DiemthuongItem("Sự kiện #MAHD86", "Mar 13, 2025", "+75"));
        items.add(new DiemthuongItem("Sự kiện #RFSS3D", "May 31, 2025", "+50"));
        items.add(new DiemthuongItem("Sự kiện #MAHD86", "Mar 13, 2025", "+75"));
        items.add(new DiemthuongItem("Sự kiện #MAHD87", "Mar 14, 2025", "+50"));

        ListView listView = findViewById(R.id.listHistory);
        DiemthuongAdapter adapter = new DiemthuongAdapter(this, items);
        listView.setAdapter(adapter);

        // Thiết lập tiêu đề và nút quay lại
        TextView txtHeaderTitle = findViewById(R.id.txtTitle);
        ImageView btnBack = findViewById(R.id.btnBack);

        txtHeaderTitle.setText("Điểm thưởng");
        btnBack.setOnClickListener(v -> finish());

    }


}