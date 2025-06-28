package com.example.eventorapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.ThesukienAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityKetquatimkiemBinding;
import com.example.models.Thesukien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

public class KetquatimkiemActivity extends BaseActivity<ActivityKetquatimkiemBinding> {

    private ThesukienAdapter adapter;
    private ArrayList<Thesukien> dataList;

    private Calendar fromDate = Calendar.getInstance();
    private Calendar toDate = Calendar.getInstance();
    private String category;
    private String searchQuery;

    @Override
    protected ActivityKetquatimkiemBinding inflateBinding() {
        return ActivityKetquatimkiemBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addControls();
        // Hiển thị progress khi bắt đầu load
        binding.progressBar.setVisibility(View.VISIBLE);
        category = getIntent().getStringExtra("category");
        searchQuery = getIntent().getStringExtra("search_query");
        loadDataFromRealtimeDatabase();
        addFilterEvent();

        // Hiển thị kết quả tìm kiếm nếu có dữ liệu được truyền sang
        // Lấy tên danh mục nếu có từ Intent và hiển thị lên kqtimkiem
        String category = getIntent().getStringExtra("category");
        if (category != null && !category.isEmpty()) {
            binding.kqtimkiem.setText("Danh mục: \"" + category + "\"");
        } else {
            String searchQuery = getIntent().getStringExtra("search_query");
            if (searchQuery != null && !searchQuery.isEmpty()) {
                binding.kqtimkiem.setText("Kết quả tìm kiếm cho \"" + searchQuery + "\"");
            } else {
                binding.kqtimkiem.setText("Tất cả sự kiện");
            }
        }

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

        binding.imgTimkiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KetquatimkiemActivity.this, TimkiemActivity.class);
                startActivity(intent);
            }
        });
        binding.nhapTimkiem.setOnTouchListener((v, event) -> {
            Intent intent = new Intent(KetquatimkiemActivity.this, TimkiemActivity.class);
            startActivity(intent);
            return true;
        });

        binding.imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateRangePicker();
            }
        });
    }

    private void addControls() {
        dataList = new ArrayList<>();
        adapter = new ThesukienAdapter(this, dataList);
        binding.gvKetquatimkiem.setAdapter(adapter);
    }

    private void loadDataFromRealtimeDatabase() {
        dataList.clear();
        binding.progressBar.setVisibility(View.VISIBLE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Thesukien item = child.getValue(Thesukien.class);
                    if (item != null) {
                        boolean matchCategory = (category == null || category.isEmpty() || (item.getCategory() != null && item.getCategory().equalsIgnoreCase(category)));
                        boolean matchSearch = true;
                        if (searchQuery != null && !searchQuery.isEmpty()) {
                            String q = searchQuery.toLowerCase();
                            matchSearch = (item.getTitle() != null && item.getTitle().toLowerCase().contains(q))
                                    || (item.getCategory() != null && item.getCategory().toLowerCase().contains(q))
                                    || (item.getOrganizer() != null && item.getOrganizer().toLowerCase().contains(q));
                        }
                        if (matchCategory && matchSearch) {
                            dataList.add(item);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.GONE);
                if (dataList.isEmpty()) {
                    String query = (searchQuery != null && !searchQuery.isEmpty()) ? searchQuery : "";
                    binding.kqtimkiem.setText("Không tìm thấy kết quả cho \"" + query + "\"");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
                Log.e("Ketquatimkiem", "Error getting data: ", error.toException());
            }
        });
    }

    private void addFilterEvent() {
        binding.imgFilter.setOnClickListener(v -> {
            BolocDialog dialog = new BolocDialog();
            dialog.show(getSupportFragmentManager(), "BolocDialog");
        });

    }

    private void showDateRangePicker() {
        DatePickerDialog fromDialog = new DatePickerDialog(
                this,
                R.style.MyDatePickerDialogTheme,
                (view, year, month, dayOfMonth) -> {
                    fromDate.set(year, month, dayOfMonth);
                    DatePickerDialog toDialog = new DatePickerDialog(
                            this,
                            R.style.MyDatePickerDialogTheme,
                            (view2, year2, month2, dayOfMonth2) -> {
                                toDate.set(year2, month2, dayOfMonth2);
                                // TODO: Xử lý ngày đã chọn ở đây (fromDate, toDate)
                            },
                            toDate.get(Calendar.YEAR),
                            toDate.get(Calendar.MONTH),
                            toDate.get(Calendar.DAY_OF_MONTH)
                    );
                    toDialog.setTitle("Chọn ngày kết thúc");
                    toDialog.getDatePicker().setMinDate(fromDate.getTimeInMillis());
                    toDialog.show();
                },
                fromDate.get(Calendar.YEAR),
                fromDate.get(Calendar.MONTH),
                fromDate.get(Calendar.DAY_OF_MONTH)
        );
        fromDialog.setTitle("Chọn ngày bắt đầu");
        fromDialog.show();
    }
}