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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignIn extends AppCompatActivity {

    EditText signInEmail, signInPassword;
    Button signIn, resetpassword;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        signInEmail = findViewById(R.id.signInEmail);
        signInPassword = findViewById(R.id.signInPassword);
        resetpassword = findViewById(R.id.resetpassword);
        signIn = findViewById(R.id.signIn);
        mAuth = FirebaseAuth.getInstance();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signInEmail.getText().toString().trim();
                String password = signInPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(SignIn.this, "Enter Email", Toast.LENGTH_SHORT).show();
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(SignIn.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    //authenticate user
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                /*
                                     Sign in success, update UI with the signed-in user's information
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    updateUI(user);
                                    */
                                if (email.equals("Admin@gmail.com")) {
                                    Toast.makeText(SignIn.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(SignIn.this, Admin.class));
                                    finish();
                                } else {
                                    Toast.makeText(SignIn.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();

                                    startActivity(new Intent(SignIn.this, MainUi.class));
                                    finish();
                                }

                            } else {
                                Toast.makeText(SignIn.this, "Email or Username is Incorrect", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = signInEmail.getText().toString().trim();

                if (!TextUtils.isEmpty(email)) {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Password reset email sent successfully
                                        Toast.makeText(getApplicationContext(), "Password reset email sent", Toast.LENGTH_SHORT).show();
                                    } else {
                                        // Failed to send password reset email
                                        Toast.makeText(getApplicationContext(), "Failed to send password reset email", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    // Empty email field
                    Toast.makeText(getApplicationContext(), "Please enter your email address", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}