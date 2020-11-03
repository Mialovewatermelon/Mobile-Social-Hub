package com.example.mobilesocialhub.album;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mobilesocialhub.R;


public class MainActivity extends AppCompatActivity {
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO = 2;
    private Intent intent1,intent2,intent3;
    private ImageView picture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara_main);

        Button chooseFromAlbum = findViewById(R.id.choose_from_album);
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(com.example.mobilesocialhub.album.MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(com.example.mobilesocialhub.album.MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PHOTO);
                } else {
                    openAlbum();//打开album的界面
                }
            }
        });

        Button takePhoto = findViewById(R.id.take_photo);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 动态申请权限
                if (ContextCompat.checkSelfPermission(com.example.mobilesocialhub.album.MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(com.example.mobilesocialhub.album.MainActivity.this, new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO);
                } else {
                    // 启动相机程序
                    startCamera();
                }
            }
        });

        picture = findViewById(R.id.picture);
        Intent i=getIntent();

        Bitmap bitmap= BitmapFactory.decodeFile(i.getStringExtra("url"));
        picture.setImageBitmap(bitmap);
        Button contacts=findViewById(R.id.contacts);
        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContact();
            }
        });

        intent1=new Intent(this,Albums.class);//创建跳转到Albums显示的窗口的Intent
        intent2=new Intent(this,Camera.class);//创建跳转到Camera显示的窗口的Intent


        Button upload = findViewById(R.id.upload_photo);
        Button download = findViewById(R.id.download_photo);

    }

    private void openContact(){
        startActivity(intent3);
    };
    private void openAlbum() {
        startActivity(intent1);//进入album的窗口界面
    }
    private void startCamera() {
        startActivity(intent2);//进入camera的窗口界面
        //Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 指定图片的输出地址为imageUri
        //mPhotoPath = getSDPath()+"/"+ getPhotoFileName();//设置图片文件路径，getSDPath()和getPhotoFileName()具体实现在下面
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        //startActivityForResult(intent, TAKE_PHOTO);
    }

}
