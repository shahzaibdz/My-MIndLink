package com.example.mindlink;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdaptor extends RecyclerView.Adapter<TaskAdaptor.TaskViewHolder>{

    List<Task> taskList;
    public TaskAdaptor (List<Task> taskList){
        this.taskList = taskList;
    }

    private OnItemLongClickListener OnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        OnItemLongClickListener = onItemLongClickListener;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_view_task,parent,false);
      return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskTitle.setText(task.getTask());

    }

    @Override
    public int getItemCount() {
      return taskList.size();
    }
    class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView taskTitle;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskTitle = itemView.findViewById(R.id.rv_task);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (OnItemLongClickListener != null){
                        int positionLong = getAdapterPosition();
                        OnItemLongClickListener.onLongClick(positionLong);
                    }

                    return false;
                }
            });

        }

    }

}

interface OnItemLongClickListener{
    void onLongClick(int position);

}