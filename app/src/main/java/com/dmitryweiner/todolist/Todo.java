package com.dmitryweiner.todolist;

import java.util.UUID;

public class Todo implements java.io.Serializable {
    private final String id;
    private final String title;
    private boolean isDone;

    public Todo(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.isDone = false;
    }

    public Todo(String id, String title, Boolean isDone) {
        this.id = id;
        this.title = title;
        this.isDone = isDone;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public void toggleIsDone() {
        this.isDone = !this.isDone;
    }
}
