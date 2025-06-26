package com.example.eventorapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.adapters.ThesukienAdapter;
import com.example.eventorapplication.databinding.FragmentSukiendaluuBinding;
import com.example.models.ThesukienItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SukiendaluuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SukiendaluuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentSukiendaluuBinding binding;

    public SukiendaluuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SukiendaluuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SukiendaluuFragment newInstance(String param1, String param2) {
        SukiendaluuFragment fragment = new SukiendaluuFragment();
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
        binding = FragmentSukiendaluuBinding.inflate(inflater, container, false);

        List<ThesukienItem> items = new ArrayList<>();
        items.add(new ThesukienItem(R.drawable.vdmamthuc, "Lễ hội ẩm thực Ấn Độ - Benaras Heritage Royal Indi", "Từ 750.000 VND", "Bình Dương", "10/07/2025"));
        items.add(new ThesukienItem(R.drawable.vdmmotnha, "The East - Live Concert “Hạ” Thành Phố Huế", "Từ 1.000.000 VND", "Hải Phòng", "2/07/2025"));
        items.add(new ThesukienItem(R.drawable.vdmthanhxuan, "Piano : Tiên nữ, giấc mơ và điệu vũ - David Greilsammer", "Từ 350.000 VND", "Bến Tre", "06/07/2025"));
        items.add(new ThesukienItem(R.drawable.vdmtiama, "Madame Show - Những Đường Chim Bay", "Từ 250.000 VND", "Hà Tĩnh", "30/06/2025"));
        items.add(new ThesukienItem(R.drawable.vdmbcn, "Lễ hội âm nhạc, sáng tạo Tràng An - Ninh Bình \"FORESTIVAL\"", "Từ 900.000 VND", "Ninh Bình", "28/06/2025"));
        items.add(new ThesukienItem(R.drawable.vdmvmh, "Future With AI - AI và tương lai doanh nghiệp", "Từ 350.000 VND", "Hà Nội", "24/06/2025"));
        items.add(new ThesukienItem(R.drawable.vdmhappy, "Noo’s Chill Night’s Concert", "Từ 750.000 VND", "Hà Nội", "15/06/2025"));
        items.add(new ThesukienItem(R.drawable.vdmquan, "Lễ hội Thể thao Giải trí hàng đầu tại Việt Nam - GAMA", "Từ 600.000 VND", "Vũng Tàu", "30/06/2025"));
        items.add(new ThesukienItem(R.drawable.vdmforest, "Nhạc kịch “Sấm Vang Dòng Như Nguyệt”", "Từ 500.000 VND", "Hà Tĩnh", "21/06/2025"));
        items.add(new ThesukienItem(R.drawable.vdmhtnau, "HBAshow: Lê Hiếu - Bạch Công Khanh \"Bài Tình Ca Cho Em\"", "Từ 300.000 VND", "TP HCM", "27/06/2025"));

        ThesukienAdapter adapter = new ThesukienAdapter(requireContext(), items);
        binding.gvSkdl.setAdapter(adapter);

        return binding.getRoot();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // tránh memory leak
    }

}