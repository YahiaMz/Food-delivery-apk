package com.aplication.dilevery_app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aplication.dilevery_app.R;


public class Activity_phone_number extends Fragment {
 private View phone_number_activity ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        phone_number_activity = inflater.inflate(R.layout.activity_phone_number , container , false);
        return this.phone_number_activity;
    }
}
