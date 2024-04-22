package com.project.moviemate;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    EditText signUpUserName, signUpEmail, signUpPhoneNumber, signUpPassword;
    Button signUpCreateAccount;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        signUpUserName = findViewById(R.id.signUpUserName);
        signUpEmail = findViewById(R.id.signUpEmail);
        signUpPhoneNumber = findViewById(R.id.signUpPhoneNumber);
        signUpPassword = findViewById(R.id.signUpPassword);
        signUpCreateAccount = findViewById(R.id.signUpCreateAccount);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        signUpCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signUpEmail.getText().toString().trim();
                String password = signUpPassword.getText().toString().trim();
                String phone = signUpPhoneNumber.getText().toString().trim();
                String name = signUpUserName.getText().toString().trim();
                if (email.equals("Admin@gmail.com")) {
                    Toast.makeText(SignUp.this, "Choose Different Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(name)) {
                    Toast.makeText(SignUp.this, "Enter Name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignUp.this, "Enter Correct Email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(SignUp.this, "Enter correct phone number", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password) || password.length()<8) {
                    Toast.makeText(SignUp.this, "Password must be 8 characters long", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(SignUp.this, "Creating User", Toast.LENGTH_SHORT).show();
                                    signUpCreateAccount.setEnabled(false);
                                    if (task.isSuccessful()) {
                                        userID = mAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = db.collection("users").document(userID);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("email", email);
                                        user.put("phone", phone);
                                        user.put("name", name);
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Toast.makeText(SignUp.this, "User Profile Is Created for:"+name, Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        startActivity(new Intent(SignUp.this, MainUi.class));
                                        finish();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(SignUp.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                                        signUpCreateAccount.setEnabled(true);
                                    }
                                }
                            });
                }
            }
        });
    }
}