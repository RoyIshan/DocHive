package com.fatbit.dochive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Fragment {

    private SearchView searchView;
    private ListView listView;
    private List<String> itemList = new ArrayList<>();
    private List<String> filteredList =  new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View views = inflater.inflate(R.layout.activity_main, container, false);

        FloatingActionButton wFab = views.findViewById(R.id.assigntask);

        wFab.setOnClickListener(view -> new AddPatDetails().show(getActivity().getSupportFragmentManager(), AddPatDetails.TAG));

        listView = views.findViewById(R.id.rview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.list_resource, R.id.textView2, itemList);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), R.layout.list_resource, R.id.textView2, filteredList);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Patient Detail")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Toast.makeText(getContext(), document.get("Patient Id").toString(), Toast.LENGTH_SHORT).show();
                                String data1 = document.get("Patient Id").toString();
                                String data2 =  document.get("Name ").toString();
                                itemList.add(data1+" - "+data2);
                                listView.setAdapter(adapter);
                            }
                        } else {
                            Toast.makeText(getContext(), "not done", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        searchView = views.findViewById(R.id.sView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filteredList.clear();
                filterList(s);
                return true;
            }

            private void filterList(String text) {
                for (String str : itemList){
                    if(str.toLowerCase().contains(text.toLowerCase())){
                        filteredList.add(str);
                    }
                }
                if(filteredList.isEmpty()){
                    listView.setAdapter(adapter2);
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                }
                else {
                    listView.setAdapter(adapter2);
                }
            }
        });
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            Intent intent = new Intent(getContext(),ViewDetail.class);
            String arr[] = selectedItem.split(" ");
            intent.putExtra("name", arr[0]);
            startActivity(intent);

            //textView.setText("The best football player is : " + selectedItem);
        });
        return views;
    }
}