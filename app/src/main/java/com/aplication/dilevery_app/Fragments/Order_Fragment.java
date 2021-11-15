package com.aplication.dilevery_app.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplication.dilevery_app.HELPER;
import com.aplication.dilevery_app.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Adapters.Food_order_item_Adapter;
import Adapters.Order_Adapter;
import Models.Food_Order;
import Models.Order;

public class Order_Fragment extends Fragment {
    View view ;
    private ArrayList<Order> orders ;
    private SharedPreferences mSharedPreferences;
    private Order_Adapter mOrder_adapter;
    private RecyclerView mOrder_recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.order_fragment, container , false );
        this.init();
        return this.view;
    }

    private void init() {
        this.mOrder_recyclerView = this.view.findViewById(R.id.order_recyclerVIew);
        this.fill_orders( );

    }

    private void fill_orders() {
        this.mSharedPreferences = getActivity().getSharedPreferences("User_Data", Context.MODE_PRIVATE);
        this.orders = new ArrayList<>();

        ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("getting your Orders ...");
        mProgressDialog.show();


        RequestQueue mRequestQueue = Volley.newRequestQueue(getContext());
        StringRequest mStringRequest = new StringRequest(Request.Method.GET, HELPER.ALL_ORDERS + mSharedPreferences.getInt("id", -1),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            JSONObject mJSON_Response = new JSONObject(response);
                            if (mJSON_Response.getBoolean("success")) {

                                JSONArray mJsonOrders = mJSON_Response.getJSONArray("message");
                                for (int x = 0 ; x<mJsonOrders.length() ; x++ ) {
                                    JSONObject current_JsonOrder = (JSONObject) mJsonOrders.get(x);

                                    ArrayList<Food_Order> food_orders = new ArrayList<>();
                                    JSONArray json_food_orders = current_JsonOrder.getJSONArray("items");

                                    for(int x1 = 0 ; x1<json_food_orders.length() ; x1++ ) {
                                        JSONObject current_json_food_order =  ((JSONObject) json_food_orders.get(x1));
                                        JSONObject currentProduct  = current_json_food_order.getJSONObject("product");

                                         food_orders.add(new Food_Order(currentProduct.getString("image") ,
                                                 currentProduct.getString("name") ,
                                                 current_json_food_order.getInt("quantity")
                                                 ));

                                    }


                                    orders.add(new Order(
                                            current_JsonOrder.getInt("id"),
                                            current_JsonOrder.getString("order_address") ,
                                            current_JsonOrder.getInt("totalPrice"),
                                            new Food_order_item_Adapter(food_orders , getContext()) ,
                                            current_JsonOrder.getString("time")
                                            ));
                                }
                                   mOrder_adapter = new Order_Adapter(orders , getContext());
                                  mOrder_recyclerView.setAdapter(mOrder_adapter);
                                  mProgressDialog.dismiss();

                            } else  {
                                ((FragmentActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , new Error_Fragment()).commit();
                            }


                        } catch (JSONException e) {
                            mProgressDialog.dismiss();
                            ((FragmentActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , new Error_Fragment()).commit();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.dismiss();
                        Fragment selected_Error = null;
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                            selected_Error = new Network_error_Fragment();
                        } else {
                            selected_Error = new Error_Fragment();
                        }
                        ((FragmentActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , selected_Error).commit();

                    }
                }
        );
        mRequestQueue.add(mStringRequest);

    }
}
