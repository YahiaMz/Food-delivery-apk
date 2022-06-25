package com.aplication.dilevery_app.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aplication.dilevery_app.R;
import com.aplication.dilevery_app.Regeiter_Activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Activity_phone_number extends Fragment {
 private View phone_number_activity ;
    private Button sendPhoneNumberBtn;
    private EditText phoneN_E_T;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        phone_number_activity = inflater.inflate(R.layout.activity_phone_number , container , false);


        init();
        this.sendPhoneNumberBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneValidator()) {
                    Intent intent = new Intent(getContext() , Regeiter_Activity.class);

                    SharedPreferences mSharedPreferences = getContext().getSharedPreferences("User_Data" , Context.MODE_PRIVATE);
                    SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                    mEditor.putString("phone_number" , phoneN_E_T.getText().toString());
                    mEditor.apply();

                    startActivity(intent);
                } else {
                    phoneN_E_T.setError("Wrong Phone Number");
                }

            }
        });
        return this.phone_number_activity;
    }



    private  void init( ) {
        this.sendPhoneNumberBtn = phone_number_activity.findViewById(R.id.sendPhoneNumber_btn);
        this.phoneN_E_T = phone_number_activity.findViewById(R.id.phone_NumberEditTEXT);
    }

    private  boolean phoneValidator( ){
        Pattern pattern = Pattern.compile("[0][567][0-9]{8}");
        Matcher matcher = pattern.matcher(phoneN_E_T.getText());

        return  matcher.matches();
    }

}
