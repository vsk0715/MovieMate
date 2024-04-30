package com.project.moviemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainUi extends AppCompatActivity {
    ImageView logout, search, account;
    RecyclerView homePageMovieList;
    UserMoviesAdapter userMoviesAdapter;
    List<Movies> movies;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ui);
        search = findViewById(R.id.search);
        account = findViewById(R.id.account);
        db = FirebaseFirestore.getInstance();
        homePageMovieList = findViewById(R.id.homePageMovieList);
        homePageMovieList.setHasFixedSize(true);
        homePageMovieList.setLayoutManager(new LinearLayoutManager(MainUi.this));
        movies = new ArrayList<>();
        movies.clear();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainUi.this, SearchActivity.class));
            }
        });
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainUi.this, UserAccount.class));
            }
        });
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
                                                String movieID = movieSnapshot.getId();
                                                String movieDescription = movieSnapshot.getString("description");
                                                String movieImageUrl = movieSnapshot.getString("imageUrl");
                                                String movieActors = movieSnapshot.getString("actors");
                                                String ticketPrice = movieSnapshot.getString("price");

                                                Movies movie = new Movies(movieID, movieName, movieDescription, movieImageUrl, movieActors, ticketPrice, theatreName);
                                                movies.add(movie);
                                            }

                                            // Update UI with search results only after all movies are processed
                                            if (!movies.isEmpty()) {
                                                userMoviesAdapter = new UserMoviesAdapter(movies);
                                                homePageMovieList.setAdapter(userMoviesAdapter);
                                                userMoviesAdapter.notifyDataSetChanged();
                                            }
                                        }
                                    });
                        }
                    }
                });
        /*
        logout = findViewById(R.id.logOutM);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                finish();
            }
        });
        */
    }
}