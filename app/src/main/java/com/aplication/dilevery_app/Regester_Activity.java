package com.aplication.dilevery_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regester_Activity extends AppCompatActivity {

    private EditText eName , eEmail , ePassword , eConfirm_password;
    private Button btnRegister;
    private boolean mPassword_Visible = false;
    SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regester);
        this.init();

        this.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                register();


            }
        });

        this.ePassword.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final  int right = 2;
                if(event.getAction() == MotionEvent.ACTION_UP){
                    if (event.getRawX()>=ePassword.getRight() - ePassword.getCompoundDrawables()[right].getBounds().width()) {
                        if(mPassword_Visible) {

                            ePassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.password_selector,0, R.drawable.ic_visibility_off , 0);
                            ePassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            mPassword_Visible = false;

                        } else  {
                            ePassword.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.password_selector,0, R.drawable.ic_visibility_on , 0);
                            ePassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            mPassword_Visible = true;
                        }
                    }
                }
                return  false;
            }

        });



    }


    private void  init( ) {
        this.eName = findViewById(R.id.user_name_for_register);
        this.eEmail = findViewById(R.id.email_FoR_register);
        this.ePassword = findViewById(R.id.password_for_register);
        this.eConfirm_password = findViewById(R.id.confirm_password_for_register);
        this.btnRegister = findViewById(R.id.mBtn_Register);
    }

    private boolean Valid_name ( String name) {
      //  Pattern pattern = Pattern.compile("[a-z][A-Z]");
        // Matcher mMatcher = pattern.matcher(name);
        return  name.isEmpty();
    }
    private boolean Valid_Email ( String email) {
        Pattern pattern = Pattern.compile("[a-z][A-Z]");
        Matcher mMatcher = pattern.matcher(email);
        return  mMatcher.matches();
    }
    private boolean Valid_Password ( String password) {
        Pattern pattern = Pattern.compile("[a-z][A-Z]");
        Matcher mMatcher = pattern.matcher(password);
        return  mMatcher.matches();
    }
    private boolean is_Password_Confirmed ( String password , String confirm_Password) {
           return password.equals(confirm_Password);
    }



    private void register () {

        this.mSharedPreferences = getSharedPreferences("User_Data" , MODE_PRIVATE);

        StringRequest mStringRequest = new StringRequest(Request.Method.POST, HELPER.REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject mJSONResponse = new JSONObject(response);
                     if( mJSONResponse.getBoolean("success") ) {

                         SharedPreferences.Editor mEditor = mSharedPreferences.edit();

                         JSONObject mJSONUser = mJSONResponse.getJSONObject("user");

                         mEditor.putBoolean("is_login" , true);
                         mEditor.putInt("id" , mJSONUser.getInt("id") );
                         mEditor.putString("name" , mJSONUser.getString("full_name"));
                         mEditor.putString("email" , mJSONUser.getString("email"));
                         mEditor.putString("phone_number" , mJSONUser.getString("phone_number"));
                         mEditor.putString("token" , mJSONResponse.getString("token"));
                         mEditor.putString("photo" , mJSONResponse.getString("profile_photo_url"));

                         mEditor.apply();
                         Toast.makeText(getApplicationContext(), "Register Success :)", Toast.LENGTH_SHORT).show();
                         Intent intent = new Intent(getApplicationContext() , MainActivity.class);
                         startActivity(intent);
                         finish();

                     } else {
                         Toast.makeText(getApplicationContext(), "something wrong!", Toast.LENGTH_SHORT).show();
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

           getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout , selected_Error).commit();

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<String , String>();
                params.put("full_name" , eName.getText().toString());
                params.put("email" , eEmail.getText().toString());
                params.put("password" , ePassword.getText().toString());
                params.put("phone_number" , mSharedPreferences.getString("phone_number" , ""));

                return  params;
            }
        };
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(mStringRequest);
    }

}