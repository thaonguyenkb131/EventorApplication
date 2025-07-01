package com.example.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.eventorapplication.PhantichbaocaoActivity;
import com.example.eventorapplication.R;
import com.example.models.SukiendadangItem;

import java.util.ArrayList;

public class SukiendadangAdapter extends BaseAdapter {

    Context context;
    ArrayList<SukiendadangItem> list;

    public SukiendadangAdapter(Context context, ArrayList<SukiendadangItem> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_sukiendadang, parent, false);
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

        SukiendadangItem item = list.get(i);
        holder.imgPoster.setImageResource(item.getHinhAnh());
        holder.txtTen.setText(item.getTen());
        holder.txtDaban.setText(item.getDaBan());
        holder.txtGia.setText(item.getGia());
        holder.txtDiaDiem.setText(item.getDiaDiem());

        // Thêm sự kiện nhấn vào biểu tượng bookmark để hiển thị popup
        holder.imvBookmark.setOnClickListener(view -> {
            View popupView = LayoutInflater.from(context).inflate(R.layout.dialog_menusukien, null);
            PopupWindow popupWindow = new PopupWindow(popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);

            popupWindow.setElevation(20);
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_popup_menusk));

            // Hiển thị popup tại vị trí của nút
            popupWindow.showAsDropDown(holder.imvBookmark, -250, 0); // chỉnh vị trí nếu cần

            // Gán sự kiện cho các nút trong popup
            popupView.findViewById(R.id.btnEditsk).setOnClickListener(v -> {
                popupWindow.dismiss();
                // TODO: Xử lý chỉnh sửa
            });

            popupView.findViewById(R.id.btnDeletesk).setOnClickListener(v -> {
                list.remove(i); // Xóa sự kiện khỏi danh sách
                notifyDataSetChanged(); // Cập nhật giao diện
                // Hiển thị thông báo xóa thành công
                Toast.makeText(context, "Đã xóa sự kiện", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();
                // TODO: Xử lý xóa
            });

            popupView.findViewById(R.id.btnViewAtt).setOnClickListener(v -> {
                // TODO: Hiện popup đã tải danh sách ảnh người tham gia
                Toast.makeText(context, "Đã tải danh sách ảnh người tham gia", Toast.LENGTH_SHORT).show();

                popupWindow.dismiss();



            });

            popupView.findViewById(R.id.btnViewRep).setOnClickListener(v -> {
                Intent intent = new Intent(context, PhantichbaocaoActivity.class);
                context.startActivity(intent);
                popupWindow.dismiss();
                // TODO: Xem phân tích báo cáo
            });
        });

        return convertView;
    }
}
