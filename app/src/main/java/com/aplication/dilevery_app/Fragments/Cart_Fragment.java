package com.aplication.dilevery_app.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplication.dilevery_app.Food_Details;
import com.aplication.dilevery_app.HELPER;
import com.aplication.dilevery_app.MainActivity;
import com.aplication.dilevery_app.R;
import com.aplication.dilevery_app.payment_activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import Adapters.Cart_Adapter;
import Adapters.Food_Adapter;
import Models.Cart_Item;


public class Cart_Fragment extends Fragment {
    private  View view;
    private RecyclerView cart_Rv;
    private Button bCheckout;

    private Cart_Adapter cart_adapter;
    public static ArrayList<Cart_Item> items;
    public static TextView total_price;
    private SharedPreferences mSharedPreferences;

    public Cart_Fragment () {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.cart_fragment , container , false);
        init( );
        return  view;
    }

    private void init() {

        this.total_price = view.findViewById(R.id.total_textV);
        this.bCheckout = this.view.findViewById(R.id.checkout_Btn);
        this.cart_Rv = view.findViewById(R.id.Cart_Rv);

        this.mSharedPreferences = getContext().getSharedPreferences("User_Data" , Context.MODE_PRIVATE);

            fill_json_data();


        this.cart_adapter = new Cart_Adapter(getContext() , items);
        this.cart_Rv.setAdapter(cart_adapter);
        LinearLayoutManager llm= new LinearLayoutManager(view.getContext());
        llm.setOrientation(RecyclerView.VERTICAL);
        this.cart_Rv.setLayoutManager(llm);
        this.total_price.setText(this.get_total(this.items) + " Da");



        this.bCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        Intent toPayment_Actvity = new Intent(getContext() , payment_activity.class);
        toPayment_Actvity.putExtra("total_price" , get_total(items) + " Da");
        startActivity(toPayment_Actvity);

            }
        });

    }

   /* private static ArrayList<Cart_Item> fill_items() {

         ArrayList<Cart_Item> itemss = new ArrayList<>();

        itemss.add(new Cart_Item("Burger One" , 150 , R.drawable.burger));
        itemss.add(new Cart_Item("Pizza" , 190 , R.drawable.pizza2));
        itemss.add(new Cart_Item("pizza Two" , 175 , R.drawable.pizza1));
        itemss.add(new Cart_Item("Coca Cola 2.5L" , 150 , R.drawable.coca));
        itemss.add(new Cart_Item("Tacos" , 185 , R.drawable.tacos));
        itemss.add(new Cart_Item("Burger 2" , 450 , R.drawable.burger2));

        return  itemss;

    } */

    private  int get_total (ArrayList<Cart_Item> items) {
        int total = 0;
        for (int w = 0 ; w<items.size() ; w++) {
            total +=items.get(w).getPrice() * items.get(w).getQuantity();
        }
    return total;
    }


    public  static  int has(int id) {
        for (int x = 0 ; x<items.size() ; x++) {
            if(items.get(x).getId() == id){
                return x;
            }
        }
        return -1;
    }


    private  void fill_json_data( ){

        items = new ArrayList<>();

        ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Loading data  ...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        RequestQueue mQueue = Volley.newRequestQueue(getContext());
        StringRequest mRequest = new StringRequest(Request.Method.GET, HELPER.CART_ITEMS + mSharedPreferences.getInt("id", -1),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //    Toast.makeText(getContext(), response , Toast.LENGTH_SHORT).show();
                        try {
                            JSONObject mJsonResponse = new JSONObject(response);
                            if(mJsonResponse.getBoolean("success")) {
                                JSONArray mJsonArray = mJsonResponse.optJSONArray("items");

                                if (mJsonArray.length() == 0) {
                                    Fragment selected_Fragment = new Empty_cart_Fragment();
                                    ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout
                                            , selected_Fragment).commit();
                                    mProgressDialog.dismiss();


                                } else {

                                    for (int x = 0; x < mJsonArray.length(); x++) {

                                        JSONObject mDataObject = mJsonArray.getJSONObject(x);
                                        JSONObject mProduct = mDataObject.getJSONObject("product");

                                        items.add(new Cart_Item(mDataObject.getInt("id")
                                                , mProduct.getString("name"),
                                                mProduct.getInt("price"),
                                                mProduct.getString("image")
                                                , mDataObject.getInt("quantity"),
                                                mProduct.getString("description")
                                        ));

                                    }
                                    cart_adapter = new Cart_Adapter(getContext(), items);

                                    cart_Rv.setAdapter(cart_adapter);
                                    LinearLayoutManager llm = new LinearLayoutManager(view.getContext());
                                    llm.setOrientation(RecyclerView.VERTICAL);
                                    cart_Rv.setLayoutManager(llm);
                                    total_price.setText(get_total(items) + " Da");

                                 mProgressDialog.dismiss();
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Exception"+ e.getMessage(), Toast.LENGTH_LONG).show();

                            mProgressDialog.dismiss();
                            Intent intent = new Intent(getContext(), Empty_cart_Fragment.class);
                            startActivity(intent);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressDialog.dismiss();
                Intent intent = new Intent(getContext(), Empty_cart_Fragment.class);
                startActivity(intent);
            }
        });

        mQueue.add(mRequest);
    }
}

