package com.example.jingdong.dingdanfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.jingdong.R;
import com.example.jingdong.adapter.MyAdapter;
import com.example.jingdong.dingdanbean.DataChangeBean;
import com.example.jingdong.dingdanbean.DataDataBean;
import com.example.jingdong.presenter.dingdan.MyPresenter;
import com.example.jingdong.presenter.dingdan.PresenterChange;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.List;


public class FragmentOne extends Fragment implements MyPresenter.PresenterListeren, PresenterChange.PresenterChangeListener {

    private RecyclerView recyclerView;
    private SpringView springView;
    private int num=1;
    private MyAdapter myAdapter;
    private MyPresenter myPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item1,container,false);
        recyclerView = view.findViewById(R.id.recyclerView01);
        springView = view.findViewById(R.id.springView);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        myPresenter = new MyPresenter();
        myPresenter.getData("10190","1",this);



        springView.setHeader(new DefaultHeader(getActivity()));
        springView.setFooter(new DefaultFooter(getActivity()));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                num=1;
                String s = String.valueOf(num);
                myPresenter.getData("10190",s,FragmentOne.this);
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onLoadmore() {
                num++;
                String s = String.valueOf(num);
                myPresenter.getData("10190",s,FragmentOne.this);
                springView.onFinishFreshAndLoad();

            }
        });
    }

    @Override
    public void success(List<DataDataBean.DataBean> data) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myAdapter = new MyAdapter(getActivity(),data);

        myAdapter.setChangeStatus(new MyAdapter.SetStatus() {
            @Override
            public void getStatus(String status, String id) {
                PresenterChange presenterChange = new PresenterChange(FragmentOne.this);
                presenterChange.getData(status,id);
            }
        });
        recyclerView.setAdapter(myAdapter);

    }

    @Override
    public void failed() {

    }

    @Override
    public void mySuccess(DataChangeBean dataChangeBean) {
        myPresenter.getData("10190","1",FragmentOne.this);

        if(dataChangeBean.getCode().equals("0")){
                 Toast.makeText(getActivity(),dataChangeBean.getMsg()+"",Toast.LENGTH_SHORT).show();
             }
    }
}
