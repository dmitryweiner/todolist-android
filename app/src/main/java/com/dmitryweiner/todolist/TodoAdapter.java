package com.dmitryweiner.todolist;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder> {
    public static enum FILTER_MODE {
      ALL,
      DONE,
      NOT_DONE
    };

    private final LayoutInflater inflater;
    private final ArrayList<Todo> todos;
    private ArrayList<Todo> filteredTodos;
    private FILTER_MODE filterMode = FILTER_MODE.ALL;

    public TodoAdapter(Context context, ArrayList<Todo> todos) {
        this.inflater = LayoutInflater.from(context);
        this.todos = todos;
        this.filteredTodos = new ArrayList<Todo>(todos);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo todo = this.filteredTodos.get(position);
        holder.checkBox.setText(todo.getTitle());
        holder.checkBox.setChecked(todo.getIsDone());
        holder.button.setTag(position);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                TodoAdapter.this.todos.remove(TodoAdapter.this.filteredTodos.get(position));
                TodoAdapter.this.filteredTodos.remove(position);
                TodoAdapter.this.notifyItemRemoved(position);
                TodoAdapter.this.notifyItemRangeChanged(position, TodoAdapter.this.filteredTodos.size());
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Todo todo = TodoAdapter.this.filteredTodos.get(position);
                todo.toggleIsDone();
                TodoAdapter.this.notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.filteredTodos.size();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setFilterMode(FILTER_MODE filterMode) {
        this.filterMode = filterMode;
        switch (this.filterMode) {
            case ALL:
                this.filteredTodos = new ArrayList<Todo>(this.todos);
                break;
            case DONE:
                this.filteredTodos = (ArrayList<Todo>) this.todos.stream().filter(Todo::getIsDone).collect(Collectors.toList());
                break;
            case NOT_DONE:
                this.filteredTodos = (ArrayList<Todo>) this.todos.stream().filter(todo -> !todo.getIsDone()).collect(Collectors.toList());
                break;
        }
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
