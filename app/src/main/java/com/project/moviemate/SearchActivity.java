package com.project.moviemate;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ImageView search_movie;
    EditText etSearchMovie;
    RecyclerView searchMovies;
    UserMoviesAdapter userMoviesAdapter;
    List<Movies> movies;
    FirebaseFirestore db;
    String theatreName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        db = FirebaseFirestore.getInstance();
        searchMovies = findViewById(R.id.searchMovies);
        searchMovies.setHasFixedSize(true);
        searchMovies.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        movies = new ArrayList<>();
        movies.clear();
        etSearchMovie = findViewById(R.id.etSearchMovie);
        search_movie = findViewById(R.id.search_movie);
        search_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(etSearchMovie.getText().toString())) {
                    String searchQuery = etSearchMovie.getText().toString().toLowerCase();

                    db.collection("users")
                            .document("wzxOzD2mXbdMwyX6jRW0xzMFyyI2")
                            .collection("Theatre")
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    List<Movies> movies = new ArrayList<>();

                                    for (QueryDocumentSnapshot theatreSnapshot : task.getResult()) {
                                        String theatreName = theatreSnapshot.getString("name");

                                        db.collection("users")
                                                .document("wzxOzD2mXbdMwyX6jRW0xzMFyyI2")
                                                .collection("Theatre")
                                                .document(theatreSnapshot.getId())
                                                .collection("movie")
                                                .get()
                                                .addOnCompleteListener(task1 -> {
                                                    if (task1.isSuccessful()) {
                                                        for (QueryDocumentSnapshot movieSnapshot : task1.getResult()) {
                                                            String movieName = movieSnapshot.getString("name");

                                                            if (movieName != null && movieName.toLowerCase().contains(searchQuery)) {
                                                                // If the movie name contains the search query, add it to the search results
                                                                String movieID = movieSnapshot.getId();
                                                                String movieDescription = movieSnapshot.getString("description");
                                                                String movieImageUrl = movieSnapshot.getString("imageUrl");
                                                                String movieActors = movieSnapshot.getString("actors");
                                                                String ticketPrice = movieSnapshot.getString("price");

                                                                Movies movie = new Movies(movieID, movieName, movieDescription, movieImageUrl, movieActors, ticketPrice, theatreName);
                                                                movies.add(movie);
                                                            }
                                                        }

                                                        // Update UI with search results only after all movies are processed
                                                        if (!movies.isEmpty()) {
                                                            userMoviesAdapter = new UserMoviesAdapter(movies);
                                                            searchMovies.setAdapter(userMoviesAdapter);
                                                            userMoviesAdapter.notifyDataSetChanged();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });
                }

            }
        });
    }
}