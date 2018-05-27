package com.example.jingdong.presenter;


import com.example.jingdong.bean.LoginBean;
import com.example.jingdong.model.IM.IMloginModel;
import com.example.jingdong.model.LoginModel;
import com.example.jingdong.presenter.IM.IMloginPresenter;
import com.example.jingdong.view.LoginActivity;

/**
 * Created by huoxuebin on 2018/5/8.
 */

public class LoginPresenter implements IMloginModel {


    private  LoginModel loginModel;
    private  IMloginPresenter iMloginPresenter;

    public LoginPresenter(IMloginPresenter iMloginPresenter) {
        this.iMloginPresenter=iMloginPresenter;
        loginModel = new LoginModel(this);
    }
    public void getdatalogin(String s, String sname, String spass) {
        loginModel.getdatalogin(s,sname,spass);
    }

    @Override
    public void success(LoginBean loginBean) {
        iMloginPresenter.success(loginBean);

    }
}
