package com.example.eventorapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.adapters.TinnhanAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTinnhanBinding;
import com.example.models.TinnhanItem;

import java.util.ArrayList;

public class Tinnhan extends BaseActivity<ActivityTinnhanBinding> {

    private ArrayList<TinnhanItem> messages;
    private TinnhanAdapter adapter;
    private static final int PICK_FILE_REQUEST = 1;

    @Override
    protected ActivityTinnhanBinding inflateBinding() {
        return ActivityTinnhanBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Khởi tạo danh sách tin nhắn mẫu
        messages = new ArrayList<>();
        messages.add(new TinnhanItem("Dạ, em chào BTC ạ", true));
        messages.add(new TinnhanItem("BTC cho em hỏi là với sự kiện Những tp mơ màng thì thời gian soát vé vào cổng là bắt đầu từ lúc mấy giờ ạ?", true));
        messages.add(new TinnhanItem("Chào Nguyên, cảm ơn bạn đã liên hệ với BTC.\nVề thời gian soát vé, sẽ bắt đầu từ lúc 5h sáng, bạn nhớ đến sớm nhất có thể để có được chỗ ngồi ưng ý nha!", false));

        // Gán adapter
        adapter = new TinnhanAdapter(this, messages);
        binding.lvTinnhan.setAdapter(adapter);

        // Long click để mở menu tin nhắn
        adapter.setOnMessageLongClickListener((view, position) -> {
            PopupMenu popup = new PopupMenu(this, view);
            popup.getMenuInflater().inflate(R.menu.message_menu, popup.getMenu());
            popup.show();
        });

        // Gửi tin nhắn
        binding.btnSend.setOnClickListener(view -> {
            String msg = binding.edtMessage.getText().toString().trim();
            if (!msg.isEmpty()) {
                messages.add(new TinnhanItem(msg, true));
                adapter.notifyDataSetChanged();
                binding.edtMessage.setText("");
                binding.lvTinnhan.setSelection(messages.size() - 1);
            }
        });

        // Mở/ẩn emoji
        binding.btnEmoji.setOnClickListener(view -> {
            binding.gvEmoji.setVisibility(
                    binding.gvEmoji.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
        });

        // Emoji đầy đủ
        String[] emojis = {
                "😀", "😃", "😄", "😁", "😆", "😅", "😂", "🤣", "🙂", "🙃",
                "😉", "😌", "😍", "🥰", "😘", "😗", "😙", "😚", "😋", "😛",
                "😜", "🤪", "😝", "🤑", "🤗", "🤭", "🤫", "🤔", "🤐", "😐",
                "😑", "😶", "😶‍🌫️", "🙄", "😏", "😒", "😞", "😔", "😟", "😕",
                "🙁", "☹️", "😣", "😖", "😫", "😩", "🥺", "😢", "😭", "😤",
                "😠", "😡", "🤬", "😱", "😨", "😰", "😥", "😓", "🥶", "🥵",
                "🤯", "😬", "😮‍💨", "😎", "🤓", "🧐", "🤠", "🥳", "🤩", "😇",
                "🤡", "👻", "💀", "👋", "👍", "👎", "👏", "🙌", "🙏", "🤝",
                "💪", "👀", "💬", "❤️", "🧡", "💛", "💚", "💙", "💜", "🖤",
                "🤍", "🤎"
        };
        ArrayAdapter<String> emojiAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emojis);
        binding.gvEmoji.setAdapter(emojiAdapter);
        binding.gvEmoji.setOnItemClickListener((parent, view, position, id) -> {
            binding.edtMessage.append(emojis[position]);
        });

        // Đính kèm tệp hoặc thư mục
        binding.btnAttach.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, binding.btnAttach);
            popup.getMenuInflater().inflate(R.menu.attach_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                if (id == R.id.menu_file) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent, PICK_FILE_REQUEST);
                    return true;
                } else if (id == R.id.menu_folder) {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                    startActivityForResult(intent, PICK_FILE_REQUEST);
                    return true;
                }
                return false;
            });
            popup.show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FILE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            messages.add(new TinnhanItem("📎 Đã chọn: " + uri.getLastPathSegment(), true));
            adapter.notifyDataSetChanged();
        }
    }
}
