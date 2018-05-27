package com.example.jingdong.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jingdong.ImageUtils;
import com.example.jingdong.R;
import com.example.jingdong.adapter.TJadapter;
import com.example.jingdong.bean.TuiBean;
import com.example.jingdong.jiekou.OnItemClickListener;
import com.example.jingdong.utils.BaseObserver;
import com.example.jingdong.utils.RetrofitManager;
import com.example.jingdong.view.DingDanActivity;
import com.example.jingdong.view.LoginActivity;
import com.example.jingdong.view.NewsActivity;
import com.example.jingdong.view.SheZhiActivity;
import com.example.jingdong.view.XiangQingActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Fragment_my extends Fragment {


    @BindView(R.id.touxiang)
    ImageView touxiang;
    @BindView(R.id.login_register)
    TextView loginRegister;
    Unbinder unbinder;
    @BindView(R.id.recy)
    RecyclerView recy;
    @BindView(R.id.shezhi)
    ImageView shezhi;
    @BindView(R.id.dingdan)
    LinearLayout dingdan;
    private SharedPreferences test;


    private String qqname;
    private String qqtouxiang;
    private String dname;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);


        unbinder = ButterKnife.bind(this, view);


        //接收手机号
        test = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);


        //点击设置跳转到切换模式界面
        shezhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SheZhiActivity.class);
                startActivity(intent);
            }
        });
        //点击我的订单跳转到订单
       dingdan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(getActivity(), DingDanActivity.class));
           }
       });

       //网络请求
        Okhttp();
        return view;

    }

    //登录判断
    @Override
    public void onResume() {
        super.onResume();
        //获取登陆手机号
        dname = test.getString("name1", null);

        Log.d("Tag","学彬"+ dname);

        //获取QQ名字
        qqname = test.getString("qqname", null);

        //获取Qq头像
        qqtouxiang = test.getString("qqtouxiang", null);
        Log.d("Tag","或"+ qqtouxiang);
        //接受登录数据如果不为空展示
        if(dname!=null){
            loginRegister.setText(dname);
        }
        //接受登录数据如果不为空展示
        else  if(qqname!=null || qqtouxiang !=null){
            loginRegister.setText(qqname);
            Glide.with(getActivity()).load(qqtouxiang).into(touxiang);
        }
        //退出登录后赋值
        else {
            loginRegister.setText("登录/注册");
            Glide.with(getActivity()).load(R.drawable.user).into(touxiang);
        }



    }

    //网络请求展示推荐
    public void Okhttp() {
        Map<String, String> map = new HashMap<>();
        map.put("source", "android");
        RetrofitManager.post("ad/getAd", map, new BaseObserver<TuiBean>() {
            @Override
            public void success(final TuiBean tuiBean) {
                final TuiBean.TuijianBean tuijian = tuiBean.getTuijian();

                recy.setLayoutManager(new GridLayoutManager(getActivity(), 2, OrientationHelper.VERTICAL, false));
                TJadapter tJadapter = new TJadapter(tuijian, getActivity());
                recy.setAdapter(tJadapter);
                tJadapter.SetOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Intent intent = new Intent(getActivity(), XiangQingActivity.class);
                        int pid = tuijian.getList().get(position).getPid();
                        intent.putExtra("pid", String.valueOf(pid));

                        startActivity(intent);
                    }
                });
            }

            @Override
            public void failure(int code) {

            }
        });
    }

    //点击事件
    @OnClick({R.id.touxiang, R.id.login_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.touxiang:
                break;
            case R.id.login_register:
                String s = loginRegister.getText().toString();

                //我的页面显示登录注册的时候跳转到登录页面
                if (s.equals("登录/注册")) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));

                }

                else {
                    //给信息页面传值
                    Intent intent = new Intent(getActivity(), NewsActivity.class);
                    //手机号
                    intent.putExtra("sname", dname);
                    //QQ昵称
                    intent.putExtra("qname",qqname);
                    //QQ头像
                    intent.putExtra("qtouxiang",qqtouxiang);
                    startActivity(intent);
                }
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
