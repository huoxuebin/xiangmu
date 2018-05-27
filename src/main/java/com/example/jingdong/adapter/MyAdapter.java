package com.example.jingdong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jingdong.R;
import com.example.jingdong.dingdanbean.DataDataBean;

import java.util.List;





public class MyAdapter extends RecyclerView.Adapter{
    Context context;
    List<DataDataBean.DataBean> data;
    public MyAdapter(Context context, List<DataDataBean.DataBean> data) {
       this.context = context;
       this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.fragmentone_item,null);

        return new ViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        ViewHolder1 holder1 = (ViewHolder1) holder;
        holder1.time.setText("创建时间:"+data.get(position).getCreatetime());
        holder1.title.setText(data.get(position).getTitle());
        holder1.price.setText("价格:"+data.get(position).getPrice()+"");
        if(data.get(position).getStatus() ==0){

            holder1.textView.setTextColor(Color.RED);
            holder1.btn.setText("取消订单");
            holder1.textView.setText("待支付");
        }else if(data.get(position).getStatus()==1){
            holder1.btn.setText("查看订单");
            holder1.textView.setText("已支付");
        }else{
            holder1.btn.setText("已取消");
            holder1.textView.setText("查看订单");

        }
        //点击事件,接口回调

        holder1.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(setStatus!=null){
                     setStatus.getStatus(data.get(position).getStatus()+"",data.get(position).getOrderid()+"");
                     notifyDataSetChanged();
                 }
            }
        });

     }
      SetStatus setStatus;
    public void setChangeStatus(SetStatus setStatus){
        this.setStatus = setStatus;
    }
     //点击接口回调
     public interface SetStatus{
        void getStatus(String status, String id);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder1 extends RecyclerView.ViewHolder {
         TextView title;
         TextView price;
         Button btn;
         TextView textView;
        TextView time;
        public ViewHolder1(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.timeGreate);
            title = itemView.findViewById(R.id.titleIt);
            price = itemView.findViewById(R.id.price);
            btn = itemView.findViewById(R.id.btn);
            textView = itemView.findViewById(R.id.textview);
        }
    }
}
