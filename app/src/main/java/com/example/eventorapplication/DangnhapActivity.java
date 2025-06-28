package com.example.eventorapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityDangnhapBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import android.content.SharedPreferences;


public class DangnhapActivity extends AppCompatActivity {

    ActivityDangnhapBinding binding;
    private DatabaseReference accountRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDangnhapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        accountRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("accounts");


        binding.txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangnhapActivity.this, DangkyActivity.class);
                startActivity(intent);
            }
        });

        binding.Googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Mở dialog chọn tài khoản Google
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_chontaikhoan, null);

                // Tạo Dialog
                Dialog dialog = new Dialog(v.getContext());
                dialog.setContentView(dialogView);
                dialog.setCancelable(true);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setGravity(Gravity.BOTTOM);

                // Xử lý nút đăng nhập
                Button btnConfirm = dialogView.findViewById(R.id.btnConfirmLogin);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Xử lý logic đăng nhập tại đây
                        Toast.makeText(v.getContext(), "Đã chọn tài khoản và đăng nhập", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DangnhapActivity.this, TrangchuActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edtEmail.getText().toString().trim();
                String password = binding.edtPassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(DangnhapActivity.this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show();
                    return;
                }

                accountRef.orderByChild("Email").equalTo(email)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    boolean matched = false;

                                    for (DataSnapshot userSnap : snapshot.getChildren()) {
                                        String passInDB = userSnap.child("Password").getValue(String.class);
                                        if (passInDB != null && passInDB.equals(password)) {
                                            matched = true;

                                            String name = userSnap.child("Name").getValue(String.class);
                                            String lastname = userSnap.child("Lastname").getValue(String.class);
                                            String emailFromDB = userSnap.child("Email").getValue(String.class);

                                            SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("userName", name);         // lưu tên
                                            editor.putString("userLastname", lastname); // lưu họ
                                            editor.putString("userEmail", emailFromDB); // lưu email
                                            editor.apply();

                                            Toast.makeText(DangnhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(DangnhapActivity.this, TrangchuActivity.class));
                                            finish();
                                            break;
                                        }
                                    }

                                    if (!matched) {
                                        Toast.makeText(DangnhapActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(DangnhapActivity.this, "Email chưa đăng ký", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(DangnhapActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.txtForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DangnhapActivity.this, QuenmatkhauActivity.class);
                startActivity(intent);
            }
        });
    }
}