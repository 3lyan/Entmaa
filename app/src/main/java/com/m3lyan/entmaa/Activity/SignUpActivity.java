package com.m3lyan.entmaa.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.m3lyan.entmaa.MainApp.MainApp;
import com.m3lyan.entmaa.Model.SignUpModel;
import com.m3lyan.entmaa.Network.APIService;
import com.m3lyan.entmaa.Network.ApiUtils;
import com.m3lyan.entmaa.R;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private Locale currentLocale;

    private EditText etxtUser;
    private EditText etxtPhone;
    private EditText etxtName;
    private EditText etxtEmail;
    private EditText etxtCompany;
    private EditText etxtPass;
    private EditText etxtRePass;

    private TextView txtSignIn;

    private String user;
    private String phone;
    private String nickName;
    private String email;
    private String comp;
    private String pass;
    private String passRe;

    private SharedPreferences mSharedPreferences;
    private String filename = "entmaa";
    public String lang;

    private Intent signInAct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);
        lang=mSharedPreferences.getString("lang","ar");
        chooseLang(lang);
        setContentView(R.layout.activity_sign_up);
        definition();
    }
    private void definition()
    {
        currentLocale=getResources().getConfiguration().locale;
        etxtUser=findViewById(R.id.etxtUser);
        etxtPhone=findViewById(R.id.etxtPhone);
        etxtName=findViewById(R.id.etxtNick);
        etxtEmail=findViewById(R.id.etxtEmail);
        etxtCompany=findViewById(R.id.etxtCompany);
        etxtPass=findViewById(R.id.etxtPass);
        etxtRePass=findViewById(R.id.etxtPassConfirm);
        txtSignIn=findViewById(R.id.txtSignIn);
        signInAct=new Intent(getApplicationContext(),SignInActivity.class);
        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(signInAct);
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        chooseLang(currentLocale.toString());
    }
    private boolean isNameValid(String name)
    {
        String expression ="^[\\u0600-\\u065F\\u066A-\\u06EF\\u06FA-\\u06FFa-zA-Z0-9]+[\\u0600-\\u065F\\u066A-\\u06EF\\u06FA-\\u06FFa-zA-Z-_\\.].{0,50}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    private boolean isEmailValid(String email) {
        //String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        String expression = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{2,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{2,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{1,25}" +
                ")+";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    private boolean isPassValid(String pass)
    {
        String expression = "^[a-zA-Z0-9\\+\\.\\_\\%\\-\\+].{5,30}";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }
    private boolean isPhoneValid(String phone)
    {
        String expression = "^[0-9].{3,20}";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public void onSignClick(View view)
    {
        if(!isNameValid(etxtUser.getText().toString()))
        {
            etxtUser.setError(getResources().getText(R.string.correctName));
        }
        else if(!isPhoneValid(etxtPhone.getText().toString()))
        {
            etxtPhone.setError(getResources().getText(R.string.correctPhone));;
        }
        else if(!isEmailValid(etxtEmail.getText().toString()))
        {
            etxtEmail.setError(getResources().getText(R.string.correctEmail));
        }
        else if (!isNameValid(etxtName.getText().toString()))
        {
            etxtName.setError(getResources().getString(R.string.correctName));
        }
        else if(!isNameValid(etxtCompany.getText().toString()))
        {
            etxtCompany.setError(getResources().getString(R.string.correctComp));
        }
        else if(!isPassValid(etxtPass.getText().toString()))
        {
            etxtPass.setError(getResources().getText(R.string.correctPass));
        }

        else if(!etxtRePass.getText().toString().equals(etxtPass.getText().toString()))
        {
            etxtRePass.setError(getResources().getText(R.string.confirmPassword));
        }
        else
        {
            user=etxtUser.getText().toString();
            phone=etxtPhone.getText().toString();
            email=etxtEmail.getText().toString();
            nickName=etxtName.getText().toString();
            comp= etxtCompany.getText().toString();
            pass=etxtPass.getText().toString();
            passRe=etxtRePass.getText().toString();

            Log.d("language",lang);
            onClickData(lang,user,pass,phone,email,nickName,comp);
        }


    }
    private void onClickData(String language,String userName,String password,String mob,String email,String nick,String company)
    {
        MainApp.apiService.signUpWrite(language,userName, password, mob, email, nick, company).enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                if (response.body().getValue())
                {
                    startActivity(signInAct);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),response.body().getMsg(),Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {

            }
        });
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
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
