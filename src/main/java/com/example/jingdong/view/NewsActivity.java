package com.example.jingdong.view;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jingdong.ImageUtils;
import com.example.jingdong.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewsActivity extends AppCompatActivity {

    @BindView(R.id.fanhui3)
    TextView fanhui3;
    @BindView(R.id.image3)
    ImageView image3;
    @BindView(R.id.yonghu)
    TextView yonghu;
    private String dname;
    private String sname;
    private String stouxiang;
    private static final String TAG = "MainActivity";

    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        Intent intent = getIntent();
       //接收扣扣登录值
        sname = intent.getStringExtra("qname");
        stouxiang = intent.getStringExtra("qtouxiang");
        //手机号登录值*/
        dname = intent.getStringExtra("sname");




        if(dname !=null){
            yonghu.setText(dname);
        }
        else if(sname !=null|| stouxiang !=null){
            yonghu.setText(sname);
            Glide.with(this).load(stouxiang).into(image3);

        }
    }

    @OnClick({R.id.fanhui3, R.id.image3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fanhui3:
                finish();
                break;
            case R.id.image3:
                shangchuan();

                break;
        }
    }

   public void shangchuan(){

       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setTitle("设置头像");
       String[] items = { "选择本地照片", "拍照" };
       builder.setNegativeButton("取消", null);
       builder.setItems(items, new DialogInterface.OnClickListener() {

           @Override
           public void onClick(DialogInterface dialog, int which) {
               switch (which) {
                   case CHOOSE_PICTURE: // 选择本地照片
                       Intent openAlbumIntent = new Intent(
                               Intent.ACTION_GET_CONTENT);
                       openAlbumIntent.setType("image/*");
                       startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                       break;
                   case TAKE_PICTURE: // 拍照
                       takePicture();
                       break;
               }
           }
       });
       builder.create().show();
   }


    private void takePicture() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= 23) {
            // 需要申请动态权限
            int check = ContextCompat.checkSelfPermission(this, permissions[0]);
            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝
            if (check != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
        Intent openCameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment
                .getExternalStorageDirectory(), "image.jpg");
        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= 24) {
            openCameraIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            tempUri = FileProvider.getUriForFile(NewsActivity.this, "com.lt.uploadpicdemo.fileProvider", file);
        } else {
            tempUri = Uri.fromFile(new File(Environment
                    .getExternalStorageDirectory(), "image.jpg"));
        }
        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }


    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            Log.d(TAG,"setImageToView:"+photo);
            photo = ImageUtils.toRoundBitmap(photo); // 这个时候的图片已经被处理成圆形的了
            image3.setImageBitmap(photo);

            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了
        String imagePath = ImageUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath+"");
        if(imagePath != null){
            // 拿着imagePath上传了
            // ...
            Log.d(TAG,"imagePath:"+imagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            // 没有获取 到权限，从新请求，或者关闭app
            Toast.makeText(this, "需要存储权限", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }


    //退出登录
    public void tuichu(View view) {


        //获取当前状态
        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);
        String name1 = test.getString("name1", null);
        String qqname = test.getString("qqname", null);
        String qqtouxiang = test.getString("qqtouxiang", null);
        //给name赋值为空

        if(yonghu.equals(dname)){
            name1=null;
        }
        else if(yonghu.equals(sname)){
            qqname=null;
            qqtouxiang = null;
            /*image3.setImageAlpha(R.drawable.user);*/
            Glide.with(this).load(R.drawable.user).into(image3);
        }

        SharedPreferences.Editor edit = test.edit();
        //传递到我的页面
        if(name1==null){
            edit.putString("name1",name1);
        }
        else if(sname==null||stouxiang==null){
            edit.putString("qqname",qqname);
            edit.putString("qqtouxiang",qqtouxiang);
        }
        edit.clear();
        //退出完成
        edit.commit();

        finish();
    }


}
