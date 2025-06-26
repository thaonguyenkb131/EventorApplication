package com.example.eventorapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.adapters.SukiendadangAdapter;
import com.example.eventorapplication.databinding.FragmentSukiendadangBinding;
import com.example.models.SukiendadangItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SukiendadangFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SukiendadangFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentSukiendadangBinding binding;

    public SukiendadangFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SukiendadangFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SukiendadangFragment newInstance(String param1, String param2) {
        SukiendadangFragment fragment = new SukiendadangFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSukiendadangBinding.inflate(inflater, container, false);

        // Khởi tạo danh sách dữ liệu
        List<SukiendadangItem> dsSuKien = new ArrayList<>();
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh1, "SuperFest - Concert Mùa Hè Rực Sáng", "Số vé đã bán: 10,982 vé", "", "Quảng Ninh"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh2, "[River Flows In You] Đêm Nhạc Xương Rồng", "Số vé đã bán: 8,422 vé", "", "Hà Nội"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh3, "Lễ Hội Ẩm Thực Việt - Vị Quê Hương", "Đã bán: 15,000 vé", "", "Hà Nội"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh4, "The “Traditional Water Puppet Show” - Múa rối nước", "Đã bán: 14,500 vé", "", "TP.HCM"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh5, "Hội Chợ Sách & Văn Hóa Đọc", "Đã bán: 14,500 vé", "", "TP.HCM"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh6, "Đại Nhạc Hội Mùa Đông - Winter Beat", "Số vé đã bán: 12,314 vé", "Từ 750.000 VND", "Hà Nội"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh7, "Hội Thảo Công Nghệ - TechNext 2025", "Số vé đã bán: 6,832 vé", "Từ 300.000 VND", "TP. Hồ Chí Minh"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh8, "Festival Ánh Sáng Hội An", "Số vé đã bán: 9,105 vé", "Từ 500.000 VND", "Quảng Nam"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh9, "Hội Chợ Sách & Văn Hóa Đọc", "Số vé đã bán: 5,247 vé", "Miễn phí vào cửa", "Đà Nẵng"));
        dsSuKien.add(new SukiendadangItem(R.drawable.skxh10, "Lễ Hội Ẩm Thực Việt - Vị Quê Hương", "Số vé đã bán: 7,980 vé", "Từ 100.000 VND", "Cần Thơ"));

        // Gắn adapter vào ListView qua binding
        SukiendadangAdapter adapter = new SukiendadangAdapter(requireContext(), new ArrayList<>(dsSuKien));
        binding.lvSkdd.setAdapter(adapter);
        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // tránh memory leak
    }
}