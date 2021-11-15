package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.aplication.dilevery_app.Food_Details;
import com.aplication.dilevery_app.HELPER;
import com.aplication.dilevery_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Models.Favourite_model;
import Models.Food;

public class Search_Food_adapter extends RecyclerView.Adapter<Search_Food_adapter.Favourite_VH> {


    public ArrayList<Food> getmFoods() {
        return mFoods;
    }

    public void setmFoods(ArrayList<Food> mFoods) {
        this.mFoods = mFoods;
    }

    private ArrayList<Food> mFoods;
    private Context mContext;
    private  SharedPreferences mSharedPreferences;

    public Search_Food_adapter(ArrayList<Food> foods, Context mContext) {
        this.mFoods = foods;
        this.mContext = mContext;
        this.mSharedPreferences = mContext.getSharedPreferences("User_Data" , -1);

    }


    @NonNull
    @Override
    public Favourite_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.mContext).inflate(R.layout.favourite_item_layout , parent , false);
        return new Favourite_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Favourite_VH holder, @SuppressLint("RecyclerView") int position) {
        Food current_item = this.mFoods.get(position);

        Picasso.get().load(HELPER.FOOD_IMAGES  + current_item.getImage()).into(holder.image);
        holder.price.setText(current_item.getPrice()  +"Da");
        holder.name.setText(current_item.getName());
        holder.bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood_toCart(current_item.getId());
            }
        });


   holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext , Food_Details.class);

                intent.putExtra("name"  , mFoods.get(position).getName());
                intent.putExtra("image"  , mFoods.get(position).getImage());
                intent.putExtra("price"  , mFoods.get(position).getPrice()  );
                intent.putExtra("product_id" , mFoods.get(position).getId());
                mContext.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return this.mFoods.size();
    }

    public class Favourite_VH extends RecyclerView.ViewHolder {

        public  TextView price , name;
        public  ImageView image,bAdd;
        public ConstraintLayout constraintLayout;


        public Favourite_VH(@NonNull View itemView) {
             super(itemView);

             this.constraintLayout = itemView.findViewById(R.id.favourite_constraint_layout);
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
                Map<String , String> params = new HashMap<>();
                params.put("Authorization" , "Bearer "+ mSharedPreferences.getString("token" , ""));
                return params;
            }
        };


        queue.add(request);
    }

}
