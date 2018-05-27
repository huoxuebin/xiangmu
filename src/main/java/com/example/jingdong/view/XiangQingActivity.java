package com.example.jingdong.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingdong.Glideimage;
import com.example.jingdong.R;
import com.example.jingdong.bean.AddShopBean;
import com.example.jingdong.bean.XQBean;
import com.example.jingdong.utils.BaseObserver;
import com.example.jingdong.utils.RetrofitManager;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class XiangQingActivity extends AppCompatActivity {

    private String pid;
    private TextView xqprice;
    private TextView title;
    private Banner imageView;
    private TextView yhprice;
    private ImageView fanhui;
    private ArrayList<String> imageUrls;
    private ImageView fenxiang;
    String url="https://www.baidu.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qing);

        xqprice = findViewById(R.id.xqprice);
        title = findViewById(R.id.xqtitle);
        imageView = findViewById(R.id.xiangqingbanner);
        yhprice = findViewById(R.id.yhprice);
        fanhui = findViewById(R.id.back2);
        fenxiang = findViewById(R.id.fenxiang);

        fenxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UMImage image = new UMImage(XiangQingActivity.this, R.drawable.mqq);
                UMWeb web = new UMWeb(url);
                web.setTitle("百度");//标题
                web.setThumb(image);
                web.setDescription("百度一下，你就知道");//描述


                new ShareAction(XiangQingActivity.this).withMedia(web) .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(shareListener).open();
            }
        });

        //接收pid
        final Intent intent = getIntent();
        pid = intent.getStringExtra("pid");

        //返回
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //跳转到放大页面
        imageView.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent1 = new Intent(XiangQingActivity.this,FangDangActivity.class);
                intent1.putStringArrayListExtra("list",(ArrayList<String>)imageUrls);
                startActivity(intent1);

            }
        });


        Log.i("++", "--" + pid);

        Map<String, String> map = new HashMap<>();
        map.put("pid", pid);
        map.put("source", "android");

        //请求数据
        RetrofitManager.post("product/getProductDetail", map, new BaseObserver<XQBean>() {
            @Override
            public void success(XQBean xqBean) {

                XQBean.DataBean data = xqBean.getData();
                String images = data.getImages();

                String[] split = images.split("\\|");


                imageUrls = new ArrayList<>();
                for (int i = 0;i<split.length;i++){
                    imageUrls.add(split[i]);
                }

                imageView.setImages(imageUrls);
                imageView.setDelayTime(1500);

                //设置banner样式...CIRCLE_INDICATOR_TITLE包含标题
                imageView.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
                //设置图片加载器
                imageView.setImageLoader(new Glideimage());
                //设置自动轮播，默认为true
                imageView.isAutoPlay(false);
                //设置轮播时间
                imageView.setDelayTime(2500);
                //设置指示器位置（当banner模式中有指示器时）
                imageView.setIndicatorGravity(BannerConfig.CENTER);
                imageView.start();

                //赋值
                double price = data.getPrice();
                xqprice.setText("￥" + price);
                title.setText(data.getTitle());
                yhprice.setText("优惠价￥"+data.getBargainPrice());



            }

            @Override
            public void failure(int code) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    //加入购物车
    public void addshop(View view) {

        Map<String,String> map = new HashMap<>();
        map.put("uid","10190");
        map.put("pid",pid);
        map.put("source","android");

        RetrofitManager.post("product/addCart", map, new BaseObserver<AddShopBean>() {
            @Override
            public void success(AddShopBean addShopBean) {
                String msg = addShopBean.getMsg();
                Toast.makeText(XiangQingActivity.this,msg,Toast.LENGTH_LONG).show();

            }

            @Override
            public void failure(int code) {

            }
        });
    }


    public void weizhi(View view) {
        startActivity(new Intent(XiangQingActivity.this,DituActivity.class));

    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
        }
        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(XiangQingActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(XiangQingActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }
        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(XiangQingActivity.this,"取消了",Toast.LENGTH_LONG).show();
        }
    };


}
