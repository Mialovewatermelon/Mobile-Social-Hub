package com.example.mobilesocialhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobilesocialhub.eventcard.Event;
import com.example.mobilesocialhub.profile.ProfileAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    private View layout;
    private ImageView back;
    private ImageView cover;
    private TextView usernameView;
    private ImageView background;
    private RecyclerView activities;
    private List<Event> activityList;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_self_profile);
        checkIntent();
        initView();
    }
    private void initView() {
        back = findViewById(R.id.back_iv);
        cover = findViewById(R.id.cover_im);
        background = findViewById(R.id.background_iv);
        activities = findViewById(R.id.home_pager_content_list);
        activities.setLayoutManager(new LinearLayoutManager(this));
        activityList = new ArrayList<>();
        final ProfileAdapter adapter = new ProfileAdapter(activityList);
        activities.setAdapter(adapter);
        usernameView = findViewById(R.id.qianming_tv);
        usernameView.setText(username);
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        storageRef.child("images/"+ username + "-head.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                try {
                    Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                    Tiny.getInstance().source(uri).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                        @Override
                        public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                            cover.setImageBitmap(bitmap);
                        }
                    });
                } catch (Exception e) {
                    //"上传失败");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        storageRef.child("images/"+ username + "-background.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                try {
                    Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                    Tiny.getInstance().source(uri).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                        @Override
                        public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                            background.setImageBitmap(bitmap);
                        }
                    });
                } catch (Exception e) {
                    //"上传失败");
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });
        //Get Data from firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference eventRef = database.getReference().child("Events");
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                activityList.clear();

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    String id = snapshot.getKey();
                    String usernamePublished = snapshot.child("usernamePublished").getValue().toString();
                    String datePublished = snapshot.child("datePublished").getValue().toString();
                    String eventDate = snapshot.child("eventDate").getValue().toString();
                    String eventTime = snapshot.child("eventTime").getValue().toString();
                    String address = snapshot.child("address").getValue().toString();
                    String activityName = snapshot.child("activityName").getValue().toString();
                    Event event = new Event(usernamePublished, datePublished, eventDate, eventTime, address, id, activityName);
                    Map<String, String> attendent = (Map<String, String>)snapshot.child("attendent").getValue();
                    event.setAttendent(attendent);
                    if (attendent.containsKey(username)) {
                        activityList.add(new Event(usernamePublished, datePublished, eventDate, eventTime, address, id, activityName));
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void checkIntent() {
        if(getIntent().hasExtra("username")){
            username = getIntent().getStringExtra("username");
        }
    }
}