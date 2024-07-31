package com.example.mindlink;

import static java.util.Locale.filter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class CreateNote extends Fragment {
    ImageButton ibNote, ibTask , btnDrawer;
    View view;
    SearchView searchView;
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    NavigationView navigationDrawer;
    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_create_note, container, false);

        ibNote = view.findViewById(R.id.ibNote);
        ibTask = view.findViewById(R.id.ibTask);
        btnDrawer = view.findViewById(R.id.btnDrawer);
        searchView = view.findViewById(R.id.searchView);
        drawerLayout = view.findViewById(R.id.drawerLayout);
        navigationDrawer = view.findViewById(R.id.navigationDrawer);
        frameLayout = view.findViewById(R.id.frameLayout);

        ibNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NotesSave.class);
                startActivity(intent);

//                Toast.makeText(MainActivity.this, "100", Toast.LENGTH_SHORT).show();
            }
        });
        ibTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),NotesSave.class);
                startActivity(intent);
            }
        });

        btnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });

        navigationDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.accessibility){
                    Toast.makeText(getContext(), "Accessibility is open", Toast.LENGTH_SHORT).show();
                }

                drawerLayout.close();
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });


      return view;
    }
    public void filter(String newText){
        List<Note> filteredList = new ArrayList<>();
        for (Note item : filteredList){
            if (item.getTitle().toLowerCase().contains(newText.toLowerCase()) || item.getDescription().toLowerCase().contains(newText.toLowerCase())){
                filteredList.add(item);
                Toast.makeText(getContext(), "Notes Searched", Toast.LENGTH_SHORT).show();
            }
        }
        NoteAdaptor noteAdaptor = new NoteAdaptor(filteredList);
        noteAdaptor.notifyDataSetChanged();

    }
    private void loadFragment(Fragment fragment ){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout,fragment);
        transaction.commit();

    }

}