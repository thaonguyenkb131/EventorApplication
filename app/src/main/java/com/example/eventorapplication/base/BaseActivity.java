package com.example.eventorapplication.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.example.eventorapplication.SukiencuatoiActivity;
import com.example.eventorapplication.TrangchuActivity;
import com.example.eventorapplication.TkdadangnhapActivity;
import com.example.eventorapplication.TaosukienActivity;
import com.example.eventorapplication.TrangthongbaoActivity;
import com.example.eventorapplication.R;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB binding;
    private View footerView;
    private int lastScrollY = 0;
    private boolean isFooterVisible = true;

    protected abstract VB inflateBinding();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflateBinding();
        setContentView(binding.getRoot());
        setupFooterNavigation();
    }

    private void setupFooterNavigation() {
        View homepage = binding.getRoot().findViewById(R.id.Homepage);
        View taosukien = binding.getRoot().findViewById(R.id.taosukien);
        View thongbao = binding.getRoot().findViewById(R.id.thongbao);
        View sukiencuatoi = binding.getRoot().findViewById(R.id.Sukiencuatoi);
        View taikhoan = binding.getRoot().findViewById(R.id.taikhoan);

        if (homepage != null) {
            addClickEffect(homepage);
            homepage.setOnClickListener(v -> {
                startActivity(new Intent(this, TrangchuActivity.class));
                finish();
            });
        }

        if (taosukien != null) {
            addClickEffect(taosukien);
            taosukien.setOnClickListener(v -> {
                startActivity(new Intent(this, TaosukienActivity.class));
                finish();
            });
        }

        if (thongbao != null) {
            addClickEffect(thongbao);
            thongbao.setOnClickListener(v -> {
                startActivity(new Intent(this, TrangthongbaoActivity.class));
                finish();
            });
        }

        if (sukiencuatoi != null) {
            addClickEffect(sukiencuatoi);
            sukiencuatoi.setOnClickListener(v -> {
                startActivity(new Intent(this, SukiencuatoiActivity.class));
                finish();
            });
        }
        if(taikhoan != null) {
            addClickEffect(taikhoan);
            taikhoan.setOnClickListener(v -> {
                startActivity(new Intent(this, TkdadangnhapActivity.class));
                finish();
            });
        }
    }

    protected void addClickEffect(View view) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    break;
            }
            return false; // vẫn truyền sự kiện cho OnClickListener
        });
    }

    protected void highlightActiveFooter(String activePageId) {
        int[] footerIds = {
                R.id.Homepage,
                R.id.taosukien,
                R.id.thongbao,
                R.id.Sukiencuatoi,
                R.id.taikhoan
        };

        for (int id : footerIds) {
            View item = binding.getRoot().findViewById(id);
            if (item != null) {
                if (getResources().getResourceEntryName(id).equals(activePageId)) {
                    item.setBackgroundResource(R.drawable.footer_item_selected); // đã chọn
                } else {
                    item.setBackgroundResource(R.drawable.footer_item_background); // mặc định
                }
            }
        }
    }



//    private void setupAutoHideFooter() {
//        View root = binding.getRoot();
//
//        // FooterActivity phải có id là R.id.footerLayout trong layout include
//        footerView = root.findViewById(R.id.footerLayout);
//        ScrollView scrollView = root.findViewById(R.id.scrollView);
//
//
//        if (footerView != null && scrollView != null) {
//            scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
//                int currentScrollY = scrollView.getScrollY();
//
//                if (currentScrollY > lastScrollY + 10 && isFooterVisible) {
//                    footerView.animate().translationY(footerView.getHeight()).setDuration(200);
//                    isFooterVisible = false;
//                } else if (currentScrollY < lastScrollY - 10 && !isFooterVisible) {
//                    footerView.animate().translationY(0).setDuration(200);
//                    isFooterVisible = true;
//                }
//
//                lastScrollY = currentScrollY;
////            });
//        }
//    }
}
