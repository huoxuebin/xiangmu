package com.example.jingdong.presenter;

import com.example.jingdong.bean.ShopBean;
import com.example.jingdong.model.IM.IMShopPresenter;
import com.example.jingdong.model.ShopModel;
import com.example.jingdong.presenter.IM.IMshopview;


/**
 * Created by huoxuebin on 2018/4/25.
 */

public class ShopPresenter implements IMShopPresenter {

    private ShopModel shopModel;
    private IMshopview iMshopview;

    public ShopPresenter(IMshopview iMshopview) {
        this.iMshopview=iMshopview;
        shopModel = new ShopModel(this);


    }

    public void getshop(String s, int uid) {
        shopModel.getshop(s,uid);

    }

    @Override
    public void shop(ShopBean shopBean) {
        iMshopview.shop(shopBean);

    }
}
