package com.example.mindlink;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    FrameLayout frame;
    BottomNavigationView navigationBar;
    FragmentManager fragmentManager;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBar = findViewById(R.id.navigationBar);
        fragmentManager = getSupportFragmentManager();

        changeFragment(new CreateNote());
//        getSupportFragmentManager().beginTransaction().add(R.id.frame,new CreateNote()).commit();
        navigationBar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.createNote){
                    changeFragment(new CreateNote());
                    return true;

                } else if (item.getItemId()==R.id.notes) {
                    changeFragment(new NotesFragment());
                    return true;

                } else if (item.getItemId()==R.id.task) {
                    changeFragment(new TaskFragment());
                    return true;

                }
                return false;
            }
        });

    }
    void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame,fragment).commit();
    }

}