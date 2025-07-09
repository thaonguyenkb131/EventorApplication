package com.example.eventorapplication;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.List;
import com.example.adapters.MapAdapter;
import com.example.models.MapItem;
import android.widget.AdapterView;
import android.content.Intent;
import android.app.Activity;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MapDialogFragment extends DialogFragment {
    public static MapDialogFragment newInstance() {
        return new MapDialogFragment();
    }

    public interface OnPlaceSelectedListener {
        void onPlaceSelected(String address);
    }
    private OnPlaceSelectedListener listener;
    public void setOnPlaceSelectedListener(OnPlaceSelectedListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_map, container, false);

        android.widget.ListView listView = view.findViewById(R.id.listRecentPlaces);
        java.util.ArrayList<MapItem> items = new java.util.ArrayList<>();
        items.add(new MapItem("Trường Đại học Kinh tế Luật, ĐHQG TPHCM", "669 Quốc lộ 1A, phường Linh Xuân, TP Thủ Đức"));
        items.add(new MapItem("Emerald Center", "Trung tâm Emerald, Đường N4, Centre Place, Quận 1"));
        items.add(new MapItem("Vạn Phúc City - Trung tâm Mới", "Quốc lộ 13, khu phố 6, Hiệp Bình Phước, Thành phố Thủ Đức"));
        items.add(new MapItem("Ký túc xá khu B - ĐHQG TPHCM", "Mạc Đỉnh Chi, khu phố Tân Hòa, Dĩ An, Bình Dương"));
        MapAdapter adapter = new MapAdapter(requireContext(), items);
        listView.setAdapter(adapter);

        // Handle item click (nếu muốn trả về địa chỉ, cần callback về Activity)
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            MapItem selected = items.get(position);
            if (listener != null) {
                listener.onPlaceSelected(selected.getTxtTendiadiem() + " - " + selected.getTxtDiachi());
            }
            dismiss();
        });

        // Nút back
        View ivBack = view.findViewById(R.id.ivBack);
        ivBack.setOnClickListener(v -> dismiss());

        // Đảm bảo EditText nhận đúng sự kiện enter
        EditText searchEditText = view.findViewById(R.id.tvHint);
        searchEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                String input = searchEditText.getText().toString().trim();
                if (!input.isEmpty() && listener != null) {
                    listener.onPlaceSelected(input);
                    dismiss();
                }
                return true;
            }
            return false;
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = (int) (requireContext().getResources().getDisplayMetrics().widthPixels * 1.0);
            dialog.getWindow().setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
} 