package com.example.spike_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

public class LoginActivtiy extends AppCompatActivity {

    EditText loginUsernameField;
    EditText loginPasswordField;
    Button loginSignInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsernameField = findViewById(R.id.loginUsernameField);
        loginPasswordField = findViewById(R.id.loginPasswordField);
        loginSignInButton = findViewById(R.id.loginSigninButton);


        loginSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginUsernameField.getText().toString().isEmpty()|| loginPasswordField.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivtiy.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                }

                else {
                    String username = loginUsernameField.getText().toString();
                    String password = loginPasswordField.getText().toString();

                    Backendless.UserService.login(username, password, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(LoginActivtiy.this, "Successful Login", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivtiy.this, HiveInfo.class));
                            LoginActivtiy.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(LoginActivtiy.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {

                // if a valid login
                if(response) {
                    String userObjectId = UserIdStorageFactory.instance().getStorage().get(); //gets currently logged in user's

                    Backendless.Data.of(BackendlessUser.class).findById(userObjectId, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            startActivity(new Intent(LoginActivtiy.this, HiveInfo.class));
                            LoginActivtiy.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(LoginActivtiy.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(LoginActivtiy.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}