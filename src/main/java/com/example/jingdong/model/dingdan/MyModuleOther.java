package com.example.jingdong.model.dingdan;


import com.example.jingdong.utils.OkHttp3Util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class MyModuleOther {

    public void getData(String uid,String page,String status, final ModuleListeren moduleListeren){

        Map<String,String> map = new HashMap<>();
        map.put("uid",uid);
        map.put("page",page);
        map.put("status",status);
        map.put("source","android");

        OkHttp3Util.doPost("https://www.zhaoapi.cn/product/getOrders", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if(moduleListeren !=null){
                    moduleListeren.failed(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                if(moduleListeren!=null){
                    moduleListeren.success(string);
                }
            }
        });
    }
   public interface ModuleListeren{
        void success(String s);
        void failed(Exception e);
   }
}
