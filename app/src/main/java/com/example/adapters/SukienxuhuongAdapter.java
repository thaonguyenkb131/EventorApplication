package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.eventorapplication.R;
import com.example.eventorapplication.databinding.ItemLvsukienxuhuongBinding;

import com.example.models.Thesukien;

import java.util.List;

public class SukienxuhuongAdapter extends BaseAdapter {
    private Context context;
    private List<Thesukien> items;

    public SukienxuhuongAdapter(Context context, List<Thesukien> items) {
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

        Thesukien item = items.get(position);

        // Load poster (thumbnail)
        if (item.getThumbnail() != null && !item.getThumbnail().isEmpty()) {
            Glide.with(context)
                .load(item.getThumbnail())
                .placeholder(R.drawable.skxh1)
                .into(binding.imgPosterSkxh);
        } else {
            binding.imgPosterSkxh.setImageResource(R.drawable.skxh1);
        }

        // Tên sự kiện
        binding.txtTen.setText(item.getTitle());
        // Số vé đã bán
        binding.txtDaban.setText("Số vé đã bán: " + item.getSoldTicket() + " vé");
        // Giá vé (ưu tiên lấy price, nếu có ticketCategories thì lấy giá nhỏ nhất)
        double price = item.getPrice();
        if (item.getTicketCategories() != null && !item.getTicketCategories().isEmpty()) {
            double minPrice = Double.MAX_VALUE;
            for (Thesukien.TicketCategory cat : item.getTicketCategories()) {
                if (cat.getPrice() < minPrice) minPrice = cat.getPrice();
            }
            price = minPrice;
        }
        if (price > 0) {
            binding.txtGia.setText("Từ " + String.format("%,.0f", price) + " VNĐ");
        } else {
            binding.txtGia.setText("Miễn phí");
        }
        // Địa điểm (city nếu có, không thì location)
        if (item.getCity() != null && !item.getCity().isEmpty()) {
            binding.txtDiaDiem.setText(item.getCity());
        } else {
            binding.txtDiaDiem.setText(item.getLocation());
        }
        // Tag TOP
        binding.txtTop.setText("TOP " + (position + 1));
        // Bookmark: click đổi màu nền dưới icon thành xanh dương
        binding.imvBookmark.setOnClickListener(v -> {
            binding.imvBookmark.setImageResource(R.drawable.save2);
        });
        return convertView;
    }
}
