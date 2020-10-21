package com.example.mobilesocialhub.EventCard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.example.mobilesocialhub.R;
import com.example.mobilesocialhub.databinding.ActivityFolderBinding;

import java.util.ArrayList;
import java.util.List;

public class FolderActivity extends AppCompatActivity {

    private ActivityFolderBinding mBinding;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_folder);
        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_folder);
//        recyclerView = findViewById(R.id.event_recycler);

        List<Event> eventsList = new ArrayList<>();
        eventsList.add(new Event("旋转木马的悲伤1","2020-02-19",getDrawable(R.drawable.walking),"2020-02-20","18:00pm","University of Melbourne"));
        eventsList.add(new Event("旋转木马的悲伤2","2020-03-19",getDrawable(R.drawable.walking),"2020-03-20","15:00pm","University of Monash"));
        eventsList.add(new Event("旋转木马的悲伤3","2020-04-19",getDrawable(R.drawable.walking),"2020-04-20","13:00pm","University of Monash"));

        FolderCellAdapter adapter = new FolderCellAdapter(eventsList);

        mBinding.eventRecycler.setLayoutManager(new GridLayoutManager(this,1));
        mBinding.eventRecycler.setAdapter(adapter);






//        ListView theListView = findViewById(R.id.mainListView);



    }
}