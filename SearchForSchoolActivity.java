package com.myschool.ibtdi.myschool.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.myschool.ibtdi.myschool.Adapter.CustomSpinnerAdapter;
import com.myschool.ibtdi.myschool.MainApp.MainApp;
import com.myschool.ibtdi.myschool.Network.VolleySingleton;
import com.myschool.ibtdi.myschool.R;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SearchForSchoolActivity extends AppCompatActivity {

    private LinearLayout searchLay;
    private LinearLayout mapLay;
    private LinearLayout messageLay;
    private LinearLayout reportLay;
    private DrawerLayout mDrawerLayout;
    private TabLayout tab;
    private TextView mtxtsearcBtn;
    private ImageView imgSearch;
    private TextView mtxtSearch;
    private ImageView imgMap;
    private TextView mtxtMap;
    private ImageView imgMessage;
    private TextView mtxtMessage;
    private ImageView imgReport;
    private TextView mtxtReport;
    private TextView mtxtLook;
    private Spinner mspinSys;
    private Spinner mspinLvl;
    private Spinner mspinState;
    private Spinner mspinCity;
    private Spinner mspinLocal;
    private ArrayList<String> listSys;
    private CustomSpinnerAdapter spinAdapterSys;
    private ArrayList<String> listLvl;
    private CustomSpinnerAdapter spinAdapterLvl;
    private ArrayList<String> listState;
    private CustomSpinnerAdapter spinAdapterState;
    private ArrayList<String> listCity;
    private CustomSpinnerAdapter spinAdapterCity;
    private ArrayList<String> listLocal;
    private CustomSpinnerAdapter spinAdapterLocal;
    private Intent messagesAct;
    private Intent reportsAct;
    private Intent schoolsAct;
    private Intent languageAct;
    private Intent mapAct;
    private Intent signAct;
    private AlphaAnimation buttonClick;
    private NavigationView navigationView;
    private Intent configAct;
    private Intent callAct;
    private Intent aboutAct;
    private ImageView btnMenu;

    private String urlSearch;

    private RequestQueue mVolleySingletonRequestQueue;

    private String typesNames;
    private ArrayList<Integer> stateIntList;
    private String urlCity;

    private ArrayList<Integer> cityIntList;
    private String urlLocal;

    private ArrayList<Integer> localIntList;

    private String urlSearchName;

    //private EditText eSearchSchool;
    //private String searchSchool;

    private SharedPreferences mSharedPreferences;
    private String filename = "school";
    private String currentLang;

    private ImageView sidebarExit;
    private TextView sideTxtExit;

    private String user_id;
    private boolean mLogin;

    private String localId;
    private ProgressBar mProgressBar;
    private TextView mTextViewProgressBar;

    private ScrollView scrollView;
    private Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keyHash();
        mSharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);
        currentLang =mSharedPreferences.getString("lang","en");
        chooseLang(currentLang);
        setContentView(R.layout.activity_search_for_school);
        definitions();
        mtxtLook.setText(R.string.look);
        mtxtsearcBtn.setText(R.string.search);
        tabSet();
        setSpinnertxt();
        tabSelect();
        spinnerGet();
    }
    private void spinnerGet()
    {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, urlSearch + currentLang + "/", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mTextViewProgressBar=(TextView)findViewById(R.id.tv_progressBar);
                    mProgressBar.setVisibility(View.GONE);
                    mTextViewProgressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);

                    JSONObject all=new JSONObject(response.toString());
                    String js=all.getString("types");
                    JSONArray types=new JSONArray(js);
                    Log.i("types",types.toString());
                    listSys.clear();
                    for (int i=0;i<types.length();i++) {
                        JSONObject typesPart=types.getJSONObject(i);
                        String typesName = typesPart.getString(typesNames);
                        listSys.add(typesName);
                    }
                    listSys.add(getString(R.string.education));
                    mspinSys.setAdapter(spinAdapterSys);
                    mspinSys.setSelection(spinAdapterSys.getCount());
                    mspinSys.setEnabled(true);

                    String stages=all.getString("stages");
                    JSONArray stageArr=new JSONArray(stages);
                    listLvl.clear();
                    for (int i=0;i<stageArr.length();i++) {
                        JSONObject typesPart=stageArr.getJSONObject(i);
                        String stageName = typesPart.getString(typesNames);
                        listLvl.add(stageName);
                    }
                    listLvl.add(getString(R.string.eduLvl));
                    mspinLvl.setAdapter(spinAdapterLvl);
                    mspinLvl.setSelection(spinAdapterLvl.getCount());
                    mspinLvl.setEnabled(true);
                    String states=all.getString("states");
                    JSONArray stateArr=new JSONArray(states);
                    listState.clear();
                    listState.add(getString(R.string.all));
                    stateIntList.add(0);

                    for (int i=0;i<stateArr.length();i++) {
                        JSONObject statePart=stateArr.getJSONObject(i);
                        String stateName = statePart.getString(typesNames);
                        Integer stateId=statePart.getInt("id");
                        stateIntList.add(stateId);
                        listState.add(stateName);
                    }

                    listState.add(getString(R.string.state));
                    mspinState.setAdapter(spinAdapterState);
                    mspinState.setSelection(spinAdapterState.getCount());
                    mspinState.setEnabled(true);
                    mspinState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            try {
                                listLocal.clear();
                                listLocal.add(" ");
                                listLocal.add(getString(R.string.local));
                                mspinLocal.setAdapter(spinAdapterLocal);
                                mspinLocal.setSelection(spinAdapterLocal.getCount());
                                int stateNum=stateIntList.get(mspinState.getSelectedItemPosition());
                                //spinnerCity(stateNum,currentLang);
                            }
                            catch (Exception e)
                            {

                            }

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spinnerGet();
            }
        });
        mVolleySingletonRequestQueue.add(jsonObjectRequest);
    }
    /*private void spinnerCity(final int id, final String lang)
    {
        mProgressBar.setVisibility(View.VISIBLE);
        mTextViewProgressBar.setVisibility(View.VISIBLE);
        //scrollView.setVisibility(View.GONE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlCity + id+"/" + lang, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mProgressBar.setVisibility(View.GONE);
                    mTextViewProgressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    listCity.clear();
                    cityIntList.clear();
                    JSONObject all = new JSONObject(response.toString());
                    String js = all.getString("cities");
                    JSONArray cityArr = new JSONArray(js);
                    Log.i("cities", cityArr.toString());
                    for (int i = 0; i < cityArr.length(); i++) {
                        JSONObject cityPart = cityArr.getJSONObject(i);
                        String cityName = cityPart.getString(typesNames);
                        Integer cityId=cityPart.getInt("id");
                        cityIntList.add(cityId);
                        listCity.add(cityName);
                    }
                    listCity.add(getString(R.string.city));
                    mspinCity.setAdapter(spinAdapterCity);
                    mspinCity.setSelection(spinAdapterCity.getCount());
                    mspinCity.setEnabled(true);
                    mspinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            try
                            {
                                int CityNum=cityIntList.get(mspinCity.getSelectedItemPosition());
                                spinnerLocal(CityNum,currentLang);
                            }
                            catch (Exception e)
                            {

                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
                    catch(JSONException e)
                    {

                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spinnerCity(id,lang);
            }
        });
        mVolleySingletonRequestQueue.add(jsonObjectRequest);

    }
    private void spinnerLocal(final int id, final String lang)
    {
        mProgressBar.setVisibility(View.VISIBLE);
        mTextViewProgressBar.setVisibility(View.VISIBLE);
        //scrollView.setVisibility(View.GONE);
        Log.i("link",urlLocal + id + "/" + lang);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, urlLocal + id + "/" + lang, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    mProgressBar.setVisibility(View.GONE);
                    mTextViewProgressBar.setVisibility(View.GONE);
                    scrollView.setVisibility(View.VISIBLE);
                    listLocal.clear();
                    JSONObject all = new JSONObject(response.toString());
                    String js = all.getString("locals");
                    JSONArray localArr = new JSONArray(js);
                    Log.i("locals", localArr.toString());
                    for (int i = 0; i < localArr.length(); i++) {
                        JSONObject localPart = localArr.getJSONObject(i);
                        String localName = localPart.getString(typesNames);
                        listLocal.add(localName);
                        Integer localId=localPart.getInt("id");
                        localIntList.add(localId);
                    }
                    if(listLocal.isEmpty())
                    {
                        //listLocal.add("");
                        listLocal.add(" ");
                        listLocal.add(getResources().getString(R.string.local));
                        mspinLocal.setAdapter(spinAdapterLocal);
                        mspinLocal.setSelection(spinAdapterLocal.getCount());
                        mspinLocal.setEnabled(false);
                    }
                    else
                    {
                        listLocal.add(getString(R.string.local));
                        mspinLocal.setAdapter(spinAdapterLocal);
                        mspinLocal.setSelection(spinAdapterLocal.getCount());
                        mspinLocal.setEnabled(true);
                    }


                }
                catch (Exception e)
                {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                spinnerLocal(id,lang);
            }
        });
        mVolleySingletonRequestQueue.add(jsonObjectRequest);
    }*/

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
    public void onClickAbout(View view) {
        startActivity(aboutAct);
        overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);
    }
    public void onClickCallus(View view)
    {
        startActivity(callAct);
        overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);
    }
    public void onClickConfig (View view) {
        if(mLogin)
        {
            startActivity(configAct);
            overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);
            //overridePendingTransition(R.anim.start_downtoup,R.anim.end_uptodown);
        }
        else
        {
            Toast.makeText(getApplicationContext(),getString(R.string.youmustLogintosee),Toast.LENGTH_SHORT).show();
            startActivity(signAct);
            overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);


        }
    }
    public void onClickBack (View view)
    {if(mLogin)
    {
        finishAffinity();
    }
    else
    {
        mSharedPreferences.edit().clear().commit();
        startActivity(languageAct);
        overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);
    }}
    public void onClickMenu (View view)
    {
        mDrawerLayout.openDrawer(Gravity.RIGHT);

    }
    public void onClickSearch(View view)
    {
        schoolsAct.removeExtra("schoolName");
             if(mspinSys.getSelectedItemPosition()==spinAdapterSys.getCount())
             {
                 //mspinLocal.getSelectedItemPosition()==spinAdapterLocal.getCount()
                 Toast.makeText(getApplicationContext(),getString(R.string.spinSysError),Toast.LENGTH_LONG).show();
             }
             else if(mspinLvl.getSelectedItemPosition()==spinAdapterLvl.getCount())
             {
                 Toast.makeText(getApplicationContext(),getString(R.string.spinLvlError),Toast.LENGTH_LONG).show();
             }
             else if(mspinState.getSelectedItemPosition()==spinAdapterState.getCount())
             {
                 Toast.makeText(getApplicationContext(),getString(R.string.spinStateError),Toast.LENGTH_LONG).show();
             }
             else
             {
                 String langId=String.valueOf(mspinSys.getSelectedItemId()+1);
                 String stageId=String.valueOf(mspinLvl.getSelectedItemId()+1);
                 String stateId=String.valueOf(stateIntList.get(mspinState.getSelectedItemPosition()));
                 //String cityId=String.valueOf(cityIntList.get(mspinCity.getSelectedItemPosition()));

                 try {
                     localId=String.valueOf(String.valueOf(localIntList.get(mspinLocal.getSelectedItemPosition())));

                 }
                 catch (Exception e)
                 {
                     localId=" ";
                 }
                 Log.i("lang_id",String.valueOf(mspinSys.getSelectedItemId()+1));
                 Log.i("stage_id",String.valueOf(mspinLvl.getSelectedItemId()+1));
                 Log.i("state_id",String.valueOf(stateIntList.get(mspinState.getSelectedItemPosition())));
                 //Log.i("city_id",String.valueOf(cityIntList.get(mspinCity.getSelectedItemPosition())));
                 //Log.i("local_id",String.valueOf(localIntList.get(mspinLocal.getSelectedItemPosition())));
                 schoolsAct.putExtra("lang_id",langId);
                 schoolsAct.putExtra("stage_id",stageId);
                 schoolsAct.putExtra("state_id",stateId);
                 schoolsAct.putExtra("stageName",(mspinLvl.getSelectedItem().toString()));

                 //schoolsAct.putExtra("city_id",cityId);
                 /*if(mspinLocal.getSelectedItemPosition()==spinAdapterLocal.getCount())
                 {
                     schoolsAct.putExtra("local_id"," ");
                 }
                 else
                 {
                     schoolsAct.putExtra("local_id",localId);
                 }*/


                 startActivity(schoolsAct);
                 overridePendingTransition(R.anim.start_lefttoright,R.anim.end_righttoleft);
             }

    }

    private void definitions() {
        final VolleySingleton mVolleySingleton = VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();
        scrollView=findViewById(R.id.scrollView);

        btnMenu=findViewById(R.id.btnMenu);

        mLogin = mSharedPreferences.getBoolean("Login",false);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mTextViewProgressBar=(TextView)findViewById(R.id.tv_progressBar);
        mProgressBar.setVisibility(View.VISIBLE);
        mTextViewProgressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.INVISIBLE);


        tab = findViewById(R.id.tabLayout);
        searchLay = (LinearLayout) getLayoutInflater().inflate(R.layout.tab_items, null);
        searchLay.setScaleY(-1);
        imgSearch = searchLay.findViewById(R.id.img);
        mtxtSearch = searchLay.findViewById(R.id.txt);
        mapLay = (LinearLayout) getLayoutInflater().inflate(R.layout.tab_items, null);
        mapLay.setScaleY(-1);
        imgMap = mapLay.findViewById(R.id.img);
        mtxtMap = mapLay.findViewById(R.id.txt);
        messageLay = (LinearLayout) getLayoutInflater().inflate(R.layout.tab_items, null);
        messageLay.setScaleY(-1);
        imgMessage = messageLay.findViewById(R.id.img);
        mtxtMessage = messageLay.findViewById(R.id.txt);
        reportLay = (LinearLayout) getLayoutInflater().inflate(R.layout.tab_items, null);
        reportLay.setScaleY(-1);
        imgReport = reportLay.findViewById(R.id.img);
        mtxtReport = reportLay.findViewById(R.id.txt);
        mtxtLook = findViewById(R.id.txtLook);
        mtxtsearcBtn = findViewById(R.id.txtsearchBtn);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mspinSys = findViewById(R.id.spinSys);
        mspinLvl = findViewById(R.id.spinLvl);
        mspinState = findViewById(R.id.spinState);
        listSys = new ArrayList<String>();
        spinAdapterSys = new CustomSpinnerAdapter(getApplicationContext(), listSys);
        listLvl = new ArrayList<String>();
        spinAdapterLvl = new CustomSpinnerAdapter(getApplicationContext(), listLvl);
        listState = new ArrayList<String>();
        spinAdapterState = new CustomSpinnerAdapter(getApplicationContext(), listState);

        messagesAct = new Intent(getApplicationContext(), MessagesActivity.class);
        reportsAct = new Intent(getApplicationContext(), ReportsResultsActivity.class);
        schoolsAct = new Intent(getApplicationContext(), SchoolsActivity.class);
        languageAct = new Intent(getApplicationContext(), LanguageSelectionActivity.class);
        mapAct = new Intent(getApplicationContext(), MapsActivity.class);
        buttonClick = new AlphaAnimation(1F, 0.8F);
        navigationView = findViewById(R.id.nav_view);
        signAct = new Intent(getApplicationContext(),SignUpNowActivity.class );
        configAct=new Intent(getApplicationContext(),ConfigActivity.class);
        callAct=new Intent(getApplicationContext(),CallUsActivity.class);
        aboutAct=new Intent(getApplicationContext(),AboutActivity.class);
        urlSearch= MainApp.SearchUrl;
        currentLang=getString(R.string.currentLang);
        if(currentLang.equals("en"))
        {
            typesNames="en_name";
        }
        else
        {
            typesNames="name";
        }
        stateIntList=new ArrayList<Integer>();
        urlCity=MainApp.CityUrl;

        //cityIntList=new ArrayList<Integer>();
        urlLocal=MainApp.LocalUrl;

        //localIntList=new ArrayList<Integer>();

        urlSearchName=MainApp.showSchoolUrl +currentLang;

        //eSearchSchool=findViewById(R.id.editSchoolName);
        //eSearchSchool.setText("");

        sidebarExit=findViewById(R.id.imgExit);
        sideTxtExit=findViewById(R.id.txtExit);
        if(mLogin)
        {
            user_id = mSharedPreferences.getString("id","");
            Log.i("user_ID",user_id);
        }
        else
        {
            sidebarExit.setImageResource(R.mipmap.login);
            sideTxtExit.setText(R.string.login);
        }



    }
    private void tabSet()
    {
        tab.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        mtxtSearch.setText(R.string.search);
        imgSearch.setImageResource(R.mipmap.search_color);
        tab.getTabAt(0).setCustomView(searchLay);
        mtxtMap.setText(R.string.map);
        imgMap.setImageResource(R.mipmap.map);
        tab.getTabAt(1).setCustomView(mapLay);
        mtxtMessage.setText(R.string.message);
        imgMessage.setImageResource(R.mipmap.message);
        tab.getTabAt(2).setCustomView(messageLay);
        mtxtReport.setText(R.string.reports);
        imgReport.setImageResource(R.mipmap.file);
        tab.getTabAt(3).setCustomView(reportLay);
    }

    private void setSpinnertxt()
    {

        Resources c=getApplicationContext().getResources();
        listSys.add(" ");
        listSys.add(c.getString(R.string.education));
        mspinSys.setAdapter(spinAdapterSys);
        mspinSys.setSelection(spinAdapterSys.getCount());
        mspinSys.setEnabled(false);
        listLvl.add(" ");
        listLvl.add(c.getString(R.string.eduLvl));
        mspinLvl.setAdapter(spinAdapterLvl);
        mspinLvl.setSelection(spinAdapterLvl.getCount());
        mspinLvl.setEnabled(false);

        listState.add(" ");
        listState.add(c.getString(R.string.state));
        mspinState.setAdapter(spinAdapterState);
        mspinState.setSelection(spinAdapterState.getCount());
        mspinState.setEnabled(false);

        /*listCity.add(" ");
        listCity.add(c.getString(R.string.city));
        mspinCity.setAdapter(spinAdapterCity);
        mspinCity.setSelection(spinAdapterCity.getCount());
        mspinCity.setEnabled(false);*/

        /*listLocal.add(" ");
        listLocal.add(c.getString(R.string.local));
        mspinLocal.setAdapter(spinAdapterLocal);
        mspinLocal.setSelection(spinAdapterLocal.getCount());
        mspinLocal.setEnabled(false);*/
    }
    public void onClickSign(View view)
    {
        //Log.i("new_Login",String.valueOf(mLogin));
        if(mLogin)
        {
            mSharedPreferences.edit().remove("id").commit();
            mSharedPreferences.edit().putBoolean("Login",false).commit();
            sidebarExit.setImageResource(R.mipmap.login);
            sideTxtExit.setText(R.string.login);
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            Toast.makeText(getApplicationContext(),getString(R.string.Logoutsucc),Toast.LENGTH_SHORT).show();
            startActivity(languageAct);
            overridePendingTransition(R.anim.start_downtoup,R.anim.end_uptodown);

        }
        else
        {
            startActivity(signAct);
            overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);
        }

    }
    private void tabSelect()
    {
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0)
                {

                }
                else if(tab.getPosition()==1)
                {
                    startActivity(mapAct);
                    overridePendingTransition(R.anim.start_lefttoright,R.anim.end_righttoleft);
                }
                else if(tab.getPosition()==2)
                {
                    if(mLogin)
                    {
                        startActivity(messagesAct);
                        overridePendingTransition(R.anim.start_lefttoright,R.anim.end_righttoleft);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),getString(R.string.youmustLogintosee),Toast.LENGTH_SHORT).show();
                        startActivity(signAct);
                        overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);
                    }

                }
                else if(tab.getPosition()==3)
                {
                    if (mLogin)
                    {
                        startActivity(reportsAct);
                        overridePendingTransition(R.anim.start_lefttoright,R.anim.end_righttoleft);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),getString(R.string.youmustLogintosee),Toast.LENGTH_SHORT).show();
                        startActivity(signAct);
                        overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);
                    }
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        if(mLogin)
        {
            finishAffinity();
        }
        else
        {
            mSharedPreferences.edit().clear().commit();
            startActivity(languageAct);
            overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);
        }

    }
    private void keyHash()
    {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.myschool.ibtdi.myschool", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            { MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e)
        {
            System.out.println(e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    @Override

    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));    }

    @Override
    protected void onResume() {
        super.onResume();
        tab.getTabAt(0).select();
    }

    public void onClickNotif(View view) {
        startActivity(new Intent(getApplicationContext(),NotificationsActivity.class));
    }
}
