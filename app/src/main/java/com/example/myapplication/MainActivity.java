package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> list = new ArrayList<String>();
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(arrayAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("list", list);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        list.addAll((ArrayList<String>) savedInstanceState.getSerializable("list"));
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                EditText editText = findViewById(R.id.editText);
                list.add(editText.getText().toString());
                editText.setText("");
                break;
            case R.id.buttonClear:
                list.clear();
                break;
            default:
                break;
        }
        arrayAdapter.notifyDataSetChanged();
    }
}
