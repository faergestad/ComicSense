package com.example.rouge.comicsense.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.rouge.comicsense.Model.Comic;
import com.example.rouge.comicsense.R;

import org.json.JSONObject;


public class NewestComicFragment extends Fragment {

    private TextView titleTxtView, descTxtView, nrTxtView;
    private ImageView comicImg;

    public NewestComicFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_newest_comic, container, false);

        titleTxtView = view.findViewById(R.id.main_title);
        descTxtView = view.findViewById(R.id.main_description);
        nrTxtView = view.findViewById(R.id.main_nr);
        comicImg = view.findViewById(R.id.main_img);

        getNewestComic();

        return view;
    }

    private void getNewestComic() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, "https://xkcd.com/info.0.json", null, new Response.Listener<JSONObject>() {
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

                        String nrTxt = "#" + String.valueOf(comic.getNum());

                        titleTxtView.setText(comic.getTitle());
                        descTxtView.setText(comic.getAlt());
                        nrTxtView.setText(nrTxt);
                        Glide.with(getActivity()).load(comic.getImg()).into(comicImg);

                        Log.d("Newest", comic.toString());

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("volley", error.toString());
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);

    }

}
