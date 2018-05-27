package com.example.jingdong.model;


import com.example.jingdong.bean.JoinBean;
import com.example.jingdong.bean.LoginBean;
import com.example.jingdong.model.IM.IMJoinModel;
import com.example.jingdong.presenter.JoinPresenter;
import com.example.jingdong.utils.BaseObserver;
import com.example.jingdong.utils.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoxuebin on 2018/5/8.
 */

public class JoinModel {


    private  IMJoinModel imJoinModel;

    public JoinModel(IMJoinModel imJoinModel) {
        this.imJoinModel=imJoinModel;
    }


    public void getdatajoin(String s, String regname, String regpass) {


        Map<String,String> map  = new HashMap<>();
        map.put("mobile",regname);
        map.put("password",regpass);
        RetrofitManager.post(s, map, new BaseObserver<JoinBean>() {

            @Override
            public void success(JoinBean joinBean) {
                imJoinModel.getjoin(joinBean);
            }

            @Override
            public void failure(int code) {

            }
        });

    }
}
