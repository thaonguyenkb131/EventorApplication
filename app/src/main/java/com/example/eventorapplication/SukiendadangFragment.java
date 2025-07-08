package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.adapters.SukiendadangAdapter;
import com.example.eventorapplication.databinding.FragmentSukiendadangBinding;
import com.example.models.Thesukien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.bumptech.glide.Glide;

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
    private SukiendadangAdapter adapter;
    private ArrayList<Thesukien> eventList = new ArrayList<>();

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

        adapter = new SukiendadangAdapter(requireContext(), eventList);
        binding.lvSkdd.setAdapter(adapter);

        binding.progressBar.setVisibility(View.VISIBLE);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("postedevents");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                eventList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Thesukien event = child.getValue(Thesukien.class);
                    if (event != null) {
                        eventList.add(event);
                    }
                }
                adapter.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu cần
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        binding.lvSkdd.setOnItemClickListener((parent, view, position, id) -> {
            if (position < eventList.size()) {
                Thesukien selectedEvent = eventList.get(position);
                Intent intent = new Intent(getActivity(), ChitietsukienActivity.class);
                com.google.gson.Gson gson = new com.google.gson.Gson();
                intent.putExtra("event_json", gson.toJson(selectedEvent));
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // tránh memory leak
    }
}