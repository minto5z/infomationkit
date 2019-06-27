package com.mintosoft.infomationkit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class WorkerReport extends AppCompatActivity {

    private static final int[] TABLE_HEADERS = {R.string.header_col1, R.string.header_col2, R.string.header_col3, R.string.header_col4, R.string.header_col5, R.string.header_col6};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
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
        tableView.setColumnCount(6);
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, arrayReceived));

        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));
        //tableView.setHeaderElevation(10);

        TableColumnWeightModel columnModel = new TableColumnWeightModel(6);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 2);
        tableView.setColumnModel(columnModel);

        TableColumnDpWidthModel columnModel1 = new TableColumnDpWidthModel(getApplicationContext(), 6, 230);
        columnModel1.setColumnWidth(1, 330);
        columnModel1.setColumnWidth(2, 300);
        tableView.setColumnModel(columnModel1);

        //tableView.addOnScrollListener(new MyOnScrollListener());

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
