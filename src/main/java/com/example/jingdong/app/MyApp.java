package com.example.jingdong.app;

import android.app.Application;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class MyApp extends Application {
    {
        //PlatformConfig.setWeixin("1105602574", "3baf1193c85774b3fd9d18447d76cab0");
        PlatformConfig.setQQZone("1106788439", "qwQLs9n3pNWxqFM4");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this,"5b01634fa40fa361f20000bb"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
    }
}