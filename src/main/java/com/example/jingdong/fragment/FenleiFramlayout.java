package com.example.jingdong.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jingdong.R;
import com.example.jingdong.adapter.FenleiwaicengAdapter;
import com.example.jingdong.bean.FenleiBean;
import com.example.jingdong.utils.BaseObserver;
import com.example.jingdong.utils.RetrofitManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FenleiFramlayout extends Fragment {

    @BindView(R.id.recy1)
    RecyclerView recy1;
    Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fenleiframlayout, container, false);
        //接收数据
        int cid = getArguments().getInt("cid");

        Log.d("TAG","ABC"+cid);


        

            //获取数据
            Map<String, String> params = new HashMap<>();
            params.put("cid", String.valueOf(cid));
            RetrofitManager.post("product/getProductCatagory", params, new BaseObserver<FenleiBean>() {


                private List<FenleiBean.DataBean> data;

                @Override
                public void success(FenleiBean fenleiBean) {
                    data = fenleiBean.getData();


                    recy1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
                    FenleiwaicengAdapter fenleiwaicengAdapter = new FenleiwaicengAdapter(data, getContext());
                    recy1.setAdapter(fenleiwaicengAdapter);


                }

                @Override
                public void failure(int code) {

                }

            });


        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
