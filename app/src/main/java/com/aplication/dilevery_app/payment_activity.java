package com.aplication.dilevery_app;

import androidx.annotation.Nullable;
import androidx.annotation.PluralsRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplication.dilevery_app.Fragments.Cart_Fragment;
import com.aplication.dilevery_app.Fragments.Empty_cart_Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Adapters.Cart_Adapter;

public class payment_activity extends AppCompatActivity {

    private TextView mTotal_Price , mSubtotal_Price;
   private ImageView back;
   private EditText ePhone_Number , eAddress;
   private Button bPlaceOrder;
   private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();

    }

    private  void init( ) {
        this.mSharedPreferences = getSharedPreferences("User_Data" , MODE_PRIVATE);
        this.ePhone_Number = findViewById(R.id.phone_number_editTxt);
        this.mTotal_Price = findViewById(R.id.total_cost_TV);
        this.mSubtotal_Price = findViewById(R.id.sub_total_cost_TV);
        this.back = findViewById(R.id.back_from_payment);
        this.bPlaceOrder = findViewById(R.id.placeOrderbtn);
        this.eAddress = findViewById(R.id.address_edit_text);




        String phone_Number = this.mSharedPreferences.getString("phone_number" , "");
        this.ePhone_Number.setText(phone_Number);
        this.bPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!phoneValidator()){
                    ePhone_Number.setError("Wrong phone number");
                }
                if (!addressValidator()) {
                    eAddress.setError("address must be at least 10 digits");
                }

                if(phoneValidator() ) {

                   placeOrder();


                }


            }
        });

        this.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        Bundle bundle = getIntent().getExtras();
        this.mSubtotal_Price.setText(getIntent().getExtras().getString("total_price"));
        this.mTotal_Price.setText(getIntent().getExtras().getString("total_price"));


    }

    private  boolean phoneValidator   ( ){
        Pattern pattern = Pattern.compile("[0][567][0-9]{8}");
        Matcher matcher = pattern.matcher(ePhone_Number.getText());

        return  matcher.matches();
    }

    private  boolean addressValidator( ){
        return  this.eAddress.getText().toString().length() > 10;
    }

    private  void  placeOrder ( ) {


      Custom_Dialog mCustom_dialog = new Custom_Dialog(this , "Creating Your Order");
      mCustom_dialog.show();



        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest mStringRequest = new StringRequest(Request.Method.POST, HELPER.CREATE_ORDER, new Response.Listener<String>() {



            @Override
            public void onResponse(String response) {

                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject mJSON_Response = new JSONObject(response);
                    if(mJSON_Response.getBoolean("success")){
   //                   Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        create_Order_items(mJSON_Response.getInt("order_id"));
                        mCustom_dialog.dismiss();
                        Intent toOrderPlacedScreen = new Intent(getApplicationContext() , Order_Placed_Activity.class);
                        startActivity(toOrderPlacedScreen);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "place Order Catch", Toast.LENGTH_SHORT).show();
                    mCustom_dialog.dismiss();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Place Order ------- " + error.toString(), Toast.LENGTH_SHORT).show();
                mCustom_dialog.dismiss();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> mParam = new HashMap<>();
                mParam.put("user_id" ,mSharedPreferences.getInt("id" , -1) + "" );
                mParam.put("phone_number" , ePhone_Number.getText().toString());
                mParam.put("order_address" , eAddress.getText().toString());
                return mParam;
            }
        };
        mRequestQueue.add(mStringRequest);

    }


    private void create_Order_items  (int x1) {



        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());



        for (int x = 0; x < Cart_Adapter.items.size(); x++) {


            int finalX = x;
            int finalX1 = x1;
            StringRequest mStringRequest = new StringRequest(Request.Method.POST, HELPER.CREATE_ORDER_ITEM,
                    new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext()," Adding Item Response " + response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), " Adding Item" +error.toString(), Toast.LENGTH_SHORT).show();
            }



        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mParam = new HashMap<>();
                mParam.put("order_id"  , finalX1 +"");
                mParam.put("product_id"  , Cart_Adapter.items.get(finalX).getProduct_id() +"");
                mParam.put("quantity"  , Cart_Adapter.items.get(finalX).getQuantity() +"");

                return mParam;
            }
        };

            mRequestQueue.add(mStringRequest);
            removeFromCart(Cart_Adapter.items.get(finalX).getId());

        }






    }

    private  void removeFromCart ( int id ){

        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest mStringRequest = new StringRequest(Request.Method.GET
                , HELPER.REMOVE_ITEM_FROM_CART + id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mJsonResponse = new JSONObject(response);
                            if(mJsonResponse.getBoolean("success")) {

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                }

        );

        mRequestQueue.add(mStringRequest);
    }


}