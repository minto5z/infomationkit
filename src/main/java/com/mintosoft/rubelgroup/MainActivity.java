package com.mintosoft.rubelgroup;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mintosoft.rubelgroup.api.BaseApiService;
import com.mintosoft.rubelgroup.api.UtilsApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String PREFS_NAME = "LoginPrefs";
    private int sum = 0;
    BaseApiService mApiService;
    JSONObject jsonRESULTS;
    Call<ResponseBody> call;
    Response<ResponseBody> testresponse;
    LinearLayout l1,l2,l3,l4,l5,l6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mApiService = UtilsApi.getAPIService();
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        final String restoredText = "Bearer " + prefs.getString("access_token", null);


        l1 = (LinearLayout)findViewById(R.id.amit_p);
        l2 = (LinearLayout)findViewById(R.id.amit_r);
        l3 = (LinearLayout)findViewById(R.id.rubel_p);
        l4 = (LinearLayout)findViewById(R.id.rubel_r);
        l5 = (LinearLayout)findViewById(R.id.receive);
        l6 = (LinearLayout)findViewById(R.id.invoice);
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AmitProcessing.class));
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,AmirRecruiting.class));
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RubelProcessing.class));
            }
        });
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RubelRecruiting.class));
            }
        });
        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData("receives");
                //startActivity(new Intent(MainActivity.this,Received.class));
            }
        });
        l6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData("invoices");
                //startActivity(new Intent(MainActivity.this,Invoiced.class));
            }
        });
        call = mApiService.getLoginResponse(restoredText);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    testresponse = response;
                    try {
                        jsonRESULTS = new JSONObject(testresponse.body().string());//

                        String total_receive = jsonRESULTS.getString("total_receive");
                        TextView tv_total_receive = findViewById(R.id.total_receive);
                        tv_total_receive.setText("" + total_receive);

                        String total_invoice = jsonRESULTS.getString("total_invoice");
                        TextView tv_total_invoice = findViewById(R.id.total_invoice);
                        tv_total_invoice.setText("" + total_invoice);

                        TextView tv_due = findViewById(R.id.total_due);
                        int due = Integer.parseInt(total_invoice)-Integer.parseInt(total_receive);
                        tv_due.setText("" +due);


                        getAmount("invoices");
                        TextView tvInvoice = (TextView)findViewById(R.id.t_invoice);
                        tvInvoice.setText("Invoice \n"+sum);
                        getAmount("receives");
                        TextView tvReceive = (TextView)findViewById(R.id.t_receive);
                        tvReceive.setText("Received \n"+sum);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Server is down", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void sendData(String url) {

        try {
            JSONArray jo2 = jsonRESULTS.getJSONArray(url);

            if (jo2 != null) {
                String[][] DATA_TO_SHOW = new String[jo2.length()][3];
                for (int i = 0; i < jo2.length(); i++) {
                    JSONObject jsonobject = jo2.getJSONObject(i);
                    DATA_TO_SHOW[i][0] = jsonobject.getString("amount");
                    DATA_TO_SHOW[i][2] = jsonobject.getString("created_at");
                    String note = "N/A";
                    if (jsonobject.getString("note") != null)
                        note = jsonobject.getString("note");
                    DATA_TO_SHOW[i][1] = note;
                }

                Intent i = new Intent(MainActivity.this, Received.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("list", DATA_TO_SHOW);
                i.putExtras(mBundle);
                startActivity(i);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getAmount(String url) {

        sum = 0;
        try {
            JSONArray jo2 = jsonRESULTS.getJSONArray(url);

            if (jo2 != null) {
                //String[][] DATA_TO_SHOW = new String[jo2.length()][3];

                for (int i = 0; i < jo2.length(); i++) {
                    JSONObject jsonobject = jo2.getJSONObject(i);
                    sum = sum +Integer.parseInt(jsonobject.getString("amount"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.ThemeDialogCustom);
        builder.setTitle("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            startActivity(new Intent(MainActivity.this, Profile.class));
        } else if (id == R.id.nav_send) {

            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("logged");
            editor.remove("access_token");
            editor.commit();
            startActivity(new Intent(MainActivity.this, SignIn.class));
        }
        else if (id == R.id.share) {

            Intent intent1 = new Intent();
            intent1.setAction(Intent.ACTION_SEND);
            intent1.setType("text/plain");
            final String text = "Check out "
                    + getResources().getString(R.string.app_name)
                    + ", the free app for rubel group. https://play.google.com/store/apps/details?id="
                    + getPackageName();
            intent1.putExtra(Intent.EXTRA_TEXT, text);
            Intent sender = Intent.createChooser(intent1, "Share " + getResources().getString(R.string.app_name));
            startActivity(sender);
        }
        else if (id == R.id.rate_us) {

            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
