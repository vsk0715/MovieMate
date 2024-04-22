package com.project.moviemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Admin extends AppCompatActivity {
    ImageView addEntities, viewDetails, logout;
    RecyclerView theatreRecyclerViewAdmin;
    AddEntityTheatreAdapter addEntityTheatreAdapter;
    List<Theatre> theatres;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        addEntities = findViewById(R.id.addEntities);
        viewDetails = findViewById(R.id.viewDetails);
        logout = findViewById(R.id.logOut);
        addEntities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this, AddEntities.class));
            }
        });
        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this, ViewDetails.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                finish();
            }
        });
        theatreRecyclerViewAdmin = findViewById(R.id.theatreRecyclerViewAdmin);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        String userID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        theatreRecyclerViewAdmin.setHasFixedSize(true);
        theatreRecyclerViewAdmin.setLayoutManager(new LinearLayoutManager(Admin.this));
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
                        addEntityTheatreAdapter = new AddEntityTheatreAdapter(theatres, Admin.this);
                        theatreRecyclerViewAdmin.setAdapter(addEntityTheatreAdapter);
                        addEntityTheatreAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Admin.this, "Error loading Theatre",Toast.LENGTH_SHORT).show();
                    }
                });

    }
}