package com.example.tahuuduc23_duan1_user.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.tahuuduc23_duan1_user.R;
import com.example.tahuuduc23_duan1_user.adapter.DanhSachDonHangPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class OrderFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private DanhSachDonHangPagerAdapter danhSachDonHangPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order, container, false);
        //init(v);
        return v;
    }
}
