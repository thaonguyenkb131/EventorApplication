package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.eventorapplication.R;
import com.example.models.TinnhanItem;

import java.util.List;

public class TinnhanAdapter extends BaseAdapter {
    private Context context;
    private List<TinnhanItem> messages;

    public TinnhanAdapter(Context context, List<TinnhanItem> messages) {
        this.context = context;
        this.messages = messages;
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        TinnhanItem item = messages.get(i);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    item.isSender() ? R.layout.item_tinnhan_send : R.layout.item_tinnhan_recv,
                    parent, false);
        }

        TextView txtMsg = convertView.findViewById(R.id.txtMessage);
        txtMsg.setText(item.getMessage());

        return convertView;
    }

}
