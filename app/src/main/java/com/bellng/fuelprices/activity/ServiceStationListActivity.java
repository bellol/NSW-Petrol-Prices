package com.bellng.fuelprices.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bellng.fuelprices.R;
import com.bellng.fuelprices.ServiceStationAdapter;
import com.bellng.fuelprices.dto.ServiceStation;
import com.bellng.fuelprices.presenter.ServiceStationListPresenter;
import com.bellng.fuelprices.view.ServiceStationListView;

import java.util.List;

import butterknife.BindView;

public class ServiceStationListActivity extends BaseMvpActivity<ServiceStationListView, ServiceStationListPresenter> implements ServiceStationListView {


    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    ServiceStationAdapter serviceStationAdapter;


    @Override
    public void inject() {
        appComponent.inject(this);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_service_station_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        serviceStationAdapter = new ServiceStationAdapter(serviceStation -> presenter.onServiceStationSelected(serviceStation));

        recyclerView.setAdapter(serviceStationAdapter);

        presenter.onAttach();
    }

    @Override
    public void setServiceStationList(List<ServiceStation> serviceStations) {
        serviceStationAdapter.setServiceStations(serviceStations);
        serviceStationAdapter.notifyDataSetChanged();
    }

}
