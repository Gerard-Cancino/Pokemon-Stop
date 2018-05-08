package com.example.fderenzi.pokemonstop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
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
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted;
    public static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private Location mLastKnownLocation;
    private LatLng mDefaultLocation;
    private static final float DEFAULT_ZOOM = 14.0f;
    String[] mLikelyPlaceNames;
    String[] mLikelyPlaceAddresses;
    String[] mLikelyPlaceAttributions;
    LatLng[] mLikelyPlaceLatLngs;
    public static final int M_MAX_ENTRIES = 5;
    private static final String TAG = "MyActivity";
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private Location mCurrentLocation;
    private Location mCameraPosition;

    private static final Node marker1 = new Node (40.799030, -73.575573);

    private static final Node marker2 = new Node (40.796685, -73.573142);
    private static final Node marker3 = new Node (40.799409, -73.574252);
    private ArrayList<Node>markList = new ArrayList<>();

    private ArrayList<Node>closeNodeList;
    private Node closestNode;
    private boolean isInGrass;
    private static boolean isInBattle;

    private Timer timer;
    private TimerTask timerTask;

    private Handler handler;
    private Handler nodeHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        closeNodeList = new ArrayList<> ();
        markList.add(marker1);
        markList.add(marker2);
        markList.add(marker3);

        marker1.getAdjNode().add(marker2);
        marker1.getAdjNode().add(marker3);

        marker2.getAdjNode().add(marker1);
        marker2.getAdjNode().add(marker3);

        marker3.getAdjNode().add(marker1);
        marker3.getAdjNode().add(marker2);

        isInGrass = false;
        handler = new Handler();
        nodeHandler = new Handler();
        isInBattle = false;

        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);



        if (savedInstanceState != null) {
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

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

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
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

    private void locationNodeUpdater() {
        nodeHandler.postDelayed(new Runnable(){
            public void run(){

                if (mLastKnownLocation != null) {
                    if (closestNode != null) {
                        updateCloseNode();
                        isInGrass = isInRadiusOfNode();
                        if (isInGrass&&!isInBattle)
                            startTimer();
                    } else
                        findCloseNode();
                }
                nodeHandler.postDelayed(this,5000);
            }
        },5000);
    }

    private void startTimer() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run () {
                if(isInGrass && !isInBattle) {
                    handler.postDelayed(this,5000); //runs second time as this time
                    battleEventTriggered(handler,this);
                }
            };
        }, 5000); //runs first time as 4 seconds


    }

    public void battleEventTriggered(Handler handleBattle,Runnable runBattle){
        Random rand = new Random();
        int n = rand.nextInt(2)+1;
        if(n==1){
            handleBattle.removeCallbacks(runBattle);
            isInBattle = true;
            Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
            startActivity(historyIntent);
            this.onPause();
        }
        isInBattle = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    private void mapAddMarker (GoogleMap map){
        LatLng grass1 = new LatLng(marker1.getLatitude(), marker1.getLongitude());
        map.addMarker(new MarkerOptions().position(grass1));
        LatLng grass2 = new LatLng(marker2.getLatitude(), marker2.getLongitude());
        map.addMarker(new MarkerOptions().position(grass2));
        LatLng grass3 = new LatLng(marker3.getLatitude(), marker3.getLongitude());
        map.addMarker(new MarkerOptions().position(grass3));
    }

    private void findCloseNode (){
        Iterator<Node> itMark = markList.iterator();
        while(itMark.hasNext()){
            Node pointer = itMark.next();
            if(pointer.calcDistance(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude())<0.0006) {
                closestNode = pointer;
                markList = closestNode.getAdjNode();
            }
        }
    }

    private boolean isInRadiusOfNode() {
        if (closestNode.calcDistance(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()) < 0.004)
            return true;
        else
            return false;
    }

    private void updateCloseNode (){
        Node current = closestNode;
        Iterator<Node>itArray = markList.iterator();
        while (itArray.hasNext()) {
            double distance = current.calcDistance(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
            Node temp = itArray.next();
            double tempDistance = temp.calcDistance(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude());
            if(tempDistance<distance)
                current=temp;
        }
        closestNode = current;
        if(closestNode.calcDistance(mLastKnownLocation.getLatitude(),mLastKnownLocation.getLongitude())>0.006)
            closestNode = null;
        markList = closestNode.getAdjNode();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
        // Do other setup activities here too, as described elsewhere in this tutorial.
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
        mapAddMarker(map);
        updateLocationUI();
        if(mLastKnownLocation!=null)
            findCloseNode();
        locationNodeUpdater();
    }

}