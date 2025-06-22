package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.adapters.OnboardingAdapter;
import com.example.models.OnboardingItem;

import java.util.ArrayList;
import java.util.List;

import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import androidx.core.graphics.Insets;

public class PageOnboarding extends AppCompatActivity {
    private ViewPager2 viewPager;
    private OnboardingAdapter adapter;
    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.page_onboarding);

        viewPager = findViewById(R.id.viewPager);

        List<OnboardingItem> items = new ArrayList<>();
        items.add(new OnboardingItem(R.drawable.slide_muaherucsang, "Tạo tài khoản miễn phí", "Chỉ mất vài bước, hoàn toàn miễn phí."));
        items.add(new OnboardingItem(R.drawable.slide_vct, "Tạo sự kiện không giới hạn", "Thỏa sức lên kế hoạch, tạo điểm hẹn cho những người bạn cùng chung đam mê. bạn là người dẫn dắt trải nghiệm, chúng tôi là người đồng hành."));
        items.add(new OnboardingItem(R.drawable.slide_bcn, "Khám phá nhiều sự kiện mới lạ ngay", "Tìm thấy những hoạt động phù hợp với sở thích và lịch trình của bạn. Dễ dàng lựa chọn, đăng ký và bắt đầu trải nghiệm mỗi ngày theo cách riêng."));

        adapter = new OnboardingAdapter(items);
        viewPager.setAdapter(adapter);

        // Khởi tạo 3 button dot
        Button btnDot1 = findViewById(R.id  .btnDot1);
        Button btnDot2 = findViewById(R.id.btnDot2);
        Button btnDot3 = findViewById(R.id.btnDot3);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                // Danh sách các button
                Button[] dots = {btnDot1, btnDot2, btnDot3};

                for (int i = 0; i < dots.length; i++) {
                    Button dot = dots[i];

                    // Đổi màu
                    int colorRes = (i == position) ? R.color.blue_1C9CCA : R.color.white;
                    dot.setBackgroundTintList(getColorStateList(colorRes));

                    // Đổi width
                    int newWidth = (i == position) ? 50 : 40; // hoặc to hơn tuỳ bạn
                    dot.getLayoutParams().width = (int) (newWidth * getResources().getDisplayMetrics().density); // dp to px
                    dot.requestLayout(); // Cập nhật lại layout sau khi thay đổi width
                }
            }
        });

        // Tự động chuyển slide
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == items.size()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);

        // Nút "Khám phá ngay"
        Button btnExplore = findViewById(R.id.btnExplore);
        btnExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PageOnboarding.this, MainActivity.class);
                startActivity(intent);
                finish(); // kết thúc màn hình hiện tại để không quay lại onboarding khi nhấn Back
            }
        });

//        Tránh che màn hình

        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che nút

            // Đẩy TextView xuống dưới status bar
            View txtTitle = findViewById(R.id.txtSpecialEvent);
            txtTitle.setPadding(
                    txtTitle.getPaddingLeft(),
                    systemBars.top,
                    txtTitle.getPaddingRight(),
                    txtTitle.getPaddingBottom()
            );

            return insets;
        });

    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

}