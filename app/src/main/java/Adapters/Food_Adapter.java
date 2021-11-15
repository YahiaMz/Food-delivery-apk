package Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import com.aplication.dilevery_app.Fragments.Cart_Fragment;
import com.aplication.dilevery_app.HELPER;
import com.aplication.dilevery_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import Models.Cart_Item;
import Models.Food;

public class Food_Adapter extends RecyclerView.Adapter<Food_Adapter.Food_VH> {

    private Context context ;
    private ArrayList<Food> foods;
    SharedPreferences mSharedPreferences;

    public Food_Adapter(Context context, ArrayList<Food> foods) {
        this.context = context;
        this.foods = foods;
        mSharedPreferences = context.getSharedPreferences("User_Data" , Context.MODE_PRIVATE);
    }

     public void update_data( ArrayList<Food> new_data) {
        this.foods.clear();
        this.foods.addAll(new_data);
        this.notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    @NonNull
    @Override
    public Food_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item_layout , parent  , false);
        return new Food_VH(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull Food_VH holder, @SuppressLint("RecyclerView") int position) {

         Food current_Item = foods.get(position);
         System.out.println(current_Item.getImage());
         Picasso.get().load(HELPER.URL + "/foods_images/"+current_Item.getImage()).into(holder.food_image);

         holder.food_name.setText(foods.get(position).getName() );
         holder.food_price.setText(foods.get(position).getPrice()+" Da");


         holder.bAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                /* int p;
                  if(Cart_Adapter.items == null){
                       p = Cart_Fragment.has(current_Item.getId());
                      if (p != -1) {
                      Cart_Fragment.items.get(p).setQuantity(Cart_Adapter.items.get(p).getQuantity() + 1);
                        }
                      else { Cart_Fragment.items.add(new Cart_Item(current_Item.getId(), current_Item.getName() , current_Item.getPrice() , current_Item.getImage() )); }

                  } else {

                       p = Cart_Adapter.has(current_Item.getId());
                      if (p != -1) {
                          Cart_Fragment.items.get(p).setQuantity(Cart_Adapter.items.get(p).getQuantity() + 1); }
                          else {
                          Cart_Fragment.items.add(new Cart_Item(current_Item.getId(), current_Item.getName() ,
                                  current_Item.getPrice() , current_Item.getImage() ));

                          }

                  }
*/
                 addFood_toCart(current_Item.getId());


             }

         });

         holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(context , Food_Details.class);

                 intent.putExtra("name"  , foods.get(position).getName());
                 intent.putExtra("image"  , foods.get(position).getImage());
                 intent.putExtra("price"  , foods.get(position).getPrice()  );
                 intent.putExtra("product_id" , foods.get(position).getId());
                 context.startActivity(intent);


             }
         });

    }

    @Override
    public int getItemCount() {
        return this.foods.size();
    }

    public class Food_VH extends RecyclerView.ViewHolder {

        public ImageView food_image;
        public TextView food_name;
        public ImageButton bAdd;
        public TextView food_price;
        public ConstraintLayout constraintLayout;

        public Food_VH(@NonNull View itemView) {
            super(itemView);
            this.constraintLayout = itemView.findViewById(R.id.food_item_constraint_layout);
            this.food_image = itemView.findViewById(R.id.food_image);
            this.food_name = itemView.findViewById(R.id.food_name);
            this.food_price = itemView.findViewById(R.id.food_price);
            this.bAdd = itemView.findViewById(R.id.addFood_btn);
        }
    }

private  void addFood_toCart( int product_id ) {
    RequestQueue queue = Volley.newRequestQueue(context);
    StringRequest request = new StringRequest(
            Request.Method.POST, HELPER.ADD_TO_CART,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                  //  Toast.makeText(context, response, Toast.LENGTH_SHORT).show();


                    Toast toast = new Toast(context);
                    toast.setGravity(Gravity.BOTTOM,0 , 150);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(LayoutInflater.from(context).inflate(R.layout.succes_taost , null , false));
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
