package com.aplication.dilevery_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import Adapters.LoginAdapter;

public class login_screen extends AppCompatActivity {
    TabLayout mTabLayout;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        System.out.println("in Login ");
        init();

    }

    private void init( ) {
        this.mTabLayout = findViewById(R.id.tabLayout);
        this.mViewPager = findViewById(R.id.viewPager);




        this.mTabLayout.addTab(mTabLayout.newTab().setText("Login"));
        this.mTabLayout.addTab(mTabLayout.newTab().setText("SingUp"));


        this.mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        LoginAdapter loginAdapter = new LoginAdapter(getSupportFragmentManager() , getApplicationContext() , this.mTabLayout.getTabCount());
        this.mViewPager.setAdapter(loginAdapter);

        this.mTabLayout.setupWithViewPager(mViewPager);
        this.mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }


}