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

import java.util.List;

public class TJadapter extends RecyclerView.Adapter {

    TuiBean.TuijianBean tuijian;

    Context context;
    private OnItemClickListener onItemClickListener;


    public TJadapter(TuiBean.TuijianBean tuijian, Context context) {
        this.tuijian = tuijian;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LinearLayout.inflate(context, R.layout.tuijian, null);
        TJHolder tjHolder = new TJHolder(view);


        return tjHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {


        final TJHolder holder1 = (TJHolder) holder;
        holder1.title.setText(tuijian.getList().get(position).getTitle());
        holder1.price.setText("￥"+tuijian.getList().get(position).getPrice());
        String[] split = tuijian.getList().get(position).getImages().split("\\|");

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
        return tuijian.getList().size();
    }

    class TJHolder extends RecyclerView.ViewHolder{

        private    TextView title;
        public   TextView price;
        public   ImageView image2;

        public TJHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title2);
            price = itemView.findViewById(R.id.price);
            image2 = itemView.findViewById(R.id.image2);


        }
    }
    public void SetOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
