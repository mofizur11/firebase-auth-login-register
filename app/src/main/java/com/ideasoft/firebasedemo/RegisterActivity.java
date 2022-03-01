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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {


    private EditText nameEt, phoneEt, emailEt, passwordEt, genderEt;
    private Button register;
    private TextView haveAccount;
    private FirebaseAuth auth;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initial();

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference();

        register.setOnClickListener(view -> {
            auth.createUserWithEmailAndPassword(emailEt.getText().toString(), passwordEt.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {


                        String name = nameEt.getText().toString();
                        String phone = phoneEt.getText().toString();
                        String email = emailEt.getText().toString();
                        String gender = genderEt.getText().toString();

                        String uId = auth.getCurrentUser().getUid();

                        DatabaseReference userInformation = reference.child("UserInformation").child(uId);

                        HashMap<String, Object> allUserInfo = new HashMap<>();
                        allUserInfo.put("Name", name);
                        allUserInfo.put("Phone", phone);
                        allUserInfo.put("Email", email);
                        allUserInfo.put("Gender", gender);

                        userInformation.setValue(allUserInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(RegisterActivity.this, "successfully register", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            }
                        });


                    } else {
                        Toast.makeText(RegisterActivity.this, "register failed", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        });


        haveAccount.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        });


    }

    private void initial() {
        nameEt = findViewById(R.id.nameEt);
        phoneEt = findViewById(R.id.phoneEt);
        emailEt = findViewById(R.id.emailEt);
        passwordEt = findViewById(R.id.passwordEt);
        genderEt = findViewById(R.id.genderEt);
        register = findViewById(R.id.registerId);
        haveAccount = findViewById(R.id.have_account);
    }
}