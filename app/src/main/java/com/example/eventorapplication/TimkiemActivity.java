package com.example.eventorapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.TimkiemgvAdapter;
import com.example.adapters.TimkiemlvAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTimkiemBinding;
import com.example.models.TimkiemgvItem;
import com.example.models.TimkiemlvItem;

import java.util.ArrayList;
import java.util.Calendar;

public class TimkiemActivity extends BaseActivity<ActivityTimkiemBinding> {

    private ArrayList<TimkiemlvItem> data;
    private TimkiemlvAdapter adapter;

    private ArrayList<TimkiemgvItem> phvbList;
    private TimkiemgvAdapter gvAdapter;

    private Calendar fromDate = Calendar.getInstance();
    private Calendar toDate = Calendar.getInstance();

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
        startActivity(intent);
    }

    // Xử lý sự kiện listview kết quả tìm kiếm
    private void setupListView() {
        data = new ArrayList<>();
        data.add(new TimkiemlvItem("lập trình", R.drawable.icclock, R.drawable.ictrash));
        data.add(new TimkiemlvItem("workshop", R.drawable.icclock, R.drawable.ictrash));
        data.add(new TimkiemlvItem("văn hóa", R.drawable.icclock, R.drawable.ictrash));
        data.add(new TimkiemlvItem("vẽ", R.drawable.icgrowth, null));
        data.add(new TimkiemlvItem("nhạc kịch", R.drawable.icgrowth, null));
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
        phvbList = new ArrayList<>();
        phvbList.add(new TimkiemgvItem(R.drawable.phvb1, "Sân khấu Thiên Đăng -  Cái gì vui vẻ thì mình ưu tiên", "Từ 300.000 VND", "TP.Hồ Chí Minh", "06/06/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb2, "Lễ hội âm nhạc, sáng tạo Tràng An, Ninh Bình - FORESTIVAL 2025", "Từ 700.000 VND", "Ninh Bình", "07/07/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb3, "Madame Show - Những đường chim bay", "Từ 700.000 VND", "Đà Nẵng", "10/07/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb4, "Sân khấu nhạc kịch - Cái gì vui vẻ thì mình ưu tiên", "Từ 300.000 VND", "TP.Hồ Chí Minh", "15/06/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb5, "BOYF DEBUT SHOWCASE -  Cái gì vui vẻ thì mình ưu tiên", "Từ 300.000 VND", "TP.Hồ Chí Minh", "15/06/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb6, "Lễ hội âm nhạc SPERFEST - Lễ hội ánh sáng", "Từ 800.000 VND", "TP.Hồ Chí Minh", "01/07/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb7, "Sân khấu nhạc kịch - Hành tinh nâu", "Từ 150.000 VND", "Tp. Hồ Chí Minh", "07/07/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb8, "Lululola Show - Hương Tràm - Cái gì vui vẻ thì mình ưu tiên\"", "Từ 250.000 VND", "Đà Lạt", "16/07/2025"));

        gvAdapter = new TimkiemgvAdapter(this, phvbList);
        binding.gvPhuhopvoiban.setAdapter(gvAdapter);

        binding.gvPhuhopvoiban.post(() ->
                setGridViewHeightBasedOnChildren(binding.gvPhuhopvoiban, 2)
        );
    }

    // Sự kiện filter
    private void addFilterEvent() {
        binding.imgFilter.setOnClickListener(v -> {
            BolocDialog dialog = new BolocDialog();
            dialog.show(getSupportFragmentManager(), "BolocDialog");
        });
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
                    fromDate.set(year, month, dayOfMonth);
                    // Sau khi chọn ngày bắt đầu, chọn ngày kết thúc
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

    @Override
    protected String getActiveFooterId() {
        return "Homepage"; // Footer này sẽ được highlight
    }
}