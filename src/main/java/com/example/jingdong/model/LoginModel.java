package com.example.jingdong.model;


import com.example.jingdong.bean.LoginBean;
import com.example.jingdong.model.IM.IMloginModel;
import com.example.jingdong.presenter.LoginPresenter;
import com.example.jingdong.utils.BaseObserver;
import com.example.jingdong.utils.RetrofitManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoxuebin on 2018/5/8.
 */

public class LoginModel {


    private  IMloginModel iMloginModel;

    public LoginModel(IMloginModel iMloginModel) {
       this.iMloginModel=iMloginModel;

    }


    public void getdatalogin(String s, String sname, String spass) {

        Map<String,String> map  = new HashMap<>();
        map.put("mobile",sname);
        map.put("password",spass);
        RetrofitManager.post(s, map, new BaseObserver<LoginBean>() {
            @Override
            public void success(LoginBean loginBean) {
                iMloginModel.success(loginBean);
            }

            @Override
            public void failure(int code) {

            }
        });

    }
}
