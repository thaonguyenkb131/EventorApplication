package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.eventorapplication.databinding.ItemLvsukienxuhuongBinding;

import com.example.models.SukienxuhuongItem;

import java.util.List;

public class SukienxuhuongAdapter extends BaseAdapter {
    private Context context;
    private List<SukienxuhuongItem> items;

    public SukienxuhuongAdapter(Context context, List<SukienxuhuongItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemLvsukienxuhuongBinding binding;

        if (convertView == null) {
            binding = ItemLvsukienxuhuongBinding.inflate(LayoutInflater.from(context), parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemLvsukienxuhuongBinding) convertView.getTag();
        }

        SukienxuhuongItem item = items.get(position);

        binding.imgPoster.setImageResource(item.getPosterResId());
        binding.txtTen.setText(item.getTen());
        binding.txtDaban.setText(item.getDaBan());
        binding.txtGia.setText(item.getGia());
        binding.txtDiaDiem.setText(item.getDiaDiem());

        return convertView;
    }


}
