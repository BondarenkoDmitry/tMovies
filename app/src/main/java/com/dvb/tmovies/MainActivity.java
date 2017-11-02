package com.dvb.tmovies;

import android.content.Context;
import android.graphics.Movie;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.R.attr.apiKey;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    RecyclerView mRecyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

//    In your main activity you are calling mPopMovie
//    mPopMovie is array of movies
//    mPopMovie = getMovieData(jsonData);

//    and for adapter your are usnig Arraylist
//    mPopMovies is array list
//    mAdapter = new PopMovieAdapter(mPopMovies);
//    that is the reson your list is empty
//    use Arraylist insted of simple list
//    =====
//    was wrong only use arraylist no need of object of array
//    mPopMovies(--Arraylist--) = getMovieData(jsonData);
//    getmoviedata() method return type must be ArrayList

    // Object of array, I'm getting data here
    private PopMovie[] mPopMovie;

    // ArrayList, I'm using it for adapter
    private ArrayList<PopMovie> mPopMovies = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);



        String apiKey = "?api_key=957c988676c0d274a6d1cc76dd5c8a93";
        String siteUrl = "https://api.themoviedb.org/3/movie/";
        String sortBy = "popular";
        String movie_id = "565";

        final String popularMovieRequestUrl = siteUrl + sortBy + apiKey;

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(popularMovieRequestUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);

                        if (response.isSuccessful()) {
                            mPopMovie = getMovieData(jsonData);
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                    catch (JSONException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        } else {
            Toast.makeText(this, getString(R.string.network_unavailable_message),
                    Toast.LENGTH_LONG).show();
        }

        Log.d(TAG, "Main UI code is running!");



        mAdapter = new PopMovieAdapter(mPopMovies);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }




    private PopMovie[] getMovieData(String jsonData) throws JSONException {

        JSONObject movie = new JSONObject(jsonData);
        JSONArray movieResults = movie.getJSONArray("results");

        PopMovie[] movies = new PopMovie[movieResults.length()];

        for (int i = 0; i < movieResults.length(); i++){
            JSONObject jsonMovie = movieResults.getJSONObject(i);
            PopMovie aMovie = new PopMovie();

            aMovie.setTitle(jsonMovie.getString("title"));
            aMovie.setPoster_path(jsonMovie.getString("poster_path"));

            movies[i] = aMovie;
        }

        return movies;
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogueFragment dialog = new AlertDialogueFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
}










