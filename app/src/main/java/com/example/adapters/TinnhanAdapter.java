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

    public interface OnMessageLongClickListener {
        void onLongClick(View view, int position);
    }

    private OnMessageLongClickListener longClickListener;

    public void setOnMessageLongClickListener(OnMessageLongClickListener listener) {
        this.longClickListener = listener;
    }

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

        convertView = LayoutInflater.from(context).inflate(
                item.isSender() ? R.layout.item_tinnhan_send : R.layout.item_tinnhan_recv,
                parent, false);

        TextView txtMsg = convertView.findViewById(R.id.txtMessage);
        txtMsg.setText(item.getMessage());

        convertView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onLongClick(v, i);
            }
            return true;
        });

        // Xử lý click vào nút ba chấm
        View btnOpt = convertView.findViewById(R.id.btnOptmessage);
        if (btnOpt != null) {
            btnOpt.setOnClickListener(v -> {
                android.widget.PopupMenu popup = new android.widget.PopupMenu(context, btnOpt);
                popup.getMenu().add("Thu hồi");
                popup.getMenu().add("Xóa");
                popup.getMenu().add("Ghim");

                popup.setOnMenuItemClickListener(itemMenu -> {
                    String title = itemMenu.getTitle().toString();
                    switch (title) {
                        case "Thu hồi":
                            messages.set(i, new TinnhanItem("Tin nhắn đã được thu hồi", true)); // hoặc xóa
                            notifyDataSetChanged();
                            break;
                        case "Xóa":
                            messages.remove(i);
                            notifyDataSetChanged();
                            break;
                        case "Ghim":
                            // Có thể thay đổi màu hoặc vị trí tin nhắn nếu muốn
                            android.widget.Toast.makeText(context, "Đã ghim tin nhắn", android.widget.Toast.LENGTH_SHORT).show();
                            break;
                    }
                    return true;
                });

                popup.show();
            });
        }

        return convertView;
    }

}
