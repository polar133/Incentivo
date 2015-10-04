package com.monkeycoders.incentavo.incentivo.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.monkeycoders.incentavo.incentivo.R;

public class HistoryActivity extends AppCompatActivity {


    ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = (ListView) findViewById(R.id.history_list);

        String[] values = new String[] { "Eslevan - Fecha 15/10/2015 - Gasto 10.000",
                "Eslevan - Fecha 10/10/2015 - Gasto 15.000",
                "Liam - Fecha 30/09/2015 - Gasto 12.000",
                "Eslevan - Fecha 10/09/2015 - Gasto 11.000",
                "Carlos - Fecha 1/09/2015 - Gasto 1.000",
                "Eslevan - Fecha 27/08/2015 - Gasto 300",
                "Eslevan - Fecha 20/08/2015 - Gasto 5.000",
                "Eslevan - Fecha 19/08/2015 - Gasto 8.000"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Para mas informacion puedes ingresar en control web", Toast.LENGTH_LONG)
                        .show();

            }

        });
    }

}
