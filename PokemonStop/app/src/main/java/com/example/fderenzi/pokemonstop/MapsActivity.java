package com.example.fderenzi.pokemonstop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBufferResponse;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Location mLastKnownLocation;
    private static final float DEFAULT_ZOOM = 18.0f;
    private static final String TAG = "MyActivity";
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    private ArrayList<Node>markList;
    private ArrayList<Node>closeNodeList;
    private Node closestNode;


    private boolean isInGrass;
    private static boolean isInBattle;
    private boolean isInTimer;

    private Handler handler;
    private Handler nodeHandler;
    private Handler locHandler;


    @Override
    /**
     * Creates a map activity
     * Instantiates variables
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Instantiating private variables
        isInGrass = false;
        handler = new Handler();
        nodeHandler = new Handler();
        locHandler = new Handler();
        isInBattle = false;
        isInTimer = false;

        setContentView(R.layout.activity_maps);

        // Finds map activity.  Map is prepared in the background.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Fused Location Provider Client allows the app to find the location by an assortment of ways: WiFi, mobile data, GPS
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
    }


    // Asks device for location permissions
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        }
        else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    // If location permission is granted, then portray ui buttons and current location icon.
    // UI buttons are predefined within maps
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    // Gets the latitude and longitude of device if permission is granted
    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = (Location) task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(0, 0), DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }

                    }
                });
            }


        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    // Handler for location permission and current location
    private void handLocation(){
        locHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                updateLocationUI();
                getDeviceLocation();
                if(mLastKnownLocation==null)
                    locHandler.postDelayed(this,100);
            }
        },100);
    }


    // Override of onStop()
    @Override
    protected void onStop(){
        super.onStop();
        isInBattle = true; // if the user is in battle activity
    }

    // Override of onResume
    protected void onResume(){
        super.onResume();
        isInBattle = false;
    }

    // If true, it will start a battle, and send in extra PRIMATIVE (... i wish we could send a reference) into battle activity)
    public void battleEventTriggered(Handler handleBattle,Runnable runBattle){
        handleBattle.removeCallbacks(runBattle);
        Monster monAtNode = closestNode.randomMonsterSelect();

        Ability a1 = monAtNode.getAbility1();
        String a1Name = a1.getAName();
        int a1Damage = a1.getDamage();
        String a1Desc= a1.getDescription();

        Ability a2 = monAtNode.getAbility2();
        String a2Name = a2.getAName();
        int a2Damage = a2.getDamage();
        String a2Desc= a2.getDescription();

        Ability a3 = monAtNode.getAbility3();
        String a3Name = a3.getAName();
        int a3Damage = a3.getDamage();
        String a3Desc= a3.getDescription();

        Ability a4 = monAtNode.getAbility4();
        String a4Name = a4.getAName();
        int a4Damage = a4.getDamage();
        String a4Desc= a4.getDescription();

        String name = monAtNode.getName();
        int health = monAtNode.getHealth();


        Intent battleIntent = new Intent(getApplicationContext(), BattleActivity.class);
        battleIntent.putExtra("a1Name", a1Name );
        battleIntent.putExtra("a1Damage", a1Damage );
        battleIntent.putExtra("a1Desc", a1Desc );

        battleIntent.putExtra("a2Name", a2Name );
        battleIntent.putExtra("a2Damage", a2Damage );
        battleIntent.putExtra("a2Desc", a2Desc );

        battleIntent.putExtra("a3Name", a3Name );
        battleIntent.putExtra("a3Damage", a3Damage );
        battleIntent.putExtra("a3Desc", a3Desc );

        battleIntent.putExtra("a4Name", a4Name );
        battleIntent.putExtra("a4Damage", a4Damage );
        battleIntent.putExtra("a4Desc", a4Desc );

        battleIntent.putExtra("monName", name );

        startActivity(battleIntent);

    }

    // Finds the closest node from the entirety of all nodes created
    // Stops when finds one that reaches the requirements
    private void findCloseNode() {
        Iterator<Node> itMark = markList.iterator();
        while (itMark.hasNext()) {
            Node pointer = itMark.next();
            if (pointer.calcDistance(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()) < 0.0006) {
                closestNode = pointer;
                closeNodeList = closestNode.getAdjNode();
            }
        }
    }

    // Boolean to check if node is in a radius (a2 + b2 = c2, where c is the distance)
    private boolean isInRadiusOfNode() {
        if (closestNode.calcDistance(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()) < 0.004)
            return true;
        else
            return false;
    }

    // Updates the closest node from the adjacent nodes of the previous closest node
    private void updateCloseNode() {
        Node current = closestNode;
        Iterator<Node> itArray = closeNodeList.iterator();
        while (itArray.hasNext()) {
            double distance = current.calcDistance(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
            Node temp = itArray.next();
            double tempDistance = temp.calcDistance(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
            if (tempDistance < distance)
                current = temp;
        }
        closestNode = current;
        closeNodeList = closestNode.getAdjNode();
        if (closestNode.calcDistance(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()) > 0.006) {
            closestNode = null;
            closeNodeList = markList;
        }
    }

    // Handler! Updates close node every 5 seconds
    // Checks if current location is within the radius of a node
    // If it is then start the timer! see below
    private void locationNodeUpdater() {
        nodeHandler.postDelayed(new Runnable(){
            public void run(){
                if (mLastKnownLocation != null) {
                    if (closestNode != null) {
                        getDeviceLocation();
                        updateCloseNode();
                        isInGrass = isInRadiusOfNode();
                        if(isInGrass && !isInBattle) {
                            Random rand = new Random();
                            int n = rand.nextInt(3)+1;
                            if( n == 1) {
                                battleEventTriggered(handler, this);//runs second time as this time
                            }
                        }
                        nodeHandler.postDelayed(this, 5000);
                    }
                    else {
                        findCloseNode();
                        nodeHandler.postDelayed(this, 5000);
                    }

                }
                else {
                    nodeHandler.postDelayed(this,100);
                }
            }
        },0);
    }

    // Called from onCreate method
    // starts map and starts all prerequisites.
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            // Return null here, so that getInfoContents() is called next.
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                // Inflate the layouts for the info window, title and snippet.
                View infoWindow = getLayoutInflater().inflate(R.layout.custom_info_contents, null);

                TextView title = ((TextView) infoWindow.findViewById(R.id.title));
                title.setText(marker.getTitle());

                TextView snippet = ((TextView) infoWindow.findViewById(R.id.snippet));
                snippet.setText(marker.getSnippet());

                return infoWindow;
            }
        });

        NodeMon nm = new NodeMon(mMap);
        markList = nm.getMarkList();
        handLocation();
        locationNodeUpdater();
    }

}