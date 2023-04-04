package com.example.tahuuduc23_duan1_user.activity;

import static com.example.tahuuduc23_duan1_user.activity.FlashActivity.userLogin;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tahuuduc23_duan1_user.R;
import com.example.tahuuduc23_duan1_user.dao.UserDao;
import com.example.tahuuduc23_duan1_user.fragment.HomeFragment;
import com.example.tahuuduc23_duan1_user.fragment.LikeProductFragment;
import com.example.tahuuduc23_duan1_user.fragment.OrderFragment;
import com.example.tahuuduc23_duan1_user.fragment.ProfileFragment;
import com.example.tahuuduc23_duan1_user.interface_.IAfterGetAllObject;
import com.example.tahuuduc23_duan1_user.model.User;
import com.example.tahuuduc23_duan1_user.ultis.OverUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseError;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        triggerOrderFragment();
        listenLockAccount();
        setUpBottomNavigationView();
    }

    private void triggerOrderFragment() {
        Intent intent = getIntent();
        if (intent != null && intent.getAction() != null){
            switch (intent.getAction()){
                case OverUtils.GO_TO_ORDER_FRAGMENT:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerViewHome,new OrderFragment())
                            .commit();
                    bottomNavigationView.setSelectedItemId(R.id.nav_order);
                    break;

                case OverUtils.GO_TO_ORDER_FROFILE_FRAGMENT:
                    Fragment fragment = new ProfileFragment();
                    Bundle bundle = new Bundle();
                    String imgLink = intent.getStringExtra("img");
                    bundle.putString("img",imgLink);
                    fragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragmentContainerViewHome,fragment)
                            .commit();
                    bottomNavigationView.setSelectedItemId(R.id.nav_profile);
                    break;

                case OverUtils.FROM_SHOW_PRODUCT:
                    Toast.makeText(getApplicationContext(), "Sản phẩm bạn vừa xem đã bị xóa", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
            setIntent(new Intent());
        }
    }

    private void listenLockAccount() {
        UserDao.getInstance().getUserByUserName(userLogin.getUsername(), new IAfterGetAllObject() {
            @Override
            public void iAfterGetAllObject(Object obj) {
                if (obj != null){
                    User user = (User) obj;
                    if (user.getUsername() != null){
                        if (!user.isEnable()){
                            new AlertDialog.Builder(HomeActivity.this)
                                    .setTitle("Tài khoản")
                                    .setMessage("Tài khoản của bạn đã vi phạm một số điều khoản khi sử dụng." +
                                            "\nVì vậy, chúng tôi tạm thời khóa tài khoản này." +
                                            "\nLiên hệ 19001000 để tìm hiểu chi tiết")
                                    .setNegativeButton("Hủy",(dialog, which) -> {
                                        SharedPreferences.Editor editor = OverUtils.getSPInstance(getApplicationContext(),OverUtils.PASS_FILE).edit();
                                        editor.putString("pass",OverUtils.PASS_FLASH_ACTIVITY);
                                        editor.apply();

                                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                    }).setPositiveButton("OK",(dialog, which) -> {
                                        SharedPreferences.Editor editor = OverUtils.getSPInstance(getApplicationContext(),OverUtils.PASS_FILE).edit();
                                        editor.putString("pass", OverUtils.PASS_FLASH_ACTIVITY);
                                        editor.apply();
                                        Intent intent = new Intent("CALL_PHONE");
                                        intent.setClass(HomeActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                    }).setCancelable(false)
                                    .create().show();
                        }
                    }
                }
            }

            @Override
            public void onError(DatabaseError error) {

            }
        });
    }

    private void initView() {
        bottomNavigationView = findViewById(R.id.bottom_nav_home);
    }


    public void btnCart(View view) {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
    }

    private void setUpBottomNavigationView() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selected = null;
            if (item.getItemId() == R.id.nav_home) {
                selected = new HomeFragment();
            } else if (item.getItemId() == R.id.nav_order) {
                selected = new OrderFragment();
            } else if (item.getItemId() == R.id.nav_like) {
                selected = new LikeProductFragment();
            } else if (item.getItemId() == R.id.nav_profile) {
                selected = new ProfileFragment();
            }
            if (selected != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerViewHome, selected).commit();
            }
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainerViewHome);
        if (fragment instanceof HomeFragment){
            finish();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerViewHome,new HomeFragment()).commit();
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }
        super.onBackPressed();
    }
}
