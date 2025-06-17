package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.eventorapplication.databinding.ItemGvketquatimkiemBinding;
import com.example.models.KetquatimkiemItem;

import java.util.List;

public class KetquatimkiemAdapter extends ArrayAdapter<KetquatimkiemItem> {
    public KetquatimkiemAdapter(Context context, List<KetquatimkiemItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemGvketquatimkiemBinding binding;

        if (convertView == null) {
            binding = ItemGvketquatimkiemBinding.inflate(LayoutInflater.from(getContext()), parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemGvketquatimkiemBinding) convertView.getTag();
        }

        KetquatimkiemItem item = getItem(position);
        if (item != null) {
            binding.imgKqtk.setImageResource(item.getImage());
            binding.txtKqtkTitle.setText(item.getTitle());
            binding.txtKqtkPrice.setText(item.getPrice());
            binding.txtKqtkLocation.setText(item.getLocation());
            binding.txtKqtkDate.setText(item.getDate());
        }
        return convertView;
    }

}
