package com.esspl.hemendra.mapdemo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hemendra on 25-07-2016.
 *
 * https://maps.googleapis.com/maps/api/directions/json?origin=75+9th+Ave+New+York,+NY&
 destination=MetLife+Stadium+1+MetLife+Stadium+Dr+East+Rutherford,+NJ+07073&key=YOUR_API_KEY
 */
public interface LocationDirectionService {

        /*@GET("/maps/api/directions/json")
        public Call<DirectionResults> getJson(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key, Callback<DirectionResults> callback);*/

    @GET("/maps/api/directions/json")
    public Call<DirectionResults> getJson(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);

    @GET("/maps/api/directions/json")
    public Call<DirectionResults> getJson(@Query("origin") String origin, @Query("destination") String destination);
}
