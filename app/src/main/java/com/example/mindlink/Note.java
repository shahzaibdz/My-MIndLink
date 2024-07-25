package com.example.mindlink;

import java.util.Objects;

public class Note {
    public String title;
    public String description;
    public String time;
    public int id = 0;

    public static final String TABLE_NAME = "MindLink";
    public static final String COL_TITLE = "Title";
    public static final String COL_DESCRIPTION = "Description";
    public static final String COL_TIME = "Time";
    public static final String COL_ID = "id";


    public static final String CREATE_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s(%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT,%s TEXT,%s TEXT)",TABLE_NAME,COL_ID,COL_TITLE,COL_DESCRIPTION,COL_TIME);
    public static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
    public static final String SELECT_ALL_NOTES ="SELECT * FROM "+TABLE_NAME;


    public Note() {
    }

    public Note(String title, String description, String time , int id) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return id == note.id && Objects.equals(title, note.title) && Objects.equals(description, note.description) && Objects.equals(time, note.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, time, id);
    }

    @Override
    public String toString() {
        return "Note{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", id=" + id +
                '}';
    }
}
