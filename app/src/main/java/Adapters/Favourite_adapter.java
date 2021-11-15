package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplication.dilevery_app.Fragments.Cart_Fragment;
import com.aplication.dilevery_app.HELPER;
import com.aplication.dilevery_app.R;

import org.w3c.dom.Text;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Models.Cart_Item;
import Models.Favourite_model;

public class Favourite_adapter extends RecyclerView.Adapter<Favourite_adapter.Favourite_VH> {


    private ArrayList<Favourite_model> favourites;
    private Context mContext;
    private SharedPreferences mSharedPreferences;

    public Favourite_adapter(ArrayList<Favourite_model> favourites, Context mContext) {
        this.favourites = favourites;
        this.mContext = mContext;
        this.mSharedPreferences = mContext.getSharedPreferences("User_Data" , Context.MODE_PRIVATE);
    }


    @NonNull
    @Override
    public Favourite_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.favourite_item_layout , parent , false);
        return new Favourite_VH(view );
    }

    @Override
    public void onBindViewHolder(@NonNull Favourite_VH holder, @SuppressLint("RecyclerView") int position) {
        Favourite_model current_item = this.favourites.get(position);
        holder.image.setImageResource(current_item.getImage());
        holder.price.setText(current_item.getPrice()  +"Da");
        holder.name.setText(current_item.getName());


        holder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood_toCart(current_item.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.favourites.size();
    }

    public class Favourite_VH extends RecyclerView.ViewHolder {

        public  TextView price , name;
        public  ImageView image,bAdd;


        public Favourite_VH(@NonNull View itemView) {
             super(itemView);
            this.image = itemView.findViewById(R.id.food_image);
            this.name = itemView.findViewById(R.id.food_name);
            this.price = itemView.findViewById(R.id.food_price);
            this.bAdd = itemView.findViewById(R.id.addFood_btn);
        }
    }


    private  void addFood_toCart( int product_id ) {
        RequestQueue queue = Volley.newRequestQueue(mContext);
        StringRequest request = new StringRequest(
                Request.Method.POST, HELPER.ADD_TO_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                         Toast.makeText(mContext, response, Toast.LENGTH_SHORT).show();


                        Toast toast = new Toast(mContext);
                        toast.setGravity(Gravity.BOTTOM,0 , 150);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(LayoutInflater.from(mContext).inflate(R.layout.succes_taost , null , false));
                        toast.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String> params = new HashMap<>();
                params.put("user_id" , mSharedPreferences.getInt("id" , -1)  +"" );
                params.put("quantity" , "1" );
                params.put("product_id" ,  product_id +"");

                return  params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                System.out.println("token : " + mSharedPreferences.getString("token" , "") );
                Map<String , String> params = new HashMap<>();
                params.put("Authorization" , "Bearer "+ mSharedPreferences.getString("token" , ""));
                return params;
            }
        };


        queue.add(request);
    }

}
