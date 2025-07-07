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

public class KetquatimkiemActivity extends BaseActivity<ActivityKetquatimkiemBinding> implements BolocDialog.FilterCallback {

    private ThesukienAdapter adapter;
    private ArrayList<Thesukien> dataList;
    private ArrayList<Thesukien> originalDataList; // Lưu dữ liệu gốc để lọc

    private Calendar fromDate = Calendar.getInstance();
    private Calendar toDate = Calendar.getInstance();
    private String category;
    private String searchQuery;
    private BolocDialog.FilterData currentFilter = new BolocDialog.FilterData();

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
        
        // Nhận bộ lọc từ Intent nếu có
        String[] filterCategories = getIntent().getStringArrayExtra("filter_categories");
        String[] filterLocations = getIntent().getStringArrayExtra("filter_locations");
        boolean filterFreeOnly = getIntent().getBooleanExtra("filter_free_only", false);
        String filterFromDate = getIntent().getStringExtra("filter_from_date");
        String filterToDate = getIntent().getStringExtra("filter_to_date");
        
        if (filterCategories != null) {
            for (String cat : filterCategories) {
                currentFilter.selectedCategories.add(cat);
            }
        }
        if (filterLocations != null) {
            for (String loc : filterLocations) {
                currentFilter.selectedLocations.add(loc);
            }
        }
        currentFilter.isFreeOnly = filterFreeOnly;
        currentFilter.fromDate = filterFromDate;
        currentFilter.toDate = filterToDate;
        
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

        binding.imgCalendar.setOnClickListener(v -> showDateRangePicker());
    }

    private void addControls() {
        dataList = new ArrayList<>();
        originalDataList = new ArrayList<>();
        adapter = new ThesukienAdapter(this, dataList);
        binding.gvKetquatimkiem.setAdapter(adapter);
        
        // Thêm click listener cho GridView
        binding.gvKetquatimkiem.setOnItemClickListener((parent, view, position, id) -> {
            if (position < dataList.size()) {
                Thesukien selectedEvent = dataList.get(position);
                Intent intent = new Intent(KetquatimkiemActivity.this, ChitietsukienActivity.class);
                com.google.gson.Gson gson = new com.google.gson.Gson();
                intent.putExtra("event_json", gson.toJson(selectedEvent));
                startActivity(intent);
            }
        });
    }

    private void loadDataFromRealtimeDatabase() {
        dataList.clear();
        originalDataList.clear();
        binding.progressBar.setVisibility(View.VISIBLE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                originalDataList.clear();
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
                            originalDataList.add(item);
                        }
                    }
                }
                applyFilter();
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
            dialog.setFilterCallback(this);
            dialog.setCurrentFilter(currentFilter);
            dialog.show(getSupportFragmentManager(), "BolocDialog");
        });
    }

    @Override
    public void onFilterApplied(BolocDialog.FilterData filterData) {
        currentFilter = filterData;
        applyFilter();
    }

    private void applyFilter() {
        dataList.clear();
        
        for (Thesukien event : originalDataList) {
            if (matchesFilter(event, currentFilter)) {
                dataList.add(event);
            }
        }
        
        adapter.notifyDataSetChanged();
        
        // Cập nhật tiêu đề
        updateTitle();
    }

    private boolean matchesFilter(Thesukien event, BolocDialog.FilterData filter) {
        // Kiểm tra danh mục
        if (!filter.selectedCategories.isEmpty()) {
            boolean categoryMatch = false;
            for (String category : filter.selectedCategories) {
                if (event.getCategory() != null && event.getCategory().equalsIgnoreCase(category)) {
                    categoryMatch = true;
                    break;
                }
            }
            if (!categoryMatch) return false;
        }
        
        // Kiểm tra địa điểm
        if (!filter.selectedLocations.isEmpty()) {
            boolean locationMatch = false;
            for (String location : filter.selectedLocations) {
                if (event.getCity() != null && event.getCity().equalsIgnoreCase(location)) {
                    locationMatch = true;
                    break;
                }
                // Xử lý trường hợp "Khác"
                if (location.equals("Khác")) {
                    if (event.getCity() != null && 
                        !event.getCity().equalsIgnoreCase("TP.Hồ Chí Minh") &&
                        !event.getCity().equalsIgnoreCase("Hà Nội") &&
                        !event.getCity().equalsIgnoreCase("Đà Nẵng")) {
                        locationMatch = true;
                        break;
                    }
                }
            }
            if (!locationMatch) return false;
        }
        
        // Kiểm tra miễn phí
        if (filter.isFreeOnly && event.getPrice() > 0) {
            return false;
        }
        
        // Kiểm tra thời gian
        if (filter.fromDate != null && filter.toDate != null && event.getDateStart() != null) {
            try {
                java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                java.util.Date from = sdf.parse(filter.fromDate);
                java.util.Date to = sdf.parse(filter.toDate);
                java.util.Date eventDate = sdf.parse(event.getDateStart());
                if (eventDate.before(from) || eventDate.after(to)) {
                    return false;
                }
            } catch (Exception e) {
                // Nếu lỗi parse thì bỏ qua lọc thời gian
            }
        }
        
        return true;
    }

    private void updateTitle() {
        StringBuilder title = new StringBuilder();
        
        if (category != null && !category.isEmpty()) {
            title.append("Danh mục: \"").append(category).append("\"");
        } else if (searchQuery != null && !searchQuery.isEmpty()) {
            title.append("Kết quả tìm kiếm cho \"").append(searchQuery).append("\"");
        } else {
            title.append("Tất cả sự kiện");
        }
        
        // Thêm thông tin bộ lọc nếu có
        if (!currentFilter.selectedCategories.isEmpty() || 
            !currentFilter.selectedLocations.isEmpty() || 
            currentFilter.isFreeOnly) {
            title.append(" (Đã lọc)");
        }
        
        binding.kqtimkiem.setText(title.toString());
    }

    private void showDateRangePicker() {
        DatePickerDialog fromDialog = new DatePickerDialog(
                this,
                R.style.MyDatePickerDialogTheme,
                (view, year, month, dayOfMonth) -> {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    java.util.Calendar cal = java.util.Calendar.getInstance();
                    cal.set(year, month, dayOfMonth);
                    currentFilter.fromDate = sdf.format(cal.getTime());
                    // Sau khi chọn ngày bắt đầu, chọn ngày kết thúc
                    DatePickerDialog toDialog = new DatePickerDialog(
                            this,
                            R.style.MyDatePickerDialogTheme,
                            (view2, year2, month2, dayOfMonth2) -> {
                                java.util.Calendar cal2 = java.util.Calendar.getInstance();
                                cal2.set(year2, month2, dayOfMonth2);
                                currentFilter.toDate = sdf.format(cal2.getTime());
                                applyFilter(); // Lọc lại ngay sau khi chọn xong
                            },
                            cal.get(java.util.Calendar.YEAR),
                            cal.get(java.util.Calendar.MONTH),
                            cal.get(java.util.Calendar.DAY_OF_MONTH)
                    );
                    toDialog.setTitle("Chọn ngày kết thúc");
                    toDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                    toDialog.show();
                },
                java.util.Calendar.getInstance().get(java.util.Calendar.YEAR),
                java.util.Calendar.getInstance().get(java.util.Calendar.MONTH),
                java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH)
        );
        fromDialog.setTitle("Chọn ngày bắt đầu");
        fromDialog.show();
    }

    @Override
    protected String getActiveFooterId() {
        return "Homepage"; // Footer này sẽ được highlight
    }
}