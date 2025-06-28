package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.eventorapplication.databinding.ItemLvtinnhanBinding;
import com.example.models.ListtinnhanItem;

import java.util.ArrayList;

public class ListtinnhanAdapter extends BaseAdapter {
    Context context;
    ArrayList<ListtinnhanItem> messages;

    public ListtinnhanAdapter(Context context, ArrayList<ListtinnhanItem> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ItemLvtinnhanBinding binding;

        ViewHolder(ItemLvtinnhanBinding binding) {
            this.binding = binding;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            ItemLvtinnhanBinding binding = ItemLvtinnhanBinding.inflate(
                    LayoutInflater.from(context), parent, false
            );
            holder = new ViewHolder(binding);
            convertView = binding.getRoot();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ListtinnhanItem item = messages.get(position);

        holder.binding.txtSender.setText(item.getSender());
        holder.binding.txtMessage.setText(item.getMessage());
        holder.binding.txtTime.setText(item.getTime());
        holder.binding.imgAvatar.setImageResource(item.getAvatar());

        return convertView;
    }
}
