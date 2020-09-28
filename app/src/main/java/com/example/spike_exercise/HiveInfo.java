package com.example.spike_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

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
    String userObjectID;
    String current_user;
    Map info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_hive_info);

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

        userObjectID = UserIdStorageFactory.instance().getStorage().get();
        // receive the username of the logged in user
        current_user = getIntent().getStringExtra("current_user");
        info_hive_name.setText(current_user);

        // get data from user's hive info and set them to the textviews
        Backendless.Data.of("Hives").find(new AsyncCallback<List<Map>>() {
            @Override
            public void handleResponse(List<Map> response) {
                for(int i = 0; i < response.size(); i++) {
                    if(response.get(i).get("username").equals(current_user)) {
                        info = response.get(i);
                        info_address_data.setText(info.get("address").toString());
                        hive_res_o_ins_data.setText(info.get("inspection_results").toString());
                        info_health_data.setText(info.get("health").toString());
                        info_honey_stores_data.setText(info.get("honey_stores").toString());
                        info_queen_production_data.setText(info.get("queen_production").toString());
                        info_hive_equipment_data.setText(info.get("hive_equipment").toString());
                        info_inven_equipment_data.setText(info.get("inventory_equipment").toString());
                        info_losses_data.setText(info.get("losses").toString());
                        info_gains_data.setText(info.get("gains").toString());

                    }
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(HiveInfo.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}