package com.aplication.dilevery_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Order_Placed_Activity extends AppCompatActivity {

    Button bBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);
        this.init( );
    }

    private  void init( ) {
        this.bBackHome = findViewById(R.id.back_to_homeBtn);
        this.bBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}