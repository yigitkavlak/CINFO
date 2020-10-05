package com.yigitkavlak.cinfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;

public class FuelTab2Fragment extends Fragment {


    ArrayList<String> userFuelTypeFromDB;
    ArrayList<String> userFuelCompanyNameFromDB;
    ArrayList<String> userFuelPriceFromDB;
    ArrayList<String> userGasolineDistanceFromDB;
    ArrayList<Date> userFuelDateFromDB;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    FuelRecyclerAdapter fuelRecyclerAdapter;
    private RecyclerView recyclerView;
    String currentUserUid;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_fuel_tab2_fragment, container, false);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userFuelCompanyNameFromDB = new ArrayList<>();
        userFuelPriceFromDB = new ArrayList<>();
        userFuelTypeFromDB = new ArrayList<>();
        userGasolineDistanceFromDB = new ArrayList<>();
        userFuelDateFromDB = new ArrayList<>();


        getDataFromFirestore();


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        fuelRecyclerAdapter = new FuelRecyclerAdapter(userFuelTypeFromDB, userFuelCompanyNameFromDB, userFuelPriceFromDB, userGasolineDistanceFromDB, userFuelDateFromDB);
        recyclerView.setAdapter(fuelRecyclerAdapter);

        return view;
    }


    public void getDataFromFirestore(){
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserUid = currentUser.getUid();

        CollectionReference collectionReference = firebaseFirestore.collection("fuelData " + currentUserUid);


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

                        Map<String,Object> fuelData = snapshot.getData();

                        String fuelType = (String) fuelData.get("fuelType");
                        String gasolineCompany = (String) fuelData.get("gasolineCompany");
                        String fuelPrice = (String) fuelData.get("fuelPrice");
                        String gasolineDistance = (String) fuelData.get("gasolineDistance");
                        Date date = (Date) fuelData.get("date") ;


                       userFuelTypeFromDB.add(fuelType);
                       userFuelCompanyNameFromDB.add(gasolineCompany);
                       userFuelPriceFromDB.add(fuelPrice);
                       userGasolineDistanceFromDB.add(gasolineDistance);
                       userFuelDateFromDB.add(date);




                      fuelRecyclerAdapter.notifyDataSetChanged();


                    }
                }
            }
        });
    }
}

