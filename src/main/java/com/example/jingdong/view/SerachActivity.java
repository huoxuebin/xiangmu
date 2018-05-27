package com.example.jingdong.view;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jingdong.Liushi.XCFlowLayout;
import com.example.jingdong.R;
import com.example.jingdong.bean.User;
import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;
import com.greendao.gen.UserDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SerachActivity extends AppCompatActivity {

    //定义数组
    private String mNames[] = {
            "男装","电脑","手机","apple",
            "坚果","苹果",
            "戴尔","阿迪短袖","迪凯斯男装",
            "男士皮鞋"
    };

    private TextView textView;

    private UserDao userDao;
    private XCFlowLayout mFlowLayout;
    private List<String> list = new ArrayList<>();
    private String sname;
    private User user;
    private Adapter adapter;
    private EditText editText;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serach);
        ButterKnife.bind(this);

        textView = findViewById(R.id.sosuo);
        editText = findViewById(R.id.edname);
        lv = findViewById(R.id.lv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname = editText.getText().toString();


                //跳转展示页面
                if("".equals(sname)){



                    Toast.makeText(SerachActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
                }
                else{
                    //将数据添加到数据库
                    user = new User(null, sname);
                    long insert = userDao.insert(user);
                    list.add(sname);
                    adapter = new Adapter(SerachActivity.this, list);
                    lv.setAdapter(adapter);

                    Intent intent  = new Intent(SerachActivity.this,ZhanshiActivity.class);
                    intent.putExtra("name", sname);

                    startActivity(intent);

                }


            }
        });
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String s = editText.getText().toString();
                    Intent intent  = new Intent(SerachActivity.this,ZhanshiActivity.class);
                    intent.putExtra("name",s);

                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
        initChildViews();
        //调取Greendao
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"user_db");
        SQLiteDatabase database = helper.getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession session = daoMaster.newSession();
        userDao = session.getUserDao();
        //长按删除
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                userDao.deleteByKey((long) i);
                list.remove(i);
                //   adapter.notifyDataSetChanged();
                lv.setAdapter(new Adapter(SerachActivity.this,list));
                return false;
            }
        });

        //点击传值到搜索框
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                editText.setText(list.get(i).toString());

            }
        });
        //查询数据库
        List<User> users = userDao.loadAll();
        if (users!=null){

            for (int i=0;i<users.size();i++){

                String sname = users.get(i).getName();
                list.add(sname);
            }
            lv.setAdapter(new Adapter(SerachActivity.this,list));
        }
    }
    //搜索,添加数据库







    //全部删除
    public void delete(View view) {


        userDao.deleteAll();

        list.clear();
       Adapter  adapter = new Adapter(SerachActivity.this, list);
        lv.setAdapter(adapter);
    }

    //适配器
    class Adapter extends BaseAdapter {
        Context context;
        List<String> list;
        public Adapter(Context context, List<String> list) {
            this.context = context;
            this.list = list;
        }
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(context);
            textView.setText(list.get(i));
            textView.setTextSize(18);

            return textView;
        }
    }
    //流式布局
    private void initChildViews() {

        mFlowLayout = findViewById(R.id.flowlayout);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(

                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for( int i = 0; i < mNames.length; i ++){
            final TextView view1 = new TextView(this);
            view1.setText(mNames[i]);
            view1.setTextColor(Color.BLACK);
            view1.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            mFlowLayout.addView(view1,lp);

            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String lname = view1.getText().toString();

                    //Toast.makeText(SerachActivity.this,view1.getText().toString() , Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SerachActivity.this,ZhanshiActivity.class);
                    intent.putExtra("name",lname);
                    startActivity(intent);
                }
            });
        }


    }

}