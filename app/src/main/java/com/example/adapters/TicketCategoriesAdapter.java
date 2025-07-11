package com.example.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventorapplication.R;
import com.example.models.Thesukien;

import java.util.List;

public class TicketCategoriesAdapter extends RecyclerView.Adapter<TicketCategoriesAdapter.TicketViewHolder> {
    private List<Thesukien.TicketCategory> ticketCategories;

    public TicketCategoriesAdapter(List<Thesukien.TicketCategory> ticketCategories) {
        this.ticketCategories = ticketCategories;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket_category, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Thesukien.TicketCategory ticket = ticketCategories.get(position);
        holder.txtTicketName.setText(ticket.getName());
        double price = ticket.getPrice();
        if (price == 0) {
            holder.txtTicketPrice.setText("Miễn phí");
            holder.txtTicketPrice.setTextColor(android.graphics.Color.parseColor("#43A047"));
        } else {
            holder.txtTicketPrice.setText(String.format("%,.0f VND", price));
            holder.txtTicketPrice.setTextColor(android.graphics.Color.parseColor("#1C9CCA"));
        }
        // Đan xen màu nền xám trắng cho từng item
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E9E9E9")); // Trắng
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#F5F5F5")); // Xám nhạt
        }
    }

    @Override
    public int getItemCount() {
        return ticketCategories != null ? ticketCategories.size() : 0;
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView txtTicketName, txtTicketPrice;
        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTicketName = itemView.findViewById(R.id.txtTicketName);
            txtTicketPrice = itemView.findViewById(R.id.txtTicketPrice);
        }
    }
}
