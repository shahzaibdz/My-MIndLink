package com.example.mindlink;

import static com.example.mindlink.Note.COL_TITLE;

import java.util.Objects;

public class Task {
    public String task;
    public int taskId = 0;

    public static final String TABLE_NAME = "Task";
    public static final String COL_ID = "id";
    public static final String COL_TASK = "Task";

    public static final String CREATE_TABLE_TASK = String.format("CREATE TABLE IF NOT EXISTS %s(%s INTEGER PRIMARY KEY AUTOINCREMENT , %s TEXT)",TABLE_NAME,COL_ID,COL_TASK);
    public static final String DROP_TABLE_TASK = "DROP TABLE IF EXISTS "+TABLE_NAME;
    public static final String SELECT_ALL_TASK = "SELECT * FROM "+TABLE_NAME;

    public Task(String task, int taskId) {
        this.task = task;
        this.taskId = taskId;
    }
    public Task(String task) {
        this.task = task;
    }

    public Task() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task1 = (Task) o;
        return taskId == task1.taskId && Objects.equals(task, task1.task);
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(task, taskId);
    }

    @Override
    public String toString() {
        return "Task{" +
                "task='" + task + '\'' +
                ", taskId=" + taskId +
                '}';
    }
}

