package com.yigitkavlak.cinfo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.yigitkavlak.cinfo.R.drawable.ic_cinfo;


public class FuelTab1Fragment extends Fragment {
    RadioGroup gasSelector;
    RadioButton gasoline;
    RadioButton lpg;
    RadioButton diesel;
    int gasolineType;
    String gasolineName;
    Spinner gasolineCompanySpinner;
    ImageView cinfoHomeImage;
    Button gasolineAddButton;
    EditText gasolinePrice;
    String currentUserUid;
    String gasolineCompanyDatabase;
    String gasolinePriceDatabase;
    EditText gasolineDistance;
    String gasolineDistanceDatabase;

    private FuelTab2Fragment fuelTab2Fragment;


    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__fuel_tab1_fragment, container, false);
        getUserID();

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        gasSelector = view.findViewById(R.id.radioGasSelector);
        gasoline = view.findViewById(R.id.radioGasoline);
        lpg = view.findViewById(R.id.radioLPG);
        diesel = view.findViewById(R.id.radioDiesel);
        gasolineCompanySpinner = view.findViewById(R.id.gasolineCompanySpinner);
        cinfoHomeImage = view.findViewById(R.id.cinfoHomeImage);
        cinfoHomeImage.setImageResource(ic_cinfo);
        gasolineAddButton = view.findViewById(R.id.gasolineAddButton);
        gasolinePrice = view.findViewById(R.id.gasolinePrice);
        gasolineDistance = view.findViewById(R.id.gasolineDistance);

        ArrayList<String> carList = new ArrayList<>();          //Spinner icini dolduracak bir liste olusturuyoruz

        carList.add("Opet");
        carList.add("Bp");
        carList.add("Total");
        carList.add("Petrol Ofisi");
        carList.add("Shell");
        carList.add("Türkiye Petrolleri");


        gasolineCompanySpinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, carList));

        gasolineCompanySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    System.out.println("gg");           //Spinner 0 konumunda seçim yapılmadı
                } else {
                    System.out.println("aa");           //Spinner 0 konumunda değil. Seçim yapıldı.
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

       /* gasolineType = gasSelector.getCheckedRadioButtonId();

        switch (gasolineType)                                                   //Kullanicinin Yakıt Seçimini belirlemek icin kullanılan switch case yapisi
        {
            case R.id.radioGasoline: {
                gasolineName = "Benzin";
                break;
            }
            case R.id.radiodiesel: {
                gasolineName = "Dizel";
                break;
            }
            case R.id.radioLPG: {
                gasolineName = "LPG";
                break;
            }
        } */


        gasolineAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                fuelSelector();

                gasolineCompanyDatabase = gasolineCompanySpinner.getSelectedItem().toString().trim();
                gasolinePriceDatabase = gasolinePrice.getText().toString();
                gasolineDistanceDatabase = gasolineDistance.getText().toString();
                //firebaseFirestore.collection(currentUserUid).document("fuelData")
                Map<String, Object> fuelData = new HashMap<>();
                fuelData.put("fuelType", gasolineName);
                fuelData.put("fuelPrice", gasolinePriceDatabase);
                fuelData.put("gasolineCompany", gasolineCompanyDatabase);
                fuelData.put("gasolineDistance", gasolineDistanceDatabase);
                fuelData.put("date", FieldValue.serverTimestamp());
                // DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(currentUserUid);

                firebaseFirestore.collection("fuelData " + currentUserUid) //.document(currentUserUid)


                        .add(fuelData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                Toast.makeText(getContext(), "Kayıt Başarıyla Oluşturuldu", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });


            }

        });

        return view;
    }

    public void getUserID() {

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserUid = currentUser.getUid();
    }

    public void fuelSelector() {
        gasolineType = gasSelector.getCheckedRadioButtonId();
        System.out.println(gasolineType);

        switch (gasolineType)                                                   //Kullanicinin Yakıt Seçimini belirlemek icin kullanılan switch case yapisi
        {
            case R.id.radioGasoline: {
                gasolineName = "Benzin";
                break;
            }
            case R.id.radioDiesel: {
                gasolineName = "Dizel";
                break;
            }
            case R.id.radioLPG: {
                gasolineName = "LPG";
                break;
            }
        }


    }
}



