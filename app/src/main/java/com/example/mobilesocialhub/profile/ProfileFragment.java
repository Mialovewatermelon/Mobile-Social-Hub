package com.example.mobilesocialhub.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesocialhub.R;
import com.example.mobilesocialhub.eventcard.Event;
import com.example.mobilesocialhub.profile.ProfileAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment implements View.OnClickListener{
    private boolean isBackground = false;
    //调取系统摄像头的请求码
    private static final int MY_ADD_CASE_CALL_PHONE = 6;
    //打开相册的请求码
    private static final int MY_ADD_CASE_CALL_PHONE2 = 7;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private LayoutInflater inflater;

    private View layout;
    private TextView takePhotoTV;
    private TextView choosePhotoTV;
    private TextView cancelTV;

    private static final String TAG = "MainActivity";

    private ImageView back;

    private ImageView cover;

    private TextView qianming;

    private ImageView background;

    private Button chat;

    private Button follow;

    private RecyclerView activities;

    private List<Event> activityList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_profile, container, false);
        initView(view);
        initListener();
        return view;
    }


    private void initListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        background.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                isBackground = true;
                Log.d(TAG,"isBackground ----> "+isBackground);
                openMenu();
            }
        });

        cover.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                UpdatePhoto(view);
            }
        });

    }

    private void initView(View view) {
        back = view.findViewById(R.id.back_iv);
        cover = view.findViewById(R.id.cover_im);
        background = view.findViewById(R.id.background_iv);
        chat = view.findViewById(R.id.chat_bt);
        follow = view.findViewById(R.id.follow_bt);
        activities = view.findViewById(R.id.home_pager_content_list);
        activities.setLayoutManager(new LinearLayoutManager(getContext()));
        activityList=new ArrayList<>();
        final ProfileAdapter adapter = new ProfileAdapter(activityList);
        activities.setAdapter(adapter);



        //Get Data from firebase
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference eventRef = database.getReference().child("Events");
        Log.w(TAG,"Completed connection！");
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                activityList.clear();

                for(DataSnapshot snapshot:datasnapshot.getChildren()) {
                    Log.w(TAG,"HI"+snapshot.getKey());
                    String id = snapshot.getKey();
                    String usernamePublished = snapshot.child("usernamePublished").getValue().toString();
                    String datePublished = snapshot.child("datePublished").getValue().toString();
                    String eventDate = snapshot.child("eventDate").getValue().toString();
                    String eventTime = snapshot.child("eventTime").getValue().toString();
                    String address = snapshot.child("address").getValue().toString();
                    String activityName = snapshot.child("activityName").getValue().toString();
                    activityList.add(new Event(usernamePublished, datePublished, eventDate, eventTime, address,id,activityName));
                    Log.w(TAG, "Completed saving data");
                    Log.w(TAG, activityList.get(0).getDatePublished());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    /*
    初始化控件方法
     */
    public void openMenu() {

        builder = new AlertDialog.Builder(getContext());//创建对话框
        inflater = getLayoutInflater();
        layout = inflater.inflate(R.layout.dialog_select_photo, null);//获取自定义布局
        builder.setView(layout);//设置对话框的布局
        dialog = builder.create();//生成最终的对话框
        dialog.show();//显示对话框

        takePhotoTV = layout.findViewById(R.id.take_photo_tv);
        choosePhotoTV = layout.findViewById(R.id.choose_photo_tv);
        cancelTV = layout.findViewById(R.id.cancel);
        //设置监听
        takePhotoTV.setOnClickListener(this);
        choosePhotoTV.setOnClickListener(this);
        cancelTV.setOnClickListener(this);
    }

    /**
     * 修改头像执行方法
     * @param view
     */
    public void UpdatePhoto(View view) {
        openMenu();
    }

    private void takePhoto() throws IOException {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        // 获取文件
        File file = createFileIfNeed("UserIcon.png");
        //拍照后原图回存入此路径下
        Uri uri;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            uri = Uri.fromFile(file);
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
            uri = FileProvider.getUriForFile(getContext(), "com.example.mobilesocialhub.fileprovider", file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
        startActivityForResult(intent, 1);
    }

    // 在sd卡中创建一保存图片（原图和缩略图共用的）文件夹
    private File createFileIfNeed(String fileName) throws IOException {
        String fileA = getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/nbinpic";
        File fileJA = new File(fileA);
        if (!fileJA.exists()) {
            fileJA.mkdirs();
        }
        File file = new File(fileA, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 打开相册
     */
    private void choosePhoto() {
        //这是打开系统默认的相册(就是你系统怎么分类,就怎么显示,首先展示分类列表)
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 2);
    }

    /**
     * 申请权限回调方法
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //如果requestCode是打开相机
        if (requestCode == MY_ADD_CASE_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    takePhoto();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(),"拒绝了你的请求",Toast.LENGTH_SHORT).show();
                //"权限拒绝");
                // TODO: 2018/12/4 这里可以给用户一个提示,请求权限被拒绝了
            }
        }

        //如果requestCode是打开相册
        if (requestCode == MY_ADD_CASE_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
                //"权限拒绝");
                // TODO: 2018/12/4 这里可以给用户一个提示,请求权限被拒绝了
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * startActivityForResult执行后的回调方法，接收返回的图片
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode != Activity.RESULT_CANCELED) {
            String state = Environment.getExternalStorageState();
            if (!state.equals(Environment.MEDIA_MOUNTED)) return;
            // 把原图显示到界面上
            Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
            Tiny.getInstance().source(readpic()).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                @Override
                public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                    saveImageToServer(bitmap, outfile);//显示图片到imgView上
                }
            });
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK
                && null != data) {
            try {
                Uri selectedImage = data.getData();//获取路径
                Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                Tiny.getInstance().source(selectedImage).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                    @Override
                    public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                        saveImageToServer(bitmap, outfile);
                    }
                });
            } catch (Exception e) {
                //"上传失败");
            }
        }
    }

    /**
     * 从保存原图的地址读取图片
     */
    private String readpic() {
        String filePath = getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/nbinpic/" + "UserIcon.png";
        return filePath;
    }

    private void saveImageToServer(final Bitmap bitmap, String outfile) {
        File file = new File(outfile);
        // TODO: 2018/12/4  这里就可以将图片文件 file 上传到服务器,上传成功后可以将bitmap设置给你对应的图片展示
        if (isBackground) {
            background.setImageBitmap(bitmap);
            isBackground = false;
        }else {
            cover.setImageBitmap(bitmap);
        }

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.take_photo_tv:
                //"点击了照相";
                //  6.0之后动态申请权限 摄像头调取权限,SD卡写入权限
                //判断是否拥有权限，true则动态申请
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_ADD_CASE_CALL_PHONE);
                } else {
                    try {
                        //有权限,去打开摄像头
                        takePhoto();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                dialog.dismiss();
                break;
            case R.id.choose_photo_tv:
                //"点击了相册";
                //  6.0之后动态申请权限 SD卡写入权限
                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_ADD_CASE_CALL_PHONE2);

                } else {
                    //打开相册
                    choosePhoto();
                }
                dialog.dismiss();
                break;
            case R.id.cancel:
                dialog.dismiss();//关闭对话框
                break;
            default:break;
        }
    }
}
