package com.myschool.ibtdi.myschool.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.myschool.ibtdi.myschool.MainApp.MainApp;
import com.myschool.ibtdi.myschool.Network.VolleySingleton;
import com.myschool.ibtdi.myschool.R;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignUpActivity extends AppCompatActivity {
    private EditText etxtName;
    private EditText etxtEmail;
    private EditText etxtPass;
    private EditText etxtPhone;
    private TextView mtxtCreate;
    private TextView mtxtHaveAcc;
    private TextView mtxtCreateAcc;
    private TextView mtxtNew;
    private Intent LoginActivity;
    private Intent schoolsAct;
    private RequestQueue mVolleySingletonRequestQueue;

    private SharedPreferences mSharedPreferences;
    private String filename = "school";
    private String currentLang;
    private Bundle extras;
    private String name;
    private String email;
    private ProgressBar mProgressBar;
    private TextView mTextViewProgressBar;
    private ScrollView scrollView;
    private EditText etxtpassRe;

    private void definitions()
    {
        etxtName=findViewById(R.id.relativePass);
        etxtEmail=findViewById(R.id.editCreateMail);
        etxtPass=findViewById(R.id.etxtPassRe);
        etxtpassRe=findViewById(R.id.etxtPass);
        etxtPhone=findViewById(R.id.editPhone);
        mtxtNew=findViewById(R.id.txtNewacc);
        mtxtCreate=findViewById(R.id.txtCreate);
        mtxtHaveAcc=findViewById(R.id.txtHaveAccount);
        mtxtCreateAcc=findViewById(R.id.txtSignin);
        LoginActivity=new Intent(getApplicationContext(),LoginActivity.class);
        schoolsAct= new Intent(getApplicationContext(),SearchForSchoolActivity.class);
        VolleySingleton mVolleySingleton = VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        extras = SignUpActivity.this.getIntent().getExtras();
        name= extras.getString("name","");
        email= extras.getString("email","");
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTextViewProgressBar=(TextView)findViewById(R.id.tv_progressBar);
        scrollView=findViewById(R.id.scrollLayout);


    }
    private void txtHint()
    {
        etxtName.setHint(R.string.name);
        etxtEmail.setHint(R.string.email);
        etxtPass.setHint(R.string.pass);
        etxtpassRe.setHint(getString(R.string.repeat));
        etxtPhone.setHint(R.string.phone);
        mtxtCreate.setText(R.string.create);
        mtxtHaveAcc.setText(R.string.haveAcc);
        mtxtCreateAcc.setText(R.string.sign);
        mtxtNew.setText(R.string.newAcc);
        if(!name.equals(""))
        {
            etxtName.setText(name);
        }
        if (!email.equals(""))
        {
            etxtEmail.setText(email);

        }
    }
    private boolean isNameValid(String name)
    {
        //String expression = "^[a-zA-Z\\s].{2,50}$";
        String expression ="^[\\u0600-\\u065F\\u066A-\\u06EF\\u06FA-\\u06FFa-zA-Z]+[\\u0600-\\u065F\\u066A-\\u06EF\\u06FA-\\u06FFa-zA-Z-_\\.].{2,50}$";
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
        String expression = "^[0-9].{9,20}";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public void onSignClick(View view)
    {
        //AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
        //view.startAnimation(buttonClick);
            AlertDialog.Builder signDialog;
            signDialog= new AlertDialog.Builder(SignUpActivity.this);
            signDialog.setTitle(R.string.error);
            String message="";
            if(!isNameValid(etxtName.getText().toString()))
            {
                message=getString(R.string.correctName);
            }
            if(!isEmailValid(etxtEmail.getText().toString()))
            {
                message+=getString(R.string.correctEmail);
            }
            if(!isPassValid(etxtPass.getText().toString()))
            {
                message+=getString(R.string.correctPass);
            }

            if(!etxtpassRe.getText().toString().equals(etxtPass.getText().toString()))

            {
                message+=getString(R.string.confirmPass);
            }
            if(!isPhoneValid(etxtPhone.getText().toString()))
            {
                message+=getString(R.string.correctPhone);
            }
            if(!message.isEmpty())
            {
                signDialog.setMessage(message);
                signDialog.show();
            }
            else
            {
                mTextViewProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.VISIBLE);
                scrollView.setVisibility(View.GONE);
                onRegister(etxtName.getText().toString(),etxtPass.getText().toString(),etxtPhone.getText().toString(),etxtEmail.getText().toString());
            }


    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);
        currentLang =mSharedPreferences.getString("lang","en");
        chooseLang(currentLang);
        setContentView(R.layout.activity_sign_up);
        definitions();
        txtHint();
    }
    private void onRegister(final String username,  final String pass,final String phone,final String email) {
        String url = MainApp.RegUrl;
        StringRequest stringRequestRegister = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("reg", response);
                try {
                    JSONObject mJsonObject = new JSONObject(response);
                    if (mJsonObject.has("msg")) {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(),getString(R.string.registerSuccess) , Toast.LENGTH_LONG).show();
                        startActivity(LoginActivity);
                        overridePendingTransition(R.anim.start_downtoup,R.anim.end_uptodown);
                    }
                    else {
                        mTextViewProgressBar.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        JSONObject jsonObject=mJsonObject.getJSONObject("errors");
                        Toast.makeText(getApplicationContext(),getString(R.string.errorEmail) , Toast.LENGTH_LONG).show();
                        //String error=jsonObject.getString("email");
                        //Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramter = new HashMap<String, String>();
                paramter.put("name", username);
                paramter.put("password", pass);
                paramter.put("phone", phone);
                paramter.put("email", email);
                return paramter;
            }
        };
        mVolleySingletonRequestQueue.add(stringRequestRegister);
    }
    private void chooseLang(String lang)
    {
        try {
            String language = lang;
            String languageToLoad = language; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
        catch (Exception e)
        {

        }
    }
    public void onSignInClick(View view)
    {
        startActivity(LoginActivity);
        overridePendingTransition(R.anim.start_downtoup,R.anim.end_uptodown);

    }
    @Override
    public void onBackPressed() {
        startActivity(schoolsAct);
        overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);
    }
    @Override

    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));    }

}
