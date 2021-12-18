package com.example.lintangapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.lintangapp.adapter.NumbersView;

import java.util.ArrayList;

public class AddUserActivity extends AppCompatActivity {

    EditText inputName;
    EditText inputRole;
    Button addValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        inputName = findViewById(R.id.inputName);
        inputRole = findViewById(R.id.inputRole);
        addValue = findViewById(R.id.addValue);

        final ArrayList<NumbersView> arrayList = new ArrayList<NumbersView>();

        addValue.setOnClickListener(v -> {
            String name = inputName.getText().toString();
            String role = inputRole.getText().toString();

            Intent intent = new Intent(AddUserActivity.this, MyUserActivity.class);
            intent.putExtra("name", name);
            intent.putExtra("role", role);
            arrayList.add(new NumbersView(R.drawable.plus, name, role));
            startActivity(intent);
        });


    }
}