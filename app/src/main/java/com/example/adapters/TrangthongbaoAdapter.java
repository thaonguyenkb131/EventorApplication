package com.example.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.eventorapplication.databinding.ItemLvthongbaoBinding;
import com.example.models.TrangthongbaoItem;

import java.util.ArrayList;

public class TrangthongbaoAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<TrangthongbaoItem> tbao;

    public TrangthongbaoAdapter(Activity activity, ArrayList<TrangthongbaoItem> tbao) {
        this.activity = activity;
        this.tbao = tbao;
    }

    @Override
    public int getCount() {
        return tbao.size();
    }

    @Override
    public Object getItem(int position) {
        return tbao.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemLvthongbaoBinding binding;

        if (convertView == null) {
            binding = ItemLvthongbaoBinding.inflate(activity.getLayoutInflater(), parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemLvthongbaoBinding) convertView.getTag();
        }

        TrangthongbaoItem item = tbao.get(position);

        // Gán dữ liệu từ item vào layout
        binding.imgThumb.setImageResource(item.getImgThumb());
        binding.txtTieude.setText(item.getTxtTieude());
        binding.txtThoigian.setText(item.getTxtThoigian());
        binding.txtNoidung.setText(item.getTxtNoidung());

        return convertView;
    }
}
