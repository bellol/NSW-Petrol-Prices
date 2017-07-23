package com.bellng.fuelprices.presenter;

import com.bellng.fuelprices.ServiceStationSource;
import com.bellng.fuelprices.dto.ServiceStation;
import com.bellng.fuelprices.view.ServiceStationListView;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

import rx.Observer;

/**
 * Created by Bell on 20-Nov-16.
 */

public class ServiceStationListPresenter extends MvpBasePresenter<ServiceStationListView> {

    ServiceStationSource serviceStationSource;

    public ServiceStationListPresenter(ServiceStationSource serviceStationSource) {
        this.serviceStationSource = serviceStationSource;
    }

    public void onAttach() {
        serviceStationSource.getServiceStations()
                .subscribe(new Observer<List<ServiceStation>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<ServiceStation> serviceStations) {
                        getView().setServiceStationList(serviceStations);
                    }
                });
    }

    public void onServiceStationSelected(ServiceStation serviceStation) {

    }
}
