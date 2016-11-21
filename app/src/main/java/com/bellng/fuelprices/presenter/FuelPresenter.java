package com.bellng.fuelprices.presenter;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bellng.fuelprices.dto.ServiceStation;
import com.bellng.fuelprices.service.FuelService;
import com.bellng.fuelprices.view.FuelView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.ArrayList;
import java.util.List;

import rx.Emitter;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Cancellable;
import rx.functions.Func1;

import static com.google.android.gms.location.LocationServices.FusedLocationApi;

/**
 * Created by Bell on 19-Nov-16.
 */

public class FuelPresenter extends MvpBasePresenter<FuelView> {

    FuelService service;
    GoogleApiClient googleApiClient;

    Location currentLocation;

    public FuelPresenter(FuelService service, GoogleApiClient googleApiClient) {
        this.service = service;
        this.googleApiClient = googleApiClient;
    }

    public void onAttach() {
        List<String> fuelTypes = new ArrayList<>();

        fuelTypes.add("E10");
        fuelTypes.add("U91");
        getView().setFuelTypeSpinner(fuelTypes);

        refreshData("E10");
    }

    public void buttonPressed(final double longitude, final double latitude, String selectedItem) {
        refreshData(selectedItem);
    }
    public Observable<String> observeConnectionCallbacks() {
        return Observable.fromEmitter(new Action1<Emitter<String>>() {
            @Override
            public void call(final Emitter<String> stringEmitter) {
                final GoogleApiClient.ConnectionCallbacks callbacks = new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        stringEmitter.onNext("connected");
                        stringEmitter.onCompleted();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                };
                stringEmitter.setCancellation(new Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        googleApiClient.unregisterConnectionCallbacks(callbacks);
                    }
                });
                googleApiClient.registerConnectionCallbacks(callbacks);
            }
        }, Emitter.BackpressureMode.BUFFER);

    }
    public Observable<Boolean> isGoogleApiClientConnected() {
        return Observable.just(googleApiClient.isConnected());
    }

    public Observable<List<ServiceStation>> getStationsDelayed(final String fuelType) {
        return observeConnectionCallbacks()
                .flatMap(new Func1<String, Observable<List<ServiceStation>>>() {
                    @Override
                    public Observable<List<ServiceStation>> call(String s) {
                        currentLocation = FusedLocationApi.getLastLocation(googleApiClient);
                        getView().centerMapTo(currentLocation.getLatitude(), currentLocation.getLongitude());
                        return service.getStations(currentLocation.getLatitude(), currentLocation.getLongitude(), 3, fuelType);
                    }
                });
    }

    private void refreshData(final String fuelType) {
        isGoogleApiClientConnected()
                .flatMap(new Func1<Boolean, Observable<List<ServiceStation>>>() {
                    @Override
                    public Observable<List<ServiceStation>> call(Boolean connected) {
                        if (!connected) {
                            return getStationsDelayed(fuelType);
                        }
                        currentLocation = FusedLocationApi.getLastLocation(googleApiClient);
                        getView().centerMapTo(currentLocation.getLatitude(), currentLocation.getLongitude());
                        return service.getStations(currentLocation.getLatitude(), currentLocation.getLongitude(), 3, fuelType);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<ServiceStation>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("asdasda", e.toString());
                    }

                    @Override
                    public void onNext(List<ServiceStation> serviceStations) {
                        getView().clearMarkers();
                        getView().setAveragePrice(calculateAveragePrice(serviceStations));
                        getView().setServiceStationList(serviceStations);

                        for (ServiceStation station : serviceStations) {
                            getView().addServiceStationMarker(station.getName(), station.getLat(), station.getLong());
                        }
                    }
                });
    }


    private double calculateAveragePrice(List<ServiceStation> serviceStations) {
        double sum = 0;

        for (ServiceStation serviceStation : serviceStations) {
            sum += serviceStation.getPrice();
        }

        return sum / serviceStations.size();
    }

    public void onMapReady() {

    }

    public void onStart() {
        googleApiClient.connect();
    }

    public void onStop() {
        googleApiClient.disconnect();
    }

    public void onServiceStationSelected(ServiceStation serviceStation) {
        getView().centerMapTo(serviceStation.getLat(), serviceStation.getLong());
        getView().caaaasda();
    }
}
