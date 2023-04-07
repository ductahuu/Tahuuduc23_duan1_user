package com.example.tahuuduc23_duan1_user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tahuuduc23_duan1_user.R;
import com.example.tahuuduc23_duan1_user.model.DonHangChiTiet;
import com.example.tahuuduc23_duan1_user.ultis.OverUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.ChiTietDonHangViewHolder> {
    private Context context;
    private List<DonHangChiTiet> donHangChiTietList;

    public ChiTietDonHangAdapter(Context context,List<DonHangChiTiet> donHangChiTietList) {
        this.context = context;
        this.donHangChiTietList = donHangChiTietList;
    }

    private void setData(List<DonHangChiTiet> list)
    {
        this.donHangChiTietList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChiTietDonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_don_hang_chi_tiet, parent, false);
        return new ChiTietDonHangViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChiTietDonHangViewHolder holder, int position) {
        DonHangChiTiet donHangChiTiet = donHangChiTietList.get(position);
        if (donHangChiTiet == null)
        {
            return;
        }

        Picasso.get()
                .load(donHangChiTiet.getProduct().getImage())
                .into(holder.imgSanPham);
        holder.tvSoLuongSP.setText("x" + donHangChiTiet.getSo_luong());
        holder.tvGiaSP.setText(OverUtils.currencyFormat.format((int) (donHangChiTiet.getProduct().getGia_ban() -
                (donHangChiTiet.getProduct().getGia_ban() * donHangChiTiet.getProduct().getKhuyen_mai()))));
        holder.tvTenSP.setText(donHangChiTiet.getProduct().getName());
    }

    @Override
    public int getItemCount() {
        if (donHangChiTietList != null)
        {
            return donHangChiTietList.size();
        }
        return 0;
    }

    public class ChiTietDonHangViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgSanPham;
        private TextView tvTenSP, tvGiaSP, tvSoLuongSP;
        public ChiTietDonHangViewHolder(@NonNull View v) {
            super(v);
            imgSanPham = v.findViewById(R.id.imgSanPham);
            tvTenSP = v.findViewById(R.id.tvTenSanPham);
            tvGiaSP = v.findViewById(R.id.tvGiaSanPham);
            tvSoLuongSP = v.findViewById(R.id.tvSoLuong);
        }
    }
}
