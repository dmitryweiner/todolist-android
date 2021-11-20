package com.dmitryweiner.todolist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static enum FILTER_MODE {
        ALL,
        DONE,
        NOT_DONE
    };

    private ArrayList<Todo> todos;
    private ArrayList<Todo> filteredTodos;
    private TodoAdapter todoAdapter;
    private FILTER_MODE filterMode = FILTER_MODE.ALL;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DBHelper(this);
        todos = dbHelper.getTodos();
        filteredTodos = new ArrayList<Todo>(todos);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.radioButtonAll:
                        MainActivity.this.setFilterModeAndFilterTodos(FILTER_MODE.ALL);
                        break;
                    case R.id.radioButtonDone:
                        MainActivity.this.setFilterModeAndFilterTodos(FILTER_MODE.DONE);
                        break;
                    case R.id.radioButtonNotDone:
                        MainActivity.this.setFilterModeAndFilterTodos(FILTER_MODE.NOT_DONE);
                        break;
                }
            }
        });

        todoAdapter = new TodoAdapter(this, todos);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        recyclerView.setAdapter(todoAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("list", todos);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        todos = new ArrayList<Todo>();
        todos.addAll((ArrayList<Todo>) savedInstanceState.getSerializable("list"));
        filteredTodos = new ArrayList<Todo>(todos);
        todoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                EditText editText = findViewById(R.id.editText);
                String title = editText.getText().toString().trim();
                if (title.length() == 0) {
                    break;
                }
                Todo todo = new Todo(title);
                todos.add(todo);
                dbHelper.addTodo(todo);
                editText.setText("");
                break;
            case R.id.buttonClear:
                todos.clear();
                dbHelper.removeAllTodos();
                break;
            default:
                break;
        }
        this.filteredTodos = getFilteredTodos(this.todos, this.filterMode);
        this.todoAdapter.setTodos(this.filteredTodos);
    }

    public void setFilterModeAndFilterTodos(FILTER_MODE filterMode) {
        this.filterMode = filterMode;
        this.filteredTodos = getFilteredTodos(this.todos, this.filterMode);
        this.todoAdapter.setTodos(this.filteredTodos);
    }

    public void notifyItemRemoved(String itemId) {
        Todo foundTodo = null;
        for (Todo todo: this.todos) {
            if (todo.getId().equals(itemId)) {
                foundTodo = todo;
            }
        }
        if (foundTodo != null) {
            todos.remove(foundTodo);
            dbHelper.removeTodo(foundTodo);
        }
    }

    public void notifyItemChanged(Todo todo) {
        filteredTodos = getFilteredTodos(this.todos, this.filterMode);
        todoAdapter.setTodos(this.filteredTodos);
        dbHelper.updateTodo(todo);
    }

    public ArrayList<Todo> getFilteredTodos(ArrayList<Todo> todos, FILTER_MODE filterMode) {
        ArrayList<Todo> filteredTodos = new ArrayList<Todo>();
        switch (filterMode) {
            case ALL:
                filteredTodos = new ArrayList<Todo>(todos);
                break;
            case DONE:
                filteredTodos = new ArrayList<Todo>();
                for (Todo todo: todos) {
                    if (todo.getIsDone()) {
                        filteredTodos.add(todo);
                    }
                }
                break;
            case NOT_DONE:
                filteredTodos = new ArrayList<Todo>();
                for (Todo todo: todos) {
                    if (!todo.getIsDone()) {
                        filteredTodos.add(todo);
                    }
                }
                break;
        }
        return filteredTodos;
    }
}
