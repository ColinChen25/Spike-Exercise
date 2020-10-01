package com.example.spike_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.spike_exercise.data.Hive;

public class HiveEdit extends AppCompatActivity {
    EditText ed_hivename;
    EditText ed_inspection;
    EditText ed_health;
    EditText ed_honeystores;
    EditText ed_queenprod;
    EditText ed_hive_equip;
    EditText ed_inven_equip;
    EditText ed_loss;
    EditText ed_gain;
    Button saveButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_hive_edit);

        ed_hivename = findViewById(R.id.hivename);
        ed_inspection = findViewById(R.id.inspection);
        ed_health = findViewById(R.id.health);
        ed_honeystores = findViewById(R.id.honeystores);
        ed_queenprod = findViewById(R.id.queenprod);
        ed_inven_equip = findViewById(R.id.inven_equip);
        ed_hive_equip = findViewById(R.id.hive_equip);
        ed_loss = findViewById(R.id.loss);
        ed_gain = findViewById(R.id.gain);

        saveButton = findViewById(R.id.edit_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ed_hivename.getText().toString().isEmpty()){
                    Toast.makeText(HiveEdit.this, "Please Enter the Name of the Hive", Toast.LENGTH_SHORT).show();
                } else {
                    // Retrieve data from the input fields
                    String hivename = ed_hivename.getText().toString().trim();
                    String inspection = ed_inspection.getText().toString().trim();
                    String health = ed_health.getText().toString().trim();
                    String honeystores = ed_honeystores.getText().toString().trim();
                    String queenprod = ed_queenprod.getText().toString().trim();
                    String inven_equip = ed_inven_equip.getText().toString().trim();
                    String hive_equip = ed_hive_equip.getText().toString().trim();
                    String loss = ed_loss.getText().toString().trim();
                    String gain = ed_gain.getText().toString().trim();

                    // Create a new hiveinfo object
                    Hive hive = new Hive();
                    hive.setHivename(hivename);
                    hive.setInspection_results(inspection);

                    hive.setHealth(health.equals("null")?0:Integer.parseInt(health));
                    hive.setHoney_stores(honeystores.equals("null")?0:Integer.parseInt(honeystores));
                    hive.setQueen_production(queenprod.equals("null")?0:Integer.parseInt(queenprod));
                    hive.setInventory_equipment(inven_equip.equals("null")?0:Integer.parseInt(inven_equip));
                    hive.setHive_equiptment(hive_equip.equals("null")?0:Integer.parseInt(hive_equip));
                    hive.setLosses(loss.equals("null")?0:Integer.parseInt(loss));
                    hive.setGains(gain.equals("null")?0:Integer.parseInt(gain));

                    Backendless.Persistence.save(hive, new AsyncCallback<Hive>() {

                        @Override
                        public void handleResponse(Hive response) {
                            Toast.makeText(HiveEdit.this, "New Hive saved successfully" , Toast.LENGTH_SHORT).show();
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