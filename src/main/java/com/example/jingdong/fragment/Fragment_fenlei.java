package com.example.jingdong.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


import com.example.jingdong.R;
import com.example.jingdong.bean.FlBean;
import com.example.jingdong.utils.BaseObserver;
import com.example.jingdong.utils.RetrofitManager;
import com.example.jingdong.view.SerachActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class Fragment_fenlei extends Fragment {

    @BindView(R.id.f1_erweima)
    LinearLayout f1Erweima;
    @BindView(R.id.sousuo)
    TextView sousuo;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    @BindView(R.id.fg)
    LinearLayout fg;
    @BindView(R.id.list_item)
    ListView listItem;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    Unbinder unbinder;
    private List<FlBean.DataBean> data;
    private ListAdapter aa;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fenlei, container, false);


        unbinder = ButterKnife.bind(this, view);

        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SerachActivity.class);
                startActivity(intent);
            }
        });


        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int cid = data.get(position).getCid();

                FenleiFramlayout fenleiFramlayout = new FenleiFramlayout();
                getChildFragmentManager().beginTransaction().replace(R.id.framelayout, fenleiFramlayout).commit();
                //传值fenleifragment
                Bundle bundle = new Bundle();
                bundle.putInt("cid",cid);
                fenleiFramlayout.setArguments(bundle);
            }
        });


        //背景变色
        listItem.setSelector(R.color.colorAccent2);

        //请求listview数据
        Map<String,String> map = new HashMap<>();

        RetrofitManager.get("product/getCatagory", map, new BaseObserver<FlBean>() {

            @Override
            public void success(FlBean flBean) {
                data = flBean.getData();

                aa = new ListAdapter(data,getActivity());
                listItem.setAdapter(aa);

            }
            @Override
            public void failure(int code) {

            }
        });

        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //listview适配器
    class ListAdapter extends BaseAdapter {

        List<FlBean.DataBean> data;
        Context context;
        private int selectedPosition = 0;// 选中的位置


        public ListAdapter(List<FlBean.DataBean> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            View view1 = View.inflate(context, R.layout.textview, null);
            final TextView text = view1.findViewById(R.id.textview);
            text.setText(data.get(i).getName());


            return view1;
        }

    }
}
