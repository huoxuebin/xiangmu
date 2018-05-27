package com.example.jingdong.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jingdong.Glideimage;
import com.example.jingdong.R;
import com.example.jingdong.bean.JGGBean;
import com.example.jingdong.bean.TuiBean;
import com.example.jingdong.jiekou.OnItemClickListener;
import com.example.jingdong.utils.BaseObserver;
import com.example.jingdong.utils.RetrofitManager;
import com.example.jingdong.view.XiangQingActivity;
import com.example.jingdong.view.ZhanshiActivity;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerClickListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyAdapter extends RecyclerView.Adapter {




    List<TuiBean.DataBean> data;

    Context context;
    private OnItemClickListener onItemClickListener;

    public SyAdapter(List<TuiBean.DataBean> data, Context context) {
        this.data = data;
        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case 0:
                view = View.inflate(context, R.layout.banneraa,null);
                 holder = new BannerHolder(view);
                break;
            case 1:
                view = View.inflate(context,R.layout.jiugongge,null);
                holder = new JiuHolder(view);
                break;
            case 2:
                view = View.inflate(context, R.layout.miaosha,null);
                holder = new MiaoshaHolder(view);
                break;
            case 3:
                view = View.inflate(context,R.layout.recytuijian,null);
                holder = new TJHolder(view);
                break;
        }


        return holder;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        switch (getItemViewType(position)){
            case 0:
                BannerHolder bannerHolder =(BannerHolder)holder;

                bannerHolder.setData();
                break;

            case 1:
                    final JiuHolder jiuHolder =(JiuHolder)holder;
                    final Map<String,String> map = new HashMap<>();
                    map.put("source","android");
                    RetrofitManager.post("product/getCatagory", map, new BaseObserver<JGGBean>() {
                   @Override
                   public void success(JGGBean jggBean) {
                       final List<JGGBean.DataBean> user = jggBean.getData();
                       jiuHolder.jiugongge.setLayoutManager(new GridLayoutManager(context,2,OrientationHelper.HORIZONTAL,false));
                       NeiRecyAdapter neiRecyAdapter = new NeiRecyAdapter(user,context);
                       jiuHolder.jiugongge.setAdapter(neiRecyAdapter);

                       neiRecyAdapter.SetOnItemClickListener(new OnItemClickListener() {
                           @Override
                           public void onItemClick(int position) {
                               Intent intent = new Intent(context, ZhanshiActivity.class);
                               String name = user.get(position).getName();
                               intent.putExtra("name",name);
                               context.startActivity(intent);


                           }
                       });
                }
            @Override
            public void failure(int code) {
            }
        });
                break;
            case 2:

                final MiaoshaHolder miaoshaHolder  =(MiaoshaHolder) holder;

                List<String> info = new ArrayList<>();
                info.add("欢迎访问京东app");
                info.add("赶紧的好好学习吧 马上毕业了");
                info.add("超级大片,全新上市");
                miaoshaHolder.marqueeView.startWithList(info);

                Map<String,String> map1 = new HashMap<>();
                map1.put("source","android");
                RetrofitManager.post("ad/getAd", map1, new BaseObserver<TuiBean>() {
                    @Override
                    public void success(TuiBean tuiBean) {

                        final TuiBean.MiaoshaBean miaosha = tuiBean.getMiaosha();


                        miaoshaHolder.miaorecy.setLayoutManager(new LinearLayoutManager(context,OrientationHelper.HORIZONTAL,false));
                        MSadapter syAdapter = new MSadapter(miaosha,context);
                        miaoshaHolder.miaorecy.setAdapter(syAdapter);

                        syAdapter.SetOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {

                                Intent intent = new Intent(context, XiangQingActivity.class);
                                int pid = miaosha.getList().get(position).getPid();

                                intent.putExtra("pid",String.valueOf(pid));

                                context.startActivity(intent);
                            }
                        });

                    }
                    @Override
                    public void failure(int code) {
                    }
                });
                break;
            case 3:
                final TJHolder tjHolder = (TJHolder) holder;
                Map<String,String> map2 = new HashMap<>();
                map2.put("source","android");
                RetrofitManager.post("ad/getAd", map2, new BaseObserver<TuiBean>() {
                    @Override
                    public void success(final TuiBean tuiBean) {
                        final TuiBean.TuijianBean tuijian = tuiBean.getTuijian();

                        tjHolder.recyclerView.setLayoutManager(new GridLayoutManager(context,2,OrientationHelper.VERTICAL,false));
                        TJadapter tJadapter = new TJadapter(tuijian,context);
                        tjHolder.recyclerView.setAdapter(tJadapter);
                        tJadapter.SetOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(context, XiangQingActivity.class);
                                int pid = tuijian.getList().get(position).getPid();
                                intent.putExtra("pid",String.valueOf(pid));

                                context.startActivity(intent);
                            }
                        });
                    }
                    @Override
                    public void failure(int code) {
                    }
                });
                break;
        }
    }
    @Override
    public int getItemCount() {
        return 4;
    }
    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
        }
        return super.getItemViewType(position);
    }
    //轮播图
    class BannerHolder extends RecyclerView.ViewHolder{
        public   Banner banner;
        public BannerHolder(View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.banneraa);
        }
        //轮播图
        public void setData() {
            //设置Banner的数据
            //得到图片地址的集合
            List<String> imageUrls=new ArrayList<>();
            for (int i=0;i<data.size();i++){
                String image=data.get(i).getIcon();
                imageUrls.add(image);
            }
       banner.setImages(imageUrls).setImageLoader(new Glideimage()).start();
/*            banner.setDelayTime(1500);
            banner.setIndicatorGravity(BannerConfig.CENTER);*/

            //设置item的点击事件
        banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    //注意这里的position是从1开始的
                    Toast.makeText(context, "position=="+position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    //九宫格
    class JiuHolder extends RecyclerView.ViewHolder{

        public   RecyclerView jiugongge;

        public JiuHolder(View itemView) {
            super(itemView);
            jiugongge = itemView.findViewById(R.id.jiugong);
        }
    }
    //秒杀
    class MiaoshaHolder extends RecyclerView.ViewHolder{
        public  RecyclerView miaorecy;
        public   MarqueeView marqueeView;

        public MiaoshaHolder(View itemView) {
            super(itemView);
            miaorecy = itemView.findViewById(R.id.msrecy);
            marqueeView = itemView.findViewById(R.id.marqueeView);
        }
    }
    //为您推荐
    class TJHolder extends RecyclerView.ViewHolder{

        public   RecyclerView recyclerView;
        public TJHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.tuirecy);
        }
    }
    public void SetOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}

