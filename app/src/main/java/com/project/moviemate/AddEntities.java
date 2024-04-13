package com.project.moviemate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddEntities extends AppCompatActivity {
    FloatingActionButton addTheatre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entities);
        addTheatre = findViewById(R.id.addTheatre);
        addTheatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddEntities.this, AddTheatre.class));
            }
        });
    }
}