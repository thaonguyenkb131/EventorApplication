package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.eventorapplication.databinding.ItemThesukienBinding;
import com.example.models.ThesukienItem;

import java.util.List;

public class ThesukienAdapter extends ArrayAdapter<ThesukienItem> {
    private final LayoutInflater inflater;

    public ThesukienAdapter(@NonNull Context context, @NonNull List<ThesukienItem> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemThesukienBinding binding;

        if (convertView == null) {
            binding = ItemThesukienBinding.inflate(inflater, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemThesukienBinding) convertView.getTag();
        }

        ThesukienItem item = getItem(position);
        if (item != null) {
            binding.imvThumb.setImageResource(item.imageResId);
            binding.tvTitle.setText(item.title);
            binding.tvPrice.setText(item.price);
            binding.tvLocation.setText(item.location);
            binding.tvDate.setText(item.date);
        }

        return convertView;
    }
}
