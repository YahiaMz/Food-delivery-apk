package com.aplication.dilevery_app.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aplication.dilevery_app.R;

import java.util.ArrayList;

import Adapters.Favourite_adapter;
import Adapters.Food_Adapter;
import Models.Favourite_model;

public class Favorite_Fragment extends Fragment {
    private View view;
    RecyclerView favouriteRv ;
    Favourite_adapter favourite_adapter;
    private ArrayList<Favourite_model> favourites;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       fill_favorites();
        this.view = inflater.inflate(R.layout.favorites_fragment , container , false );
        this.favouriteRv = view.findViewById(R.id.favourite_RV);
        this.favourite_adapter = new Favourite_adapter( this.favourites , this.getContext());

        this.favouriteRv.setAdapter(this.favourite_adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext() , 2);
        gridLayoutManager.supportsPredictiveItemAnimations();


        this.favouriteRv.setLayoutManager(gridLayoutManager);
        return this.view;
    }


    private void fill_favorites() {
        this.favourites = new ArrayList<>();
        favourites.add(new Favourite_model(R.drawable.burger , "Burger" , 250 , 1));
        favourites.add(new Favourite_model(R.drawable.pizza1 , "Pizza Royal" , 300 ,1 ));
        favourites.add(new Favourite_model(R.drawable.pizza2 , "Pizza 2" , 600 , 3));
        favourites.add(new Favourite_model(R.drawable.coca , "Coca Cola 2.5L" , 150 , 6));
        favourites.add(new Favourite_model(R.drawable.tacos2 , "Tacos 2" , 400 , 5));
        favourites.add(new Favourite_model(R.drawable.tacos , "Tacos" , 350,4));


    }
}
