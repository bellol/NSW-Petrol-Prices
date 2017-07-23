package com.bellng.fuelprices.presenter;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bellng.fuelprices.ServiceStationSource;
import com.bellng.fuelprices.dto.ServiceStation;
import com.bellng.fuelprices.view.DashboardView;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.maps.android.ui.IconGenerator;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

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
 * Created by Bell on 20-Nov-16.
 */

public class DashboardPresenter extends MvpBasePresenter<DashboardView> {

    ServiceStationSource serviceStationSource;
    GoogleApiClient googleApiClient;

    Location lastLocation;

    public DashboardPresenter(ServiceStationSource serviceStationSource, GoogleApiClient googleApiClient) {
        this.serviceStationSource = serviceStationSource;
        this.googleApiClient = googleApiClient;
    }

    public void onAttach() {

    }

    private Observable<Location> getUpdatedLocation() {
        return isGoogleApiClientConnected().flatMap(isConnected -> {
            if (!isConnected) {
                return observeConnectionCallbacks().flatMap(new Func1<String, Observable<Location>>() {
                    @Override
                    public Observable<Location> call(String s) {
                        lastLocation = FusedLocationApi.getLastLocation(googleApiClient);
                        return Observable.just(lastLocation);
                    }
                });
            }
            lastLocation = FusedLocationApi.getLastLocation(googleApiClient);
            return Observable.just(lastLocation);
        });
    }


    private Observable<Boolean> isGoogleApiClientConnected() {
        return Observable.just(googleApiClient.isConnected());
    }

    private Observable<String> observeConnectionCallbacks() {
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
                stringEmitter.setCancellation(() -> googleApiClient.unregisterConnectionCallbacks(callbacks));
                googleApiClient.registerConnectionCallbacks(callbacks);
            }
        }, Emitter.BackpressureMode.BUFFER);

    }

    private double calculateAveragePrice(List<ServiceStation> serviceStations) {
        double sum = 0;

        for (ServiceStation serviceStation : serviceStations) {
            sum += serviceStation.getPrice();
        }

        return sum / serviceStations.size();
    }

    public void onStart() {
        googleApiClient.connect();
    }

    public void onStop() {
        googleApiClient.disconnect();
    }

    public void onMapReady() {
        refreshData();
    }

    private void refreshData() {
        getUpdatedLocation()
                .flatMap(location -> {
                    getView().moveCamera(location.getLatitude(), location.getLongitude());
                    return serviceStationSource.update(location.getLatitude(), location.getLongitude());
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
                        for (ServiceStation station : serviceStations) {
                            getView().addServiceStationMarker(station.getName(), station.getPrice().toString(), station.getLat(), station.getLong());
                        }
                    }
                });
    }

    public void onListViewClicked() {
        getView().showListViewActivity();
    }

    public void onSettingsClicked() {
        getView().showSettingsView();
    }

    public void onRefreshClicked() {
        refreshData();
    }
}
