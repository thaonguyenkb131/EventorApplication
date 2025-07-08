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
import com.example.models.Thesukien;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SukiendadangAdapter extends BaseAdapter {

    Context context;
    public ArrayList<Thesukien> list;

    public SukiendadangAdapter(Context context, ArrayList<Thesukien> list) {
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

        Thesukien item = list.get(i);
        // Load ảnh
        if (item.getThumbnail() != null && item.getThumbnail().startsWith("http")) {
            Glide.with(holder.imgPoster.getContext()).load(item.getThumbnail()).into(holder.imgPoster);
        } else if (item.getThumbnail() != null) {
            int resId = context.getResources().getIdentifier(item.getThumbnail(), "drawable", context.getPackageName());
            if (resId != 0) holder.imgPoster.setImageResource(resId);
            else holder.imgPoster.setImageResource(R.drawable.anhthaythe);
        } else {
            holder.imgPoster.setImageResource(R.drawable.anhthaythe);
        }
        holder.txtTen.setText(item.getTitle());
        holder.txtDaban.setText("Đã bán: " + item.getSoldTicket() + " vé");
        holder.txtGia.setText(item.getPrice() == 0 ? "Miễn phí" : String.format("Từ %,.0f VND", item.getPrice()));
        holder.txtDiaDiem.setText(item.getLocation());

        // Thêm sự kiện nhấn vào biểu tượng bookmark để hiển thị popup
        holder.imvBookmark.setOnClickListener(view -> {
            View popupView = LayoutInflater.from(context).inflate(R.layout.dialog_menusukien, null);
            PopupWindow popupWindow = new PopupWindow(popupView,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    true);

            popupWindow.setElevation(20);
            popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.bg_popup_menusk));

            // Lấy vị trí của imvBookmark trên màn hình
            int[] location = new int[2];
            holder.imvBookmark.getLocationOnScreen(location);
            int bookmarkY = location[1];
            int screenHeight = holder.imvBookmark.getResources().getDisplayMetrics().heightPixels;
            popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            int popupHeight = popupView.getMeasuredHeight();

            // Nếu bookmark gần đáy màn hình, show lên trên
            if (bookmarkY + holder.imvBookmark.getHeight() + popupHeight > screenHeight) {
                // Hiển thị phía trên icon
                popupWindow.showAtLocation(holder.imvBookmark, android.view.Gravity.NO_GRAVITY,
                        location[0] - 250, bookmarkY - popupHeight);
            } else {
                // Hiển thị phía dưới icon như bình thường
                popupWindow.showAsDropDown(holder.imvBookmark, -250, 0);
            }
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
