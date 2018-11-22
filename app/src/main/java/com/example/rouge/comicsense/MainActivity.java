package com.example.rouge.comicsense;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.rouge.comicsense.Adapter.ComicAdapter;
import com.example.rouge.comicsense.Model.Comic;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private RequestQueue requestQueue;

    private List<Comic> comicList;
    private List<Comic> result;
    private List<Comic> reset;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        requestQueue = Volley.newRequestQueue(this);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        comicList = new ArrayList<>();
        reset = new ArrayList<>();
        adapter = new ComicAdapter(this, comicList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        String COMIC_URL = "https://xkcd.com/info.0.json";

        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://xkcd.com/2063/info.0.json");
        urls.add("https://xkcd.com/2064/info.0.json");
        urls.add("https://xkcd.com/2065/info.0.json");
        urls.add("https://xkcd.com/2066/info.0.json");
        urls.add("https://xkcd.com/2067/info.0.json");
        urls.add("https://xkcd.com/2068/info.0.json");
        urls.add("https://xkcd.com/2069/info.0.json");
        urls.add("https://xkcd.com/2070/info.0.json");
        urls.add("https://xkcd.com/2071/info.0.json");
        urls.add("https://xkcd.com/2072/info.0.json");

        String startUrl = "https://xkcd.com/";
        String endUrl = "/info.0.json";
        int nr = 2075;

        while(nr > 0) {
            getJsonComic(startUrl + nr + endUrl);
            nr--;
        }
        //getJsonComic(COMIC_URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        final MenuItem searhItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searhItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        result = new ArrayList<>();

        for(Comic comic : reset)
            if (comic.getTitle().toLowerCase().contains(query) || String.valueOf(comic.getNum()).equals(query)) {
                result.add(comic);
            }

        comicList.clear();
        comicList.addAll(result);

        if(query.equals("")) {
            comicList.addAll(reset);
        }
        adapter.notifyDataSetChanged();

        return false;
    }

    private void getJsonComic(String s) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, s, null, new Response.Listener<JSONObject>() {
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
                        reset.add(comic);

                        Log.d("getJson", comic.toString());
                        /*
                        if(currentComicNr == 0) {
                            newestComicNr = comic.getNum();
                            currentComicNr = newestComicNr;
                        }*/
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("volley", error.toString());
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

}