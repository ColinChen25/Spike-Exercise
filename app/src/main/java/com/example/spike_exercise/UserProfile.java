package com.example.spike_exercise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.backendless.Backendless;
import com.backendless.files.BackendlessFile;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;


public class UserProfile extends AppCompatActivity {

    TextView profileName;
    TextView profileAddress;
    TextView profileEmail;
    TextView profilePhone;
    ImageView profilePic;
    private String filePath;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_user_profile);


        profileName = findViewById(R.id.profileName);
        profileAddress = findViewById(R.id.profileAddress);
        profileEmail = findViewById(R.id.profileEmail);
        profilePic = findViewById(R.id.profilePicture);
        profilePhone = findViewById(R.id.profilePhone);
        if(ApplicationClass.user.getProperty("profile_pic") == null) {
            filePath = "images/" + ApplicationClass.user.getProperty("profile_pic").toString();
            storageReference = FirebaseStorage.getInstance().getReference().child(filePath);
            try {
                final File localFile = File.createTempFile(ApplicationClass.user.getProperty("profile_pic").toString(), "jpeg");
                storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitImg = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        profilePic.setImageBitmap(bitImg);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserProfile.this, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }catch(IOException e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        // set up the textviews
        profileName.setText(ApplicationClass.user.getProperty("username").toString());
        profileAddress.setText(ApplicationClass.user.getProperty("address").toString());
        profileEmail.setText(ApplicationClass.user.getProperty("email").toString());
        profilePhone.setText(ApplicationClass.user.getProperty("phone_number").toString());

    }
}