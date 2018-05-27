package com.example.jingdong.presenter;


import com.example.jingdong.bean.JoinBean;
import com.example.jingdong.model.IM.IMJoinModel;
import com.example.jingdong.model.JoinModel;
import com.example.jingdong.presenter.IM.IMJoinPresenter;
import com.example.jingdong.view.JoinActivity;

/**
 * Created by huoxuebin on 2018/5/8.
 */

public class JoinPresenter implements IMJoinModel {


    private  JoinModel joinModel;
    private  IMJoinPresenter imJoinPresenter;

    public JoinPresenter(IMJoinPresenter imJoinPresenter) {
        joinModel = new JoinModel(this);
        this.imJoinPresenter=imJoinPresenter;
    }

    public void getdatajoin(String s, String regname, String regpass) {
        joinModel.getdatajoin(s,regname,regpass);


    }

    @Override
    public void getjoin(JoinBean joinBean) {
        imJoinPresenter.getjoin(joinBean);

    }
}
