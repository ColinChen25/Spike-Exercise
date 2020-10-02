package com.example.spike_exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HiveEdit extends AppCompatActivity {

    final String INT_ERR_MSG = "Health, Honey Stores, Queen Production, Hive Equipment \nInventory Equipment, Loss, and Gains must be of number value";
    EditText hivename;
    EditText inspection;
    EditText health;
    EditText honeystores;
    EditText queenprod;
    EditText hive_equip;
    EditText inven_equip;
    EditText edit_loss;
    EditText edit_gains;
    int index;

    Button edit_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_hive_edit);

        // Bottom navigation bar for interchanging
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nagivation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navHives:
                        Intent hiveListIntent = new Intent(HiveEdit.this, HivesList.class);
                        startActivity(hiveListIntent);
                        break;
                    case R.id.navSearch:
                        break;
                    case R.id.navProfile:
                        Intent intent = new Intent(HiveEdit.this, UserProfile.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        // initialize fields
        hivename = findViewById(R.id.edit_hivename);
        inspection = findViewById(R.id.edit_inspection);
        health = findViewById(R.id.edit_health);
        honeystores = findViewById(R.id.edit_honeystores);
        queenprod = findViewById(R.id.queenprod);
        hive_equip = findViewById(R.id.hive_equip);
        inven_equip = findViewById(R.id.inven_equip);
        edit_loss = findViewById(R.id.edit_loss);
        edit_gains = findViewById(R.id.gain);
        edit_save = findViewById(R.id.edit_save);
        index = getIntent().getIntExtra("index", 0);

        // set the fields from info hive
        hivename.setText(getIntent().getStringExtra("hivename"));
        inspection.setText(getIntent().getStringExtra("inspection_results"));
        health.setText(getIntent().getStringExtra("health"));
        honeystores.setText(getIntent().getStringExtra("honey_stores"));
        queenprod.setText(getIntent().getStringExtra("queen_production"));
        hive_equip.setText(getIntent().getStringExtra("hive_equipment"));
        inven_equip.setText(getIntent().getStringExtra("inventory_equipment"));
        edit_loss.setText(getIntent().getStringExtra("loss"));
        edit_gains.setText(getIntent().getStringExtra("gains"));


        // save the inputted fields
        edit_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hivename.getText().toString().isEmpty()|| inspection.getText().toString().isEmpty()|| health.getText().toString().isEmpty()||
                honeystores.getText().toString().isEmpty()|| queenprod.getText().toString().isEmpty()|| hive_equip.getText().toString().isEmpty()||
                inven_equip.getText().toString().isEmpty()|| edit_loss.getText().toString().isEmpty()|| edit_gains.getText().toString().isEmpty()) {
                    Toast.makeText(HiveEdit.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    try {
                        ApplicationClass.hives.get(index).setHivename(hivename.getText().toString());
                        ApplicationClass.hives.get(index).setInspection_results(inspection.getText().toString());
                        ApplicationClass.hives.get(index).setHealth(health.getText().toString());
                        ApplicationClass.hives.get(index).setHoney_stores(honeystores.getText().toString());
                        ApplicationClass.hives.get(index).setQueen_production(queenprod.getText().toString());
                        ApplicationClass.hives.get(index).setHive_equipment(hive_equip.getText().toString());
                        ApplicationClass.hives.get(index).setInventory_equipment(inven_equip.getText().toString());
                        ApplicationClass.hives.get(index).setLosses(edit_loss.getText().toString());
                        ApplicationClass.hives.get(index).setGains(edit_gains.getText().toString());
                    }
                    catch (Exception e) {
                        Toast.makeText(HiveEdit.this, INT_ERR_MSG, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Backendless.Persistence.save(ApplicationClass.hives.get(index), new AsyncCallback<Hives>() {
                        @Override
                        public void handleResponse(Hives response) {
                            Intent intent = new Intent(HiveEdit.this, HivesList.class);
                            startActivity(intent);
                            HiveEdit.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(HiveEdit.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

    }
}