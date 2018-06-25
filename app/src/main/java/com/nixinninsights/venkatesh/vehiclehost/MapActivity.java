package com.nixinninsights.venkatesh.vehiclehost;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;

import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;

import android.text.TextUtils;
import android.util.Log;

import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.gson.JsonArray;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;


import com.nixinninsights.venkatesh.vehiclehost.InternetCheck.CheckInternet;

import com.nixinninsights.venkatesh.vehiclehost.InternetCheck.InternetCheck;
import com.nixinninsights.venkatesh.vehiclehost.Services.LocationMonitoringService;
import com.nixinninsights.venkatesh.vehiclehost.SharedPreference.UserDetailsLogin;
import com.nixinninsights.venkatesh.vehiclehost.Transisions.TransistionOfFragments;
import com.nixinninsights.venkatesh.vehiclehost.Urls.UrlData;
import com.nixinninsights.venkatesh.vehiclehost.Urls.VehicleHostKeys;
import com.nixinninsights.venkatesh.vehiclehost.Validations.FormsValidations;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

TransistionOfFragments transistionOfFragments=new TransistionOfFragments();
    private static final String TAG ="MAP ACTIVITY" ;
    //setting map properties to  google maps
   public  GoogleMap mGoogleMap;
    //setting googel apiclient functions to map functions
    GoogleApiClient mGoogleApiClient;
    //Location for getting the current update locatioin in maps

    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;
    private String mLastUpdateTime;
    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 20000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    public Double intitlat, initlong;
    public Double TrackerLatitude,TrackerLongitude;
    public String TrackerName;
    SupportMapFragment mapFragment;

    public int grantresule[] = {1, 1, 1, 1};
    //My prermission requestgrated
    public Snackbar snackbar;
    public DrawerLayout drawer;
    Criteria mCriteria;
    BottomSheetBehavior bottomSheetBehavior;

    private boolean internetConnected = false;
    public boolean verified;
    public static final int Myrequestpermissiongranted = 11;
    public  boolean mLocationPermissionGranted;
    LocationManager manager;
    private String bestProvider;
    DataforStorage dataforStorage;
    Location mLocation;
    CardView card;

    ArrayList directionMarker=new ArrayList();
    Marker NearVehicleMarker;
    FusedLocationProviderClient mFusedLocationProviderClient;

    TextView placetext,directions;

    String locationfailure = "PLEASE TURN ON LOCATION";

    String internetfailure = "YOU ARE NOT CONNECTED TO INTERNET PLEAE CONEECT TO INTERNET";

    //Toolbar objects
    Switch aSwitch;
    CardView Routeinfo;
    //


    public String Hostname="venkatesh",Hostvehicleno="Ap34FEG",BusRouteNo="127k",
            BusRouteFrom="kondapur",BusRouteTOAddress="Koti";
