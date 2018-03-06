package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  {

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private static ArrayList<Movie> movieArrayList;
    private int popularPath = 1;
    private int topRatedPath = 2;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_movies);

        movieArrayList = new ArrayList<>();

       new MovieAsyncTask().execute(MovieUtils.buildUrl(popularPath));

        GridLayoutManager layoutManager= new GridLayoutManager(context, 2);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(context, movieArrayList);

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


    public static class MovieAsyncTask extends AsyncTask<URL, Void, ArrayList<Movie>> {

       @Override
       protected ArrayList<Movie> doInBackground(URL... urls) {
           URL searchUrl = urls[0];
           String json;
           movieArrayList = new ArrayList<>();
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
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);

        }
    }
}
