package com.example.jingdong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.jingdong.R;
import com.example.jingdong.bean.ZSBean;
import com.example.jingdong.holder.ZSHolder;
import com.example.jingdong.jiekou.OnItemClickListener;

import java.util.List;

/**
 * Created by huoxuebin on 2018/4/23.
 */

public class ZSAdapter extends RecyclerView.Adapter<ZSHolder> {

    List<ZSBean.DataBean> data;
    Context context;
    private OnItemClickListener onItemClickListener;

    public ZSAdapter(List<ZSBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ZSHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.zhanshi, null);
        ZSHolder zsHolder = new ZSHolder(view);
        return zsHolder;
    }

    @Override
    public void onBindViewHolder(ZSHolder holder, final int position) {
        holder.zsprice.setText("￥"+data.get(position).getPrice());
        holder.zstitle.setText(data.get(position).getTitle());
        String images = data.get(position).getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.zsimg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
