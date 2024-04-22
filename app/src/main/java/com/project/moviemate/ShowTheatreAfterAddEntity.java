package com.project.moviemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class ShowTheatreAfterAddEntity extends AppCompatActivity {
    TextView tvTheatreName;
    MoviesAdapter moviesAdapter;
    List<Movies> movies;
    FirebaseFirestore db;
    ImageView theatreImage;
    RecyclerView moviesRecyclerView;
    FloatingActionButton addMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_theatre_after_add_entity);
        tvTheatreName = findViewById(R.id.tvTheatreName);
        theatreImage = findViewById(R.id.theatreImage);
        moviesRecyclerView = findViewById(R.id.moviesRecyclerView);
        moviesRecyclerView.setHasFixedSize(true);
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(ShowTheatreAfterAddEntity.this));
        movies = new ArrayList<>();
        movies.clear();
        addMovie = findViewById(R.id.addMovie);
        Intent intent = getIntent();
        tvTheatreName.setText(intent.getStringExtra("theatreName"));
        db = FirebaseFirestore.getInstance();
        Glide.with(this)
                .load(intent.getStringExtra("imageURL"))
                .placeholder(R.drawable.camera)
                .error(R.drawable.camera)
                .into(theatreImage);
        db.collection("users")
                        .document("wzxOzD2mXbdMwyX6jRW0xzMFyyI2")
                                .collection("Theatre")
                                        .document(Objects.requireNonNull(intent.getStringExtra("TheatreID")))
                                                .collection("movie")
                                                        .get()
                                                                .addOnCompleteListener(task -> {
                                                                    if (task.isSuccessful()) {
                                                                        AtomicInteger count = new AtomicInteger();
                                                                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                                            String movieID = documentSnapshot.getId();
                                                                            String movieName = documentSnapshot.getString("name");
                                                                            String movieDescription = documentSnapshot.getString("description");
                                                                            String movieImageUrl = documentSnapshot.getString("imageUrl");
                                                                            String movieActors = documentSnapshot.getString("actors");
                                                                            String ticketPrice = documentSnapshot.getString("price");
                                                                            String theatreName = intent.getStringExtra("theatreName");


                                                                            Movies movie = new Movies(movieID, movieName, movieDescription, movieImageUrl, movieActors, ticketPrice, theatreName);
                                                                            movies.add(movie);
                                                                            count.getAndIncrement();
                                                                        }
                                                                        moviesAdapter = new MoviesAdapter(movies);
                                                                        moviesRecyclerView.setAdapter(moviesAdapter);
                                                                        moviesAdapter.notifyDataSetChanged();
                                                                    }
                                                                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ShowTheatreAfterAddEntity.this, "Error loading Page",Toast.LENGTH_SHORT).show();
                    }
                });



        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowTheatreAfterAddEntity.this, AddMovie.class);
                i.putExtra("TheatreID", intent.getStringExtra("TheatreID"));
                startActivity(i);
            }
        });

    }
}