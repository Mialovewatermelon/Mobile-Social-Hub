package com.example.mobilesocialhub.EventCard;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilesocialhub.MainActivity;
import com.example.mobilesocialhub.R;
import com.example.mobilesocialhub.databinding.ActivityFolderchildBinding;

import java.util.List;


public class FolderCellAdapter extends RecyclerView.Adapter<FolderCellAdapter.FolderViewHolder>  {

    private List<Event> event;
    final String TAG="Click";

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
    public void onBindViewHolder(@NonNull final FolderCellAdapter.FolderViewHolder holder, int position) {
        holder.binding.usernamePublished.setText(event.get(position).getUsernamePublished());
        holder.binding.datePublished.setText(event.get(position).getDatePublished());
//        holder.binding.activity.setImageDrawable(event.get(position).getActivity());
        holder.binding.eventDate.setText(event.get(position).getEventDate());
        holder.binding.eventTime.setText(event.get(position).getEventTime());
        holder.binding.address.setText(event.get(position).getAddress());
        holder.binding.elip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.w(TAG,"Test Click"+String.valueOf(holder.getAdapterPosition()));
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                intent.putExtra("address",holder.binding.address.toString());
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return event.size();
    }
}

