package com.example.spike_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    final String INT_FIELD_ERR = "Health, Honey Stores, Queen Production, Hive Equipment," +
            " \nInventory Equipement, Losses, and Gains must have numeric inputs!";
    EditText regUsername;
    EditText regHiveName;
    EditText regAddress;
    EditText regResultInspection;
    EditText regHealth;
    EditText regHoneyStores;
    EditText regQueenProduction;
    EditText regHiveEquip;
    EditText regInvenEquip;
    EditText regLosses;
    EditText regGains;
    EditText regPassword;
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setContentView(R.layout.activity_register);
        regUsername = findViewById(R.id.regUsername);
       // regHiveName = findViewById(R.id.regHiveName);
        regAddress = findViewById(R.id.regAddress);
//        regResultInspection = findViewById(R.id.regResultInspection);
//        regHealth = findViewById(R.id.regHealth);
//        regHoneyStores = findViewById(R.id.regHoneyStores);
//        regQueenProduction = findViewById(R.id.regQueenProduction);
//        regHiveEquip = findViewById(R.id.regHiveEquip);
//        regInvenEquip = findViewById(R.id.regInvenEquip);
//        regLosses = findViewById(R.id.regLosses);
//        regGains = findViewById(R.id.regGains);
        regPassword = findViewById(R.id.regPassword);

        buttonRegister = findViewById(R.id.btnRegister);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int queenProduction;
                final int hiveEquip;
                final int invenEquip;
                final int losses;
                final int gains;
                final int health;
                final int honeyStores;
                final HashMap hive;

                // if there are any empty fields
                if (regUsername.getText().toString().isEmpty() || regHiveName.getText().toString().isEmpty()
                        || regResultInspection.getText().toString().isEmpty() || regHealth.getText().toString().isEmpty()
                        || regHoneyStores.getText().toString().isEmpty()|| regQueenProduction.getText().toString().isEmpty()
                        || regHiveEquip.getText().toString().isEmpty()|| regInvenEquip.getText().toString().isEmpty()
                        || regLosses.getText().toString().isEmpty()||regGains.getText().toString().isEmpty()
                        || regPassword.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Error: Empty Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    final String username = regUsername.getText().toString();
                    String password = regPassword.getText().toString();
                    final String hiveName = regHiveName.getText().toString();
                    final String address = regAddress.getText().toString();
                    final String resultInspection = regResultInspection.getText().toString();
                    String healthStr = regHealth.getText().toString(); // int
                    String honeyStoresStr =  regHoneyStores.getText().toString(); // int
                    String queenProductionStr = regQueenProduction.getText().toString(); // int
                    String hiveEquipStr = regHiveEquip.getText().toString(); // int
                    String invenEquipStr = regInvenEquip.getText().toString(); // int
                    String lossesStr = regLosses.getText().toString(); // int
                    String gainsStr = regGains.getText().toString(); // int

                    // convert from string to ints
                    try {
                        queenProduction = Integer.parseInt(queenProductionStr);
                        hiveEquip = Integer.parseInt(hiveEquipStr);
                        invenEquip = Integer.parseInt(invenEquipStr);
                        losses = Integer.parseInt(lossesStr);
                        gains = Integer.parseInt(gainsStr);
                        health = Integer.parseInt(healthStr);
                        honeyStores = Integer.parseInt(honeyStoresStr);
                    } catch(Exception e) {
                        Toast.makeText(RegisterActivity.this, INT_FIELD_ERR, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // creating hive object
                    hive = new HashMap();
                    hive.put("username", username);
                    hive.put("hivename", hiveName);
                    hive.put("address", address);
                    hive.put("inspection_results", resultInspection);
                    hive.put("health", health);
                    hive.put("honey_stores", honeyStores);
                    hive.put("queen_production", queenProduction);
                    hive.put("hive_equipment", hiveEquip);
                    hive.put("inventory_equipment", invenEquip);
                    hive.put("losses", losses);
                    hive.put("gains", gains);

                    BackendlessUser newAccount = new BackendlessUser();
                    newAccount.setProperty("username", username);
                    newAccount.setPassword(password);

                    Backendless.UserService.register(newAccount, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(RegisterActivity.this, "Registering...", Toast.LENGTH_SHORT).show();
                            Backendless.Data.of("Hives").save(hive, new AsyncCallback<Map>() {
                                @Override
                                public void handleResponse(Map response) {
                                    Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(RegisterActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            RegisterActivity.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(RegisterActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}
