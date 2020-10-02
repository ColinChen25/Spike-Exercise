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
import com.backendless.persistence.local.UserIdStorageFactory;

public class LoginActivity extends AppCompatActivity {

    EditText loginUsernameField;
    EditText loginPasswordField;
    Button loginSignInButton;
    Button loginRegisterButton;
    String userObjectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsernameField = findViewById(R.id.loginUsername);
        loginPasswordField = findViewById(R.id.loginPassword);
        loginSignInButton = findViewById(R.id.btnLogin);
        loginRegisterButton = findViewById(R.id.btnLoginToReg);


        loginSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginUsernameField.getText().toString().isEmpty() || loginPasswordField.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    String username = loginUsernameField.getText().toString();
                    String password = loginPasswordField.getText().toString();

                    Backendless.UserService.login(username, password, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            ApplicationClass.user = response;
                            Toast.makeText(LoginActivity.this, "Successful Login", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, HivesList.class);
                            String currentUser = (String) Backendless.UserService.CurrentUser().getProperty("username");
                            intent.putExtra("current_user", currentUser);
                            startActivity(intent);
                            LoginActivity.this.finish();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {

                            Toast.makeText(LoginActivity.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        loginRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}