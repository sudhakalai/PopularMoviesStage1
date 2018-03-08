package com.example.android.popularmoviesstage1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * This class displays the details of the movie being clicked.
 */

public class MovieDetailsActivity extends AppCompatActivity {

    //Declaring the variables
    private TextView nameTv;
    private TextView plotTv;
    private TextView ratingTv;
    private TextView releaseTv;
    private ImageView posterIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        //Initialising the views
        nameTv = findViewById(R.id.tv_movie_name);
        plotTv = findViewById(R.id.tv_movie_plot);
        ratingTv = findViewById(R.id.tv_movie_rating);
        releaseTv = findViewById(R.id.tv_movie_release);
        posterIv = findViewById(R.id.iv_poster);

        //Getting the value from the intent
        Intent intent = getIntent();
        Movie movie;
        //if (intent != null) {
            //if (intent.hasExtra(Intent.EXTRA_TEXT)) {
                movie = (Movie) intent.getSerializableExtra("MovieObject");

                //Setting the values to the views
                nameTv.setText(movie.getOrginalTitle());
                plotTv.setText(movie.getPlotAnalysis());
                ratingTv.setText(movie.getVoteAverage());
                releaseTv.setText(movie.getReleaseDate());

                String posterPath = "http://image.tmdb.org/t/p/w780/" + movie.getImageUrl();

                //Drawing the poster using picasso library.
                Picasso.with(this)
                        .load(posterPath)
                        .into(posterIv);
           // }
       // }
    }
}
