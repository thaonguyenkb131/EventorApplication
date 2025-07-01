package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.eventorapplication.R;
import com.example.models.BinhluanItem;

import java.util.List;

public class BinhluanAdapter extends RecyclerView.Adapter<BinhluanAdapter.ViewHolder> {

    private Context context;
    private List<BinhluanItem> binhluanList;

    public BinhluanAdapter(Context context, List<BinhluanItem> binhluanList) {
        this.context = context;
        this.binhluanList = binhluanList;
    }

    public void updateData(List<BinhluanItem> newList) {
        this.binhluanList = newList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return binhluanList != null ? binhluanList.size() : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_binhluan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BinhluanItem review = binhluanList.get(position);
        holder.txtName.setText(review.getName());
        holder.txtComment.setText(review.getComment());
        holder.imgAvatar.setImageResource(review.getAvatarResId());

        // Hiển thị số sao
        holder.lnStar.removeAllViews();
        for (int i = 0; i < 5; i++) {
            ImageView star = new ImageView(context);
            star.setLayoutParams(new LinearLayout.LayoutParams(28, 28));
            if (i > 0) {
                ((LinearLayout.LayoutParams) star.getLayoutParams()).setMarginStart(5);
            }
            star.setImageResource(i < review.getRating() ? R.drawable.ic_star : R.drawable.ic_nostar);
            holder.lnStar.addView(star);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtName, txtComment;
        LinearLayout lnStar;

        public ViewHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtName = itemView.findViewById(R.id.txtName);
            txtComment = itemView.findViewById(R.id.txtComment);
            lnStar = itemView.findViewById(R.id.lnStar);
        }
    }
}
