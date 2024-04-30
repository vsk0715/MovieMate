package com.project.moviemate;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class UserMoviesAdapter extends RecyclerView.Adapter<UserMoviesAdapter.UserMoviesViewHolder>{
    private final List<Movies> movies;

    public UserMoviesAdapter(List<Movies> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public UserMoviesAdapter.UserMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_admin, parent, false);
        return new UserMoviesAdapter.UserMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserMoviesAdapter.UserMoviesViewHolder holder, int position) {
        Movies movie = movies.get(position);
        Glide.with(holder.itemView.getContext())
                .load(movie.getMovieImageUrl())
                .placeholder(R.drawable.camera)
                .error(R.drawable.camera)
                .into(holder.MovieImage);
        holder.theatreName.setText(movie.getTheatreName());
        holder.movieName.setText(movie.getMovieName());
        holder.movieDescription.setText(movie.getMovieDescription());
        String actors = "Main Casts: " + movie.getMovieActors();
        holder.movieActors.setText(actors);
        holder.movieTicketPrice.setText("Ticket Price: " + movie.getTicketPrice() + " Rs" +"");
        holder.deleteMovie.setVisibility(View.INVISIBLE);
        holder.deleteMovie.setEnabled(false);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), BookTicketActivity.class);
                intent.putExtra("MovieImageUrl", movie.getMovieImageUrl());
                intent.putExtra("TheatreName", movie.getTheatreName());
                intent.putExtra("MovieName", movie.getMovieName());
                intent.putExtra("MovieDescription", movie.getMovieDescription());
                intent.putExtra("MovieActors", actors);
                intent.putExtra("MovieTicketPrice", "Ticket Price: " + movie.getTicketPrice() + " Rs" +"");
                intent.putExtra("ticketPrice", movie.getTicketPrice());
                v.getContext().startActivity(intent);
            }

        });

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class UserMoviesViewHolder extends RecyclerView.ViewHolder{
        ImageView MovieImage, deleteMovie;
        TextView movieName;
        TextView movieDescription;
        TextView movieActors;
        TextView theatreName;
        TextView movieTicketPrice;
        public UserMoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            MovieImage = itemView.findViewById(R.id.MovieImage);
            deleteMovie = itemView.findViewById(R.id.deleteMovie);
            movieName = itemView.findViewById(R.id.movieName);
            movieDescription = itemView.findViewById(R.id.movieDescription);
            movieTicketPrice = itemView.findViewById(R.id.movieTicketPrice);
            theatreName = itemView.findViewById(R.id.theatreName);
            movieActors = itemView.findViewById(R.id.movieActors);

        }
    }
}
