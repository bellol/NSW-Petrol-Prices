package com.bellng.fuelprices.view;

import com.bellng.fuelprices.dto.ServiceStation;
import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.List;

/**
 * Created by Bell on 20-Nov-16.
 */

public interface ServiceStationListView extends MvpView {
    void setServiceStationList(List<ServiceStation> serviceStations);
}
