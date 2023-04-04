package com.example.tahuuduc23_duan1_user.activity;

import static com.example.tahuuduc23_duan1_user.ultis.OverUtils.ERROR_MESSAGE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.tahuuduc23_duan1_user.R;
import com.example.tahuuduc23_duan1_user.R;
import com.example.tahuuduc23_duan1_user.dao.UserDao;
import com.example.tahuuduc23_duan1_user.interface_.IAfterGetAllObject;
import com.example.tahuuduc23_duan1_user.model.User;
import com.example.tahuuduc23_duan1_user.ultis.OverUtils;
import com.github.ybq.android.spinkit.style.Wave;
import com.google.firebase.database.DatabaseError;

import java.util.Timer;
import java.util.TimerTask;

public class FlashActivity extends AppCompatActivity {
    public static User userLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //sharePreferences
        String passState = OverUtils.getSPInstance(FlashActivity.this,OverUtils.PASS_FILE)
                .getString("pass",OverUtils.NO_PASS);
        setUpPassAction(passState);

    }

    private void setUpPassAction(String passState) {
        switch (passState){
            case OverUtils.NO_PASS:
                setContentView(R.layout.activity_main);
                break;

            case OverUtils.PASS_FLASH_ACTIVITY:
                Intent loginIntent = new Intent(FlashActivity.this,LoginActivity.class);
                goToActivity(loginIntent);
                break;

            case OverUtils.PASS_LOGIN_ACTIVITY:
                Intent homeIntent = new Intent(FlashActivity.this,HomeActivity.class);
                goToActivity(homeIntent);
                break;

            default:
                break;
        }
    }

    private void goToActivity(Intent intent) {
        ProgressBar progressCircular;
        Timer timer = new Timer();  //hen gio(dong ho)
        setContentView(R.layout.activity_flash);
        progressCircular = findViewById(R.id.progress_circular);
        progressCircular.setIndeterminateDrawable(new Wave());  //animation xu ly loading

        UserDao.getInstance().getUserByUserName(OverUtils.getUserLogin(FlashActivity.this).getUsername(), new IAfterGetAllObject() {
            @Override
            public void iAfterGetAllObject(Object obj) {
                if (obj != null){
                    userLogin = (User) obj;
                    // cài đặt delay vào màn hình login
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(intent);
                            finish();
                        }
                    },500);
                }
            }

            @Override
            public void onError(DatabaseError error) {
                OverUtils.makeToast(FlashActivity.this,ERROR_MESSAGE);
            }
        });
    }

    //onClick MainActivity
    public void btnStart(View view){
        SharedPreferences.Editor editor = OverUtils.getSPInstance(FlashActivity.this,OverUtils.PASS_FILE).edit();
        editor.putString("pass",OverUtils.PASS_FLASH_ACTIVITY);
        editor.apply();
        startActivity(new Intent(FlashActivity.this,LoginActivity.class));
        finish();
    }
}