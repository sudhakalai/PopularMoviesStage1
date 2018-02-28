package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MovieAdapter.GridItemClickLitener {

    private MovieAdapter mAdapter;
    private RecyclerView mMovieGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMovieGrid = (RecyclerView) findViewById(R.id.rv_movies);
        int noOfColoumns = 2;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, noOfColoumns);
        mMovieGrid.setLayoutManager(gridLayoutManager);
        mMovieGrid.setHasFixedSize(true);
        mAdapter = new MovieAdapter(10, this);
        mMovieGrid.setAdapter(mAdapter);
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
                Toast.makeText(MainActivity.this, "Sorting by popularity", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_most_rated:
                Toast.makeText(MainActivity.this, "Sorting by most rated", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onGridItemClick(int clickedItemIndex) {
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        startActivity(intent);
    }
}
