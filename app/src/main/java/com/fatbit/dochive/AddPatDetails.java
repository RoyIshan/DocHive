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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddPatDetails extends BottomSheetDialogFragment {
    public static final String TAG = "AddNewTask";

    private TextView setDeadline;
    private EditText wTaskEdit,address,phoneNumber;
    private Button wAssignBtn;

    private Context context;
    private String deadLine = "",NameofWorker;



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

        setDeadline = view.findViewById(R.id.set_deadline);
        wTaskEdit = view.findViewById(R.id.task_edittext);
        wAssignBtn = view.findViewById(R.id.assignbtn);
        address = view.findViewById(R.id.task_address);
        phoneNumber = view.findViewById(R.id.task_phoneNumber);



        wTaskEdit.addTextChangedListener(new TextWatcher() {
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

        setDeadline.setOnClickListener(view1 -> {
            Calendar calendar = Calendar.getInstance();

            int MONTH = calendar.get(Calendar.MONTH);
            int YEAR = calendar.get(Calendar.YEAR);
            int DAY = calendar.get(Calendar.DATE);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view1, int year, int month, int dayOfMonth) {
                    month = month + 1;
                    setDeadline.setText(dayOfMonth + "/" + month + "/" + year);
                    deadLine = dayOfMonth + "/" + month + "/" + year;
                }
            }, YEAR, MONTH, DAY);
            datePickerDialog.show();
        });

        wAssignBtn.setOnClickListener(view12 -> {
            String task = wTaskEdit.getText().toString();
            String smsNumber = phoneNumber.getText().toString();
            String verificationCode = randomGenerate();
            String smsText = "Code for Worker Attendance verification is : "+verificationCode+" \nWorker will ask you for this code";

            Uri uri = Uri.parse("smsto:" + smsNumber);
            Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
            intent.putExtra("sms_body", smsText);
            startActivity(intent);

            if (task.isEmpty()) {
                Toast.makeText(context, "Empty task not allowed!", Toast.LENGTH_SHORT).show();
            } else {
                Map<String, Object> taskMap = new HashMap<>();

                taskMap.put("task", task);
                taskMap.put("deadline", deadLine);
                taskMap.put("status", 1);
                taskMap.put("address", address.getText().toString());
                taskMap.put("number", phoneNumber.getText().toString());
                taskMap.put("worker",NameofWorker);
                taskMap.put("code",verificationCode);

            }
            dismiss();
        });
    }

    private String randomGenerate() {
        return String.valueOf((int) (Math.random()*(9999-1000+1)+1000));
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
