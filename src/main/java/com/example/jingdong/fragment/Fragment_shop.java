package com.example.jingdong.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingdong.R;
import com.example.jingdong.adapter.ShopAdapter;
import com.example.jingdong.bean.CountPriceBean;
import com.example.jingdong.bean.ShopBean;
import com.example.jingdong.erji.ErMyliebiao;
import com.example.jingdong.presenter.IM.IMshopview;
import com.example.jingdong.presenter.ShopPresenter;
import com.example.jingdong.view.DingDanActivity;
import com.example.jingdong.view.DituActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Fragment_shop extends Fragment implements IMshopview, View.OnClickListener {
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                CountPriceBean countPriceBean = (CountPriceBean) msg.obj;
                priceString = countPriceBean.getPriceString();

                //设置价格和数量
                text_total.setText("合计:¥" + priceString);
                text_buy.setText("去结算(" + countPriceBean.getCount() + ")");



                text_buy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(getActivity(), DingDanActivity.class);
                        startActivity(intent);





                    }
                });
            }
        }
    };


    private int uid = 10190;

    private LinearLayout linearLayout;
    private CheckBox check_all;
    private TextView text_total;
    private TextView text_buy;
    private RelativeLayout relative_progress;

    private CountPriceBean countPriceBean;
    private ShopPresenter presenter;
    private List<ShopBean.DataBean.ListBean> listBeans;
    private ShopAdapter shopAdapter;
    private ErMyliebiao erMyliebiao;
    private String priceString;
    private SharedPreferences test;

    private LinearLayout aaa;
    private TextView d;
    private ImageView dwei;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);

        erMyliebiao = view.findViewById(R.id.expanable_list_view);
        check_all = view.findViewById(R.id.check_all);
        text_total = view.findViewById(R.id.text_total);
        text_buy = view.findViewById(R.id.text_buy);
        relative_progress = view.findViewById(R.id.relative_progress);
        linearLayout = view.findViewById(R.id.linear_bottom);
        aaa = view.findViewById(R.id.gouwuche);
        dwei = view.findViewById(R.id.dingwei);

        aaa.setVisibility(View.GONE);


        //跳转到地图
        dwei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DituActivity.class));
            }
        });


        //接收登录状态
        test = getActivity().getSharedPreferences("test", Context.MODE_PRIVATE);
        //实例化对象
        presenter = new ShopPresenter(this);


        Map<String, String> map = new HashMap<>();
        presenter.getshop("product/getCarts", uid);

        check_all.setOnClickListener(this);

        return view;
    }


    //判断登录状态
    @Override
    public void onResume() {
        super.onResume();
        //显示进度条
        String name1 = test.getString("name1", null);
        String qqname = test.getString("qqname", null);

        if(name1=="登录/注册"){
            aaa.setVisibility(View.VISIBLE);
            erMyliebiao.setVisibility(View.GONE);
        }else if(name1==null){
            aaa.setVisibility(View.VISIBLE);
            erMyliebiao.setVisibility(View.GONE);
        }
        else if(qqname!=null){
            aaa.setVisibility(View.GONE);
            erMyliebiao.setVisibility(View.VISIBLE);
        }
        else {
            aaa.setVisibility(View.GONE);
            erMyliebiao.setVisibility(View.VISIBLE);
        }

        relative_progress.setVisibility(View.VISIBLE);
        presenter.getshop("product/getCarts",uid);
    }

    //接收数据
    @Override
    public void shop(ShopBean shopBean) {

        //获取数据成功...隐藏
        relative_progress.setVisibility(View.GONE);

        List<ShopBean.DataBean> data = shopBean.getData();
        if (shopBean != null) {
            //cBean再设置奢配器之前需不需要改变数据
            //1.在bean类中添加商家是否选中的字段....默认值的false,初始值是根据该组下面所有孩子的状态进行改变的
            for (int i = 0; i < shopBean.getData().size(); i++) {
                //当前组中所有孩子的数据

                listBeans = shopBean.getData().get(i).getList();
                //设置组的初始选中状态,,,,根据所有孩子的状态
                shopBean.getData().get(i).setGroup_check(isAllChildInGroupChecked(listBeans));
            }
            //2.根据所有商家选中的状态,改变全选的状态
            check_all.setChecked(isAllGroupChecked(shopBean));
            //设置适配器
            //   myAdapter = new MyAdapter(MainActivity.this, cartBean,handler,relative_progress,mainPresenter);
            shopAdapter = new ShopAdapter(getActivity(), shopBean, handler, relative_progress, presenter);

            erMyliebiao.setAdapter(shopAdapter);

            //展开所有的组...expanableListView.expandGroup()
            for (int i = 0; i < shopBean.getData().size(); i++) {
                erMyliebiao.expandGroup(i);
            }
            //3.计算总价和商品的数量
            shopAdapter.sendPriceAndCount();

        } else {
            Toast.makeText(getActivity(), "购物车空,请添加购物车", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 所有的商家是否选中
     *
     * @param cartBean
     * @return
     */
    private boolean isAllGroupChecked(ShopBean cartBean) {

        for (int i = 0; i < cartBean.getData().size(); i++) {
            //只要有一个组未选中 返回false
            if (!cartBean.getData().get(i).isGroup_check()) {
                return false;
            }
        }
        return true;
    }
    /**
     * 当前组中所有的孩子是否选中
     *
     * @param listBeans 当前组中所有的孩子的数据
     * @return
     */
    private boolean isAllChildInGroupChecked(List<ShopBean.DataBean.ListBean> listBeans) {

        for (int i = 0; i < listBeans.size(); i++) {
            if (listBeans.get(i).getSelected() == 0) {
                return false;
            }
        }

        return true;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.check_all:
                //根据全选的状态更新所有商品的状态...check_all.isChecked() true...1;;;;false---0
                shopAdapter.setAllChildsChecked(check_all.isChecked());

                break;
        }
    }


}


