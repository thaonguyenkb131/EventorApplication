package com.example.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.eventorapplication.R;
import com.example.models.Thesukien;

import java.util.List;
import java.util.Locale;

public class ThesukienAdapter extends ArrayAdapter<Thesukien> {
    private final LayoutInflater inflater;

    public ThesukienAdapter(@NonNull Context context, @NonNull List<Thesukien> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.item_thesukien, parent, false);
        }

        Thesukien item = getItem(position);
        if (item != null) {
            ImageView imvThumb = view.findViewById(R.id.imvThumb);
            TextView tvTitle = view.findViewById(R.id.tvTitle);
            TextView tvPrice = view.findViewById(R.id.tvPrice);
            TextView tvLocation = view.findViewById(R.id.tvLocation);
            TextView tvDate = view.findViewById(R.id.tvDate);

            tvTitle.setText(item.getTitle());
            tvLocation.setText(item.getLocation());
            tvDate.setText(item.getDate());
            tvPrice.setText(String.format(Locale.getDefault(), "%,.0f VND", item.getPrice()));

            if (item.getThumbnail() != null && item.getThumbnail().startsWith("http")) {
                Glide.with(imvThumb.getContext()).load(item.getThumbnail()).into(imvThumb);
            } else if (item.getThumbnail() != null) {
                int resId = getContext().getResources().getIdentifier(item.getThumbnail(), "drawable", getContext().getPackageName());
                if (resId != 0) imvThumb.setImageResource(resId);
            }

            view.setOnClickListener(v -> {
                android.content.Intent intent = new android.content.Intent(getContext(), com.example.eventorapplication.ChitietsukienActivity.class);
                intent.putExtra("event_id", item.getId());
                getContext().startActivity(intent);
            });
        }
        return view;
    }
}
