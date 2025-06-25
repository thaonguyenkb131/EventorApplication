package com.example.eventorapplication;

import android.os.Bundle;

import com.example.eventorapplication.base.BaseActivity;
import com.example.eventorapplication.databinding.ActivityChatbotBinding;

public class ChatbotActivity extends BaseActivity<ActivityChatbotBinding> {

    @Override
    protected ActivityChatbotBinding inflateBinding() {
        return ActivityChatbotBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}