package com.example.fyp_student.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp_student.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;


public class Fragment_ViewMaterial extends Fragment {
    public Fragment_ViewMaterial() {

    }

    View view;
    TextView viewsubjectcode;
    StorageReference mStorageRef;
    PDFView book1;

    FirebaseStorage storage = FirebaseStorage.getInstance();

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_viewmaterial, container, false);
        viewsubjectcode = view.findViewById(R.id.viewsubname);

        Bundle bundle = this.getArguments();
        String nameWOextension = bundle.getString("name");
        String subjectCode = bundle.getString("code");
        viewsubjectcode.setText(nameWOextension);


        mStorageRef = FirebaseStorage.getInstance().getReference().child("Material/" + subjectCode + "/" + nameWOextension + ".pdf");

        book1 = (PDFView) view.findViewById(R.id.pdfviewer);

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


        return view;
    }
}
