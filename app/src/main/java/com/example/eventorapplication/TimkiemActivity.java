package com.example.eventorapplication;

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

public class TimkiemActivity extends BaseActivity<ActivityTimkiemBinding> {

    private ArrayList<TimkiemlvItem> data;
    private TimkiemlvAdapter adapter;

    private ArrayList<TimkiemgvItem> phvbList;
    private TimkiemgvAdapter gvAdapter;

    @Override
    protected ActivityTimkiemBinding inflateBinding() {
        return ActivityTimkiemBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addEvents();

        //        Tránh che màn hình

        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che nút

            // Đẩy TextView xuống dưới status bar
            View txtTitle = findViewById(R.id.Thanhtimkiem);
            txtTitle.setPadding(
                    txtTitle.getPaddingLeft(),
                    systemBars.top,
                    txtTitle.getPaddingRight(),
                    txtTitle.getPaddingBottom()
            );

            return insets;
        });
    }

    private void addEvents() {
        setupListView();
        setupGridView();
        addFilterEvent();
    }

    // Xử lý sự kiện listview kết quả tìm kiếm
    private void setupListView() {
        data = new ArrayList<>();
        data.add(new TimkiemlvItem("jun phạm", R.drawable.icclock, R.drawable.ictrash));
        data.add(new TimkiemlvItem("workshop", R.drawable.icclock, R.drawable.ictrash));
        data.add(new TimkiemlvItem("làm gốm", R.drawable.icclock, R.drawable.ictrash));
        data.add(new TimkiemlvItem("vẽ", R.drawable.icgrowth, null));
        data.add(new TimkiemlvItem("nhạc kịch", R.drawable.icgrowth, null));
        data.add(new TimkiemlvItem("múa rối", R.drawable.icgrowth, null));

        adapter = new TimkiemlvAdapter(this, data);
        binding.lvTimkiem.setAdapter(adapter);

        binding.lvTimkiem.post(() ->
                setListViewHeightBasedOnItems(binding.lvTimkiem)
        );
    }

    // Xử lý sự kiện gridview
    private void setupGridView() {
        phvbList = new ArrayList<>();
        phvbList.add(new TimkiemgvItem(R.drawable.phvb1, "Sân khấu Thiên Đăng", "Từ 300.000 VND", "TP.Hồ Chí Minh", "06/06/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb2, "Lễ hội âm nhạc, sáng tạo Tràng An, Ninh Bình - FORESTIVAL 2025", "Từ 700.000 VND", "Ninh Bình", "07/07/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb3, "Madame Show - Những đường chim bay", "Từ 700.000 VND", "Đà Nẵng", "10/07/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb4, "Sân khấu nhạc kịch - Cái gì vui vẻ thì mình ưu tiên", "Từ 300.000 VND", "TP.Hồ Chí Minh", "15/06/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb5, "BOYF DEBUT SHOWCASE", "Từ 300.000 VND", "TP.Hồ Chí Minh", "15/06/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb6, "Lễ hội âm nhạc SPERFEST - Lễ hội ánh sáng", "Từ 800.000 VND", "TP.Hồ Chí Minh", "01/07/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb7, "Sân khấu nhạc kịch - Hành tinh nâu", "Từ 150.000 VND", "Tp. Hồ Chí Minh", "07/07/2025"));
        phvbList.add(new TimkiemgvItem(R.drawable.phvb8, "Lululola Show - Hương Tràm", "Từ 250.000 VND", "Đà Lạt", "16/07/2025"));

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

    // Tính chiều cao GridView theo số cột và chiều cao item lớn nhất
    private void setGridViewHeightBasedOnChildren(GridView gridView, int numColumns) {
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) return;

        int items = listAdapter.getCount();
        int rows = (int) Math.ceil((double) items / numColumns);

        // Đo chiều cao lớn nhất của item trong một hàng
        int maxItemHeight = 0;
        for (int i = 0; i < Math.min(numColumns, items); i++) {
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(
                    View.MeasureSpec.makeMeasureSpec(gridView.getWidth(), View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            );
            maxItemHeight = Math.max(maxItemHeight, listItem.getMeasuredHeight());
        }

        int totalHeight = maxItemHeight * rows + gridView.getVerticalSpacing() * (rows - 1) + 20; // +20dp tránh cắt

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight;
        gridView.setLayoutParams(params);
        gridView.requestLayout();
    }
}
