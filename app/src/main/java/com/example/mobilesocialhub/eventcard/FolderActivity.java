package com.example.mobilesocialhub.eventcard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mobilesocialhub.R;

public class FolderActivity extends AppCompatActivity {

    final String TAG = "Database";


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //With out databinding
        setContentView(R.layout.activity_folder);
//        recyclerView = findViewById(R.id.event_recycler);


//        mBinding= DataBindingUtil.setContentView(this, R.layout.activity_folder);
//        final List<Event> eventsList = new ArrayList<>();
//        final FolderCellAdapter adapter = new FolderCellAdapter(eventsList);
//        mBinding.eventRecycler.setLayoutManager(new GridLayoutManager(this,1));
//        mBinding.eventRecycler.setAdapter(adapter);

//        eventsList.add(new Event("旋转木马的悲伤1","2020-02-19","2020-02-20","18:00pm","University of Melbourne"));

//        database= FirebaseDatabase.getInstance();
//        final DatabaseReference eventRef = database.getReference().child("Event");
//        Log.w(TAG,"Completed connection");
//        eventRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                String usernamePublished = datasnapshot.child("usernamePublished").getValue().toString();
//                String datePublished = datasnapshot.child("datePublished").getValue().toString();
//                String eventDate =datasnapshot.child("eventDate").getValue().toString();
//                String eventTime =datasnapshot.child("eventTime").getValue().toString();
//                String address = datasnapshot.child("address").getValue().toString();
//
//                eventsList.add(new Event(usernamePublished,datePublished,eventDate,eventTime,address));
////                mAdapter.notifyDataSetChanged();
//                Log.w(TAG,"Completed saving data");
//                Log.w(TAG,eventsList.get(0).getDatePublished());
//
//                Log.w(TAG,"!:"+datasnapshot.child("address").getValue());
//                adapter.notifyDataSetChanged();
////                for(DataSnapshot snapshot:datasnapshot.getChildren() ){
////                    Log.w(TAG, "HI!");
////                    Log.w(TAG,"!:"+snapshot.getValue().toString());
////                }
////                mAdapter.notifyDataSetChanged();
//
//
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//       eventsList.add(new Event("旋转木马的悲伤1","2020-02-19","2020-02-20","18:00pm","University of Melbourne"));
//        Log.w(TAG,"second"+eventsList.get(0).getDatePublished());

    }
}