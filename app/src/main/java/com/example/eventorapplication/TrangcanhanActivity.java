    package com.example.eventorapplication;

    import android.app.Dialog;
    import android.content.Context;
    import android.content.Intent;
    import android.graphics.Color;
    import android.graphics.drawable.ColorDrawable;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;
    import android.widget.ImageView;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import com.bumptech.glide.Glide;
    import com.example.adapters.SukiendadangAdapter;
    import com.example.eventorapplication.base.BaseActivity;
    import com.example.eventorapplication.databinding.ActivityTrangcanhanBinding;
    import com.google.android.flexbox.FlexboxLayout;
    import com.example.models.Thesukien;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class TrangcanhanActivity extends AppCompatActivity {

        private ActivityTrangcanhanBinding binding;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityTrangcanhanBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            View mainLayout = findViewById(R.id.main);
            mainLayout.setVisibility(View.GONE);

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

            ArrayList<Thesukien> Skdtc = new ArrayList<>();
            SukiendadangAdapter adapter = new SukiendadangAdapter(this, Skdtc);
            lvSkdtc.setAdapter(adapter);

            // Load data from Firebase 'postedevents'
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("postedevents");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Skdtc.clear();
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Thesukien event = child.getValue(Thesukien.class);
                        if (event != null) {
                            Skdtc.add(event);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(TrangcanhanActivity.this, "Không thể tải dữ liệu sự kiện đã đăng", Toast.LENGTH_SHORT).show();
                }
            });

            lvSkdtc.setOnItemClickListener((parent, view, position, id) -> {
                if (position < Skdtc.size()) {
                    Thesukien selectedEvent = Skdtc.get(position);
                    Intent intent = new Intent(TrangcanhanActivity.this, ChitietsukienActivity.class);
                    com.google.gson.Gson gson = new com.google.gson.Gson();
                    intent.putExtra("event_json", gson.toJson(selectedEvent));
                    startActivity(intent);
                }
            });

            TextView txtHeaderTitle = findViewById(R.id.txtTitle);
            ImageView btnBack = findViewById(R.id.btnBack);

            txtHeaderTitle.setText("Trang cá nhân");
            btnBack.setOnClickListener(v -> finish());

            String userId = getIntent().getStringExtra("userId");

            if (userId != null) {
                DatabaseReference userRef = FirebaseDatabase.getInstance()
                        .getReference("accounts").child(userId);

                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String firstname = snapshot.child("name").getValue(String.class);
                            String lastname = snapshot.child("lastname").getValue(String.class);
                            String email = snapshot.child("email").getValue(String.class);
                            String fullName = (lastname != null ? lastname : "") + " " + (firstname != null ? firstname : "");
                            ((TextView) findViewById(R.id.txtName)).setText(fullName.trim());
                            String city = snapshot.child("City").getValue(String.class);
                            if (city == null || city.isEmpty()) {
                                city = "Hồ Chí Minh";
                            }
                            String avatarUrl = snapshot.child("avatarUrl").getValue(String.class);
                            String coverUrl = snapshot.child("coverUrl").getValue(String.class);

                            // Gán vào View
                            binding.txtName.setText(fullName);
                            binding.txtEmail.setText(email);
                            binding.txtCity.setText(city);

                            if (avatarUrl != null && !avatarUrl.isEmpty()) {
                                Glide.with(TrangcanhanActivity.this).load(avatarUrl).into(binding.imgAvatar);
                            }
                            if (coverUrl != null && !coverUrl.isEmpty()) {
                                Glide.with(TrangcanhanActivity.this).load(coverUrl).into(binding.imgCover);
                            }
                        }

                        // Hiển thị Interests dưới dạng emoji tag
                        DataSnapshot interestsSnapshot = snapshot.child("Interests");
                        FlexboxLayout flexboxLayout = findViewById(R.id.flexboxLayout);

