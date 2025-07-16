package com.example.eventorapplication.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.example.eventorapplication.SukiencuatoiActivity;
import com.example.eventorapplication.TrangchuActivity;
import com.example.eventorapplication.TkdadangnhapActivity;
import com.example.eventorapplication.TaosukienActivity;
import com.example.eventorapplication.TrangthongbaoActivity;
import com.example.eventorapplication.R;
import com.example.eventorapplication.utils.DataManager;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB binding;
    private View footerView;
    private int lastScrollY = 0;
    private boolean isFooterVisible = true;
    protected DataManager dataManager = DataManager.getInstance();
    private long lastClickTime = 0;
    private static final long DOUBLE_CLICK_TIME_DELTA = 300; // 300ms

    protected abstract VB inflateBinding();
    protected abstract String getActiveFooterId();
    // Bổ sung abstract method để các Activity con override scroll lên đầu trang
    protected abstract void scrollToTopIfNeeded(String footerId);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflateBinding();
        setContentView(binding.getRoot());
        setupFooterNavigation();
        highlightActiveFooter(getActiveFooterId());
    }

    private void setupFooterNavigation() {
        View homepage = binding.getRoot().findViewById(R.id.Homepage);
        View taosukien = binding.getRoot().findViewById(R.id.taosukien);
        View thongbao = binding.getRoot().findViewById(R.id.thongbao);
        View sukiencuatoi = binding.getRoot().findViewById(R.id.Sukiencuatoi);
        View taikhoan = binding.getRoot().findViewById(R.id.taikhoan);

        if (homepage != null) {
            homepage.setOnClickListener(v -> {
                long clickTime = System.currentTimeMillis();
                if (this instanceof com.example.eventorapplication.TrangchuActivity) {
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                        // Double click - reload data
                        dataManager.removeData(DataManager.KEY_OUTSTANDING_EVENTS);
                        dataManager.removeData(DataManager.KEY_TRENDING_EVENTS);
                        dataManager.removeData(DataManager.KEY_FOR_YOU_EVENTS);
                        Intent intent = new Intent(this, com.example.eventorapplication.TrangchuActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        intent.putExtra("force_reload", true);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                    } else {
                        // Single click - scroll to top
                        scrollToTopIfNeeded("Homepage");
                    }
                } else {
                    // Chuyển trang
                    startActivity(new Intent(this, com.example.eventorapplication.TrangchuActivity.class));
                    finish();
                }
                lastClickTime = clickTime;
            });
        }

        if (taosukien != null) {
            taosukien.setOnClickListener(v -> {
                long clickTime = System.currentTimeMillis();
                if (this instanceof com.example.eventorapplication.TaosukienActivity) {
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                        Intent intent = new Intent(this, com.example.eventorapplication.TaosukienActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        scrollToTopIfNeeded("taosukien");
                    }
                } else {
                    startActivity(new Intent(this, com.example.eventorapplication.TaosukienActivity.class));
                    finish();
                }
                lastClickTime = clickTime;
            });
        }

        if (thongbao != null) {
            thongbao.setOnClickListener(v -> {
                long clickTime = System.currentTimeMillis();
                if (this instanceof com.example.eventorapplication.TrangthongbaoActivity) {
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                        dataManager.removeData(DataManager.KEY_NOTIFICATIONS);
                        Intent intent = new Intent(this, com.example.eventorapplication.TrangthongbaoActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        scrollToTopIfNeeded("thongbao");
                    }
                } else {
                    startActivity(new Intent(this, com.example.eventorapplication.TrangthongbaoActivity.class));
                    finish();
                }
                lastClickTime = clickTime;
            });
        }

        if (sukiencuatoi != null) {
            sukiencuatoi.setOnClickListener(v -> {
                long clickTime = System.currentTimeMillis();
                if (this instanceof com.example.eventorapplication.SukiencuatoiActivity) {
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                        dataManager.removeData(DataManager.KEY_MY_EVENTS);
                        Intent intent = new Intent(this, com.example.eventorapplication.SukiencuatoiActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        scrollToTopIfNeeded("Sukiencuatoi");
                    }
                } else {
                    startActivity(new Intent(this, com.example.eventorapplication.SukiencuatoiActivity.class));
                    finish();
                }
                lastClickTime = clickTime;
            });
        }
        
        if(taikhoan != null) {
            taikhoan.setOnClickListener(v -> {
                long clickTime = System.currentTimeMillis();
                if (this instanceof com.example.eventorapplication.TkdadangnhapActivity) {
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                        Intent intent = new Intent(this, com.example.eventorapplication.TkdadangnhapActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        scrollToTopIfNeeded("taikhoan");
                    }
                } else {
                    startActivity(new Intent(this, com.example.eventorapplication.TkdadangnhapActivity.class));
                    finish();
                }
                lastClickTime = clickTime;
            });
        }
    }

    // BaseActivity.java
    protected void highlightActiveFooter(String activePageId) {
        int[] footerIds = {
                R.id.Homepage, R.id.taosukien, R.id.thongbao, R.id.Sukiencuatoi, R.id.taikhoan
        };
        int[] iconIds = {
                R.id.icon_home, R.id.icon_taosukien, R.id.icon_thongbao, R.id.icon_sukiencuatoi, R.id.icon_taikhoan
        };
        int[] iconNormal = {
                R.drawable.ic_homepage, R.drawable.ftadd, R.drawable.ic_bell, R.drawable.ic_ticket, R.drawable.ic_account
        };
        int[] iconBlue = {
                R.drawable.ic_homepage_blue, R.drawable.ic_add_blue, R.drawable.ic_bell_blue, R.drawable.ic_ticket_blue, R.drawable.ic_account_blue
        };

        for (int i = 0; i < footerIds.length; i++) {
            View footer = binding.getRoot().findViewById(footerIds[i]);
            ImageView icon = binding.getRoot().findViewById(iconIds[i]);
            if (footer != null && icon != null) {
                String currentFooterId = getResources().getResourceEntryName(footerIds[i]);
                if (currentFooterId.equals(activePageId)) {
                    footer.setBackgroundResource(R.drawable.bg_footeritemselected);
                    icon.setImageResource(iconBlue[i]);
                } else {
                    footer.setBackgroundResource(0);
                    icon.setImageResource(iconNormal[i]);
                }
            }
        }
    }
}
