package com.example.fyp_student.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fyp_student.Class.Users;
import com.example.fyp_student.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Fragment_Profile extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    EditText edName, edType, edStuid, edGender;
    TextInputLayout l1;
    Button editBT, updateBT, cancelBT;
    View view;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile,container,false);
        fAuth = FirebaseAuth.getInstance();
        edName = view.findViewById(R.id.editName);
        edName.setEnabled(false);
        edType = view.findViewById(R.id.editUserType);
        edType.setEnabled(false);
        edStuid = view.findViewById(R.id.editStudentID);
        edStuid.setEnabled(false);
        edGender = view.findViewById(R.id.editUserGender);
        edGender.setEnabled(false);
        l1 = view.findViewById(R.id.l1);
        updateBT = view.findViewById(R.id.updateBt);
        updateBT.setVisibility(View.GONE);
        cancelBT = view.findViewById(R.id.cancelBT);
        cancelBT.setVisibility(View.GONE);
        editBT = view.findViewById(R.id.editBtn);
        String UID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users").document(UID);
        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null) {
                    edName.setText(documentSnapshot.getString("uname"));
                    edGender.setText(documentSnapshot.getString("ugender"));
                    edType.setText(documentSnapshot.getString("utype"));
                    edStuid.setText(documentSnapshot.getString("ustudentid"));
                }
            }
        });
        editBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edName.setEnabled(true);
                edGender.setEnabled(true);
                edType.setEnabled(true);
                edStuid.setEnabled(true);
                editBT.setVisibility(View.GONE);
                updateBT.setVisibility(View.VISIBLE);
                cancelBT.setVisibility(View.VISIBLE);

            }
        });


        updateBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String CurUser = fAuth.getCurrentUser().getUid();
                DocumentReference documentReference = fStore.collection("Users").document(CurUser);
                String upName = edName.getText().toString();
                String upGender = edGender.getText().toString();
                String upType = edType.getText().toString();
                String upID = edStuid.getText().toString();
                Users upUser = new Users(CurUser,upID,upName,upType,upGender);
                documentReference.set(upUser);
                getActivity().getSupportFragmentManager().beginTransaction().replace(Fragment_Profile.this.getId(), new Fragment_Profile()).commit();

            }
        });

        cancelBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edName.setEnabled(false);
                edType.setEnabled(false);
                edStuid.setEnabled(false);
                edGender.setEnabled(false);
                editBT.setVisibility(View.VISIBLE);
                updateBT.setVisibility(View.GONE);
                cancelBT.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().beginTransaction().replace(Fragment_Profile.this.getId(), new Fragment_Profile()).commit();

            }
        });



        return view;
    }
}
