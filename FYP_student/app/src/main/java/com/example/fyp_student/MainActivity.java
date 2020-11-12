package com.example.fyp_student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.fyp_student.Activity.Login;
import com.example.fyp_student.Fragment.Fragment_Home;
import com.example.fyp_student.Fragment.Fragment_Message;
import com.example.fyp_student.Fragment.Fragment_Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Start of Bottom Navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.btm_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment_Home()).commit();

                        return true;
                    case R.id.nav_task:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment_Task()).commit();

                        return true;

                    case R.id.nav_message:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment_Message()).commit();

                        return true;
                }

                return false;
            }
        });

        // End of Bottom Navigation
    }

    public void showloadinglogin(){
        final Dialog mDialog;
        mDialog = new Dialog(this);
        mDialog.setContentView(R.layout.load_dialog);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }
}