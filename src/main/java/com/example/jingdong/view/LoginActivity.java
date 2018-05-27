package com.example.jingdong.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jingdong.R;

import com.example.jingdong.bean.LoginBean;

import com.example.jingdong.fragment.Fragment_my;
import com.example.jingdong.presenter.IM.IMloginPresenter;
import com.example.jingdong.presenter.LoginPresenter;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;


import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class LoginActivity extends AppCompatActivity implements IMloginPresenter {


    @BindView(R.id.loginname)
    EditText loginname;
    @BindView(R.id.loginpass)
    EditText loginpass;
    @BindView(R.id.zhcue)
    TextView zhcue;
    @BindView(R.id.qq)
    ImageView qq;
    @BindView(R.id.weixin)
    ImageView weixin;
    @BindView(R.id.fanhui1)
    ImageView fanhui1;
    private SharedPreferences.Editor edit;
    String url="https://www.baidu.com";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        loginname.setText(name);

        SharedPreferences text = getSharedPreferences("test", MODE_PRIVATE);
        edit = text.edit();
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMShareAPI mShareAPI = UMShareAPI.get(LoginActivity.this);

                mShareAPI.getPlatformInfo(LoginActivity.this, SHARE_MEDIA.QQ, umAuthListener);

            }
        });

    }




    //登录
    public void login(View view) {

        LoginPresenter loginPresenter = new LoginPresenter(this);
        String sname = loginname.getText().toString();
        String spass = loginpass.getText().toString();


        loginPresenter.getdatalogin("user/login", sname, spass);
    }

    //接收回传数据
    @Override
    public void success(LoginBean loginBean) {

        String msg = loginBean.getMsg();

        String code = loginBean.getCode();


        String s = loginname.getText().toString();

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        if ("0".equals(code)) {
            edit.putString("name1", s);
            edit.commit();
            finish();
        }

    }

    @OnClick({R.id.fanhui1, R.id.zhcue, R.id.qq, R.id.weixin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui1:
                finish();
                break;
            case R.id.zhcue:
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
                break;
            case R.id.qq:


                break;
            case R.id.weixin:
                break;
        }
    }


    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }



    UMAuthListener umAuthListener = new UMAuthListener() {

        @Override

        public void onStart(SHARE_MEDIA share_media) {

            Log.e("onStart", "onStart"); }

//授权成功了。map里面就封装了一些qq信息

        @Override

        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

         /*   tv.setText(map.get("name"));//QQ名字

            Glide.with(L.this).load(map.get("iconurl")).into(iv);//QQ头像*/

            String uid= map.get("uid");

            String openid = map.get("openid");//微博没有

            String unionid = map.get("unionid");//微博没有

            String access_token = map.get("access_token");

            String refresh_token = map.get("refresh_token");//微信,qq,微博都没有获取到

            String expires_in = map.get("expires_in");

            String name = map.get("name");

            String gender = map.get("gender");//性别

            String iconurl = map.get("iconurl");//头像

            Toast.makeText(getApplicationContext(), "name=" + name + ",gender=" + gender, Toast.LENGTH_SHORT).show();


            //传递扣扣信息
            edit.putString("qqname",name);
            edit.putString("qqtouxiang",iconurl);
            edit.commit();
            finish();

        }

//授权失败

        @Override public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            Log.e("onError", "onError");

        }


        @Override public void onCancel(SHARE_MEDIA share_media, int i) {

            Log.e("onCancel", "onCancel");

        }

    };





}
