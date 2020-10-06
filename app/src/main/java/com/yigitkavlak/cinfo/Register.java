package com.yigitkavlak.cinfo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;


public class Register extends AppCompatActivity {
    Spinner carSpinner;
    EditText mFullName, mEmail, mPassword;
    Button mRegisterBtn;
    TextView mLoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseUser firebaseUser;
    String userID;
    String emailDatabase;
    String passwordDatabase;
    String userNameDatabase;
    String userCarModelDatabase;


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mLoginBtn = findViewById(R.id.createText);


        emailDatabase = mEmail.getText().toString();
        passwordDatabase = mPassword.getText().toString();


        fAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        carSpinner = findViewById(R.id.carSpinner);    //Arac secme için spinner

        ArrayList<String> carList = new ArrayList<>();          //Spinner icini dolduracak bir liste olusturuyoruz

        carList.add("Honda");
        carList.add("Mazda");
        carList.add("Toyota");
        carList.add("Renault");
        carList.add("Ford");
        carList.add("Fiat");
        carList.add("BMW");
        carList.add("Mercedes");
        carList.add("Volvo");
        carList.add("Peugeot");
        carList.add("Citroen");
        carList.add("Chevrolet");

        carSpinner.setAdapter(new ArrayAdapter<>(Register.this, android.R.layout.simple_spinner_dropdown_item, carList));

        carSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCarModelDatabase = carSpinner.getSelectedItem().toString();
                userNameDatabase = mFullName.getText().toString();
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {

                    mEmail.setError("E-mail kısmı boş bırakılamaz!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Şifre kısmı boş bırakılamaz!");
                    return;
                }
                if (password.length() < 8) {

                    mPassword.setError("Şifre kısmı 8 karakterden küçük olamaz!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //kullanıcıyı firebase'e kaydetme

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //kullanıcının emailini onaylamak için mail gönder
                            FirebaseUser user = fAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Onay Maili Gönderildi.Lütfen Gelen Kutunu Kontrol Et.", Toast.LENGTH_SHORT).show();
                                    userID = fAuth.getCurrentUser().getUid();

                                    DocumentReference documentReference = firebaseFirestore.collection("Users").document(userID);

                                    HashMap<String, Object> userData = new HashMap<>();

                                    userData.put("eMail", email);
                                    userData.put("userName", userNameDatabase);
                                    userData.put("carModel", userCarModelDatabase);
                                    userData.put("date", FieldValue.serverTimestamp());
                                    documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("TAG", "onSuccess: user Profile is created for" + userID);


                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {


                                        }
                                    });


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email gönderilemedi." + e.getMessage());
                                }
                            });


                            Toast.makeText(Register.this, "Kullanıcı başarıyla oluşturuldu.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "HATALI DENEME!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });


            }
        });


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });


    }
}