//
    public double directionlattitude,directionlongitude;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private boolean mRequestingLocationUpdates;
    JSONObject HostedVehicles;
    public  int counting =1;
    private ArrayList<Hoster> personList;
    View hostVehicleDetailsbottomsheet;
    Button Checkservice;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private boolean mAlreadyStartedService = false;
    UserDetailsLogin Usersession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        aSwitch=toolbar.findViewById(R.id.Hostlocation);
         Routeinfo=toolbar.findViewById(R.id.RouteInfo);
         Checkservice=toolbar.findViewById(R.id.getLocation);
         Usersession=new UserDetailsLogin(getApplicationContext());
         if(Usersession.CheckLogin())
             finish();

        dataforStorage=new DataforStorage(this);


        personList = new ArrayList<>();
         card=findViewById(R.id.Cardviewfordetails);

         LinearLayout llBottomSheet = (LinearLayout) findViewById(R.id.bottomsheet);

        placetext=findViewById(R.id.TextviewforPlace);
        directions=findViewById(R.id.directionText);

        requestPermissions();
        //if checking the playservice avilable in maps then initialiing the maps
        if (GoogleplayserviceAvilable()) {
            initMap();
        }

        Init();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headeritems=navigationView.getHeaderView(0);
        TextView Accountname=headeritems.findViewById(R.id.AccountViews);

      // Toast.makeText(this, Accountname.toString(), Toast.LENGTH_SHORT).show();
        Accountname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);

                 transistionOfFragments.AccountTransistion("Venkates",getFragmentManager());


            }
        });
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                    {

                        locationchecking(getApplicationContext());
                        if(internetConnected)
                        {
                            if(initlong!=null &&intitlat !=null)
                            {

                                ChecckDataaddornotinHost();
                            }
                            aSwitch.setText("TURN OF");
                        }
                        else
                        {
                            SnackbarMessagee(internetfailure);
                        }
                    }
                    else
                    {
                        aSwitch.setChecked(false);
                        aSwitch.setText("Host Vehicle");
                    }
            }
        });
        Checkservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                        new BroadcastReceiver() {
                            @Override
                            public void onReceive(Context context, Intent intent) {
                                String latitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE);
                                String longitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE);

                                if (latitude != null && longitude != null)
                                {
                                    Toast.makeText(getApplicationContext(),
                                            "started locationserrvice"
                                            + "\n Latitude : " + latitude +
                                                    "\n Longitude: " + longitude,Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST)
                );
            }
        });
    }

    public void ChecckDataaddornotinHost()
    { Cursor result=dataforStorage.getdata();
        if(result.getCount()==0)
        {
            Toast.makeText(this, "Nodata", Toast.LENGTH_SHORT).show();
            aSwitch.setChecked(false);
            transistionOfFragments.Modifiyingdetails(getFragmentManager());
        }
        else {
            aSwitch.setChecked(true);
            while (result.moveToNext())
            {
                BusRouteNo=result.getString(1);
                BusRouteFrom=result.getString(2);
                BusRouteTOAddress=result.getString(3);
                Hostvehicleno=result.getString(4);
                Hostname=result.getString(5);
            }
            UploadHostdata();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        //updateLocationUI();
    }
    private void initMap() {
         mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        try {
            manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(myIntent);

            } else {
                mapFragment.getMapAsync(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        mapFragment.getMapAsync(this);
    }
    public void Init()
    {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                // location is received
                mCurrentLocation = locationResult.getLastLocation();
                mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
                updateLocationUI();
            }
        };


        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }
    public void PlaceforNearVehicles()
    {
        if(intitlat!=null&&initlong!=null)
        {
            Toast.makeText(this, "PlaceForNewdeails", Toast.LENGTH_SHORT).show();
            String type ="Bus Station";

            StringBuilder sb = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            sb.append("location="+intitlat+","+initlong);
            sb.append("&radius=5000");
            sb.append("&types="+type);
            sb.append("&sensor=true");
            sb.append("&key=AIzaSyBIeIjGb1hCxgmwXni-ff5-w_kjcXUkuq8");

//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=17.00,78.00&radius=5000&types=Bus%20Stations&sensor=true&key=AIzaSyBIeIjGb1hCxgmwXni-ff5-w_kjcXUkuq8
            // Creating a new non-ui thread task to download json data
           PlacesTask placesTask = new PlacesTask();

            // Invokes the "doInBackground()" method of the class PlaceTask
            placesTask.execute(sb.toString());

        }
    }

    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            initlong=mCurrentLocation.getLongitude();
            intitlat= mCurrentLocation.getLatitude();
        }
    }




    public void UploadHostdata()
    {
      //  progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
     //   progressDialog.show();
    //    GetValueFromEditText();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlData.UploadHostdataurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             //   progressDialog.dismiss();

                Toast.makeText(MapActivity.this, response, Toast.LENGTH_LONG).show();
            }
        }
                ,new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
               // progressDialog.dismiss();
                Toast.makeText(MapActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        )
        {
            @Override
            protected Map<String,String> getParams()
            {   Map<String,String> params=new HashMap<>();
                params.put("HOSTEMAIL","venky@gmail.com");
                params.put("HOSTNAME",Hostname);
                params.put("VEHICLENO",Hostvehicleno);
                params.put("BUSROUTENO",BusRouteNo);
                params.put("FROM",BusRouteFrom);
                params.put("TO",BusRouteTOAddress);
                params.put("LATITUDE",Double.toString(intitlat));
                params.put("LONGITUDE",Double.toString(initlong));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MapActivity.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.map, menu);
        return true;
    }
    //create the full sceen with transperent screen in mapview
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.NearBus) {
            UploadTrackerData();

            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            PlaceforNearVehicles();
        }
        else if(id==R.id.Normal)
        {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        else if(id==R.id.Hybrid)
        {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }
        else if(id==R.id.Satellite)
        {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }

        else if(id==R.id.terrain)
        {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {
       //mCurrentlocatioin = location;
        if (location == null) {
            Toast.makeText(this, "could not get currnet location", Toast.LENGTH_SHORT).show();
        } else {

            LatLng latlong = new LatLng(location.getLatitude(), location.getLongitude());
            intitlat = location.getLatitude();
            initlong = location.getLongitude();
            //updating the camera very near to the earh
            if (mGoogleApiClient != null) {
                // locationManager.removeUpdates(this);
            }
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(latlong, 15);
            mGoogleMap.animateCamera(update);
        }
    }



    public void locationchecking(final Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        try {
            assert lm != null;
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ignored) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ignored) {
        }

        if (!gps_enabled && !network_enabled) {
            Toast.makeText(context, "Gps Not Enabled", Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            context.startActivity(myIntent);
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public void UploadTrackerData()
    {

       TrackerLongitude= initlong;
       TrackerLatitude=intitlat;
       TrackerName="Hello Venky";
        //  progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        //   progressDialog.show();
        //    GetValueFromEditText();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlData.TrackerUploadData, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   progressDialog.dismiss();
               // Toast.makeText(MapActivity.this, response, Toast.LENGTH_LONG).show();
                NearVehicleList();
        }
        }
                ,new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
                Toast.makeText(MapActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        )
        {
            @Override
            protected Map<String,String> getParams()
            {   Map<String,String> params=new HashMap<String, String>();
                params.put("TRACKERNAME",TrackerName);
                params.put("LATITUDE",Double.toString(TrackerLatitude));
                params.put("LONGITUDE",Double.toString(TrackerLongitude));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MapActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
    private void NearVehicleList() {

        //  progressDialog.setMessage("Please Wait, We are Inserting Your Data on Server");
        //   progressDialog.show();
        //    GetValueFromEditText();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlData.NearVehicleList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //   progressDialog.dismiss();
                 Toast.makeText(MapActivity.this, response, Toast.LENGTH_LONG).show();
                //NearVehicleList();
                ShowJson(response);
            }
        }
                ,new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
                Toast.makeText(MapActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }
        )
        {
            @Override
            protected Map<String,String> getParams()
            {   Map<String,String> params=new HashMap<String, String>();
                params.put("TRACKERNAME",TrackerName);
                    params.put("RANGE",Double.toString(10));

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MapActivity.this);
        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }

    private void ShowJson(String response) {
        String hostname;
        String lattitude;
        String longitude;
        String Hostpersonname;


        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            // Toast.makeText(getApplicationContext(), result.toString(),Toast.LENGTH_LONG).show();
            String nullable="[]";


            if(nullable.equals(result.toString()))
            {
                if(counting==1)
                {
                    Toast.makeText(getApplicationContext(),"No hosted persons",Toast.LENGTH_LONG).show();
                    mGoogleMap.clear();
                    counting++;}
            }

            int retval = personList.size();
            if (retval == 0) {
                for (int i = 0; i < result.length(); i++) {

                    HostedVehicles = result.getJSONObject(i);
                    hostname = HostedVehicles.getString(VehicleHostKeys.KEY_NAME);
                    lattitude = HostedVehicles.getString(VehicleHostKeys.KEY_LATTITUDE);
                    longitude = HostedVehicles.getString(VehicleHostKeys.KEY_LONGITUDE);
                    Hostpersonname=HostedVehicles.getString(VehicleHostKeys.KEY_HostPerson);
                    Hoster s = new Hoster(hostname, lattitude, longitude,Hostpersonname);
                    personList.add(s);
                }
            } else {
                personList.clear();
                for (int i = 0; i < result.length(); i++) {

                    HostedVehicles = result.getJSONObject(i);
                    hostname = HostedVehicles.getString(VehicleHostKeys.KEY_NAME);
                    lattitude = HostedVehicles.getString(VehicleHostKeys.KEY_LATTITUDE);
                    longitude = HostedVehicles.getString(VehicleHostKeys.KEY_LONGITUDE);
                    Hostpersonname=HostedVehicles.getString(VehicleHostKeys.KEY_HostPerson);
                    Hoster s = new Hoster(hostname, lattitude, longitude,Hostpersonname);
                    personList.add(s);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Toast.makeText(MainActivity.this,response,Toast).show();
      placingthemarker();
    }

    private void placingthemarker() {
        String Routename;
        String HostpersonName;
        String lat;
        String lng;
        //  String markers = "true";
        int siz = personList.size();
        Iterator<Hoster> itr = personList.iterator();
        mGoogleMap.clear();
        while (itr.hasNext()) {
            Hoster hst = itr.next();
            Routename = hst.Routename;
            lat = hst.lattitude;
            lng = hst.longitude;
            HostpersonName=hst.Hostpersonname;
            setmarkers(Routename, Double.parseDouble(lat), Double.parseDouble(lng), siz,HostpersonName);
            // drawMarker(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)), name);

            // mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng))));

        }

        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(18));
        //  Toast.makeText(this,"values are"+name,Toast.LENGTH_LONG).show();
    }


    private void setmarkers(String Routename, double lat, double lon, int size,String Hostpersonname) {

        LatLng ll = new LatLng(lat, lon);

        MarkerOptions options = new MarkerOptions().title(Routename)
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.bus))
                .position(new LatLng(lat, lon))
                .snippet(Hostpersonname);

        NearVehicleMarker = mGoogleMap.addMarker(options);
        NearVehicleMarker.showInfoWindow();
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
    }

    public void RouteinformationDetails(View view) {
        transistionOfFragments.Modifiyingdetails(getFragmentManager());

    }

    private class Hoster {
        String Routename;
        String lattitude;
        String longitude;
        String Hostpersonname;
        Hoster(String Routename, String lattitude, String longitude,String Hostpersoname) {
            this.Routename = Routename;
            this.lattitude = lattitude;
            this.longitude = longitude;
            this.Hostpersonname=Hostpersoname;
        }
    }

    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                      //  Toast.makeText(getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());

                        updateLocationUI();
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(MapActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);

                                Toast.makeText(MapActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }

                        updateLocationUI();
                    }
                });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        //when initiising the map in the fragment we are setting mapfunctionlities to Customfunctionalitsin too google map
        mGoogleMap = googleMap;
        if (CheckInternet.isInternetAvailable(getApplicationContext())) {

            if (mGoogleMap != null) {
                manager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
                mCriteria = new Criteria();
                bestProvider = String.valueOf(manager.getBestProvider(mCriteria, true));

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,

                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                mLocation = manager.getLastKnownLocation(bestProvider);
                if (mLocation != null) {
                    Log.e("TAG", "GPS is on");
                    final double currentLatitude = mLocation.getLatitude();
                    final double currentLongitude = mLocation.getLongitude();
                    LatLng loc1 = new LatLng(currentLatitude, currentLongitude);
                   // mGoogleMap.addMarker(new MarkerOptions().position(loc1).title("Your Current Location"));
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 15));
                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);
                }
                else {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    manager.requestLocationUpdates(bestProvider, 1000, 0, (LocationListener) this);
                }
                setupMap();
                mGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {

                        if (directionMarker.size() > 0) {
                            directionMarker.clear();
                            mGoogleMap.clear();
                        }
                        // Adding new item to the ArrayList
                        directionMarker.add(latLng);

                        MarkerOptions options = new MarkerOptions();
                        options.position(latLng);
                        if (directionMarker.size() == 1)
                        {
                            options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                            mGoogleMap.addMarker(options);
                        }
                        LatLng latlongg=options.getPosition();
                        String ss=getAddress(latlongg);

                        directionlattitude=latLng.latitude;
                        directionlongitude=latLng.longitude;
                        placetext.setText(ss);
                        card.setVisibility(View.VISIBLE);

                    }
                });
                mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        View v = getLayoutInflater().inflate(R.layout.hostmarkerinfo, null);
                        TextView TVROute = (TextView) v.findViewById(R.id.Routetitle);
                        TextView Hostperson = (TextView) v.findViewById(R.id.Hostperson);
                        TVROute.setText(marker.getTitle());
                        Hostperson.setText(marker.getSnippet());
                        return v;
                    }
                });
                mGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                    //passingthe hostperson name to vehicle fragment to getiing information for vehicle.
                        transistionOfFragments.MovingNewfragment(marker.getSnippet(),getFragmentManager());

                     //  Toast.makeText(MapActivity.this, marker.getSnippet(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });

                mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        card.setVisibility(View.GONE);
                       // directionMarker.clear();
                       mGoogleMap.clear();
                    }

                });

            }}
        }


    private void setupMap() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
       mylocationbuttonposition();
       mGoogleMap.setPadding(200,0,0,0);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        mGoogleMap.getUiSettings().setIndoorLevelPickerEnabled(true);
        mGoogleMap.setBuildingsEnabled(true);
        mGoogleMap.setIndoorEnabled(true);
        StartLocationUpdatesStartActivity();
    }
    public void mylocationbuttonposition()
    {
        View locationButton = ((View) mapFragment.getView().findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        //changing position of location button to bottom
        rlp.setMargins(0, 0, 30, 320);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        registerInternetCheckReceiver();
        if(checkPermissions()) {
            mapFragment.getMapAsync(this);
        }
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }
        else
        {
            requestPermissions();
        }
        startStep1();

    }
    private void startStep1() {

        //Check whether this user has installed Google play service which is being used by Location updates.
        if (GoogleplayserviceAvilable()) {

            //Passing null to indicate that it is executing for the first time.
            startStep2(null);
        }
        else {
            Toast.makeText(getApplicationContext(),
                    R.string.no_google_playservice_available, Toast.LENGTH_LONG).show();
        }
    }
    private Boolean startStep2(DialogInterface dialog) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            promptInternetConnect();
            return false;
        }


        if (dialog != null) {
            dialog.dismiss();
        }

        //Yes there is active internet connection. Next check Location is granted by user or not.

        if (checkPermissions()) { //Yes permissions are granted by the user. Go to the next step.
            startStep3();
        } else {  //No user has not granted the permissions yet. Request now.
            requestPermissions();
        }
        return true;
    }
    private void promptInternetConnect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MapActivity.this);
        builder.setTitle(R.string.title_alert_no_intenet);
        builder.setMessage(R.string.msg_alert_no_internet);
        String positiveText = getString(R.string.btn_label_refresh);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Block the Application Execution until user grants the permissions
                        if (startStep2(dialog)) {
                            //Now make sure about location permission.
                            if (checkPermissions()) {
                                //Step 2: Start the Location Monitor Service
                                //Everything is there to start the service.
                                startStep3();
                            } else if (!checkPermissions()) {
                                requestPermissions();
                            }
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void requestPermissions() {

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        boolean shouldProvideRationale2 =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);


        // Provide an additional rationale to the img_user. This would happen if the img_user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(MapActivity.this,
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the img_user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(MapActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }
    private void startStep3() {

        //And it will be keep running until you close the entire application from task manager.
        //This method will executed only once.

        if (!mAlreadyStartedService ) {


            //Start location sharing service to app server.........
            Intent intent = new Intent(this, LocationMonitoringService.class);
            startService(intent);

            mAlreadyStartedService = true;
            //Ends................................................
        }
    }
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }



    private boolean checkPermissions() {

        int permissionState1 = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED
                && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }
    public boolean GoogleplayserviceAvilable()
    {
        GoogleApiAvailability api=GoogleApiAvailability.getInstance();
        int isAvailable=api.isGooglePlayServicesAvailable(this);
        if(isAvailable==ConnectionResult.SUCCESS)
        {
            return  true;
        }
        else if(api.isUserResolvableError(isAvailable))
        {
            Dialog dailog = api.getErrorDialog(this, isAvailable, 0);
            dailog.show();
        }
        else
        {
            SnackbarMessagee("canot connect play service");
        }
        return false;
    }


    public void SnackbarMessagee(String status) {
        String internetStatus = "";
        snackbar = Snackbar.make(drawer, status, Snackbar.LENGTH_LONG);
        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        sbView.setBackgroundColor(Color.RED);
        snackbar.show();

    }
    public void setSnackbarMessage(String status) {
        String internetStatus = "";
        if (status.equalsIgnoreCase("Wifi enabled") || status.equalsIgnoreCase("Mobile data enabled")) {
            internetStatus = "Connecton established";
        } else {
            internetStatus = "Could not connect to internet";
        }

        snackbar = Snackbar.make(drawer, internetStatus, Snackbar.LENGTH_LONG);
        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);

        // Changing action button text color
        View sbView = snackbar.getView();

        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        if (internetStatus.equalsIgnoreCase("Could not connect to internet")) {
            if (internetConnected) {

                sbView.setBackgroundColor(Color.RED);
                verified=false;
                snackbar.show();
                internetConnected = false;
            }
        } else {
            if (!internetConnected) {

                sbView.setBackgroundColor(Color.rgb(0, 100, 0));
                verified=true;
                internetConnected = true;
                snackbar.show();
            }
        }

    }

    private void registerInternetCheckReceiver() {
        IntentFilter internetFilter = new IntentFilter();
        internetFilter.addAction("android.net.wifi.STATE_CHANGE");
        internetFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(broadcastReceiver, internetFilter);
    }
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String status = InternetCheck.getConnectivityStatusString(context);
            setSnackbarMessage(status);
        }
    };
    public void DirectionsinMaps(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/maps?daddr="+directionlattitude+","+directionlongitude));
        startActivity(intent);
    }
