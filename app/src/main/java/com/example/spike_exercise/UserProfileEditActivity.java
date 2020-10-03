package com.example.spike_exercise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class UserProfileEditActivity extends AppCompatActivity {


    private static final int EDIT_IMAGE_REQUEST_CODE = 4;
    ImageView profileEditPicture;
    EditText profileEditName;
    EditText profileEditAddress;
    EditText profileEditPhone;
    EditText profileEditEmail;
    Button editUserSave;
    private String filePath;
    private StorageReference storageReference;
    private Uri imageUri = null;
    private String imageKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);

        profileEditPicture = findViewById(R.id.profileEditPicture);
        profileEditName = findViewById(R.id.profileEditName);
        profileEditAddress = findViewById(R.id.profileEditAddress);
        profileEditPhone = findViewById(R.id.profileEditPhone);
        profileEditEmail = findViewById(R.id.profileEditEmail);
        editUserSave = findViewById(R.id.editUserSave);


        if(ApplicationClass.user.getProperty("profile_pic") == null) {
            String filePath = "images/" + ApplicationClass.user.getProperty("profile_pic").toString();
            storageReference = FirebaseStorage.getInstance().getReference().child(filePath);
            try {
                final File localFile = File.createTempFile(ApplicationClass.user.getProperty("profile_pic").toString(), "jpeg");
                storageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Bitmap bitImg = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        profileEditPicture.setImageBitmap(bitImg);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserProfileEditActivity.this, "Error: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }catch(IOException e) {
                Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        profileEditName.setText(ApplicationClass.user.getProperty("username").toString());
        profileEditAddress.setText(ApplicationClass.user.getProperty("address").toString());
        profileEditPhone.setText(ApplicationClass.user.getProperty("phone_number").toString());
        profileEditEmail.setText(ApplicationClass.user.getProperty("email").toString());

        // find a picture from device's image storage
        profileEditPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        editUserSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(profileEditAddress.getText().toString().isEmpty()|| profileEditEmail.getText().toString().isEmpty()||
                        profileEditName.getText().toString().isEmpty()|| profileEditPhone.getText().toString().isEmpty()) {
                    Toast.makeText(UserProfileEditActivity.this, "Error: Empty Fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    ApplicationClass.user.setProperty("email", profileEditAddress.getText().toString());
                    ApplicationClass.user.setProperty("phone_number", profileEditPhone.getText().toString());
                    ApplicationClass.user.setProperty("username", profileEditName.getText().toString());
                    ApplicationClass.user.setProperty("address", profileEditAddress.getText().toString());

                    if(imageUri != null) {
                        uploadImageFile();
                        ApplicationClass.user.setProperty("profile_pic", imageKey);
                    }

                    Backendless.UserService.update(ApplicationClass.user, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            Toast.makeText(UserProfileEditActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UserProfileEditActivity.this, UserProfile.class);
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(UserProfileEditActivity.this, "Error: " + fault.getMessage() , Toast.LENGTH_SHORT).show();
                            return;
                        }
                    });


                }
            }
        });
    }

    // Menu for topbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_edit_menu, menu);
        return true;
    }

    // Function for clicking on item
    // TODO: Add functions and calls for the proper screen

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profileSave:
                Toast.makeText(this, "profileSave selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.profileLogout:
                Toast.makeText(this, "logout selected", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // method that choose an image from the device's image storage
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent. ACTION_GET_CONTENT);
        startActivityForResult(intent, EDIT_IMAGE_REQUEST_CODE);

    }

    // set the newly selected image to the imageview
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == EDIT_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && (data != null) && (data.getData() != null)) {
            imageUri = data.getData();
            profileEditPicture.setImageURI(imageUri);
        }
    }

    private void uploadImageFile() {
        imageKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("images/" + imageKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Snackbar.make(findViewById(android.R.id.content), "Image Uploaded", Snackbar.LENGTH_LONG ).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {

                        Toast.makeText(UserProfileEditActivity.this, "Error:" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
            }
        });
    }
}