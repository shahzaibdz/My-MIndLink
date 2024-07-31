package com.example.mindlink;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class TaskViewHolder extends RecyclerView.ViewHolder {
    TextView taskTitle;

    public TaskViewHolder(@NonNull View itemView) {
        super(itemView);
        taskTitle = itemView.findViewById(R.id.tvTask_rv);
    }

}
