package com.dmitryweiner.todolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final ArrayList<Todo> todos;

    public TodoAdapter(Context context, ArrayList<Todo> todos) {
        this.inflater = LayoutInflater.from(context);
        this.todos = todos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = this.todos.get(position);
        holder.title.setText(todo.getTitle());
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.title);
        }
    }
}
