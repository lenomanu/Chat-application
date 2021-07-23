package com.finix.midnight.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.finix.midnight.R;
import com.finix.midnight.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
    }

    public void setListeners(){
        binding.textSignUp.setOnClickListener(v ->
                startActivity(new Intent(getApplicationContext(),SignInActivity.class)));

    }

}