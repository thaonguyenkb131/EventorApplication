package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.SukienxuhuongAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivitySukienxuhuongBinding;
import com.example.models.Thesukien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.util.Log;


public class SukienxuhuongActivity extends AppCompatActivity {

    private ActivitySukienxuhuongBinding binding;

    private ArrayList<Thesukien> dsSuKien;
    private SukienxuhuongAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySukienxuhuongBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadTrendingEventsFromDb();

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

        // Thiết lập tiêu đề và nút quay lại
        TextView txtHeaderTitle = findViewById(R.id.txtTitle);
        ImageView btnBack = findViewById(R.id.btnBack);

        txtHeaderTitle.setText("Sự kiện xu hướng");
        btnBack.setOnClickListener(v -> finish());

        binding.lvSukien.setOnItemClickListener((parent, view, position, id) -> {
            Thesukien selectedItem = dsSuKien.get(position);
            Intent intent = new Intent(SukienxuhuongActivity.this, ChitietsukienActivity.class);
            intent.putExtra("event_json", new com.google.gson.Gson().toJson(selectedItem));
            startActivity(intent);
        });

    }

    private void loadTrendingEventsFromDb() {
        Log.d("SukienxuhuongActivity", "Starting to load trending events...");
        
        // Hiển thị ProgressBar
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.lvSukien.setVisibility(View.GONE);
        
        dsSuKien = new ArrayList<>();
        adapter = new SukienxuhuongAdapter(this, dsSuKien);
        binding.lvSukien.setAdapter(adapter);
        
        // Add a test item to verify ListView is working
        Thesukien testEvent = new Thesukien();
        testEvent.setTitle("Test Event");
        testEvent.setSoldTicket(100);
        testEvent.setPrice(500000);
        testEvent.setCity("Test City");
        dsSuKien.add(testEvent);
        adapter.notifyDataSetChanged();
        Log.d("SukienxuhuongActivity", "Added test event, total items: " + dsSuKien.size());
        
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("topevents");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("SukienxuhuongActivity", "Data received from Firebase. Children count: " + dataSnapshot.getChildrenCount());
                dsSuKien.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Thesukien event = snapshot.getValue(Thesukien.class);
                    if (event != null) {
                        Log.d("SukienxuhuongActivity", "Added event: " + event.getTitle());
                        dsSuKien.add(event);
                    } else {
                        Log.w("SukienxuhuongActivity", "Event is null for snapshot: " + snapshot.getKey());
                    }
                }
                Log.d("SukienxuhuongActivity", "Total events loaded: " + dsSuKien.size());
                adapter.notifyDataSetChanged();
                
                // Ẩn ProgressBar và hiển thị ListView
                binding.progressBar.setVisibility(View.GONE);
                binding.lvSukien.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("SukienxuhuongActivity", "Database error: " + error.getMessage());
                // Ẩn ProgressBar và hiển thị ListView ngay cả khi có lỗi
                binding.progressBar.setVisibility(View.GONE);
                binding.lvSukien.setVisibility(View.VISIBLE);
            }
        });
    }

}
