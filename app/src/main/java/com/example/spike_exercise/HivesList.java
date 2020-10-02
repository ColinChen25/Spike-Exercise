package com.example.spike_exercise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HivesList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_hive_list);
<<<<<<< Updated upstream
=======
        current_username = getIntent().getStringExtra("current_username");
        addButton = findViewById(R.id.add_hive);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nagivation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navHives:
                        Toast.makeText(HivesList.this, "navHives selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navSearch:
                        Toast.makeText(HivesList.this, "navSearch selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navProfile:
                        Toast.makeText(HivesList.this, "navProfile selected", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        String whereClause = "username = '" + ApplicationClass.user.getProperty("username") + "'";

        DataQueryBuilder query = DataQueryBuilder.create();
        query.setWhereClause(whereClause);
        query.setGroupBy("created");

        recyclerView = findViewById(R.id.hive_list_recycler_view);



        Backendless.Persistence.of(Hives.class).find(query, new AsyncCallback<List<Hives>>() {
            @Override
            public void handleResponse(List<Hives> response) {

                ApplicationClass.hives = response;
                adapter = new HivesAdapter(HivesList.this, response);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(HivesList.this));

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent (HivesList.this, HiveAdd.class);
                intent.putExtra("current_username", current_username);
                startActivity(intent);
                HivesList.this.finish();
            }
        });
>>>>>>> Stashed changes
    }
}