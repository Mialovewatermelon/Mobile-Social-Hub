package com.example.mobilesocialhub.profile;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.mobilesocialhub.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.Inflater;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private boolean isBackground = false;
    private static final int CROP_SMALL_PICTURE = 2;
    public static final String TAG = "ProfileFragment";
    protected static Uri tempUri;

    private ImageView back;

    private ImageView cover;;

    @BindView(R.id.qianming_tv)
    public TextView qianming;

    private ImageView background;

    private Button chat;

    private Button follow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_profile, container, false);
        initview(view);
        initpresenter();
        initListener();
        return view;
    }
    private void initview(View view) {
        back = view.findViewById(R.id.back_iv);
        cover = view.findViewById(R.id.cover_im);;
        background = view.findViewById(R.id.background_iv);;
        chat = view.findViewById(R.id.chat_bt);
        follow = view.findViewById(R.id.follow_bt);;

    }
    private void initpresenter() {
    }
    private void initListener() {
        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicDialog();
            }
        });
        background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isBackground = true;
                showChoosePicDialog();
                isBackground = false;
            }
        });

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
    }

    /**
     * 显示修改头像的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Set");
        String[] items = { "From Album", "Take Photo" };
        builder.setNegativeButton("Cancel", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_PICK);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
//                        tempUri = Uri.fromFile(new File(Environment
//                                .getExternalStorageDirectory(), "image.jpg"));
                        File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM), "image.jpg");
                        try {
                            if (file.exists()) {
                                file.delete();
                            }
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        tempUri = FileProvider.getUriForFile(getContext(), "com.example.mobilesocialhub.fileprovider", file);
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换

                        String filePath = getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/image.jpg";
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            if(isBackground){
                if (data != null) {
                    setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                }
                return;
            }
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
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Log.i("tag", uri.toString());
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
        Uri cropUri = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "myHeader_crop.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     *
     */
    protected void setImageToView(Intent data){
        Bundle extras = data.getExtras();
        Log.i("tag", String.valueOf(extras==null));
        Bitmap photo = data.getParcelableExtra("data");
        if (extras != null) {
            try {
                photo = BitmapFactory.decodeStream(getActivity().getContentResolver()
                        .openInputStream(tempUri));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(isBackground){
                background.setImageBitmap(photo);
                return;
            }
//            photo = ImageUtils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            cover.setImageBitmap(photo);
//            uploadPic(photo);
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
        }
    }

    public interface ImageResult{
        void showImage();
    }

}