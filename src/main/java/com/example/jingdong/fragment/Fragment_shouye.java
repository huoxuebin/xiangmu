package com.example.jingdong.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingdong.R;
import com.example.jingdong.adapter.SyAdapter;
import com.example.jingdong.bean.JGGBean;
import com.example.jingdong.bean.TuiBean;
import com.example.jingdong.utils.BaseObserver;
import com.example.jingdong.utils.RetrofitManager;
import com.example.jingdong.view.SerachActivity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Fragment_shouye extends Fragment {
    @BindView(R.id.recycler)
    XRecyclerView recycler;
    Unbinder unbinder;
    @BindView(R.id.sousuo)
    TextView sousuo;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    @BindView(R.id.f1_erweima)
    LinearLayout f1Erweima;
    Handler handler=new Handler();
    private List<JGGBean.DataBean> user;
    private int mDistanceY;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_shouye, container, false);


        unbinder = ButterKnife.bind(this, view);
        ImageView erweim = view.findViewById(R.id.erweima);

        erweim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        lunbo();

        recycler.setLoadingListener(new XRecyclerView.LoadingListener() {


            LinearLayout fg=  view.findViewById(R.id.fg);
            @Override
            public void onRefresh() {
                fg = view.findViewById(R.id.fg);
                fg.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fg.setVisibility(View.VISIBLE);
                        recycler.refreshComplete();
                    }
                },1000);
            }

            @Override
            public void onLoadMore() {

            }
        });


        return view;


    }


    //轮播图
    public void lunbo() {


        Map<String, String> map = new HashMap<>();
        map.put("source", "android");
        RetrofitManager.post("ad/getAd", map, new BaseObserver<TuiBean>() {
            @Override
            public void success(TuiBean tuiBean) {

                List<TuiBean.DataBean> data = tuiBean.getData();


                recycler.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.VERTICAL, false));
                SyAdapter syAdapter = new SyAdapter(data, getContext());
                recycler.setAdapter(syAdapter);

            }

            @Override
            public void failure(int code) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.sousuo, R.id.linearlayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sousuo:
                Intent intent = new Intent(getActivity(), SerachActivity.class);
                startActivity(intent);
                break;
            case R.id.linearlayout:


                break;
        }
    }
}
