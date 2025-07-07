package com.example.eventorapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTrangchuBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.adapters.BannerAdapter;
import com.example.models.Thesukien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.cardview.widget.CardView;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;

public class TrangchuActivity extends BaseActivity<ActivityTrangchuBinding> {

    private Handler sliderHandler = new Handler();
    private int[] imageResIds = {
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
    };

    @Override
    protected ActivityTrangchuBinding inflateBinding() {
        return ActivityTrangchuBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupBannerSlider();
        setupDots();
        setupAutoSlide();
        applyHoverEffects();
        addCardClickEvents(R.id.linearLayoutSknb);
        addCardClickEvents(R.id.linearLayoutSkxh);
        addCardClickEvents(R.id.linearLayoutDcb);

        setupDanhmucClick();

        // Progress bar
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        // Tránh che màn hình

        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che nút

            // Đẩy TextView xuống dưới status bar
            View txtTitle = findViewById(R.id.header);
            if (txtTitle != null) {
                txtTitle.setPadding(
                        txtTitle.getPaddingLeft(),
                        systemBars.top,
                        txtTitle.getPaddingRight(),
                        txtTitle.getPaddingBottom()
                );
            }

            return insets;
        });

        loadOutstandingEvents();
        loadTrendingEvents();
        loadForYouEvents();

        // Lấy FCM token và log ra Logcat
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String token = task.getResult();
                Log.d("FCM_TOKEN", token);
            } else {
                Log.w("FCM_TOKEN", "Lấy token thất bại", task.getException());
            }
        });
    }

    private void loadOutstandingEvents() {
        binding.progressBar.setVisibility(View.VISIBLE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("outstandingevents");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Thesukien> outstandingList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Thesukien event = snapshot.getValue(Thesukien.class);
                    if (event != null) {
                        outstandingList.add(event);
                    }
                }
                // Hiển thị danh sách thumbnail sự kiện nổi bật lên linearLayoutSknb
                binding.linearLayoutSknb.removeAllViews();
                Gson gson = new Gson();
                for (Thesukien event : outstandingList) {
                    CardView cardView = new CardView(TrangchuActivity.this);
                    LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                        (int) getResources().getDimension(R.dimen.sknb_card_width),
                        (int) getResources().getDimension(R.dimen.sknb_card_height)
                    );
                    cardParams.setMargins((int) getResources().getDimension(R.dimen.sknb_card_margin), 0, (int) getResources().getDimension(R.dimen.sknb_card_margin), 0);
                    cardView.setLayoutParams(cardParams);
                    cardView.setRadius(getResources().getDimension(R.dimen.sknb_card_radius));
                    cardView.setCardElevation(8f);
                    cardView.setUseCompatPadding(true);

                    ImageView imageView = new ImageView(TrangchuActivity.this);
                    imageView.setLayoutParams(new LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT
                    ));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    if (event.getThumbnail() != null && event.getThumbnail().startsWith("http")) {
                        com.bumptech.glide.Glide.with(TrangchuActivity.this)
                                .load(event.getThumbnail())
                                .placeholder(R.drawable.anhthaythe)
                                .into(imageView);
                    }
                    cardView.addView(imageView);
                    // Xử lý click: truyền object Thesukien dạng JSON
                    cardView.setOnClickListener(v -> {
                        Intent intent = new Intent(TrangchuActivity.this, ChitietsukienActivity.class);
                        intent.putExtra("event_json", gson.toJson(event));
                        startActivity(intent);
                    });
                    binding.linearLayoutSknb.addView(cardView);
                }
                binding.progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loadTrendingEvents() {
        binding.progressBarSkxh.setVisibility(View.VISIBLE);
        binding.linearLayoutSkxh.setVisibility(View.GONE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("topevents");
        ref.limitToFirst(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Không removeAllViews, giữ nguyên layout XML
                Gson gson = new Gson();
                int maxItems = 10;
                int idx = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (idx >= maxItems) break;
                    Thesukien event = snapshot.getValue(Thesukien.class);
                    if (event == null) continue;
                    int imgId = getResources().getIdentifier("imgSkxh" + (idx + 1), "id", getPackageName());
                    ImageView imgView = findViewById(imgId);
                    if (imgView != null) {
                        if (event.getThumbnail() != null && event.getThumbnail().startsWith("http")) {
                            com.bumptech.glide.Glide.with(TrangchuActivity.this)
                                    .load(event.getThumbnail())
                                    .placeholder(R.drawable.anhthaythe)
                                    .into(imgView);
                        } else {
                            imgView.setImageResource(R.drawable.anhthaythe);
                        }
                        // Set click cho CardView cha
                        View parent = (View) imgView.getParent();
                        while (parent != null && !(parent instanceof androidx.cardview.widget.CardView)) {
                            parent = (View) parent.getParent();
                        }
                        if (parent instanceof androidx.cardview.widget.CardView) {
                            parent.setOnClickListener(v -> {
                                Intent intent = new Intent(TrangchuActivity.this, ChitietsukienActivity.class);
                                intent.putExtra("event_json", gson.toJson(event));
                                startActivity(intent);
                            });
                        }
                    }
                    idx++;
                }
                binding.progressBarSkxh.setVisibility(View.GONE);
                binding.linearLayoutSkxh.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                binding.progressBarSkxh.setVisibility(View.GONE);
                binding.linearLayoutSkxh.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadForYouEvents() {
        binding.progressBar.setVisibility(View.VISIBLE);

        LinearLayout linearLayoutDcb = findViewById(R.id.linearLayoutDcb);
        if (linearLayoutDcb == null) return;
        linearLayoutDcb.removeAllViews();

        // Lấy userId từ SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        if (userId == null) {
            TextView warningView = createWarningView("Bạn chưa đăng nhập");
            linearLayoutDcb.addView(warningView);
            binding.progressBar.setVisibility(View.GONE);
            return;
        }

        // Lấy danh sách lĩnh vực quan tâm (Interests)
        DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference("accounts").child(userId).child("Interests");
        accountRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot interestSnapshot) {
                List<String> interests = new ArrayList<>();
                for (DataSnapshot tag : interestSnapshot.getChildren()) {
                    String interestKey = tag.getKey(); // "Âm nhạc", "Tình nguyện", ...
                    if (interestKey != null) interests.add(interestKey);
                }

                if (interests.isEmpty()) {
                    TextView warningView = createWarningView("Bạn chưa chọn lĩnh vực quan tâm");
                    linearLayoutDcb.addView(warningView);
                    binding.progressBar.setVisibility(View.GONE);
                    return;
                }

                // Lấy danh sách sự kiện
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("foryouevents");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        java.text.NumberFormat nf = java.text.NumberFormat.getInstance(new java.util.Locale("vi", "VN"));
                        int cardWidth = (int) getResources().getDimension(R.dimen.dcb_card_width);
                        int cardHeight = (int) getResources().getDimension(R.dimen.dcb_card_height);
                        float cardRadius = getResources().getDimension(R.dimen.dcb_card_radius);
                        Gson gson = new Gson();
                        Typeface montserrat = ResourcesCompat.getFont(TrangchuActivity.this, R.font.montserrat_semibold);

                        int matchedCount = 0;

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Thesukien event = snapshot.getValue(Thesukien.class);
                            if (event == null || event.getCategory() == null) continue;

                            // Lọc theo Interests
                            if (!interests.contains(event.getCategory())) continue;
                            matchedCount++;

                            // Tạo layout cho sự kiện...
                            LinearLayout eventLayout = new LinearLayout(TrangchuActivity.this);
                            eventLayout.setOrientation(LinearLayout.VERTICAL);
                            LinearLayout.LayoutParams eventParams = new LinearLayout.LayoutParams(
                                    cardWidth,
                                    LinearLayout.LayoutParams.WRAP_CONTENT
                            );
                            eventParams.setMargins(0, 0, 20, 0);
                            eventLayout.setLayoutParams(eventParams);
                            eventLayout.setGravity(android.view.Gravity.CENTER_HORIZONTAL);

                            // CardView chứa ảnh
                            CardView cardView = new CardView(TrangchuActivity.this);
                            cardView.setRadius(cardRadius);
                            cardView.setCardElevation(8f);
                            cardView.setUseCompatPadding(true);
                            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                                    cardWidth,
                                    cardHeight
                            );
                            cardView.setLayoutParams(cardParams);

                            ImageView imageView = new ImageView(TrangchuActivity.this);
                            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.MATCH_PARENT
                            ));
                            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                            if (event.getThumbnail() != null && event.getThumbnail().startsWith("http")) {
                                Glide.with(TrangchuActivity.this)
                                        .load(event.getThumbnail())
                                        .placeholder(R.drawable.default_dcb)
                                        .into(imageView);
                            } else {
                                imageView.setImageResource(R.drawable.default_dcb);
                            }
                            cardView.addView(imageView);
                            eventLayout.addView(cardView);

                            // Tên sự kiện
                            TextView titleView = new TextView(TrangchuActivity.this);
                            titleView.setText(event.getTitle());
                            titleView.setMaxLines(2);
                            titleView.setEllipsize(android.text.TextUtils.TruncateAt.END);
                            titleView.setTextColor(android.graphics.Color.BLACK);
                            titleView.setTextSize(14);
                            titleView.setGravity(android.view.Gravity.START);
                            if (montserrat != null)
                                titleView.setTypeface(montserrat, Typeface.BOLD);
                            titleView.setPadding((int) getResources().getDimension(R.dimen.dcb_text_padding_left), 0, 0, 0);
                            eventLayout.addView(titleView);

                            // Sau mỗi sự kiện hợp lệ, thêm vào linearLayoutDcb

                            linearLayoutDcb.addView(eventLayout);
                        }


                        // Nếu không có sự kiện nào phù hợp
                        if (matchedCount == 0) {
                            TextView warningView = createWarningView("Không có sự kiện phù hợp cho bạn");
                            linearLayoutDcb.addView(warningView);
                        }

                        binding.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        binding.progressBar.setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    private TextView createWarningView(String message) {
        TextView tv = new TextView(this);
        tv.setText(message);
        tv.setTextColor(Color.GRAY);
        tv.setTextSize(14);
        tv.setTypeface(null, Typeface.ITALIC);
        tv.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(16, 32, 16, 32);
        tv.setLayoutParams(params);
        return tv;
    }


    private void setupDanhmucClick() {
        List<View> danhMucCardViews = Arrays.asList(
                binding.Dmskan,
                binding.Dmskvh,
                binding.Dmskht,
                binding.Dmsktt,
                binding.Dmsktn,
                binding.Dmskmt,
                binding.Dmskdl
        );

        for (View cardView : danhMucCardViews) {
            cardView.setOnClickListener(v -> {
                // Lấy TextView phía dưới cùng cha LinearLayout
                View parent = (View) cardView.getParent();
                if (parent instanceof LinearLayout) {
                    LinearLayout parentLayout = (LinearLayout) parent;
                    for (int i = 0; i < parentLayout.getChildCount(); i++) {
                        View child = parentLayout.getChildAt(i);
                        if (child instanceof TextView) {
                            String category = ((TextView) child).getText().toString();
                            Intent intent = new Intent(this, KetquatimkiemActivity.class);
                            intent.putExtra("category", category);
                            startActivity(intent);
                            break;
                        }
                    }
                }
            });
        }

        // Danh mục trên thanh ngang (danhmuc)
        binding.txtamnhac.setOnClickListener(v -> openKetquatimkiemWithCategory(binding.txtamnhac.getText().toString()));
        binding.txtvanhoa.setOnClickListener(v -> openKetquatimkiemWithCategory(binding.txtvanhoa.getText().toString()));
        binding.txthocthuat.setOnClickListener(v -> openKetquatimkiemWithCategory(binding.txthocthuat.getText().toString()));
        binding.txtthethao.setOnClickListener(v -> openKetquatimkiemWithCategory(binding.txtthethao.getText().toString()));
        binding.txttinhnguyen.setOnClickListener(v -> openKetquatimkiemWithCategory(binding.txttinhnguyen.getText().toString()));
        binding.txtmoitruong.setOnClickListener(v -> openKetquatimkiemWithCategory(binding.txtmoitruong.getText().toString()));
        binding.txtdulich.setOnClickListener(v -> openKetquatimkiemWithCategory(binding.txtdulich.getText().toString()));
    }

    private void addCardClickEvents(int layoutContainerId) {
        LinearLayout layoutContainer = findViewById(layoutContainerId);

        if (layoutContainer == null) return;

        for (int i = 0; i < layoutContainer.getChildCount(); i++) {
            View child = layoutContainer.getChildAt(i);

            if (child instanceof androidx.cardview.widget.CardView) {
                // Trường hợp CardView nằm trực tiếp
                child.setOnClickListener(v -> openChiTietSuKien());

            } else if (child instanceof LinearLayout || child instanceof FrameLayout) {
                // CardView lồng trong LinearLayout hoặc FrameLayout
                ViewGroup innerLayout = (ViewGroup) child;
                for (int j = 0; j < innerLayout.getChildCount(); j++) {
                    View innerChild = innerLayout.getChildAt(j);
                    if (innerChild instanceof androidx.cardview.widget.CardView) {
                        innerChild.setOnClickListener(v -> openChiTietSuKien());
                        break;
                    }
                }
            }
        }
    }

    private void openChiTietSuKien() {
        Intent intent = new Intent(this, ChitietsukienActivity.class);
        startActivity(intent);
    }

    private void setupBannerSlider() {
        BannerAdapter adapter = new BannerAdapter(this, imageResIds);
        binding.viewPagerBanner.setAdapter(adapter);

        binding.imvtimkiem.setOnClickListener(v -> {
            startActivity(new Intent(this, TimkiemActivity.class));
        });

        binding.imvnhantin.setOnClickListener(v -> {
            startActivity(new Intent(this, ListtinnhanActivity.class));
        });

        binding.txtXemthem.setOnClickListener(v -> {
            startActivity(new Intent(this, SukienxuhuongActivity.class));
        });

        binding.viewPagerBanner.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateDots(position);
                sliderHandler.removeCallbacks(slideRunnable);
                sliderHandler.postDelayed(slideRunnable, 3000);
            }
        });

        binding.imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TrangchuActivity.this, ChatbotActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setupDots() {
        binding.dotsIndicator.removeAllViews();
        for (int i = 0; i < imageResIds.length; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageResource(R.drawable.dot_inactive);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            dot.setLayoutParams(params);
            binding.dotsIndicator.addView(dot);
        }
        updateDots(0);
    }

    private void updateDots(int currentPosition) {
        for (int i = 0; i < binding.dotsIndicator.getChildCount(); i++) {
            ImageView dot = (ImageView) binding.dotsIndicator.getChildAt(i);
            dot.setImageResource(i == currentPosition ? R.drawable.dot_active : R.drawable.dot_inactive);
        }
    }

    private Runnable slideRunnable = new Runnable() {
        @Override
        public void run() {
            int nextItem = (binding.viewPagerBanner.getCurrentItem() + 1) % imageResIds.length;
            binding.viewPagerBanner.setCurrentItem(nextItem, true);
        }
    };

    private void setupAutoSlide() {
        sliderHandler.postDelayed(slideRunnable, 3000);
    }

    private void applyHoverEffects() {
        int[] ids = {
                R.id.txtvanhoa, R.id.txtamnhac, R.id.txthocthuat,
                R.id.txtthethao, R.id.txttinhnguyen,
                R.id.txtmoitruong, R.id.txtdulich
        };

        for (int id : ids) {
            TextView tv = findViewById(id);
            tv.setOnHoverListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_HOVER_ENTER:
                        v.animate().scaleX(1.2f).scaleY(1.2f).setDuration(150).start();
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT:
                        v.animate().scaleX(1f).scaleY(1f).setDuration(150).start();
                        break;
                }
                return true;
            });
        }
    }

    private void openKetquatimkiemWithCategory(String category) {
        Intent intent = new Intent(this, KetquatimkiemActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(slideRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(slideRunnable, 3000);
    }

    @Override
    protected String getActiveFooterId() {
        return "Homepage";
    }
}
