package com.example.tahuuduc23_duan1_user.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.tahuuduc23_duan1_user.fragment.DanhSachDonHangByTTFragment;
import com.example.tahuuduc23_duan1_user.model.TrangThai;

import java.util.List;

public class DanhSachDonHangPagerAdapter extends FragmentStateAdapter {
    List<TrangThai> trangThaiList;

    public DanhSachDonHangPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<TrangThai> trangThaiList) {
        super(fragmentActivity);
        this.trangThaiList = trangThaiList;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        DanhSachDonHangByTTFragment fragment = new DanhSachDonHangByTTFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("trang_thai", trangThaiList.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        if(trangThaiList != null) {
            return trangThaiList.size();
        }
        return 0;
    }
}
