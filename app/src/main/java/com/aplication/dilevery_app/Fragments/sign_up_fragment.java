package com.aplication.dilevery_app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.aplication.dilevery_app.R;

public class sign_up_fragment extends Fragment {

    private View sing_up_View;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.sing_up_View = inflater.inflate(R.layout.regester_fragment , container , false);
        return this.sing_up_View;
    }
}
