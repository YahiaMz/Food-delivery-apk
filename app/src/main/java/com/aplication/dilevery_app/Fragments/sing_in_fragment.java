package com.aplication.dilevery_app.Fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aplication.dilevery_app.HELPER;
import com.aplication.dilevery_app.MainActivity;
import com.aplication.dilevery_app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class sing_in_fragment extends Fragment {
    private View sign_inView;
   private Button mLoginBtn;
   private EditText email_ET , password_ET;
   private boolean mPassword_Visible = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.sign_inView = inflater.inflate(R.layout.login_fragment , container , false);

        this.init();
        return this.sign_inView;
    }

    private void init() {

        this.email_ET = this.sign_inView.findViewById(R.id.email_for_login);
        this.password_ET = this.sign_inView.findViewById(R.id.password_for_login);


        this.mLoginBtn = this.sign_inView.findViewById(R.id.login_btn);
    this.mLoginBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            login();
        }
    });

    this.password_ET.setOnTouchListener(new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            final  int right = 2;
            if(event.getAction() == MotionEvent.ACTION_UP){
                if (event.getRawX()>=password_ET.getRight() - password_ET.getCompoundDrawables()[right].getBounds().width()) {
                    if(mPassword_Visible) {

                        password_ET.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.password_selector,0, R.drawable.ic_visibility_off , 0);
                        password_ET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        mPassword_Visible = false;
                    } else  {
                        password_ET.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.password_selector,0, R.drawable.ic_visibility_on , 0);
                        password_ET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        mPassword_Visible = true;
                    }
                }
            }
         return  false;
        }

    });
    }

    private void login( ){

     /*   ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("logging ..");
        progressDialog.show();
*/

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, HELPER.LOGIN, new Response.Listener<String>() {
            @SuppressLint("ResourceType")
            @Override
            public void onResponse(String response) {


               try {
                    JSONObject mResponseJsonObject = new JSONObject(response);
                    if(mResponseJsonObject.getBoolean("success")) {

                        SharedPreferences mSharedPreferences = getContext().getSharedPreferences("User_Data"  , Context.MODE_PRIVATE);
                        SharedPreferences.Editor mEditor = mSharedPreferences.edit();

                        JSONObject mUserData = mResponseJsonObject.getJSONObject("user");
                        mEditor.putInt("id", mUserData.getInt("id"));
                        mEditor.putString("name" , mUserData.getString("name"));
                        mEditor.putString("email" , mUserData.getString("email"));
                        mEditor.putBoolean("is_login" , true);
                        mEditor.putString("token" , mResponseJsonObject.getString("token"));
                        mEditor.putString("phone_number" , mUserData.getString("phone_number"));
                        mEditor.putString("photo" , mUserData.getString("profile_photo_url"));

                        mEditor.apply();


                        Toast.makeText(getContext(), "login Success", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getContext() , MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();


                    } else  {

                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                   Toast.makeText(getContext(), "In Catch", Toast.LENGTH_SHORT).show();


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                       Map<String ,String > params = new HashMap<>();
                       params.put("email" ,email_ET.getText().toString() );
                       params.put("password" , password_ET.getText().toString());

                       return  params;

            }
        };

        requestQueue.add(request);

    }

}