//clicking the button updating the location updates
    public void StartLocationUpdatesStartActivity() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }
                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }


    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);

        }
    }

    public String getAddress(LatLng point) {
        try {
            Geocoder geocoder;
            List<Address> addresses;
            geocoder = new Geocoder(this);
            if (point.latitude != 0 || point.longitude != 0) {
                addresses = geocoder.getFromLocation(point.latitude,
                        point.longitude, 1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getAddressLine(1);
                String country = addresses.get(0).getAddressLine(2);
                return address;
            } else {
                Toast.makeText(this, "latitude and longitude are null",
                        Toast.LENGTH_LONG).show();
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }
    public  class PlacesTask extends AsyncTask<String, Integer, String>{

        String data = null;

        // Invoked by execute() method of this object
        @Override
        protected String doInBackground(String... url) {
            try{
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(String result){
            ParserTask parserTask = new ParserTask();

            // Start parsing the Google places in JSON format
            // Invokes the "doInBackground()" method of the class ParseTask
            parserTask.execute(result);
        }

    }

    /** A class to parse the Google Places in JSON format */
    public  class ParserTask extends AsyncTask<String, Integer, List<HashMap<String,String>>>{

        JSONObject jObject;

        // Invoked by execute() method of this object
        @Override
        protected List<HashMap<String,String>> doInBackground(String... jsonData) {

            List<HashMap<String, String>> places = null;
            PlaceJSONParser placeJsonParser = new PlaceJSONParser();

            try{
                jObject = new JSONObject(jsonData[0]);

                /** Getting the parsed data as a List construct */
                places = placeJsonParser.parse(jObject);

            }catch(Exception e){
                Log.d("Exception",e.toString());
            }
            return places;
        }

        // Executed after the complete execution of doInBackground() method
        @Override
        protected void onPostExecute(List<HashMap<String,String>> list){
            // Clears all the existing markers
            mGoogleMap.clear();

            for(int i=0;i<list.size();i++){

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Getting a place from the places list
                HashMap<String, String> hmPlace = list.get(i);

                // Getting latitude of the place
                double lat = Double.parseDouble(hmPlace.get("lat"));

                // Getting longitude of the place
                double lng = Double.parseDouble(hmPlace.get("lng"));
                //Toast.makeText(MapActivity.this, "Hello", Toast.LENGTH_SHORT).show();

                // Getting name
                String name = hmPlace.get("place_name");

                // Getting vicinity
                String vicinity = hmPlace.get("vicinity");

                LatLng latLng = new LatLng(lat, lng);

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                //This will be displayed on taping the marker
                markerOptions.title(name + " : " + vicinity);
                Log.v("MAP ACTIVITY","Runnigthe data in for amoaefiieoibnkjvnfvububu");

                // Placing a marker on the touched position
                mGoogleMap.addMarker(markerOptions);
            }
        }
    }

}
