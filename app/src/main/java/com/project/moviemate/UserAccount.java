package com.project.moviemate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class UserAccount extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    RecyclerView recyclerViewMovieTickets;
    TextView userName,emailID, phoneNumber;
    String userID;
    TicketAdapter setAdapter;
    List<Ticket> tickets;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        recyclerViewMovieTickets = findViewById(R.id.recyclerViewMovieTickets);
        recyclerViewMovieTickets.setHasFixedSize(true);
        recyclerViewMovieTickets.setLayoutManager(new LinearLayoutManager(UserAccount.this));
        tickets = new ArrayList<>();
        tickets.clear();
        userName = findViewById(R.id.userName);
        emailID = findViewById(R.id.emailID);
        phoneNumber = findViewById(R.id.phoneNumber);
        userID = mAuth.getCurrentUser().getUid();
        fab = findViewById(R.id.logout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), LogInActivity.class));
                finish();
            }
        });

        db.collection("users")
                .document(userID)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            userName.setText(documentSnapshot.getString("name"));
                            emailID.setText(documentSnapshot.getString("email"));
                            phoneNumber.setText("+91"+documentSnapshot.getString("phone"));
                        } else {

                        }
                    }
                });

        db.collection("users")
                .document(userID)
                .collection("Tickets")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                            String theatreID = documentSnapshot.getId();
                            String TheatreName = documentSnapshot.getString("TheatreName");
                            String MovieName = documentSnapshot.getString("MovieName");
                            Long noOfTickets = documentSnapshot.getLong("NoOfTickets");
                            String numTickets = "" + noOfTickets;
                            Long Payment = documentSnapshot.getLong("PaymentMade");
                            String PaymentMade = "" + Payment;
                            String MovieDate = documentSnapshot.getString("MovieDate");
                            Ticket ticket = new Ticket(theatreID, TheatreName, MovieName, numTickets, PaymentMade, MovieDate);
                            tickets.add(ticket);

                        }
                        setAdapter = new TicketAdapter(tickets);
                        recyclerViewMovieTickets.setAdapter(setAdapter);
                        setAdapter.notifyDataSetChanged();
                    }
                });

    }
}