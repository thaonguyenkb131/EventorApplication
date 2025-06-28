package com.example.eventorapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.TicketCategoriesAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityChitietsukienBinding;
import com.example.models.Thesukien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChitietsukienActivity extends BaseActivity<ActivityChitietsukienBinding> {


    private boolean isSaved = false;
    private TicketCategoriesAdapter ticketAdapter;

    @Override
    protected ActivityChitietsukienBinding inflateBinding() {
        return ActivityChitietsukienBinding.inflate(getLayoutInflater());
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
            txtTitle.setPadding(
                    txtTitle.getPaddingLeft(),
                    systemBars.top,
                    txtTitle.getPaddingRight(),
                    txtTitle.getPaddingBottom()
            );

            return insets;
        });

        binding.btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChitietsukienActivity.this, ChitiettinnhanActivity.class);
                startActivity(intent);
            }
        });

        binding.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ChitietsukienActivity.this);
                dialog.setContentView(R.layout.dialog_chiasesk);

                dialog.show();
            }
        });

        binding.btnBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(ChitietsukienActivity.this);
                dialog.setContentView(R.layout.dialog_soluongve);

                dialog.show();

                Button btnXacnhan = dialog.findViewById(R.id.btnXacnhan);
                btnXacnhan.setOnClickListener(view -> {
                    Intent intent = new Intent(ChitietsukienActivity.this, ThanhtoanActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                });
            }
        });

        // Sự kiện đổi hình nền khi nhấn "Lưu sự kiện"
        LinearLayout btnSave = findViewById(R.id.btnSave);
        LinearLayout saveIcon = (LinearLayout) btnSave.getChildAt(0); // layout nền chứa icon

        btnSave.setOnClickListener(v -> {
            if (!isSaved) {
                saveIcon.setBackgroundResource(R.drawable.save2); // đổi sang ảnh đã lưu
                isSaved = true;
            } else {
                saveIcon.setBackgroundResource(R.drawable.save); // trở lại ảnh gốc
                isSaved = false;
            }
        });

        // Thiết lập tiêu đề và nút quay lại
        TextView txtHeaderTitle = findViewById(R.id.txtTitle);
        ImageView btnBack = findViewById(R.id.btnBack);

        txtHeaderTitle.setText("Chi tiết sự kiện");
        btnBack.setOnClickListener(v -> finish());

        // Khởi tạo RecyclerView cho các hạng mục vé
        RecyclerView rcvTicketCategories = findViewById(R.id.rcvTicketCategories);
        rcvTicketCategories.setLayoutManager(new LinearLayoutManager(this));
        ticketAdapter = new TicketCategoriesAdapter(null); // Khởi tạo rỗng, sẽ cập nhật sau
        rcvTicketCategories.setAdapter(ticketAdapter);

        int eventId = getIntent().getIntExtra("event_id", -1);
        if (eventId != -1) {
            loadEventDetail(eventId);
        }
    }

    private void loadEventDetail(int eventId) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");
        ref.orderByChild("id").equalTo(eventId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Thesukien event = snapshot.getValue(Thesukien.class);
                    if (event != null) {
                        showEventDetail(event);
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // handle error
            }
        });
    }

    private void showEventDetail(Thesukien event) {
        // Hiển thị các trường dữ liệu từ Thesukien lên UI
        binding.txtName.setText(event.getTitle());
        binding.txtLocation.setText(event.getLocation());
        binding.txtDate.setText(event.getDate());
        binding.txtDescription.setText(event.getDescription());
        binding.txtOrganizer.setText(event.getOrganizer());
        if (event.getThumbnail() != null && event.getThumbnail().startsWith("http")) {
            com.bumptech.glide.Glide.with(this).load(event.getThumbnail()).into(binding.imvThumb);
        }
        // Hiển thị các hạng mục vé
        if (event.getTicketCategories() != null) {
            ticketAdapter = new TicketCategoriesAdapter(event.getTicketCategories());
            RecyclerView rcvTicketCategories = findViewById(R.id.rcvTicketCategories);
            rcvTicketCategories.setAdapter(ticketAdapter);
        }
    }

}