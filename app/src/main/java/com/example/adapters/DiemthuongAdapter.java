package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.eventorapplication.R;
import com.example.models.DiemthuongItem;

import java.util.List;

public class DiemthuongAdapter extends ArrayAdapter<DiemthuongItem> {
    public DiemthuongAdapter(Context context, List<DiemthuongItem> items) {
        super(context, 0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_diemthuong, parent, false);
        }
        DiemthuongItem item = getItem(position);
        ((TextView) convertView.findViewById(R.id.txtName)).setText(item.name);
        ((TextView) convertView.findViewById(R.id.txtDate)).setText(item.date);
        ((TextView) convertView.findViewById(R.id.txtScore)).setText(item.score);
        return convertView;
    }
}
