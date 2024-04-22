package com.project.moviemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class AddEntities extends AppCompatActivity {
    FloatingActionButton addTheatre;
    RecyclerView theatreRecyclerView;
    AddEntityTheatreAdapter addEntityTheatreAdapter;
    List<Theatre> theatres;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entities);
        addTheatre = findViewById(R.id.addTheatre);
        theatreRecyclerView = findViewById(R.id.theatreRecyclerView);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        theatreRecyclerView.setHasFixedSize(true);
        theatreRecyclerView.setLayoutManager(new LinearLayoutManager(AddEntities.this));
        theatres = new ArrayList<>();
        theatres.clear();
        db.collection("users")
                        .document("wzxOzD2mXbdMwyX6jRW0xzMFyyI2")
                                .collection("Theatre")
                                        .get()
                                                .addOnCompleteListener(task -> {
                                                    if (task.isSuccessful()) {
                                                        AtomicInteger count = new AtomicInteger();
                                                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                                            String theatreID = documentSnapshot.getId();
                                                            String theatreName = documentSnapshot.getString("name");
                                                            String theatreLocation = documentSnapshot.getString("location");
                                                            String theatreImageUrl = documentSnapshot.getString("imageUrl");
                                                            Theatre theatre = new Theatre(theatreName, theatreLocation, theatreImageUrl, theatreID);
                                                            theatres.add(theatre);
                                                            count.getAndIncrement();


                                                        }
                                                        addEntityTheatreAdapter = new AddEntityTheatreAdapter(theatres, AddEntities.this);
                                                        theatreRecyclerView.setAdapter(addEntityTheatreAdapter);
                                                        addEntityTheatreAdapter.notifyDataSetChanged();
                                                    }
                                                })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(AddEntities.this, "Error loading Theatre",Toast.LENGTH_SHORT).show();
                                                            }
                                                        });



        addTheatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddEntities.this, AddTheatre.class));
            }
        });
    }
}