package com.example.jingdong.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jingdong.R;
import com.example.jingdong.bean.CountPriceBean;
import com.example.jingdong.bean.ShopBean;
import com.example.jingdong.jiekou.OnItemClickListener;
import com.example.jingdong.presenter.ShopPresenter;
import com.example.jingdong.utils.BaseObserver;
import com.example.jingdong.utils.RetrofitManager;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by huoxuebin on 2018/4/25.
 */

public class ShopAdapter extends BaseExpandableListAdapter {
    private ShopPresenter presenter;
    private int uid=10190;
    private int childIndex;
    private int allIndex;
    private Context context;
    private ShopBean shopBean;
    private Handler handler;
    private RelativeLayout relative_progress;
    private OnItemClickListener onItemClickListener;


    public ShopAdapter(Context context, ShopBean shopBean, Handler handler, RelativeLayout relative_progress, ShopPresenter presenter) {
        this.context = context;
        this.shopBean = shopBean;
        this.handler = handler;
        this.relative_progress = relative_progress;
        this.presenter = presenter;
    }


    @Override
    public int getGroupCount() {
        return shopBean.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return shopBean.getData().get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return shopBean.getData().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        return shopBean.getData().get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        final GroupHolder holder;
        if (view == null){
            view = View.inflate(context, R.layout.yijiliebiao,null);
            holder = new GroupHolder();

            holder.checkBox=view.findViewById(R.id.group_check);
            holder.textView= view.findViewById(R.id.group_text);

            view.setTag(holder);
        }else {
            holder = (GroupHolder) view.getTag();
        }

        final ShopBean.DataBean dataBean = shopBean.getData().get(groupPosition);

        //赋值
        holder.textView.setText(dataBean.getSellerName());
        holder.checkBox.setChecked(dataBean.isGroup_check());

        //商家的点击事件
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显示progress
                relative_progress.setVisibility(View.VISIBLE);

                //根据商家的状态holder.checkbox.ischecked(),改变下面所有子条目的状态,,,10,,改变十次,更新完成一个之后再去执行下一个...递归
                childIndex = 0;
                updateAllChildInGroup(dataBean,holder.checkBox.isChecked());

            }
        });


        return view;
    }

    /**
     * 根据商家状态使用递归改变所有的子条目
     * @param dataBean
     * @param checked
     */
    private void updateAllChildInGroup(final ShopBean.DataBean dataBean, final boolean checked) {

        ShopBean.DataBean.ListBean listBean = dataBean.getList().get(childIndex);

        Map<String, String> params = new HashMap<>();

        //?uid=71&sellerid=1&pid=1&selected=0&num=10
        params.put("uid","10190");
        params.put("sellerid", String.valueOf(listBean.getSellerid()));
        params.put("pid", String.valueOf(listBean.getPid()));

        params.put("selected", String.valueOf(checked ? 1:0));
        params.put("num", String.valueOf(listBean.getNum()));

        RetrofitManager.post("product/updateCarts", params, new BaseObserver<ShopBean>() {
            @Override
            public void success(ShopBean shopBean) {


                childIndex++;
                if (childIndex < dataBean.getList().size()) {
                    //再去更新下一条
                    updateAllChildInGroup(dataBean, checked);

                } else {//全都更新完成了....重新查询购物车

                    presenter.getshop("product/getCarts", uid);

                }
            }

            @Override
            public void failure(int code) {

            }
        });
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        ChildHolder holder;
        if (view == null){
            view = View.inflate(context, R.layout.erjiliebiao,null);
            holder = new ChildHolder();

            holder.checkBox = view.findViewById(R.id.child_check);
            holder.text_title = view.findViewById(R.id.child_title);
            holder.img = view.findViewById(R.id.child_image);
            holder.text_price = view.findViewById(R.id.child_price);
            holder.text_jian = view.findViewById(R.id.text_jian);
            holder.text_num = view.findViewById(R.id.text_num);
            holder.text_add = view.findViewById(R.id.text_add);
            holder.text_delete = view.findViewById(R.id.text_delete);

            view.setTag(holder);
        }else {
            holder = (ChildHolder) view.getTag();
        }

        final ShopBean.DataBean.ListBean listBean = shopBean.getData().get(groupPosition).getList().get(childPosition);

        //赋值
        holder.text_title.setText(listBean.getTitle());
        holder.text_price.setText("¥"+listBean.getBargainPrice());

        String[] strings = listBean.getImages().split("\\|");
        Glide.with(context).load(strings[0]).into(holder.img);

        holder.checkBox.setChecked(listBean.getSelected() == 0? false:true);//根据0,1进行设置是否选中
        //setText()我们使用一定是设置字符串
        holder.text_num.setText(listBean.getNum()+"");

        //点击事件
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此时需要显示进度条
                relative_progress.setVisibility(View.VISIBLE);
                //更新购物车,,,,需要改变是否选中,,,如果现在显示的是0,改成1;;;1--->0

                Map<String, String> params = new HashMap<>();

                //?uid=71&sellerid=1&pid=1&selected=0&num=10
                params.put("uid","10190");
                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                params.put("pid", String.valueOf(listBean.getPid()));

                params.put("selected", String.valueOf(listBean.getSelected() == 0? 1:0));
                params.put("num", String.valueOf(listBean.getNum()));

                RetrofitManager.post("product/updateCarts", params, new BaseObserver<ShopBean>() {
                    @Override
                    public void success(ShopBean shopBean) {

                        presenter.getshop("product/getCarts",uid);

                    }

                    @Override
                    public void failure(int code) {

                    }
                });

            }
        });

        //加号
        holder.text_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //此时需要显示进度条
                relative_progress.setVisibility(View.VISIBLE);
                //更新购物车,,,,需要改变是数量,,,,需要加1

                Map<String, String> params = new HashMap<>();

                //?uid=71&sellerid=1&pid=1&selected=0&num=10
                params.put("uid","10190");
                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                params.put("pid", String.valueOf(listBean.getPid()));
                params.put("selected", String.valueOf(listBean.getSelected()));

                params.put("num", String.valueOf(listBean.getNum()+1));

                RetrofitManager.post("product/updateCarts", params, new BaseObserver<ShopBean>() {
                    @Override
                    public void success(ShopBean shopBean) {


                        presenter.getshop("product/getCarts", uid);

                    }
                    @Override
                    public void failure(int code) {

                    }
                });

            }
        });
        //减号
        holder.text_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = listBean.getNum();

                if (num == 1){
                    Toast.makeText(context,"最小数量为1", Toast.LENGTH_SHORT).show();
                    return;
                }
                //此时需要显示进度条
                relative_progress.setVisibility(View.VISIBLE);
                //更新购物车,,,,需要改变是数量,,,,需要加1

                Map<String, String> params = new HashMap<>();

                //?uid=71&sellerid=1&pid=1&selected=0&num=10
                params.put("uid","10190");
                params.put("sellerid", String.valueOf(listBean.getSellerid()));
                params.put("pid", String.valueOf(listBean.getPid()));
                params.put("selected", String.valueOf(listBean.getSelected()));

                params.put("num", String.valueOf(num-1));

                RetrofitManager.post("product/updateCarts", params, new BaseObserver<ShopBean>() {
                    @Override
                    public void success(ShopBean shopBean) {


                        presenter.getshop("product/getCarts", uid);

                    }

                    @Override
                    public void failure(int code) {


                    }
                });

            }
        });
        //删除
        holder.text_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显示进度条
                relative_progress.setVisibility(View.VISIBLE);
                //调用删除的接口
                Map<String, String> params = new HashMap<>();

                //uid=72&pid=1
                params.put("uid", "10190");
                params.put("pid", String.valueOf(listBean.getPid()));


                RetrofitManager.post("product/deleteCart", params, new BaseObserver<ShopBean>() {
                    @Override
                    public void success(ShopBean shopBean) {


                        presenter.getshop("product/getCarts", uid);

                    }

                    @Override
                    public void failure(int code) {

                    }

                });
            }
        });



        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 计算总价和数量,,,并且发送给activity进行显示
     */
    public void sendPriceAndCount() {

        double price = 0;
        int count = 0;

        for (int i = 0;i<shopBean.getData().size();i++){
            List<ShopBean.DataBean.ListBean> listBeans = shopBean.getData().get(i).getList();
            for (int j = 0; j< listBeans.size(); j++){

                if (listBeans.get(j).getSelected() == 1){
                    price += listBeans.get(j).getBargainPrice() * listBeans.get(j).getNum();
                    count += listBeans.get(j).getNum();
                }
            }
        }
        //double高精度,,,计算的时候可能会出现一串数字...保留两位
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String priceString = decimalFormat.format(price);
        CountPriceBean countPriceBean = new CountPriceBean(priceString, count);

        //发送到主页面进行显示
        Message msg = Message.obtain();

        msg.what = 0;
        msg.obj = countPriceBean;
        handler.sendMessage(msg);
    }
    /**
     * 根据全选的状态更新所有商品的状态
     * @param checked
     */
    public void setAllChildsChecked(boolean checked) {

        //创建一个大的结合,,,存放所有商品的数据
        List<ShopBean.DataBean.ListBean> allList = new ArrayList<>();
        for (int i= 0;i<shopBean.getData().size();i++){
            List<ShopBean.DataBean.ListBean> listBeans = shopBean.getData().get(i).getList();
            for (int j=0;j<listBeans.size();j++){
                allList.add(listBeans.get(j));
            }
        }
        //显示progress
        relative_progress.setVisibility(View.VISIBLE);
        //递归更新....
        allIndex = 0;
        updateAllChecked(allList,checked);
    }
    /**
     * 更新所有的商品
     * @param allList
     * @param checked
     */
    private void updateAllChecked(final List<ShopBean.DataBean.ListBean> allList, final boolean checked) {

        ShopBean.DataBean.ListBean listBean = allList.get(allIndex);
//        ShopBean.DataBean.ListBean listBean = allList.get(allIndex);
        Map<String, String> params = new HashMap<>();
        //?uid=71&sellerid=1&pid=1&selected=0&num=10
        params.put("uid","10190");
        params.put("sellerid", String.valueOf(listBean.getSellerid()));
        params.put("pid", String.valueOf(listBean.getPid()));
        params.put("selected", String.valueOf(checked ? 1:0));
        params.put("num", String.valueOf(listBean.getNum()));
        RetrofitManager.post("product/updateCarts", params, new BaseObserver<ShopBean>() {
            @Override
            public void success(ShopBean shopBean) {
                allIndex ++;

                if(allIndex < allList.size()){
                    updateAllChecked(allList,checked);
                }
                else{
                    presenter.getshop("product/getCarts", uid);
                }
            }
            @Override
            public void failure(int code) {
            }
        });
    }

    private class GroupHolder{
        CheckBox checkBox;
        TextView textView;
    }


    private class ChildHolder{
        CheckBox checkBox;
        ImageView img;
        TextView text_title;
        TextView text_price;
        TextView text_num;
        TextView text_jian;
        TextView text_add;
        TextView text_delete;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
       this.onItemClickListener=onItemClickListener;
    }
}