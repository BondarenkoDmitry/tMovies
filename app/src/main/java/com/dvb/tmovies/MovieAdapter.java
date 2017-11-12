package com.dvb.tmovies;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dvb.tmovies.transformation.RoundedTransformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dmitrybondarenko on 11.10.17.
 */

public class MovieAdapter extends RecyclerView.Adapter
        <MovieAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<Movie> mPopMovies = new ArrayList<Movie>();

    //    Maybe a bit later I'll fetch data directly from here.
    public MovieAdapter(ArrayList<Movie> arrayList){
        this.mPopMovies = arrayList;
    }

    public ArrayList<Movie> getPopMovies() {
        return mPopMovies;
    }

    public void setPopMovies(ArrayList<Movie> popMovies) {
        mPopMovies = popMovies;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popular_movie_item, parent, false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        Movie popMovie = mPopMovies.get(position);

        holder.title.setText(popMovie.getTitle());

        // Pass the context that can be retrieved from itemview
        Picasso.with(holder.itemView.getContext())
                .load("https://image.tmdb.org/t/p/w185" + popMovie.getPoster_path())
                .transform(new RoundedTransformation(20, 5))
                // TODO aaa resource doesn't exist, I replaced it with launcher image in case of error
                .error(R.mipmap.ic_launcher)
                .into(holder.poster_path);

        /* FIXME Assets should be pushed to GIT, this crashes on my side
        Typeface latoBlack = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "fonts/Lato-Black.ttf");
        holder.title.setTypeface(latoBlack);*/
    }

    @Override
    public int getItemCount() {
        if (mPopMovies == null){
            return 0;
        }
        return mPopMovies.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView poster_path;
        TextView title;

        public RecyclerViewHolder(View view){
            super(view);

            poster_path = (ImageView)view.findViewById(R.id.img);
            title = (TextView)view.findViewById(R.id.f_name);

        }

    }

}
