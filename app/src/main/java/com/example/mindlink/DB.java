package com.example.mindlink;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    private static DB instance;
    private static final String DB_NAME = "mindlink.db";
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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
