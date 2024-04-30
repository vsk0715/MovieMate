package com.project.moviemate;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MakePayment extends AppCompatActivity {
    Spinner noOFTickets;
    TextView tvDate, totalMovieTicketPrice,MovieTicketPrice;
    ImageView MovieImage;
    Button makePayment;
    FirebaseFirestore db;
    int seats;
    String selectedDate;
    long total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        noOFTickets = findViewById(R.id.noOFTickets);
        db = FirebaseFirestore.getInstance();
        makePayment = findViewById(R.id.makePayment);
        totalMovieTicketPrice = findViewById(R.id.totalMovieTicketPrice);
        MovieTicketPrice = findViewById(R.id.MovieTicketPrice);
        MovieImage = findViewById(R.id.MovieImage);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.noOfTickets, android.R.layout.simple_spinner_item);
        Intent intent = getIntent();
        Glide.with(this)
                .load(intent.getStringExtra("MovieImageUrl"))
                .placeholder(R.drawable.camera)
                .error(R.drawable.camera)
                .into(MovieImage);
        MovieTicketPrice.setText(intent.getStringExtra("MovieTicketPrice") + " Rs");


        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        noOFTickets.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                seats = Integer.parseInt(parent.getItemAtPosition(position).toString());
                total = seats * Integer.parseInt(intent.getStringExtra("MovieTicketPrice"));
                totalMovieTicketPrice.setText(""+total+ " Rs");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                totalMovieTicketPrice.setText(""+intent.getStringExtra("MovieTicketPrice"));
            }
        });

        // Apply the adapter to the spinner
        noOFTickets.setAdapter(adapter);
        Button selectDateButton = findViewById(R.id.selectDateButton);
        tvDate = findViewById(R.id.tvDate);

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        makePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("TheatreName", intent.getStringExtra("theatreName"));
                data.put("MovieName", intent.getStringExtra("MovieName"));
                data.put("MovieDate", selectedDate);
                data.put("PaymentMade", total);
                data.put("TimeStamp", FieldValue.serverTimestamp());
                data.put("NoOfTickets", seats);
                db.collection("users")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("Tickets")
                        .document()
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(MakePayment.this, "Tickets Booked Successfully.", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(MakePayment.this, MainUi.class));
                                finish();
                            }
                        });


            }
        });

    }
    private void showDatePickerDialog() {
        // Get current date
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog and set current date as minimum selectable date
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDayOfMonth) {
                // Handle selected date
                selectedDate = selectedDayOfMonth + "/" + (selectedMonth + 1) + "/" + selectedYear;
                tvDate.setText(selectedDate);
            }
        }, year, month, dayOfMonth);

        // Set minimum selectable date to today
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
}