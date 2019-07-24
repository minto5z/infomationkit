package com.mintosoft.infomationkit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mintosoft.infomationkit.api.BaseApiService;
import com.mintosoft.infomationkit.api.UtilsApi;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    BaseApiService mApiService;
    public static final String PREFS_NAME = "LoginPrefs";
    Call<ResponseBody> call;
    JSONObject jsonRESULTS;
    ImageView profileImage;
    TextView tvName,tvEmail,tvDesignation,tvFatherName,tvMotherName,tvMobileNumber,tvContactNumber,tvNid,tvCompany,tvDistrict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mApiService = UtilsApi.getAPIService();
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, 0);
        final String restoredText = "Bearer " + prefs.getString("access_token", null);

        tvName =findViewById(R.id.name);
        profileImage =findViewById(R.id.profile);
        tvEmail =findViewById(R.id.email);
        tvDesignation =findViewById(R.id.designation);
        tvFatherName =findViewById(R.id.father);
        tvMotherName =findViewById(R.id.mother);
        tvMobileNumber =findViewById(R.id.mobile);
        tvContactNumber =findViewById(R.id.contact);
        tvNid =findViewById(R.id.nid);
        tvCompany =findViewById(R.id.company);
        tvDistrict =findViewById(R.id.location);
        call = mApiService.getLoginResponse(restoredText);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    try {
                        jsonRESULTS = new JSONObject(response.body().string());
                        String name = (String) jsonRESULTS.getString("name");
                        String email = (String) jsonRESULTS.getString("email");
                        String designation = (String) jsonRESULTS.getString("designation");
                        String father_name = (String) jsonRESULTS.getString("father_name");
                        String mother_name = (String) jsonRESULTS.getString("mother_name");
                        String mobile_number = (String) jsonRESULTS.getString("mobile_number");
                        String contact_number = (String) jsonRESULTS.getString("contact_number");
                        String nid = (String) jsonRESULTS.getString("nid");
                        String company_name = (String) jsonRESULTS.getString("company_name");
                        String district_name = (String) jsonRESULTS.getString("district_name");
                        String proImage = (String) jsonRESULTS.getString("image");

                        Picasso.get().load(proImage).into(profileImage);
                        tvName.setText(name);
                        tvEmail.setText(email);
                        tvDesignation.setText(designation);
                        tvFatherName.setText(father_name);
                        tvMotherName.setText(mother_name);
                        tvMobileNumber.setText(mobile_number);
                        tvContactNumber.setText(contact_number);
                        tvCompany.setText(company_name);
                        tvNid.setText(nid);
                        tvDistrict.setText(district_name);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Profile.this, "Server is down", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

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
