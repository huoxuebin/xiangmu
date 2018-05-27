package com.example.jingdong.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jingdong.R;


/**
 * Created by huoxuebin on 2018/4/24.
 */

public class NeiHolder extends RecyclerView.ViewHolder {

    public ImageView neiimg;
    public TextView neiname;

    public NeiHolder(View itemView) {
        super(itemView);

        neiimg = itemView.findViewById(R.id.neicengimg);
        neiname = itemView.findViewById(R.id.neicengname);



    }
}
