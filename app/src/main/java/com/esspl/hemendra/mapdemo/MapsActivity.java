package com.esspl.hemendra.mapdemo;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final  String BASE_URL = "https://maps.googleapis.com/";
    private static final String TAG = MapsActivity.class.getSimpleName();
    private MarkerOptions markerOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    private void setupNetworkCall() {
        LocationDirectionService locationDirectionService = ServiceFactory.createRetrofitService(LocationDirectionService.class, BASE_URL);
        Log.i(TAG, "setupNetworkCall: key:"+this.getString(R.string.google_maps_key));
        //Call<DirectionResults> directionResultsCall = locationDirectionService.getJson("UNIT- 9, Bhubaneswar, Odisha", "Shriya Talkies, Kharabela Nagar, Bhubaneswar, Odisha");
        Call<DirectionResults> directionResultsCall = locationDirectionService.getJson("20.343846, 85.806321", "20.238479, 85.833698");
        directionResultsCall.enqueue(new Callback<DirectionResults>() {
            @Override
            public void onResponse(Call<DirectionResults> call, Response<DirectionResults> response) {
                Toast.makeText(MapsActivity.this, "on Response"+response.toString(), Toast.LENGTH_SHORT).show();

                DirectionResults directionResults =  response.body();
                Log.i("zacharia", "Error Message :" +directionResults.getError_message());
                Log.i("zacharia", "Status :" +directionResults.getStatus());
                Log.i("zacharia", "inside on success" +directionResults.getRoutes().size());
                Log.i(TAG, "onResponse: body"+ response.body().toString());
                ArrayList<LatLng> routelist = new ArrayList<LatLng>();
                List<Legs> legs = new ArrayList<Legs>();
                if(directionResults.getRoutes().size()>0){
                    ArrayList<LatLng> decodelist;
                    Route routeA = directionResults.getRoutes().get(0);
                    legs = routeA.getLegs();
                    Log.i("zacharia", "Legs length : "+ legs.size());
                    if(legs.size()>0){
                        List<Steps> steps= legs.get(0).getSteps();
                        Log.i("zacharia","Steps size :"+steps.size());
                        Steps step;
                        Location location;
                        String polyline;
                        for(int i=0 ; i<steps.size();i++){
                            step = steps.get(i);
                            location =step.getStart_location();
                            routelist.add(new LatLng(location.getLat(), location.getLng()));
                            Log.i("zacharia", "Start Location :" + location.getLat() + ", " + location.getLng());
                            polyline = step.getPolyline().getPoints();
                            decodelist = RouteDecode.decodePoly(polyline);
                            routelist.addAll(decodelist);
                            location =step.getEnd_location();
                            routelist.add(new LatLng(location.getLat() ,location.getLng()));
                            Log.i("zacharia","End Location :"+location.getLat() +", "+location.getLng());
                        }
                    }
                }
                Log.i("zacharia","routelist size : "+routelist.size());
                if(routelist.size()>0){
                    PolylineOptions rectLine = new PolylineOptions().width(10).color(Color.RED);

                    for (int i = 0; i < routelist.size(); i++) {
                        rectLine.add(routelist.get(i));
                    }
                    // Adding route on the map
                    markerOptions = new MarkerOptions();
                    mMap.addPolyline(rectLine);
                    StartLocation start_location = legs.get(0).getStart_location();
                    LatLng start = new LatLng(start_location.getLat(), start_location.getLng());
                    markerOptions.position(start).title(legs.get(0).getStart_address());
                    markerOptions.draggable(true);
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(start));
                    mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

                    EndLocation endLocation = legs.get(0).getEnd_location();
                    Log.i(TAG, "onResponse: End Lat = "+endLocation.getLat() + "End Lan:"+endLocation.getLng());
                    LatLng end = new LatLng(endLocation.getLat(), endLocation.getLng());
                    markerOptions.position(end).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).title(legs.get(0).getEnd_address());
                    markerOptions.draggable(true);
                    mMap.addMarker(markerOptions);
                }
            }

            @Override
            public void onFailure(Call<DirectionResults> call, Throwable t) {
                Toast.makeText(MapsActivity.this, "on Failure......", Toast.LENGTH_SHORT).show();
            }
        });

        /*try {
            Response<DirectionResults> directionResultsResponse = directionResultsCall.execute();
            DirectionResults directionResults = directionResultsResponse.body();
            Log.i("zacharia", "inside on success" +directionResults.getRoutes().size());
            ArrayList<LatLng> routelist = new ArrayList<LatLng>();
            if(directionResults.getRoutes().size()>0){
                ArrayList<LatLng> decodelist;
                Route routeA = directionResults.getRoutes().get(0);
                Log.i("zacharia", "Legs length : "+routeA.getLegs().size());
                if(routeA.getLegs().size()>0){
                    List<Steps> steps= routeA.getLegs().get(0).getSteps();
                    Log.i("zacharia","Steps size :"+steps.size());
                    Steps step;
                    Location location;
                    String polyline;
                    for(int i=0 ; i<steps.size();i++){
                        step = steps.get(i);
                        location =step.getStart_location();
                        routelist.add(new LatLng(location.getLat(), location.getLng()));
                        Log.i("zacharia", "Start Location :" + location.getLat() + ", " + location.getLng());
                        polyline = step.getPolyline().getPoints();
                        decodelist = RouteDecode.decodePoly(polyline);
                        routelist.addAll(decodelist);
                        location =step.getEnd_location();
                        routelist.add(new LatLng(location.getLat() ,location.getLng()));
                        Log.i("zacharia","End Location :"+location.getLat() +", "+location.getLng());
                    }
                }
            }
            Log.i("zacharia","routelist size : "+routelist.size());
            if(routelist.size()>0){
                PolylineOptions rectLine = new PolylineOptions().width(10).color(
                        Color.RED);

                for (int i = 0; i < routelist.size(); i++) {
                    rectLine.add(routelist.get(i));
                }
                // Adding route on the map
                mMap.addPolyline(rectLine);
                markerOptions.position(new LatLng(20.238479, 85.833698));
                markerOptions.draggable(true);
                mMap.addMarker(markerOptions);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/


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
        setupNetworkCall();


        // Add a marker in Sydney and move the camera
        /*LatLng lingarajTemple = new LatLng(20.238529, 85.833719);
        mMap.addMarker(new MarkerOptions().position(lingarajTemple).title("Lingaraj Temple").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lingarajTemple));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18));
        //mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.addPolyline(new PolylineOptions().add(lingarajTemple, new LatLng(20.237606, 85.832226)).color(Color.MAGENTA));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        mMap.setMyLocationEnabled(true);*/

    }
}
