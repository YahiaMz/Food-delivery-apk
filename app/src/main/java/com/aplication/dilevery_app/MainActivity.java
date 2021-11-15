package com.aplication.dilevery_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.aplication.dilevery_app.Fragments.Cart_Fragment;
import com.aplication.dilevery_app.Fragments.Favorite_Fragment;
import com.aplication.dilevery_app.Fragments.Home_Fragment;
import com.aplication.dilevery_app.Fragments.Search_Fragment;
import com.aplication.dilevery_app.Fragments.Order_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private BottomNavigationView bNv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences mSharedPreferences = getSharedPreferences("User_Data" , MODE_PRIVATE);


        if(!mSharedPreferences.getBoolean("is_login",false)) {
            Intent intent = new Intent(this , login_screen.class);
            startActivity(intent);
            finish();
        }

        //Toast.makeText(getApplicationContext(), mSharedPreferences.getString("token" , " Empty ") + "", Toast.LENGTH_SHORT).show();

        init();
    }

    private void init() {
        this.frameLayout = findViewById(R.id.frameLayout);
        this.bNv = findViewById(R.id.bottom_nav);
        this.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , new Home_Fragment()).commit();
        this.bNv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selected_Fragment = null;
                switch (item.getItemId()) {
                    case R.id.bn_HOME:
                        selected_Fragment = new Home_Fragment();
                        break;
                    case R.id.bn_Cart:
                        selected_Fragment =  new Cart_Fragment();
                        break;
                    case R.id.bn_likes:
                        selected_Fragment = new Favorite_Fragment();
                        break;
                    case R.id.bn_Search:
                        selected_Fragment = new Search_Fragment();
                        break;
                    case R.id.bn_user:
                        selected_Fragment = new Order_Fragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , selected_Fragment).commit();
                return true;
            }
        });

    }
}