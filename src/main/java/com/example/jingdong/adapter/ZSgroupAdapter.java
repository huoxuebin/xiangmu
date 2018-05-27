package com.example.jingdong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.jingdong.R;
import com.example.jingdong.bean.ZSBean;
import com.example.jingdong.holder.ZSGroupHolder;
import com.example.jingdong.jiekou.OnItemClickListener;

import java.util.List;

/**
 * Created by huoxuebin on 2018/4/23.
 */

public class ZSgroupAdapter extends RecyclerView.Adapter<ZSGroupHolder> {

    List<ZSBean.DataBean> data;
    Context context;
    private OnItemClickListener onItemClickListener;

    public ZSgroupAdapter(List<ZSBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ZSGroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.zhanshigroup, null);
        ZSGroupHolder groupHolder = new ZSGroupHolder(view);
        return groupHolder;
    }

    @Override
    public void onBindViewHolder(ZSGroupHolder holder, final int position) {
        holder.price.setText("￥"+data.get(position).getPrice());
        holder.title.setText(data.get(position).getTitle());
        String images = data.get(position).getImages();
        String[] split = images.split("\\|");
        Glide.with(context).load(split[0]).into(holder.imageView);
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
