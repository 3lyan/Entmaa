package com.myschool.ibtdi.myschool.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.myschool.ibtdi.myschool.MainApp.MainApp;
import com.myschool.ibtdi.myschool.Network.VolleySingleton;
import com.myschool.ibtdi.myschool.R;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    private static StringBuilder macAddress;

    private EditText etxtEmail;
    private EditText etxtPass;
    private TextView mtxtSignIn;
    private TextView mtxtLogin;
    private TextView mtxtFace;
    private TextView mtxtAccount;
    private TextView mtxtSignup;

    private Intent schoolsAct;
    private RequestQueue mVolleySingletonRequestQueue;

    private SharedPreferences mSharedPreferences;
    private String filename = "school";
    private String currentLang;

    private Intent PassReAct;

    private Intent SignUpAct;

    private static final String EMAIL = "email";
    private CallbackManager callbackManager;
    private RelativeLayout loginButton;
    private ProgressBar mProgressBar;
    private TextView mTextViewProgressBar;
    private ScrollView scrollView;
    private View dialogView;
    private android.app.AlertDialog.Builder dialog;
    private android.app.AlertDialog alert;
    private Typeface tf_regular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);
        currentLang =mSharedPreferences.getString("lang","en");
        chooseLang(currentLang);
        setContentView(R.layout.activity_login);
        startService(new Intent(this, FCMRegistrationService.class));
        getMac();
        definitions();
        txtSet();

    }
    public void facebookLogin(View view)
    {

        callbackManager = CallbackManager.Factory.create();
        loginButton =  findViewById(R.id.login_button);

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile","email"));
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.i("succ","succ");
                        setFacebookData(loginResult);

                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        exception.printStackTrace();
                        Log.i("mshsucc","mshsucc");
                    }
                });

    }
    public static String getMac() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) {
                    continue;
                }

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                macAddress = new StringBuilder();
                for (byte b : macBytes) {
                    macAddress.append(String.format("%02X:", b));

                }

                if (macAddress.length() > 0) {
                    macAddress.deleteCharAt(macAddress.length() - 1);
                }
                Log.i("mac1", "" + macAddress);
                return macAddress.toString();


            }

        } catch (Exception ex) {
        }
        return "02:00:00:00:00:00";
    }
    private void setFacebookData(final LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        try {
                            Log.i("Responseface",response.toString());
                            //JSONObject graphObject=response.getJSONObject().getString("graphObject");
                            String firstName = object.getString("first_name");
                            String lastName = object.getString("last_name");
                            //String gender = object.getString("gender");
                            Profile profile = Profile.getCurrentProfile();
                            //String id = profile.getId();
                            //String link = profile.getLinkUri().toString();
                            String email;
                            try {
                                 email= object.getString("email");
                                Log.i("Login" + "Email", email);
                            }
                            catch (Exception e)
                            {
                                email="";
                            }
                            //Log.i("Link",link);
                            Log.i("Login"+ "FirstName", firstName);
                            Log.i("Login" + "LastName", lastName);
                            //Log.i("Login" + "Gender", gender);
                            onFaceLogin(email,firstName+" "+lastName);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
    private void definitions()
    {
        final VolleySingleton mVolleySingleton = VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        etxtEmail=findViewById(R.id.relativePass);
        etxtPass=findViewById(R.id.etxtPassRe);
        mtxtSignIn=findViewById(R.id.txtSignin);
        mtxtLogin=findViewById(R.id.txtLogin);
        mtxtFace=findViewById(R.id.txtFace);
        mtxtAccount=findViewById(R.id.txtAccount);
        mtxtSignup=findViewById(R.id.txtSignup);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTextViewProgressBar=(TextView)findViewById(R.id.tv_progressBar);
        scrollView=findViewById(R.id.scrollLayout);
        schoolsAct=new Intent(getApplicationContext(),SearchForSchoolActivity.class);
        PassReAct =new Intent(getApplicationContext(),PassRecoveryActivity.class);

        SignUpAct=new Intent(getApplicationContext(),SignUpActivity.class);
    }
    public void onClickForget(View view)
    {
        startActivity(PassReAct);
        overridePendingTransition(R.anim.start_downtoup,R.anim.end_uptodown);

    }
    private void onLogin(final String email, final String pass)
    {
        String url = MainApp.LoginUrl;
        StringRequest stringRequestLogin=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject mJsonObject = new JSONObject(response);
                    if (mJsonObject.has("id")) {

                        Toast.makeText(getApplicationContext(), getString(R.string.loginSuccess), Toast.LENGTH_SHORT).show();
                        String id=mJsonObject.getString("id");
                        Log.i("id",id);
                        mSharedPreferences.edit().putBoolean("Login", true).commit();
                        mSharedPreferences.edit().putString("id",id).commit();
                        //mSharedPreferences.edit().putString("email",email).apply();
                        //mSharedPreferences.edit().putString("pass",pass).apply();

                        schoolsAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        schoolsAct.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        schoolsAct.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        userWelcome(id);
                    }
                    else
                    {
                        mTextViewProgressBar.setVisibility(View.GONE);
                        mProgressBar.setVisibility(View.GONE);
                        scrollView.setVisibility(View.VISIBLE);
                        Toast.makeText(getApplicationContext(), getString(R.string.errorLogin), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramter = new HashMap<String, String>();
                paramter.put("password", pass);
                paramter.put("login", email);
                paramter.put("mac", "" + macAddress);
                paramter.put("token", FCMRegistrationService.token);
                Log.i("token firebase",FCMRegistrationService.token);
                return paramter;
            }
        };
        mVolleySingletonRequestQueue.add(stringRequestLogin);
    }
    public void onClickSign(View view) {
        //SignupAct.putExtra("lang",lang);

        String email=etxtEmail.getText().toString();
        String pass=etxtPass.getText().toString();
        AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);
        view.startAnimation(buttonClick);
        if (email.matches("")) {
            Toast.makeText(getApplicationContext(), getString(R.string.correctEmail), Toast.LENGTH_LONG).show();
        } else if (pass.matches("")) {
            Toast.makeText(getApplicationContext(), getString(R.string.correctPass), Toast.LENGTH_LONG).show();
        } else {
            mTextViewProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
            onLogin(email, pass);
        }
    }

    private void onFaceLogin(final String email, final String name) {
        String url = MainApp.FaceLoginUrl;
        StringRequest stringRequestLogin = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject mJsonObject = new JSONObject(response);
                    String msg = mJsonObject.getString("msg");
                    if(msg.equals("logged in successfully"))
                    {
                        String id=mJsonObject.getString("id");
                        mSharedPreferences.edit().putBoolean("Login", true).commit();
                        mSharedPreferences.edit().putString("id",id).commit();
                        userWelcome(id);
                    }
                    else{
                        SignUpAct.putExtra("email",email);
                        SignUpAct.putExtra("name",name);
                        startActivity(SignUpAct);
                        overridePendingTransition(R.anim.start_downtoup,R.anim.end_uptodown);
                        //Toast.makeText(getApplicationContext(), getString(R.string.errorLogin), Toast.LENGTH_LONG).show();
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
                paramter.put("email", email);
                paramter.put("mac", "" + macAddress);
                paramter.put("token", FCMRegistrationService.token);
                return paramter;
            }
        };
        mVolleySingletonRequestQueue.add(stringRequestLogin);
    }
    private void txtSet()
    {
        etxtEmail.setHint(R.string.emailorPhone);
        etxtPass.setHint(R.string.pass);
        mtxtSignIn.setText(R.string.sign);
        mtxtLogin.setText(R.string.login);
        mtxtFace.setText(R.string.signinFace);
        mtxtAccount.setText(R.string.account);
        mtxtSignup.setText(R.string.signup);


    }
    private void userWelcome(final String id_user)
    {
        String url = MainApp.ProfUrl;
        StringRequest stringRequestRegister = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("reg", response);
                try {
                    JSONObject all = new JSONObject(response);
                    JSONObject userJson=all.getJSONObject("user");
                    String userName=userJson.getString("name");
                    //Toast.makeText(getApplicationContext(),getString(R.string.welcome)+" "+userName,Toast.LENGTH_LONG).show();
                    dialogView = View.inflate(getApplicationContext(), R.layout.dialog_welcome, null);
                    dialog = new android.app.AlertDialog.Builder(LoginActivity.this);
                    dialog.setView(dialogView);
                    dialog.setCancelable(false);
                    alert = dialog.create();
                    alert.setCancelable(false);
                    alert.show();
                    TextView txtMessage=alert.findViewById(R.id.txtComment);
                    TextView txtTitle=alert.findViewById(R.id.txtTitle);
                    tf_regular = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/JF-Flat-medium.ttf");
                    txtMessage.setText(getString(R.string.welcome)+" "+userName);
                    txtMessage.setTypeface(tf_regular);
                    txtTitle.setTypeface(tf_regular);
                    Button btnContinue= alert.findViewById(R.id.btnContinue);

                    btnContinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(schoolsAct);
                            overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                userWelcome(id_user);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> paramter = new HashMap<String, String>();
                paramter.put("id",id_user);
                return paramter;
            }
        };
        mVolleySingletonRequestQueue.add(stringRequestRegister);
    }
    @Override
    public void onBackPressed() {
        startActivity(schoolsAct);
        overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);
    }

    @Override

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    public void onClickSignUp(View view)
    {
        startActivity(SignUpAct);
        overridePendingTransition(R.anim.start_downtoup,R.anim.end_uptodown);
    }


}
