package com.example.spike_exercise;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_hive_edit);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nagivation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }


}
