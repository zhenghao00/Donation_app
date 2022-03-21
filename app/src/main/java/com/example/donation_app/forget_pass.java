package com.example.donation_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forget_pass extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordBut;
    private ProgressBar progressBar;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        emailEditText = (EditText) findViewById(R.id.gmail);
        resetPasswordBut = (Button) findViewById(R.id.restPassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        auth = FirebaseAuth.getInstance();


        resetPasswordBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restPassword();
                Intent i = new Intent(forget_pass.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void restPassword(){
        String email = emailEditText.getText().toString().trim();

        if(email.isEmpty()){
            emailEditText.setError("Email Is Required! ");
            emailEditText.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailEditText.setError("Please Provide a Valid Email! ");
            emailEditText.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(forget_pass.this, "Check Your Email To Reset your Password! ", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);


                }else{
                    Toast.makeText(forget_pass.this, "Please Try Again ", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}