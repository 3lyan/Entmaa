package com.m3lyan.entmaa.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.m3lyan.entmaa.Adapter.CustomSpinnerAdapter;
import com.m3lyan.entmaa.Adapter.MyRecyclerNotifyAdapter;
import com.m3lyan.entmaa.Adapter.MyRecyclerQAdapter;
import com.m3lyan.entmaa.Model.AddQModel;
import com.m3lyan.entmaa.Model.NotifyModel;
import com.m3lyan.entmaa.R;

import java.util.ArrayList;
import java.util.Locale;

public class NotifyActivity extends AppCompatActivity {

    private RecyclerView recyclerNotify;
    private MyRecyclerNotifyAdapter notifyAdapter;
    private NotifyModel notifyModel;
    private ArrayList<NotifyModel> data;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Intent aboutAct;
    private Intent callUsAct;
    private Intent configAct;
    private Intent homeAct;
    private Intent questionAct;
    private Intent packAct;

    private RelativeLayout menuButton;
    private RelativeLayout backButton;
    private Locale currentLocale;

    private SharedPreferences mSharedPreferences;
    private String filename = "entmaa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);
        definitions();
        navClick();
    }
    private void definitions()
    {
        mSharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);

        currentLocale=getResources().getConfiguration().locale;

        recyclerNotify=findViewById(R.id.recyclerNotify);
        recyclerNotify.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        data=new ArrayList<>();
        notifyModel=new NotifyModel(getResources().getString(R.string.byAdmin),getResources().getString(R.string.youMustSign));
        data.add(notifyModel);
        notifyModel=new NotifyModel(getResources().getString(R.string.byAdmin),getResources().getString(R.string.youMustSign));
        data.add(notifyModel);
        notifyModel=new NotifyModel(getResources().getString(R.string.byAdmin),getResources().getString(R.string.youMustSign));
        data.add(notifyModel);
        notifyModel=new NotifyModel(getResources().getString(R.string.byAdmin),getResources().getString(R.string.youMustSign));
        data.add(notifyModel);
        notifyAdapter=new MyRecyclerNotifyAdapter(getApplicationContext(),data);
        recyclerNotify.setAdapter(notifyAdapter);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer);

        aboutAct=new Intent(getApplicationContext(),AboutActivity.class);
        callUsAct=new Intent(getApplicationContext(),CallUsActivity.class);
        configAct=new Intent(getApplicationContext(),ConfigActivity.class);
        homeAct=new Intent(getApplicationContext(),HomeActivity.class);
        questionAct=new Intent(getApplicationContext(),QuestionsActivity.class);
        packAct=new Intent(getApplicationContext(),PackagesActivity.class);

        menuButton=findViewById(R.id.relativeButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        backButton=findViewById(R.id.relativeBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnBack();
        spinCreator();

    }
    private void navClick()
    {
        if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            navigationView.setPadding(0,350,0,0);
        }
        else{
            navigationView.setPadding(0,100,0,0);
        }
        navigationView.setCheckedItem(R.id.nav_about);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        startActivity(homeAct);
                        break;
                    case R.id.nav_about:
                        startActivity(aboutAct);
                        break;
                    case R.id.nav_call:
                        startActivity(callUsAct);
                        break;
                    case R.id.nav_config:
                        startActivity(configAct);
                        break;
                    case R.id.nav_consult:
                        startActivity(questionAct);
                        break;
                    case R.id.nav_notify:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_backage:
                        startActivity(packAct);
                        break;
                    case R.id.nav_exit:
                        Intent signinAct=new Intent(getApplicationContext(),SignInActivity.class);
                        mSharedPreferences.edit().putBoolean("login", false).commit();
                        startActivity(signinAct);
                        break;
                }
                return true;
            }
        });
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setLocale();
    }
    private void setLocale() {
        Locale.setDefault(currentLocale);
        Configuration config = new Configuration();
        config.locale = currentLocale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
    private void btnBack()
    {
        ImageView imgBack=findViewById(R.id.imgBack);
        if(currentLocale.toString().equals("ar"))
        {
            imgBack.setImageResource(R.mipmap.arrow);
        }
        else
        {
            imgBack.setImageResource(R.mipmap.arrow2);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_notify);
    }
    private void spinCreator()
    {
        final ArrayList<String> language;
        language = new ArrayList<String>();
        language.add("اللغة العربية");
        language.add("English");
        Spinner spinner = (Spinner) navigationView.getMenu().findItem(R.id.nav_lang).getActionView();
        CustomSpinnerAdapter spinAdapter = new CustomSpinnerAdapter(getApplicationContext(), language);
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,language));
        if (currentLocale.toString().equals("ar"))
        {
            spinner.setSelection(0);
        }
        else
        {
            spinner.setSelection(1);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0&&currentLocale.toString().equals("en"))
                {
                    //chooseLang("ar");
                    mSharedPreferences.edit().putString("lang","ar").apply();
                    startActivity(homeAct);
                }
                else if(position==1&&currentLocale.toString().equals("ar"))
                {
                    //chooseLang("en");
                    mSharedPreferences.edit().putString("lang","en").apply();
                    startActivity(homeAct);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

}
