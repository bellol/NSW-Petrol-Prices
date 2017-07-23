package com.bellng.fuelprices;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bellng.fuelprices.presenter.DashboardPresenter;
import com.bellng.fuelprices.presenter.ServiceStationListPresenter;
import com.bellng.fuelprices.presenter.SettingsPresenter;
import com.bellng.fuelprices.service.FuelService;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * Created by Bell on 19-Nov-16.
 */

@Module
public class AppModule {

    FuelApplication application;

    public AppModule(FuelApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    FuelApplication provideFuelApplication() {
        return application;
    }

    @Provides
    DashboardPresenter provideDashboardPresenter(ServiceStationSource source, GoogleApiClient googleApiClient){
        return new DashboardPresenter(source, googleApiClient);
    }

    @Provides
    ServiceStationListPresenter provideServiceStationListPresenter(ServiceStationSource source){
        return new ServiceStationListPresenter(source);
    }

    @Provides
    SettingsPresenter provideSettingsPresenter(SharedPreferences sharedPreferences){
        return new SettingsPresenter(sharedPreferences);
    }

    @Provides
    SharedPreferences provideSharedPreferences(FuelApplication application){
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    ServiceStationSource provideServiceStationSource(FuelService service, SharedPreferences sharedPreferences){
        return new ServiceStationSource(service,sharedPreferences);
    }

    @Provides
    @Singleton
    GoogleApiClient provideGoogleApiClient(FuelApplication application) {
        return new GoogleApiClient.Builder(application)
                .addApi(LocationServices.API)
                .build();
    }

    @Provides
    @Singleton
    FuelService provideFuelService() {
        String baseUrl = "https://api.onegov.nsw.gov.au/FuelCheckApp/v1/fuel/prices/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(FuelService.class);
    }
}
