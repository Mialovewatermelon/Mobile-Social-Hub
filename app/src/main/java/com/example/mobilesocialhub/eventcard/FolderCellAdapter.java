package com.example.mobilesocialhub.eventcard;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        holder.binding.setEvent(event.get(position));
        //here change to the activity name
        setPicture(event.get(position).getActivityName(), holder);

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
                intent.putExtra("address",holder.binding.getEvent().getId());
                view.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return event.size();
    }



    public interface OnElipsClick{
        public void onelipsclick(View view);
    }

    public void setPicture(String activityName, FolderCellAdapter.FolderViewHolder holder){
        switch (activityName){
            case "sport":
                holder.binding.activity.setImageResource(R.drawable.sport);
                break;

            case "date":
                holder.binding.activity.setImageResource(R.drawable.dating);
                break;

            case "dining":
                holder.binding.activity.setImageResource(R.drawable.restaurant);
                break;
            case "work":
                holder.binding.activity.setImageResource(R.drawable.work);
                break;
            case "coffee":
                holder.binding.activity.setImageResource(R.drawable.coffee);
                break;
            case "shopping":
                holder.binding.activity.setImageResource(R.drawable.shopping);
                break;
        }
    }
}

