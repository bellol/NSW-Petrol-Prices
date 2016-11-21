package com.bellng.fuelprices.view;

import com.bellng.fuelprices.dto.ServiceStation;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Bell on 19-Nov-16.
 */

public interface FuelView extends MvpView {

    void setFuelTypeSpinner(List<String> fuelTypes);

    void setAveragePrice(double averagePrice);

    void setServiceStationList(List<ServiceStation> serviceStations);

    void clearMarkers();

    void addServiceStationMarker(String name, double latitude, double longitude);

    void centerMapTo(double latitude, double longitude);

    void caaaasda();
}
