package com.example.mobilesocialhub.EventCard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilesocialhub.R;
import com.example.mobilesocialhub.databinding.FragmentEventBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private FragmentEventBinding mBinding;
    FirebaseDatabase database;
    final String TAG = "Database";
    private List<Event> eventsList;

    // TODO: Rename and change types of parameters

    public EventFragment() {
        // Required empty public constructor
    }

    public static EventFragment newInstance(String param1, String param2) {
        EventFragment fragment = new EventFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        final FolderCellAdapter adapter = new FolderCellAdapter(eventsList);
        mBinding.eventRecycler.setAdapter(adapter);

        //Get Data from firebase
        database= FirebaseDatabase.getInstance();
        final DatabaseReference eventRef = database.getReference().child("Events");
        Log.w(TAG,"Completed connection！");
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                for(DataSnapshot snapshot:datasnapshot.getChildren()) {
                    Log.w(TAG,"HI"+snapshot.getValue().toString());
                    String usernamePublished = snapshot.child("usernamePublished").getValue().toString();
                    String datePublished = snapshot.child("datePublished").getValue().toString();
                    String eventDate = snapshot.child("eventDate").getValue().toString();
                    String eventTime = snapshot.child("eventTime").getValue().toString();
                    String address = snapshot.child("address").getValue().toString();

                    eventsList.add(new Event(usernamePublished, datePublished, eventDate, eventTime, address));
                    Log.w(TAG, "Completed saving data");
                    Log.w(TAG, eventsList.get(0).getDatePublished());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return mBinding.getRoot();
    }

    private void initialData() {
        eventsList=new ArrayList<>();
        eventsList.add(new Event("旋转木马的悲伤1","2020-02-19","2020-02-20","18:00pm","University of Melbourne"));

    }
}