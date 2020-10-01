package com.example.spike_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {

    TextView profileName;
    TextView profileAddress;
    TextView profileEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        profileName = findViewById(R.id.profileName);
        profileAddress = findViewById(R.id.profileAddress);
        profileEmail = findViewById(R.id.profileEmail);

        profileName.setText(ApplicationClass.user.getProperty("username").toString());
        profileAddress.setText(ApplicationClass.user.getProperty("address").toString());
        profileEmail.setText(ApplicationClass.user.getProperty("email").toString());


    }
}