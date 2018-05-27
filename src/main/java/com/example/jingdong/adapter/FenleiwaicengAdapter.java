package com.example.jingdong.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingdong.R;
import com.example.jingdong.bean.FenleiBean;
import com.example.jingdong.holder.FenleiwaiHolder;


import java.util.List;

/**
 * Created by huoxuebin on 2018/4/24.
 */

public class FenleiwaicengAdapter extends RecyclerView.Adapter<FenleiwaiHolder> {

    List<FenleiBean.DataBean> data;
    Context context;


    public FenleiwaicengAdapter(List<FenleiBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public FenleiwaiHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.fenleiwaiceng, null);
        FenleiwaiHolder fenleiwaiHolder = new FenleiwaiHolder(view);

        return fenleiwaiHolder;
    }

    @Override
    public void onBindViewHolder(FenleiwaiHolder holder, int position) {
        List<FenleiBean.DataBean.ListBean> list = data.get(position).getList();


        holder.wainame.setText(list.get(position).getName());

        holder.recyclerView.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
        holder.recyclerView.setAdapter(new FenleineicengAdapter(list, context));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
