package com.yigitkavlak.cinfo;

import android.graphics.drawable.shapes.OvalShape;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Nullable;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    ArrayList<String> userNameFromDB;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    String userID;
    TextView textView;
    String userName;
    TextView userCarModel;
    TextView lastFuelData;
    TextView dateText;
    ImageView imageView;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);

        // userNameFromDB = new ArrayList<>();
        //init firebase
        firebaseAuth = FirebaseAuth.getInstance(); //Initiliaze
        firebaseFirestore = FirebaseFirestore.getInstance(); //Initiliaze
        firebaseUser = firebaseAuth.getCurrentUser();

        userID = firebaseAuth.getCurrentUser().getUid();


        userCarModel = viewGroup.findViewById(R.id.carModelText);
        textView = viewGroup.findViewById(R.id.userNameText);
        lastFuelData = viewGroup.findViewById(R.id.lastFuelData);
        dateText = viewGroup.findViewById(R.id.dateText);
        imageView = viewGroup.findViewById(R.id.imageView);

        imageView.setImageResource(R.drawable.ic_cinfo);


        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                textView.setText(documentSnapshot.getString("userName") + " !");
                userCarModel.setText(documentSnapshot.getString("carModel"));
                lastFuelData.setText(documentSnapshot.getString("eMail"));
                dateText.setText(documentSnapshot.getDate("date").toString());


            }
        });


        // getDataFromFireStore();
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile, container, false);
        return viewGroup;

    }

   /* public void getDataFromFireStore(){

        CollectionReference collectionReference =firebaseFirestore.collection("Data");
        collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if(e != null){
                        Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
                if (queryDocumentSnapshots != null){

                    for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){


                        Map<String,Object> data = snapshot.getData();



                    }

                }

            }
        });




    }*/


}