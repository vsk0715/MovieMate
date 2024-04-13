package com.project.moviemate;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class AddTheatre extends AppCompatActivity {
    ImageView imageViewTheatrePic;
    Button btnAddTheatre;
    EditText editTextTheatreName, editTextLocation;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_theatre);
        imageViewTheatrePic = findViewById(R.id.imageViewTheatrePic);
        btnAddTheatre = findViewById(R.id.btnAddTheatre);
        editTextTheatreName = findViewById(R.id.editTextTheatreName);
        editTextLocation = findViewById(R.id.editTextLocation);
        storageReference = FirebaseStorage.getInstance().getReference();

        btnAddTheatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextTheatreName.getText().toString().trim();
                String location = editTextLocation.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(location)) {
                    Toast.makeText(AddTheatre.this, "Enter details", Toast.LENGTH_LONG).show();
                } else {
                    ImagePicker.with(AddTheatre.this)
                            .crop(16f, 9f)
                            .compress(1024)
                            .maxResultSize(1080, 1080)
                            .start(10);
                }


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK && data != null ) {
            Uri uri = data.getData();
            uploadImageToFirebase(uri);
            imageViewTheatrePic.setImageURI(uri);
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri != null) {
            // Get the current user ID
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

            // Define the path in Firebase Storage where the image will be stored
            StorageReference TheatrePictureFolder = storageReference.child("TheatrePictureFolder").child(userId);
            StorageReference TheatreImageRef = TheatrePictureFolder.child("Theatre_" + System.currentTimeMillis() + ".jpg");

            // Upload the image to Firebase Storage
            TheatreImageRef.putFile(imageUri)
                    .addOnSuccessListener(taskSnapshot -> {
                        TheatreImageRef.getDownloadUrl().addOnSuccessListener(downloadUri ->{
                            String downloadUrl = downloadUri.toString();
                            String Location = editTextLocation.getText().toString().trim();
                            String Name = editTextTheatreName.getText().toString().trim();
                            Map<String, Object> postData = new HashMap<>();
                            postData.put("imageUrl", downloadUrl);
                            postData.put("location",Location);
                            postData.put("name", Name);
                            FirebaseFirestore.getInstance().collection("users")
                                    .document(userId)
                                    .collection("Theatre")
                                    .document()
                                    .set(postData)
                                    .addOnSuccessListener(a -> {
                                        Toast.makeText(AddTheatre.this, "Theatre Added", Toast.LENGTH_LONG).show();
                                    })
                                    .addOnFailureListener(b -> {
                                        Toast.makeText(AddTheatre.this, "Try Again", Toast.LENGTH_LONG).show();
                                    })
                                    .addOnCompleteListener(c -> {
                                        startActivity(new Intent(AddTheatre.this, AddEntities.class));
                                        finish();
                                    });
                        });
                    });
        }
    }
}