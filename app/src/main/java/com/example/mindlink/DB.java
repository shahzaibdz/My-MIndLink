package com.example.mindlink;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    private static DB instance;
    private static final String DB_NAME = "MINDLINK";
    private static final int DB_VERSION = 3;

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
     db.execSQL(Task.CREATE_TABLE_TASK);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      if(oldVersion != newVersion){
          db.execSQL(Note.DROP_TABLE);
          db.execSQL(Note.CREATE_TABLE);
          db.execSQL(Task.DROP_TABLE_TASK);
          db.execSQL(Task.CREATE_TABLE_TASK);

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


    public boolean updateNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Note.COL_TITLE, note.getTitle());
        contentValues.put(Note.COL_DESCRIPTION, note.getDescription());
        contentValues.put(Note.COL_TIME, note.getTime());
        contentValues.put(Note.COL_ID, note.getId());

        long rowId = -1;
        try {
            Log.d("Note Id in Update Note Method", "updateNote: " + note.getId());
            rowId = db.update(Note.TABLE_NAME, contentValues, Note.COL_ID + "=?", new String[]{String.valueOf(note.getId())});
            Log.d("Row ID", "updateNote:  " + rowId);
        } catch (Exception ex) {
            Log.e("Update Error", "updateNote: ", ex);
            return false;
        }

        return rowId > -1;
    }



    public boolean deleteNote(Note note){
        SQLiteDatabase db = getWritableDatabase();

        long rowId;
        try {
            rowId = db.delete(Note.TABLE_NAME,Note.COL_ID+"=?",new String[]{String.valueOf(note.getId())});
        }catch (Exception ex){
            return false;
        }
        return rowId != -1;
    }

    public List<Note> fetchNotes(){
        try {
            SQLiteDatabase db = getReadableDatabase();
            Log.d(" Flutter", db.getPath());
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
                }while (cursor.moveToNext());
            }
            cursor.close();
            return notes;
        }catch(Exception ex){
            Log.d("FETCH NOTE EXCEPTION", "fetchNotes: " + ex.toString());
            ArrayList<Note> noteList = new ArrayList<Note>();
            noteList.add(new Note("One Note","One Description","00:00 am",1));
            noteList.add(new Note("Two Note","Two Description","00:00 am",2));
            noteList.add(new Note("Three Note","Three Description","00:00 am",3));
            noteList.add(new Note("Four Note","Four Description","00:00 am",4));
            noteList.add(new Note("Five Note","Five Description","00:00 am",5));
            noteList.add(new Note("Six Note","Six Description","00:00 am",6));
            noteList.add(new Note("Seven Note","Seven Description","00:00 am",7));
            noteList.add(new Note("Eight Note","Eight Description","00:00 am",8));
            return noteList;


        }
    }

    public boolean insertTask(Task task){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Task.COL_TASK,task.getTask());
        long rowId;
        try {
            rowId = db.insert(Task.TABLE_NAME,null,contentValues);
        }catch (Exception ex){
            return false;
        }
        return rowId!= -1;
    }

    public List<Task> fetchTask(){
       try {
           SQLiteDatabase db = getReadableDatabase();
           Cursor cursor = db.rawQuery(Task.SELECT_ALL_TASK,null);
           List<Task> tasks = new ArrayList<>(cursor.getCount());
           if(cursor.moveToFirst()){
               do {
                   Task task = new Task();
                   int index = cursor.getColumnIndex(Task.COL_TASK);
                   task.setTask(cursor.getString(index));
                   index = cursor.getColumnIndex(Task.COL_ID);
                   task.setTaskId(cursor.getInt(index));
                   tasks.add(task);
               }while (cursor.moveToNext());
           }
           cursor.close();
           return tasks;
       }catch(Exception ex){
           ArrayList<Task> db = new ArrayList<>();
           db.add(new Task("Task 1",1));
           db.add(new Task("Task 2",2));
           db.add(new Task("Task 3",3));
           db.add(new Task("Task 4",4));
           db.add(new Task("Task 5",5));
           return db;
        }
    }

    public boolean deleteTask(Task task){
        SQLiteDatabase db = getWritableDatabase();

        long rowId;
        try {
            rowId = db.delete(Task.TABLE_NAME,Task.COL_ID+" = ?",new String[]{String.valueOf(task.getTaskId())});
            return rowId != -1;
        }catch (Exception ex){
            ex.printStackTrace();
            return false;
        }

    }

}
