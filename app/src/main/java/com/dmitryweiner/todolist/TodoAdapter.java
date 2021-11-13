package com.dmitryweiner.todolist;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
        holder.checkBox.setText(todo.getTitle());
        holder.checkBox.setChecked(todo.getIsDone());
        holder.button.setTag(position);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                TodoAdapter.this.todos.remove(position);
                TodoAdapter.this.notifyItemRemoved(position);
                TodoAdapter.this.notifyItemRangeChanged(position, TodoAdapter.this.todos.size());
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Todo todo = TodoAdapter.this.todos.get(position);
                todo.toggleIsDone();
                TodoAdapter.this.notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.todos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final CheckBox checkBox;
        final Button button;
        ViewHolder(View view){
            super(view);
            checkBox = view.findViewById(R.id.checkBox);
            button = view.findViewById(R.id.button);
        }
    }
}
