package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.adapters.ThesukienAdapter;
import com.example.eventorapplication.databinding.FragmentSukiendaluuBinding;
import com.example.models.Thesukien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

        List<Thesukien> items = new ArrayList<>();
        ThesukienAdapter adapter = new ThesukienAdapter(requireContext(), items);
        binding.gvSkdl.setAdapter(adapter);

        binding.progressBar.setVisibility(View.VISIBLE);
        // Đọc dữ liệu từ Realtime Database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("savedevents");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Thesukien item = snapshot.getValue(Thesukien.class);
                    items.add(item);
                    android.util.Log.d("FIREBASE_EVENT", "Loaded: " + item.getTitle());
                }
                adapter.notifyDataSetChanged();
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                android.util.Log.e("FIREBASE_EVENT", "Error: ", error.toException());
                binding.progressBar.setVisibility(View.GONE);
            }
        });

        binding.gvSkdl.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Thesukien item = (Thesukien) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), ChitietsukienActivity.class);
                intent.putExtra("event_id", item.getId());
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