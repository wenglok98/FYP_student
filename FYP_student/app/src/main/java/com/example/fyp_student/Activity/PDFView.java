package com.example.fyp_student.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fyp_student.Class.Enrollment;
import com.example.fyp_student.Fragment.Fragment_Home;
import com.example.fyp_student.Fragment.Fragment_ModifySubject;
import com.example.fyp_student.Fragment.Fragment_SubjectDetail;
import com.example.fyp_student.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class PDFView extends AppCompatActivity {
    String sub1, sub2, sub3, sub4, writein, previoustotal;
    Enrollment minsonly = new Enrollment();
    String asdf, wensong;
    View view;
    TextView stopwatch;
    StorageReference mStorageRef;
    com.github.barteksc.pdfviewer.PDFView book1;
    int startinghour, startingminutes, closinghour, closingminutes;
    ImageButton returnbt;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    int totalhour, totalmins, totaloverall, totalbeforesum, lasttotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view);

        final Bundle extras = getIntent().getExtras();
        String subjectCode = extras.getString("code");
        String nameWOextension = extras.getString("name");
        final String enrolluid = extras.getString("enrolluid");
//        returnbt = findViewById(R.id.return_btn);
//        stopwatch = findViewById(R.id.stt);
        mStorageRef = FirebaseStorage.getInstance().getReference().child("Material/" + subjectCode + "/" + nameWOextension + ".pdf");

        book1 = findViewById(R.id.pdfviewfull);

//Start of open pdf file from storage
        try {
            final File localFile = File.createTempFile(nameWOextension, "pdf");
            mStorageRef.getFile(localFile)
                    .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                            book1.fromFile(localFile).load(); // open and load the pdf
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
//End of open pdf file from firebase storage


        Date currentdate = new Date();
        SimpleDateFormat hour = new SimpleDateFormat("hh");
        SimpleDateFormat min = new SimpleDateFormat("mm");

        String strthour = hour.format(currentdate);
        startinghour = Integer.parseInt(strthour);
        String strtmins = min.format(currentdate);
        startingminutes = Integer.parseInt(strtmins);
        DocumentReference documentReference = fstore.collection("Enrollment").document(enrolluid);
        documentReference.addSnapshotListener(PDFView.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                previoustotal = documentSnapshot.getString("visitedminutes");
                asdf = documentSnapshot.getString("visitedminutes");
            }
        });

        wensong = enrolluid;
//        returnbt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Date exittime = new Date();
//                SimpleDateFormat exhour = new SimpleDateFormat("hh");
//                SimpleDateFormat exmin = new SimpleDateFormat("mm");
//
//                String exithour = exhour.format(exittime);
//                closinghour = Integer.parseInt(exithour);
//                String exitminutes = exmin.format(exittime);
//                closingminutes = Integer.parseInt(exitminutes);
//
//
//                totalhour = closinghour - startinghour;
//
//
//                totalmins = closingminutes - startingminutes;
//
//
//                totaloverall = (totalhour * 60) + totalmins;
//                String abc = enrolluid;
//
//
//
//
//                totalbeforesum = Integer.parseInt(previoustotal);
//                lasttotal = totalbeforesum + totaloverall;
//                writein = Integer.toString(lasttotal);
//                Map<String, Object> map = new HashMap<>();
//                map.put("visitedminutes", writein);
//
//                fstore.collection("Enrollment").document(enrolluid).set(map, SetOptions.merge());
//                Toast.makeText(PDFView.this, "The total is" + Integer.toString(totaloverall), Toast.LENGTH_SHORT).show();
//
//                Intent intent = new Intent(PDFView.this, Home.class);
////                intent.putExtra("returnfrompdf", true);
//
//                startActivity(intent);
////finish();
//
//            }
//
//        });

    }

    @Override
    public void onBackPressed() {


        Date exittime = new Date();
        SimpleDateFormat exhour = new SimpleDateFormat("hh");
        SimpleDateFormat exmin = new SimpleDateFormat("mm");

        String exithour = exhour.format(exittime);
        closinghour = Integer.parseInt(exithour);
        String exitminutes = exmin.format(exittime);
        closingminutes = Integer.parseInt(exitminutes);


        totalhour = closinghour - startinghour;


        totalmins = closingminutes - startingminutes;


        totaloverall = (totalhour * 60) + totalmins;
        String abc = wensong;


        totalbeforesum = Integer.parseInt(previoustotal);
        lasttotal = totalbeforesum + totaloverall;
        writein = Integer.toString(lasttotal);
        Map<String, Object> map = new HashMap<>();
        map.put("visitedminutes", writein);

        fstore.collection("Enrollment").document(wensong).set(map, SetOptions.merge());
        Toast.makeText(PDFView.this, "The total is" + Integer.toString(totaloverall), Toast.LENGTH_SHORT).show();
        overridePendingTransition(R.anim.animation_enter,R.anim.animation_leave);
        super.onBackPressed();
    }
}