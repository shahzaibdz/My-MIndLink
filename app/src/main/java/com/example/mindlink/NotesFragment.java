package com.example.mindlink;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class NotesFragment extends Fragment {

      ImageButton noteSend;
      View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_notes, container, false);
        noteSend = view.findViewById(R.id.noteSend);
        noteSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(),NotesSave.class);
                startActivity(intent);
            }
        });
        return view;
    }
}