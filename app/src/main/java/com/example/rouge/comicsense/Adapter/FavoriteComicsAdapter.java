package com.example.rouge.comicsense.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rouge.comicsense.Database.DatabaseHelper;
import com.example.rouge.comicsense.Model.Comic;
import com.example.rouge.comicsense.R;

import java.util.List;

public class FavoriteComicsAdapter extends RecyclerView.Adapter<FavoriteComicsAdapter.ViewHolder> {

    private List<Comic> comics;
    private Context context;
    private DatabaseHelper db;

    public FavoriteComicsAdapter(Context context, List<Comic> comics) {
        super();
        this.context = context;
        this.comics = comics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_comic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        final Comic comic = comics.get(i);

        final String comicNr = "#" + comic.getNum();

        holder.comicImage.setImageBitmap(convertToBitmap(comic.getImgBytes()));
        holder.titleTxtView.setText(comic.getTitle());
        holder.nrTxtView.setText(comicNr);
        holder.descTxtView.setText(comic.getAlt());
    }

    private Bitmap convertToBitmap(byte[] imgBytes) {
        return BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTxtView, descTxtView, nrTxtView;
        ImageView comicImage;

        ViewHolder(View itemView) {
            super(itemView);

            comicImage = itemView.findViewById(R.id.main_img);
            titleTxtView = itemView.findViewById(R.id.main_title);
            nrTxtView = itemView.findViewById(R.id.main_description);
            descTxtView = itemView.findViewById(R.id.main_nr);
        }

        @Override
        public void onClick(View v) {
        }
    }

}
