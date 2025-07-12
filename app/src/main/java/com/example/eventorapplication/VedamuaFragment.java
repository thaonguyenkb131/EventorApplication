package com.example.eventorapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.adapters.ThesukienAdapter;
import com.example.eventorapplication.databinding.FragmentVedamuaBinding;
import com.example.eventorapplication.utils.DataManager;
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
 * Use the {@link VedamuaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VedamuaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentVedamuaBinding binding;
    private DataManager dataManager = DataManager.getInstance();

    public VedamuaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VedamuaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VedamuaFragment newInstance(String param1, String param2) {
        VedamuaFragment fragment = new VedamuaFragment();
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

        binding = FragmentVedamuaBinding.inflate(inflater, container, false);

        List<Thesukien> items = new ArrayList<>();
        ThesukienAdapter adapter = new ThesukienAdapter(requireContext(), items);
        binding.gvVdm.setAdapter(adapter);

        // Kiểm tra cache trước khi load
        if (dataManager.getData(DataManager.KEY_MY_TICKETS) == null) {
            loadMyTickets(items, adapter);
        } else {
            displayCachedMyTickets(items, adapter);
        }

        binding.gvVdm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Thesukien selectedItem = items.get(position);
                Intent intent = new Intent(getActivity(), ChitietvedamuaActivity.class);
                intent.putExtra("thesukien", selectedItem);
                startActivity(intent);
            }
        });

        return binding.getRoot();
    }
    
    private void loadMyTickets(List<Thesukien> items, ThesukienAdapter adapter) {
        binding.progressBar.setVisibility(View.VISIBLE);
        // Đọc dữ liệu từ Realtime Database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("mytickets");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Thesukien> ticketsList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Thesukien item = snapshot.getValue(Thesukien.class);
                    if (item != null) {
                        ticketsList.add(item);
                        android.util.Log.d("FIREBASE_EVENT", "Loaded: " + item.getTitle());
                    }
                }
                
                // Lưu vào cache
                dataManager.putData(DataManager.KEY_MY_TICKETS, ticketsList);
                
                displayMyTickets(items, adapter, ticketsList);
                binding.progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                android.util.Log.e("FIREBASE_EVENT", "Error: ", error.toException());
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
    
    private void displayCachedMyTickets(List<Thesukien> items, ThesukienAdapter adapter) {
        ArrayList<Thesukien> ticketsList = (ArrayList<Thesukien>) dataManager.getData(DataManager.KEY_MY_TICKETS);
        if (ticketsList != null) {
            displayMyTickets(items, adapter, ticketsList);
            binding.progressBar.setVisibility(View.GONE);
        }
    }
    
    private void displayMyTickets(List<Thesukien> items, ThesukienAdapter adapter, ArrayList<Thesukien> ticketsList) {
        items.clear();
        items.addAll(ticketsList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // tránh memory leak
    }
}