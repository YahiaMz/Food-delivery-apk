package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.aplication.dilevery_app.Fragments.Error_Fragment;
import com.aplication.dilevery_app.Fragments.Network_error_Fragment;
import com.aplication.dilevery_app.HELPER;
import com.aplication.dilevery_app.R;
import com.aplication.dilevery_app.Track_Order_Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import Models.Order;

public class Order_Adapter extends RecyclerView.Adapter<Order_Adapter.Order_VH> {
    private ArrayList<Order> orders ;
    private Context mContext;

    public Order_Adapter(ArrayList<Order> orders, Context mContext) {
        this.orders = orders;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Order_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.order_item_layout , parent , false);
        return new Order_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Order_VH holder, @SuppressLint("RecyclerView") int position) {

        Order current_Order = orders.get(position);

        holder.itemsRV.setAdapter(current_Order.getFood_order_adapter());
        holder.addressTV.setText(current_Order.getAddress());
        holder.total_PriceTv.setText(current_Order.getTotal_price()+" Da");
        holder.orderTime.setText(current_Order.getTime());
        holder.bTrackOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toTrack_Intent = new Intent(mContext , Track_Order_Activity.class);
                  toTrack_Intent.putExtra("order_id" ,  current_Order.getId());
                mContext.startActivity(toTrack_Intent);
            }
        });

        holder.bOrderDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disableOrder( current_Order.getId() , position );
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.orders.size();
    }

    public  class  Order_VH extends RecyclerView.ViewHolder {

        public RecyclerView itemsRV;
        public TextView addressTV , total_PriceTv , orderTime;
        public Button bTrackOrder , bOrderDone;
        public Order_VH(@NonNull View itemView) {
            super(itemView);

            this.total_PriceTv = itemView.findViewById(R.id.item_order_Total_price);
            this.addressTV = itemView.findViewById(R.id.item_order_address);
            this.itemsRV = itemView.findViewById(R.id.order_Item_recyclerVIew);
            this.orderTime = itemView.findViewById(R.id.order_item_time);
            this.bTrackOrder = itemView.findViewById(R.id.order_item_TrackOrder);
            this.bOrderDone = itemView.findViewById(R.id.order_item_orderdone);

        }
    }

    void disableOrder ( int order_id , int pos) {
        RequestQueue mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        StringRequest mStringRequest  = new StringRequest(Request.Method.GET, HELPER.DISABLE_ORDER + order_id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject mJSONObject = new JSONObject(response);
                            if(mJSONObject.getBoolean("success")) {

                                orders.remove(pos);
                                notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Fragment selected_Error = null;
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    selected_Error = new Network_error_Fragment();
                } else {
                    selected_Error = new Error_Fragment();
                }
                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , selected_Error).commit();


            }
        });
        mRequestQueue.add(mStringRequest);
    }
}
