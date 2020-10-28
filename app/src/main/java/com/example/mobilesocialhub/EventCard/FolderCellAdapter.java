package com.example.mobilesocialhub.EventCard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobilesocialhub.R;
import com.example.mobilesocialhub.databinding.ActivityFolderchildBinding;

import java.util.List;


public class FolderCellAdapter extends RecyclerView.Adapter<FolderCellAdapter.FolderViewHolder>  {

    private List<Event> event;

    public FolderCellAdapter(List<Event> event) {
        this.event = event;
    }

    public class FolderViewHolder extends RecyclerView.ViewHolder{

        private ActivityFolderchildBinding binding;
        public FolderViewHolder(@NonNull ActivityFolderchildBinding binding) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }


    @NonNull
    @Override
    public FolderCellAdapter.FolderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ActivityFolderchildBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.activity_folderchild,parent,false);
        return new FolderViewHolder(binding);
    }



    @Override
    public void onBindViewHolder(@NonNull FolderCellAdapter.FolderViewHolder holder, int position) {
        holder.binding.usernamePublished.setText(event.get(position).getUsernamePublished());
        holder.binding.datePublished.setText(event.get(position).getDatePublished());
//        holder.binding.activity.setImageDrawable(event.get(position).getActivity());
        holder.binding.eventDate.setText(event.get(position).getEventDate());
        holder.binding.eventTime.setText(event.get(position).getEventTime());
        holder.binding.address.setText(event.get(position).getAddress());

    }

    @Override
    public int getItemCount() {
        return event.size();
    }
}

