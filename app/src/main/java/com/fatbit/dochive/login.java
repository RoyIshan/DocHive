package com.fatbit.dochive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class login extends AppCompatActivity {

    EditText number;
    TextView signup;
    Button btn;
    FirebaseAuth fAuth;
    String mNumber;
    FirebaseFirestore db,dbp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fAuth = FirebaseAuth.getInstance();

        number = findViewById(R.id.number_login);
        btn = findViewById(R.id.btn_login);
        signup = findViewById(R.id.btn_signup);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mNumber =user.getPhoneNumber();

        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mNumber = "+91" + number.getText().toString();

                if (mNumber.length() == 13) {
                    Intent intent = new Intent(login.this, Otp.class);
                    intent.putExtra("mobile", mNumber);
                    startActivity(intent);
                } else {
                    number.setError("Length of Number should be 10");
                    number.requestFocus();
                }
            }});
    }
}