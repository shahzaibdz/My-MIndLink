package com.example.mindlink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

 public class NoteAdaptor extends RecyclerView.Adapter<NoteAdaptor.NoteViewHolder>    {

    private List<Note> notes;
    public NoteAdaptor(List<Note> notes) {
        this.notes = notes;
    }
    //222222222222222
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;



    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener = onItemLongClickListener;
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
            //33333333333333
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        onItemClickListener.onItemClick(position);
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemLongClickListener != null) {
                        int positionLong = getAdapterPosition();
                        onItemLongClickListener.onItemLongClick(positionLong);
                    }
                    return false;
                }
            });

        }
    }
  //111111
    interface OnItemClickListener {

        void onItemClick(int position);
    }

    interface OnItemLongClickListener {
        void onItemLongClick(int positionLong);
    }
}
