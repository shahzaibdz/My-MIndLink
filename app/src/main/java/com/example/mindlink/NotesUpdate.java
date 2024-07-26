package com.example.mindlink;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;

public class NotesUpdate extends AppCompatActivity {
    MaterialToolbar materialToolbar;
    EditText title,writing;
    Button btnSave;
    DB db = DB.getInstance(this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_save);
        materialToolbar = findViewById(R.id.icon);
        title = findViewById(R.id.title);
        writing = findViewById(R.id.writing);
        btnSave = findViewById(R.id.btnSave);

        materialToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }


        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dbTitle = title.getText().toString();
                String dbWriting = writing.getText().toString();


                Note note = new Note(dbTitle,dbWriting,"25/04/2023",2);
                if (db.updateNote(note)){
                    Toast.makeText(NotesUpdate.this, "Note Update", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(NotesUpdate.this, "Note Not Update", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });



    }
    public void boldText(View view){
        SpannableString spannableString = new SpannableString(writing.getText().toString());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD),
                writing.getSelectionStart(),
                writing.getSelectionEnd(),0);
        writing.setText(spannableString);
    }
    public void italic(View view){
        SpannableString spannableString = new SpannableString(writing.getText().toString());
        spannableString.setSpan(new StyleSpan(Typeface.ITALIC),
                writing.getSelectionStart(),
                writing.getSelectionEnd(),0);
        writing.setText(spannableString);
    }

    public void underLine(View view){
        SpannableString spannableString = new SpannableString(writing.getText().toString());
        spannableString.setSpan(new UnderlineSpan(),
                writing.getSelectionStart(),
                writing.getSelectionEnd(),0);
        writing.setText(spannableString);
    }

    public void ShareText(View view){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,title.getText().toString() +"\n\n"+ writing.getText().toString()  );
        startActivity(Intent.createChooser(intent,"Share Text"));
        startActivity(intent);
    }
}