package com.example.spike_exercise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.UploadCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;
import com.backendless.servercode.annotation.Async;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterActivity extends AppCompatActivity {



    public static final int IMAGE_REQUEST_CODE = 5;
    final String INT_FIELD_ERR = "Health, Honey Stores, Queen Production, Hive Equipment," +
            " \nInventory Equipement, Losses, and Gains must have numeric inputs!";
    EditText regUsername;
    EditText regEmail;
    EditText regPhone;
    EditText regAddress;
    EditText regPassword;
    ImageView regProfilePic;
    Button buttonRegister;
    private Uri imageUri;
    FirebaseStorage storage;
    private StorageReference storage_reference;
    String imageKey = "";
    Button btnRegToLogin;


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
        btnRegToLogin = findViewById(R.id.btnRegToLogin);

        storage = FirebaseStorage.getInstance();
        storage_reference = storage.getReference();

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
                    // if an image has been selected
                    if(imageUri != null) {
                        uploadImageFile();
                        newAccount.setProperty("profile_pic", imageKey);
                    }
                    Backendless.UserService.register(newAccount, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
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

        // method to invoke either the camera or photo storage to set a profile picture
        regProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnRegToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }
        });
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent. ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && (data != null) && (data.getData() != null)) {
           imageUri = data.getData();
           regProfilePic.setImageURI(imageUri);
        }
    }

    private void uploadImageFile() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Registering...");
        pd.show();
        imageKey = UUID.randomUUID().toString();
        StorageReference riversRef = storage_reference.child("images/" + imageKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Registration Success", Snackbar.LENGTH_LONG ).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(RegisterActivity.this, "Error:" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                pd.setMessage((int)progressPercent + "%");
            }
        });
    }
}
