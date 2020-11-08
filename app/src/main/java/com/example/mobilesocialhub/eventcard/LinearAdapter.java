package com.example.mobilesocialhub.eventcard;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesocialhub.R;
import com.example.mobilesocialhub.databinding.ActivityFolderchildBinding;
import com.example.mobilesocialhub.databinding.LayoutListItemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.util.List;


public class LinearAdapter extends RecyclerView.Adapter<LinearAdapter.LinearViewHolder>  {

    private List<Comment> comment;
    final String TAG="Click";

    public LinearAdapter(List<Comment> comment) {
        this.comment = comment;
    }

    public class LinearViewHolder extends RecyclerView.ViewHolder{

        private LayoutListItemBinding binding;
        public LinearViewHolder(@NonNull LayoutListItemBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }


    @NonNull
    @Override
    public LinearAdapter.LinearViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LayoutListItemBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.layout_list_item,parent,false);
        return new LinearAdapter.LinearViewHolder(binding);
    }



    @Override
    public void onBindViewHolder(@NonNull final LinearAdapter.LinearViewHolder holder, int position) {
        holder.binding.username.setText(comment.get(position).getUsername());
        holder.binding.content.setText(comment.get(position).getContent());
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        String username = holder.binding.username.getText().toString();
        storageRef.child("images/" + username + "-head.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                try {
                    Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                    Tiny.getInstance().source(uri).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                        @Override
                        public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                            holder.binding.iv.setImageBitmap(bitmap);
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


    }

    @Override
    public int getItemCount() {
        return comment.size();
    }
}

