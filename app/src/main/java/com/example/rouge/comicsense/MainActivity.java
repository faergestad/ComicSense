package com.example.rouge.comicsense;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.rouge.comicsense.Model.Comic;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private int newestComicNr, currentComicNr;
    private TextView title, description;
    private ImageView comicImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String COMIC_URL = "https://xkcd.com/info.0.json";

        title = findViewById(R.id.comic_title);
        comicImg = findViewById(R.id.comic_img);
        description = findViewById(R.id.comic_description);
        Button previous = findViewById(R.id.previous);
        Button next = findViewById(R.id.next);

        getJsonComic(COMIC_URL);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previousComic = currentComicNr - 1;
                currentComicNr = previousComic;

                String startUrl = "https://xkcd.com/";
                String endUrl = "/info.0.json";

                getJsonComic(startUrl + previousComic + endUrl);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startUrl = "https://xkcd.com/";
                String endUrl = "/info.0.json";
                int nextComic = currentComicNr + 1;
                currentComicNr = nextComic;

                if(nextComic > newestComicNr) {
                    Toast.makeText(getApplicationContext(), "No comic after this, I'm afraid..", Toast.LENGTH_SHORT).show();
                    //Log.d("nummer", "newest " + newestComicNr);
                    //Log.d("nummer", "current " + currentComicNr);
                } else {
                    //Toast.makeText(getApplicationContext(), "" + nextComic, Toast.LENGTH_SHORT).show();
                    getJsonComic(startUrl + nextComic + endUrl);
                }
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
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

                        title.setText(comic.getTitle());
                        Glide.with(getApplicationContext()).load(comic.getImg()).into(comicImg);
                        description.setText(comic.getAlt());

                        if(currentComicNr == 0) {
                            newestComicNr = comic.getNum();
                            currentComicNr = newestComicNr;
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("volley", error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

}