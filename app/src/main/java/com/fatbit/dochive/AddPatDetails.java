package com.fatbit.dochive;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddPatDetails extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewTask";

    private EditText wNameEdit,pID,phoneNumber;
    private Button wAssignBtn;

    private Context context;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_new_patient, container, false);
    }

    public interface OnDialogCloseListner {
        void onDialogClose(DialogInterface dialogInterface);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        wNameEdit = view.findViewById(R.id.task_edittext);
        wAssignBtn = view.findViewById(R.id.assignbtn);
        pID = view.findViewById(R.id.task_address);
        phoneNumber = view.findViewById(R.id.task_phoneNumber);
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        wNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (toString().equals("")) {
                    wAssignBtn.setEnabled(false);
                    wAssignBtn.setBackgroundColor(Color.GRAY);
                } else {
                    wAssignBtn.setEnabled(true);
                    wAssignBtn.setBackgroundColor(getResources().getColor(R.color.purple_200));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        wAssignBtn.setOnClickListener(view12 -> {
            String Name = wNameEdit.getText().toString();

            if (Name.isEmpty()) {
                Toast.makeText(context, "Empty Name not allowed!", Toast.LENGTH_SHORT).show();
            } else {
                Map<String, Object> patientMap = new HashMap<>();

                patientMap.put("Name ", Name);
                patientMap.put("Patient Id", pID.getText().toString());
                patientMap.put("number", phoneNumber.getText().toString());

                db.collection("Patient Detail")
                        .add(patientMap)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(context, "Data Added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            dismiss();
        });
    }


    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Activity activity = getActivity();
        if (activity instanceof OnDialogCloseListner){
            ((OnDialogCloseListner)activity).onDialogClose(dialog);
        }
    }
}
