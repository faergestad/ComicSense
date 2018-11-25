package com.example.rouge.comicsense.Fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rouge.comicsense.Adapter.FavoriteComicsAdapter;
import com.example.rouge.comicsense.Database.DatabaseHelper;
import com.example.rouge.comicsense.Model.Comic;
import com.example.rouge.comicsense.R;

import java.util.ArrayList;
import java.util.List;

public class FavoritesFragment extends Fragment {

    private List<Comic> favoriteComics;
    private Context context;
    private DatabaseHelper db;
    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private Comic comic;
    private Bitmap bitmap;
    private byte[] imgByte;

    public FavoritesFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHelper(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        favoriteComics = new ArrayList<>();

        adapter = new FavoriteComicsAdapter(getContext(), favoriteComics);

        linearLayoutManager = new LinearLayoutManager(getContext());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_favs);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        getComicsFromDB();

        return view;
    }

    private void getComicsFromDB() {
        favoriteComics.addAll(db.getAllFavorites());
        adapter.notifyDataSetChanged();
    }

}
