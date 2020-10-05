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

import java.util.HashMap;
import java.util.Map;

import static com.yigitkavlak.cinfo.R.drawable.ic_cinfo;


public class TireTab1Fragment extends Fragment {

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    String currentUserUid;
    int tireType;
    Button tireAddButton;
    EditText tireDistanceText;
    EditText tirePriceText;
    RadioGroup tireSelector;
    RadioButton summerTire;
    RadioButton winterTire;
    String tireTypeName;
    String tireDistanceDatabase;
    String tireTypeDatabase;
    String tirePriceDatabase;
    ImageView cinfoHomeImage;







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tire_tab1, container, false);
        getUserID();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        tireSelector = view.findViewById(R.id.radioTireSelector);
        summerTire = view.findViewById(R.id.radioSummer);
        winterTire = view.findViewById(R.id.radioWinter);
        tireAddButton = view.findViewById(R.id.tireAddButton);
        tireDistanceText = view.findViewById(R.id.tireDistanceText);
        tirePriceText = view.findViewById(R.id.tirePriceText);
        cinfoHomeImage = view.findViewById(R.id.cinfoHomeImage);
        cinfoHomeImage.setImageResource(ic_cinfo);

       /* tireType = tireSelector.getCheckedRadioButtonId();
        switch (tireType)                                                   //Kullanicinin Şirket Seçimini belirlemek icin kullanılan switch case yapisi
        {
            case R.id.radioSummer: {
                tireTypeName = "Yaz Lastiği";
                break;
            }
            case R.id.radioWinter: {
                tireTypeName = "Kış Lastiği";
                break;
            }

        } */

        tireAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTireSelector();
                tireDistanceDatabase = tireDistanceText.getText().toString().trim();
                tirePriceDatabase = tirePriceText.getText().toString().trim();
                Map<String, Object> tireData = new HashMap<>();

                tireData.put("tireType", tireTypeName);
                tireData.put("tirePrice", tirePriceDatabase);
                tireData.put("tireDistance", tireDistanceDatabase);
                tireData.put("date", FieldValue.serverTimestamp());

                firebaseFirestore.collection("tireData " + currentUserUid)
                        .add(tireData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {

                                Toast.makeText(getContext(),"Kayıt Başarıyla Oluşturuldu",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                    }
                });



            }
        });





        return view;
    }


    public void getTireSelector(){


        tireType = tireSelector.getCheckedRadioButtonId();
        switch (tireType)                                                   //Kullanicinin Şirket Seçimini belirlemek icin kullanılan switch case yapisi
        {
            case R.id.radioSummer: {
                tireTypeName = "Yaz Lastiği";
                break;
            }
            case R.id.radioWinter: {
                tireTypeName = "Kış Lastiği";
                break;
            }

        }
    }

    public void getUserID(){

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUserUid = currentUser.getUid();
    }

}