package com.example.tahuuduc23_duan1_user.activity;

import static com.example.tahuuduc23_duan1_user.activity.FlashActivity.userLogin;
import static com.example.tahuuduc23_duan1_user.ultis.OverUtils.ERROR_MESSAGE;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tahuuduc23_duan1_user.R;
import com.example.tahuuduc23_duan1_user.adapter.DonHangAdapter;
import com.example.tahuuduc23_duan1_user.dao.UserDao;
import com.example.tahuuduc23_duan1_user.interface_.IAfterGetAllObject;
import com.example.tahuuduc23_duan1_user.model.DonHang;
import com.example.tahuuduc23_duan1_user.ultis.OverUtils;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderActivity extends AppCompatActivity {
    private List<DonHang> donHangList;
    private DonHangAdapter donHangAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        getListDonHang();
    }

    private void getListDonHang() {
        donHangList = new ArrayList<>();
        donHangAdapter = new DonHangAdapter(OrderActivity.this,donHangList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(OrderActivity.this,DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(donHangAdapter);
        UserDao.getInstance().getDonHangByUser(userLogin, new IAfterGetAllObject() {
            @Override
            public void iAfterGetAllObject(Object obj) {
                donHangList = (List<DonHang>) obj;
                Collections.reverse(donHangList);
                donHangAdapter.setData(donHangList);
            }

            @Override
            public void onError(DatabaseError error) {
                OverUtils.makeToast(OrderActivity.this,ERROR_MESSAGE);
            }
        });
    }

    private void initView() {
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView = findViewById(R.id.rv_donhang);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
