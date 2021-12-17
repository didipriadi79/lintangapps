package com.example.lintangapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout layoutEmpty;

    EditText searchUser;
    RecyclerView listUser;
    FloatingActionButton add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutEmpty = findViewById(R.id.layout_empty);
        searchUser = findViewById(R.id.searchUser);
        listUser = findViewById(R.id.listUser);
        add = findViewById(R.id.fab);

        layoutEmpty.setVisibility(View.VISIBLE);

        add.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MyUserActivity.class);
            startActivity(intent);
        });

        searchUser.setOnEditorActionListener((v, actionId, event) -> {
            String strUsername =searchUser.getText().toString();
            if (strUsername.isEmpty()){
                Toast.makeText(MainActivity.this, "search tidak boleh kosong",Toast.LENGTH_SHORT).show();
            } else {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    layoutEmpty.setVisibility(View.GONE);
                    return true;
                }
            }
            return false;
        });
    }
}