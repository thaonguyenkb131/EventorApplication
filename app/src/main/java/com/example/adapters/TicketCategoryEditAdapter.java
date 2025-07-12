package com.example.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventorapplication.R;
import com.example.models.Thesukien;

import java.util.List;

public class TicketCategoryEditAdapter extends RecyclerView.Adapter<TicketCategoryEditAdapter.TicketViewHolder> {
    private List<Thesukien.TicketCategory> ticketCategories;
    private OnTicketDeleteListener deleteListener;

    public interface OnTicketDeleteListener {
        void onTicketDelete(int position);
    }

    public TicketCategoryEditAdapter(List<Thesukien.TicketCategory> ticketCategories, OnTicketDeleteListener deleteListener) {
        this.ticketCategories = ticketCategories;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_category_edit, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Thesukien.TicketCategory ticket = ticketCategories.get(position);
        holder.txtTicketName.setText(ticket.getName());
        double price = ticket.getPrice();
        if (price == 0) {
            holder.txtTicketPrice.setText("Miễn phí");
            holder.txtTicketPrice.setTextColor(Color.parseColor("#43A047"));
        } else {
            holder.txtTicketPrice.setText(String.format("%,.0f VND", price));
            holder.txtTicketPrice.setTextColor(Color.parseColor("#1C9CCA"));
        }
        
        // Đan xen màu nền xám trắng cho từng item
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E9E9E9")); // Trắng
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#F5F5F5")); // Xám nhạt
        }

        holder.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onTicketDelete(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ticketCategories != null ? ticketCategories.size() : 0;
    }

    public void updateData(List<Thesukien.TicketCategory> newData) {
        this.ticketCategories = newData;
        notifyDataSetChanged();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView txtTicketName, txtTicketPrice;
        ImageButton btnDelete;
        
        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTicketName = itemView.findViewById(R.id.txtTicketName);
            txtTicketPrice = itemView.findViewById(R.id.txtTicketPrice);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
} 