package com.dvb.tmovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
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

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private PopularMovie[] mPopularMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "?api_key=";
        String siteUrl = "https://api.themoviedb.org/3/movie/";
        String sortBy = "popular";
        String movie_id = "565";

        final String movieIdRequestUrl = siteUrl + movie_id + apiKey;
        final String popularMovieRequestUrl = siteUrl + sortBy + apiKey;

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(siteUrl)
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
                            mPopularMovie = getMovieData(jsonData);
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

    }



    private PopularMovie[] getMovieData(String jsonData) throws JSONException {
        JSONObject movie = new JSONObject(jsonData);
        JSONArray movieResults = movie.getJSONArray("results");

        PopularMovie[] movies = new PopularMovie[movieResults.lenght()];

        for (int i = 0; i < movieResults.length(); i++){
            JSONObject jsonMovie = movieResults.getJSONObject(i);
            PopularMovie aMovie = new PopularMovie();

            aMovie.setPoster_path(jsonMovie.getString("poster_path"));
            aMovie.setTitle(jsonMovie.getString("title"));

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
