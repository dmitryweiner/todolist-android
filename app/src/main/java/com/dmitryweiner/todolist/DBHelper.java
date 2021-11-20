package com.dmitryweiner.todolist;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tododb";
    public static final String TABLE_NAME = "todos";
    public static final String KEY_ID = "id";
    public static final String KEY_TITLE = "title";
    public static final String KEY_IS_DONE = "is_done";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " TEXT NOT NULL, " + KEY_TITLE + " TEXT NOT NULL, " + KEY_IS_DONE + " INTEGER NOT NULL)");
        // we use own Java-generated ID
        db.execSQL("CREATE INDEX " + TABLE_NAME + "_indexed ON " + TABLE_NAME +"(" + KEY_ID + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_NAME);
        onCreate(db);
    }

    public ArrayList<Todo> getTodos() {
        ArrayList<Todo> result = new ArrayList<Todo>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.query(TABLE_NAME, null, null, null,
                null, null, null);
        if (cursor.moveToFirst())
        {
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int titleIndex = cursor.getColumnIndex(KEY_TITLE);
            int isDoneIndex = cursor.getColumnIndex(KEY_IS_DONE);
            do {
                Todo todo = new Todo(
                        cursor.getString(idIndex),
                        cursor.getString(titleIndex),
                        cursor.getInt(isDoneIndex) == 1);
                result.add(todo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return result;
    }

    public void addTodo(Todo todo) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_ID, todo.getId());
        contentValues.put(DBHelper.KEY_TITLE, todo.getTitle());
        contentValues.put(DBHelper.KEY_IS_DONE, todo.getIsDone() ? 1: 0);
        database.insert(DBHelper.TABLE_NAME, null, contentValues);
        this.close();
    }

    public void updateTodo(Todo todo) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_TITLE, todo.getTitle());
        contentValues.put(DBHelper.KEY_IS_DONE, todo.getIsDone() ? 1: 0);
        database.update(DBHelper.TABLE_NAME, contentValues, DBHelper.KEY_ID + " = ?", new String[] {todo.getId()});
        this.close();
    }

    public void removeTodo(Todo todo) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DBHelper.TABLE_NAME, DBHelper.KEY_ID + " = ?", new String[] {todo.getId()});
        this.close();
    }

    public void removeAllTodos() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DBHelper.TABLE_NAME, null, null);
        this.close();
    }
}
