package com.ideasoft.firebasedemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText emailEt, passwordEt;
    private Button login;
    private TextView createAccount;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initial();

        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(view -> {
            auth.signInWithEmailAndPassword(emailEt.getText().toString(), passwordEt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                        Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });

        createAccount.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this,RegisterActivity.class));
        });
    }

    private void initial() {
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        login = findViewById(R.id.loginId);
        createAccount = findViewById(R.id.new_account);
    }
}