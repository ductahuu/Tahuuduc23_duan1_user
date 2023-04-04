package com.example.tahuuduc23_duan1_user.ultis;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.tahuuduc23_duan1_user.model.User;

public class LoginViewModel extends ViewModel {
    private final MutableLiveData<User> selectedItem = new MutableLiveData<>();

    public MutableLiveData<User> getData(){
        return selectedItem;
    }

    public void setData(User user){
        selectedItem.setValue(user);
    }

    public User getSelectedItem(){
        return selectedItem.getValue();
    }
}
