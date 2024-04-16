package com.project.moviemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowTheatreAfterAddEntity extends AppCompatActivity {
    TextView tvTheatreName;
    ImageView theatreImage;
    RecyclerView movieTheatreAddEntity;
    FloatingActionButton addMovie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_theatre_after_add_entity);
        tvTheatreName = findViewById(R.id.tvTheatreName);
        theatreImage = findViewById(R.id.theatreImage);
        movieTheatreAddEntity = findViewById(R.id.movieTheatreAddEntity);
        addMovie = findViewById(R.id.addMovie);
        Intent intent = getIntent();
        tvTheatreName.setText(intent.getStringExtra("theatreName"));
        Glide.with(this)
                .load(intent.getStringExtra("imageURL"))
                .placeholder(R.drawable.camera)
                .error(R.drawable.camera)
                .into(theatreImage);
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