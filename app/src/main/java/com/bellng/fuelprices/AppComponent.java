package com.bellng.fuelprices;

import com.bellng.fuelprices.activity.DashboardActivity;
import com.bellng.fuelprices.activity.ServiceStationListActivity;
import com.bellng.fuelprices.activity.SettingsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bell on 19-Nov-16.
 */

@Singleton
@Component(modules={AppModule.class})
public interface AppComponent {
    void inject(DashboardActivity activity);

    void inject(ServiceStationListActivity activity);

    void inject(SettingsActivity settingsActivity);
}
