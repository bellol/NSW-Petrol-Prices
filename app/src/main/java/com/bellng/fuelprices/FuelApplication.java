package com.bellng.fuelprices;

import android.app.Application;

/**
 * Created by Bell on 20-Nov-16.
 */

public class FuelApplication extends Application {

    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent component(){
        return appComponent;
    }
}
