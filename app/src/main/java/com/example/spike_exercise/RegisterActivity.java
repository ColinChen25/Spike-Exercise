package com.example.spike_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    EditText regEmail;
    EditText regPhone;
    EditText regAddress;
    EditText regPassword;
    ImageView regProfilePic;
    Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setContentView(R.layout.activity_register);
        regUsername = findViewById(R.id.regUsername);
        regEmail = findViewById(R.id.regEmail);
        regPhone = findViewById(R.id.regPhone);
        regAddress = findViewById(R.id.regAddress);
        regPassword = findViewById(R.id.regPassword);
        buttonRegister = findViewById(R.id.btnRegister);
        regProfilePic = findViewById(R.id.regProfilePic);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // if there are any empty fields
                if (regUsername.getText().toString().isEmpty() || regEmail.getText().toString().isEmpty() ||
                       regPhone.getText().toString().isEmpty() || regPassword.getText().toString().isEmpty()|| regAddress.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Error: Empty Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    final String username = regUsername.getText().toString();
                    String password = regPassword.getText().toString();
                    String email = regEmail.getText().toString();
                    String address = regAddress.getText().toString();
                    String phone = regPhone.getText().toString();

                    // creating hive object

                    BackendlessUser newAccount = new BackendlessUser();
                    newAccount.setProperty("username", username);
                    newAccount.setPassword(password);
                    newAccount.setProperty("email", email);
                    newAccount.setProperty("address", address);
                    newAccount.setProperty("phone_number", phone);

                    Backendless.UserService.register(newAccount, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(RegisterActivity.this, "Registering...", Toast.LENGTH_SHORT).show();
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

        regProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
            }
        });
    }
}
