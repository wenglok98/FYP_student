package com.example.fyp_student.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.fyp_student.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;



public class MainActivity extends AppCompatActivity {

 FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




//        Users currentus = new Users("testing","123");
//
//        db.collection("Users").document().set(currentus);
//String brgin = "TestingUSer";
//        db.collection("Users").document(brgin).get()
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if(documentSnapshot.exists())
//                        {
//
//
//                           Users cr = documentSnapshot.toObject(Users.class);
//                            String nname2 = cr.getName();
//                            String aage2 = cr.getAge();
//
//                            String nname = documentSnapshot.getString("name");
//                            String aage = documentSnapshot.getString("age");
//                            Users crt = new Users(documentSnapshot.getString("name"),documentSnapshot.getString("age"));
//                        }
//                        else
//                        {}
//                    }
//                });








    }
}