package com.project.moviemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class BookTicketActivity extends AppCompatActivity {
    TextView MovieTitle, MovieDescription, MovieActors, TheatreName, MovieTicketPrice;
    ImageView MovieImage;
    Button btnBookTicket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ticket);
        MovieTitle = findViewById(R.id.MovieTitle);
        MovieTicketPrice = findViewById(R.id.MovieTicketPrice);
        btnBookTicket = findViewById(R.id.btnBookTicket);
        MovieDescription = findViewById(R.id.MovieDescription);
        TheatreName = findViewById(R.id.TheatreName);
        MovieActors = findViewById(R.id.MovieActors);
        MovieImage = findViewById(R.id.MovieImage);
        Intent intent = getIntent();
        MovieTitle.setText(intent.getStringExtra("MovieName"));
        MovieDescription.setText(intent.getStringExtra("MovieDescription"));
        MovieActors.setText(intent.getStringExtra("MovieActors"));
        TheatreName.setText(intent.getStringExtra("TheatreName"));
        MovieTicketPrice.setText(intent.getStringExtra("MovieTicketPrice"));

        Glide.with(this)
                .load(intent.getStringExtra("MovieImageUrl"))
                .placeholder(R.drawable.camera)
                .error(R.drawable.camera)
                .into(MovieImage);
        btnBookTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(BookTicketActivity.this, MakePayment.class);
                intent1.putExtra("MovieImageUrl", intent.getStringExtra("MovieImageUrl"));
                intent1.putExtra("MovieTicketPrice", intent.getStringExtra("ticketPrice"));
                intent1.putExtra("theatreName", intent.getStringExtra("TheatreName"));
                intent1.putExtra("MovieName", intent.getStringExtra("MovieName"));
                startActivity(intent1);
            }
        });
    }
}