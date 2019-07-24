package com.mintosoft.infomationkit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mintosoft.infomationkit.api.BaseApiService;
import com.mintosoft.infomationkit.api.UtilsApi;

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
                        tv_total_receive.setText("Total Paid  : " + total_receive);

                        String total_invoice = jsonRESULTS.getString("total_invoice");
                        TextView tv_total_invoice = findViewById(R.id.total_invoice);
                        tv_total_invoice.setText("Total Invoice  : " + total_invoice);

                        TextView tv_due = findViewById(R.id.total_due);
                        int due = Integer.parseInt(total_invoice)-Integer.parseInt(total_receive);
                        tv_due.setText("Total Due : " +due);
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

//    private void setData(String restoredText) {
//
//        Call<ResponseBody> call = mApiService.getLoginResponse(restoredText);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//
//                JSONArray jo2;
//                if (response.isSuccessful()) {
//                    try {
//                        jsonRESULTS = new JSONObject(response.body().string());
//
//                        //------------------------------------ruble processing--------------------------------------------------
//
//                        jo2 = jsonRESULTS.getJSONArray("rbl_p_okala");
//                        TextView rtextview2 = findViewById(R.id.rtextview2);
//                        rtextview2.setText(jo2.length() + "\n" + "Okala");
//
//                        jo2 = jsonRESULTS.getJSONArray("rbl_p_okalawaiting");
//                        TextView rtextview1 = findViewById(R.id.rtextview1);
//                        rtextview1.setText(jo2.length() + "\n" + "Waiting");
//
//                        jo2 = jsonRESULTS.getJSONArray("rbl_p_visa");
//                        TextView rtextview3 = findViewById(R.id.rtextview3);
//                        rtextview3.setText(jo2.length() + "\n" + "Visa");
//
//                        jo2 = jsonRESULTS.getJSONArray("rbl_p_manpower");
//                        TextView rtextview4 = findViewById(R.id.rtextview4);
//                        rtextview4.setText(jo2.length() + "\n" + "Man Power");
//
//                        jo2 = jsonRESULTS.getJSONArray("rbl_p_delivery");
//                        TextView rtextview5 = findViewById(R.id.rtextview5);
//                        rtextview5.setText(jo2.length() + "\n" + "Dileverd");
//
//                        jo2 = jsonRESULTS.getJSONArray("rbl_p_invoice");
//                        TextView rtextview6 = findViewById(R.id.rtextview6);
//                        rtextview6.setText(jo2.length() + "\n" + "Invoice");
//
//
//                        //------------------------------------Ruble Recuiting--------------------------------------------------
//
//                        jo2 = jsonRESULTS.getJSONArray("rbl_r_okala");
//                        TextView rrtextview2 = findViewById(R.id.rrtextview2);
//                        rrtextview2.setText(jo2.length() + "\n" + "Okala");
//
//                        jo2 = jsonRESULTS.getJSONArray("rbl_r_okalawaiting");
//                        TextView rrtextview1 = findViewById(R.id.rrtextview1);
//                        rrtextview1.setText(jo2.length() + "\n" + "Waiting");
//
//                        jo2 = jsonRESULTS.getJSONArray("rbl_r_visa");
//                        TextView rrtextview3 = findViewById(R.id.rrtextview3);
//                        rrtextview3.setText(jo2.length() + "\n" + "Visa");
//
//                        jo2 = jsonRESULTS.getJSONArray("rbl_r_manpower");
//                        TextView rrtextview4 = findViewById(R.id.rrtextview4);
//                        rrtextview4.setText(jo2.length() + "\n" + "Man Power");
//
//                        jo2 = jsonRESULTS.getJSONArray("rbl_r_delivery");
//                        TextView rrtextview5 = findViewById(R.id.rrtextview5);
//                        rrtextview5.setText(jo2.length() + "\n" + "Dileverd");
//
//                        jo2 = jsonRESULTS.getJSONArray("rbl_r_invoice");
//                        TextView rrtextview6 = findViewById(R.id.rrtextview6);
//                        rrtextview6.setText(jo2.length() + "\n" + "Invoice");
//
//
//                        //------------------------------------Amir Processing--------------------------------------------------
//
//                        jo2 = jsonRESULTS.getJSONArray("amir_p_okala");
//                        TextView atextview2 = findViewById(R.id.atextview2);
//                        atextview2.setText(jo2.length() + "\n" + "Okala");
//
//                        jo2 = jsonRESULTS.getJSONArray("amir_p_okalawaiting");
//                        TextView atextview1 = findViewById(R.id.atextview1);
//                        atextview1.setText(jo2.length() + "\n" + "Waiting");
//
//                        jo2 = jsonRESULTS.getJSONArray("amir_p_visa");
//                        TextView atextview3 = findViewById(R.id.atextview3);
//                        atextview3.setText(jo2.length() + "\n" + "Visa");
//
//                        jo2 = jsonRESULTS.getJSONArray("amir_p_manpower");
//                        TextView atextview4 = findViewById(R.id.atextview4);
//                        atextview4.setText(jo2.length() + "\n" + "Man Power");
//
//                        jo2 = jsonRESULTS.getJSONArray("amir_p_delivery");
//                        TextView atextview5 = findViewById(R.id.atextview5);
//                        atextview5.setText(jo2.length() + "\n" + "Dileverd");
//
//                        jo2 = jsonRESULTS.getJSONArray("amir_p_invoice");
//                        TextView atextview6 = findViewById(R.id.atextview6);
//                        atextview6.setText(jo2.length() + "\n" + "Invoice");
//
//                        //------------------------------------Amir Recuiting--------------------------------------------------
//
//                        jo2 = jsonRESULTS.getJSONArray("amir_r_okala");
//                        TextView artextview2 = findViewById(R.id.artextview2);
//                        artextview2.setText(jo2.length() + "\n" + "Okala");
//
//                        jo2 = jsonRESULTS.getJSONArray("amir_r_okalawaiting");
//                        TextView artextview1 = findViewById(R.id.artextview1);
//                        artextview1.setText(jo2.length() + "\n" + "Waiting");
//
//                        jo2 = jsonRESULTS.getJSONArray("amir_r_visa");
//                        TextView artextview3 = findViewById(R.id.artextview3);
//                        artextview3.setText(jo2.length() + "\n" + "Visa");
//
//                        jo2 = jsonRESULTS.getJSONArray("amir_r_manpower");
//                        TextView artextview4 = findViewById(R.id.artextview4);
//                        artextview4.setText(jo2.length() + "\n" + "Man Power");
//
//                        jo2 = jsonRESULTS.getJSONArray("amir_r_delivery");
//                        TextView artextview5 = findViewById(R.id.artextview5);
//                        artextview5.setText(jo2.length() + "\n" + "Dileverd");
//
//                        jo2 = jsonRESULTS.getJSONArray("amir_r_invoice");
//                        TextView artextview6 = findViewById(R.id.artextview6);
//                        artextview6.setText(jo2.length() + "\n" + "Invoice");
//
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Server is down", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
