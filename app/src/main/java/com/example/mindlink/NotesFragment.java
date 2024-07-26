package com.example.mindlink;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NotesFragment extends Fragment {

      ImageButton noteSend;
      View view;
      RecyclerView rvNotes;
      List<Note>notesList;
    NoteAdaptor noteAdaptor;
      DB db = DB.getInstance(getContext());
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_notes, container, false);
        noteSend = view.findViewById(R.id.noteSend);
        rvNotes = view.findViewById(R.id.rvNotes);
        notesList = generateNotes();
        noteAdaptor = new NoteAdaptor(notesList);
        rvNotes.setAdapter(noteAdaptor);
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNotes.setHasFixedSize(true);
        noteSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(),NotesSave.class);
                startActivity(intent);
            }
        });

//        notesList = db.fetchNotes();


       noteAdaptor.setOnItemClickListener(new NoteAdaptor.OnItemClickListener() {
           @Override
           public void onItemClick(int position) {
               Intent intent = new Intent(getContext(),NotesUpdate.class);
               startActivity(intent);
           }
       });

       noteAdaptor.setOnItemLongClickListener(new NoteAdaptor.OnItemLongClickListener() {
           @Override
           public void onItemLongClick(int positionLong) {
               new AlertDialog.Builder(getContext())
                       .setMessage("Do You Want To Delete This Note")
                       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                               String dbTitle = notesList.get(positionLong).getTitle();
                               String dbWriting = notesList.get(positionLong).getDescription();



                               Note note = new Note(dbTitle,dbWriting,"25/04/2023",2);
                               if (db.deleteNote(note)){
                                   Toast.makeText(getContext(), "Note Save", Toast.LENGTH_SHORT).show();
                               }else {
                                   Toast.makeText(getContext(), "Note Not Save", Toast.LENGTH_SHORT).show();
                               }
                           }
                       })
                       .setNegativeButton("No", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {

                           }
                       })
                       .create()
                       .show();
           }
       });


       return view;
    }
    public List<Note> generateNotes(){
        List<Note> notes= new ArrayList<>();
        notes.add(new Note("Title","Description","25/04/2023",2));
        notes.add(new Note("Title","Description","25/04/2023",3));
        notes.add(new Note("Title","Description","25/04/2023",4));
        notes.add(new Note("Title","Description","25/04/2023",5));
        return notes;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}