package com.example.tahuuduc23_duan1_user.activity;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import static com.example.tahuuduc23_duan1_user.activity.FlashActivity.userLogin;
import static com.example.tahuuduc23_duan1_user.ultis.OverUtils.ERROR_MESSAGE;
import static com.example.tahuuduc23_duan1_user.ultis.OverUtils.HOAT_DONG;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tahuuduc23_duan1_user.R;
import com.example.tahuuduc23_duan1_user.adapter.GioHangAdapter;
import com.example.tahuuduc23_duan1_user.dao.GioHangDao;
import com.example.tahuuduc23_duan1_user.dao.OrderDao;
import com.example.tahuuduc23_duan1_user.dao.ProductDao;
import com.example.tahuuduc23_duan1_user.dao.UserDao;
import com.example.tahuuduc23_duan1_user.interface_.IAfterGetAllObject;
import com.example.tahuuduc23_duan1_user.interface_.IAfterInsertObject;
import com.example.tahuuduc23_duan1_user.interface_.IAfterUpdateObject;
import com.example.tahuuduc23_duan1_user.model.DonHang;
import com.example.tahuuduc23_duan1_user.model.DonHangChiTiet;
import com.example.tahuuduc23_duan1_user.model.GioHang;
import com.example.tahuuduc23_duan1_user.model.Product;
import com.example.tahuuduc23_duan1_user.model.TrangThai;
import com.example.tahuuduc23_duan1_user.model.User;
import com.example.tahuuduc23_duan1_user.ultis.OverUtils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ThanhToanActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imvBack;
    private TextView tvTitle;
    private TextView tvThemDiaChi;
    private CardView rcvDiaChi;
    private TextView tvTiltleHoTen;
    private TextView tvHoTen;
    private TextView tvTitleDiaChiGiaoHang;
    private TextView tvDiaChiGiaoHang;
    private TextView textView8;
    private TextView tvSDT;
    private TextView tvtThoiGianGiaoHang;
    private TextView tvDoiThoiGianGH;
    private RecyclerView rcvSanPham;
    private TextView tvSoSanPham;
    private TextView tvTien;
    private TextView tvTitlePhiVanChuyen;
    private TextView tvPhiVanChuyen;
    private TextView tvTongTien;
    private TextView tvNhapMaGiamGia;
    private TextView tvNhapGhiChu;
    private TextView tvDangHang;


    private List<GioHang> gioHangList;
    private List<GioHang> gioHangListNoValid;
    private List<DonHangChiTiet> donHangChiTietList;

    private static int tongSoSP;
    private static int thoiGianGiaoHang;
    private static int soTienVanChuyen;
    private static int soTienThanhToan;
    
    private String ghiChu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        initView();
        setUpToolbar();
        setUpCarDiaChi();
        setUpThemDiaChi();
        setUpGhiChu();
        setUpDoiThoiGian();
        setUpSanPhamList();
    }

    int i = 0; //cờ để xác định khi lấy thông tin
    private void setUpSanPhamList() {
        gioHangList = new ArrayList<>();
        gioHangListNoValid = new ArrayList<>();
        UserDao.getInstance().getGioHangOfUser(userLogin, new IAfterGetAllObject() {
            @Override
            public void iAfterGetAllObject(Object obj) {
                List<GioHang> gioHangsOfUser = (List<GioHang>) obj;
                for (GioHang gioHang : gioHangsOfUser) {
                    ProductDao.getInstance().getProductById(gioHang.getMa_sp(), new IAfterGetAllObject() {
                        @Override
                        public void iAfterGetAllObject(Object obj) {
                            i++;
                            Product product = (Product) obj;
                            if (product.getTrang_thai().equals(HOAT_DONG)) {
                                gioHangList.add(gioHang);
                            } else {
                                gioHangListNoValid.add(gioHang);
                            }
                            if (i == gioHangsOfUser.size()) {
                                GioHangAdapter gioHangAdapter = new GioHangAdapter(gioHangList);
                                rcvSanPham.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                rcvSanPham.setAdapter(gioHangAdapter);
                                setUpThoiGianGiaoAndTienThanhToan();
                            }
                        }

                        @Override
                        public void onError(DatabaseError error) {
                            OverUtils.makeToast(ThanhToanActivity.this, ERROR_MESSAGE);
                        }
                    });
                }
            }

            @Override
            public void onError(DatabaseError error) {
                OverUtils.makeToast(ThanhToanActivity.this, ERROR_MESSAGE);
            }
        });

    }

    private void setUpThoiGianGiaoAndTienThanhToan() {
        donHangChiTietList = new ArrayList<>();
        for (GioHang gioHang : gioHangList){
            ProductDao.getInstance().getProductById(gioHang.getMa_sp(), new IAfterGetAllObject() {
                @Override
                public void iAfterGetAllObject(Object obj) {
                    if (obj != null){
                        Product product = (Product) obj;
                        count++;
                        //lấy tổng sản phẩm
                        tongSoSP += gioHang.getSo_luong();
                        //lấy đơn hàng chi tiết
                        DonHangChiTiet donHangChiTiet = new DonHangChiTiet();
                        donHangChiTiet.setSo_luong(gioHang.getSo_luong());
                        donHangChiTiet.setProduct(product);
                        donHangChiTietList.add(donHangChiTiet);

                        //lấy thời gian giao hàng và phí
                        thoiGianGiaoHang += product.getThoiGianCheBien();
                        soTienThanhToan += ((product.getGia_ban() - (product.getGia_ban() * product.getKhuyen_mai()))* gioHang.getSo_luong());
                        if (count == gioHangList.size()){
                            tvSoSanPham.setText(tongSoSP + " sản phẩm");
                            tvTien.setText(OverUtils.currencyFormat.format(soTienThanhToan));
                            tvTongTien.setText(OverUtils.currencyFormat.format(soTienThanhToan + soTienVanChuyen));

                            //thời gian giao hàng bằng thời gian hiện tại + thời gian chế bieesn + thời gian ship(20p)
                            long thoiGianGiaoHangDuKien = System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(thoiGianGiaoHang) + TimeUnit.MINUTES.toMillis(20);
                            setUpDatHang();
                        }
                    }
                }

                @Override
                public void onError(DatabaseError error) {

                }
            });
        }
    }

    private boolean datHang = true;
    private void setUpDatHang() {
        tvDangHang.setOnClickListener(v -> {
            if (datHang){
                datHang = false;
                if (userLogin.getAddress() == null || userLogin.getName() == null){
                    OverUtils.makeToast(ThanhToanActivity.this,"Cần thêm địa chỉ");
                    return;
                }
                UserDao.getInstance().getUserByUserName(userLogin.getUsername(), new IAfterGetAllObject() {
                    @Override
                    public void iAfterGetAllObject(Object obj) {
                        User user = (User) obj;
                        if (user.getUsername() != null || user.isEnable()){
                            if (!donHangChiTietList.isEmpty()){
                                DonHang donHang = new DonHang();
                                donHang.setUser_id(user.getUsername());
                                donHang.setDia_chi(user.getAddress());
                                donHang.setHo_ten(user.getName());
                                donHang.setDon_hang_chi_tiets(donHangChiTietList);
                                if (ghiChu != null){
                                    donHang.setGhi_chu(ghiChu);
                                }
                                donHang.setSdt(user.getPhone_number());
                                donHang.setTrang_thai(TrangThai.CHUA_XAC_NHAN.getTrangThai());
                                donHang.setThoiGianDatHang(OverUtils.getSimpleDateFormat().format(new Date(System.currentTimeMillis())));
                                donHang.setThoiGianGiaoHangDuKien(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(thoiGianGiaoHang) + TimeUnit.MINUTES.toMillis(30));
                                donHang.setTong_tien(soTienThanhToan + soTienVanChuyen);
                                String key = FirebaseDatabase.getInstance().getReference().child("don_hang").push().getKey();
                                donHang.setId(key);
                                OrderDao.getInstance().insertDonHang(donHang, new IAfterInsertObject() {
                                    @Override
                                    public void onSuccess(Object obj) {
                                        xoaGioHang();
                                    }

                                    @Override
                                    public void onError(DatabaseError exception) {
                                        OverUtils.makeToast(ThanhToanActivity.this, ERROR_MESSAGE);
                                    }
                                });
                            }else {
                                OverUtils.makeToast(ThanhToanActivity.this, "Quý khách vui lòng chọn sản phẩm");
                            }
                        }else {
                            if (user.getUsername() != null){
                                OverUtils.makeToast(ThanhToanActivity.this,"Tài khoản của bạn đã bị khóa");
                            }
                        }
                    }

                    @Override
                    public void onError(DatabaseError error) {

                    }
                });
            }

        });
    }

    private void xoaGioHang() {
        userLogin.setGio_hang(gioHangListNoValid);
        GioHangDao.getInstance().insertGioHang(userLogin, userLogin.getGio_hang(), new IAfterInsertObject() {
            @Override
            public void onSuccess(Object obj) {
                OverUtils.makeToast(ThanhToanActivity.this,"Đặt hàng thành công");
                datHang = true;
                Intent intent = new Intent(ThanhToanActivity.this,HomeActivity.class);
                intent.setAction(OverUtils.GO_TO_ORDER_FRAGMENT);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(DatabaseError exception) {
                OverUtils.makeToast(ThanhToanActivity.this,ERROR_MESSAGE);
            }
        });
    }


    private void setUpDoiThoiGian() {
        tvDoiThoiGianGH.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Chưa hỗ trợ", Toast.LENGTH_SHORT).show();
        });
    }

    private void setUpThemDiaChi() {
        tvThemDiaChi.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ThanhToanActivity.this);
            bottomSheetDialog.setContentView(R.layout.layout_them_dia_chi);

            EditText edtHoTen;
            EditText edtDiaChi;
            EditText edtSDT;
            Button btnHuy;
            Button btnThemDiaChi;

            edtHoTen = bottomSheetDialog.findViewById(R.id.edtHoTen);
            edtDiaChi = bottomSheetDialog.findViewById(R.id.edtDiaChi);
            edtSDT = bottomSheetDialog.findViewById(R.id.edtSDT);
            btnHuy = bottomSheetDialog.findViewById(R.id.btnHuy);
            btnThemDiaChi = bottomSheetDialog.findViewById(R.id.btnThemDiaChi);


            if (userLogin.getPhone_number() != null) {
                edtSDT.setText(userLogin.getPhone_number());
            }
            if (userLogin.getName() != null) {
                edtHoTen.setText(userLogin.getName());
            }
            if (userLogin.getAddress() != null) {
                edtDiaChi.setText(userLogin.getAddress());
            }
            btnHuy.setOnClickListener(v1 -> bottomSheetDialog.cancel());

            btnThemDiaChi.setOnClickListener(v12 -> {
                String hoTen = edtHoTen.getText().toString().trim();
                String diaChi = edtDiaChi.getText().toString().trim();
                String sdt = edtSDT.getText().toString().trim();
                if (hoTen.isEmpty() || diaChi.isEmpty() || sdt.isEmpty()) {
                    OverUtils.makeToast(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin");
                    return;
                }
                if (!sdt.matches("^\\+84\\d{9,10}$")) {
                    OverUtils.makeToast(getApplicationContext(), "Vui lòng nhập đúng định dạng số điện thoại (vd: +84868358175)");
                    return;
                }

                userLogin.setPhone_number(sdt);
                userLogin.setAddress(diaChi);
                userLogin.setName(hoTen);

                UserDao.getInstance().updateUser(userLogin,
                        userLogin.toMapThongTinGiaoHang(),
                        new IAfterUpdateObject() {
                            @Override
                            public void onSuccess(Object obj) {
                                OverUtils.makeToast(getApplicationContext(), "Lưu địa chỉ thành công");
                                rcvDiaChi.setVisibility(VISIBLE);
                                tvHoTen.setText(hoTen);
                                tvDiaChiGiaoHang.setText(diaChi);
                                tvSDT.setText(sdt);
                                bottomSheetDialog.cancel();
                            }

                            @Override
                            public void onError(DatabaseError error) {
                                OverUtils.makeToast(ThanhToanActivity.this, ERROR_MESSAGE);
                            }
                        });
            });

            bottomSheetDialog.show();
        });
    }

    private void setUpCarDiaChi() {
        String hoTen = userLogin.getName();
        String diaChi = userLogin.getAddress();
        String phoneNumber = userLogin.getPhone_number();
        if (hoTen == null || diaChi == null) {
            rcvDiaChi.setVisibility(INVISIBLE);
        } else {
            rcvDiaChi.setVisibility(VISIBLE);
            tvThemDiaChi.setText("Sửa địa chỉ");
            tvDiaChiGiaoHang.setText(diaChi);
            tvSDT.setText(phoneNumber);
            tvHoTen.setText(hoTen);
        }
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> ThanhToanActivity.this.onBackPressed());
    }

    private void setUpGhiChu() {
        tvNhapGhiChu.setOnClickListener(v -> {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ThanhToanActivity.this);
            bottomSheetDialog.setContentView(R.layout.layout_ghi_chu);

            EditText edtGhiChu = bottomSheetDialog.findViewById(R.id.edtGhiChu);
            Button btnGhiChu = bottomSheetDialog.findViewById(R.id.btnGhiChu);

            if (ghiChu != null){
                edtGhiChu.setText(ghiChu);
            }

            btnGhiChu.setOnClickListener(v1 -> {
                ThanhToanActivity.this.ghiChu = edtGhiChu.getText().toString().trim();
                bottomSheetDialog.cancel();
            });

            bottomSheetDialog.cancel();
        });
    }
    
    int count = 0;//bien nay dung de xac dinh khi nao client lay het du lieu,sau do hien thi cac thong tin
    
    private void initView(){
        toolbar = findViewById(R.id.toolbar);
        imvBack = findViewById(R.id.imvBack);
        tvTitle = findViewById(R.id.tvTitle);
        tvThemDiaChi = findViewById(R.id.tvThemDiaChi);
        rcvDiaChi = findViewById(R.id.rcvDiaChi);
        tvTiltleHoTen = findViewById(R.id.tvTiltleHoTen);
        tvHoTen = findViewById(R.id.tvHoTen);
        tvTitleDiaChiGiaoHang = findViewById(R.id.tvTitleDiaChiGiaoHang);
        tvDiaChiGiaoHang = findViewById(R.id.tvDiaChiGiaoHang);
        textView8 = findViewById(R.id.textView8);
        tvSDT = findViewById(R.id.tvSDT);
        tvtThoiGianGiaoHang = findViewById(R.id.tvtThoiGianGiaoHang);
        tvDoiThoiGianGH = findViewById(R.id.tvDoiThoiGianGH);
        rcvSanPham = findViewById(R.id.rcvSanPham);
        tvSoSanPham = findViewById(R.id.tvSoSanPham);
        tvTien = findViewById(R.id.tvTien);
        tvTitlePhiVanChuyen = findViewById(R.id.tvTitlePhiVanChuyen);
        tvPhiVanChuyen = findViewById(R.id.tvPhiVanChuyen);
        tvTongTien = findViewById(R.id.tvTongTien);
        tvNhapMaGiamGia = findViewById(R.id.tvNhapMaGiamGia);
        tvNhapGhiChu = findViewById(R.id.tvNhapGhiChu);
        tvDangHang = findViewById(R.id.tvDangHang);

        tongSoSP = 0;
        thoiGianGiaoHang = 0;
        count = 0;
        soTienThanhToan = 0;
        soTienVanChuyen = 0;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
