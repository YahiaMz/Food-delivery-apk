package com.aplication.dilevery_app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aplication.dilevery_app.R;

public class Network_error_Fragment extends Fragment {
   private View mNetworkError_Fragment_view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mNetworkError_Fragment_view = inflater.inflate(R.layout.network_error_activity , container , false);
        return this.mNetworkError_Fragment_view;
    }
}
