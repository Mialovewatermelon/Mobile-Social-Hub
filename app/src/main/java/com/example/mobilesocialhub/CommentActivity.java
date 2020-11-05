package com.example.mobilesocialhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.mobilesocialhub.chat.ChatActivity;
import com.example.mobilesocialhub.databinding.FragmentCommentBinding;
import com.example.mobilesocialhub.eventcard.Comment;
import com.example.mobilesocialhub.eventcard.FolderCellAdapter;
import com.example.mobilesocialhub.eventcard.LinearAdapter;
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
import java.util.UUID;

public class CommentActivity extends AppCompatActivity {

    private FragmentCommentBinding mBinding;
    FirebaseDatabase database;
    final String TAG = "Database";
    private List<Comment> list;
    String username;
    String eventID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkIntent();
        mBinding = DataBindingUtil.setContentView(this,R.layout.fragment_comment);
        initialData();
        final LinearAdapter adapter = new LinearAdapter(list);
        mBinding.commentRecycler.setAdapter(adapter);



        database= FirebaseDatabase.getInstance();
        final DatabaseReference eventRef = database.getReference().child("Events").child(eventID);
        final DatabaseReference commentRef = database.getReference().child("Comment").child(eventID);
        Log.w(TAG,"Completed connection！");

        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot:datasnapshot.getChildren()) {
                    //Log.w(TAG,"HI"+snapshot.getKey());
                    String username = snapshot.child("username").getValue().toString();
                    String content= snapshot.child("content").getValue().toString();

                    list.add(new Comment(username, content));
                    Log.w(TAG, "Completed comment loading");

                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                //datasnapshot.child()
                //Log.w(TAG,"HI"+snapshot.getKey());

                String usernamePublished = datasnapshot.child("usernamePublished").getValue().toString();
                String datePublished = datasnapshot.child("datePublished").getValue().toString();
                String eventDate = datasnapshot.child("eventDate").getValue().toString();
                String eventTime = datasnapshot.child("eventTime").getValue().toString();
                String address = datasnapshot.child("address").getValue().toString();
                String activityName = datasnapshot.child("activityName").getValue().toString();
                Boolean attend = datasnapshot.child("attendent").child(username).exists();


                StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                storageRef.child("images/" + usernamePublished + "-head.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        // Got the download URL for 'users/me/profile.png'
                        try {
                            Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                            Tiny.getInstance().source(uri).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                                @Override
                                public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                                    mBinding.profileImage.setImageBitmap(bitmap);
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

                mBinding.joinImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference().child("Events/" + eventID);
                        if (attend) {
                            Toast.makeText(v.getContext(), "You are already in this event", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            eventRef.child("attendent").child(username).setValue("1");
                            Toast.makeText(v.getContext(), "Congratulation! You are successfully joined", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



                setPicture(activityName);

                Log.w(TAG, "Completed saving data");

                mBinding.usernamePublished.setText(usernamePublished);
                mBinding.datePublished.setText(datePublished);
                mBinding.eventDate.setText(eventDate);
                mBinding.eventTime.setText(eventTime);
                mBinding.address.setText(address);
                //Log.w(TAG, eventsList.get(0).getDatePublished());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String content = mBinding.inputText.getText().toString();
                if (!"".equals(content)) {
                    Comment newComment = new Comment(username, content);
                    list.clear();
                    String uuid = UUID.randomUUID().toString();
                    commentRef.child(uuid).setValue(newComment);
                    mBinding.inputText.setText(""); // 清空输入框中的内容
                }
            }
        });
        mBinding.elip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                startActivity(intent);
            }
        });



    }

    private void checkIntent(){
        Log.d(TAG,"intent is coming1");
        if(getIntent().hasExtra("username")&&getIntent().hasExtra("eventID")){
            Log.d(TAG,"found");
            username = getIntent().getStringExtra("username");
            eventID = getIntent().getStringExtra("eventID");
            Log.d(TAG,username);
        }
    }

    public void setPicture(String activityName){
        switch (activityName){
            case "sport":
                mBinding.activity.setImageResource(R.drawable.sport);
                break;

            case "date":
                mBinding.activity.setImageResource(R.drawable.dating);
                break;

            case "dining":
                mBinding.activity.setImageResource(R.drawable.restaurant);
                break;
            case "work":
                mBinding.activity.setImageResource(R.drawable.work);
                break;
            case "coffee":
                mBinding.activity.setImageResource(R.drawable.coffee);
                break;
            case "shopping":
                mBinding.activity.setImageResource(R.drawable.shopping);
                break;
        }
    }
    private void initialData() {
        list=new ArrayList<>();
    }

}