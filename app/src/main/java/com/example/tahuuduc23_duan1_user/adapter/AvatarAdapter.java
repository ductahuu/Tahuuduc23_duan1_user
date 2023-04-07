package com.example.tahuuduc23_duan1_user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tahuuduc23_duan1_user.R;
import com.example.tahuuduc23_duan1_user.interface_.ClickAvatar;
import com.example.tahuuduc23_duan1_user.model.Avatar;

import java.util.List;

public class AvatarAdapter extends RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder> {

    private Context context;
    private List<Avatar> list;
    private ClickAvatar clickAvatar;

    public AvatarAdapter(Context context, List<Avatar> list) {
        this.context = context;
        this.list = list;
    }

    public void setClickAvatar(ClickAvatar clickAvatar) {
        this.clickAvatar = clickAvatar;
    }

    @NonNull
    @Override
    public AvatarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_avatar, parent, false);
        return new AvatarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvatarViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class AvatarViewHolder extends RecyclerView.ViewHolder{

        public AvatarViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
