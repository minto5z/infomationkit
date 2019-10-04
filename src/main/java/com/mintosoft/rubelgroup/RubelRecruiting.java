package com.mintosoft.rubelgroup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class RubelRecruiting extends AppCompatActivity {

    public static final String PREFS_NAME = "LoginPrefs";

    BaseApiService mApiService;
    JSONObject jsonRESULTS;
    Call<ResponseBody> call;
    Response<ResponseBody> testresponse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubel_recruiting);
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
                    Toast.makeText(RubelRecruiting.this, "Server is down", Toast.LENGTH_SHORT).show();
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
                sendData("rbl_r_okalawaiting");
                break;
            case R.id.acardview1:
                sendData("rbl_r_okala");
                break;
            case R.id.acardview2:
                sendData("rbl_r_visa");
                break;
            case R.id.acardview3:
                sendData("rbl_r_manpower");
                break;
            case R.id.acardview4:
                sendData("rbl_r_delivery");
                break;
            case R.id.acardview5:
                sendData("rbl_r_completed");
                break;
        }
    }
    public void sendData(final String url) {

        try {
            JSONArray jo2 = jsonRESULTS.getJSONArray(url);
            String[][] DATA_TO_SHOW = new String[jo2.length()][2];
            String[][] DATA_TO_SHOW1 = new String[jo2.length()][30];
            for (int i = 0; i < jo2.length(); i++) {
                JSONObject jsonobject = jo2.getJSONObject(i);
                //DATA_TO_SHOW[i][0] = jsonobject.getString("created_at");
                DATA_TO_SHOW[i][0] = jsonobject.getString("worker_number");
                DATA_TO_SHOW[i][1] = jsonobject.getString("passport_name");
//                DATA_TO_SHOW[i][3] = jsonobject.getString("passport_number");
//                DATA_TO_SHOW[i][4] = jsonobject.getString("visa_number");
//                String occupation = "N/A";
//                if (jsonobject.getString("occupation") != null)
//                    occupation = jsonobject.getString("occupation");
//                DATA_TO_SHOW[i][5] = occupation;


                DATA_TO_SHOW1[i][2] = jsonobject.getString("worker_number");
                DATA_TO_SHOW1[i][1] = jsonobject.getString("passport_number");
                DATA_TO_SHOW1[i][0] = jsonobject.getString("passport_name");
                DATA_TO_SHOW1[i][3] = jsonobject.getString("processing_rate");
                DATA_TO_SHOW1[i][4] = jsonobject.getString("visa_number");

                DATA_TO_SHOW1[i][5] = jsonobject.getString("company_id_number");
                DATA_TO_SHOW1[i][6] = jsonobject.getString("occupation");

                String driving_licenec = "No";
                if (jsonobject.getString("diving_licence_status") != null)
                    driving_licenec = jsonobject.getString("diving_licence_status");
                DATA_TO_SHOW1[i][7] = driving_licenec;

                String police_clearence = "No";
                if (jsonobject.getString("police_clearence_status").equals("1"))
                    police_clearence = "Yes";
                DATA_TO_SHOW1[i][8] = police_clearence;

                String medical_status = "No";
                if (jsonobject.getString("medical_status").equals("1"))
                    medical_status = "Yes";
                DATA_TO_SHOW1[i][9] = medical_status;

                String musaned_status = "No";
                if (jsonobject.getString("musaned_status").equals("1"))
                    musaned_status = "Yes";
                DATA_TO_SHOW1[i][10] = musaned_status;

                String musaned_date = " ";
                if (jsonobject.getString("musaned_date") != null)
                    musaned_date = jsonobject.getString("musaned_date");
                DATA_TO_SHOW1[i][11] = musaned_date;

                String okala_status = "No";
                if (jsonobject.getString("okala_status").equals("1"))
                    okala_status = "Yes";
                DATA_TO_SHOW1[i][12] = okala_status;

                String okala_date = " ";
                if (jsonobject.getString("okala_date")!=null)
                    okala_date = jsonobject.getString("okala_date");
                DATA_TO_SHOW1[i][13] = okala_date;

                String mofa_status = "No";
                if (jsonobject.getString("mofa_status").equals("1"))
                    mofa_status = "Yes";
                DATA_TO_SHOW1[i][14] = mofa_status;

                String mofa_date = " ";
                if (jsonobject.getString("mofa_date")!=null)
                    mofa_date = jsonobject.getString("mofa_date");
                DATA_TO_SHOW1[i][15] = mofa_date;

                String stamping_status = "No";
                if (jsonobject.getString("stamping_status").equals("1"))
                    stamping_status = "Yes";
                DATA_TO_SHOW1[i][16] = stamping_status;

                String stamping_date = " ";
                if (jsonobject.getString("stamping_date")!=null)
                    stamping_date = jsonobject.getString("stamping_date");
                DATA_TO_SHOW1[i][17] = stamping_date;

                String finger_training = "No";
                if (jsonobject.getString("finger_training_status").equals("1"))
                    finger_training = "Yes";
                DATA_TO_SHOW1[i][18] = finger_training;

                String finger_training_date = " ";
                if (jsonobject.getString("finger_training_date")!=null)
                    finger_training_date = jsonobject.getString("finger_training_date");
                DATA_TO_SHOW1[i][19] = finger_training_date;

                String training = "No";
                if (jsonobject.getString("training").equals("1"))
                    training = "Yes";
                DATA_TO_SHOW1[i][20] = training;

                String training_date = " ";
                if (jsonobject.getString("training_date")!=null)
                    training_date = jsonobject.getString("training_date");
                DATA_TO_SHOW1[i][21] = training_date;

                String manpower_status = "No";
                if (jsonobject.getString("manpower_status").equals("1"))
                    manpower_status = "Yes";
                DATA_TO_SHOW1[i][22] = manpower_status;

                String manpowe_date = " ";
                if (jsonobject.getString("manpowe_date")!=null)
                    manpowe_date = jsonobject.getString("manpowe_date");
                DATA_TO_SHOW1[i][23] = manpowe_date;

                String flight_status = "No";
                if (jsonobject.getString("flight_status").equals("1"))
                    flight_status = "Yes";
                DATA_TO_SHOW1[i][24] = flight_status;

                String flight_date = " ";
                if (jsonobject.getString("flight_date")!=null)
                    flight_date = jsonobject.getString("flight_date");
                DATA_TO_SHOW1[i][25] = flight_date;

                String completed = "No";
                if (jsonobject.getString("completed").equals("1"))
                    completed = "Yes";
                DATA_TO_SHOW1[i][26] = completed;

                String completed_date = " ";
                if (jsonobject.getString("completed_date")!=null)
                    completed_date = jsonobject.getString("completed_date");
                DATA_TO_SHOW1[i][27] = completed_date;

                String account_status = "No";
                if (jsonobject.getString("account_status").equals("1"))
                    account_status = "Yes";
                DATA_TO_SHOW1[i][28] = account_status;

                String account_date = " ";
                if (jsonobject.getString("account_date")!=null)
                    account_date = jsonobject.getString("account_date");
                DATA_TO_SHOW1[i][29] = account_date;
            }

            Intent i = new Intent(RubelRecruiting.this, WorkerReport.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable("list", DATA_TO_SHOW);
            mBundle.putSerializable("list1", DATA_TO_SHOW1);
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

                        //------------------------------------rbl Processing--------------------------------------------------

                        jo2 = jsonRESULTS.getJSONArray("rbl_r_okala");
                        TextView atextview2 = findViewById(R.id.atextview2);
                        atextview2.setText(jo2.length()+"");

                        jo2 = jsonRESULTS.getJSONArray("rbl_r_okalawaiting");
                        TextView atextview1 = findViewById(R.id.atextview1);
                        atextview1.setText(jo2.length() + "");

                        jo2 = jsonRESULTS.getJSONArray("rbl_r_visa");
                        TextView atextview3 = findViewById(R.id.atextview3);
                        atextview3.setText(jo2.length()+"");

                        jo2 = jsonRESULTS.getJSONArray("rbl_r_manpower");
                        TextView atextview4 = findViewById(R.id.atextview4);
                        atextview4.setText(jo2.length()+"");

                        jo2 = jsonRESULTS.getJSONArray("rbl_r_delivery");
                        TextView atextview5 = findViewById(R.id.atextview5);
                        atextview5.setText(jo2.length()+"");

                        jo2 = jsonRESULTS.getJSONArray("rbl_r_completed");
                        TextView atextview6 = findViewById(R.id.atextview6);
                        atextview6.setText(jo2.length()+"");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(RubelRecruiting.this, "Server is down", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

}
