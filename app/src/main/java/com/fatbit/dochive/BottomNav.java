package com.fatbit.dochive;

import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class BottomNav extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    android.os.Handler customHandler = new android.os.Handler();
    Location lastKnownLocation;

    String uid;
    MainActivity firstFragment = new MainActivity();
    //WorkerProfile secondFragment = new WorkerProfile();
    //ViewAttendance thirdFragment = new ViewAttendance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.item3);




    }


   @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item3:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, firstFragment).commit();
                return true;

            /*case R.id.item1:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
                return true;

            case R.id.item2:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String mobile = user.getPhoneNumber();
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    db.collection("Worker detail")
                            .whereEqualTo("Phone Number",mobile)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String name =document.getString("Name");
                                            Bundle bundle = new Bundle();
                                            bundle.putString("String", name);
                                            thirdFragment.setArguments(bundle);
                                            getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();

                                        }
                                    } else {
                                        Toast.makeText(BottomNav.this,"error",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else {
                    Toast.makeText(BottomNav.this, "error", Toast.LENGTH_SHORT).show();
                }

                return true;

            case R.id.item4:
                startActivity(new Intent(BottomNav.this, MapsActivity.class));
                return true;

            case R.id.item5:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(BottomNav.this, login.class));
                Toast.makeText(BottomNav.this, "You have logged out", Toast.LENGTH_SHORT).show();
                return true;*/

        }
        return false;
    }



}