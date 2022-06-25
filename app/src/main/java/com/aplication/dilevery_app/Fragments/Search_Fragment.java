package com.aplication.dilevery_app.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.Locale;

import Adapters.Favourite_adapter;
import Adapters.Food_Adapter;
import Adapters.Search_Food_adapter;
import Models.Favourite_model;
import Models.Food;

public class Search_Fragment extends Fragment {
private View view;
private RecyclerView mRecyclerView;
private ArrayList<Food> mFoods ;
private EditText sEditText;
private Search_Food_adapter search_Food_adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.search_fragment , container , false );

       this.init( );
        return  view;
    }

    private void init() {
    this.mRecyclerView = this.view.findViewById(R.id.searchRv);
    this.sEditText = this.view.findViewById(R.id.search_edit_text);
    this.get_All_foods();

    this.sEditText.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            filter(s.toString().toLowerCase());
        }


    });

    }
    private void filter(String query) {
        ArrayList<Food> filtered_ArrayList = new ArrayList<>();
        for (int x = 0 ; x< this.mFoods.size() ; x++ ) {
            Food current_food = mFoods.get(x);
            if(current_food.getName().toLowerCase().contains(query)) {
                filtered_ArrayList.add(current_food);
            }

        }

        this.search_Food_adapter.setmFoods(filtered_ArrayList);
        this.search_Food_adapter.notifyDataSetChanged();

    }
    private  void get_All_foods ( ) {
        this.mFoods = new ArrayList<>();

        ProgressDialog mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Getting Data");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();


        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        StringRequest request = new StringRequest(Request.Method.GET, HELPER.FOODS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("{-> Response <-}" + response);

                try {
                    JSONObject responseJson = new JSONObject(response);
                    if(responseJson.getBoolean("success")) {
                        JSONArray data_jsonArray = responseJson.getJSONArray("message");
                        for (int x= 0 ; x<data_jsonArray.length() ; x++) {
                            JSONObject jsonData = (JSONObject) data_jsonArray.get(x);

                            mFoods.add(new Food(
                                    jsonData.getInt("category_id"),
                                    jsonData.getString("name") ,
                                    jsonData.getString("image") ,
                                    jsonData.getInt("price"),
                                    jsonData.getInt("id") ,
                                    jsonData.getString("description")
                            ));
                        }

                        search_Food_adapter = new Search_Food_adapter(  mFoods , getContext());
                        mRecyclerView.setAdapter(search_Food_adapter);
                        mProgressDialog.dismiss();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mProgressDialog.dismiss();

                }


            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Fragment selected_Error = null;
                mProgressDialog.dismiss();
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



}
