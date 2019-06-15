package com.dusenbery.firebase_authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class RegistrationActivity extends AppCompatActivity {

    private EditText etFirstName, etLastName, etEmail, etPassword;
    String firstName, lastName, email, password;

    private Button regBtn;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private DatabaseReference myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();

        myDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        initializeUI();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });
    }

    private void registerNewUser() {
        progressBar.setVisibility(View.VISIBLE);

        firstName = etFirstName.getText().toString();
        lastName = etLastName.getText().toString();

        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        if (TextUtils.isEmpty(firstName)) {
            Toast.makeText(getApplicationContext(), "Please enter first name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(lastName)) {
            Toast.makeText(getApplicationContext(), "Please enter last name...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email...", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                            OnAuth(task.getResult().getUser());
                            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    private void OnAuth(FirebaseUser user) {
        createAnewUser(user.getUid());
    }

    private void createAnewUser(String uid) {
        User user = BuildNewuser();
        myDatabase.child(uid).setValue(user);
    }

    private User BuildNewuser(){
        return new User(
                getFirstName(),
                getLastName(),
                getUserEmail(),
                new Date().getTime()
        );
    }


    private void initializeUI() {
        etFirstName = findViewById(R.id.firstName);
        etLastName = findViewById(R.id.lastName);
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressBar);
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return firstName;
    }
    public String getUserEmail() {
        return email;
    }


}