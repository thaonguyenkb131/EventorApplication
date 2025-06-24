package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityMainBinding;

import androidx.annotation.Nullable;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.os.Handler;

import com.example.adapters.BannerAdapter;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private Handler sliderHandler = new Handler();
    private int[] imageResIds = {
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3
    };

    @Override
    protected ActivityMainBinding inflateBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
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
                            Intent intent = new Intent(this, Ketquatimkiem.class);
                            intent.putExtra("category", category);
                            startActivity(intent);
                            break;
                        }
                    }
                }
            });
        }
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
        Intent intent = new Intent(this, TrangChiTietSuKien.class);
        startActivity(intent);
    }

    private void setupBannerSlider() {
        BannerAdapter adapter = new BannerAdapter(this, imageResIds);
        binding.viewPagerBanner.setAdapter(adapter);

        binding.imvtimkiem.setOnClickListener(v -> {
            startActivity(new Intent(this, Timkiem.class));
        });

        binding.imvnhantin.setOnClickListener(v -> {
            startActivity(new Intent(this, Tinnhan.class));
        });

        binding.txtamnhac.setOnClickListener(v -> {
            startActivity(new Intent(this,Ketquatimkiem.class));
        });

        binding.txtvanhoa.setOnClickListener(v -> {
            startActivity(new Intent(this,Ketquatimkiem.class));
        });

        binding.txthocthuat.setOnClickListener(v -> {
            startActivity(new Intent(this,Ketquatimkiem.class));
        });

        binding.txtthethao.setOnClickListener(v -> {
            startActivity(new Intent(this,Ketquatimkiem.class));
        });

        binding.txttinhnguyen.setOnClickListener(v -> {
            startActivity(new Intent(this,Ketquatimkiem.class));
        });

        binding.txtmoitruong.setOnClickListener(v -> {
            startActivity(new Intent(this,Ketquatimkiem.class));
        });

        binding.txtdulich.setOnClickListener(v -> {
            startActivity(new Intent(this,Ketquatimkiem.class));
        });

        binding.txtXemthem.setOnClickListener(v -> {
            startActivity(new Intent(this,Sukienxuhuong.class));
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
}
