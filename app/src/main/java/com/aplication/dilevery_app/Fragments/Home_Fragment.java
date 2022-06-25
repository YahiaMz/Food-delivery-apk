package com.aplication.dilevery_app.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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
import com.aplication.dilevery_app.login_screen;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import Adapters.Category_Adapter;
import Adapters.Food_Adapter;
import Models.Category;
import Models.Food;

public class Home_Fragment extends Fragment {


    private TextView user_name;
    private ImageView user_image;
    //--------------------------------------------------//
    private RecyclerView categories_RV;
    private Category_Adapter category_adapter;
    private ArrayList<Category> my_categories;
    // ------------------------------------------------- //
    public static RecyclerView foodRV;
    public static Food_Adapter food_adapter;
    public static ArrayList<Food> foods = new ArrayList<>();
    // --------------------------------------------------- //

    private PopupMenu popupMenu;


    SharedPreferences mSharedPreferences;




    private  View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          this.view = inflater.inflate(R.layout.home_fragment , container , false);
          init();
          return  view;
    }

    void  init( ) {
        this.mSharedPreferences = getActivity().getSharedPreferences("User_Data" , Context.MODE_PRIVATE);

        this.user_name = this.view.findViewById(R.id.user_name);
        this.user_image = this.view.findViewById(R.id.user_image);

        String user_name = "Hi "+ this.mSharedPreferences.getString("name" , "");
        String mUser_image = this.mSharedPreferences.getString("photo" , "");


        Picasso.get().load(mUser_image).into(user_image);
        this.user_name.setText(user_name);

        this.popupMenu = new PopupMenu(getContext() , this.user_image);
        this.popupMenu.inflate(R.menu.popup_menu);


        fill_categories();
        this.categories_RV = this.view.findViewById(R.id.category_RV);
        this.category_adapter = new Category_Adapter(this.my_categories, this.getContext());


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

        this.categories_RV.setAdapter(this.category_adapter);
        this.categories_RV.setLayoutManager(linearLayoutManager);

        ////////////////////////////////////////////////////////////

        if(foods.size() == 0) {
            fill_foods();
        }

            food_adapter = new Food_Adapter(getContext(), filter_food(foods, 1));
            foodRV = view.findViewById(R.id.food_RV);
            foodRV.setAdapter(food_adapter);

            LinearLayoutManager ll = new LinearLayoutManager(getContext());
            ll.setOrientation(RecyclerView.HORIZONTAL);

            foodRV.setLayoutManager(ll);


            this.popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {

                    switch (item.getItemId()) {
                        case R.id.logoutBtnMenu:
                            Toast.makeText(getContext(), "Loging out ...", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = mSharedPreferences.edit();
                            editor.putBoolean("is_login" , false );
                            editor.remove("token");
                            editor.remove("name");
                            editor.remove("email");

                            editor.apply();
                            Intent intent = new Intent(getContext() , login_screen.class);
                            startActivity(intent);
                            getActivity().finish();

                            break;
                    }

                  return true;
                }
            });
            this.user_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     popupMenu.show();
                }
            });

    }

    void fill_categories (  ) {

        this.my_categories = new ArrayList<>();

        this.my_categories.add(new Category(1,"Pizza" , R.drawable.ic_pizza));
        this.my_categories.add(new Category(2,"Burger" , R.drawable.ic_burger));
        this.my_categories.add(new Category(3,"Soda" , R.drawable.ic_coca));
        this.my_categories.add(new Category(4,"Tacos" , R.drawable.ic_frit));
        this.my_categories.add(new Category(6,"Cake" , R.drawable.ic_gateau));

        this.my_categories.get(0).setId(1);

    }
    void fill_foods ( ){

      //  this.foods = new ArrayList<Food>();

       /* if(foods==null || foods.size()==0) {

        this.foods.add(new Food(1,"Pizza Royal" , R.drawable.pizza1 , 300 , 1 ));
        this.foods.add(new Food(2,"Burger" , R.drawable.burger , 250 , 2)  );
        this.foods.add(new Food(1,"Pizza 2" , R.drawable.pizza2 , 600 , 3));
        this.foods.add(new Food(5,"Tacos" , R.drawable.tacos , 350 , 4));
        this.foods.add(new Food(5,"Tacos 2" , R.drawable.tacos2 , 400 , 5));
        this.foods.add(new Food(3,"Coca Cola 2.5L" , R.drawable.coca , 150 , 6));
        this.foods.add(new Food(3,"PepsiL" , R.drawable.pepsi , 130 , 7));
        this.foods.add(new Food(2,"Burger 2" , R.drawable.burger1 , 250 , 8)  );
        this.foods.add(new Food(2,"Burger 3" , R.drawable.burger2 , 250 , 9)  );
        this.foods.add(new Food(2,"Burger 4", R.drawable.burger , 250 , 10)  );
        this.foods.add(new Food(1,"Pizza 2" , R.drawable.pizza , 600, 11));
    }*/

       ProgressDialog progressDialog = new ProgressDialog(this.getContext());
         progressDialog.setMessage("getting data ..");
         progressDialog.setCancelable(false);
         progressDialog.show();


        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        StringRequest request = new StringRequest(Request.Method.GET, HELPER.FOODS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject responseJson = new JSONObject(response);
                    if(responseJson.getBoolean("success")) {
                        JSONArray data_jsonArray = responseJson.getJSONArray("message");
                        for (int x= 0 ; x<data_jsonArray.length() ; x++) {
                            JSONObject jsonData = (JSONObject) data_jsonArray.get(x);

                            foods.add(new Food(jsonData.getInt("category_id") ,
                                    jsonData.getString("name") ,
                                    jsonData.getString("image") ,
                                    jsonData.getInt("price"),
                                    jsonData.getInt("id") ,
                                    jsonData.getString("description")
                                    ));
                        }

                        food_adapter = new Food_Adapter(getContext() , filter_food(foods , 1) );
                        foodRV = view.findViewById(R.id.food_RV);
                        foodRV.setAdapter(food_adapter);

                        LinearLayoutManager ll = new LinearLayoutManager(getContext());
                        ll.setOrientation(RecyclerView.HORIZONTAL);

                        foodRV.setLayoutManager(ll);
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                 }


            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Fragment selected_Error = null;
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {

                    selected_Error = new Network_error_Fragment();
                } else {
                    selected_Error = new Error_Fragment();
                }
                ((FragmentActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , selected_Error).commit();

            }
        });

        queue.add(request);

    }



    public static ArrayList<Food> filter_food ( ArrayList<Food> foods , int category_id) {
        ArrayList<Food> result = new ArrayList<>();
        for (int x = 0 ; x<foods.size() ; x++) {
            if( category_id == foods.get(x).getCategory_id() ) {result.add(foods.get(x)); } }
        return  result;
    }


}
