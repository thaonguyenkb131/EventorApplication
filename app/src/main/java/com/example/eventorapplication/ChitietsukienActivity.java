package com.example.eventorapplication;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.adapters.BinhluanAdapter;
import com.example.adapters.TicketCategoriesAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityChitietsukienBinding;
import com.example.models.BinhluanItem;
import com.example.models.Thesukien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChitietsukienActivity extends AppCompatActivity {

    private ActivityChitietsukienBinding binding;

    private boolean isSaved = false;
    private TicketCategoriesAdapter ticketAdapter;
    private List<BinhluanItem> binhluanList;
    private BinhluanAdapter adapter;
    private int visibleItemCount = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChitietsukienBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Ẩn toàn bộ nội dung chính, chỉ show khi đã có dữ liệu
        binding.getRoot().findViewById(R.id.scrollView_content).setVisibility(View.GONE);
        View layoutReviewAndComment = findViewById(R.id.layoutReviewAndComment);
        if (layoutReviewAndComment != null) layoutReviewAndComment.setVisibility(View.GONE);
        Button btnBuyTicket = findViewById(R.id.btnBuyTicket);
        if (btnBuyTicket != null) btnBuyTicket.setVisibility(View.GONE);


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

        binding.Organizer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChitietsukienActivity.this, TrangcanhanActivity.class);
                startActivity(intent);
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

        Thesukien event = null;
        String eventJson = getIntent().getStringExtra("event_json");
        if (eventJson != null) {
            event = new Gson().fromJson(eventJson, Thesukien.class);
            showEventDetail(event);
        }
        int eventId = getIntent().getIntExtra("event_id", -1);
        if (event == null && eventId != -1) {
            loadEventDetail(eventId);
        }

        // Dữ liệu mẫu
        binhluanList = new ArrayList<>();
        binhluanList.add(new BinhluanItem("Trần Ân Tú", "Sự kiện rất hay!", 2, R.drawable.avatar_ex));
        binhluanList.add(new BinhluanItem("Minh Anh", "Tổ chức chuyên nghiệp!", 4, R.drawable.avatar1));
        binhluanList.add(new BinhluanItem("Ngọc Lan", "Trải nghiệm thú vị!", 5, R.drawable.avatar2));
        binhluanList.add(new BinhluanItem("Trần Ân Tú", "Sự kiện rất hay!", 2, R.drawable.avatar3));
        binhluanList.add(new BinhluanItem("Minh Anh", "Tổ chức chuyên nghiệp!", 4, R.drawable.avatar4));
        binhluanList.add(new BinhluanItem("Ngọc Lan", "Trải nghiệm thú vị!", 5, R.drawable.avatar5));


// Khởi tạo RecyclerView
        binding.listBinhluan.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BinhluanAdapter(this, new ArrayList<>());
        binding.listBinhluan.setAdapter(adapter);
        updateCommentList(); // Hiển thị lần đầu

// Ẩn nếu không còn "Xem thêm"
        if (binhluanList.size() <= visibleItemCount) {
            binding.txtMore.setVisibility(View.GONE);
        }

        binding.txtMore.setOnClickListener(v -> {
            if (visibleItemCount < binhluanList.size()) {
                visibleItemCount = Math.min(visibleItemCount + 3, binhluanList.size());
                updateCommentList();
            }

            if (visibleItemCount >= binhluanList.size()) {
                binding.txtMore.setVisibility(View.GONE);
            }
        });

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
        // Khi đã có dữ liệu, show nội dung
        binding.getRoot().findViewById(R.id.scrollView_content).setVisibility(View.VISIBLE);
        // Hiển thị các trường dữ liệu từ Thesukien lên UI
        binding.txtName.setText(event.getTitle());
        if (event.getLocation().equalsIgnoreCase("Online")) {
            binding.txtLocation.setText("Online");
        } else {
            binding.txtLocation.setText(event.getLocation() + ", " + event.getDetailAddress());
        }
        binding.txtDate.setText(event.getDetailTime());
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

        // Ẩn/hiện phần đánh giá và bình luận dựa vào endDate
        boolean isPastEvent = false;
        String endDateStr = event.getDateEnd();
        if (endDateStr != null && !endDateStr.isEmpty()) {
            try {
                // Giả sử định dạng dd/MM/yyyy
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date endDate = sdf.parse(endDateStr);
                Date today = new Date();
                if (endDate != null && endDate.before(today)) {
                    isPastEvent = true;
                }
            } catch (ParseException e) {
                // Nếu lỗi parse thì coi như chưa kết thúc
                isPastEvent = false;
            }
        }
        // Ẩn/hiện toàn bộ khối đánh giá + bình luận
        View layoutReviewAndComment = findViewById(R.id.layoutReviewAndComment);
        if (layoutReviewAndComment != null) layoutReviewAndComment.setVisibility(isPastEvent ? View.VISIBLE : View.GONE);

        // Ẩn nút Đặt vé nếu sự kiện đã kết thúc
        Button btnBuyTicket = findViewById(R.id.btnBuyTicket);
        if (btnBuyTicket != null) btnBuyTicket.setVisibility(isPastEvent ? View.GONE : View.VISIBLE);
    }

    private void updateCommentList() {
        List<BinhluanItem> sublist = new ArrayList<>(binhluanList.subList(0, visibleItemCount));
        adapter.updateData(sublist);
    }

}