package com.example.jingdong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jingdong.R;
import com.example.jingdong.bean.TuiBean;
import com.example.jingdong.jiekou.OnItemClickListener;

public class MSadapter extends RecyclerView.Adapter {

    TuiBean.MiaoshaBean miaosha;
    Context context;
    private OnItemClickListener onItemClickListener;


    public MSadapter(TuiBean.MiaoshaBean miaosha, Context context) {
        this.miaosha = miaosha;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LinearLayout.inflate(context, R.layout.miaoshaitem, null);
        TJHolder tjHolder = new TJHolder(view);


        return tjHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {


        final TJHolder holder1 = (TJHolder) holder;
        holder1.title.setText(miaosha.getList().get(position).getTitle());

        String[] split = miaosha.getList().get(position).getImages().split("\\|");

        Glide.with(context).load(split[0]).into(holder1.image2);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return miaosha.getList().size();
    }

    class TJHolder extends RecyclerView.ViewHolder{


        public   ImageView image2;
        public TextView title;



        public TJHolder(View itemView) {
            super(itemView);

            image2 = itemView.findViewById(R.id.msitemage);
            title = itemView.findViewById(R.id.mstitle);

        }
    }
    public void SetOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
