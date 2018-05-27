package com.example.jingdong.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jingdong.R;


/**
 * Created by huoxuebin on 2018/4/23.
 */

public class ZSHolder extends RecyclerView.ViewHolder {

    public ImageView zsimg;
    public TextView zstitle;
    public TextView zsprice;

    public ZSHolder(View itemView) {
        super(itemView);

        zsimg = itemView.findViewById(R.id.zhanshiimg);
        zstitle = itemView.findViewById(R.id.zhanshititle);
        zsprice = itemView.findViewById(R.id.zhanshiprice);


    }
}
