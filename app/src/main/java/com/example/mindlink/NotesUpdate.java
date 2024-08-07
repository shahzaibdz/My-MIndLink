package com.example.mindlink;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesUpdate extends AppCompatActivity {
    MaterialToolbar materialToolbar;
    EditText et_title,et_writing;
    Button btnUpdate;
    DB db = DB.getInstance(this);
    List<Note> notesList = db.fetchNotes();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_update);
        materialToolbar = findViewById(R.id.update_icon);
        et_title = findViewById(R.id.update_title);
        et_writing = findViewById(R.id.update_writing);
        btnUpdate = findViewById(R.id.btn_update);


        Intent intent = getIntent();
        int position = intent.getIntExtra("Position", 0);
        et_title.setText(intent.getStringExtra("Title"));
        et_writing.setText(intent.getStringExtra("Description"));

        materialToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }

        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = notesList.get(position).getId();
                Log.d("Note Id", "onClick:  " + id);
                String title1 = et_title.getText().toString();
                Log.d("Note title", "onClick:  " + title1);
                String writing1 = et_writing.getText().toString();
                Log.d("Note body", "onClick:  " + writing1);

                DateFormat date = new SimpleDateFormat("HH:mm , dd/MM/yyyy", Locale.getDefault());
                String currentDateAndTime = date.format(new Date());

                Note note1 = new Note(title1, writing1, currentDateAndTime, id);

                if (db.updateNote(note1)) {
                    Toast.makeText(NotesUpdate.this, "Note Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NotesUpdate.this, "Note Not Updated", Toast.LENGTH_SHORT).show();
                }

                finish();
            }
        });


    }

    public void boldText(View view){
        SpannableString spannableString = new SpannableString(et_writing.getText().toString());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD),
                et_writing.getSelectionStart(),
                et_writing.getSelectionEnd(),0);
        et_writing.setText(spannableString);
    }
    public void italic(View view){
        SpannableString spannableString = new SpannableString(et_writing.getText().toString());
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC),
                et_writing.getSelectionStart(),
                et_writing.getSelectionEnd(),0);
        et_writing.setText(spannableString);
    }

    public void underLine(View view){
        SpannableString spannableString = new SpannableString(et_writing.getText().toString());
        spannableString.setSpan(new UnderlineSpan(),
                et_writing.getSelectionStart(),
                et_writing.getSelectionEnd(),0);
        et_writing.setText(spannableString);
    }

    public void ShareText(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,et_title.getText().toString() +"\n\n"+ et_writing.getText().toString()  );
        startActivity(Intent.createChooser(intent,"Share Text"));
        startActivity(intent);
    }
}