package com.example.eventorapplication;

import static android.widget.Toast.makeText;

import android.content.SharedPreferences;
import android.util.Patterns;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.models.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;


import com.example.eventorapplication.databinding.ActivityDangkyBinding;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

                if (lastname.isEmpty() || name.isEmpty() || email.isEmpty() || password.isEmpty() || rePassword.isEmpty()) {
                    Toast.makeText(DangkyActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(rePassword)) {
                    Toast.makeText(DangkyActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(DangkyActivity.this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(rePassword)) {
                    Toast.makeText(DangkyActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

//                Kiểm tra email
                accountRef.orderByChild("Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()) {
                            Toast.makeText(DangkyActivity.this, "Email đã được sử dụng", Toast.LENGTH_SHORT).show();
                        }else {
                            String Id = accountRef.push().getKey();
                            UserModel user = new UserModel(lastname, name, email, phone, password);
                            accountRef.child(Id).setValue(user).addOnCompleteListener(task -> {
                                if(task.isSuccessful()) {
                                    SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("userLastname", lastname);
                                    editor.putString("userName", name);
                                    editor.putString("userEmail", email);
                                    editor.apply();

                                    Toast.makeText(DangkyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(DangkyActivity.this, TrangchuActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(DangkyActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

    }
}