package com.fatbit.dochive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class ViewDetail extends AppCompatActivity {

    String pname;
    TextView pName, pId,pNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_detail);

        pname = getIntent().getStringExtra("name");
        Toast.makeText(this, pname, Toast.LENGTH_SHORT).show();
        pName = findViewById(R.id.pName);
        pId = findViewById(R.id.pID);
        pNumber = findViewById(R.id.number);

        Toast.makeText(this, pname, Toast.LENGTH_SHORT).show();
        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();
        db.collection("Patient Detail")
                .whereEqualTo("Name",pname)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Toast.makeText(ViewDetail.this, document.getString("Name "), Toast.LENGTH_SHORT).show();
                                pName.setText(document.getString("Name "));
                                pId.setText(document.getString("Patient ID"));
                                pNumber.setText(document.getString("number"));
                            }
                        } else {

                        }
                    }
                });
    }
}