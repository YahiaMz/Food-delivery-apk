package Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
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
import com.aplication.dilevery_app.Fragments.Empty_cart_Fragment;
import com.aplication.dilevery_app.HELPER;
import com.aplication.dilevery_app.MainActivity;
import com.aplication.dilevery_app.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Models.Cart_Item;
import Models.Food;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.Cart_VH> {
    private Context context;
    public static ArrayList<Cart_Item> items;

    private SharedPreferences mSharedPreferences;


    public Cart_Adapter(Context context, ArrayList<Cart_Item> items) {
        this.context = context;
        this.items = items;
        mSharedPreferences = context.getSharedPreferences("User_Data" , Context.MODE_PRIVATE);
    }

    @NonNull
    @Override
    public Cart_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(this.context).inflate(R.layout.cart_list_item_layout , parent ,false);
        return new Cart_VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Cart_VH holder, @SuppressLint("RecyclerView") int position) {


        Cart_Item current_item = items.get(position);

        Picasso.get().load(HELPER.URL + "/foods_images/"+current_item.getImage()).into(holder.image);
              holder.price.setText(current_item.getPrice() * current_item.getQuantity()+" Da");
              holder.name.setText(current_item.getName());
              holder.quantity.setText( this.full_number( current_item.getQuantity() ));

              holder.delete_item.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        removeItem(current_item.getId() , position);
                  }

              });
              holder.add_quantity.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                  change_DB_Quantity(current_item.getQuantity()+1 , position);

                  }
              });
              holder.minus_quantity.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (current_item.getQuantity() > 1) {
                         change_DB_Quantity(current_item.getQuantity()-1 , position);
                      }
                  }
              });
              holder.image.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      Intent intent = new Intent(context , Food_Details.class);

                      intent.putExtra("name"  , items.get(position).getName());
                      intent.putExtra("image"  , items.get(position).getImage());
                      intent.putExtra("price"  , items.get(position).getPrice());
                      intent.putExtra("desc" , current_item.getDescription());

                      context.startActivity(intent);
                  }
              });





    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public  class  Cart_VH extends RecyclerView.ViewHolder {

        public ImageView image, delete_item;
        public TextView name;
        public TextView price;
        public TextView quantity;

        public ImageButton add_quantity , minus_quantity;

    public Cart_VH(@NonNull View itemView) {
        super(itemView);

        this.image = itemView.findViewById(R.id.cart_item_imageView);
        this.name = itemView.findViewById(R.id.cart_item_name);
        this.price = itemView.findViewById(R.id.cart_item_price);
        this.quantity = itemView.findViewById(R.id.cart_item_quantity);
        this.delete_item= itemView.findViewById(R.id.delete_cart_item);
        this.quantity = itemView.findViewById(R.id.cart_item_quantity);
        this.add_quantity = itemView.findViewById(R.id.add_quantity_cart_item);
        this.minus_quantity = itemView.findViewById(R.id.minus_quantity_cart_item);
    }
}
private String full_number( int x) {
    return  (x<10)? "0"+x : x+"";
}

private int total_price ( ArrayList<Cart_Item> items) {
    int total = 0;
    for (int w = 0 ; w<items.size() ; w++) {
        total +=items.get(w).getPrice() * items.get(w).getQuantity();
    }
    return total;

}

public  static  int has (int id) {
        for ( int x = 0 ; x<items.size() ; x++) {
            if(id == items.get(x).getId() ) { return x; }
        }
        return -1;
}

private  void change_DB_Quantity ( int quantity , int position ) {
    RequestQueue mQueue = Volley.newRequestQueue(this.context);
    StringRequest mStringRequest = new StringRequest(Request.Method.POST, HELPER.CHANGE_QUANTITY_CART_ITEM,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getBoolean("success")){

                            Cart_Item current_item = items.get(position);
                            current_item.setQuantity(quantity);
                            Cart_Fragment.total_price.setText(total_price(items) +" Da");
                            notifyItemChanged(position);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
    ){
        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
           Map<String , String> mParams = new HashMap<>();
           mParams.put("id" , items.get(position).getId() +"");
           mParams.put("quantity" ,quantity + "" );
           return  mParams;

        }
    };


    mQueue.add(mStringRequest);
}
private void removeItem( int id , int pos) {
       RequestQueue mRequestQueue = Volley.newRequestQueue(context);


    ProgressDialog mProgressDialog = new ProgressDialog(context);
    mProgressDialog.setMessage("deleting item ...");
    mProgressDialog.setCancelable(false);

    mProgressDialog.show();

       StringRequest mStringRequest = new StringRequest(Request.Method.GET
               , HELPER.REMOVE_ITEM_FROM_CART + id,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       try {
                           JSONObject mJsonResponse = new JSONObject(response);
                            if(mJsonResponse.getBoolean("success")) {

                                items.remove(pos);
                                Cart_Fragment.total_price.setText(total_price(items) +" Da");
                                notifyDataSetChanged();
                                mProgressDialog.dismiss();
                                if (items ==null || items.isEmpty()){
                                    Fragment selected_Fragment = new Empty_cart_Fragment();
                                    ((FragmentActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout
                                            , selected_Fragment).commit();
                                }

                            }


                       } catch (JSONException e) {
                           e.printStackTrace();
                           mProgressDialog.dismiss();

                       }
                   }
               },
               new Response.ErrorListener() {
                   @Override
                   public void onErrorResponse(VolleyError error) {
                       mProgressDialog.dismiss();


                   }
               }

       );

       mRequestQueue.add(mStringRequest);
}


}

