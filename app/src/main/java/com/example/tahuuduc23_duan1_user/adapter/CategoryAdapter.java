package com.example.tahuuduc23_duan1_user.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tahuuduc23_duan1_user.R;
import com.example.tahuuduc23_duan1_user.interface_.UpdateRecyclerView;
import com.example.tahuuduc23_duan1_user.model.LoaiSP;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    Context context;
    List<LoaiSP> categoryArrayList;
    UpdateRecyclerView updateRecyclerView;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<LoaiSP> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;
        notifyDataSetChanged();
    }

    public CategoryAdapter(Context context, List<LoaiSP> categoryArrayList, UpdateRecyclerView updateRecyclerView) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.updateRecyclerView = updateRecyclerView;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_cat,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        LoaiSP category = categoryArrayList.get(position);
        holder.categoryName.setText(category.getName());
        Picasso.get().load(category.getHinhanh())
                .placeholder(R.drawable.ic_image)
                .into(holder.categoryPic);

        holder.mainLayout.setOnClickListener(v -> updateRecyclerView.callback(category.getId()));
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout mainLayout;
        TextView categoryName;
        ImageView categoryPic;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            categoryName = itemView.findViewById(R.id.categoryName);
            categoryPic = itemView.findViewById(R.id.categoryPic);
        }
    }
}
