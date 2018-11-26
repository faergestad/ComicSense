package com.example.rouge.comicsense.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.daimajia.swipe.SwipeLayout;
import com.example.rouge.comicsense.Database.DatabaseHelper;
import com.example.rouge.comicsense.Model.Comic;
import com.example.rouge.comicsense.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;

public class ComicAdapter extends RecyclerView.Adapter<ComicAdapter.ViewHolder> {

    private List<Comic> comics;
    private Context context;

    public ComicAdapter(Context context, List<Comic> comics) {
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
    public void onBindViewHolder(@NonNull final ComicAdapter.ViewHolder holder, int position) {

        final Comic comic = comics.get(position);

        final String comicNr = "#" + comic.getNum();

        Glide.with(context).load(comic.getImg()).into(holder.comicImage);
        holder.titleTxtView.setText(comic.getTitle());
        holder.nrTxtView.setText(comicNr);
        holder.descTxtView.setText(comic.getAlt());

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

        holder.btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Added #" +
                        comic.getNum() + " to favorites", Toast.LENGTH_SHORT).show();

                addComicToFavs(comic);

                Log.d("Favorite", "" + holder.comicImage.getDrawable());
            }
        });

        holder.btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);

                share.putExtra(Intent.EXTRA_SUBJECT, "xkcd: " + comic.getTitle());
                share.putExtra(Intent.EXTRA_TEXT, "https://xkcd.com/" + comic.getNum());

                context.startActivity(Intent.createChooser(share, "Share comic"));
            }
        });

        holder.btnExplanation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new getExplanation(context).execute("https://www.explainxkcd.com/wiki/index.php/" + comic.getNum());

                Toast.makeText(context, "Getting explanation..", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void addComicToFavs(Comic comic) {
        new DatabaseHelper(context).addComic(comic);
    }

    @Override
    public int getItemCount() {
        return comics.size();
    }

    public static class getExplanation extends AsyncTask {
        private WeakReference<Context> contextRef;

        private String output = "Hm.. The server with explanations seems to be down";

        getExplanation(Context context) {
            contextRef = new WeakReference<>(context);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            StringBuilder explanation = new StringBuilder();
            Document doc;
            Log.d("getExplanation", "" + params[0]);
            try {
                doc = Jsoup.connect((String) params[0]).get();
                Elements paragraphs = doc.select("p");

                for (Element p : paragraphs) {
                    explanation.append(p.text());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return String.valueOf(explanation);
        }

        @Override
        protected void onPostExecute(Object result) {
            Context context = contextRef.get();
            if (context != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                if (result.equals("")) {
                    builder.setMessage("Hm.. The explanation server seems to be down. Terribly sorry. Try again later");
                    builder.setPositiveButton("Unacceptable!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                } else {
                    builder.setMessage(String.valueOf(result));
                    builder.setPositiveButton("Haha, I get it!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                        }
                    });
                }
                builder.show();
            }
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        SwipeLayout swipeLayout;
        ImageButton btnFavorites;
        TextView titleTxtView, descTxtView, nrTxtView, btnExplanation, btnShare;
        ImageView comicImage;

        ViewHolder(View itemView) {
            super(itemView);

            swipeLayout = itemView.findViewById(R.id.swipe);
            btnExplanation = itemView.findViewById(R.id.explanation);
            btnShare = itemView.findViewById(R.id.share);
            btnFavorites = itemView.findViewById(R.id.btnFavorites);

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

