package com.example.mobilesocialhub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.mobilesocialhub.eventcard.CreateEventFragment;
import com.example.mobilesocialhub.eventcard.EventFragment;
import com.example.mobilesocialhub.chat.ChatFragment;
import com.example.mobilesocialhub.databinding.ActivityTestBinding;
import com.example.mobilesocialhub.profile.ProfileFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class TestActivity extends AppCompatActivity {
    boolean flag;
    EventFragment eventFragment;
    ChatFragment chatFragment;
    CreateEventFragment createFragment;
    ProfileFragment profileFragment;
    Fragment nowFragment;

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //检查权限
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION
                        , Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            } else {
                flag = true;
            }
        } else {
            flag = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermission();


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
        profileFragment = new ProfileFragment();

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
                        navContent(nowFragment, profileFragment);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_fragment);
        f.onActivityResult(requestCode,resultCode,data);
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