package com.mintosoft.infomationkit;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.model.TableColumnDpWidthModel;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class WorkerReport extends AppCompatActivity {

    private static final int[] TABLE_HEADERS = {R.string.header_col2, R.string.header_col3};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String[][] arrayReceived = null;
        String[][] arrayReceived1 = null;
        Object[] objectArray = null;
        Object[] objectArray1 = null;
        objectArray = (Object[]) getIntent().getExtras().getSerializable("list");
        if (objectArray != null) {
            arrayReceived = new String[objectArray.length][];
            for (int i = 0; i < objectArray.length; i++) {
                arrayReceived[i] = (String[]) objectArray[i];
            }
        }

        objectArray1 = (Object[]) getIntent().getExtras().getSerializable("list1");
        if (objectArray1 != null) {
            arrayReceived1 = new String[objectArray1.length][];
            for (int i = 0; i < objectArray1.length; i++) {
                arrayReceived1[i] = (String[]) objectArray1[i];
                Log.d("WorkerReport.this", "-   +   -> " + arrayReceived1[i]);
            }
        }

        TableView<String[]> tableView = (TableView<String[]>) findViewById(R.id.tableView);
        tableView.setColumnCount(2);
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, arrayReceived));

        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, TABLE_HEADERS));
        //tableView.setHeaderElevation(10);

        TableColumnWeightModel columnModel = new TableColumnWeightModel(2);
        columnModel.setColumnWeight(1, 2);
        columnModel.setColumnWeight(2, 2);
        tableView.setColumnModel(columnModel);

        TableColumnDpWidthModel columnModel1 = new TableColumnDpWidthModel(getApplicationContext(), 2, 180);
        columnModel1.setColumnWidth(1, 300);
        columnModel1.setColumnWidth(2, 230);
        tableView.setColumnModel(columnModel1);

        final String[][] finalArrayReceived = arrayReceived1;
        tableView.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {


                String[] clickedString = new String[30];
                for (int i = 0; i < finalArrayReceived[rowIndex].length; i++) {
                    clickedString[i] = finalArrayReceived[rowIndex][i];
                }
                showSimpleAdapterAlertDialog(clickedString);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private void showSimpleAdapterAlertDialog(String[] clickedString) {

        String[] maintitle ={
                "Passport Name","Passport Number",
                "Worker Number","Processing Rate",
                "Visa Number","Company Id Number",
                "Occupation","Diving Licence Status",
                "Police Clearence Status","Medical Status",
                "Musaned Status","Musaned Date",
                "Okala Status","Okala Date",
                "Mofa Status","Mofa Date",
                "Stamping Status","Stamping Date",
                "Finger Training Status","Finger Training Date",
                "Training","Training Date",
                "Manpower Status","Manpower Date",
                "Flight Status","Flight Date",
                "Completed","Completed Date",
                "Account Status","Account Date"

        };
        AlertDialog.Builder   alertdialog = new AlertDialog.Builder(WorkerReport.this);
        alertdialog.setTitle("Information Details");
        alertdialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        LayoutInflater inflaterr = (LayoutInflater)WorkerReport.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewtemplelayout= inflaterr.inflate(R.layout.list, null);
        MyListAdapter adap=new MyListAdapter(WorkerReport.this, maintitle,clickedString);
        ListView list = (ListView)viewtemplelayout.findViewById(R.id.list);
        list.setAdapter(adap);
        alertdialog.setView(viewtemplelayout);
        alertdialog.show();
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
