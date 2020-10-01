package com.example.spike_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;
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
    String name_of_hive;
    int index;
    Button editButton;
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

        name_of_hive = getIntent().getStringExtra("hive_name");
        info_hive_name.setText(name_of_hive);
        editButton = findViewById(R.id.edit_button);

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
                        intent.putExtra("losses",response.get(0).get("losses").toString());
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

    }
}