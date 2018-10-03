package com.myschool.ibtdi.myschool.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myschool.ibtdi.myschool.MainApp.MainApp;
import com.myschool.ibtdi.myschool.Network.VolleySingleton;
import com.myschool.ibtdi.myschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Intent searchSchoolsAct;
    private Intent reportsAct;
    private Intent messagesAct;
    private TabLayout tab;
    private DrawerLayout mDrawerLayout;
    private ImageView imgSearch;
    private TextView mtxtSearch;
    private ImageView imgMap;
    private TextView mtxtMap;
    private ImageView imgMessage;
    private TextView mtxtMessage;
    private ImageView imgReport;
    private TextView mtxtReport;
    private LinearLayout searchLay;
    private LinearLayout mapLay;
    private LinearLayout messageLay;
    private LinearLayout reportLay;
    private Intent signAct;
    private Intent configAct;
    private Intent callAct;
    private Intent aboutAct;
    private LocationManager locationManager;

    private String urlMapData;
    private RequestQueue mVolleySingletonRequestQueue;

    private SharedPreferences mSharedPreferences;
    private String filename = "school";
    private String currentLang;

    private ImageView sidebarExit;
    private TextView sideTxtExit;

    private String user_id;
    private boolean mLogin;

    private AlertDialog.Builder dialog;

    private Intent myIntent;

    public void onClickAbout(View view) {
        startActivity(aboutAct);
    }

    public void onClickCallus(View view) {
        startActivity(callAct);
    }

    public void onClickConfig(View view) {
        if (mLogin) {
            startActivity(configAct);
            overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.youmustLogintosee), Toast.LENGTH_SHORT).show();
            startActivity(signAct);
            overridePendingTransition(R.anim.start_uptodown,R.anim.end_downtoup);

        }
    }

    public void onClickMenu(View view) {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
    }

    public void onClickBack(View view) {
        startActivity(searchSchoolsAct);
    }

    private void definition() {
        final VolleySingleton mVolleySingleton = VolleySingleton.getsInstance();
        mVolleySingletonRequestQueue = mVolleySingleton.getRequestQueue();

        mLogin = mSharedPreferences.getBoolean("Login", false);

        messagesAct = new Intent(getApplicationContext(), MessagesActivity.class);
        reportsAct = new Intent(getApplicationContext(), ReportsResultsActivity.class);
        searchSchoolsAct = new Intent(getApplicationContext(), SearchForSchoolActivity.class);
        tab = findViewById(R.id.tabLayout);
        mDrawerLayout = findViewById(R.id.drawer_layout);
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
        signAct = new Intent(getApplicationContext(), SignUpNowActivity.class);
        configAct = new Intent(getApplicationContext(), ConfigActivity.class);
        callAct = new Intent(getApplicationContext(), CallUsActivity.class);
        aboutAct = new Intent(getApplicationContext(), AboutActivity.class);
        currentLang = getString(R.string.currentLang);
        urlMapData = MainApp.MapData + currentLang;

        sidebarExit = findViewById(R.id.imgExit);
        sideTxtExit = findViewById(R.id.txtExit);
        if (mLogin) {
            user_id = mSharedPreferences.getString("id", "");
            Log.i("user_ID", user_id);
        } else {
            sidebarExit.setImageResource(R.mipmap.login);
            sideTxtExit.setText(R.string.login);
        }
        dialog = new AlertDialog.Builder(MapsActivity.this);
        myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        //alert = dialog.create();
        dialog.setMessage(getApplicationContext().getResources().getString(R.string.gps));
        dialog.setPositiveButton(getApplicationContext().getResources().getString(R.string.open_location), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub
                startActivity(myIntent);
                //alert.dismiss();
                //get gps
            }
        });
        dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void tabSet() {
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

    public void onClickSign(View view) {
        if (mLogin) {
            mSharedPreferences.edit().remove("id").commit();
            mSharedPreferences.edit().putBoolean("Login", false).commit();
            sidebarExit.setImageResource(R.mipmap.login);
            sideTxtExit.setText(R.string.login);
            mDrawerLayout.closeDrawer(Gravity.RIGHT);
            Toast.makeText(getApplicationContext(), getString(R.string.Logoutsucc), Toast.LENGTH_SHORT).show();
            startActivity(searchSchoolsAct);
        } else {
            startActivity(signAct);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPreferences(filename, MODE_PRIVATE);
        currentLang = mSharedPreferences.getString("lang", "en");
        chooseLang(currentLang);
        setContentView(R.layout.activity_maps);
        definition();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tabSet();
        tabSelect();
        OnPerrmisssion();
    }

    private void chooseLang(String lang) {
        try {
            String language = lang;
            String languageToLoad = language; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        } catch (Exception e) {

        }
    }

    private void mapSchoolsLocations() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlMapData, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject all = new JSONObject(response.toString());
                    String js = all.getString("locations");
                    JSONArray loactionArr = new JSONArray(js);
                    for (int i = 0; i < loactionArr.length(); i++) {
                        JSONObject locationObject = loactionArr.getJSONObject(i);

                        int locationId = locationObject.getInt("id");
                        String schoolName;
                        if (currentLang.equals("en")) {
                            schoolName = locationObject.getString("en_name");
                        } else {
                            schoolName = locationObject.getString("name");
                        }
                        double latitude = locationObject.getDouble("lat");
                        double longitude = locationObject.getDouble("lng");
                        LatLng place = new LatLng(latitude, longitude);
                        Log.i("lng", Double.toString(latitude));
                        final Marker myMarker = mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_school)).position(place).title(schoolName));
                        myMarker.setTag(locationId);
                        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker) {
                                Integer clickCount = (Integer) marker.getTag();
                                if (clickCount == 0) {

                                } else {
                                    Toast.makeText(getApplicationContext(), getString(R.string.youChoose) +" "+ marker.getTitle(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), AboutSchoolActivity.class);
                                    intent.putExtra("school_id", clickCount);
                                    startActivity(intent);
                                }

                                return false;
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mapSchoolsLocations();
            }
        });
        mVolleySingletonRequestQueue.add(jsonObjectRequest);
    }


    private void tabSelect() {
        tab.getTabAt(1).select();
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    startActivity(searchSchoolsAct);
                    overridePendingTransition(R.anim.start_righttoleft,R.anim.end_lefttoright);
                } else if (tab.getPosition() == 1) {

                } else if (tab.getPosition() == 2) {
                    if (mLogin) {
                        startActivity(messagesAct);
                        overridePendingTransition(R.anim.start_lefttoright,R.anim.end_righttoleft);
                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.youmustLogintosee), Toast.LENGTH_SHORT).show();
                        startActivity(signAct);
                    }

                } else if (tab.getPosition() == 3) {
                    if (mLogin) {
                        startActivity(reportsAct);
                        overridePendingTransition(R.anim.start_lefttoright,R.anim.end_righttoleft);

                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.youmustLogintosee), Toast.LENGTH_SHORT).show();
                        startActivity(signAct);
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
        startActivity(searchSchoolsAct);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapSchoolsLocations();
        //foo(MapsActivity.this);
        //getLocation();

        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(home.latitude, home.longitude), 17.0f));

    }

    private void OnPerrmisssion() {
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

    }

    void getLocation() {
        try {
            //boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            //if (isNetworkEnabled) {
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean gps_enabled;
            boolean provider_enabled;
            try {
                gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
                gps_enabled = false;
            }
            try {
                provider_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
                provider_enabled = false;
            }
            if (provider_enabled) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 15000, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                            LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                            Marker myMarker = mMap.addMarker(new MarkerOptions().position(current).title(getString(R.string.yourplace)));
                            myMarker.setTag(0);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current.latitude, current.longitude), 14.0f));
                        }


                        @Override
                        public void onStatusChanged(String s, int i, Bundle bundle) {
                            //Toast.makeText(MapsActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
                            getLocation();

                        }

                        @Override
                        public void onProviderEnabled(String s) {
                            getLocation();
                        }

                        @Override
                        public void onProviderDisabled(String s) {

                        }
                    });
                }
            else if (gps_enabled) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5000, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
                        Marker myMarker = mMap.addMarker(new MarkerOptions().position(current).title(getString(R.string.yourplace)));
                        myMarker.setTag(0);
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(current.latitude, current.longitude), 14.0f));
                    }


                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                        //Toast.makeText(MapsActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_SHORT).show();
                        getLocation();

                    }

                    @Override
                    public void onProviderEnabled(String s) {
                        getLocation();
                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });
            }
                else
                {
                    dialog.show();
                    //Toast.makeText(MapsActivity.this, getString(R.string.gps), Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }

        }
        catch(SecurityException e) {
            //e.printStackTrace();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        tab.getTabAt(1);
        getLocation();
    }

    @Override

    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));    }

}
