package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.eventorapplication.databinding.ItemMapdialogBinding;
import com.example.models.MapItem;

import java.util.List;

public class MapAdapter extends BaseAdapter {
    private Context context;
    private List<MapItem> mapList;

    public MapAdapter(Context context, List<MapItem> mapList) {
        this.context = context;
        this.mapList = mapList;
    }

    @Override
    public int getCount() {
        return mapList.size();
    }

    @Override
    public Object getItem(int position) {
        return mapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            ItemMapdialogBinding binding = ItemMapdialogBinding.inflate(
                    LayoutInflater.from(context), parent, false
            );
            holder = new ViewHolder(binding);
            convertView = binding.getRoot();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MapItem item = mapList.get(position);
        holder.binding.txtTendiadiem.setText(item.getTxtTendiadiem());
        holder.binding.txtDiachi.setText(item.getTxtDiachi());

        return convertView;
    }

    static class ViewHolder {
        ItemMapdialogBinding binding;

        ViewHolder(ItemMapdialogBinding binding) {
            this.binding = binding;
        }
    }
}
