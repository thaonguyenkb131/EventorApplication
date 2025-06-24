package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eventorapplication.R;
import com.example.models.VeDaMua;
import com.example.eventorapplication.TrangChiTietSuKien; // nhớ import class đích

import java.util.List;

public class VeDaMuaAdapter extends BaseAdapter {
    private Context context;
    private List<VeDaMua> list;

    public VeDaMuaAdapter(Context context, List<VeDaMua> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_event_tag, parent, false);
        }

        VeDaMua item = list.get(i);
        ImageView imvThumb = convertView.findViewById(R.id.imvThumb);
        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        TextView tvLocation = convertView.findViewById(R.id.tvLocation);
        TextView tvDate = convertView.findViewById(R.id.tvDate);
        LinearLayout layoutEventTag = convertView.findViewById(R.id.EventTag); // lấy view cần bắt sự kiện

        imvThumb.setImageResource(item.getImage());
        tvTitle.setText(item.getTitle());
        tvPrice.setText(item.getPrice());
        tvLocation.setText(item.getLocation());
        tvDate.setText(item.getDate());

        return convertView;
    }
}
