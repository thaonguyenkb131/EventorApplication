package com.example.eventorapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.example.models.Thesukien;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import android.widget.ImageButton;

public class SoluongveDialogFragment extends DialogFragment {
    public interface OnTicketSelectedListener {
        void onTicketsSelected(List<Thesukien.TicketCategory> selectedTickets, List<Integer> quantities, double totalAmount, double finalTotal);
    }
    private OnTicketSelectedListener listener;
    private List<Thesukien.TicketCategory> ticketCategories;
    private List<Integer> quantities;

    public static SoluongveDialogFragment newInstance(ArrayList<Thesukien.TicketCategory> tickets) {
        SoluongveDialogFragment frag = new SoluongveDialogFragment();
        Bundle args = new Bundle();
        args.putString("tickets_json", new Gson().toJson(tickets));
        frag.setArguments(args);
        return frag;
    }

    public void setOnTicketSelectedListener(OnTicketSelectedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_soluongve, container, false);
        String json = getArguments().getString("tickets_json");
        ticketCategories = new Gson().fromJson(json, new TypeToken<List<Thesukien.TicketCategory>>(){}.getType());
        quantities = new ArrayList<>();
        for (int i = 0; i < ticketCategories.size(); i++) quantities.add(0);

        // Giả sử bạn có 3 dòng vé cứng trong XML
        LinearLayout[] rows = new LinearLayout[] {
            view.findViewById(R.id.row1),
            view.findViewById(R.id.row2),
            view.findViewById(R.id.row3)
        };
        TextView[] names = new TextView[] {
            view.findViewById(R.id.name1),
            view.findViewById(R.id.name2),
            view.findViewById(R.id.name3)
        };
        TextView[] prices = new TextView[] {
            view.findViewById(R.id.price1),
            view.findViewById(R.id.price2),
            view.findViewById(R.id.price3)
        };
        ImageButton[] btnMinus = new ImageButton[] {
            view.findViewById(R.id.btnMinus1),
            view.findViewById(R.id.btnMinus2),
            view.findViewById(R.id.btnMinus3)
        };
        ImageButton[] btnPlus = new ImageButton[] {
            view.findViewById(R.id.btnPlus1),
            view.findViewById(R.id.btnPlus2),
            view.findViewById(R.id.btnPlus3)
        };
        TextView[] txtCount = new TextView[] {
            view.findViewById(R.id.txtCount1),
            view.findViewById(R.id.txtCount2),
            view.findViewById(R.id.txtCount3)
        };
        TextView tvTotalAmount = view.findViewById(R.id.tv_total_amount);
        TextView tvFinalTotal = view.findViewById(R.id.tv_final_total);
        final int voucherDiscount = 50000;

        Runnable updateTotals = () -> {
            double total = 0;
            for (int i = 0; i < ticketCategories.size(); i++) {
                total += ticketCategories.get(i).getPrice() * quantities.get(i);
            }
            tvTotalAmount.setText(String.format("%,.0f VND", total));
            double finalTotal = total - voucherDiscount;
            if (finalTotal < 0) finalTotal = 0;
            tvFinalTotal.setText(String.format("%,.0f VND", finalTotal));
        };
        updateTotals.run();

        for (int i = 0; i < rows.length; i++) {
            if (i < ticketCategories.size()) {
                Thesukien.TicketCategory ticket = ticketCategories.get(i);
                names[i].setText(ticket.getName());
                prices[i].setText(String.format("%,.0f VND", ticket.getPrice()));
                rows[i].setVisibility(View.VISIBLE);
                int idx = i;
                txtCount[i].setText(String.valueOf(quantities.get(idx)));
                btnMinus[i].setOnClickListener(v -> {
                    int val = quantities.get(idx);
                    if (val > 0) {
                        val--;
                        quantities.set(idx, val);
                        txtCount[idx].setText(String.valueOf(val));
                        updateTotals.run();
                    }
                });
                btnPlus[i].setOnClickListener(v -> {
                    int val = quantities.get(idx);
                    val++;
                    quantities.set(idx, val);
                    txtCount[idx].setText(String.valueOf(val));
                    updateTotals.run();
                });
            } else {
                rows[i].setVisibility(View.GONE);
            }
        }

        Button btnXacnhan = view.findViewById(R.id.btnXacnhan);
        btnXacnhan.setOnClickListener(v -> {
            double total = 0;
            for (int i = 0; i < ticketCategories.size(); i++) {
                total += ticketCategories.get(i).getPrice() * quantities.get(i);
            }
            double finalTotal = total - voucherDiscount;
            if (total <= 0) {
                total = 0;
                finalTotal = 0;
            } else if (finalTotal < 0) {
                finalTotal = 0;
            }
            if (listener != null) listener.onTicketsSelected(ticketCategories, quantities, total, finalTotal);
            dismiss();
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            int width = (int) (370 * getResources().getDisplayMetrics().density); // 370dp to px
            getDialog().getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
} 