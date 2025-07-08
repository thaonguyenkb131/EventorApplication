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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventorapplication.databinding.ActivityQuenmatkhauBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuenmatkhauActivity extends AppCompatActivity {

    ActivityQuenmatkhauBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuenmatkhauBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Tránh che màn hình
        View rootView = findViewById(R.id.main);
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(0, 0, 0, systemBars.bottom);

            View txtTitle = findViewById(R.id.header);
            if (txtTitle != null) {
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

        txtHeaderTitle.setText("Quên mật khẩu");
        btnBack.setOnClickListener(v -> finish());

//        Click lấy mã hiện dialog otp
        binding.btnGetCode.setOnClickListener(v -> {
            String email = binding.edtForgotEmail.getText().toString().trim();

            // Kiểm tra rỗng & định dạng
            if (email.isEmpty()) {
                binding.txtErrorEmail.setText("Vui lòng nhập email");
                binding.txtErrorEmail.setVisibility(View.VISIBLE);
                return;
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.txtErrorEmail.setText("Email không hợp lệ");
                binding.txtErrorEmail.setVisibility(View.VISIBLE);
                return;
            } else {
                binding.txtErrorEmail.setVisibility(View.GONE);
            }

            // Kiểm tra trong Firebase xem email có tồn tại không
            DatabaseReference accountRef = FirebaseDatabase.getInstance().getReference("accounts");
            accountRef.orderByChild("email").equalTo(email)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // Email tồn tại → hiển thị dialog nhập OTP
                                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.dialog_otp, null);

                                Dialog dialog = new Dialog(v.getContext());
                                dialog.setContentView(dialogView);
                                dialog.setCancelable(true);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                dialog.getWindow().setGravity(Gravity.CENTER);

                                Button btnConfirm = dialogView.findViewById(R.id.btnConfirmOtp);
                                btnConfirm.setOnClickListener(view -> {
                                    Intent intent = new Intent(QuenmatkhauActivity.this, CailaimatkhauActivity.class);
                                    intent.putExtra("email", email); // truyền email sang
                                    startActivity(intent);
                                    dialog.dismiss();
                                });

                                dialog.show();
                            } else {
                                // Không tìm thấy email
                                binding.txtErrorEmail.setText("Email không tồn tại trong hệ thống");
                                binding.txtErrorEmail.setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(QuenmatkhauActivity.this, "Đã xảy ra lỗi, vui lòng thử lại", Toast.LENGTH_SHORT).show();
                        }
                    });
        });

    }
}