package com.example.tahuuduc23_duan1_user.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tahuuduc23_duan1_user.R;
import com.example.tahuuduc23_duan1_user.activity.LoginActivity;
import com.example.tahuuduc23_duan1_user.dao.UserDao;
import com.example.tahuuduc23_duan1_user.interface_.IAfterGetAllObject;
import com.example.tahuuduc23_duan1_user.interface_.IAfterInsertObject;
import com.example.tahuuduc23_duan1_user.model.User;
import com.example.tahuuduc23_duan1_user.ultis.LoginViewModel;
import com.example.tahuuduc23_duan1_user.ultis.OverUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;

public class SignupTabFragment extends Fragment {
    private EditText edtSoDienThoai;
    private EditText edtTenDangNhap;
    private EditText edtMatKhau;
    private EditText edtNhapLaiMatKhau;
    private ToggleButton btnCheckPass2;
    private ToggleButton btnCheckPass;
    private Button btnHuyDangKi;
    private Button btnDangKy;

    private LoginActivity loginActivity;

    //bien moi truong ket noi cua Auth cua Firebase
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        loginActivity = (LoginActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_tab,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        setUpEdtSoDienThoai();
        setUpBtnCheckPass(btnCheckPass, edtMatKhau);
        setUpBtnCheckPass(btnCheckPass2, edtNhapLaiMatKhau);
        setUpBtnLogin();
        setUpBtnCancel();
    }

    private void setUpBtnLogin() {
        btnDangKy.setOnClickListener(v -> {
            String phone_number = "+84" + edtSoDienThoai.getText().toString().trim();
            String username = edtTenDangNhap.getText().toString().trim();
            String password = edtMatKhau.getText().toString().trim();
            String rePassword = edtNhapLaiMatKhau.getText().toString().trim();

            if (validate(phone_number,username,password,rePassword)){
                UserDao.getInstance().isDuplicateUserName(username, new IAfterGetAllObject() {
                    @Override
                    public void iAfterGetAllObject(Object obj) {
                        if ((Boolean) obj){
                            OverUtils.makeToast(getContext(), "Tên đăng nhập này đễ tồn tại");
                        }else {
                            UserDao.getInstance().isDuplicatePhoneNumber(phone_number, new IAfterGetAllObject() {
                                @Override
                                public void iAfterGetAllObject(Object obj) {
                                    if ((Boolean) obj){
                                        OverUtils.makeToast(getContext(), "Số điện thoại này đã tồn tại");
                                        return;
                                    }
                                    User newUser = new User(username,password,phone_number,true);
                                    UserDao.getInstance().insertUser(newUser, new IAfterInsertObject() {
                                        @Override
                                        public void onSuccess(Object obj) {
                                            //dung view model dang ky
                                            LoginViewModel loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
                                            loginViewModel.setData((User) obj);
                                            OverUtils.makeToast(getContext(),"Đăng kí thành công");
                                            goToLoginFragment();
                                        }

                                        @Override
                                        public void onError(DatabaseError exception) {
                                            OverUtils.makeToast(getContext(), OverUtils.ERROR_MESSAGE);
                                        }
                                    });
                                }

                                @Override
                                public void onError(DatabaseError error) {
                                    OverUtils.makeToast(getContext(), OverUtils.ERROR_MESSAGE);

                                }
                            });
                        }
                    }

                    @Override
                    public void onError(DatabaseError error) {
                        OverUtils.makeToast(getContext(), OverUtils.ERROR_MESSAGE);
                    }
                });
            }
        });
    }

    private void setUpEdtSoDienThoai() {
        edtSoDienThoai.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus){
                edtSoDienThoai.setHint("");
            }else {
                edtSoDienThoai.setHint("Nhập số điện thoại");
            }
        });
    }

    private boolean validate(String phone_number, String username, String password, String rePassword) {
        if (password.isEmpty() || rePassword.isEmpty() || username.isEmpty() || phone_number.isEmpty()) {
            OverUtils.makeToast(getContext(), "Quý khánh vui lòng nhập đầy đủ thông tin");
            return false;
        }

        if (username.length() <= 5) {
            OverUtils.makeToast(getContext(), "Quý khánh vui lòng đặt tên đăng nhập từ 6 kí tự trở lên");
            return false;
        }

        if (!phone_number.matches("^\\+84\\d{9,10}$")) {
            OverUtils.makeToast(getContext(), "Sai định dạng số điện thoại");
            return false;
        }

        if (!password.equals(rePassword)) {
            OverUtils.makeToast(getContext(), "Mật khẩu không trùng khớp");
            return false;
        }

        if (password.matches("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")) {
        } else {
            OverUtils.makeToast(getContext(),
                    "Mật khẩu cần 8 kí tự trở lên, trong đó có chứa kí tự đặc biệt, chữ cái viết hoa và số");
            return false;
        }
        return true;
    }

    private void goToLoginFragment() {
        edtSoDienThoai.setText("");
        edtMatKhau.setText("");
        edtNhapLaiMatKhau.setText("");
        edtTenDangNhap.setText("");
        loginActivity.getTabLayout().selectTab(loginActivity.getTabLayout().getTabAt(0));
    }

    private void setUpBtnCheckPass(ToggleButton btnCheckPass, EditText edtMatKhau) {
        btnCheckPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btnCheckPass.isChecked()){
                    edtMatKhau.setTransformationMethod(null);
                }else {
                    //an ky tu ngay khi nhap vao
                    edtMatKhau.setTransformationMethod(new PasswordTransformationMethod());
                }
            }
        });
    }

    private void setUpBtnCancel() {
        btnHuyDangKi.setOnClickListener(v -> {
            edtTenDangNhap.setText("");
            edtMatKhau.setText("");
            edtNhapLaiMatKhau.setText("");
            edtSoDienThoai.setText("");
        });
    }

    private void initView(View view) {
        edtSoDienThoai = view.findViewById(R.id.edtSoDienThoai);
        edtTenDangNhap = view.findViewById(R.id.edtTenDangNhap);
        edtMatKhau = view.findViewById(R.id.edtMatKhau);
        edtNhapLaiMatKhau = view.findViewById(R.id.edtNhapLaiMatKhau);
        btnCheckPass2 = view.findViewById(R.id.btnCheckPass2);
        btnHuyDangKi = view.findViewById(R.id.btnHuyDangKy);
        btnDangKy = view.findViewById(R.id.btnDangKy);
        btnCheckPass = view.findViewById(R.id.btnCheckPass);
    }
}
