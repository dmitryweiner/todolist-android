package com.dmitryweiner.todolist;

import java.util.UUID;

public class Todo {
    private final String id;
    private final String title;
    private final boolean isDone;

    public Todo(String title) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.isDone = false;
    }

    public String getTitle() {
        return this.title;
    }

    public String getId() {
        return this.id;
    }

    public boolean getIsDone() {
        return this.isDone;
    }
}
