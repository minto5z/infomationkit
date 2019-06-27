package com.mintosoft.infomationkit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class AmitProcessing extends AppCompatActivity {

    public static final String PREFS_NAME = "LoginPrefs";

    BaseApiService mApiService;
    JSONObject jsonRESULTS;
    Call<ResponseBody> call;
    Response<ResponseBody> testresponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amit_processing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mApiService = UtilsApi.getAPIService();
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        final String restoredText = "Bearer " + prefs.getString("access_token", null);
        setData(restoredText);
        setData(restoredText);
        call = mApiService.getLoginResponse(restoredText);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    testresponse = response;
                    try {
                        jsonRESULTS = new JSONObject(testresponse.body().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AmitProcessing.this, "Server is down", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.acardview:
                sendData("amir_p_okalawaiting");
                break;
            case R.id.acardview1:
                sendData("amir_p_okala");
                break;
            case R.id.acardview2:
                sendData("amir_p_visa");
                break;
            case R.id.acardview3:
                sendData("amir_p_manpower");
                break;
            case R.id.acardview4:
                sendData("amir_p_delivery");
                break;
            case R.id.acardview5:
                //sendData("rbl_p_invoice");
                break;
        }
    }
    public void sendData(final String url) {

        try {
            JSONArray jo2 = jsonRESULTS.getJSONArray(url);
            String[][] DATA_TO_SHOW = new String[jo2.length()][6];
            for (int i = 0; i < jo2.length(); i++) {
                JSONObject jsonobject = jo2.getJSONObject(i);
                DATA_TO_SHOW[i][0] = jsonobject.getString("created_at");
                DATA_TO_SHOW[i][1] = jsonobject.getString("worker_number");
                DATA_TO_SHOW[i][2] = jsonobject.getString("passport_name");
                DATA_TO_SHOW[i][3] = jsonobject.getString("passport_number");
                DATA_TO_SHOW[i][4] = jsonobject.getString("visa_number");
                String occupation = "N/A";
                if (jsonobject.getString("occupation") != null)
                    occupation = jsonobject.getString("occupation");
                DATA_TO_SHOW[i][5] = occupation;
            }

            Intent i = new Intent(AmitProcessing.this, WorkerReport.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("list", DATA_TO_SHOW);
            i.putExtras(mBundle);
            startActivity(i);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void setData(String restoredText) {

        Call<ResponseBody> call = mApiService.getLoginResponse(restoredText);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                JSONArray jo2;
                if (response.isSuccessful()) {
                    try {
                        jsonRESULTS = new JSONObject(response.body().string());

                        //------------------------------------Amir Processing--------------------------------------------------

                        jo2 = jsonRESULTS.getJSONArray("amir_p_okala");
                        TextView atextview2 = findViewById(R.id.atextview2);
                        atextview2.setText(jo2.length()+"");

                        jo2 = jsonRESULTS.getJSONArray("amir_p_okalawaiting");
                        TextView atextview1 = findViewById(R.id.atextview1);
                        atextview1.setText(jo2.length() + "");

                        jo2 = jsonRESULTS.getJSONArray("amir_p_visa");
                        TextView atextview3 = findViewById(R.id.atextview3);
                        atextview3.setText(jo2.length()+"");

                        jo2 = jsonRESULTS.getJSONArray("amir_p_manpower");
                        TextView atextview4 = findViewById(R.id.atextview4);
                        atextview4.setText(jo2.length()+"");

                        jo2 = jsonRESULTS.getJSONArray("amir_p_delivery");
                        TextView atextview5 = findViewById(R.id.atextview5);
                        atextview5.setText(jo2.length()+"");

                        jo2 = jsonRESULTS.getJSONArray("amir_p_invoice");
                        TextView atextview6 = findViewById(R.id.atextview6);
                        atextview6.setText(jo2.length()+"");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(AmitProcessing.this, "Server is down", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
