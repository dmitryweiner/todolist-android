package com.dmitryweiner.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final ArrayList<Todo> todos = new ArrayList<Todo>();
    private TodoAdapter todoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                switch (id) {
                    case R.id.radioButtonAll:
                        Log.d("TODO", "Filter All");
                        break;
                    case R.id.radioButtonDone:
                        Log.d("TODO", "Filter Done");
                        break;
                    case R.id.radioButtonNotDone:
                        Log.d("TODO", "Filter Not Done");
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
        todos.addAll((ArrayList<Todo>) savedInstanceState.getSerializable("list"));
        todoAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                EditText editText = findViewById(R.id.editText);
                if (editText.length() == 0) {
                    break;
                }
                todos.add(new Todo(editText.getText().toString()));
                editText.setText("");
                break;
            case R.id.buttonClear:
                todos.clear();
                break;
            default:
                break;
        }
        todoAdapter.notifyDataSetChanged();
    }
}
