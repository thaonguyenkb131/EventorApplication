package com.example.eventorapplication;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.adapters.ListtinnhanAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityListtinnhanBinding;
import com.example.models.ListtinnhanItem;

import java.util.ArrayList;

public class ListtinnhanActivity extends BaseActivity<ActivityListtinnhanBinding> {

    ListtinnhanAdapter adapter;
    ArrayList<ListtinnhanItem> messages;
    private boolean isNotificationOn = false;

    @Override
    protected ActivityListtinnhanBinding inflateBinding() {
        return binding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Nút bật tắt thông báo
        binding.btnTatThongBao.setOnClickListener(v -> {
            isNotificationOn = !isNotificationOn;

            if (isNotificationOn) {
                binding.btnTatThongBao.setText("Tắt thông báo");
                binding.btnTatThongBao.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#006183")));
                binding.btnTatThongBao.setTextColor(Color.WHITE);
            } else {
                binding.btnTatThongBao.setText("Bật thông báo");
                binding.btnTatThongBao.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#9e9e9e")));
                binding.btnTatThongBao.setTextColor(Color.BLACK);
            }
        });

        loadData();

        binding.lvMessages.setOnItemClickListener((parent, view, position, id) -> {
            // Mở trang chi tiết tin nhắn
            Intent intent = new Intent(ListtinnhanActivity.this, ChitiettinnhanActivity.class);

            startActivity(intent);
        });

    }

    private void loadData() {
        messages = new ArrayList<>();
        messages.add(new ListtinnhanItem(R.drawable.avatar1, "Trần Ân Tú", "Sự kiện này m có tham gia được không? \n Cuối tuần này đi đi, đằng nào cũng hết deadline rồi mà :)))", "10:45am"));
        messages.add(new ListtinnhanItem(R.drawable.avatar2, "Lê Minh Ngọc", "Ê m, sự kiện này hay nè, coi thứ 4 có học gì không kéo cả nhóm đi join đi, hợp với hướng ngành tụi mình lum", "10:30am"));
        messages.add(new ListtinnhanItem(R.drawable.avatar3, "Madame Show - Những đường chim bay", "Chào bạn! Cảm ơn bạn đã liên hệ với chúng mình nha! \n Về thắc mắc của bạn thì chúng mình xin gửi lại thông tin ở trang này nhé ạ!", "10:15am"));
        messages.add(new ListtinnhanItem(R.drawable.avatar4, "GAMA Music Rating Show", "Hiện tại sự kiện đã mở bán vé trên nhiều nền tảng, banjc ó thể theo dõi các trang mạng xã hội khác của chúng mình để cập nhật thông tin mới nhất nhé!", "09:59am"));
        messages.add(new ListtinnhanItem(R.drawable.avatar5, "Lễ hội âm nhạc, sáng tạo Tràng An - Ninh Bình", "Chúng mình rất tiếc khi phải nói là sự kiện lần này đã kết thúc rồi. Bạn có thể để ý thông báo và tham gia những sự kiện sau của chúng mình nhé!", "09:56am"));

        adapter = new ListtinnhanAdapter(this, messages);
        binding.lvMessages.setAdapter(adapter);
    }
}