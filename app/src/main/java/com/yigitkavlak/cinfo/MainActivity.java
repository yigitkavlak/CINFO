package com.yigitkavlak.cinfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    String userID;

    public static LayoutInflater inflater;

    private FirebaseAuth firebaseAuth;
    private ActionBar toolbar;  //Navigation Bar


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = getSupportActionBar();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_cinfo);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        // toolbar.setTitle("Search");
        loadFragment(new FuelFragment());

        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        BottomNavigationView navigation = findViewById(R.id.navigation);                    //.xml dosyamızda tanımladığımız id'si navigasyon olan BottomNavigationView'in nesnesini oluşturduk.
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);  //listener ekledik.

        firebaseAuth = FirebaseAuth.getInstance();  // initializing

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_search:
                    //  toolbar.setTitle("Search");
                    fragment = new FuelFragment();
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_home:
                     //toolbar.setTitle("Home");
                    fragment = new TireFragment();
                    loadFragment(fragment);
                    return true;

                case R.id.navigation_forum:
                    //  toolbar.setTitle("Home");
                    fragment = new PaymentFragment();
                    loadFragment(fragment);
                    return true;


                case R.id.navigation_profile:
                    //   toolbar.setTitle("Profile");
                    fragment = new ProfileFragment();
                    loadFragment(fragment);
                    return true;
            }

            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        //load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.top_menu,menu);


        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.profile){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            ProfileFragment profileFragment = new ProfileFragment();
            //fragmentTransaction.add(R.id.frameLayout,secondFragment).commit();
            fragmentTransaction.replace(R.id.frame_container, profileFragment).commit();

        }else if(item.getItemId() == R.id.signout){

            firebaseAuth.signOut();

            Intent intentToSignUp = new Intent(MainActivity.this,Login.class);
            startActivity(intentToSignUp);
            finish();

        }


        return super.onOptionsItemSelected(item);
    }

    public void getUser(){

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = currentUser.getUid();
    }


}