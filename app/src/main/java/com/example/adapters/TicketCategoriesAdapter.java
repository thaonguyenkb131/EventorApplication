package com.example.adapters;

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
        holder.txtTicketPrice.setText(String.format("%,.0f VND", ticket.getPrice()));
        // Đan xen màu nền xám trắng cho từng item
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(0xFFFFFFFF); // Trắng
        } else {
            holder.itemView.setBackgroundColor(0xFFE9E9E9); // Xám nhạt
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
