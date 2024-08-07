package com.example.mindlink;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_notes, container, false);
        noteSend = view.findViewById(R.id.noteSend);
        rvNotes = view.findViewById(R.id.rvNotes);
        DB db = DB.getInstance(getContext());
        notesList = db.fetchNotes();
//        notesList = generateNotes();
//        List<Note> notesList = generateNotes();
        noteAdaptor = new NoteAdaptor(notesList);
        rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvNotes.setAdapter(noteAdaptor);
        rvNotes.setHasFixedSize(true);
//        Toast.makeText(getContext(), "Your Notes"+notesList.size(), Toast.LENGTH_SHORT).show();


//        try {
//            noteAdaptor = new NoteAdaptor(generateNotes());
//            rvNotes.setLayoutManager(new LinearLayoutManager(getContext()));
//            rvNotes.setAdapter(noteAdaptor);
//            rvNotes.setHasFixedSize(true);
//        }catch (Exception e){
//            Log.d("Try error : ", e.getMessage());
//        }

        noteSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getContext(),NotesSave.class);
                startActivity(intent);
            }
        });


       noteAdaptor.setOnItemClickListener(new NoteAdaptor.OnItemClickListener() {
           @Override
           public void onItemClick(int position) {
               List<Note> notes = db.fetchNotes();
               Intent intent = new Intent(getContext() , NotesUpdate.class);
               intent.putExtra("Position", position);
               intent.putExtra("Title", notes.get(position).getTitle());
               intent.putExtra("Description", notes.get(position).getDescription());
               intent.putExtra("Time", notes.get(position).getTime());
               startActivity(intent);
           }
       });

        noteAdaptor.setOnItemLongClickListener(new NoteAdaptor.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(int positionLong) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int id = notesList.get(positionLong).getId();

                                Note note = new Note(
                                        notesList.get(positionLong).getTitle(),
                                        notesList.get(positionLong).getDescription(),
                                        notesList.get(positionLong).getTime(),
                                        id
                                );

                                if (db.deleteNote(note)) {
                                    // Remove the note from the list and notify the adapter
                                    notesList.remove(positionLong);
                                    noteAdaptor.notifyItemRemoved(positionLong);

                                    Toast.makeText(getContext(), "Note Deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Note Not Deleted", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create()
                        .show();
            }
        });

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        DB db = DB.getInstance(getContext());
        notesList.clear(); // Clear the old list
        notesList.addAll(db.fetchNotes()); // Add the updated notes
        noteAdaptor.notifyDataSetChanged(); // Notify the adapter to refresh the RecyclerView
    }
}

//    public List<Note> generateNotes(){
//        List<Note> notes= new ArrayList<>();
//        notes.add(new Note("Title","Description","25/04/2023",2));
//        notes.add(new Note("Title","Description","25/04/2023",3));
//        notes.add(new Note("Title","Description","25/04/2023",4));
//        notes.add(new Note("Title","Description","25/04/2023",5));
//        return notes;





