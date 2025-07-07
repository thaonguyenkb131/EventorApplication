    package com.example.eventorapplication;

    import android.os.Bundle;
    import android.view.View;
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

    import com.example.models.Thesukien;
    import com.google.firebase.database.DataSnapshot;
    import com.google.firebase.database.DatabaseError;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.database.ValueEventListener;

    import java.util.ArrayList;
    import java.util.List;

    public class TrangcanhanActivity extends AppCompatActivity {

        private ActivityTrangcanhanBinding binding;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            binding = ActivityTrangcanhanBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

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
            // Ví dụ dữ liệu mẫu, bạn có thể load từ Firebase tương tự như ở Fragment
            Thesukien event1 = new Thesukien();
            event1.setTitle("SuperFest - Concert Mùa Hè Rực Sáng");
            event1.setSoldTicket(10982);
            event1.setPrice(0);
            event1.setLocation("Quảng Ninh");
            event1.setThumbnail("https://your-image-link.com/image1.jpg");
            Skdtc.add(event1);
            Thesukien event2 = new Thesukien();
            event2.setTitle("[River Flows In You] Đêm Nhạc Xương Rồng");
            event2.setSoldTicket(8422);
            event2.setPrice(0);
            event2.setLocation("Hà Nội");
            event2.setThumbnail("https://your-image-link.com/image2.jpg");
            Skdtc.add(event2);

            SukiendadangAdapter adapter = new SukiendadangAdapter(this, Skdtc);
            lvSkdtc.setAdapter(adapter);

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
                            String firstname = snapshot.child("Name").getValue(String.class);
                            String lastname = snapshot.child("Lastname").getValue(String.class);
                            String fullName = (lastname != null ? lastname : "") + " " + (firstname != null ? firstname : "");
                            ((TextView) findViewById(R.id.txtName)).setText(fullName.trim());
                            String email = snapshot.child("Email").getValue(String.class);
                            String city = snapshot.child("City").getValue(String.class);
                            if (city == null || city.isEmpty()) {
                                city = "Not found";
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
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(TrangcanhanActivity.this, "Không thể tải dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

    }