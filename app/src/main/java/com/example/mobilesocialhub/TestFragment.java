package com.example.mobilesocialhub;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobilesocialhub.databinding.FragmentEventBinding;
import com.example.mobilesocialhub.databinding.FragmentTestBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TestFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String id;
    private FragmentTestBinding mBinding;
    private String TAG = "intent";

    public TestFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TestFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TestFragment newInstance(String id) {
        TestFragment fragment = new TestFragment();
        Bundle args = new Bundle();
        args.putString("eventID",id);
        fragment.setArguments(args);
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

        if (getArguments() != null) {
            id = getArguments().getString("eventID");
            Log.w(TAG,id);
        }else{
            Log.w(TAG,"No argument:(");
        }

        mBinding = FragmentTestBinding.inflate(inflater, container,false);
        Log.w(TAG,"hey, I found it"+id);
        mBinding.intentid.setText(id);
        return mBinding.getRoot();
    }


}