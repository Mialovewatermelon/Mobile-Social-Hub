package com.example.mobilesocialhub.eventcard;

import android.content.Intent;
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
        /*
        holder.binding.usernamePublished.setText(event.get(position).getUsernamePublished());
        holder.binding.setEvent(event.get(position));
        holder.binding.datePublished.setText(event.get(position).getDatePublished());
        holder.binding.activity.setImageDrawable(event.get(position).getActivity());
        holder.binding.eventDate.setText(event.get(position).getEventDate());
        holder.binding.eventTime.setText(event.get(position).getEventTime());
        holder.binding.address.setText(event.get(position).getAddress());

         */
        holder.binding.username.setText(comment.get(position).getUsername());
        holder.binding.content.setText(comment.get(position).getContent());
        holder.binding.date.setText(comment.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return comment.size();
    }
}

