package com.example.spike_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class HiveInfo extends AppCompatActivity {

    TextView hiveName;
    TextView addressInfo;
    TextView resOInspectionInfo;
    TextView healthInfo;
    TextView honeyStoresInfo;
    TextView queenProdInfo;
    TextView hiveEquipmentInfo;
    TextView invenEquipmentInfo;
    TextView lossesInfo;
    TextView gainsInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_hive_info);

        hiveName = findViewById(R.id.hiveName);
        addressInfo = findViewById(R.id.addressInfo);
        resOInspectionInfo = findViewById(R.id.resOInspection);
        healthInfo = findViewById(R.id.healthInfo);
        honeyStoresInfo = findViewById(R.id.honeyStoresInfo);
        queenProdInfo = findViewById(R.id.queenProd);
        hiveEquipmentInfo = findViewById(R.id.hiveEquipmentInfo);
        invenEquipmentInfo = findViewById(R.id.invenEquipmentInfo);
        lossesInfo = findViewById(R.id.lossesInfo);
        gainsInfo = findViewById(R.id.gainsInfo);



    }
}