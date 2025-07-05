package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.eventorapplication.databinding.ItemGvtimkiemBinding;
import com.example.models.TimkiemgvItem;

import java.util.List;

public class TimkiemgvAdapter extends BaseAdapter {
    private Context context;
    private List<TimkiemgvItem> items;

    public TimkiemgvAdapter(Context context, List<TimkiemgvItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TimkiemgvItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemGvtimkiemBinding binding;

        if (convertView == null) {
            binding = ItemGvtimkiemBinding.inflate(LayoutInflater.from(context), parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ItemGvtimkiemBinding) convertView.getTag();
        }

        TimkiemgvItem item = getItem(position);

        binding.imgPhvb.setImageResource(item.getImageResId());
        binding.txtPhvbTitle.setText(item.getTitle());
        // Giá
        try {
            double price = Double.parseDouble(item.getPrice().replaceAll("[^0-9.]", ""));
            if (price == 0) {
                binding.txtPhvbPrice.setText("Miễn phí");
                binding.txtPhvbPrice.setTextColor(android.graphics.Color.parseColor("#43A047"));
            } else {
                java.text.NumberFormat nf = java.text.NumberFormat.getInstance(new java.util.Locale("vi", "VN"));
                binding.txtPhvbPrice.setText("Từ " + nf.format(price) + " VND");
                binding.txtPhvbPrice.setTextColor(android.graphics.Color.parseColor("#1C9CCA"));
            }
        } catch (Exception e) {
            binding.txtPhvbPrice.setText(item.getPrice());
            binding.txtPhvbPrice.setTextColor(android.graphics.Color.BLACK);
        }
        binding.txtPhvbLocation.setText(item.getLocation());
        binding.txtPhvbDate.setText(item.getDate());

        return convertView;
    }

}
