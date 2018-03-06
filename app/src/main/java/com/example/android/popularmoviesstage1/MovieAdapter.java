package com.example.android.popularmoviesstage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kalaimaran on 02-Mar-18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{

    private Context mContext;
    private ArrayList<Movie> mMovies = new ArrayList<>();

    public MovieAdapter(Context context, ArrayList<Movie> movies){
         mContext= context;
         mMovies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.movie_grid_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        holder.movie = mMovies.get(position);

        String movieName = holder.movie.getOrginalTitle();

        holder.bind(movieName);

    }

    @Override
    public int getItemCount() {
        if(mMovies!= null){
            return mMovies.size();
        }else{
            return 0;
        }
    }

    class MovieViewHolder extends RecyclerView.ViewHolder{

        public TextView movieNameTV;
        public Movie movie;

        public MovieViewHolder(View itemView) {
            super(itemView);

            movieNameTV = itemView.findViewById(R.id.tv_movie_name);
        }

        public void bind(String movieName){
            movieNameTV.setText(movieName);
        }
    }
}
