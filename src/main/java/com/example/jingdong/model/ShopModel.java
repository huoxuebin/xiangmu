package com.example.jingdong.model;

import com.example.jingdong.bean.ShopBean;
import com.example.jingdong.model.IM.IMShopPresenter;
import com.example.jingdong.utils.BaseObserver;
import com.example.jingdong.utils.RetrofitManager;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoxuebin on 2018/4/25.
 */

public class ShopModel {
    private IMShopPresenter imShopPresenter;

    public ShopModel(IMShopPresenter imShopPresenter) {
        this.imShopPresenter=imShopPresenter;

    }


    public void getshop(final String s, int uid) {

        Map<String,String> map = new HashMap<>();

        map.put("uid", String.valueOf(uid));
        map.put("source","android");
        RetrofitManager.post(s, map, new BaseObserver<ShopBean>() {
            @Override
            public void success(ShopBean shopBean) {
                imShopPresenter.shop(shopBean);


            }

            @Override
            public void failure(int code) {

            }
        });
    }
}

