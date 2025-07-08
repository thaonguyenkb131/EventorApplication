package com.example.eventorapplication;

import static android.widget.Toast.makeText;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Patterns;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.models.UserModel;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;


import com.example.eventorapplication.databinding.ActivityDangkyBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DangkyActivity extends AppCompatActivity {

    ActivityDangkyBinding binding;

    private DatabaseReference accountRef;

    private EditText edtLastname, edtName, edtEmail, edtPhone, edtPassword, edtRePassword;
    private Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangkyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        accountRef = FirebaseDatabase.getInstance().getReference("accounts");

        //        Tránh che màn hình

        View rootView = findViewById(R.id.main); // ConstraintLayout có id="main"
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom); // tránh bị che nút

            // Đẩy TextView xuống dưới status bar
            View txtTitle = findViewById(R.id.header);
            if(txtTitle != null) {
                txtTitle.setPadding(
                        txtTitle.getPaddingLeft(),
                        systemBars.top,
                        txtTitle.getPaddingRight(),
                        txtTitle.getPaddingBottom()
                );
            }

            return insets;
        });

        // Thiết lập tiêu đề và nút quay lại
        TextView txtHeaderTitle = findViewById(R.id.txtTitle);
        ImageView btnBack = findViewById(R.id.btnBack);

        txtHeaderTitle.setText("Đăng ký tài khoản");
        btnBack.setOnClickListener(v -> finish());


//        Xử lý nút

        binding.txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangkyActivity.this, DangnhapActivity.class);
                startActivity(intent);
            }
        });

//        Xử lý nút đăng ký
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String lastname = binding.edtLastName.getText().toString().trim();
                String name = binding.edtName.getText().toString().trim();
                String email = binding.edtEmail.getText().toString().trim();
                String phone = binding.edtPhone.getText().toString().trim();
                String password = binding.edtPassword.getText().toString().trim();
                String rePassword = binding.edtRePassword.getText().toString().trim();

                // Ẩn lỗi cũ trước khi kiểm tra
                binding.txtErrorEmail.setVisibility(View.GONE);
                binding.txtErrorPhone.setVisibility(View.GONE);
                binding.txtErrorPassword.setVisibility(View.GONE);

