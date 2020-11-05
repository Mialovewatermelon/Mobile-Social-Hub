package com.example.mobilesocialhub.eventcard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mobilesocialhub.databinding.FragmentEventBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment {

    //fragment manager
    FragmentManager fm;

    // Button callback
    OnButtonClick onButtonClick;
    String username;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private FragmentEventBinding mBinding;
    FirebaseDatabase database;
    final String TAG = "Database";
    OnElipsClick onElipsClick;

    private List<Event> eventsList;

    // TODO: Rename and change types of parameters

    public EventFragment() {
        // Required empty public constructor
    }

    public static EventFragment newInstance(String id) {
        EventFragment fragment = new EventFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // get the fragment manager
        fm = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentEventBinding.inflate(inflater, container,false);
        mBinding.eventRecycler.setLayoutManager(new GridLayoutManager(getContext(),1));

        //new the array and can set default value

        initialData();

        //set the adapter
        final FolderCellAdapter adapter = new FolderCellAdapter(eventsList, username);
        mBinding.eventRecycler.setAdapter(adapter);

        //Get Data from firebase
        database= FirebaseDatabase.getInstance();
        final DatabaseReference eventRef = database.getReference().child("Events");
        Log.w(TAG,"Completed connection！");
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                eventsList.clear();

                for(DataSnapshot snapshot:datasnapshot.getChildren()) {
                    Log.w(TAG,"HI"+snapshot.getKey());
                    String id = snapshot.getKey();
                    String usernamePublished = snapshot.child("usernamePublished").getValue().toString();
                    String datePublished = snapshot.child("datePublished").getValue().toString();
                    String eventDate = snapshot.child("eventDate").getValue().toString();
                    String eventTime = snapshot.child("eventTime").getValue().toString();
                    String address = snapshot.child("address").getValue().toString();
                    String activityName = snapshot.child("activityName").getValue().toString();
                    Event event  = new Event(usernamePublished, datePublished, eventDate, eventTime, address,id,activityName);
                    Map<String, String> attendent = (Map<String, String>) snapshot.child("attendent").getValue();
                    event.setAttendent(attendent);
                    eventsList.add(event);
                    Log.w(TAG, "Completed saving data");
                    Log.w(TAG, eventsList.get(0).getDatePublished());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // add add event button
        Button addEventBtn = mBinding.addEventBtn;
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClick != null) {
                    // set commentfragment string id's value
                    onButtonClick.onclick(v);
                }
            }
        });

        return mBinding.getRoot();
    }

    private void initialData() {
        eventsList=new ArrayList<>();
        eventsList.add(new Event("旋转木马的悲伤1","2020-02-19","2020-02-20","18:00pm","University of Melbourne","10086","coffee"));

    }

    public OnButtonClick getOnButtonClick() {
        return this.onButtonClick;
    }

    public void setOnButtonClick(OnButtonClick onButtonClick) {
        this.onButtonClick = onButtonClick;
    }

    public interface OnButtonClick{
        public void onclick(View view);
    }

    public void setUsername(String username){
        this.username = username;
    }



    public interface OnElipsClick{
        public void onelipsclick(View view);
    }

    public void setOnElipsClick(OnElipsClick onElipsClick){
        this.onElipsClick=onElipsClick;
    }



}