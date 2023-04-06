package com.example.tahuuduc23_duan1_user.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tahuuduc23_duan1_user.R;
import com.example.tahuuduc23_duan1_user.activity.ChiTietDonHangActivity;
import com.example.tahuuduc23_duan1_user.model.DonHang;
import com.example.tahuuduc23_duan1_user.model.TrangThai;
import com.example.tahuuduc23_duan1_user.ultis.OverUtils;

import java.util.Date;
import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.DonHangViewHolder> {
    Context context;
    List<DonHang> donHangList;

    public DonHangAdapter(Context context, List<DonHang> donHangList) {
        this.context = context;
        this.donHangList = donHangList;
    }

    public void setData(List<DonHang> list){
        donHangList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonHangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_donhang, parent, false);
        return new DonHangViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DonHangViewHolder holder, int position) {
        DonHang donHang = donHangList.get(position);
        if (donHang.getDon_hang_chi_tiets() == null){
            return;
        }
        holder.tvMaDonHang.setText(donHang.getId());
        if (donHang.getThoiGianGiaoHangDuKien() == 0){
            holder.tvNgayDatHang.setText(donHang.getThoiGianDatHang());
        }else {
            holder.tvNgayDatHang.setText(OverUtils.getSimpleDateFormat().format(new Date(donHang.getThoiGianGiaoHangDuKien())));
        }
        holder.tvTongTien.setText(donHang.getTong_tien() + " VND");
        if (donHang.getTrang_thai().equals(TrangThai.HOAN_THANH.getTrangThai())){
            holder.tvTrangThai.setTextColor(Color.GREEN);
        }else {
            holder.tvTrangThai.setTextColor(Color.RED);
        }
        holder.tvTrangThai.setText(donHang.getTrang_thai());
        holder.linearLayoutDonHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChiTietDonHangActivity.class);
                intent.putExtra("donHangID",donHang.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }

    class DonHangViewHolder extends RecyclerView.ViewHolder{
        TextView tvMaDonHang,tvNgayDatHang, tvTongTien, tvTrangThai;
        LinearLayout linearLayoutDonHang;
        TextView tvTenSanPhams;
        TextView tvShipper;
        TextView tvTitleShipper;
        public DonHangViewHolder(@NonNull View v) {
            super(v);
            linearLayoutDonHang = v.findViewById(R.id.lineLayout_DonHang);
            tvMaDonHang = v.findViewById(R.id.tv_MaDonHang);
            tvNgayDatHang = v.findViewById(R.id.tv_NgayDatHang);
            tvTongTien = v.findViewById(R.id.tv_TongTien);
            tvTrangThai = v.findViewById(R.id.tv_TrangThai);
            tvTenSanPhams = v.findViewById(R.id.tv_TenSanPham);
            tvShipper = v.findViewById(R.id.tv_Shipper);
            tvTitleShipper = v.findViewById(R.id.tvTitleShipper);
        }
    }
}
