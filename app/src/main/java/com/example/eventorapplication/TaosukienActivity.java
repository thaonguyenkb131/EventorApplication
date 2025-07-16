package com.example.eventorapplication;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.adapters.TicketCategoryEditAdapter;
import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityTaosukienBinding;
import com.example.models.Thesukien;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TaosukienActivity extends BaseActivity<ActivityTaosukienBinding> {

    private PopupWindow popupTaianh;
    private PopupWindow popupMaps;
    private static final int REQUEST_CODE_PICK_LOCATION = 1001;
    private static final int REQUEST_CODE_PICK_IMAGE = 2001;
    private Uri selectedImageUri = null;
    private String uploadedImageUrl = "";
    private Thesukien lastCreatedEvent; // Lưu sự kiện vừa tạo
    private List<Thesukien.TicketCategory> ticketCategories;
    private TicketCategoryEditAdapter ticketAdapter;

    @Override
    protected ActivityTaosukienBinding inflateBinding() {
        return ActivityTaosukienBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Đặt màu thanh điều hướng là trắng, icon tối (giống trang chủ, thông báo)
        Window window = getWindow();
        window.setNavigationBarColor(Color.WHITE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final View decor = window.getDecorView();
            int flags = decor.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
            decor.setSystemUiVisibility(flags);
        }

        // Đồng nhất tránh che footer, footer tách biệt với thanh điều hướng
        View rootView = findViewById(R.id.main); // Layout gốc có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, 0); // Không thêm padding cho rootView

            // Đẩy header xuống dưới status bar nếu có
            View txtTitle = findViewById(R.id.Header);
            if (txtTitle != null) {
                txtTitle.setPadding(
                        txtTitle.getPaddingLeft(),
                        systemBars.top,
                        txtTitle.getPaddingRight(),
                        txtTitle.getPaddingBottom()
                );
            }

            // Footer tách biệt với thanh điều hướng
            View footer = findViewById(R.id.footerLayout);
            if (footer != null) {
                footer.setPadding(
                    footer.getPaddingLeft(),
                    footer.getPaddingTop(),
                    footer.getPaddingRight(),
                    systemBars.bottom
                );
            }

            return insets;
        });

        // Kiểm tra trạng thái đăng nhập
        // Kiểm tra đăng nhập
        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String userId = prefs.getString("userId", null);

        if (userId == null) {
            binding.txtDangNhap.setVisibility(View.VISIBLE);
            binding.scrollView.setVisibility(View.GONE); // Ẩn toàn bộ nội dung chính
            binding.txtDangNhap.setPaintFlags(binding.txtDangNhap.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            binding.txtDangNhap.setOnClickListener(v -> {
                Intent intent = new Intent(this, DangnhapActivity.class);
                startActivity(intent);
            });
            return;
        } else {
            binding.txtDangNhap.setVisibility(View.GONE);
            binding.scrollView.setVisibility(View.VISIBLE); // Hiện nội dung chính nếu đã đăng nhập
        }

        binding.edtSearch.setFocusable(false);
        binding.edtSearch.setClickable(true);
        binding.edtSearch.setShowSoftInputOnFocus(false);

        // Chỉ cho phép chọn địa điểm khi chọn Offline
        binding.edtSearch.setOnClickListener(view -> {
            if (binding.rbOffline.isChecked()) {
                FragmentManager fm = getSupportFragmentManager();
                MapDialogFragment dialog = MapDialogFragment.newInstance();
                dialog.setOnPlaceSelectedListener(address -> binding.edtSearch.setText(address));
                dialog.show(fm, "map_dialog");
            }
        });

        binding.btnDangkyBTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaosukienActivity.this, DangkybtcActivity.class);
                startActivity(intent);
            }
        });

        binding.btnDoiAnh.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        });

        // Khởi tạo Spinner cho danh mục sự kiện
        Spinner spinnerCategory = findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.event_categories, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        // Khởi tạo danh sách hạng vé
        ticketCategories = new ArrayList<>();
        
        // Khởi tạo RecyclerView cho hạng vé
        RecyclerView rcvTicketCategories = findViewById(R.id.rcvTicketCategories);
        rcvTicketCategories.setLayoutManager(new LinearLayoutManager(this));
        ticketAdapter = new TicketCategoryEditAdapter(ticketCategories, position -> {
            // Xóa hạng vé khi nhấn nút xóa
            ticketCategories.remove(position);
            ticketAdapter.updateData(ticketCategories);
        });
        rcvTicketCategories.setAdapter(ticketAdapter);

        // Xử lý nút thêm hạng vé
        binding.btnThemHangVe.setOnClickListener(v -> showAddTicketDialog());

        binding.btnDangSukien.setOnClickListener(view -> {
            boolean isValid = true;
            // Reset all error messages
            binding.errorTenSukien.setVisibility(View.GONE);
            binding.errorOpen.setVisibility(View.GONE);
            binding.errorGiobatdau.setVisibility(View.GONE);
            binding.errorStart.setVisibility(View.GONE);
            binding.errorGioketthuc.setVisibility(View.GONE);
            binding.errorEnd.setVisibility(View.GONE);
            binding.errorCategory.setVisibility(View.GONE);
            binding.errorLocation.setVisibility(View.GONE);
            binding.errorLinkSukien.setVisibility(View.GONE);
            binding.errorMoTa.setVisibility(View.GONE);

            // Lấy dữ liệu từ các trường nhập
            String title = binding.edtTenSukien.getText().toString().trim();
            String registrationOpen = binding.edtOpen.getText().toString().trim();
            String timeStart = binding.edtGiobatdau.getText().toString().trim();
            String dateStart = binding.edtStart.getText().toString().trim();
            String timeEnd = binding.edtGioketthuc.getText().toString().trim();
            String dateEnd = binding.edtEnd.getText().toString().trim();
            String description = binding.edtMoTa.getText().toString().trim();
            String thumbnail = uploadedImageUrl;
            String location = "";
            String mapUrl = "";
            String mode = "";
            String category = ((Spinner) findViewById(R.id.spinnerCategory)).getSelectedItem().toString();
            boolean isOnline = binding.rbOnline.isChecked();
            boolean isOffline = binding.rbOffline.isChecked();
            String linkMap = binding.edtlinksukien.getText().toString().trim();
            if (isOnline) {
                mode = "Online";
                location = "Online";
            } else if (isOffline) {
                mode = "Offline";
                location = binding.edtSearch.getText().toString().trim();
            }

            // Validate fields
            if (title.isEmpty()) {
                binding.errorTenSukien.setText("Vui lòng nhập tên sự kiện");
                binding.errorTenSukien.setVisibility(View.VISIBLE);
                isValid = false;
            }
            if (registrationOpen.isEmpty()) {
                binding.errorOpen.setText("Vui lòng nhập thời gian mở đơn");
                binding.errorOpen.setVisibility(View.VISIBLE);
                isValid = false;
            }
            if (timeStart.isEmpty()) {
                binding.errorGiobatdau.setText("Vui lòng nhập giờ bắt đầu");
                binding.errorGiobatdau.setVisibility(View.VISIBLE);
                isValid = false;
            } else if (!timeStart.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
                binding.errorGiobatdau.setText("Định dạng giờ bắt đầu không hợp lệ (hh:mm)");
                binding.errorGiobatdau.setVisibility(View.VISIBLE);
                isValid = false;
            }
            if (dateStart.isEmpty()) {
                binding.errorStart.setText("Vui lòng nhập ngày bắt đầu");
                binding.errorStart.setVisibility(View.VISIBLE);
                isValid = false;
            } else if (!dateStart.matches("^\\d{1,2}/\\d{1,2}/\\d{4}$")) {
                binding.errorStart.setText("Định dạng ngày bắt đầu không hợp lệ (dd/MM/yyyy)");
                binding.errorStart.setVisibility(View.VISIBLE);
                isValid = false;
            }
            if (timeEnd.isEmpty()) {
                binding.errorGioketthuc.setText("Vui lòng nhập giờ kết thúc");
                binding.errorGioketthuc.setVisibility(View.VISIBLE);
                isValid = false;
            } else if (!timeEnd.matches("^([01]?\\d|2[0-3]):[0-5]\\d$")) {
                binding.errorGioketthuc.setText("Định dạng giờ kết thúc không hợp lệ (hh:mm)");
                binding.errorGioketthuc.setVisibility(View.VISIBLE);
                isValid = false;
            }
            if (dateEnd.isEmpty()) {
                binding.errorEnd.setText("Vui lòng nhập ngày kết thúc");
                binding.errorEnd.setVisibility(View.VISIBLE);
                isValid = false;
            } else if (!dateEnd.matches("^\\d{1,2}/\\d{1,2}/\\d{4}$")) {
                binding.errorEnd.setText("Định dạng ngày kết thúc không hợp lệ (dd/MM/yyyy)");
                binding.errorEnd.setVisibility(View.VISIBLE);
                isValid = false;
            }
            if (category.isEmpty() || category.equals("Chọn danh mục")) {
                binding.errorCategory.setText("Vui lòng chọn danh mục sự kiện");
                binding.errorCategory.setVisibility(View.VISIBLE);
                isValid = false;
            }
            if (isOffline && location.isEmpty()) {
                binding.errorLocation.setText("Vui lòng chọn địa điểm tổ chức");
                binding.errorLocation.setVisibility(View.VISIBLE);
                isValid = false;
            }
            if (isOnline && location.isEmpty()) {
                binding.errorLinkSukien.setText("Vui lòng nhập liên kết tham gia sự kiện");
                binding.errorLinkSukien.setVisibility(View.VISIBLE);
                isValid = false;
            }
            if (description.isEmpty()) {
                binding.errorMoTa.setText("Vui lòng nhập mô tả sự kiện");
                binding.errorMoTa.setVisibility(View.VISIBLE);
                isValid = false;
            }

            if (!isValid) return;

            // Lấy thông tin người dùng đang đăng nhập để set làm organizer
            SharedPreferences userPrefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
            String userName = userPrefs.getString("userName", "");
            String userLastname = userPrefs.getString("userLastname", "");
            String organizerName = (userLastname + " " + userName).trim();
            
            // Tạo đối tượng Thesukien
            Thesukien event = new Thesukien();
            event.setTitle(title);
            event.setRegistrationOpen(registrationOpen);
            event.setTimeStart(timeStart);
            event.setDateStart(dateStart);
            event.setTimeEnd(timeEnd);
            event.setDateEnd(dateEnd);
            event.setDescription(description);
            event.setThumbnail(thumbnail);
            event.setLocation(location);
            event.setMapUrl(mapUrl);
            event.setMode(mode);
            event.setCategory(category);
            event.setSoldTicket(0);
            event.setRemainingTicket(0);
            event.setOrganizer(organizerName); // Tự động set organizer là tên người dùng đang đăng nhập
            // Tạo detailtime
            String detailtime;
            if (dateStart.equals(dateEnd)) {
                detailtime = timeStart + "-" + timeEnd + ", " + dateEnd;
            } else {
                detailtime = timeStart + ", " + dateStart + " - " + timeEnd + ", " + dateEnd;
            }
            event.setDetailTime(detailtime);
            // Lưu thuộc tính format (Online/Offline)
            event.setForm(mode);
            event.setLinkMap(linkMap);
            if (mode.equals("Online")) {
                event.setCity("Online");
                event.setDetailAddress("Online");
                event.setLocation("Online");
            }
            // Thêm danh sách hạng vé vào sự kiện
            event.setTicketCategories(ticketCategories);
            
            // TODO: set thêm các trường khác nếu cần
            lastCreatedEvent = event; // Lưu lại sự kiện vừa tạo
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("postedevents");
            String eventId = ref.push().getKey();
            ref.child(eventId).setValue(event).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    showPopupTaoskthanhcong();
                } else {
                    Toast.makeText(this, "Đăng sự kiện thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.rbOnline.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.edtOffline.setVisibility(View.GONE);
                binding.edtOnline.setVisibility(View.VISIBLE);
            }
        });

        binding.rbOffline.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                binding.edtOffline.setVisibility(View.VISIBLE);
                binding.edtOnline.setVisibility(View.GONE);
            }
        });

        // Calendar icon for thời gian mở đơn
        binding.imgCalendarmodon.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    R.style.MyDatePickerDialogTheme,
                    (view1, year, month, dayOfMonth) -> {
                        String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        binding.edtOpen.setText(selectedDate);
                    },
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        // Calendar icon for ngày bắt đầu
        binding.imgCalendarStart.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    R.style.MyDatePickerDialogTheme,
                    (view1, year, month, dayOfMonth) -> {
                        String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        binding.edtStart.setText(selectedDate);
                    },
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        // Calendar icon for ngày kết thúc
        binding.imgCalendarEnd.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    R.style.MyDatePickerDialogTheme,
                    (view1, year, month, dayOfMonth) -> {
                        String selectedDate = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                        binding.edtEnd.setText(selectedDate);
                    },
                    calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        // Click vào mapevent để đổi ảnh nền
        View mapEvent = findViewById(R.id.mapevent);
        mapEvent.setOnClickListener(v -> {
            mapEvent.setBackgroundResource(R.drawable.anhggmap2);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                binding.imgThumbnail.setImageURI(selectedImageUri);
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference imageRef = storageRef.child("thumbnails/" + System.currentTimeMillis() + ".jpg");
                imageRef.putFile(selectedImageUri)
                    .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        uploadedImageUrl = uri.toString();
                        Toast.makeText(this, "Tải ảnh thành công", Toast.LENGTH_SHORT).show();
                    }))
                    .addOnFailureListener(e -> Toast.makeText(this, "Tải ảnh thất bại", Toast.LENGTH_SHORT).show());
            }
        }
        if (requestCode == REQUEST_CODE_PICK_LOCATION && resultCode == Activity.RESULT_OK && data != null) {
            String address = data.getStringExtra("selected_address");
            if (address != null) {
                binding.edtSearch.setText(address);
                // Change background of mapevent to anhggmap2
                View mapEvent = findViewById(R.id.mapevent);
                mapEvent.setBackgroundResource(R.drawable.anhggmap2);
            }
        }
    }

    private void showPopupTaoskthanhcong() {
        View popupView = LayoutInflater.from(this).inflate(R.layout.dialog_taosukienthanhcong, null);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(popupView);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); // full width
            int padding = (int) getResources().getDisplayMetrics().density * 24; // ~24dp
            window.getDecorView().setPadding(padding, padding, padding, padding); // cách lề đều
            window.setGravity(Gravity.CENTER);
        }
        dialog.getWindow().setGravity(Gravity.CENTER);

        // Bắt sự kiện click nút Tạo sự kiện
        Button btnXemsukien = popupView.findViewById(R.id.btnXemsukien);
        btnXemsukien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang ChitietsukienActivity với dữ liệu sự kiện vừa tạo
                Intent intent = new Intent(TaosukienActivity.this, ChitietsukienActivity.class);
                if (lastCreatedEvent != null) {
                    String eventJson = new Gson().toJson(lastCreatedEvent);
                    intent.putExtra("event_json", eventJson);
                }
                startActivity(intent);
                dialog.dismiss(); // đóng dialog
                resetForm(); // Reset form sau khi tạo sự kiện thành công
            }
        });

        dialog.show();
    }

    // Hàm reset toàn bộ form về mặc định
    private void resetForm() {
        binding.edtTenSukien.setText("");
        binding.edtOpen.setText("");
        binding.edtGiobatdau.setText("");
        binding.edtStart.setText("");
        binding.edtGioketthuc.setText("");
        binding.edtEnd.setText("");
        binding.edtMoTa.setText("");
        binding.edtSearch.setText("");
        binding.edtlinksukien.setText("");
        binding.spinnerCategory.setSelection(0);
        binding.rbOnline.setChecked(false);
        binding.rbOffline.setChecked(false);
        binding.edtOffline.setVisibility(View.GONE);
        binding.edtOnline.setVisibility(View.GONE);
        binding.imgThumbnail.setImageResource(R.drawable.anhthaythe);
        uploadedImageUrl = "";
        selectedImageUri = null;
        // Reset danh sách vé
        ticketCategories.clear();
        ticketAdapter.updateData(ticketCategories);
        // Reset các lỗi
        binding.errorTenSukien.setVisibility(View.GONE);
        binding.errorOpen.setVisibility(View.GONE);
        binding.errorGiobatdau.setVisibility(View.GONE);
        binding.errorStart.setVisibility(View.GONE);
        binding.errorGioketthuc.setVisibility(View.GONE);
        binding.errorEnd.setVisibility(View.GONE);
        binding.errorCategory.setVisibility(View.GONE);
        binding.errorLocation.setVisibility(View.GONE);
        binding.errorLinkSukien.setVisibility(View.GONE);
        binding.errorMoTa.setVisibility(View.GONE);
    }

    private void showPopupMap(View anchorView) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.dialog_map, null);

        popupMaps = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupMaps.setOutsideTouchable(true);
        popupMaps.setFocusable(true);

        popupMaps.showAtLocation(binding.getRoot(), android.view.Gravity.CENTER, 0, 0);
    }

    private void showPopupTaianh(View anchorView) {
        View popupView = LayoutInflater.from(this).inflate(R.layout.dialog_taianhlen, null);

        popupTaianh = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true
        );

        popupTaianh.setOutsideTouchable(true);
        popupTaianh.setFocusable(true);
        popupTaianh.setBackgroundDrawable(getDrawable(android.R.color.transparent));

        popupTaianh.showAsDropDown(anchorView, 0, 16);
    }

    private void showAddTicketDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_ticket_category);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int padding = (int) getResources().getDisplayMetrics().density * 24;
            window.getDecorView().setPadding(padding, padding, padding, padding);
            window.setGravity(Gravity.CENTER);
        }

        EditText edtTicketName = dialog.findViewById(R.id.edtTicketName);
        EditText edtTicketPrice = dialog.findViewById(R.id.edtTicketPrice);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnAdd = dialog.findViewById(R.id.btnAdd);

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnAdd.setOnClickListener(v -> {
            String ticketName = edtTicketName.getText().toString().trim();
            String priceStr = edtTicketPrice.getText().toString().trim();

            if (ticketName.isEmpty()) {
                edtTicketName.setError("Vui lòng nhập tên hạng vé");
                return;
            }

            if (priceStr.isEmpty()) {
                edtTicketPrice.setError("Vui lòng nhập giá vé");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                edtTicketPrice.setError("Giá vé không hợp lệ");
                return;
            }

            // Tạo hạng vé mới
            Thesukien.TicketCategory newTicket = new Thesukien.TicketCategory();
            newTicket.setName(ticketName);
            newTicket.setPrice(price);

            // Thêm vào danh sách
            ticketCategories.add(newTicket);
            ticketAdapter.updateData(ticketCategories);

            dialog.dismiss();
            Toast.makeText(this, "Đã thêm hạng vé: " + ticketName, Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }

    @Override
    protected String getActiveFooterId() {
        return "taosukien";
    }

    @Override
    protected void scrollToTopIfNeeded(String footerId) {
        if ("taosukien".equals(footerId)) {
            android.widget.ScrollView scrollView = findViewById(R.id.scrollView);
            if (scrollView != null) scrollView.smoothScrollTo(0, 0);
        }
    }
}
