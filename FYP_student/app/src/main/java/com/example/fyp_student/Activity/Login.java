package com.example.fyp_student.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fyp_student.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import io.grpc.okhttp.internal.Util;

public class Login extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button loginbt;
    //    ProgressBar progressBar;
    LottieAnimationView progressBar;
    FirebaseAuth fAuth;
    TextView nav_to_reg;
    TextView dis1to10;
    TextInputLayout login_email;
    TextInputLayout login_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Dialog mDialog;
        mDialog = new Dialog(Login.this);
        mDialog.setContentView(R.layout.load_dialog);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mEmail = findViewById(R.id.login_email1);
        mPassword = findViewById(R.id.login_password1);
        progressBar = findViewById(R.id.login_progressbar);
        fAuth = FirebaseAuth.getInstance();
        loginbt = findViewById(R.id.login_button);
        nav_to_reg = findViewById(R.id.nav_to_register);
        login_email = findViewById(R.id.login_email);
        login_password = findViewById(R.id.login_password);



        int a = 1;
        String text = "Login";
        loginbt.setText(text);

        if (fAuth.getCurrentUser() != null) {

            startActivity(new Intent(getApplicationContext(), FingerPrint_Auth.class));

            finish();
        }
        loginbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if(email.isEmpty())
                {
                    login_email.setError("Please Enter Your Username");
                }
                if(password.isEmpty())
                {
                    login_password.setError("Please Enter Your Password");
                }

                mDialog.show();
//                progressBar.setVisibility(View.VISIBLE);
                try {


                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Log in Succesfull", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Home.class));


                            } else {
//                                progressBar.setVisibility(View.GONE);
                                mDialog.hide();
                                Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                } catch (Exception e) {
//                    progressBar.setVisibility(View.GONE);
                    mDialog.hide();
                    Toast.makeText(Login.this, "Please Enter Your Username and Password !", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


        nav_to_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_next = new Intent(getApplicationContext(), Register.class);
                overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave);
                startActivity(intent_next);
//                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }
}