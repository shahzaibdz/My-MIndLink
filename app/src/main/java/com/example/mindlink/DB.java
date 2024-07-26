package com.example.mindlink;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    private static DB instance;
    private static final String DB_NAME = "MINDLINK";
    private static final int DB_VERSION = 1;

    private DB(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }
    public static DB getInstance(Context context){
        if(instance == null){
            instance = new DB(context);
        }
        return instance;
        }
    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL(Note.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      if(oldVersion != newVersion){
          db.execSQL(Note.DROP_TABLE);
          db.execSQL(Note.CREATE_TABLE);
      }
    }
    public boolean insertNote(Note note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Note.COL_TITLE,note.getTitle());
        contentValues.put(Note.COL_DESCRIPTION,note.getDescription());
        contentValues.put(Note.COL_TIME,note.getTime());
        long rowId;
        try {
            rowId = db.insert(Note.TABLE_NAME,null,contentValues);
        }catch (Exception ex){
            return false;
        }
        return rowId!= -1;
    }

    public boolean updateNote(Note note){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Note.COL_TITLE,note.getTitle());
        contentValues.put(Note.COL_DESCRIPTION,note.getDescription());
        contentValues.put(Note.COL_TIME,note.getTime());
        long rowId;
        try {
            rowId = db.update(Note.TABLE_NAME,contentValues,Note.COL_ID+" = ?",new String[note.getId()]);
        }catch (Exception ex){
            return false;
        }
        return rowId != -1;
    }
    public boolean deleteNote(Note note){
        SQLiteDatabase db = getWritableDatabase();

        long rowId;
        try {
            rowId = db.delete(Note.TABLE_NAME,Note.COL_ID+" = ?",new String[note.getId()]);
        }catch (Exception ex){
            return false;
        }
        return rowId != -1;
    }

    public List<Note> fetchNotes(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(Note.SELECT_ALL_NOTES,null);
        List<Note> notes = new ArrayList<>(cursor.getCount());
        if(cursor.moveToFirst()){
            do {
                Note note = new Note();
                int index = cursor.getColumnIndex(Note.COL_TITLE);
                note.setTitle(cursor.getString(index));
                index = cursor.getColumnIndex(Note.COL_DESCRIPTION);
                note.setDescription(cursor.getString(index));
                index = cursor.getColumnIndex(Note.COL_TIME);
                note.setTime(cursor.getString(index));
                index = cursor.getColumnIndex(Note.COL_ID);
                note.setId(cursor.getInt(index));
                notes.add(note);
            }while (cursor.moveToFirst());
        }
        return notes;
    }

}
