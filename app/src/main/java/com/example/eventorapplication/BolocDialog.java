package com.example.eventorapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.eventorapplication.databinding.ActivityBolocBinding;

public class BolocDialog extends DialogFragment {
    private ActivityBolocBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = ActivityBolocBinding.inflate(inflater, container, false);

        binding.btnReset.setOnClickListener(v -> {
            dismiss();
        });

        binding.btnApdung.setOnClickListener(v -> {
            dismiss();
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
