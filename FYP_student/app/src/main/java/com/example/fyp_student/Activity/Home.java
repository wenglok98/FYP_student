package com.example.fyp_student.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.fyp_student.Fragment.Fragment_Home;
import com.example.fyp_student.Fragment.Fragment_Message;
import com.example.fyp_student.Fragment.Fragment_ModifySubject;
import com.example.fyp_student.Fragment.Fragment_Profile;
import com.example.fyp_student.Fragment.Fragment_SubjectDetail;
import com.example.fyp_student.Fragment.Fragment_Task;
import com.example.fyp_student.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    Boolean returnfrompdf;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();


    private static final String TAG = "Home";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ((TextView) findViewById(R.id.apptitle)).setText("Home");


        //Testing
//returnfrompdf = false;
        fAuth = FirebaseAuth.getInstance();
//        String abc = fAuth.getCurrentUser().getUid();
//        DocumentReference documentReference = fStore.collection("Users").document(abc);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
////                usname.setText(documentSnapshot.getString("uname"));
//            }
//        });
//
//
//        //End of testing


        // Start of side bar nav
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        navigationView.bringToFront(); // light up effect
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //End of Side Bar Nav

        //Set default fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment_Home()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // End of setting default fragment


        //Start of return from pdf to subject detail fragment
//        Intent data = getIntent();

//        returnfrompdf = data.getBooleanExtra("returnfrompdf", false);
//
//
//        if (returnfrompdf == true) {
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment_SubjectDetail()).commit();
//        }
        // End of return from pdf

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment_Profile()).commit();
                break;

            case R.id.addSubject:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new Fragment_ModifySubject()).commit();
                break;

            case R.id.sign_out:

                fAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(getApplicationContext(), Login.class));
                break;
//                    Toast.makeText(this, "Sign Out Successful", Toast.LENGTH_SHORT).show();


//                break;
//
//                } catch (Exception e) {
//                    Log.v(TAG, "im failed");
//                    e.printStackTrace();
//                }

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

}