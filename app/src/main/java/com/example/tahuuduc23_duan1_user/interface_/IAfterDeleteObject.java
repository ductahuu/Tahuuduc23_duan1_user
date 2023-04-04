package com.example.tahuuduc23_duan1_user.interface_;

import com.google.firebase.database.DatabaseError;

public interface IAfterDeleteObject {
    void onSuccess(Object obj);
    void onError(DatabaseError error);
}
