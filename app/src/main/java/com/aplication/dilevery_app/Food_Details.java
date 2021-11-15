package com.aplication.dilevery_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Food_Details extends AppCompatActivity {
     TextView name  , price , quantity , description , time , rating;
     ImageView food_image;
     ImageButton back_Btn , incQuantity , decQuantity ;
     Button mAdd_To_Cart;
     SharedPreferences mSharedPreferences ;
     Bundle bundle ;

     private  int iQuantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);
        init();
        this.back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private  void init() {
        this.bundle = getIntent().getExtras();
        mSharedPreferences = getSharedPreferences("User_Data" , MODE_PRIVATE);
        this.mAdd_To_Cart = findViewById(R.id.add_toCart_btn);
        this.incQuantity = findViewById(R.id.incQuantity);
        this.decQuantity = findViewById(R.id.decQuantity);
        this.back_Btn = findViewById(R.id.back_btn);
        this.name = findViewById(R.id.food_details_name);
        this.price = findViewById(R.id.food_details_price);
        this.quantity = findViewById(R.id.food_details_quantity);
        this.description = findViewById(R.id.food_details_desc);
        this.time = findViewById(R.id.food_details_time);
        this.rating = findViewById(R.id.food_details_rating);
        this.food_image = findViewById(R.id.imageFoodDetails);



        String imageUrl = bundle.getString("image");
        Picasso.get().load(HELPER.URL + "/foods_images/"+imageUrl).into(food_image);
        this.price.setText(bundle.getInt("price") +" DA");
        this.name.setText(bundle.getString("name"));
        this.description.setText(bundle.getString("desc"));

        this.mAdd_To_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart();
            }
        });
        this.increment_Quantity();
        this.decrement_Quantity();

    }

    private void increment_Quantity() {
         this.incQuantity.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                iQuantity += 1;
                quantity.setText(iQuantity + "");
                changePrice(iQuantity);
             }
         });

    }
    private void decrement_Quantity() {
        this.decQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(iQuantity>1){
                    iQuantity -= 1;
                    quantity.setText(iQuantity + "");
                    changePrice(iQuantity);
                }

            }
        });

    }


    private  void addCart ( ) {
        RequestQueue queue = Volley.newRequestQueue(this.getApplicationContext());
        StringRequest request = new StringRequest(
                Request.Method.POST, HELPER.ADD_TO_CART,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
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
                params.put("quantity" , quantity.getText().toString() );
                params.put("product_id" , bundle.getInt("product_id") + "");

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

    void  changePrice  (int a) {
        int q = a * bundle.getInt("price");
        this.price.setText( (q>9 ? q+"" : "0"+q) + " DA");
    }
}