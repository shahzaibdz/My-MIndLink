package com.example.mindlink;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class TaskFragment extends Fragment {
    private View view;
    private ImageButton createTask;
    private RecyclerView recyclerView;
    private TaskAdaptor taskAdaptor;
    private List<Task> taskList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DB db = DB.getInstance(getContext());

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_task, container, false);
        createTask = view.findViewById(R.id.createTask);
        recyclerView = view.findViewById(R.id.rv_task);

        // Fetch tasks from the database and initialize the task list
        taskList = db.fetchTask();

        // Initialize the adapter with the task list
        taskAdaptor = new TaskAdaptor(taskList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(taskAdaptor);

        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.bottom_sheet_dlg);
                TextView tvSaveTask = dialog.findViewById(R.id.tvSaveTask);
                EditText editText = dialog.findViewById(R.id.etTask);

                tvSaveTask.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String taskName = editText.getText().toString();
                        Task newTask = new Task(taskName);
                        if (db.insertTask(newTask)) {
                            Toast.makeText(getContext(), "Task Saved", Toast.LENGTH_SHORT).show();

                            // Update the task list and adapter
                            taskList.clear();
                            taskList.addAll(db.fetchTask());
                            taskAdaptor.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), "Task Not Saved", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });

                Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
                dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.show();
            }
        });

        taskAdaptor.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onLongClick(int position) {
                new AlertDialog.Builder(getContext())
                        .setMessage("Do you want to delete this task?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Task task = taskList.get(position);

                                if (db.deleteTask(task)) {
                                    taskList.remove(position);
                                    taskAdaptor.notifyItemRemoved(position);

                                    Toast.makeText(getContext(), "Task Deleted", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getContext(), "Task Not Deleted", Toast.LENGTH_SHORT).show();
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
        // Update task list on resume
        taskList.clear();
        taskList.addAll(db.fetchTask());
        taskAdaptor.notifyDataSetChanged();
    }
}
