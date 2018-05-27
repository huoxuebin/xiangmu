package com.example.jingdong.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.jingdong.R;
import com.example.jingdong.bean.JoinBean;
import com.example.jingdong.presenter.IM.IMJoinPresenter;
import com.example.jingdong.presenter.JoinPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class JoinActivity extends AppCompatActivity implements IMJoinPresenter {


    @BindView(R.id.fanhui2)
    ImageView fanhui2;
    @BindView(R.id.registername)
    EditText registername;
    @BindView(R.id.registerpass)
    EditText registerpass;
    private String regname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi_ster);
        ButterKnife.bind(this);


    }



    //注册
    public void register(View view) {
        JoinPresenter joinPresenter = new JoinPresenter(this);
        regname = registername.getText().toString();
        String regpass = registerpass.getText().toString();


        joinPresenter.getdatajoin("user/reg", regname, regpass);

    }

    //接收回传数据
    @Override
    public void getjoin(JoinBean joinBean) {
        String msg = joinBean.getMsg();

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        if (msg.equals("注册成功")) {
            Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
            intent.putExtra("name", regname);
            startActivity(intent);

            finish();
        }

    }

    @OnClick(R.id.fanhui2)
    public void onViewClicked() {
        finish();
    }
}
