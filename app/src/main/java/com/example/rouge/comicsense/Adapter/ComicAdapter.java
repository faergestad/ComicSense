package com.example.rouge.comicsense.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.example.rouge.comicsense.Model.Comic;
import com.example.rouge.comicsense.R;

import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {

    private List<Comic> comics;
    private Context context;

    public ComicAdapter(Context context, List<Comic> comics) {
        super();
        this.context = context;
        this.comics = comics;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_comic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ComicAdapter.ViewHolder holder, int position) {

        final Comic comic = comics.get(position);

        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Left, holder.swipeLayout.findViewById(R.id.bottom_wrapper1));
        holder.swipeLayout.addDrag(SwipeLayout.DragEdge.Right, holder.swipeLayout.findViewById(R.id.bottom_wrapper));
        holder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });

        holder.btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Added #" +
                        comic.getNum() + " to favorites", Toast.LENGTH_SHORT).show();
            }
        });

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked on Share " +
                        holder.titleTxtView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "Clicked on Edit  " +
                        holder.titleTxtView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        final String comicNr = "#" + comic.getNum();

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

        SwipeLayout swipeLayout;
        ImageButton btnLocation;
        TextView titleTxtView, descTxtView, nrTxtView, edit, share;
        ImageView comicImage;

        ViewHolder(View itemView) {
            super(itemView);

            swipeLayout = itemView.findViewById(R.id.swipe);
            edit = itemView.findViewById(R.id.edit);
            share = itemView.findViewById(R.id.share);
            btnLocation = itemView.findViewById(R.id.btnLocation);

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

