package com.bellng.fuelprices.service;

import com.bellng.fuelprices.dto.ServiceStation;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Bell on 08-Nov-16.
 */

public interface FuelService {

    @GET("bylocation?")
    Observable<List<ServiceStation>> getStations(@Query("latitude") double latitude, @Query("longitude") double longitude, @Query("radius") int radius, @Query("fuelType") String fuelType);

}
