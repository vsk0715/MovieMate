package com.project.moviemate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class AddMovie extends AppCompatActivity {
    EditText editTextMovieName, editTextMovieDescription, editTextMovieActors, editTextTicketPrice;
    ImageView imageViewMoviePic;
    Button btnAddMovie;
    StorageReference storageReference;
    String theatreID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        editTextMovieName = findViewById(R.id.editTextMovieName);
        editTextMovieDescription = findViewById(R.id.editTextMovieDescription);
        editTextMovieActors = findViewById(R.id.editTextMovieActors);
        imageViewMoviePic = findViewById(R.id.imageViewMoviePic);
        editTextTicketPrice = findViewById(R.id.editTextTicketPrice);
        btnAddMovie = findViewById(R.id.btnAddMovie);
        storageReference = FirebaseStorage.getInstance().getReference();
        Intent intent = getIntent();
        theatreID = intent.getStringExtra("TheatreID");


        btnAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextMovieName.getText().toString().trim();
                String description = editTextMovieDescription.getText().toString().trim();
                String actors = editTextMovieActors.getText().toString().trim();
                String price = editTextTicketPrice.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description) || TextUtils.isEmpty(actors) || TextUtils.isEmpty(price))  {
                    Toast.makeText(AddMovie.this, "Enter details", Toast.LENGTH_LONG).show();
                } else {
                    ImagePicker.with(AddMovie.this)
                            .crop(16f, 9f)
                            .compress(1024)
                            .maxResultSize(1080, 1080)
                            .start(20);
                }


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 20 && resultCode == RESULT_OK && data != null ) {
            Uri uri = data.getData();
            uploadImageToFirebase(uri);
            imageViewMoviePic.setImageURI(uri);
        }
    }
    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri != null) {
            // Get the current user ID
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Define the path in Firebase Storage where the image will be stored
            StorageReference moviePictureFolder = storageReference.child("MoviePictureFolder").child(userId);
            StorageReference movieImageRef = moviePictureFolder.child("movie_" + System.currentTimeMillis() + ".jpg");

            // Upload the image to Firebase Storage
            movieImageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        movieImageRef.getDownloadUrl().addOnSuccessListener(downloadUri ->{
                            String downloadUrl = downloadUri.toString();
                            String description = editTextMovieDescription.getText().toString().trim();
                            String Name = editTextMovieName.getText().toString().trim();
                            String actors = editTextMovieActors.getText().toString().trim();
                            String price = editTextTicketPrice.getText().toString().trim();
                            Map<String, Object> movieData = new HashMap<>();
                            movieData.put("imageUrl", downloadUrl);
                            movieData.put("description",description);
                            movieData.put("actors",actors);
                            movieData.put("name", Name);
                            movieData.put("price", price);
                            FirebaseFirestore.getInstance().collection("users")
                                    .document(userId)
                                    .collection("Theatre")
                                    .document(theatreID)
                                    .collection("movie")
                                    .document()
                                    .set(movieData)
                                    .addOnSuccessListener(a -> {
                                        Toast.makeText(AddMovie.this, "Movie Added", Toast.LENGTH_LONG).show();
                                        btnAddMovie.setEnabled(false);
                                    })
                                    .addOnFailureListener(b -> {
                                        Toast.makeText(AddMovie.this, "Try Again", Toast.LENGTH_LONG).show();
                                    });
                        });
                    });
        }
    }
}