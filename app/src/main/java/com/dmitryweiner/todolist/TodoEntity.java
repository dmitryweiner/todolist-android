package com.dmitryweiner.todolist;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;

@Entity(indices = {@Index("id")})
public class TodoEntity {
    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "is_done")
    public int isDone;

}
