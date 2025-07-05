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

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB binding;
    private View footerView;
    private int lastScrollY = 0;
    private boolean isFooterVisible = true;

    protected abstract VB inflateBinding();
    protected abstract String getActiveFooterId();

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
                // Highlight the active footer item
                highlightActiveFooter("Homepage");
                startActivity(new Intent(this, TrangchuActivity.class));
                finish();
            });
        }

        if (taosukien != null) {
            taosukien.setOnClickListener(v -> {
                // Highlight the active footer item
                highlightActiveFooter("taosukien");
                startActivity(new Intent(this, TaosukienActivity.class));
                finish();
            });
        }

        if (thongbao != null) {
            thongbao.setOnClickListener(v -> {
                // Highlight the active footer item
                highlightActiveFooter("thongbao");
                startActivity(new Intent(this, TrangthongbaoActivity.class));
                finish();
            });
        }

        if (sukiencuatoi != null) {
            sukiencuatoi.setOnClickListener(v -> {
                // Highlight the active footer item
                highlightActiveFooter("sukiencuatoi");
                startActivity(new Intent(this, SukiencuatoiActivity.class));
                finish();
            });
        }
        if(taikhoan != null) {
            taikhoan.setOnClickListener(v -> {
                // Highlight the active footer item
                highlightActiveFooter("taikhoan");
                startActivity(new Intent(this, TkdadangnhapActivity.class));
                finish();
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
                if (getResources().getResourceEntryName(footerIds[i]).equals(activePageId)) {
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
