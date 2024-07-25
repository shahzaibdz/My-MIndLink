package com.example.mindlink;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class CreateNote extends Fragment {
    ImageButton ibNote, ibTask;
    View view;
    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_create_note, container, false);

        ibNote = view.findViewById(R.id.ibNote);
        ibTask = view.findViewById(R.id.ibTask);
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
      return view;
    }

}