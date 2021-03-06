package lewczyk.pracainzynierska.UserExercise.Tracker;

import android.Manifest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lewczyk.pracainzynierska.Data.DefaultId;
import lewczyk.pracainzynierska.R;
import lewczyk.pracainzynierska.UserExercise.UserExerciseList.UserExerciseListActivity;
import lewczyk.pracainzynierska.UserExercise.UserExercisePlanExerciseList.UserExercisePlanExerciseListActivity;
import lewczyk.pracainzynierska.UserExercise.UserExerciseToDoList.UserExerciseToDoListActivity;

public class TrackerActivity extends FragmentActivity implements  LocationListener, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        TrackerView{

    @BindView(R.id.distance) TextView distanceTextView;
    @BindView(R.id.duration_value) TextView durationTextView;
    @BindView(R.id.startStopTracking) Button startStopTracking;
    private int DEFAULT_ID = DefaultId.DEFAULT_ID.defaultNumber;
    private static final String TAG = "TrackerActivity";
    private static long INTERVAL = 1000 * 30 * 1; //30 seconds
    private static long FASTEST_INTERVAL = 1000 * 30 * 1;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;
    private String mLastUpdateTime;
    private GoogleMap googleMap;
    private ArrayList<Location> locations;
    private float distanceReached;
    private float[] distanceResult;
    private Handler timerHandler = new Handler();
    private boolean exerciseContinues = false;
    private long startTime, timeInMilliseconds, timeBuffer, updatedTime;
    private int fromActivity;
    private long exerciseToDoId, exerciseId, trainingPlanId;
    private double sensorParameter, planedDistance;
    private ArrayList<String> exercisesDone;
    private TrackerPresenter presenter;

    private static final int PERMISSION_LOCATION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TrackerPresenter(this);
        setContentView(R.layout.activity_tracker);
        ButterKnife.bind(this);
        if(!checkLocationPermission()){
            requestPermissionsFromUser();
        }
        if (!isGooglePlayServicesAvailable()) {
            finish();
        }
        setViewSettings();
        initVariables();
        createLocationRequest();
        buildGoogleApiClient();
        getSupportFragmentManagerAndMap();
    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    private void setViewSettings() {
        setTitle(getString(R.string.title_activity_tracker));
        loadIntentData();
        startStopTracking.setText(getString(R.string.begin_exercise));
        durationTextView.setText("0:00:000");
        if(planedDistance != 0.0){
            distanceTextView.setText("0.0 m" + "/" + planedDistance + "m");
        } else {
            distanceTextView.setText("0.0 m");
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    private void initVariables() {
        distanceReached = 0;
        distanceResult = new float[1];
        locations = new ArrayList<>();
    }

    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }

    private void loadIntentData() {
        Intent intent = getIntent();
        fromActivity = intent.getIntExtra("from", DEFAULT_ID);
        exercisesDone = intent.getStringArrayListExtra("exercisesDone");
        switch(fromActivity){
            case 0:
                exerciseId = intent.getLongExtra("exerciseId", DEFAULT_ID);
                trainingPlanId = intent.getLongExtra("trainingPlan", DEFAULT_ID);
                if(validateId(exerciseId) && validateId(trainingPlanId)){
                    presenter.loadExercisePlanData();
                    planedDistance = presenter.getTrainingPlanLoad();
                    sensorParameter = presenter.getSensorParameter();
                }
                break;
            case 1:
                exerciseId = intent.getLongExtra("exerciseId", DEFAULT_ID);
                if(validateId(exerciseId)){
                    presenter.loadExerciseParametersActivityData();
                    planedDistance = intent.getDoubleExtra("distance", 0.0);
                    sensorParameter = presenter.getSensorParameter();
                }
                break;
            case 2:
                exerciseToDoId = intent.getLongExtra("exerciseToDo", DEFAULT_ID);
                if(validateId(exerciseToDoId)){
                    presenter.loadExerciseToDoData();
                    planedDistance = presenter.getExerciseToDoLoad();
                    sensorParameter = presenter.getSensorParameter();
                }
                break;
        }
        Log.i(TAG, "planedDistance" + planedDistance);
        INTERVAL = 1000 * (long) sensorParameter;
        FASTEST_INTERVAL = 1000 * (long) sensorParameter;
    }

    private boolean validateId(long id) {
        return presenter.validateId(id);
    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    private void getSupportFragmentManagerAndMap(){
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        fm.getMapAsync(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if(checkLocationPermission()){
            this.googleMap.setMyLocationEnabled(true);
            this.googleMap.getUiSettings().setZoomControlsEnabled(true);
            this.googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            this.googleMap.getUiSettings().setCompassEnabled(true);
            this.googleMap.getUiSettings().setRotateGesturesEnabled(true);
            this.googleMap.getUiSettings().setZoomGesturesEnabled(true);
        } else {
            requestPermissionsFromUser();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "onConnected - isConnected ...............: " + mGoogleApiClient.isConnected());
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.d(TAG, "Connection method has been called");
                        if (checkLocationPermission()) {
                            startLocationUpdates();
                        }else{
                            requestPermissionsFromUser();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });
    }

    private void startLocationUpdates() {
        if(checkLocationPermission()){
            PendingResult<Status> pendingResult = LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
            Log.d(TAG, "Location update started ..............: ");
        } else {
            requestPermissionsFromUser();
        }
    }

    private void requestPermissionsFromUser(){
        ActivityCompat.requestPermissions(TrackerActivity.this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_REQUEST_CODE);
    }

    private boolean checkLocationPermission() {
        return ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "Firing onLocationChanged..............................................");
        mCurrentLocation = location;
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        locations.add(location);
        Log.i(TAG, "location added");
        if(exerciseContinues){
            if(locations.size()>1) {
                calculateDistance();
                Log.i(TAG, "calculate distance finished");
            }
            addMarker();
        }
    }

    private void calculateDistance() {
        int firstPoint = locations.size() - 2;
        int secondPoint = locations.size() - 1;
        Location.distanceBetween(locations.get(firstPoint).getLatitude(), locations.get(firstPoint).getLongitude(), locations.get(secondPoint).getLatitude(), locations.get(secondPoint).getLongitude(), distanceResult);
        distanceReached += distanceResult[0];
        if(planedDistance != 0.0){
            distanceTextView.setText(String.valueOf(Math.round(distanceReached * 10)/ 10f) + " / " + planedDistance + " m");
            if(distanceReached > planedDistance){
                playMotivationSound();
            }
        } else {
            distanceTextView.setText(String.valueOf(Math.round(distanceReached * 10)/ 10f) + " m");
        }
    }

    private void playMotivationSound() {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.ta_da);
        mediaPlayer.start();
    }

