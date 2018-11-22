package com.example.rouge.comicsense.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.rouge.comicsense.Model.Comic;
import com.example.rouge.comicsense.R;

import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {

    List<Comic> comics;
    private Context context;

    public ComicAdapter(Context context, List<Comic> comics) {
        super();
        this.context = context;
        this.comics = comics;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ComicAdapter.ViewHolder holder, int position) {

        Comic comic = comics.get(position);

        String comicNr = "#" + comic.getNum();

        Glide.with(context).load(comic.getImg()).into(holder.comicImage);
        holder.titleTxtView.setText(comic.getTitle());
        holder.nrTxtView.setText(comicNr);
        holder.descTxtView.setText(comic.getAlt());

    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView comicImage;
        public TextView titleTxtView, descTxtView, nrTxtView;

        public ViewHolder(View itemView) {
            super(itemView);

            comicImage = itemView.findViewById(R.id.main_img);
            titleTxtView = itemView.findViewById(R.id.main_title);
            nrTxtView = itemView.findViewById(R.id.main_nr);
            descTxtView = itemView.findViewById(R.id.main_rating);
        }

        @Override
        public void onClick(View v) {
            // TODO add onclick on comics
        }
    }
}

