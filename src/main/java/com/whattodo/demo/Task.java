package com.whattodo.demo;

public class Task {


    boolean isDone;
    boolean isImportant;
    String taskName;

    public Task (String taskName, boolean isImportant){
        this.taskName = taskName;
        this.isImportant = isImportant;
    }
}
