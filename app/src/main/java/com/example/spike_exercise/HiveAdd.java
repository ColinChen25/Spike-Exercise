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

public class HiveAdd extends AppCompatActivity {

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

        ed_hivename = findViewById(R.id.edit_hivename);
        ed_inspection = findViewById(R.id.edit_inspection);
        ed_health = findViewById(R.id.edit_health);
        ed_honeystores = findViewById(R.id.edit_honeystores);
        ed_queenprod = findViewById(R.id.queenprod);
        ed_inven_equip = findViewById(R.id.inven_equip);
        ed_hive_equip = findViewById(R.id.hive_equip);
        ed_loss = findViewById(R.id.edit_loss);
        ed_gain = findViewById(R.id.gain);

        saveButton = findViewById(R.id.edit_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(ed_hivename.getText().toString().isEmpty() || ed_inspection.toString().isEmpty() || ed_health.toString().isEmpty()
                        || ed_honeystores.toString().isEmpty() || ed_queenprod.toString().isEmpty() || ed_inven_equip.toString().isEmpty()
                        || ed_hive_equip.toString().isEmpty() || ed_loss.toString().isEmpty() || ed_gain.toString().isEmpty()){
                    Toast.makeText(HiveAdd.this, "Please Enter All Required Fields", Toast.LENGTH_SHORT).show();
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

                    String username = ApplicationClass.user.getProperty("username").toString();
                    // Create a new hiveinfo object
                    Hives hive = new Hives();

                    hive.setUsername(username);
                    hive.setHivename(hivename);
                    hive.setInspection_results(inspection);

                    hive.setHealth(health.equals("null") ?0:Integer.parseInt(health));
                    hive.setHoney_stores(honeystores.equals("null")?0:Integer.parseInt(honeystores));
                    hive.setQueen_production(queenprod.equals("null")?0:Integer.parseInt(queenprod));
                    hive.setInventory_equipment(inven_equip.equals("null")?0:Integer.parseInt(inven_equip));
                    hive.setHive_equipment(hive_equip.equals("null")?0:Integer.parseInt(hive_equip));
                    hive.setLosses(loss.equals("null")?0:Integer.parseInt(loss));
                    hive.setGains(gain.equals("null")?0:Integer.parseInt(gain));
                    hive.setAddress(ApplicationClass.user.getProperty("address").toString());

                    Backendless.Persistence.save(hive, new AsyncCallback<Hives>() {

                        @Override
                        public void handleResponse(Hives response) {
                            Toast.makeText(HiveAdd.this, "New Hive saved successfully" , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HiveAdd.this, HivesList.class);
                            startActivity(intent);
                            HiveAdd.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(HiveAdd.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}