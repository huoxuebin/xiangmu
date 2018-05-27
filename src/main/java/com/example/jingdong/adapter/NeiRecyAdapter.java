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
import com.example.jingdong.bean.JGGBean;
import com.example.jingdong.jiekou.OnItemClickListener;

import java.util.List;

public class NeiRecyAdapter extends RecyclerView.Adapter{

    List<JGGBean.DataBean> user;;
    Context context;
    private OnItemClickListener onItemClickListener;

    public NeiRecyAdapter(List<JGGBean.DataBean> user, Context context) {
        this.user = user;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LinearLayout.inflate(context, R.layout.jiuitem, null);



        Text text = new Text(view);
        return text;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {



        Text text =(Text) holder;
        Glide.with(context).load(user.get(position).getIcon()).into(text.imagejiu);

        text.jiutitle.setText(user.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    class Text extends RecyclerView.ViewHolder{


        public   ImageView imagejiu;
        public   TextView jiutitle;

        public Text(View itemView) {
            super(itemView);
            imagejiu = itemView.findViewById(R.id.jiuimage);
            jiutitle = itemView.findViewById(R.id.jiutitle);

        }
    }
    public void SetOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
