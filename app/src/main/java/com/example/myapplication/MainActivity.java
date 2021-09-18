package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
        Button buttonClear = findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        TextView textView = findViewById(R.id.textView);
        EditText editText = findViewById(R.id.editText);

        switch (view.getId()) {
            case R.id.button:
                list.add(editText.getText().toString());
                editText.setText("");
                break;
            case R.id.buttonClear:
                list.clear();
                break;
            default:
                break;
        }

        StringBuilder result = new StringBuilder();
        for(int i = 0; i < list.size(); i++) {
            result.append(list.get(i)).append("\n");
        }
        textView.setText(result);
    }
}
