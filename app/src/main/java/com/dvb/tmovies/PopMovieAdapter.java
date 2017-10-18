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

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by dmitrybondarenko on 11.10.17.
 */

public class PopMovieAdapter extends RecyclerView.Adapter
        <PopMovieAdapter.RecyclerViewHolder> {

    private Context context;
    private ArrayList<PopMovie> mPopMovies = new ArrayList<PopMovie>();

    public PopMovieAdapter(ArrayList<PopMovie> arrayList){
        this.mPopMovies = arrayList;
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
        PopMovie popMovie = mPopMovies.get(position);

        holder.film_name.setText(popMovie.getTitle());

        Picasso.with(context)
                .load("https://image.tmdb.org/t/p/w185" + popMovie.getPoster_path())
                .transform(new RoundedTransformation(20, 5))
                .error(R.drawable.aaa)
                .into(holder.mImageView);

        Typeface latoBlack = Typeface.createFromAsset(context.getAssets(), "fonts/Lato-Black.ttf");
        holder.film_name.setTypeface(latoBlack);
    }

    @Override
    public int getItemCount() {
        if (mPopMovies == null){
            return 0;
        }
        return mPopMovies.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView film_name;

        public RecyclerViewHolder(View view){
            super(view);

            mImageView = (ImageView)view.findViewById(R.id.img);
            film_name = (TextView)view.findViewById(R.id.f_name);

        }

    }

}
