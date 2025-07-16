package com.example.eventorapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.ThesukienAdapter;
import com.example.adapters.TimkiemlvAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTimkiemBinding;
import com.example.models.TimkiemlvItem;
import com.example.models.Thesukien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class TimkiemActivity extends BaseActivity<ActivityTimkiemBinding> {

    private ArrayList<TimkiemlvItem> data;
    private TimkiemlvAdapter adapter;

    private ArrayList<Thesukien> phvbList;
    private ThesukienAdapter gvAdapter;

    private Calendar fromDate = Calendar.getInstance();
    private Calendar toDate = Calendar.getInstance();

    private BolocDialog.FilterData currentFilter = new BolocDialog.FilterData();

    @Override
    protected ActivityTimkiemBinding inflateBinding() {
        return ActivityTimkiemBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addEvents();

        // Tránh che footer, đồng bộ hiệu ứng với các trang khác
        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, 0); // Không set padding bottom cho rootView

            // Đẩy LinearLayout tìm kiếm xuống dưới status bar
            View txtTitle = findViewById(R.id.Thanhtimkiem);
            if (txtTitle != null) {
                txtTitle.setPadding(
                        txtTitle.getPaddingLeft(),
                        systemBars.top,
                        txtTitle.getPaddingRight(),
                        txtTitle.getPaddingBottom()
                );
            }

            // Đảm bảo footer không bị che bởi thanh điều hướng
            View footer = findViewById(R.id.footerLayout);
            if (footer != null) {
                footer.setPadding(
                        footer.getPaddingLeft(),
                        footer.getPaddingTop(),
                        footer.getPaddingRight(),
                        systemBars.bottom
                );
            }

            return insets;
        });
    }

    private void addEvents() {
        setupListView();
        setupGridView();
        addFilterEvent();
        setupDanhmucClick();

        binding.imgCalendar.setOnClickListener(v -> showDateRangePicker());
        // Sự kiện click vào icon tìm kiếm
        binding.imgTimkiem.setOnClickListener(v -> navigateToKetquatimkiem());
        // Sự kiện nhấn Enter trong EditText
        binding.edtTimkiem.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH) {
                navigateToKetquatimkiem();
                return true;
            }
            return false;
        });
    }

    private void navigateToKetquatimkiem() {
        Intent intent = new Intent(this, KetquatimkiemActivity.class);
        String searchText = binding.edtTimkiem.getText().toString();
        intent.putExtra("search_query", searchText);
        intent.putExtra("filter_categories", currentFilter.selectedCategories.toArray(new String[0]));
        intent.putExtra("filter_locations", currentFilter.selectedLocations.toArray(new String[0]));
        intent.putExtra("filter_free_only", currentFilter.isFreeOnly);
        intent.putExtra("filter_from_date", currentFilter.fromDate);
        intent.putExtra("filter_to_date", currentFilter.toDate);
        startActivity(intent);
    }

    // Xử lý sự kiện listview kết quả tìm kiếm
    private void setupListView() {
        data = new ArrayList<>();
        data.add(new TimkiemlvItem("lập trình", R.drawable.icclock, R.drawable.ictrash));
        data.add(new TimkiemlvItem("workshop", R.drawable.icclock, R.drawable.ictrash));
        data.add(new TimkiemlvItem("văn hóa", R.drawable.icclock, R.drawable.ictrash));
        data.add(new TimkiemlvItem("vẽ", R.drawable.icgrowth, null));
        data.add(new TimkiemlvItem("việc làm", R.drawable.icgrowth, null));
        data.add(new TimkiemlvItem("hội nghị", R.drawable.icgrowth, null));

        adapter = new TimkiemlvAdapter(this, data);
        binding.lvTimkiem.setAdapter(adapter);

        binding.lvTimkiem.post(() ->
                setListViewHeightBasedOnItems(binding.lvTimkiem)
        );

        binding.lvTimkiem.setOnItemClickListener((parent, view, position, id) -> {
            TimkiemlvItem item = adapter.getItem(position);
            if (item != null) {
                Intent intent = new Intent(this, KetquatimkiemActivity.class);
                intent.putExtra("search_query", item.getTitle());
                startActivity(intent);
            }
        });
    }

    // Xử lý sự kiện gridview
    private void setupGridView() {
        loadAdvertisedEvents();
    }

    private void loadAdvertisedEvents() {
        phvbList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("events");
        ref.orderByChild("mode").equalTo("advertised").limitToFirst(8).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Thesukien event = snapshot.getValue(Thesukien.class);
                    if (event != null) {
                        phvbList.add(event);
                    }
                }
                
                // Xáo trộn danh sách để hiển thị ngẫu nhiên
                Collections.shuffle(phvbList);

                gvAdapter = new ThesukienAdapter(TimkiemActivity.this, phvbList);
                binding.gvPhuhopvoiban.setAdapter(gvAdapter);

                // Thêm click listener cho GridView
                binding.gvPhuhopvoiban.setOnItemClickListener((parent, view, position, id) -> {
                    if (position < phvbList.size()) {
                        Thesukien selectedEvent = phvbList.get(position);
                        Intent intent = new Intent(TimkiemActivity.this, ChitietsukienActivity.class);
                        Gson gson = new Gson();
                        intent.putExtra("event_json", gson.toJson(selectedEvent));
                        startActivity(intent);
                    }
                });

                binding.gvPhuhopvoiban.post(() ->
                        setGridViewHeightBasedOnChildren(binding.gvPhuhopvoiban, 2)
                );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Nếu lỗi, tạo dữ liệu mẫu với Thesukien
                Thesukien event1 = new Thesukien();
                event1.setTitle("Sân khấu Thiên Đăng - Cái gì vui vẻ thì mình ưu tiên");
                event1.setPrice(300000);
                event1.setLocation("TP.Hồ Chí Minh");
                event1.setDate("06/06/2025");
                phvbList.add(event1);

                Thesukien event2 = new Thesukien();
                event2.setTitle("Lễ hội âm nhạc, sáng tạo Tràng An, Ninh Bình - FORESTIVAL 2025");
                event2.setPrice(700000);
                event2.setLocation("Ninh Bình");
                event2.setDate("07/07/2025");
                phvbList.add(event2);

                gvAdapter = new ThesukienAdapter(TimkiemActivity.this, phvbList);
                binding.gvPhuhopvoiban.setAdapter(gvAdapter);

                binding.gvPhuhopvoiban.post(() ->
                        setGridViewHeightBasedOnChildren(binding.gvPhuhopvoiban, 2)
                );
            }
        });
    }

    // Sự kiện filter
    private void addFilterEvent() {
        binding.imgFilter.setOnClickListener(v -> {
            BolocDialog dialog = new BolocDialog();
            dialog.setFilterCallback(new BolocDialog.FilterCallback() {
                @Override
                public void onFilterApplied(BolocDialog.FilterData filterData) {
                    currentFilter = filterData;
                    updateFilterIcon();
                    updateCalendarIcon();
                }
            });
            dialog.setCurrentFilter(currentFilter);
            dialog.show(getSupportFragmentManager(), "BolocDialog");
        });
        
        // Cập nhật icon ban đầu
        updateFilterIcon();
        updateCalendarIcon();
    }
    
    private void updateFilterIcon() {
        // Kiểm tra xem có bộ lọc filter nào được áp dụng không (không bao gồm date)
        boolean hasFilter = !currentFilter.selectedCategories.isEmpty() || 
                           !currentFilter.selectedLocations.isEmpty() || 
                           currentFilter.isFreeOnly;
        
        if (hasFilter) {
            binding.imgFilter.setImageResource(R.drawable.icboloc_filled);
        } else {
            binding.imgFilter.setImageResource(R.drawable.ic_filter);
        }
    }
    
    private void updateCalendarIcon() {
        // Kiểm tra xem có bộ lọc ngày nào được áp dụng không
        boolean hasDateFilter = (currentFilter.fromDate != null && currentFilter.toDate != null);
        
        if (hasDateFilter) {
            binding.imgCalendar.setImageResource(R.drawable.icbolocdate_filled);
        } else {
            binding.imgCalendar.setImageResource(R.drawable.ic_calendar);
        }
    }

    // Xử lý click vào danh mục
    private void setupDanhmucClick() {
        // Lấy LinearLayout chứa tất cả danh mục
        LinearLayout dmTheloai = findViewById(R.id.DmTheloai);
        if (dmTheloai != null) {
            // Duyệt qua tất cả các LinearLayout con (mỗi danh mục)
            for (int i = 0; i < dmTheloai.getChildCount(); i++) {
                View child = dmTheloai.getChildAt(i);
                if (child instanceof LinearLayout) {
                    LinearLayout danhMucLayout = (LinearLayout) child;
                    danhMucLayout.setOnClickListener(v -> {
                        // Tìm TextView chứa tên danh mục
                        for (int j = 0; j < danhMucLayout.getChildCount(); j++) {
                            View danhMucChild = danhMucLayout.getChildAt(j);
                            if (danhMucChild instanceof TextView) {
                                String category = ((TextView) danhMucChild).getText().toString();
                                Intent intent = new Intent(TimkiemActivity.this, KetquatimkiemActivity.class);
                                intent.putExtra("category", category);
                                startActivity(intent);
                                break;
                            }
                        }
                    });
                }
            }
        }
    }

    // Tính chiều cao ListView để hiển thị toàn bộ nội dung trong ScrollView
    private void setListViewHeightBasedOnItems(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) return;

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View item = listAdapter.getView(i, null, listView);
            item.measure(
                    View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            totalHeight += item.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    // Tính chiều cao GridView theo số cột và chiều cao item lớn nhất từng hàng
    private void setGridViewHeightBasedOnChildren(GridView gridView, int numColumns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) return;

        int items = listAdapter.getCount();
        int rows = (int) Math.ceil((double) items / numColumns);

        int totalHeight = 0;
        int gridViewWidth = gridView.getWidth();
        if (gridViewWidth == 0) {
            // Nếu width chưa có, post lại sau khi layout xong
            gridView.post(() -> setGridViewHeightBasedOnChildren(gridView, numColumns));
            return;
        }

        for (int row = 0; row < rows; row++) {
            int maxHeightInRow = 0;
            for (int col = 0; col < numColumns; col++) {
                int index = row * numColumns + col;
                if (index < items) {
                    View listItem = listAdapter.getView(index, null, gridView);
                    int itemWidth = (gridViewWidth
                        - gridView.getPaddingLeft()
                        - gridView.getPaddingRight()
                        - gridView.getHorizontalSpacing() * (numColumns - 1)) / numColumns;
                    listItem.measure(
                        View.MeasureSpec.makeMeasureSpec(itemWidth, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
                    );
                    maxHeightInRow = Math.max(maxHeightInRow, listItem.getMeasuredHeight());
                }
            }
            totalHeight += maxHeightInRow;
        }

        if (rows > 0) {
            totalHeight += gridView.getVerticalSpacing() * (rows - 1);
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + gridView.getPaddingTop() + gridView.getPaddingBottom();
        gridView.setLayoutParams(params);
        gridView.requestLayout();
    }

    private void showDateRangePicker() {
        // Chọn ngày bắt đầu
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
                                updateFilterIcon(); // Cập nhật icon sau khi chọn ngày
                                updateCalendarIcon(); // Cập nhật icon calendar sau khi chọn ngày
                            },
                            toDate.get(Calendar.YEAR),
                            toDate.get(Calendar.MONTH),
                            toDate.get(Calendar.DAY_OF_MONTH)
                    );
                    toDialog.setTitle("Chọn ngày kết thúc");
                    toDialog.getDatePicker().setMinDate(cal.getTimeInMillis());
                    toDialog.show();
                },
                fromDate.get(Calendar.YEAR),
                fromDate.get(Calendar.MONTH),
                fromDate.get(Calendar.DAY_OF_MONTH)
        );
        fromDialog.setTitle("Chọn ngày bắt đầu");
        fromDialog.show();
    }

    @Override
    protected String getActiveFooterId() {
        return "Homepage";
    }

    @Override
    protected void scrollToTopIfNeeded(String footerId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}