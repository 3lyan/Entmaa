package com.m3lyan.entmaa.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.m3lyan.entmaa.Adapter.CustomSpinnerAdapter;
import com.m3lyan.entmaa.Adapter.MyRecyclerCategoriesAdapter;
import com.m3lyan.entmaa.Adapter.MyRecyclerTypesAdapter;
import com.m3lyan.entmaa.MainApp.MainApp;
import com.m3lyan.entmaa.Model.CategoriesModel;
import com.m3lyan.entmaa.Model.HomeDataModel;
import com.m3lyan.entmaa.Model.HomeModel;
import com.m3lyan.entmaa.Model.TypesDataModel;
import com.m3lyan.entmaa.Model.TypesModel;
import com.m3lyan.entmaa.R;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TypesActivity extends AppCompatActivity {
    private RecyclerView recyclerTypes;
    private MyRecyclerTypesAdapter typesAdapter;
    private TypesModel typesModel;
    private ArrayList<TypesDataModel> data;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Intent aboutAct;
    private Intent callUsAct;
    private Intent configAct;
    private Intent homeAct;
    private Intent questionAct;
    private Intent notifyAct;
    private Intent packAct;

    private RelativeLayout menuButton;
    private RelativeLayout backButton;

    private Locale currentLocale;

    private SharedPreferences mSharedPreferences;
    private String filename = "entmaa";
    private int typesId;
    private TypesDataModel typesDataModel;

    private TextView txtTitle;
    private String typesTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_types);
        definitions();
        navClick();
        homeData();

    }
    private void definitions()
    {

        mSharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);
        currentLocale=getResources().getConfiguration().locale;
        recyclerTypes=findViewById(R.id.recyclerTypes);
        recyclerTypes.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        data=new ArrayList<>();
        //typesAdapter=new MyRecyclerTypesAdapter(getApplicationContext(),data);
        //recyclerTypes.setAdapter(typesAdapter);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer);

        aboutAct=new Intent(getApplicationContext(),AboutActivity.class);
        callUsAct=new Intent(getApplicationContext(),CallUsActivity.class);
        configAct=new Intent(getApplicationContext(),ConfigActivity.class);
        homeAct=new Intent(getApplicationContext(),HomeActivity.class);
        questionAct=new Intent(getApplicationContext(),QuestionsActivity.class);
        notifyAct=new Intent(getApplicationContext(),NotifyActivity.class);
        packAct=new Intent(getApplicationContext(),PackagesActivity.class);

        Bundle extras = TypesActivity.this.getIntent().getExtras();
        typesId=extras.getInt("type_id");
        typesTitle=extras.getString("type_title");

        txtTitle=findViewById(R.id.toolbar_title);
        txtTitle.setText(typesTitle);
        
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
                        startActivity(notifyAct);
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
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_about);
        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }

    }
    private void homeData()
    {
        MainApp.apiService.TypesDataRead(typesId,currentLocale.toString()).enqueue(new Callback<TypesModel>() {
            @Override
            public void onResponse(Call<TypesModel> call, Response<TypesModel> response) {
                for (int i=0;i<response.body().getData().size();i++)
                {
                    Log.i("classes",response.body().getData().get(i).getName());
                    typesDataModel=new TypesDataModel(response.body().getData().get(i).getId(),response.body().getData().get(i).getImage(),response.body().getData().get(i).getName(),response.body().getData().get(i).getOffer());
                    data.add(typesDataModel);
                }
                typesAdapter=new MyRecyclerTypesAdapter(getApplicationContext(),data);
                recyclerTypes.setAdapter(typesAdapter);
            }

            @Override
            public void onFailure(Call<TypesModel> call, Throwable t) {

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
