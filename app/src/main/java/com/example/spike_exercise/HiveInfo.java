package com.example.spike_exercise;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import org.w3c.dom.Text;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class HiveInfo extends AppCompatActivity {

    TextView info_hive_name;
    TextView info_address_data;
    TextView hive_res_o_ins_data;
    TextView info_health_data;
    TextView info_honey_stores_data;
    TextView info_queen_production_data;
    TextView info_hive_equipment_data;
    TextView info_inven_equipment_data;
    TextView info_losses_data;
    TextView info_gains_data;
    String name_of_hive;
    int index;
    Button editButton;
    Button deleteButton;

    // Hive Image
    ImageView hiveImage;
    private Uri imageUri;
    FirebaseStorage storage;
    private StorageReference storage_reference;
    String imageKey = "";
    final int HIVE_IMAGE_REQUEST_CODE = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_hive_info);

        // Bottom navigation bar for interchanging
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nagivation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navHives:
                        Intent hiveListIntent = new Intent(HiveInfo.this, HivesList.class);
                        startActivity(hiveListIntent);
                        break;
                    case R.id.navSearch:

                        break;
                    case R.id.navProfile:
                        Intent intent = new Intent(HiveInfo.this, UserProfile.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        info_hive_name = findViewById(R.id.info_hive_name);
        info_address_data = findViewById(R.id.info_address_data);
        hive_res_o_ins_data = findViewById(R.id.hive_res_o_ins_data);
        info_health_data = findViewById(R.id.info_health_data);
        info_honey_stores_data = findViewById(R.id.info_honey_stores_data);
        info_queen_production_data = findViewById(R.id.info_queen_production_data);
        info_hive_equipment_data = findViewById(R.id.info_hive_equipment_data);
        info_inven_equipment_data = findViewById(R.id.info_inven_equipment_data);
        info_losses_data = findViewById(R.id.info_losses_data);
        info_gains_data = findViewById(R.id.info_gains_data);

        name_of_hive = getIntent().getStringExtra("hive_name");
        info_hive_name.setText(name_of_hive);
        editButton = findViewById(R.id.edit_button);
        deleteButton = findViewById(R.id.delete_button);

        index = getIntent().getIntExtra("index", 0);


        final DataQueryBuilder query = DataQueryBuilder.create();
        query.setWhereClause("hivename = '" + name_of_hive + "'");

        // get data from user's hive info and set them to the textviews
        Backendless.Data.of("Hives").find(query, new AsyncCallback<List<Map>>() {
            @Override
            public void handleResponse(List<Map> response) {
                info_address_data.setText(response.get(0).get("address").toString());
                hive_res_o_ins_data.setText(response.get(0).get("inspection_results").toString());
                info_health_data.setText(response.get(0).get("health").toString());
                info_honey_stores_data.setText(response.get(0).get("honey_stores").toString());
                info_queen_production_data.setText(response.get(0).get("queen_production").toString());
                info_hive_equipment_data.setText(response.get(0).get("hive_equipment").toString());
                info_inven_equipment_data.setText(response.get(0).get("inventory_equipment").toString());
                info_losses_data.setText(response.get(0).get("losses").toString());
                info_gains_data.setText(response.get(0).get("gains").toString());
            }

            @Override
            public void handleFault(BackendlessFault fault) {

                //
                Toast.makeText(HiveInfo.this, fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(HiveInfo.this, HiveEdit.class);
                intent.putExtra("index", index);

                Backendless.Data.of("Hives").find(query, new AsyncCallback<List<Map>>() {
                    @Override
                    public void handleResponse(List<Map> response) {

                        intent.putExtra("hivename", response.get(0).get("hivename").toString());
                        intent.putExtra("address", response.get(0).get("address").toString());
                        intent.putExtra("inspection_results", response.get(0).get("inspection_results").toString());
                        intent.putExtra("health", response.get(0).get("health").toString());
                        intent.putExtra("honey_stores", response.get(0).get("honey_stores").toString());
                        intent.putExtra("queen_production", response.get(0).get("queen_production").toString());
                        intent.putExtra("hive_equipment", response.get(0).get("hive_equipment").toString());
                        intent.putExtra("inventory_equipment", response.get(0).get("inventory_equipment").toString());
                        intent.putExtra("loss",response.get(0).get("losses").toString());
                        intent.putExtra("gains", response.get(0).get("gains").toString());
                        startActivity(intent);

                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(HiveInfo.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // deletes the current viewing hive
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(HiveInfo.this);
                dialog.setMessage("Are you sure you want to delete this hive?");
                dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Backendless.Persistence.of(Hives.class).remove(ApplicationClass.hives.get(index), new AsyncCallback<Long>() {
                            @Override
                            public void handleResponse(Long response) {
                                ApplicationClass.hives.remove(index);
                                Toast.makeText(HiveInfo.this, "Hive Has Been Removed", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(HiveInfo.this, HivesList.class);
                                startActivity(intent);
                                HiveInfo.this.finish();


                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(HiveInfo.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.show();

            }
        });
        hiveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                if(imageUri != null) {
                    uploadImageFile();
                    ApplicationClass.hives.get(index).setImageKey(imageKey);
                }
                Backendless.Persistence.save(ApplicationClass.hives.get(index), new AsyncCallback<Hives>() {
                    @Override
                    public void handleResponse(Hives response) {
                        Intent intent = new Intent(HiveInfo.this, HivesList.class);
                        startActivity(intent);
                        HiveInfo.this.finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(HiveInfo.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent. ACTION_GET_CONTENT);
        startActivityForResult(intent, HIVE_IMAGE_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == HIVE_IMAGE_REQUEST_CODE && resultCode == RESULT_OK && (data != null) && (data.getData() != null)) {
            imageUri = data.getData();
            hiveImage.setImageURI(imageUri);
        }
    }

    private void uploadImageFile() {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Uploading your hive image...");
        pd.show();
        imageKey = UUID.randomUUID().toString();
        StorageReference riversRef = storage_reference.child("images/" + imageKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Upload Successful", Snackbar.LENGTH_LONG ).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(HiveInfo.this, "Error:" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progressPercent = (100.00 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                pd.setMessage((int)progressPercent + "%");
            }
        });
    }
    // Menu for topbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_info_menu, menu);
        return true;
    }

    // Function for clicking on item
    // TODO: Add functions and calls for the proper screen

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.infoEdit:
                Toast.makeText(this, "profileEdit selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.infoRemove:
                Toast.makeText(this, "logout selected", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}