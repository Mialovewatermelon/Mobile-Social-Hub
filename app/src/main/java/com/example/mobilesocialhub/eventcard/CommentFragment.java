package com.example.mobilesocialhub.eventcard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobilesocialhub.R;
import com.example.mobilesocialhub.chat.Msg;
import com.example.mobilesocialhub.databinding.FragmentCommentBinding;
import com.example.mobilesocialhub.databinding.FragmentTestBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentCommentBinding mBinding;
    FirebaseDatabase database;
    final String TAG = "Database";
    private List<Comment> list;
    String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public CommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Comment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommentFragment newInstance(String param1, String param2) {
        CommentFragment fragment = new CommentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = FragmentCommentBinding.inflate(inflater, container,false);
        mBinding.commentRecycler.setLayoutManager(new GridLayoutManager(getContext(),1));
        initialData();

        final LinearAdapter adapter = new LinearAdapter(list);
        mBinding.commentRecycler.setAdapter(adapter);

        database= FirebaseDatabase.getInstance();
        final DatabaseReference eventRef = database.getReference().child("Events").child("2a9c08cb-549d-4e94-8f8b-0609864134ee");
        final DatabaseReference commentRef = database.getReference().child("Comment").child("2a9c08cb-549d-4e94-8f8b-0609864134ee");
        Log.w(TAG,"Completed connection！");
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                //datasnapshot.child()
                //Log.w(TAG,"HI"+snapshot.getKey());

                String usernamePublished = datasnapshot.child("usernamePublished").getValue().toString();
                String datePublished = datasnapshot.child("datePublished").getValue().toString();
                String eventDate = datasnapshot.child("eventDate").getValue().toString();
                String eventTime = datasnapshot.child("eventTime").getValue().toString();
                String address = datasnapshot.child("address").getValue().toString();
                String id = datasnapshot.getKey();
                Log.w(TAG, "Completed saving data");

                mBinding.usernamePublished.setText(usernamePublished);
                mBinding.datePublished.setText(datePublished);
                mBinding.eventDate.setText(eventDate);
                mBinding.eventTime.setText(eventTime);
                mBinding.address.setText(address);
                //Log.w(TAG, eventsList.get(0).getDatePublished());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //list.add(new Comment("sage", "2020-01-01", "hello world"));

        commentRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for(DataSnapshot snapshot:datasnapshot.getChildren()) {
                    //Log.w(TAG,"HI"+snapshot.getKey());
                    String username = snapshot.child("username").getValue().toString();
                    String content= snapshot.child("content").getValue().toString();
                    String date = "2020-01-01";

                    list.add(new Comment(username, date, content));
                    //Log.w(TAG, "Completed saving data");
                    Log.w(TAG, list.get(0).getDate());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mBinding.send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mBinding.inputText.getText().toString();
                if (!"".equals(content)) {
                    list.add(new Comment("this.user", "2020-01-01", content));
                    adapter.notifyItemInserted(list.size() - 1); // 当有新消息时，刷新ListView中的显示
                    mBinding.commentRecycler.scrollToPosition(list.size() - 1); // 将ListView定位到最后一行
                    mBinding.inputText.setText(""); // 清空输入框中的内容
                }
            }
        });

        return mBinding.getRoot();
    }

    private void initialData() {
        list=new ArrayList<>();
    }
}