package com.example.lintangapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lintangapp.adapter.NumbersView;
import com.example.lintangapp.adapter.NumbersViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyUserActivity extends AppCompatActivity {

    String name_S;
    String role_S;
    TextView texttest;
    FloatingActionButton add;
    FloatingActionButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_user);

        add = findViewById(R.id.add);
        home = findViewById(R.id.home);
        texttest = findViewById(R.id.texttest);

        final ArrayList<NumbersView> arrayList = new ArrayList<NumbersView>();

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                name_S = null;
                role_S = null;
            } else {
                name_S = extras.getString("name");
                role_S = extras.getString("role");
            }
        } else {
            name_S = (String) savedInstanceState.getSerializable("name");
            role_S = (String) savedInstanceState.getSerializable("role");

        }
        arrayList.add(new NumbersView(R.drawable.plus, name_S, role_S));

        NumbersViewAdapter numbersArrayAdapter = new NumbersViewAdapter(this, arrayList);

        ListView numbersListView = findViewById(R.id.listFav);
        numbersListView.setAdapter(numbersArrayAdapter);

        add.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
            startActivity(intent);
        });
        home.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }
}