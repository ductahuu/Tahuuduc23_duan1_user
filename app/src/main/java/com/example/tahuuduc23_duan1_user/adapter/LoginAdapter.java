package com.example.tahuuduc23_duan1_user.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.tahuuduc23_duan1_user.fragment.LoginTabFragment;
import com.example.tahuuduc23_duan1_user.fragment.SignupTabFragment;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;

    public LoginAdapter(@NonNull FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new LoginTabFragment();
            case 1:
                return new SignupTabFragment();
            default:
                return null;
        }
    }


    //Tao 2 ben dang nhap dang ky = viewpager
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Đăng nhập";
                break;

            case 1 :
                title = "Đăng ký";
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
