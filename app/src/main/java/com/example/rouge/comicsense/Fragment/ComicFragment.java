package com.example.rouge.comicsense.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rouge.comicsense.Adapter.ComicAdapter;
import com.example.rouge.comicsense.Model.Comic;
import com.example.rouge.comicsense.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ComicFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RequestQueue requestQueue;

    private List<Comic> comicList;
    private List<Comic> reset;

    private RecyclerView.Adapter adapter;
    private LinearLayoutManager linearLayoutManager;

    private int visibleItemCount;
    private int totalItemCount;
    private int pastVisibleItems;
    private int nr;
    private int counter;
    private int NEWEST_COMICNR;

    public ComicFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic, container, false);

        comicList = new ArrayList<>();

        adapter = new ComicAdapter(getContext(), comicList);

        requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        reset = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // ScrollListener that loads new comics when needed to speed up loading
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                        Log.d("Loading:", "reached last item. It was: " + nr);

                        getComics();
                    }
                }

                super.onScrolled(recyclerView, dx, dy);
            }
        });
        // Gets the newest comicnr
        SharedPreferences pref = getActivity().getSharedPreferences("NewestComic", 0);
        NEWEST_COMICNR = pref.getInt("newest", 0);
        nr = NEWEST_COMICNR;
        Log.d("comicnr", "comic #" + nr);

        getComics();
        getAllComics();

        return view;
    }

    private void getAllComics() {
        int countDown = NEWEST_COMICNR;
        while (countDown > 0) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, "https://xkcd.com/" + countDown + "/info.0.json", null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Comic comic = new Comic();
                            comic.setMonth(response.optString("month"));
                            comic.setNum(response.optInt("num"));
                            comic.setLink(response.optString("link"));
                            comic.setLink(response.optString("year"));
                            comic.setNews(response.optString("news"));
                            comic.setSafeTitle(response.optString("safe_title"));
                            comic.setTranscript(response.optString("transcript"));
                            comic.setAlt(response.optString("alt"));
                            comic.setImg(response.optString("img"));
                            comic.setTitle(response.optString("title"));
                            comic.setDay(response.optString("day"));

                            reset.add(comic);

                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("volley", error.toString());
                        }
                    });
            requestQueue.add(jsonObjectRequest);

            countDown--;
        }
    }

    public void getComics() {
        int visibleLimit = 5;
        while (counter < visibleLimit) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, "https://xkcd.com/" + nr + "/info.0.json", null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Comic comic = new Comic();
                            comic.setMonth(response.optString("month"));
                            comic.setNum(response.optInt("num"));
                            comic.setLink(response.optString("link"));
                            comic.setLink(response.optString("year"));
                            comic.setNews(response.optString("news"));
                            comic.setSafeTitle(response.optString("safe_title"));
                            comic.setTranscript(response.optString("transcript"));
                            comic.setAlt(response.optString("alt"));
                            comic.setImg(response.optString("img"));
                            comic.setTitle(response.optString("title"));
                            comic.setDay(response.optString("day"));

                            comicList.add(comic);

                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("volley", error.toString());
                        }
                    });
            requestQueue.add(jsonObjectRequest);
            nr--;
            Log.d("Loading", "nr after-- " + nr);
            counter++;
            Log.d("Loading", "counter " + counter);
        }
        counter = 0;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflates search menu in appbar
        inflater.inflate(R.menu.search, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        List<Comic> result = new ArrayList<>();

        for (Comic comic : reset)
            if (comic.getTitle().toLowerCase().contains(query) || String.valueOf(comic.getNum()).equals(query)) {
                result.add(comic);
            }

        comicList.clear();
        comicList.addAll(result);

        if (query.equals("")) {
            comicList.addAll(reset);
        }
        adapter.notifyDataSetChanged();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        // Is supposed to clear the search and reset the result by getting the newest comics, again
        if (query.equals("")) {
            comicList.clear();
            adapter.notifyDataSetChanged();
            getComics();
            nr = NEWEST_COMICNR;
        }
        return false;
    }

}
