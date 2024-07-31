package com.example.mindlink;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
      View view;
      ImageButton createTask;
//      TextView tvSaveTask;
//      EditText etTask;
      RecyclerView recyclerView;
      TaskAdaptor taskAdaptor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DB db = DB.getInstance(getContext());
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_task, container, false);
        createTask = view.findViewById(R.id.createTask);
//        tvSaveTask = view.findViewById(R.id.tvSaveTask);
//        etTask = view.findViewById(R.id.etTask);
        recyclerView = view.findViewById(R.id.rv_task);
        taskAdaptor = new TaskAdaptor(db.fetchTask());
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
                        String task = editText.getText().toString();
                        Task task1 = new Task(task);
                        if (db.insertTask(task1)){
                            Toast.makeText(getContext(), "Task Saved", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getContext(), "Task Not Saved", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                });

             Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT , ViewGroup.LayoutParams.WRAP_CONTENT);
             dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
             dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
             dialog.getWindow().setGravity(Gravity.BOTTOM);
                dialog.show();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}