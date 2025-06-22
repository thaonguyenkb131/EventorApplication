package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eventorapplication.R;
import com.example.models.TimkiemlvItem;

import java.util.List;

public class TimkiemlvAdapter extends BaseAdapter {
    private Context context;
    private List<TimkiemlvItem> items;

    public TimkiemlvAdapter(Context context, List<TimkiemlvItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public TimkiemlvItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView imgLeft, imgRight;
        TextView txtTitle;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_lvtimkiem, parent, false);
            holder = new ViewHolder();
            holder.imgLeft = convertView.findViewById(R.id.imglefticon);
            holder.txtTitle = convertView.findViewById(R.id.txtnoidungsearch);
            holder.imgRight = convertView.findViewById(R.id.imgictrash);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        TimkiemlvItem item = getItem(position);
        holder.imgLeft.setImageResource(item.getLeftIcon());
        holder.txtTitle.setText(item.getTitle());

        if (item.getRightIcon() != null) {
            holder.imgRight.setVisibility(View.VISIBLE);
            holder.imgRight.setImageResource(item.getRightIcon());
        } else {
            holder.imgRight.setVisibility(View.GONE);
        }

        return convertView;
    }

}
