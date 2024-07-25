package com.example.mindlink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdaptor extends RecyclerView.Adapter<NoteAdaptor.NoteViewHolder>    {

    private List<Note> notes;
    public NoteAdaptor(List<Note> notes) {
        this.notes = notes;
    }
    @NonNull
    @Override
    public NoteAdaptor.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_view,parent,false);
    return new NoteViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdaptor.NoteViewHolder holder, int position) {
     Note note = notes.get(position);
     holder.title.setText(note.getTitle());
     holder.description.setText(note.getDescription());
     holder.time.setText(note.getTime());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title,description,time;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.rv_title);
            description = itemView.findViewById(R.id.rv_description);
            time = itemView.findViewById(R.id.rv_time);
        }
    }
}
