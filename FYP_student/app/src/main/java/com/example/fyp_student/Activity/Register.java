package com.example.fyp_student.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp_student.Class.Users;
import com.example.fyp_student.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {

    EditText mName, mEmail, mPassword, mPassword2;
    Button mRegister_bt;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    String userid;
    TextView nav_to_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = findViewById(R.id.reg_name);
        mEmail = findViewById(R.id.reg_email);
        mPassword = findViewById(R.id.reg_password);
        mPassword2 = findViewById(R.id.reg_password2);

        mRegister_bt = findViewById(R.id.register_button);
        nav_to_login = findViewById(R.id.nav_to_login);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.reg_progressbar);


        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }


        mRegister_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                final String name = mName.getText().toString().trim();
                try {
                    progressBar.setVisibility(View.VISIBLE);

                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                                userid = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("Users").document(userid);
                                Users newreg = new Users(userid, name);
                                documentReference.set(newreg);
                                startActivity(new Intent(getApplicationContext(), Login.class));

                            } else {
                                Toast.makeText(Register.this, "Error !" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(Register.this, "Please Enter Your Account", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
        });
        nav_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

    }
}