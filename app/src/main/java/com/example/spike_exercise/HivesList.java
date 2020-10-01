package com.example.spike_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HivesList extends AppCompatActivity {
    Button addButton;
    String current_username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_hive_list);
        current_username = getIntent().getStringExtra("current_username");
        addButton = findViewById(R.id.add_hive);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent (HivesList.this, HiveEdit.class);
                intent.putExtra("current_username", current_username);
                startActivity(intent);
            }
        });
    }
}