package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{

    //Declaring global variables
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private MovieAdapter.MovieAdapterOnClickHandler clickHandler;
    private int popularPath = 1;
    private int topRatedPath = 2;
    Context context = this;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_movies);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        clickHandler = this;

        new MovieAsyncTask().execute(MovieUtils.buildUrl(popularPath));

        GridLayoutManager layoutManager;

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager= new GridLayoutManager(context, 2);
        }
        else{
            layoutManager= new GridLayoutManager(context, 3);
        }


        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(movieArrayList, clickHandler);
        recyclerView.setAdapter(movieAdapter);
    }

    /*
    ** This method inflates the sort menu in the action bar.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sort, menu);
        return true;
    }

    /*
    ** This method performs the corresponding sort operation when the menu item is clicked.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId){
            case R.id.action_popular:
                new MovieAsyncTask().execute(MovieUtils.buildUrl(popularPath));
                return true;

            case R.id.action_most_rated:
                new MovieAsyncTask().execute(MovieUtils.buildUrl(topRatedPath));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra("MovieObject", movie);
        startActivity(intent);
    }

    /**
     * This is the Movie AsyncTask which takes in the URL and returns the movie details as ArrayList.
     */

    public class MovieAsyncTask extends AsyncTask<URL, Void, ArrayList<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String json;
            try {
                json = MovieUtils.getResponseFromHttpUrl(searchUrl);
                movieArrayList = MovieJsonUtils.getDetailsFromJson(json);
                return movieArrayList;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return movieArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> moviesList) {
            super.onPostExecute(moviesList);
            if(moviesList == null || moviesList.isEmpty()){
                mErrorMessageDisplay.setVisibility(View.VISIBLE);
            }
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            movieAdapter.setMovieList(moviesList);
        }
    }


}
