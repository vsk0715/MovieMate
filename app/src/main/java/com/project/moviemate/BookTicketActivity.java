package com.project.moviemate;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class BookTicketActivity extends AppCompatActivity {
    TextView MovieTitle, MovieDescription, MovieActors, TheatreName, MovieTicketPrice;
    ImageView MovieImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        MovieTitle = findViewById(R.id.MovieTitle);
        MovieTicketPrice = findViewById(R.id.MovieTicketPrice);
        MovieDescription = findViewById(R.id.MovieDescription);
        TheatreName = findViewById(R.id.TheatreName);
        MovieActors = findViewById(R.id.MovieActors);
        MovieImage = findViewById(R.id.MovieImage);
        Intent intent = getIntent();
        MovieTitle.setText(intent.getStringExtra("MovieName"));
        MovieDescription.setText(intent.getStringExtra("MovieDescription"));
        MovieActors.setText(intent.getStringExtra("MovieActors"));
        TheatreName.setText(intent.getStringExtra("TheatreName"));
        MovieTicketPrice.setText("Ticket Price: " + intent.getStringExtra("MovieTicketPrice") + " Rs" +"");

        Glide.with(this)
                .load(intent.getStringExtra("MovieImageUrl"))
                .placeholder(R.drawable.camera)
                .error(R.drawable.camera)
                .into(MovieImage);
    }
}