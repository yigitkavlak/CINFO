package com.yigitkavlak.cinfo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;


public class TireTab2Fragment extends Fragment {


    ArrayList<String> userTireTypeFDB;
    ArrayList<String> userTirePriceFDB;
    ArrayList<String> userTireDistanceFDB;
    ArrayList<Date> userTireDateFDB;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    String currentUserUid;


    private TireRecyclerAdapter tireRecyclerAdapter;
    private RecyclerView recyclerViewTire;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tire_tab2, container, false);




        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userTireTypeFDB = new ArrayList<>();
        userTirePriceFDB = new ArrayList<>();
        userTireDistanceFDB = new ArrayList<>();
        userTireDateFDB = new ArrayList<>();


        getDataFromFirestore();

        recyclerViewTire = view.findViewById(R.id.recyclerViewTire);
        recyclerViewTire.setLayoutManager(new LinearLayoutManager(getContext()));
        tireRecyclerAdapter = new TireRecyclerAdapter(userTireTypeFDB, userTirePriceFDB, userTireDistanceFDB, userTireDateFDB);
        recyclerViewTire.setAdapter(tireRecyclerAdapter);





        return view;
    }

    public void getDataFromFirestore(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserUid = currentUser.getUid();

        CollectionReference collectionReference = firebaseFirestore.collection("tireData " + currentUserUid);


        collectionReference
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e!= null){
                            Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                        if(queryDocumentSnapshots != null){

                            for  (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()){

                                Map<String,Object> tireData = snapshot.getData();

                                String tireType = (String) tireData.get("tireType");
                                String tirePrice = (String) tireData.get("tirePrice");
                                String tireDistance = (String) tireData.get("tireDistance");
                                Date date = (Date) tireData.get("date") ;


                               userTireTypeFDB.add(tireType);
                               userTirePriceFDB.add(tirePrice);
                               userTireDistanceFDB.add(tireDistance);
                               userTireDateFDB.add(date);




                                tireRecyclerAdapter.notifyDataSetChanged();


                            }
                        }
                    }
                });
    }
}