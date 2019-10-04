package com.mintosoft.rubelgroup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class ForgotPassword extends AppCompatActivity {

    TextView continue_pass, signin;
    EditText et_email;
    String email;
    BaseApiService mApiService;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        signin = findViewById(R.id.signin);
        continue_pass = findViewById(R.id.continue_pass);
        et_email = findViewById(R.id.email);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPassword.this, SignIn.class));
            }
        });
        continue_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = et_email.getText().toString().trim();
                mApiService.forgotpassword(email)
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    String[] strArray = new String[]{email};
                                    if (email.charAt(0) == '0' && email.charAt(1) == '1') {
                                        Log.d("SignIn.this", "============1111111111111111111111=> successful");
                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://demo.rubelgroup.com.bd/code-submit?mobile=" + email));
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Log.d("SignIn.this", "=============> successful");
                                        Toast.makeText(mContext, "Check mail to follow instruction", Toast.LENGTH_SHORT).show();
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
}
