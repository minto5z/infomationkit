package com.mintosoft.infomationkit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class Received extends AppCompatActivity {
    private static final int[] TABLE_HEADERS = {R.string.header_col4, R.string.header_col5, R.string.header_col6};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[][] arrayReceived = null;
        Object[] objectArray = null;
        objectArray = (Object[]) getIntent().getExtras().getSerializable("list");
        if (objectArray != null) {
            arrayReceived = new String[objectArray.length][];
            for (int i = 0; i < objectArray.length; i++) {
                arrayReceived[i] = (String[]) objectArray[i];
            }
        }
        TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
        tableView.setColumnCount(3);
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, arrayReceived));

        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));
        //tableView.setHeaderElevation(10);

        TableColumnWeightModel columnModel = new TableColumnWeightModel(3);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 2);
        tableView.setColumnModel(columnModel);

        TableColumnDpWidthModel columnModel1 = new TableColumnDpWidthModel(getApplicationContext(), 3, 100);
        columnModel1.setColumnWidth(1, 100);
        columnModel1.setColumnWidth(2, 200);
        tableView.setColumnModel(columnModel1);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
