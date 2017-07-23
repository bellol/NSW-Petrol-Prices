package com.bellng.fuelprices.view;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Bell on 20-Nov-16.
 */

public interface DashboardView extends MvpView {
    void clearMarkers();

    void setAveragePrice(double averagePrice);

    void addServiceStationMarker(String name, String snippet, double latitude, double longitude);

    void moveCamera(double latitude, double longitude);

    void showListViewActivity();

    void showSettingsView();
}
