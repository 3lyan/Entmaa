package com.m3lyan.entmaa.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.m3lyan.entmaa.MainApp.MainApp;
import com.m3lyan.entmaa.Model.SignInModel;
import com.m3lyan.entmaa.Network.APIService;
import com.m3lyan.entmaa.Network.ApiUtils;
import com.m3lyan.entmaa.R;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private Locale currentLocale;

    private SharedPreferences mSharedPreferences;
    private String filename = "entmaa";
    private String lang;

    private EditText etxtUser;
    private EditText etxtPass;

    private ImageButton btnSignIn;

    private String userName;
    private String pass;

    private Intent homeAct;
    private Intent signUpAct;
    private TextView txtSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);
        lang=mSharedPreferences.getString("lang","ar");
        homeAct=new Intent(getApplicationContext(),HomeActivity.class);
        if (mSharedPreferences.getBoolean("login",false))
        {
            startActivity(homeAct);
        }
        setTheme(R.style.AppTheme_NoActionAndStatusBar);
        chooseLang(lang);
        setContentView(R.layout.activity_sign_in);
        definition();
    }
    private void definition()
    {
        currentLocale=getResources().getConfiguration().locale;
        btnSignIn=findViewById(R.id.btnSignIn);
        etxtUser=findViewById(R.id.etxtUser);
        etxtPass=findViewById(R.id.etxtPass);


        txtSignUp=findViewById(R.id.txtSignUp);
        signUpAct=new Intent(getApplicationContext(),SignUpActivity.class);
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(signUpAct);
            }
        });

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        chooseLang(lang);
    }
    public void chooseLang(String language)
    {
        String languageToLoad=language;
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
    public void OnClickSignIn(View view)
    {
        if (etxtUser.getText().toString().matches("")) {
            etxtUser.setError(getResources().getString(R.string.correctName));
        } else if (etxtPass.getText().toString().matches("")) {
            etxtPass.setError(getResources().getString(R.string.correctName));
        } else {
            userName=etxtUser.getText().toString();
            pass=etxtPass.getText().toString();
            onLogin(lang,userName, pass);
        }
    }
    private void onLogin(String language,String user,String password)
    {
        MainApp.apiService.signInWrite(language,user,password).enqueue(new Callback<SignInModel>() {
            @Override
            public void onResponse(Call<SignInModel> call, Response<SignInModel> response) {
                if (response.body().getValue())
                {
                    mSharedPreferences.edit().putBoolean("login", true).commit();
                    mSharedPreferences.edit().putString("id",response.body().getData().getId()).commit();
                    Log.i("id",response.body().getData().getId());
                    startActivity(homeAct);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SignInModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
