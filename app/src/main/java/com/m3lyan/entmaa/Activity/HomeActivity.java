package com.m3lyan.entmaa.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.m3lyan.entmaa.Adapter.CustomSpinnerAdapter;
import com.m3lyan.entmaa.Adapter.MyRecyclerCategoriesAdapter;
import com.m3lyan.entmaa.MainApp.MainApp;
import com.m3lyan.entmaa.Model.BannersDataModel;
import com.m3lyan.entmaa.Model.BannersModel;
import com.m3lyan.entmaa.Model.CategoriesModel;
import com.m3lyan.entmaa.Model.HomeDataModel;
import com.m3lyan.entmaa.Model.HomeModel;
import com.m3lyan.entmaa.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private SliderLayout mHeadSlider;
    private HashMap<String,ArrayList<BannersDataModel>> imgCollect;
    private ArrayList<BannersDataModel> bannersData;
    private BannersDataModel bannersDataModel;

    private RecyclerView recyclerCategory;
    private MyRecyclerCategoriesAdapter categoriesAdapter;
    private HomeDataModel homeDataModel;
    private ArrayList<HomeDataModel> data;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private Intent aboutAct;
    private Intent callUsAct;
    private Intent configAct;
    private Intent questionAct;
    private Intent notifyAct;
    private Intent packAct;
    private Intent homeAct;

    private RelativeLayout menuButton;

    private SharedPreferences mSharedPreferences;
    private String filename = "entmaa";
    public String lang;

    private TextView txtItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme_NoActionAndStatusBar);
        mSharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);
        lang=mSharedPreferences.getString("lang","ar");
        chooseLang(lang);
        setContentView(R.layout.activity_home);
        definitions();
        imgSlider();
        navClick();
        homeData();



    }
    private void definitions()
    {
        mHeadSlider=findViewById(R.id.slider);
        imgCollect= new HashMap<String, ArrayList<BannersDataModel>>();
        bannersData=new ArrayList<>();
        recyclerCategory=findViewById(R.id.recyclerCategories);
        txtItems =findViewById(R.id.txtItems);
        if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerCategory.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        }
        else{
            recyclerCategory.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        }
        data=new ArrayList<>();



        navigationView = (NavigationView) findViewById(R.id.nav_view);

        drawerLayout=findViewById(R.id.drawer);

        aboutAct=new Intent(getApplicationContext(),AboutActivity.class);
        callUsAct=new Intent(getApplicationContext(),CallUsActivity.class);
        configAct=new Intent(getApplicationContext(),ConfigActivity.class);
        questionAct=new Intent(getApplicationContext(),QuestionsActivity.class);
        notifyAct=new Intent(getApplicationContext(),NotifyActivity.class);
        packAct=new Intent(getApplicationContext(),PackagesActivity.class);
        homeAct=new Intent(getApplicationContext(),HomeActivity.class);

        menuButton=findViewById(R.id.relativeButton);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        spinCreator();

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
    private void navClick()
    {

        if(getApplicationContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            navigationView.setPadding(0,350,0,0);
        }
        else{
            navigationView.setPadding(0,100,0,0);
        }
        navigationView.setCheckedItem(R.id.nav_home);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        drawerLayout.closeDrawers();
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
    private void imgSlider() {
        MainApp.apiService.BannersImg(lang).enqueue(new Callback<BannersModel>() {
            @Override
            public void onResponse(Call<BannersModel> call, Response<BannersModel> response) {
                for (int i=0;i<response.body().getData().size();i++)
                {
                    bannersDataModel=new BannersDataModel(response.body().getData().get(i).getId(),response.body().getData().get(i).getLink(),response.body().getData().get(i).getImage(),response.body().getData().get(i).getText());
                    bannersData.add(bannersDataModel);
                    imgCollect.put(response.body().getData().get(i).getText(), bannersData);
                }

                for (int i=0;i<bannersData.size();i++) {
                    TextSliderView textSliderView = new TextSliderView(HomeActivity.this);
                    final int finalI1 = i;
                    textSliderView
                            .description(bannersData.get(i).getText())
                            .image(bannersData.get(i).getImage())
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                @Override
                                public void onSliderClick(BaseSliderView slider) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(bannersData.get(finalI1).getLink()));
                                    startActivity(intent);
                                }
                            });

                    textSliderView.bundle(new Bundle());

                    textSliderView.getBundle()
                            .putString("extra", response.body().getData().get(i).getText());
                    mHeadSlider.addSlider(textSliderView);
                }
                mHeadSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                mHeadSlider.setCustomAnimation(new DescriptionAnimation());
                mHeadSlider.setDuration(25000);
                mHeadSlider.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            }

            @Override
            public void onFailure(Call<BannersModel> call, Throwable t) {

            }
        });


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
        if (lang.equals("ar"))
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
                if(position==0&&lang.equals("en"))
                {
                    //chooseLang("ar");
                    mSharedPreferences.edit().putString("lang","ar").apply();
                    startActivity(homeAct);
                }
                else if(position==1&&lang.equals("ar"))
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
    private void homeData()
    {
        MainApp.apiService.homeDataRead(lang).enqueue(new Callback<HomeModel>() {
            @Override
            public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {
                for (int i=0;i<response.body().getData().size();i++)
                {
                    Log.i("classes",response.body().getData().get(i).getName());
                    homeDataModel=new HomeDataModel(response.body().getData().get(i).getId(),response.body().getData().get(i).getImage(),response.body().getData().get(i).getName());
                    data.add(homeDataModel);
                    categoriesAdapter=new MyRecyclerCategoriesAdapter(getApplicationContext(),data);
                    recyclerCategory.setAdapter(categoriesAdapter);
                }
            }

            @Override
            public void onFailure(Call<HomeModel> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
