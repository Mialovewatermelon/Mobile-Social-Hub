package com.example.mobilesocialhub;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;

import com.example.mobilesocialhub.eventcard.EventFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
    String TAG = "IntentCheck";
    TestFragment testFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        testFragment = new TestFragment();
        checkIntent();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.fragment2,testFragment);
        transaction.commit();








//        database= FirebaseDatabase.getInstance();
//        Log.d(TAG,"database");
//        myRef= database.getReference("mobile-social-hub/message2");
//        myRef.setValue("Hello, Word");
//
//
//
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "key is: " + dataSnapshot.getKey());
//                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
    }

    private void checkIntent(){
        Log.d(TAG,"intent is coming1");
        if(getIntent().hasExtra("address")){
            Log.d(TAG,"found");
            String address = getIntent().getStringExtra("address");
            Log.d(TAG,getIntent().getStringExtra("address"));
            testFragment = testFragment.newInstance(address);
        }
    }
}