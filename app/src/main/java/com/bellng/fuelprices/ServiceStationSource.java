package com.bellng.fuelprices;

import android.content.SharedPreferences;

import com.bellng.fuelprices.dto.ServiceStation;
import com.bellng.fuelprices.service.FuelService;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Bell on 20-Nov-16.
 */

public class ServiceStationSource {
    FuelService service;
    SharedPreferences sharedPreferences;

    List<ServiceStation> serviceStations;

    public ServiceStationSource(FuelService service, SharedPreferences sharedPreferences) {
        this.service = service;
        this.sharedPreferences = sharedPreferences;
    }

    public Observable<List<ServiceStation>> update(double latitude, double longitude, int radius, String fuelType) {
        return service.getStations(latitude, longitude, radius, fuelType)
                .flatMap(new Func1<List<ServiceStation>, Observable<List<ServiceStation>>>() {
                    @Override
                    public Observable<List<ServiceStation>> call(List<ServiceStation> serviceStations) {
                        ServiceStationSource.this.serviceStations = serviceStations;
                        return Observable.just(serviceStations);
                    }
                });
    }

    public Observable<List<ServiceStation>> update(double latitude, double longitude) {
        return update(latitude, longitude, sharedPreferences.getInt(Constants.SEARCH_RADIUS, 3), sharedPreferences.getString(Constants.FUEL_TYPE, "E10"));
    }

    public Observable<List<ServiceStation>> getServiceStations() {
        if (serviceStations == null) {
            return Observable.empty();
        }

        return Observable.just(serviceStations);
    }
}