// Xóa tag cũ (tránh trùng lặp khi refresh)
                        flexboxLayout.removeAllViews();

                        if (interestsSnapshot.exists()) {
                            for (DataSnapshot entry : interestsSnapshot.getChildren()) {
                                String interest = entry.getValue(String.class); // "🎼 Âm nhạc"
                                if (interest != null && !interest.isEmpty()) {
                                    TextView tagView = new TextView(TrangcanhanActivity.this);
                                    tagView.setText(interest);
                                    tagView.setTextSize(15);
                                    tagView.setTextColor(Color.BLACK);
                                    tagView.setBackgroundResource(R.drawable.bg_tag); // tạo file bg_tag.xml nếu chưa có
                                    tagView.setPadding(30, 15, 30, 15);

                                    // set margin
                                    FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                                            FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                            FlexboxLayout.LayoutParams.WRAP_CONTENT);
                                    params.setMargins(10, 10, 10, 10);
                                    tagView.setLayoutParams(params);

                                    flexboxLayout.addView(tagView);
                                }
                            }
                        } else {
                            // Nếu chưa có lĩnh vực, có thể thêm một TextView thông báo
                            TextView emptyView = new TextView(TrangcanhanActivity.this);
                            emptyView.setText("Chưa có lĩnh vực quan tâm");
                            emptyView.setTextColor(Color.GRAY);
                            emptyView.setPadding(30, 15, 30, 15);
                            flexboxLayout.addView(emptyView);
                        }

                        TextView plusTag = new TextView(TrangcanhanActivity.this);
                        plusTag.setId(R.id.tagAdd);
                        plusTag.setBackgroundResource(R.drawable.baseline_add_24);
                        plusTag.setTextColor(Color.parseColor("#006183"));
                        plusTag.setTextSize(24);
                        plusTag.setGravity(View.TEXT_ALIGNMENT_CENTER);
                        FlexboxLayout.LayoutParams addParams = new FlexboxLayout.LayoutParams(50, 50);
                        addParams.setMargins(10, 10, 10, 10);
                        plusTag.setLayoutParams(addParams);
                        flexboxLayout.addView(plusTag);

                        plusTag.setOnClickListener(v -> {
                            showDialogLinhVucQuanTam(userId); // Gọi lại hàm hiển thị dialog
                        });
                        mainLayout.setVisibility(View.VISIBLE); // Hiện layout sau khi đã load xong dữ liệu
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TrangcanhanActivity.this, "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
                        mainLayout.setVisibility(View.VISIBLE); // Hiện layout kể cả khi lỗi để tránh bị ẩn vĩnh viễn
                    }
                });
            }


        }
        private void showDialogLinhVucQuanTam(String id) {
            View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_linhvucquantam, null);
            Dialog dialog = new Dialog(this);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(dialogView);
            dialog.setCancelable(false);
            dialog.getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            dialog.show();

            FlexboxLayout tagContainer = dialogView.findViewById(R.id.tagContainer);
            Map<String, String> selectedInterestMap = new HashMap<>();

            // BƯỚC 1: Lấy dữ liệu đã chọn từ Firebase
            DatabaseReference ref = FirebaseDatabase.getInstance()
                    .getReference("accounts")
                    .child(id)
                    .child("Interests");

            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Map<String, String> savedMap = new HashMap<>();

                    for (DataSnapshot tagSnap : snapshot.getChildren()) {
                        String key = tagSnap.getKey();               // VD: "Âm nhạc"
                        String value = tagSnap.getValue(String.class); // VD: "🎵 Âm nhạc"
                        if (key != null && value != null) {
                            savedMap.put(key, value);
                        }
                    }

                    // BƯỚC 2: Duyệt từng tag trong layout và gắn xử lý
                    for (int i = 0; i < tagContainer.getChildCount(); i++) {
                        View view = tagContainer.getChildAt(i);
                        if (view instanceof TextView) {
                            TextView textView = (TextView) view;
                            String key = String.valueOf(textView.getTag()); // Lấy từ android:tag
                            String fullText = textView.getText().toString(); // "🎵 Âm nhạc"

                            // Nếu đã chọn → đánh dấu
                            if (savedMap.containsKey(key)) {
                                selectedInterestMap.put(key, fullText);
                                textView.setBackgroundResource(R.drawable.bg_tagselected);
                                textView.setTextColor(Color.WHITE);
                            } else {
                                textView.setBackgroundResource(R.drawable.bg_tag);
                                textView.setTextColor(Color.BLACK);
                            }

                            // Lắng nghe sự kiện chọn/bỏ chọn
                            String finalKey = key;
                            textView.setOnClickListener(v -> {
                                if (selectedInterestMap.containsKey(finalKey)) {
                                    selectedInterestMap.remove(finalKey);
                                    textView.setBackgroundResource(R.drawable.bg_tag);
                                    textView.setTextColor(Color.BLACK);
                                } else {
                                    selectedInterestMap.put(finalKey, fullText);
                                    textView.setBackgroundResource(R.drawable.bg_tagselected);
                                    textView.setTextColor(Color.WHITE);
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(TrangcanhanActivity.this, "Không thể tải lĩnh vực", Toast.LENGTH_SHORT).show();
                }
            });

            // BƯỚC 3: Lưu
            Button btnSave = dialogView.findViewById(R.id.btnSave);
            btnSave.setOnClickListener(v -> {
                if (selectedInterestMap.isEmpty()) {
                    Toast.makeText(this, "Vui lòng chọn ít nhất một lĩnh vực", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Lưu vào Firebase theo dạng key: fullText (VD: "Âm nhạc": "🎵 Âm nhạc")
                DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference("accounts");
                accountRef.child(id).child("Interests").setValue(selectedInterestMap)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(this, "Đã lưu lĩnh vực quan tâm", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                recreate(); // Reload giao diện
                            }
                        });
            });
        }
    }