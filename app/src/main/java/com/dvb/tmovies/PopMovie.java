package com.dvb.tmovies;

/**
 * Created by dmitrybondarenko on 05.10.17.
 */

public class PopMovie {

    private String title;
    private String poster_path;

    public PopMovie(String title, String poster_path){
        this.setTitle(title);
        this.setPoster_path(poster_path);
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

}
