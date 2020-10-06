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

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.annotation.Nullable;


public class PaymentTab2Fragment extends Fragment {

    ArrayList<String> userPaymentTypeDB;
    ArrayList<String> userPaymentPriceDB;
    ArrayList<Date> userPaymentDateDB;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    String currentUserUid;

    private PaymentRecyclerAdapter paymentRecyclerAdapter;
    private RecyclerView recyclerViewPayment;

    public PaymentTab2Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_tab2, container, false);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        userPaymentTypeDB = new ArrayList<>();
        userPaymentPriceDB = new ArrayList<>();
        userPaymentDateDB = new ArrayList<>();

        getDataFromFirestore();

        recyclerViewPayment = view.findViewById(R.id.recyclerViewPayment);
        recyclerViewPayment.setLayoutManager(new LinearLayoutManager(getContext()));
        paymentRecyclerAdapter = new PaymentRecyclerAdapter(userPaymentTypeDB, userPaymentPriceDB, userPaymentDateDB);
        recyclerViewPayment.setAdapter(paymentRecyclerAdapter);


        return view;
    }

    public void getDataFromFirestore() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserUid = currentUser.getUid();

        CollectionReference collectionReference = firebaseFirestore.collection("paymentData " + currentUserUid);


        collectionReference
                .orderBy("date", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (queryDocumentSnapshots != null) {

                            for (DocumentSnapshot snapshot : queryDocumentSnapshots.getDocuments()) {

                                Map<String, Object> paymentData = snapshot.getData();

                                String paymentType = (String) paymentData.get("paymentType");
                                String paymentPrice = (String) paymentData.get("paymentPrice");
                                Date date = (Date) paymentData.get("date");


                                userPaymentTypeDB.add(paymentType);
                                userPaymentPriceDB.add(paymentPrice);
                                userPaymentDateDB.add(date);


                                paymentRecyclerAdapter.notifyDataSetChanged();


                            }
                        }
                    }
                });
    }
}