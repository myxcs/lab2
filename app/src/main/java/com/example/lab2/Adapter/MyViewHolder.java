package com.example.lab2.Adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab2.R;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tvContent, tvDate;
    CheckBox chk;
    ImageView iv_xoa, iv_sua;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        tvContent = itemView.findViewById(R.id.tv_content);
        tvDate = itemView.findViewById(R.id.tv_date);
        chk = itemView.findViewById(R.id.chk_status);
       iv_xoa = itemView.findViewById(R.id.iv_delete);
       iv_sua = itemView.findViewById(R.id.iv_edit);
    }
}
