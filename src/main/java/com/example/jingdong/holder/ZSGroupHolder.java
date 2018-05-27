package com.example.jingdong.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jingdong.R;


/**
 * Created by huoxuebin on 2018/4/20.
 */

public class ZSGroupHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView title;
    public TextView price;
    public ZSGroupHolder(View itemView) {
        super(itemView);


        imageView = itemView.findViewById(R.id.gropu_img);
        title = itemView.findViewById(R.id.group_title);

        price = itemView.findViewById(R.id.group_price);



    }
}
