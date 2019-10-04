package com.mintosoft.rubelgroup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mintosoft.rubelgroup.api.BaseApiService;
import com.mintosoft.rubelgroup.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends Activity {

    TextView em, ps, signin,signup,fp;
    String email, password;
    Context mContext;
    //SessionManager session;
    public static final String PREFS_NAME = "LoginPrefs";
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mContext = this;
        mApiService = UtilsApi.getAPIService();
        em = findViewById(R.id.email);
        ps = findViewById(R.id.password);
        fp = findViewById(R.id.forgotpassword);
        signin = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,SignUp.class));
            }
        });
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if (settings.getString("logged", "").toString().equals("logged")) {
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            startActivity(intent);
        }

        fp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignIn.this,ForgotPassword.class));
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = em.getText().toString().trim();
                password = ps.getText().toString().trim();

                mApiService.loginRequest(email, password)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                //JSONObject jsonRESULTS = null;

                                if (response.isSuccessful()) {
                                    Log.d("SignIn.this", "=============> successful");
                                    try {
                                        JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                        String access_token = (String) jsonRESULTS.get("access_token");
                                        JSONObject jo2 = jsonRESULTS.getJSONObject("user");
                                        String name = (String) jo2.get("name");
                                        String email = (String) jo2.get("email");
                                        String type = (String) jo2.get("type");
                                        Log.d("SignIn.this", "=============> " + name + "=============> " + email + "=============> " + type);


                                        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                                        SharedPreferences.Editor editor = settings.edit();
                                        editor.putString("logged", "logged");

                                        editor.putString("access_token", access_token);
                                        editor.commit();
                                        startActivity(new Intent(SignIn.this, MainActivity.class));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
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


//    prefs = getSharedPreferences("logindetail", 0);
//    SharedPreferences.Editor edit = prefs.edit();
//                                edit.clear();
//                                edit.commit();

}
