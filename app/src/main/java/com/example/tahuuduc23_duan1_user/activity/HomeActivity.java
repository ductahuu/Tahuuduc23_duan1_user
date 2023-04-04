package com.example.tahuuduc23_duan1_user.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tahuuduc23_duan1_user.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        initView();
//        triggerOrderFragment();
//        listenLockAccount();
//        setUpBottomNavigationView();
    }

    public void btnCart(View view) {
    }
}
