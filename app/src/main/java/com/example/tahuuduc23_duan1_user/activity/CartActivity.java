package com.example.tahuuduc23_duan1_user.activity;

import static com.example.tahuuduc23_duan1_user.activity.FlashActivity.userLogin;
import static com.example.tahuuduc23_duan1_user.ultis.OverUtils.ERROR_MESSAGE;
import static com.example.tahuuduc23_duan1_user.ultis.OverUtils.HOAT_DONG;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tahuuduc23_duan1_user.R;
import com.example.tahuuduc23_duan1_user.adapter.CartAdapter;
import com.example.tahuuduc23_duan1_user.dao.ProductDao;
import com.example.tahuuduc23_duan1_user.dao.UserDao;
import com.example.tahuuduc23_duan1_user.interface_.IAfterGetAllObject;
import com.example.tahuuduc23_duan1_user.interface_.OnChangeSoLuongItem;
import com.example.tahuuduc23_duan1_user.interface_.OnClickItem;
import com.example.tahuuduc23_duan1_user.model.GioHang;
import com.example.tahuuduc23_duan1_user.model.Product;
import com.example.tahuuduc23_duan1_user.ultis.OverUtils;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements OnClickItem, OnChangeSoLuongItem {

    private Toolbar toolbar;
    private RecyclerView recyclerViewCart;
    private TextView tvThanhToan;

    private List<GioHang> gioHangList;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        setUpToolbar();

    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> CartActivity.this.onBackPressed());
    }

    static int soLuongSPPhuHop = 0;

    @Override
    protected void onStart() {
        super.onStart();
        setUpGioHangList();
        setUpBtnThanhToan();
    }

    private void setUpBtnThanhToan() {
        tvThanhToan.setOnClickListener(v-> {
            if (gioHangList == null || gioHangList.size() == 0){
                OverUtils.makeToast(CartActivity.this,"Giỏ hàng của quý khách đang trống");
            }else {
                int count = 0;
                for (int i = 0;i < gioHangList.size();i++){
                    count++;
                    int finalCount = count;
                    ProductDao.getInstance().getProductById(gioHangList.get(i).getMa_sp(), new IAfterGetAllObject() {
                        @Override
                        public void iAfterGetAllObject(Object obj) {
                            if (obj != null){
                                Product product = (Product) obj;
                                if (product.getTrang_thai().equals(HOAT_DONG)){
                                    soLuongSPPhuHop++;
                                }
                                if (finalCount == gioHangList.size()){
                                    if (soLuongSPPhuHop == 0){
                                        OverUtils.makeToast(CartActivity.this,"Giỏ hàng không có sản phẩm phù hợp");
                                    }else {
                                        soLuongSPPhuHop = 0;
                                        Intent intent = new Intent(CartActivity.this,ThanhToanActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onError(DatabaseError error) {

                        }
                    });
                }
            }
        });
    }

    private void setUpGioHangList() {
        gioHangList = new ArrayList<>();
        cartAdapter = new CartAdapter(CartActivity.this,gioHangList,CartActivity.this,CartActivity.this);
        recyclerViewCart.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        recyclerViewCart.setAdapter(cartAdapter);

        UserDao.getInstance().getGioHangOfUser(userLogin, new IAfterGetAllObject() {
            @Override
            public void iAfterGetAllObject(Object obj) {
                gioHangList = (List<GioHang>) obj;
                cartAdapter.setData(gioHangList);
            }

            @Override
            public void onError(DatabaseError error) {
                OverUtils.makeToast(CartActivity.this,ERROR_MESSAGE);
            }
        });
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        recyclerViewCart = findViewById(R.id.recyclerViewCart);
        tvThanhToan = findViewById(R.id.tvThanhToan);
    }

    @Override
    public void onChangeItem(int viTriItem, GioHang gioHang) {

    }

    @Override
    public void onClickItem(Object obj) {
        String productId = (String) obj;
        Intent intent = new Intent(CartActivity.this, ShowProductActivity.class);
        intent.putExtra("productId", productId);
        startActivity(intent);
    }

    @Override
    public void onDeleteItem(Object obj) {
        GioHang gioHang = (GioHang) obj;
        gioHangList.remove(gioHang);
        userLogin.setGio_hang(gioHangList);
        UserDao.getInstance().updateUser(userLogin,
                userLogin.toMapGioHang());
        cartAdapter.setData(gioHangList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
