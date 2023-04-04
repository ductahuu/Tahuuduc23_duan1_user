package com.example.tahuuduc23_duan1_user.local_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.tahuuduc23_duan1_user.model.User;

@Database(entities = {User.class},version = 1)
//@TypeConverters({StringTypeConverter.class,GioHangTypeConverter.class})
public abstract class LocalUserDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "user.db";

    private static LocalUserDatabase instance;
    //chỉ cho phép một luồng duy nhất truy cập vào dữ liệu hoặc tài nguyên : synchronized
    public synchronized static LocalUserDatabase getInstance(Context context){
        if (instance == null){
            instance =
                    Room.databaseBuilder(context,LocalUserDatabase.class,DATABASE_NAME)
                            .allowMainThreadQueries().build();
        }
        return instance;
    }

    public abstract LocalUserDao getUserDao();
}
