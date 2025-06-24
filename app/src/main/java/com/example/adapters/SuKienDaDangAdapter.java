package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eventorapplication.R;
import com.example.models.SuKienDaDangItem;

import java.util.ArrayList;

public class SuKienDaDangAdapter extends BaseAdapter {

    Context context;
    ArrayList<SuKienDaDangItem> list;

    public SuKienDaDangAdapter(Context context, ArrayList<SuKienDaDangItem> list) {
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

    static class ViewHolder {
        ImageView imgPoster, imvBookmark;
        TextView txtTen, txtDaban, txtGia, txtDiaDiem;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_sukiendadang_item, parent, false);
            holder = new ViewHolder();
            holder.imgPoster = convertView.findViewById(R.id.imgPosterSkxh);
            holder.imvBookmark = convertView.findViewById(R.id.imvBookmark);
            holder.txtTen = convertView.findViewById(R.id.txtTen);
            holder.txtDaban = convertView.findViewById(R.id.txtDaban);
            holder.txtGia = convertView.findViewById(R.id.txtGia);
            holder.txtDiaDiem = convertView.findViewById(R.id.txtDiaDiem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SuKienDaDangItem item = list.get(i);
        holder.imgPoster.setImageResource(item.getHinhAnh());
        holder.txtTen.setText(item.getTen());
        holder.txtDaban.setText(item.getDaBan());
        holder.txtGia.setText(item.getGia());
        holder.txtDiaDiem.setText(item.getDiaDiem());

        return convertView;
    }
}
