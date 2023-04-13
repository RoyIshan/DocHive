package com.fatbit.dochive;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Fragment {

    private SearchView searchView;
    DrawerLayout mDrawerLayout;
    private ListView listView;
    private List<String> itemList = new ArrayList<>();
    private List<String> filteredList =  new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View views = inflater.inflate(R.layout.activity_main, container, false);

        FloatingActionButton wFab = views.findViewById(R.id.assigntask);

        wFab.setOnClickListener(view -> new AddPatDetails().show(getActivity().getSupportFragmentManager(), AddPatDetails.TAG));

        itemList.add("Item 1");
        itemList.add("Item 2");
        itemList.add("Item 3");
        itemList.add("Item 4");
        itemList.add("Item 5");
        itemList.add("Item 6");
        itemList.add("Item 7");
        itemList.add("Item 8");
        itemList.add("Item 8");
        itemList.add("Item 9");
        listView = views.findViewById(R.id.rview);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, itemList);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, filteredList);
        listView.setAdapter(adapter);

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
                    Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
                }
                else {
                    listView.setAdapter(adapter2);
                }
            }
        });
        return views;
    }
}