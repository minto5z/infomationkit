package com.mintosoft.rubelgroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mintosoft.rubelgroup.api.BaseApiService;
import com.mintosoft.rubelgroup.api.UtilsApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    TextView nm, em, ps, rps, signup, signin;
    String email, password, reapetpassword, name;
    Context mContext;
    //SessionManager session;
    public static final String PREFS_NAME = "LoginPrefs";
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mContext = this;
        mApiService = UtilsApi.getAPIService();
        nm = findViewById(R.id.name);
        em = findViewById(R.id.email);
        ps = findViewById(R.id.password);
        rps = findViewById(R.id.rpassword);
        signin = findViewById(R.id.signin);
        signup = findViewById(R.id.signup);
//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
//        if (settings.getString("logged", "").toString().equals("logged")) {
//            Intent intent = new Intent(SignUp.this, SignIn.class);
//            startActivity(intent);
//        }
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nm.getText().toString().trim();
                email = em.getText().toString().trim();
                password = ps.getText().toString().trim();
                reapetpassword = rps.getText().toString().trim();

                mApiService.registerRequest(name, email, password)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                //JSONObject jsonRESULTS = null;

                                if (response.isSuccessful()) {
                                    Log.d("SignUp.this", "=============> successful");
                                    startActivity(new Intent(SignUp.this, SignIn.class));
                                } else {
                                    Toast.makeText(mContext, "Please enter your valid input", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("debug", "onFailure: ERROR > " + t.toString());
                            }
                        });
            }
        });
    }
}