    private void addMarker() {
        MarkerOptions options = new MarkerOptions();
        IconGenerator iconFactory = new IconGenerator(this);
        iconFactory.setStyle(IconGenerator.STYLE_PURPLE);
        options.icon(BitmapDescriptorFactory.fromBitmap(iconFactory.makeIcon(mLastUpdateTime)));
        options.anchor(iconFactory.getAnchorU(), iconFactory.getAnchorV());

        LatLng currentLatLng = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        options.position(currentLatLng);
        Marker mapMarker = googleMap.addMarker(options);
        long atTime = mCurrentLocation.getTime();
        mLastUpdateTime = DateFormat.getTimeInstance().format(new Date(atTime));
        mapMarker.setTitle(mLastUpdateTime);
        Log.d(TAG, "Marker added.............................");
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng,
                13));
        Log.d(TAG, "Zoom done.............................");
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    private void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
        Log.d(TAG, "Location update stopped .......................");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mGoogleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @OnClick(R.id.startStopTracking)
    public void startStopTrackingButtonPressed(){
        if(exerciseContinues){
            timerStops();
            changeScreenBrightness(-1F);
        } else {
            timerCounts();
            changeScreenBrightness(0.01F);
        }
    }

    private void changeScreenBrightness(float screenBrightness){
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.screenBrightness = screenBrightness;
        getWindow().setAttributes(layout);
    }

    @Override
    public void onBackPressed(){
        timerStops();
        confirmEndOfExercise();
    }

    private void timerCounts(){
        startTime = SystemClock.uptimeMillis();
        timerHandler.postDelayed(updateTimerThread, 0);
        startStopTracking.setText(getString(R.string.stop_exercise));
        exerciseContinues = true;
    }

    private void timerStops(){
        timeBuffer += timeInMilliseconds;
        timerHandler.removeCallbacks(updateTimerThread);
        startStopTracking.setText(getString(R.string.begin_exercise));
        exerciseContinues = false;
    }

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeBuffer + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            durationTextView.setText(mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%03d", milliseconds));
            timerHandler.postDelayed(this, 0);
        }
    };

    private void confirmEndOfExercise() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirm);
        builder.setMessage(R.string.popup_end_exercise_confirm);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                goToPreviousActivity();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void goToPreviousActivity(){
        presenter.addExerciseToArchive();
        if(exercisesDone == null){
            exercisesDone = new ArrayList<>();
        }
        exercisesDone.add(String.valueOf(exerciseId));
        Intent intent;
        switch(fromActivity){
            case 0:
                intent = new Intent(this, UserExercisePlanExerciseListActivity.class);
                intent.putStringArrayListExtra("exercisesDone", exercisesDone);
                intent.putExtra("planId", trainingPlanId);
                startActivity(intent);
                finish();
                break;
            case 1:
                intent = new Intent(this, UserExerciseListActivity.class);
                startActivity(intent);
                finish();
                break;
            case 2:
                presenter.deleteCurrentExerciseFromExerciseToDo();
                intent = new Intent(this, UserExerciseToDoListActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "Connection failed: " + connectionResult.toString());
    }

    @Override
    public long getUpdatedTime(){
        return updatedTime;
    }

    @Override
    public long getExerciseId(){
        return exerciseId;
    }

    @Override
    public long getTrainingPlanId(){
        return trainingPlanId;
    }

    @Override
    public long getExerciseToDoId(){
        return exerciseToDoId;
    }

    @Override
    public float getDistanceReached(){return distanceReached;}

    @Override
    public Context getContext(){
        return this;
    }
}