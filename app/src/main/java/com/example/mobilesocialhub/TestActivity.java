package com.example.mobilesocialhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mobilesocialhub.eventcard.CreateEventFragment;
import com.example.mobilesocialhub.eventcard.EventFragment;
import com.example.mobilesocialhub.chat.ChatFragment;
import com.example.mobilesocialhub.databinding.ActivityTestBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class TestActivity extends AppCompatActivity {
    EventFragment eventFragment;
    ChatFragment chatFragment;
    CreateEventFragment createFragment;
    Fragment nowFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestBinding testBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        eventFragment = new EventFragment();
        eventFragment.setOnButtonClick(new EventFragment.OnButtonClick() {
            @Override
            public void onclick(View view) {
                navContent(nowFragment, createFragment);
            }
        });
        chatFragment = new ChatFragment();
        createFragment = new CreateEventFragment();
        createFragment.setOnButtonClick(new CreateEventFragment.OnButtonClick() {
            @Override
            public void onclick(View view) {
                navContent(nowFragment, eventFragment);
            }
        });

        nowFragment = eventFragment;
        ChipNavigationBar bottomNavView = testBinding.chipNavigationBar;

        ft.replace(R.id.main_fragment, eventFragment).commit();
        ChipNavigationBar.OnItemSelectedListener itemListener = new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home_info:
                        navContent(nowFragment, eventFragment);
                        break;
                    case R.id.person_info:
                        navContent(nowFragment, chatFragment);
                        break;
                    case R.id.activity_info:
                        navContent(nowFragment, createFragment);
                        break;
                    default:
                        break;
                }
            }
        };
        bottomNavView.setOnItemSelectedListener(itemListener);
        bottomNavView.setItemSelected(R.id.home_info, true);
    }
    private void navContent(Fragment from, Fragment to) {
        if(nowFragment != to) {
            nowFragment = to;
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            if (!to.isAdded()) {
                ft.hide(from).add(R.id.main_fragment, to).commit();
            } else {
                ft.hide(from).show(to).commit();
            }
        }
    }
}