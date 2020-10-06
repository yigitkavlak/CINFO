package com.yigitkavlak.cinfo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.yigitkavlak.cinfo.R.drawable.ic_cinfo;


public class PaymentTab1Fragment extends Fragment {


    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    ImageView cinfoHomeImage;
    EditText paymentPrice;
    RadioGroup paymentSelector;
    RadioButton radioMTV;
    RadioButton radioSigorta;
    RadioButton radioKasko;
    Button addPaymentButton;
    String currentUserUid;
    int paymentType;
    String paymentTypeName;
    String paymentPriceDatabase;
    String paymentTypeDatabase;


    public PaymentTab1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment_tab1, container, false);

        getUserID();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        paymentSelector = view.findViewById(R.id.radioPaymentSelector);
        cinfoHomeImage = view.findViewById(R.id.cinfoHomeImage);
        cinfoHomeImage.setImageResource(ic_cinfo);
        paymentPrice = view.findViewById(R.id.paymentPriceText);
        radioMTV = view.findViewById(R.id.radioMTV);
        radioKasko = view.findViewById(R.id.radioKasko);
        radioSigorta = view.findViewById(R.id.radioSigorta);
        addPaymentButton = view.findViewById(R.id.paymentAddButton);


        /*paymentType = paymentSelector.getCheckedRadioButtonId();
        System.out.println(paymentType);
        switch (paymentType)                                                   //Kullanicinin Ödeme Tipi Seçimini belirlemek icin kullanılan switch case yapisi
        {
            case R.id.radioMTV: {
                paymentTypeName = "MTV";
                break;
            }
            case R.id.radioSigorta: {
                paymentTypeName = "Sigorta";
                break;
            }
            case R.id.radioKasko: {
                paymentTypeName = "Kasko";
                break;
            }
        } */




        /* paymentType = paymentSelector.getCheckedRadioButtonId();
        switch (paymentType)                                                   //Kullanicinin Ödeme Tipi Seçimini belirlemek icin kullanılan switch case yapisi
        {
            case R.id.radioMTV: {
                paymentTypeName = "MTV";
                break;
            }
            case R.id.radioSigorta: {
                paymentTypeName = "Sigorta";
                break;
            }
            case R.id.radioKasko: {
                paymentTypeName = "Kasko ";
                break;
            }

        } */

        addPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPaymentSelector();
                paymentPriceDatabase = paymentPrice.getText().toString();
                Map<String, Object> paymentData = new HashMap<>();

                paymentData.put("paymentType", paymentTypeName);
                paymentData.put("paymentPrice", paymentPriceDatabase);
                paymentData.put("date", FieldValue.serverTimestamp());

                System.out.println(paymentTypeName);
                firebaseFirestore.collection("paymentData " + currentUserUid)
                        .add(paymentData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                Toast.makeText(getContext(), "Kayıt Başarıyla Oluşturuldu", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


        return view;


    }

    public void getPaymentSelector() {

        paymentType = paymentSelector.getCheckedRadioButtonId();
        System.out.println(paymentType);
        switch (paymentType)                                                   //Kullanicinin Ödeme Tipi Seçimini belirlemek icin kullanılan switch case yapisi
        {
            case R.id.radioMTV: {
                paymentTypeName = "MTV";
                break;
            }
            case R.id.radioSigorta: {
                paymentTypeName = "Sigorta";
                break;
            }
            case R.id.radioKasko: {
                paymentTypeName = "Kasko";
                break;
            }
        }
    }

    public void getUserID() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserUid = currentUser.getUid();
    }
}