// Kiểm tra rỗng
                if (lastname.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(DangkyActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

// Kiểm tra email
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.txtErrorEmail.setText("Email không hợp lệ");
                    binding.txtErrorEmail.setVisibility(View.VISIBLE);
                    return;
                }

// Kiểm tra số điện thoại
                if (!phone.isEmpty() && !phone.matches("0[0-9]{9}")) {
                    binding.txtErrorPhone.setText("Số điện thoại không hợp lệ");
                    binding.txtErrorPhone.setVisibility(View.VISIBLE);
                    return;
                }

    // Kiểm tra mật khẩu
                if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")) {
                    binding.txtErrorPassword.setText("Mật khẩu phải từ 8 ký tự, có chữ và số, không ký tự đặc biệt");
                    binding.txtErrorPassword.setVisibility(View.VISIBLE);
                    return;
                }

    // Kiểm tra nhập lại mật khẩu
                if (!password.equals(rePassword)) {
                    binding.txtErrorPassword.setText("Mật khẩu không khớp");
                    binding.txtErrorPassword.setVisibility(View.VISIBLE);
                    return;
                }

                // Kiểm tra trùng email trong Firebase
                accountRef.orderByChild("Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            showEmailExistsDialog();
                            return;
                        } else {
                            String Id = accountRef.push().getKey();
                            UserModel user = new UserModel(lastname, name, email, phone, password);
                            showDialogOtp(user, Id);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
        });

        binding.edtEmail.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.txtErrorEmail.setVisibility(View.GONE);
            }
        });

        binding.edtPhone.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.txtErrorPhone.setVisibility(View.GONE);
            }
        });

        binding.edtPassword.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.txtErrorPassword.setVisibility(View.GONE);
            }
        });

        binding.edtRePassword.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.txtErrorPassword.setVisibility(View.GONE);
            }
        });

    }

    private void showEmailExistsDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_emailtontai, null);
        Dialog dialog = new Dialog(this);

        dialog.setContentView(dialogView);
        dialog.setCancelable(false); // Không tắt khi bấm ngoài
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(
                (int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        Button btnBack = dialogView.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private void showDialogLinhVucQuanTam(String id) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_linhvucquantam, null);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(dialogView); // đặt contentView trước
        dialog.setCancelable(false);

// Sau đó mới thao tác với getWindow
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().setLayout(
                    (int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        dialog.show();

        Map<String, String> selectedInterestMap = new HashMap<>();

        FlexboxLayout tagContainer = dialogView.findViewById(R.id.tagContainer);

        for (int i = 0; i < tagContainer.getChildCount(); i++) {
            View tagView = tagContainer.getChildAt(i);
            if (tagView instanceof TextView) {
                TextView textView = (TextView) tagView;

                textView.setOnClickListener(v -> {
                    String tagKey = textView.getTag().toString();      // ví dụ: "Môi trường"
                    String tagValue = textView.getText().toString();   // ví dụ: "♻️ Môi trường"

                    if (selectedInterestMap.containsKey(tagKey)) {
                        selectedInterestMap.remove(tagKey);
                        textView.setBackgroundResource(R.drawable.bg_tag);
                        textView.setTextColor(Color.BLACK);
                    } else {
                        selectedInterestMap.put(tagKey, tagValue);
                        textView.setBackgroundResource(R.drawable.bg_tagselected);
                        textView.setTextColor(Color.WHITE);
                    }
                });
            }
        }

        Button btnSave = dialogView.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            if (selectedInterestMap.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một lĩnh vực", Toast.LENGTH_SHORT).show();
                return;
            }

            accountRef.child(id).child("Interests").setValue(selectedInterestMap)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Đã lưu lĩnh vực quan tâm", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            startActivity(new Intent(this, TrangchuActivity.class));
                            finish();
                        }
                    });
        });
    }

    public abstract class SimpleTextWatcher implements android.text.TextWatcher {
        @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override public void afterTextChanged(android.text.Editable s) {}
    }

    private void showDialogOtp(UserModel user, String userId) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_otp, null);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(
                (int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        EditText[] otpInputs = new EditText[6];
        otpInputs[0] = dialogView.findViewById(R.id.otp1);
        otpInputs[1] = dialogView.findViewById(R.id.otp2);
        otpInputs[2] = dialogView.findViewById(R.id.otp3);
        otpInputs[3] = dialogView.findViewById(R.id.otp4);
        otpInputs[4] = dialogView.findViewById(R.id.otp5);
        otpInputs[5] = dialogView.findViewById(R.id.otp6);

        TextView txtErrorOtp = dialogView.findViewById(R.id.txtErrorOtp);
        Button btnConfirm = dialogView.findViewById(R.id.btnConfirmOtp);

        // Tự động chuyển focus
        for (int i = 0; i < otpInputs.length - 1; i++) {
            final int index = i;
            otpInputs[i].addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1) {
                        otpInputs[index + 1].requestFocus();
                    }
                }
            });
        }

        // Nút xác nhận OTP
        btnConfirm.setOnClickListener(v -> {
            StringBuilder otpBuilder = new StringBuilder();
            for (EditText editText : otpInputs) {
                String digit = editText.getText().toString().trim();
                if (digit.isEmpty()) {
                    txtErrorOtp.setVisibility(View.VISIBLE);
                    return;
                }
                otpBuilder.append(digit);
            }

            // Giả lập xác nhận OTP đúng (chỉ cần nhập đủ 6 số)
            if (otpBuilder.length() == 6) {
                txtErrorOtp.setVisibility(View.GONE);
                dialog.dismiss();

                // Sau khi xác nhận OTP → lưu user vào Firebase
                accountRef.child(userId).setValue(user).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("userLastname", user.getLastname());
                        editor.putString("userName", user.getName());
                        editor.putString("userEmail", user.getEmail());
                        editor.putString("userId", userId);
                        editor.apply();

                        Toast.makeText(this, "Xác nhận OTP thành công", Toast.LENGTH_SHORT).show();
                        showDialogDangKyThanhCong(userId);
                    } else {
                        Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        dialog.show();
    }

    private void showDialogDangKyThanhCong(String userId) {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_dangkythanhcong, null);
        Dialog dialog = new Dialog(this);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(
                (int) (getResources().getDisplayMetrics().widthPixels * 0.9),
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        Button btnNext = dialogView.findViewById(R.id.btnNext);
        btnNext.setOnClickListener(v -> {
            dialog.dismiss();
            showDialogLinhVucQuanTam(userId); // chuyển qua dialog lĩnh vực
        });

        dialog.show();
    }


}