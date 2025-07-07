package com.example.eventorapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.eventorapplication.databinding.ActivityCailaimatkhauBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CailaimatkhauActivity extends AppCompatActivity {

    ActivityCailaimatkhauBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCailaimatkhauBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String email = getIntent().getStringExtra("email");
        Log.d("ResetPassword", "Email nhận được: " + email);

        binding.btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = binding.edtNewPassword.getText().toString().trim();
                String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    binding.txtErrorReset.setText("Vui lòng nhập đầy đủ mật khẩu");
                    binding.txtErrorReset.setVisibility(View.VISIBLE);
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    binding.txtErrorReset.setText("Mật khẩu không khớp");
                    binding.txtErrorReset.setVisibility(View.VISIBLE);
                    return;
                }

                binding.txtErrorReset.setVisibility(View.GONE);

                // Cập nhật mật khẩu trong Firebase
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("accounts");
                ref.get().addOnSuccessListener(snapshot -> {
                    boolean found = false;
                    for (DataSnapshot userSnap : snapshot.getChildren()) {
                        String currentEmail = userSnap.child("email").getValue(String.class);
                        if (email.equals(currentEmail)) {
                            // Đúng email → cập nhật mật khẩu
                            userSnap.getRef().child("password").setValue(newPassword)
                                    .addOnSuccessListener(unused -> showSuccessDialog())
                                    .addOnFailureListener(e -> {
                                        binding.txtErrorReset.setText("Lỗi cập nhật mật khẩu");
                                        binding.txtErrorReset.setVisibility(View.VISIBLE);
                                    });
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        binding.txtErrorReset.setText("Không tìm thấy tài khoản");
                        binding.txtErrorReset.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(e -> {
                    Log.e("ResetPassword", "Lỗi get dữ liệu:", e);
                    binding.txtErrorReset.setText("Đã xảy ra lỗi, vui lòng thử lại");
                    binding.txtErrorReset.setVisibility(View.VISIBLE);
                });
            }
        });
    }

    private void showSuccessDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_doimkthanhcong, null);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(dialogView);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.CENTER);

        Button btnConfirm = dialogView.findViewById(R.id.btnGoHome);
        btnConfirm.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(CailaimatkhauActivity.this, DangnhapActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        dialog.show();
    }
}