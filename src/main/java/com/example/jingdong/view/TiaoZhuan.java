package com.example.jingdong.view;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jingdong.R;

public class TiaoZhuan extends AppCompatActivity {

    int time=4;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                time--;
                if(time>1){
                    handler.sendEmptyMessageDelayed(0,1000);
                    tim.setText("剩余时间"+time+"秒");
                }
                else {
                    Intent intent = new Intent(TiaoZhuan.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    };
    private TextView tim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiao_zhuan);
        tim = findViewById(R.id.timer);


        handler.sendEmptyMessageDelayed(0,1000);






    }
}
