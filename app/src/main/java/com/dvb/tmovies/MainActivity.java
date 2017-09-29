package com.dvb.tmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "?api_key=957c988676c0d274a6d1cc76dd5c8a93";
//        https://api.themoviedb.org/3/movie/503?api_key=957c988676c0d274a6d1cc76dd5c8a93&language=en-US
        String siteUrl = "https://api.themoviedb.org/3/movie/";
        String sortBy = "popular";

        final String requestUrl = siteUrl + sortBy + apiKey;

        if (isNetworkAvailable()) {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(requestUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {

                }

                @Override
                public void onResponse(Response response) throws IOException {
                    try{
                        Log.v(TAG, response.body().string());
                        if (response.isSuccessful()) {
                        } else {
                               alertUserAboutError();
                            }
                        } catch (IOException e){
                        Log.e(TAG, "Exception caught:", e);
                    }

                }
            });
        }
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
