package com.aplication.dilevery_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplication.dilevery_app.Fragments.Error_Fragment;
import com.aplication.dilevery_app.Fragments.Network_error_Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Track_Order_Activity extends AppCompatActivity {

    private ImageView bBack;
    private Bundle mBundle;

    private View v1 , v2 , v3 ,v4 , v5;
    private View connector1 , connector2 , connector3 , connector4  ;
    private ConstraintLayout layout1 , layout2 , layout3 , layout4 , layout5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);
        init();
    }

    private  void init( ) {



        this.v1 = findViewById(R.id.view);
        this.v2 = findViewById(R.id.view2);
        this.v3 = findViewById(R.id.view3);
        this.v4 = findViewById(R.id.view4);
        this.v5 = findViewById(R.id.view5);

        // ------------------------

        this.connector1 = findViewById(R.id.connector1);
        this.connector2 = findViewById(R.id.connector2);
        this.connector3 = findViewById(R.id.connector3);
        this.connector4 = findViewById(R.id.connector4);

        // -------------------------

        this.layout1 = findViewById(R.id.constraintLayout1);
        this.layout2 = findViewById(R.id.constraintLayout2);
        this.layout3 = findViewById(R.id.constraintLayoutPreparingOrder);
        this.layout4 = findViewById(R.id.constraintLayoutOrder_On_the_way);
        this.layout5 = findViewById(R.id.constraintLayoutOrder_Delivered);

        // -------------------------






        this.bBack = findViewById(R.id.back_from_track_order);
        this.bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        this.mBundle = getIntent().getExtras();
        getStateFromDb();


    }

    @SuppressLint("ResourceAsColor")
    private  void setState(int state){

        if(state == 0) return;

        ConstraintLayout layouts[] = {layout1 , layout2 , layout3 , layout4 , layout5};
        View rings [] = {v1 , v2,v3 ,v4 ,v5 };
        View connectors [] = {connector1 , connector2 , connector3 , connector4};




        for ( int x = 0 ; x<rings.length ; x ++ ) {
            if(x+1 < state ) {
                rings[x].setBackgroundResource(R.drawable.green_ring);
                layouts[x].setAlpha(1);
            } else if( x+1 == state) {
                    rings[x].setBackgroundResource(R.drawable.orange_ring);
                    layouts[x].setAlpha(1);
            } else  {
                layouts[x].setAlpha((float)0.4);
                rings[x].setBackgroundResource(R.drawable.grey_ring);
            }
        }

        for ( int y = 0 ; y<state-1 ; y++) {
            connectors[y].setBackgroundResource(R.color.iosGreen);
        }
        for ( int z = state-1 ; z<4 ; z++) {
            connectors[z].setBackgroundResource(R.color.light_grey);
        }




    }

    void getStateFromDb( ) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, HELPER.ORDER_GET_STATE + this.mBundle.getInt("order_id"),
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject mJsonObject = new JSONObject(response);

                    if(mJsonObject.getBoolean("success")) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        int state = mJsonObject.getInt("message");
                        setState(state);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

               /* Fragment selected_Error = null;
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    selected_Error = new Network_error_Fragment();
                } else {
                    selected_Error = new Error_Fragment();
                }
                ((FragmentActivity)getApplicationContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , selected_Error).commit();
                */
            }

        });


        mRequestQueue.add(mStringRequest);
    }





}